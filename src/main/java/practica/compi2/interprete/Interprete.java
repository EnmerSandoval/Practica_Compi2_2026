package practica.compi2.interprete;

import practica.compi2.semantic.TypeSystem;
import practica.compi2.tree.ast.*;
import practica.compi2.visitors.AstNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Interprete {

    private final ConsolaIO consola;
    private final Map<String, FunctionDeclarationNode> funciones = new HashMap<>();
    private final Map<String, StructDefinitionNode> estructuras = new HashMap<>();
    private final EntornoEjecucion global = new EntornoEjecucion(null);
    private final TypeSystem typeSystem = new TypeSystem();
    private String tipoRetornoActual;

    public Interprete(ConsolaIO consola) {
        this.consola = consola;
    }

    public void ejecutar(ProgramNode programa) {
        recolectarEstructuras(programa);
        for (AstNode fn : programa.functions()) {
            if (fn instanceof FunctionDeclarationNode f) {
                funciones.put(f.name(), f);
            }
        }
        for (AstNode g : programa.globalVariables()) {
            if (g instanceof VariableDeclarationNode decl) {
                ejecutarDeclaracion(decl, global);
            }
        }
        ejecutarBloque(programa.mainBlock(), new EntornoEjecucion(global));
    }

    private void recolectarEstructuras(AstNode nodo) {
        if (nodo instanceof StructDefinitionNode def) {
            estructuras.put(def.name(), def);
        }
        for (AstNode hijo : nodo.children()) {
            if (hijo != null) {
                recolectarEstructuras(hijo);
            }
        }
    }


    private void ejecutarBloque(BlockNode bloque, EntornoEjecucion entorno) {
        for (AstNode statement : bloque.statements()) {
            if (statement != null) {
                ejecutarInstruccion(statement, entorno);
            }
        }
    }

    private void ejecutarInstruccion(AstNode statement, EntornoEjecucion entorno) {
        switch (statement) {
            case VariableDeclarationNode decl -> ejecutarDeclaracion(decl, entorno);
            case StructDefinitionNode ignored -> {

            }
            case AssignmentNode asignacion -> ejecutarAsignacion(asignacion, entorno);
            case IfNode si -> ejecutarSi(si, entorno);
            case WhileNode dum -> ejecutarDum(dum, entorno);
            case DoWhileNode facere -> ejecutarFacere(facere, entorno);
            case ForNode per -> ejecutarPer(per, entorno);
            case BreakNode ignored -> throw new SenalRuptura();
            case ContinueNode ignored -> throw new SenalContinuacion();
            case ReturnNode retorno -> {
                Valor valor = retorno.value() != null ? evaluar(retorno.value(), entorno, tipoRetornoActual) : null;
                throw new SenalRetorno(valor);
            }
            case ReadNode lectura -> ejecutarLectura(lectura, entorno);
            case WriteNode escritura -> {
                StringBuilder texto = new StringBuilder();
                for (AstNode expresion : escritura.expressions()) {
                    texto.append(evaluar(expresion, entorno, null).representar());
                }
                consola.escribir(texto.toString());
            }
            case ExpressionStatementNode expr -> evaluar(expr.expression(), entorno, null);
            default -> {
            }
        }
    }

    private void ejecutarDeclaracion(VariableDeclarationNode decl, EntornoEjecucion entorno) {
        if (decl.isArray()) {
            entorno.declarar(decl.name(), evaluarArreglo(decl, entorno));
            return;
        }
        Valor valor = decl.initializer() != null
                ? evaluar(decl.initializer(), entorno, decl.type())
                : valorPorDefecto(decl.type());
        entorno.declarar(decl.name(), ajustarTipo(valor, decl.type()));
    }

    private Valor evaluarArreglo(VariableDeclarationNode decl, EntornoEjecucion entorno) {
        if (decl.initializer() instanceof ArrayInitializerNode init) {
            Valor[] elementos = new Valor[init.elements().size()];
            for (int i = 0; i < elementos.length; i++) {
                elementos[i] = ajustarTipo(evaluar(init.elements().get(i), entorno, decl.type()), decl.type());
            }
            return Valor.arreglo(new ArregloRuntime(elementos, decl.type()));
        }
        int tamano = decl.arraySize() != null ? decl.arraySize() : 0;
        Valor[] elementos = new Valor[tamano];
        Arrays.fill(elementos, valorPorDefecto(decl.type()));
        return Valor.arreglo(new ArregloRuntime(elementos, decl.type()));
    }

    private void ejecutarAsignacion(AssignmentNode asignacion, EntornoEjecucion entorno) {
        if (!(asignacion.target() instanceof AccessNode destino)) {
            return;
        }
        String tipoDestino = leerAcceso(destino, entorno).tipo();
        Valor valor = ajustarTipo(evaluar(asignacion.value(), entorno, tipoDestino), tipoDestino);
        asignarEnAcceso(destino, entorno, valor);
    }

    private void ejecutarSi(IfNode nodo, EntornoEjecucion entorno) {
        if (evaluar(nodo.condition(), entorno, null).comoBooleano()) {
            ejecutarBloque(nodo.thenBlock(), new EntornoEjecucion(entorno));
            return;
        }
        AstNode rama = nodo.elseBranch();
        if (rama instanceof IfNode siguienteSi) {
            ejecutarSi(siguienteSi, entorno);
        } else if (rama instanceof BlockNode bloqueSino) {
            ejecutarBloque(bloqueSino, new EntornoEjecucion(entorno));
        }
    }

    private void ejecutarDum(WhileNode nodo, EntornoEjecucion entorno) {
        while (evaluar(nodo.condition(), entorno, null).comoBooleano()) {
            try {
                ejecutarBloque(nodo.body(), new EntornoEjecucion(entorno));
            } catch (SenalRuptura r) {
                break;
            } catch (SenalContinuacion c) {

            }
        }
    }

    private void ejecutarFacere(DoWhileNode nodo, EntornoEjecucion entorno) {
        do {
            try {
                ejecutarBloque(nodo.body(), new EntornoEjecucion(entorno));
            } catch (SenalRuptura r) {
                break;
            } catch (SenalContinuacion c) {

            }
        } while (evaluar(nodo.condition(), entorno, null).comoBooleano());
    }

    private void ejecutarPer(ForNode nodo, EntornoEjecucion entorno) {
        EntornoEjecucion entornoPer = new EntornoEjecucion(entorno);
        ejecutarDeclaracion(nodo.init(), entornoPer);
        while (evaluar(nodo.condition(), entornoPer, null).comoBooleano()) {
            try {
                ejecutarBloque(nodo.body(), new EntornoEjecucion(entornoPer));
            } catch (SenalRuptura r) {
                break;
            } catch (SenalContinuacion c) {
            }
            ejecutarIncremento(nodo.increment(), entornoPer);
        }
    }

    private void ejecutarIncremento(AstNode incremento, EntornoEjecucion entorno) {
        switch (incremento) {
            case AssignmentNode asignacion -> ejecutarAsignacion(asignacion, entorno);
            case PostfixExpressionNode postfix -> evaluar(postfix, entorno, null);
            default -> {
            }
        }
    }

    private void ejecutarLectura(ReadNode lectura, EntornoEjecucion entorno) {
        if (!(lectura.target() instanceof AccessNode destino)) {
            consola.leer(null);
            return;
        }
        String tipoActual = leerAcceso(destino, entorno).tipo();
        String textoLeido = consola.leer(destino.label());
        asignarEnAcceso(destino, entorno, parsear(textoLeido, tipoActual, destino));
    }

    private Valor parsear(String texto, String tipo, AstNode nodo) {
        String limpio = texto == null ? "" : texto.trim();
        try {
            return switch (tipo) {
                case TypeSystem.NUMERUS -> Valor.numero(Long.parseLong(limpio));
                case TypeSystem.DECIMALIS -> Valor.decimal(Double.parseDouble(limpio));
                case TypeSystem.LITTERA -> Valor.caracter(limpio.isEmpty() ? ' ' : limpio.charAt(0));
                case TypeSystem.BOOLEANO -> Valor.booleano(limpio.equalsIgnoreCase("verum"));
                default -> Valor.texto(limpio);
            };
        } catch (NumberFormatException e) {
            throw error("el valor ingresado '" + texto + "' no es valido para el tipo " + tipo, nodo);
        }
    }



    private Valor leerAcceso(AccessNode acceso, EntornoEjecucion entorno) {
        if (acceso.base() == null) {
            Valor base = entorno.obtener(acceso.name());
            if (base == null) {
                throw error("'" + acceso.name() + "' no tiene un valor asignado", acceso);
            }
            return acceso.index() != null ? indexar(base, acceso, entorno) : base;
        }
        Valor base = leerAcceso((AccessNode) acceso.base(), entorno);
        Valor campo = base.comoEstructura().campos().get(acceso.name());
        if (campo == null) {
            throw error("'" + acceso.base().label() + "' no tiene el atributo '" + acceso.name() + "'", acceso);
        }
        return acceso.index() != null ? indexar(campo, acceso, entorno) : campo;
    }

    private Valor indexar(Valor arregloValor, AccessNode acceso, EntornoEjecucion entorno) {
        ArregloRuntime arreglo = arregloValor.comoArreglo();
        int indice = (int) evaluar(acceso.index(), entorno, null).comoNumero();
        verificarIndice(indice, arreglo.tamano(), acceso);
        return arreglo.elementos()[indice];
    }

    private void asignarEnAcceso(AccessNode acceso, EntornoEjecucion entorno, Valor valor) {
        if (acceso.base() == null) {
            if (acceso.index() != null) {
                asignarEnArreglo(entorno.obtener(acceso.name()), acceso, entorno, valor);
                return;
            }
            entorno.asignar(acceso.name(), valor);
            return;
        }
        Valor base = leerAcceso((AccessNode) acceso.base(), entorno);
        Map<String, Valor> campos = base.comoEstructura().campos();
        if (acceso.index() != null) {
            asignarEnArreglo(campos.get(acceso.name()), acceso, entorno, valor);
            return;
        }
        campos.put(acceso.name(), valor);
    }

    private void asignarEnArreglo(Valor arregloValor, AccessNode acceso, EntornoEjecucion entorno, Valor valor) {
        ArregloRuntime arreglo = arregloValor.comoArreglo();
        int indice = (int) evaluar(acceso.index(), entorno, null).comoNumero();
        verificarIndice(indice, arreglo.tamano(), acceso);
        arreglo.elementos()[indice] = ajustarTipo(valor, arreglo.tipoElemento());
    }

    private void verificarIndice(int indice, int tamano, AstNode nodo) {
        if (indice < 0 || indice >= tamano) {
            throw error("indice fuera de rango: " + indice + " (el arreglo tiene tamano " + tamano + ")", nodo);
        }
    }



    private Valor evaluar(AstNode nodo, EntornoEjecucion entorno, String tipoEsperado) {
        return switch (nodo) {
            case null -> null;
            case IntegerLiteralNode lit -> Valor.numero(lit.value());
            case DecimalLiteralNode lit -> Valor.decimal(lit.value());
            case StringLiteralNode lit -> Valor.texto(desescapar(lit.value()));
            case CharLiteralNode lit -> Valor.caracter(desescapar(lit.value()).charAt(0));
            case BooleanLiteralNode lit -> Valor.booleano(lit.value());
            case AccessNode acceso -> leerAcceso(acceso, entorno);
            case FunctionCallNode llamada -> invocar(llamada, entorno);
            case BinaryExpressionNode binaria -> evaluarBinaria(binaria, entorno);
            case UnaryExpressionNode unaria -> evaluarUnaria(unaria, entorno);
            case PostfixExpressionNode postfix -> evaluarPostfix(postfix, entorno);
            case StructInitializerNode structInit -> evaluarStructInit(structInit, entorno, tipoEsperado);
            default -> null;
        };
    }

    private static String desescapar(String crudo) {
        String interior = crudo.substring(1, crudo.length() - 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < interior.length(); i++) {
            char c = interior.charAt(i);
            if (c == '\\' && i + 1 < interior.length()) {
                char siguiente = interior.charAt(++i);
                switch (siguiente) {
                    case 'n' -> sb.append('\n');
                    case 't' -> sb.append('\t');
                    case 'r' -> sb.append('\r');
                    default -> sb.append(siguiente);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private Valor invocar(FunctionCallNode llamada, EntornoEjecucion entorno) {
        FunctionDeclarationNode funcion = funciones.get(llamada.name());
        if (funcion == null) {
            throw error("la funcion '" + llamada.name() + "' no existe", llamada);
        }
        EntornoEjecucion entornoFuncion = new EntornoEjecucion(global);
        List<ParameterNode> parametros = funcion.parameters();
        for (int i = 0; i < parametros.size() && i < llamada.arguments().size(); i++) {
            String tipoParametro = parametros.get(i).type();
            Valor argumento = evaluar(llamada.arguments().get(i), entorno, tipoParametro);
            entornoFuncion.declarar(parametros.get(i).name(), ajustarTipo(argumento, tipoParametro));
        }
        for (VariableDeclarationNode local : funcion.localVariables()) {
            ejecutarDeclaracion(local, entornoFuncion);
        }
        String tipoRetornoAnterior = tipoRetornoActual;
        tipoRetornoActual = funcion.returnType();
        try {
            for (AstNode statement : funcion.body()) {
                if (statement != null) {
                    ejecutarInstruccion(statement, entornoFuncion);
                }
            }
            return null;
        } catch (SenalRetorno retorno) {
            return ajustarTipo(retorno.valor(), funcion.returnType());
        } finally {
            tipoRetornoActual = tipoRetornoAnterior;
        }
    }

    private Valor evaluarStructInit(StructInitializerNode nodo, EntornoEjecucion entorno, String tipoEsperado) {
        String tipo = nodo.typeName() != null ? nodo.typeName() : tipoEsperado;
        StructDefinitionNode definicion = estructuras.get(tipo);
        EstructuraRuntime instancia = new EstructuraRuntime(tipo);
        Map<String, String> tiposAtributos = new HashMap<>();
        if (definicion != null) {
            for (StructAttributeNode atributo : definicion.attributes()) {
                tiposAtributos.put(atributo.name(), atributo.type());
                instancia.campos().put(atributo.name(), valorPorDefecto(atributo.type()));
            }
        }
        for (StructFieldInitNode campo : nodo.fields()) {
            if (campo.elementType() != null) {
                Valor[] elementos = new Valor[campo.arraySize()];
                Arrays.fill(elementos, valorPorDefecto(campo.elementType()));
                instancia.campos().put(campo.name(), Valor.arreglo(new ArregloRuntime(elementos, campo.elementType())));
            } else {
                String tipoCampo = tiposAtributos.get(campo.name());
                instancia.campos().put(campo.name(), ajustarTipo(evaluar(campo.value(), entorno, tipoCampo), tipoCampo));
            }
        }
        return Valor.estructura(instancia);
    }

    private Valor evaluarBinaria(BinaryExpressionNode nodo, EntornoEjecucion entorno) {
        String operador = nodo.operator();
        if (operador.equals("&&") || operador.equals("||")) {
            boolean izquierda = evaluar(nodo.left(), entorno, null).comoBooleano();
            if (operador.equals("&&") && !izquierda) {
                return Valor.booleano(false);
            }
            if (operador.equals("||") && izquierda) {
                return Valor.booleano(true);
            }
            return Valor.booleano(evaluar(nodo.right(), entorno, null).comoBooleano());
        }
        Valor izq = evaluar(nodo.left(), entorno, null);
        Valor der = evaluar(nodo.right(), entorno, null);
        return switch (operador) {
            case "+" -> sumar(izq, der);
            case "-" -> aritmetica(izq, der, false);
            case "*" -> aritmetica(izq, der, true);
            case "/" -> dividir(izq, der, nodo);
            case "==" -> Valor.booleano(comparar(izq, der) == 0);
            case "!=" -> Valor.booleano(comparar(izq, der) != 0);
            case "<" -> Valor.booleano(comparar(izq, der) < 0);
            case ">" -> Valor.booleano(comparar(izq, der) > 0);
            case "<=" -> Valor.booleano(comparar(izq, der) <= 0);
            case ">=" -> Valor.booleano(comparar(izq, der) >= 0);
            default -> throw error("operador desconocido '" + operador + "'", nodo);
        };
    }

    private Valor sumar(Valor a, Valor b) {
        if (a.tipo().equals(TypeSystem.TEXTUM) || b.tipo().equals(TypeSystem.TEXTUM)) {
            return Valor.texto(a.representar() + b.representar());
        }
        if (esDecimal(a) || esDecimal(b)) {
            return Valor.decimal(numerico(a) + numerico(b));
        }
        return Valor.numero((long) (numerico(a) + numerico(b)));
    }

    private Valor aritmetica(Valor a, Valor b, boolean multiplicar) {
        if (esDecimal(a) || esDecimal(b)) {
            return Valor.decimal(multiplicar ? numerico(a) * numerico(b) : numerico(a) - numerico(b));
        }
        long x = (long) numerico(a);
        long y = (long) numerico(b);
        return Valor.numero(multiplicar ? x * y : x - y);
    }

    private Valor dividir(Valor a, Valor b, AstNode nodo) {
        if (esDecimal(a) || esDecimal(b)) {
            double divisor = numerico(b);
            if (divisor == 0) {
                throw error("division entre cero", nodo);
            }
            return Valor.decimal(numerico(a) / divisor);
        }
        long divisor = (long) numerico(b);
        if (divisor == 0) {
            throw error("division entre cero", nodo);
        }
        return Valor.numero((long) numerico(a) / divisor);
    }

    private int comparar(Valor a, Valor b) {
        if (a.tipo().equals(TypeSystem.TEXTUM) || b.tipo().equals(TypeSystem.TEXTUM)) {
            return a.representar().compareTo(b.representar());
        }
        return Double.compare(numerico(a), numerico(b));
    }

    private boolean esDecimal(Valor v) {
        return v.tipo().equals(TypeSystem.DECIMALIS);
    }

    private double numerico(Valor v) {
        return switch (v.tipo()) {
            case TypeSystem.NUMERUS -> v.comoNumero();
            case TypeSystem.DECIMALIS -> v.comoDecimal();
            case TypeSystem.LITTERA -> v.comoCaracter();
            case TypeSystem.BOOLEANO -> v.comoBooleano() ? 1 : 0;
            default -> 0;
        };
    }

    private Valor evaluarUnaria(UnaryExpressionNode nodo, EntornoEjecucion entorno) {
        return switch (nodo.operator()) {
            case "non" -> Valor.booleano(!evaluar(nodo.operand(), entorno, null).comoBooleano());
            case "-" -> negar(evaluar(nodo.operand(), entorno, null));
            case "+" -> evaluar(nodo.operand(), entorno, null);
            case "++", "--" -> incrementar((AccessNode) nodo.operand(), entorno, nodo.operator(), true);
            default -> throw error("operador unario desconocido '" + nodo.operator() + "'", nodo);
        };
    }

    private Valor negar(Valor v) {
        return esDecimal(v) ? Valor.decimal(-v.comoDecimal()) : Valor.numero(-v.comoNumero());
    }

    private Valor evaluarPostfix(PostfixExpressionNode nodo, EntornoEjecucion entorno) {
        return incrementar((AccessNode) nodo.operand(), entorno, nodo.operator(), false);
    }


    private Valor incrementar(AccessNode acceso, EntornoEjecucion entorno, String operador, boolean devuelvePrefijo) {
        Valor actual = leerAcceso(acceso, entorno);
        Valor nuevo = Valor.numero(actual.comoNumero() + (operador.equals("++") ? 1 : -1));
        asignarEnAcceso(acceso, entorno, nuevo);
        return devuelvePrefijo ? nuevo : actual;
    }

    private Valor valorPorDefecto(String tipoCrudo) {
        String tipo = typeSystem.normalize(tipoCrudo);
        return switch (tipo) {
            case TypeSystem.NUMERUS -> Valor.numero(0);
            case TypeSystem.DECIMALIS -> Valor.decimal(0.0);
            case TypeSystem.TEXTUM -> Valor.texto("");
            case TypeSystem.LITTERA -> Valor.caracter(' ');
            case TypeSystem.BOOLEANO -> Valor.booleano(false);
            default -> valorPorDefectoEstructura(tipo);
        };
    }

    private Valor valorPorDefectoEstructura(String tipo) {
        StructDefinitionNode definicion = estructuras.get(tipo);
        if (definicion == null) {
            return Valor.texto("");
        }
        EstructuraRuntime instancia = new EstructuraRuntime(tipo);
        for (StructAttributeNode atributo : definicion.attributes()) {
            instancia.campos().put(atributo.name(),
                    atributo.isArray() ? Valor.arreglo(new ArregloRuntime(new Valor[0], atributo.type())) : valorPorDefecto(atributo.type()));
        }
        return Valor.estructura(instancia);
    }


    private Valor ajustarTipo(Valor valor, String tipoDestinoCrudo) {
        if (valor == null || tipoDestinoCrudo == null) {
            return valor;
        }
        String tipoDestino = typeSystem.normalize(tipoDestinoCrudo);
        if (tipoDestino.equals(valor.tipo())) {
            return valor;
        }
        if (tipoDestino.equals(TypeSystem.DECIMALIS) && (valor.tipo().equals(TypeSystem.NUMERUS) || valor.tipo().equals(TypeSystem.LITTERA))) {
            return Valor.decimal(numerico(valor));
        }
        if (tipoDestino.equals(TypeSystem.NUMERUS) && valor.tipo().equals(TypeSystem.LITTERA)) {
            return Valor.numero((long) numerico(valor));
        }
        if (tipoDestino.equals(TypeSystem.TEXTUM) && !valor.tipo().equals(TypeSystem.TEXTUM)) {
            return Valor.texto(valor.representar());
        }
        return valor;
    }

    private ErrorEjecucion error(String mensaje, AstNode nodo) {
        return new ErrorEjecucion(mensaje, nodo.line(), nodo.column());
    }
}
