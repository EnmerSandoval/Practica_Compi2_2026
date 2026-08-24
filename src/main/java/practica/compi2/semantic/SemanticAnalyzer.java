package practica.compi2.semantic;

import practica.compi2.errors.CompileError;
import practica.compi2.tree.ast.*;
import practica.compi2.visitors.AstNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class SemanticAnalyzer {

    private final List<CompileError> errors = new ArrayList<>();
    private final Map<String, StructSymbol> structs = new LinkedHashMap<>();
    private final Map<String, FunctionSymbol> functions = new LinkedHashMap<>();
    private final TypeSystem typeSystem = new TypeSystem();

    private Scope globalScope;
    private FunctionDeclarationNode currentFunction;
    private boolean insideFunctionBody;
    private int loopDepth;

    public List<CompileError> analyze(ProgramNode program) {
        globalScope = new Scope("global", null);

        collectStructsRecursively(program);
        validateStructAttributeTypes();
        for (AstNode functionNode : program.functions()) {

            if (functionNode != null) {
                registerFunction((FunctionDeclarationNode) functionNode);
            }
        }

        for (AstNode global : program.globalVariables()) {
            analyzeStatement(global, globalScope);
        }

        for (AstNode functionNode : program.functions()) {
            if (functionNode != null) {
                analyzeFunction((FunctionDeclarationNode) functionNode);
            }
        }

        currentFunction = null;
        insideFunctionBody = false;
        loopDepth = 0;
        if (program.mainBlock() != null) {
            analyzeBlock(program.mainBlock(), new Scope("main", globalScope));
        }

        return errors;
    }

    public SymbolTable symbolTable() {
        return new SymbolTable(globalScope, structs, functions);
    }


    private void collectStructsRecursively(AstNode node) {
        if (node instanceof StructDefinitionNode structDef) {
            registerStruct(structDef);
        }
        for (AstNode child : node.children()) {
            collectStructsRecursively(child);
        }
    }

    private void registerStruct(StructDefinitionNode def) {
        if (structs.containsKey(def.name())) {
            error("la estructura '" + def.name() + "' ya fue declarada", def);
            return;
        }
        LinkedHashMap<String, AttributeInfo> attributes = new LinkedHashMap<>();
        for (StructAttributeNode attribute : def.attributes()) {
            if (attributes.containsKey(attribute.name())) {
                error("el atributo '" + attribute.name() + "' esta repetido en la estructura '" + def.name() + "'", attribute);
                continue;
            }
            attributes.put(attribute.name(), new AttributeInfo(typeSystem.normalize(attribute.type()), attribute.isArray()));
        }
        structs.put(def.name(), new StructSymbol(def.name(), attributes, def.line(), def.column()));
    }

    private void validateStructAttributeTypes() {
        for (StructSymbol struct : structs.values()) {
            for (Map.Entry<String, AttributeInfo> entry : struct.attributes().entrySet()) {
                String type = entry.getValue().type();
                if (!typeSystem.isPrimitive(type) && !structs.containsKey(type)) {
                    error("tipo desconocido '" + type + "' en el atributo '" + entry.getKey() + "' de la estructura '" + struct.name() + "'",
                            struct.line(), struct.column());
                }
            }
        }
    }

    private void registerFunction(FunctionDeclarationNode fn) {
        if (functions.containsKey(fn.name())) {
            error("la funcion '" + fn.name() + "' ya fue declarada", fn);
            return;
        }
        List<VariableSymbol> parameters = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (ParameterNode parameter : fn.parameters()) {
            String type = typeSystem.normalize(parameter.type());
            if (!seen.add(parameter.name())) {
                error("el parametro '" + parameter.name() + "' esta repetido en la funcion '" + fn.name() + "'", parameter);
            }
            parameters.add(new VariableSymbol(parameter.name(), type, false, null, true, parameter.line(), parameter.column()));
        }
        String returnType = fn.returnType() == null ? null : typeSystem.normalize(fn.returnType());
        functions.put(fn.name(), new FunctionSymbol(fn.name(), returnType, parameters, fn.line(), fn.column()));
    }


    private void analyzeFunction(FunctionDeclarationNode fn) {
        Scope fnScope = new Scope("func:" + fn.name(), globalScope);
        for (ParameterNode parameter : fn.parameters()) {
            String type = typeSystem.normalize(parameter.type());
            if (!typeSystem.isPrimitive(type) && !structs.containsKey(type)) {
                error("tipo desconocido '" + type + "' en el parametro '" + parameter.name() + "'", parameter);
            }
            if (!fnScope.declaredInThisScope(parameter.name())) {
                fnScope.declare(new VariableSymbol(parameter.name(), type, false, null, true, parameter.line(), parameter.column()));
            }
        }
        for (VariableDeclarationNode local : fn.localVariables()) {
            analyzeVariableDeclaration(local, fnScope);
        }

        FunctionDeclarationNode previousFunction = currentFunction;
        boolean previousInside = insideFunctionBody;
        int previousLoopDepth = loopDepth;
        currentFunction = fn;
        insideFunctionBody = true;
        loopDepth = 0;

        BlockNode bodyBlock = new BlockNode(fn.line(), fn.column(), fn.body());
        analyzeBlock(bodyBlock, fnScope);

        if (fn.returnType() != null && !pathAlwaysReturns(bodyBlock)) {
            error("la funcion '" + fn.name() + "' no retorna un valor en todos los caminos posibles", fn);
        }

        currentFunction = previousFunction;
        insideFunctionBody = previousInside;
        loopDepth = previousLoopDepth;
    }


    private void analyzeVariableDeclaration(VariableDeclarationNode decl, Scope scope) {
        String type = typeSystem.normalize(decl.type());
        if (!typeSystem.isPrimitive(type) && !structs.containsKey(type)) {
            error("tipo desconocido '" + type + "' en la declaracion de '" + decl.name() + "'", decl);
        }
        if (scope.declaredInThisScope(decl.name())) {
            error("'" + decl.name() + "' ya fue declarado en este ambito", decl);
        } else {
            scope.declare(new VariableSymbol(decl.name(), type, decl.isArray(), decl.arraySize(), false, decl.line(), decl.column()));
        }

        if (decl.initializer() == null) {
            return;
        }

        if (decl.isArray()) {
            if (decl.initializer() instanceof ArrayInitializerNode arrayInit) {
                if (decl.arraySize() != null && arrayInit.elements().size() != decl.arraySize()) {
                    error("el arreglo '" + decl.name() + "' declara tamano " + decl.arraySize()
                            + " pero el inicializador tiene " + arrayInit.elements().size() + " elementos", decl);
                }
                for (AstNode element : arrayInit.elements()) {
                    String elementType = inferExpressionType(resolverAnonimo(element, type), scope);
                    if (elementType != null && !compatible(elementType, type)) {
                        error("elemento incompatible en el arreglo '" + decl.name() + "': se esperaba " + type + ", se obtuvo " + elementType, element);
                    }
                }
            }
            return;
        }

        String valueType = inferExpressionType(resolverAnonimo(decl.initializer(), type), scope);
        if (valueType != null && !compatible(valueType, type)) {
            error("tipo incompatible al inicializar '" + decl.name() + "': se esperaba " + type + ", se obtuvo " + valueType, decl);
        }
    }


    private AstNode resolverAnonimo(AstNode expresion, String tipoEsperado) {
        if (tipoEsperado != null && expresion instanceof StructInitializerNode anonimo && anonimo.typeName() == null) {
            return new StructInitializerNode(anonimo.line(), anonimo.column(), tipoEsperado, anonimo.fields());
        }
        return expresion;
    }


    private void analyzeBlock(BlockNode block, Scope scope) {
        boolean terminated = false;
        boolean reportedUnreachable = false;
        for (AstNode statement : block.statements()) {

            if (statement == null) {
                continue;
            }
            if (terminated && !reportedUnreachable) {
                error("codigo inalcanzable: ya se retorno un valor en todos los caminos anteriores", statement);
                reportedUnreachable = true;
            }
            analyzeStatement(statement, scope);
            if (isTerminal(statement)) {
                terminated = true;
            }
        }
    }

    private void analyzeStatement(AstNode statement, Scope scope) {
        switch (statement) {
            case null -> {

            }
            case VariableDeclarationNode decl -> {
                if (insideFunctionBody) {
                    error("las variables solo se pueden declarar al inicio de la funcion (seccion VARIABILES[...])", decl);
                }
                analyzeVariableDeclaration(decl, scope);
            }
            case StructDefinitionNode ignored -> {

            }
            case AssignmentNode assignment -> analyzeAssignment(assignment, scope);
            case IfNode ifNode -> analyzeIf(ifNode, scope);
            case WhileNode whileNode -> {
                checkBooleanCondition(whileNode.condition(), scope);
                loopDepth++;
                analyzeBlock(whileNode.body(), new Scope("dum", scope));
                loopDepth--;
            }
            case DoWhileNode doWhileNode -> {
                loopDepth++;
                analyzeBlock(doWhileNode.body(), new Scope("facere", scope));
                loopDepth--;
                checkBooleanCondition(doWhileNode.condition(), scope);
            }
            case ForNode forNode -> analyzeFor(forNode, scope);
            case BreakNode breakNode -> {
                if (loopDepth == 0) {
                    error("'interrumpe' solo puede usarse dentro de un ciclo", breakNode);
                }
            }
            case ContinueNode continueNode -> {
                if (loopDepth == 0) {
                    error("'perge' solo puede usarse dentro de un ciclo", continueNode);
                }
            }
            case ReturnNode returnNode -> analyzeReturn(returnNode, scope);
            case ReadNode readNode -> {
                if (readNode.target() instanceof AccessNode access) {
                    inferAccessType(access, scope, true);
                }
            }
            case WriteNode writeNode -> {
                for (AstNode expression : writeNode.expressions()) {
                    inferExpressionType(expression, scope);
                }
            }
            case ExpressionStatementNode expressionStatement -> {
                if (expressionStatement.expression() instanceof FunctionCallNode call) {
                    inferFunctionCallType(call, scope, false);
                } else {
                    inferExpressionType(expressionStatement.expression(), scope);
                }
            }
            default -> {

            }
        }
    }

    private void analyzeIf(IfNode ifNode, Scope scope) {
        checkBooleanCondition(ifNode.condition(), scope);
        analyzeBlock(ifNode.thenBlock(), new Scope("si", scope));
        AstNode elseBranch = ifNode.elseBranch();
        if (elseBranch instanceof IfNode elseIf) {
            analyzeIf(elseIf, scope);
        } else if (elseBranch instanceof BlockNode elseBlock) {
            analyzeBlock(elseBlock, new Scope("aliter", scope));
        }
    }

    private void analyzeFor(ForNode forNode, Scope scope) {
        Scope forScope = new Scope("per", scope);
        analyzeVariableDeclaration(forNode.init(), forScope);
        checkBooleanCondition(forNode.condition(), forScope);
        if (forNode.increment() instanceof AssignmentNode assignment) {
            analyzeAssignment(assignment, forScope);
        } else {
            inferExpressionType(forNode.increment(), forScope);
        }
        loopDepth++;
        analyzeBlock(forNode.body(), new Scope("per-body", forScope));
        loopDepth--;
    }

    private void analyzeReturn(ReturnNode returnNode, Scope scope) {
        if (currentFunction == null) {
            error("'reddere' solo puede usarse dentro de una funcion", returnNode);
            return;
        }
        String expectedType = currentFunction.returnType() == null ? null : typeSystem.normalize(currentFunction.returnType());
        if (returnNode.value() == null) {
            if (expectedType != null) {
                error("la funcion '" + currentFunction.name() + "' debe retornar un valor de tipo " + expectedType, returnNode);
            }
            return;
        }
        if (expectedType == null) {
            error("la funcion '" + currentFunction.name() + "' no tiene tipo de retorno (actio) y no puede retornar un valor", returnNode);
            return;
        }
        String actualType = inferExpressionType(resolverAnonimo(returnNode.value(), expectedType), scope);
        if (actualType != null && !compatible(actualType, expectedType)) {
            error("tipo de retorno incorrecto en '" + currentFunction.name() + "': se esperaba " + expectedType + ", se obtuvo " + actualType, returnNode);
        }
    }

    private void analyzeAssignment(AssignmentNode assignment, Scope scope) {
        if (!(assignment.target() instanceof AccessNode target)) {
            return;
        }
        String targetType = inferAccessType(target, scope, true);
        String valueType = inferExpressionType(resolverAnonimo(assignment.value(), targetType), scope);
        if (targetType != null && valueType != null && !compatible(valueType, targetType)) {
            error("tipo incompatible en asignacion a '" + target.label() + "': se esperaba " + targetType + ", se obtuvo " + valueType, assignment);
        }
    }

    private boolean isTerminal(AstNode statement) {
        if (statement instanceof ReturnNode) {
            return true;
        }
        if (statement instanceof IfNode ifNode) {
            return ifChainAlwaysReturns(ifNode);
        }
        if (statement instanceof DoWhileNode doWhileNode) {
            return pathAlwaysReturns(doWhileNode.body());
        }
        return false;
    }

    private boolean ifChainAlwaysReturns(IfNode ifNode) {
        if (!pathAlwaysReturns(ifNode.thenBlock())) {
            return false;
        }
        AstNode elseBranch = ifNode.elseBranch();
        if (elseBranch instanceof IfNode elseIf) {
            return ifChainAlwaysReturns(elseIf);
        }
        if (elseBranch instanceof BlockNode elseBlock) {
            return pathAlwaysReturns(elseBlock);
        }
        return false;
    }

    private boolean pathAlwaysReturns(BlockNode block) {
        for (AstNode statement : block.statements()) {
            if (isTerminal(statement)) {
                return true;
            }
        }
        return false;
    }


    private String inferExpressionType(AstNode node, Scope scope) {
        return switch (node) {

            case null -> null;
            case IntegerLiteralNode ignored -> TypeSystem.NUMERUS;
            case DecimalLiteralNode ignored -> TypeSystem.DECIMALIS;
            case StringLiteralNode ignored -> TypeSystem.TEXTUM;
            case CharLiteralNode ignored -> TypeSystem.LITTERA;
            case BooleanLiteralNode ignored -> TypeSystem.BOOLEANO;
            case AccessNode access -> inferAccessType(access, scope, false);
            case FunctionCallNode call -> inferFunctionCallType(call, scope, true);
            case BinaryExpressionNode binary -> inferBinaryType(binary, scope);
            case UnaryExpressionNode unary -> inferUnaryType(unary, scope);
            case PostfixExpressionNode postfix -> inferPostfixType(postfix, scope);
            case StructInitializerNode structInit -> inferStructInitializerType(structInit, scope);
            default -> null;
        };
    }


    private String inferAccessType(AccessNode access, Scope scope, boolean isAssignTarget) {
        if (access.base() == null) {
            VariableSymbol base = scope.resolve(access.name());
            if (base == null) {
                error("'" + access.name() + "' no ha sido declarado", access);
                return null;
            }
            if (access.index() != null) {
                if (!base.isArray()) {
                    error("'" + base.name() + "' no es un arreglo", access);
                    return null;
                }
                checkIndex(access.index(), scope, base.arraySize());
                return base.type();
            }
            if (base.isArray() && !isAssignTarget) {
                error("no se puede usar el arreglo '" + base.name() + "' directamente en una expresion, use un indice", access);
                return null;
            }
            if (!base.isArray() && !typeSystem.isPrimitive(base.type()) && !isAssignTarget) {
                error("no se puede usar la estructura '" + base.name() + "' directamente en una expresion", access);
                return null;
            }
            return base.type();
        }

        String baseType = inferAccessType((AccessNode) access.base(), scope, true);
        if (baseType == null) {
            return null;
        }
        StructSymbol struct = structs.get(baseType);
        if (struct == null) {
            error("'" + access.base().label() + "' no es una estructura", access);
            return null;
        }
        AttributeInfo attribute = struct.attributes().get(access.name());
        if (attribute == null) {
            error("la estructura '" + struct.name() + "' no tiene el atributo '" + access.name() + "'", access);
            return null;
        }
        if (access.index() != null) {
            if (!attribute.isArray()) {
                error("el atributo '" + access.name() + "' no es un arreglo", access);
                return null;
            }
            checkIndex(access.index(), scope, null);
            return attribute.type();
        }
        if (attribute.isArray() && !isAssignTarget) {
            error("no se puede usar el arreglo '" + access.name() + "' directamente en una expresion, use un indice", access);
            return null;
        }
        return attribute.type();
    }

    private void checkIndex(AstNode indexExpr, Scope scope, Integer bound) {
        String indexType = inferExpressionType(indexExpr, scope);
        if (indexType != null && !indexType.equals(TypeSystem.NUMERUS)) {
            error("el indice de un arreglo debe ser de tipo numerus (se obtuvo " + indexType + ")", indexExpr);
            return;
        }
        if (bound == null) {
            return;
        }
        Optional<Long> constant = evalConstantInt(indexExpr);
        if (constant.isPresent() && (constant.get() < 0 || constant.get() >= bound)) {
            error("indice fuera de rango: " + constant.get() + " (el arreglo tiene tamano " + bound + ")", indexExpr);
        }
    }

    private Optional<Long> evalConstantInt(AstNode expr) {
        if (expr instanceof IntegerLiteralNode literal) {
            return Optional.of(literal.value());
        }
        if (expr instanceof UnaryExpressionNode unary && ("-".equals(unary.operator()) || "+".equals(unary.operator()))) {
            return evalConstantInt(unary.operand()).map(value -> "-".equals(unary.operator()) ? -value : value);
        }
        if (expr instanceof BinaryExpressionNode binary) {
            Optional<Long> left = evalConstantInt(binary.left());
            Optional<Long> right = evalConstantInt(binary.right());
            if (left.isEmpty() || right.isEmpty()) {
                return Optional.empty();
            }
            long l = left.get();
            long r = right.get();
            return switch (binary.operator()) {
                case "+" -> Optional.of(l + r);
                case "-" -> Optional.of(l - r);
                case "*" -> Optional.of(l * r);
                case "/" -> {
                    if (r == 0) {
                        error("division entre cero", binary);
                        yield Optional.empty();
                    }
                    yield Optional.of(l / r);
                }
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }

    private String inferFunctionCallType(FunctionCallNode call, Scope scope, boolean requireReturnValue) {
        FunctionSymbol function = functions.get(call.name());
        if (function == null) {
            error("la funcion '" + call.name() + "' no ha sido declarada", call);
            for (AstNode argument : call.arguments()) {
                inferExpressionType(argument, scope);
            }
            return null;
        }
        List<VariableSymbol> parameters = function.parameters();
        if (parameters.size() != call.arguments().size()) {
            error("'" + call.name() + "' espera " + parameters.size() + " argumento(s), se recibieron " + call.arguments().size(), call);
        }
        int limit = Math.min(parameters.size(), call.arguments().size());
        for (int i = 0; i < limit; i++) {
            String parameterType = parameters.get(i).type();
            String argumentType = inferExpressionType(resolverAnonimo(call.arguments().get(i), parameterType), scope);
            if (argumentType != null && !compatible(argumentType, parameterType)) {
                error("argumento " + (i + 1) + " de '" + call.name() + "' incompatible: se esperaba " + parameterType + ", se obtuvo " + argumentType, call);
            }
        }
        for (int i = limit; i < call.arguments().size(); i++) {
            inferExpressionType(call.arguments().get(i), scope);
        }
        if (function.returnType() == null) {
            if (requireReturnValue) {
                error("la funcion '" + call.name() + "' no retorna un valor y no puede usarse en una expresion", call);
            }
            return null;
        }
        return function.returnType();
    }

    private String inferBinaryType(BinaryExpressionNode binary, Scope scope) {
        String left = inferExpressionType(binary.left(), scope);
        String right = inferExpressionType(binary.right(), scope);
        if (left == null || right == null) {
            return null;
        }
        String operator = binary.operator();
        return switch (operator) {
            case "+", "-", "*", "/" -> {
                if (!typeSystem.isPrimitive(left) || !typeSystem.isPrimitive(right)) {
                    error("los operandos de '" + operator + "' deben ser tipos primitivos", binary);
                    yield null;
                }
                boolean textInvolved = left.equals(TypeSystem.TEXTUM) || right.equals(TypeSystem.TEXTUM);
                if (textInvolved && !operator.equals("+")) {
                    error("textum solo admite concatenacion ('+'), no '" + operator + "'", binary);
                    yield null;
                }
                if (textInvolved) {
                    yield TypeSystem.TEXTUM;
                }
                yield typeSystem.rank(left) >= typeSystem.rank(right) ? left : right;
            }
            case "==", "!=", "<", ">", "<=", ">=" -> {
                if (left.equals(TypeSystem.TEXTUM) || right.equals(TypeSystem.TEXTUM)) {
                    error("textum no admite el operador '" + operator + "'", binary);
                    yield null;
                }
                if (!typeSystem.isPrimitive(left) || !typeSystem.isPrimitive(right)) {
                    error("operandos invalidos para '" + operator + "'", binary);
                    yield null;
                }
                yield TypeSystem.BOOLEANO;
            }
            case "&&", "||" -> {
                if (!left.equals(TypeSystem.BOOLEANO) || !right.equals(TypeSystem.BOOLEANO)) {
                    error("los operandos de '" + operator + "' deben ser booleano", binary);
                    yield null;
                }
                yield TypeSystem.BOOLEANO;
            }
            default -> null;
        };
    }

    private String inferUnaryType(UnaryExpressionNode unary, Scope scope) {
        String operandType = inferExpressionType(unary.operand(), scope);
        if (operandType == null) {
            return null;
        }
        return switch (unary.operator()) {
            case "non" -> {
                if (!operandType.equals(TypeSystem.BOOLEANO)) {
                    error("'non' requiere un operando booleano (se obtuvo " + operandType + ")", unary);
                    yield null;
                }
                yield TypeSystem.BOOLEANO;
            }
            case "-", "+" -> {
                if (!operandType.equals(TypeSystem.NUMERUS) && !operandType.equals(TypeSystem.DECIMALIS) && !operandType.equals(TypeSystem.LITTERA)) {
                    error("el operador unario '" + unary.operator() + "' requiere un tipo numerico", unary);
                    yield null;
                }
                yield operandType;
            }
            case "++", "--" -> {
                if (!(unary.operand() instanceof AccessNode)) {
                    error("solo se puede incrementar/decrementar una variable", unary);
                    yield null;
                }
                if (!operandType.equals(TypeSystem.NUMERUS)) {
                    error("'" + unary.operator() + "' requiere numerus (se obtuvo " + operandType + ")", unary);
                    yield null;
                }
                yield TypeSystem.NUMERUS;
            }
            default -> null;
        };
    }

    private String inferPostfixType(PostfixExpressionNode postfix, Scope scope) {
        String operandType = inferExpressionType(postfix.operand(), scope);
        if (operandType == null) {
            return null;
        }
        if (!(postfix.operand() instanceof AccessNode)) {
            error("solo se puede incrementar/decrementar una variable", postfix);
            return null;
        }
        if (!operandType.equals(TypeSystem.NUMERUS)) {
            error("'" + postfix.operator() + "' requiere numerus (se obtuvo " + operandType + ")", postfix);
            return null;
        }
        return TypeSystem.NUMERUS;
    }

    private String inferStructInitializerType(StructInitializerNode structInit, Scope scope) {
        if (structInit.typeName() == null) {
            error("no se pudo determinar el tipo de esta estructura a partir del contexto", structInit);
            for (StructFieldInitNode field : structInit.fields()) {
                if (field.value() != null) {
                    inferExpressionType(field.value(), scope);
                }
            }
            return null;
        }
        StructSymbol struct = structs.get(structInit.typeName());
        if (struct == null) {
            error("la estructura '" + structInit.typeName() + "' no ha sido declarada", structInit);
            for (StructFieldInitNode field : structInit.fields()) {
                if (field.value() != null) {
                    inferExpressionType(field.value(), scope);
                }
            }
            return null;
        }

        Set<String> seen = new HashSet<>();
        for (StructFieldInitNode field : structInit.fields()) {
            AttributeInfo attribute = struct.attributes().get(field.name());
            if (attribute == null) {
                error("la estructura '" + struct.name() + "' no tiene el atributo '" + field.name() + "'", field);
                continue;
            }
            if (!seen.add(field.name())) {
                error("el atributo '" + field.name() + "' esta repetido en el inicializador de '" + struct.name() + "'", field);
                continue;
            }
            if (field.elementType() != null) {
                if (!attribute.isArray()) {
                    error("'" + field.name() + "' no es un atributo de tipo arreglo", field);
                } else if (!typeSystem.normalize(field.elementType()).equals(attribute.type())) {
                    error("tipo de elemento incorrecto para '" + field.name() + "': se esperaba " + attribute.type()
                            + ", se obtuvo " + field.elementType(), field);
                }
                continue;
            }
            if (attribute.isArray()) {
                error("'" + field.name() + "' es un arreglo, use la forma 'Tipo[N]'", field);
                continue;
            }
            String valueType = inferExpressionType(resolverAnonimo(field.value(), attribute.type()), scope);
            if (valueType != null && !compatible(valueType, attribute.type())) {
                error("tipo incompatible para el atributo '" + field.name() + "': se esperaba " + attribute.type() + ", se obtuvo " + valueType, field);
            }
        }
        for (String attributeName : struct.attributes().keySet()) {
            if (!seen.contains(attributeName)) {
                error("falta el atributo '" + attributeName + "' en el inicializador de '" + struct.name() + "'", structInit);
            }
        }
        return struct.name();
    }

    private void checkBooleanCondition(AstNode condition, Scope scope) {
        String type = inferExpressionType(condition, scope);
        if (type != null && !type.equals(TypeSystem.BOOLEANO)) {
            error("la condicion debe ser de tipo booleano (se obtuvo " + type + ")", condition);
        }
    }

    private boolean compatible(String valueType, String targetType) {
        return valueType.equals(targetType) || typeSystem.isAssignable(valueType, targetType);
    }

    private void error(String message, AstNode node) {
        error(message, node.line(), node.column());
    }

    private void error(String message, int line, int column) {
        errors.add(new CompileError("semantico", message, line, column));
    }
}
