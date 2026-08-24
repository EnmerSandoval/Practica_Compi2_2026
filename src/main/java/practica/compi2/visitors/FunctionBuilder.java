package practica.compi2.visitors;

import practica.compi2.GrammarParser;
import practica.compi2.tree.ast.FunctionDeclarationNode;
import practica.compi2.tree.ast.ParameterNode;
import practica.compi2.tree.ast.VariableDeclarationNode;

import java.util.ArrayList;
import java.util.List;

public class FunctionBuilder {
    private final AstBuilderVisitor visitor;
    private final Posiciones posiciones = new Posiciones();
    private final TipoResolver tipoResolver = new TipoResolver();

    FunctionBuilder(AstBuilderVisitor visitor) {
        this.visitor = visitor;
    }

    AstNode visitDefinicionFuncion(GrammarParser.DefinicionFuncionContext ctx) {
        if (ctx.funcionSinRetorno() != null) {
            return visitor.visit(ctx.funcionSinRetorno());
        }
        return visitor.visit(ctx.funcionConRetorno());
    }

    AstNode visitFuncionSinRetorno(GrammarParser.FuncionSinRetornoContext ctx) {
        String name = ctx.identificador().getText();
        List<ParameterNode> parameters = parametersOf(ctx.parametros());
        List<VariableDeclarationNode> localVariables = localVariablesOf(ctx.bloqueFuncion());
        List<AstNode> body = bodyOf(ctx.bloqueFuncion());
        return new FunctionDeclarationNode(posiciones.linea(ctx), posiciones.columna(ctx), name, null, parameters, localVariables, body);
    }

    AstNode visitFuncionConRetorno(GrammarParser.FuncionConRetornoContext ctx) {
        String name = ctx.identificador().getText();
        String returnType = tipoResolver.nombreDe(ctx.tipo());
        List<ParameterNode> parameters = parametersOf(ctx.parametros());
        List<VariableDeclarationNode> localVariables = localVariablesOf(ctx.bloqueFuncion());
        List<AstNode> body = bodyOf(ctx.bloqueFuncion());
        return new FunctionDeclarationNode(posiciones.linea(ctx), posiciones.columna(ctx), name, returnType, parameters, localVariables, body);
    }

    private List<ParameterNode> parametersOf(GrammarParser.ParametrosContext ctx) {
        List<ParameterNode> parameters = new ArrayList<>();
        if (ctx == null) {
            return parameters;
        }
        var ids = ctx.identificador();
        var types = ctx.tipo();
        for (int i = 0; i < ids.size(); i++) {
            parameters.add(new ParameterNode(posiciones.linea(ctx), posiciones.columna(ctx), ids.get(i).getText(), tipoResolver.nombreDe(types.get(i))));
        }
        return parameters;
    }

    private List<VariableDeclarationNode> localVariablesOf(GrammarParser.BloqueFuncionContext ctx) {
        List<VariableDeclarationNode> localVariables = new ArrayList<>();
        if (ctx != null && ctx.seccionVariablesFuncion() != null) {
            for (var declCtx : ctx.seccionVariablesFuncion().declaracionVariable()) {
                localVariables.add((VariableDeclarationNode) visitor.visit(declCtx));
            }
        }
        return localVariables;
    }

    private List<AstNode> bodyOf(GrammarParser.BloqueFuncionContext ctx) {
        List<AstNode> body = new ArrayList<>();
        if (ctx == null) {
            return body;
        }
        for (var instruccionCtx : ctx.instruccion()) {
            body.add(visitor.visit(instruccionCtx));
        }
        return body;
    }
}
