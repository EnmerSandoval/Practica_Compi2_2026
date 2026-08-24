package practica.compi2.visitors;

import practica.compi2.GrammarBaseVisitor;
import practica.compi2.GrammarParser;
import practica.compi2.tree.ast.BlockNode;
import practica.compi2.tree.ast.ProgramNode;

import java.util.ArrayList;
import java.util.List;

public class AstBuilderVisitor extends GrammarBaseVisitor<AstNode> {
    private final DeclarationBuilder declarationBuilder = new DeclarationBuilder(this);
    private final FunctionBuilder functionBuilder = new FunctionBuilder(this);
    private final StatementBuilder statementBuilder = new StatementBuilder(this);
    private final ExpressionBuilder expressionBuilder = new ExpressionBuilder(this);
    private final Posiciones posiciones = new Posiciones();

    @Override
    public AstNode visit(org.antlr.v4.runtime.tree.ParseTree tree) {
        return tree == null ? null : super.visit(tree);
    }

    @Override
    public AstNode visitProgram(GrammarParser.ProgramContext ctx) {
        List<AstNode> globals = new ArrayList<>();
        if (ctx.declaracionVarablesOpcionales() != null) {
            for (var hijo : ctx.declaracionVarablesOpcionales().children) {
                if (hijo instanceof GrammarParser.DeclaracionVariableContext || hijo instanceof GrammarParser.DefinicionEstructuraContext) {
                    globals.add(visit(hijo));
                }
            }
        }
        List<AstNode> functions = new ArrayList<>();
        if (ctx.declaracionFuncionesOpcionales() != null) {
            for (var funcCtx : ctx.declaracionFuncionesOpcionales().definicionFuncion()) {
                functions.add(visit(funcCtx));
            }
        }
        BlockNode mainBlock = (BlockNode) visit(ctx.declaracionPrgrama().bloque());
        return new ProgramNode(posiciones.linea(ctx), posiciones.columna(ctx), globals, functions, mainBlock);
    }

    @Override
    public AstNode visitDeclaracionVariable(GrammarParser.DeclaracionVariableContext ctx) {
        return declarationBuilder.visitDeclaracionVariable(ctx);
    }

    @Override
    public AstNode visitDefinicionEstructura(GrammarParser.DefinicionEstructuraContext ctx) {
        return declarationBuilder.visitDefinicionEstructura(ctx);
    }

    @Override
    public AstNode visitAtributoEstructura(GrammarParser.AtributoEstructuraContext ctx) {
        return declarationBuilder.visitAtributoEstructura(ctx);
    }

    @Override
    public AstNode visitInicializadorEstructura(GrammarParser.InicializadorEstructuraContext ctx) {
        return declarationBuilder.visitInicializadorEstructura(ctx);
    }

    @Override
    public AstNode visitAsignacionAtributo(GrammarParser.AsignacionAtributoContext ctx) {
        return declarationBuilder.visitAsignacionAtributo(ctx);
    }

    @Override
    public AstNode visitDefinicionFuncion(GrammarParser.DefinicionFuncionContext ctx) {
        return functionBuilder.visitDefinicionFuncion(ctx);
    }

    @Override
    public AstNode visitFuncionSinRetorno(GrammarParser.FuncionSinRetornoContext ctx) {
        return functionBuilder.visitFuncionSinRetorno(ctx);
    }

    @Override
    public AstNode visitFuncionConRetorno(GrammarParser.FuncionConRetornoContext ctx) {
        return functionBuilder.visitFuncionConRetorno(ctx);
    }

    @Override
    public AstNode visitBloque(GrammarParser.BloqueContext ctx) {
        return statementBuilder.visitBloque(ctx);
    }

    @Override
    public AstNode visitInstruccion(GrammarParser.InstruccionContext ctx) {
        return statementBuilder.visitInstruccion(ctx);
    }

    @Override
    public AstNode visitAsignacion(GrammarParser.AsignacionContext ctx) {
        return statementBuilder.visitAsignacion(ctx);
    }

    @Override
    public AstNode visitIncrementoInstruccion(GrammarParser.IncrementoInstruccionContext ctx) {
        return statementBuilder.visitIncrementoInstruccion(ctx);
    }

    @Override
    public AstNode visitAccesoVariable(GrammarParser.AccesoVariableContext ctx) {
        return statementBuilder.visitAccesoVariable(ctx);
    }

    @Override
    public AstNode visitCondicional(GrammarParser.CondicionalContext ctx) {
        return statementBuilder.visitCondicional(ctx);
    }

    @Override
    public AstNode visitRamas(GrammarParser.RamasContext ctx) {
        return statementBuilder.visitRamas(ctx);
    }

    @Override
    public AstNode visitCicloMientras(GrammarParser.CicloMientrasContext ctx) {
        return statementBuilder.visitCicloMientras(ctx);
    }

    @Override
    public AstNode visitCicloHacerMientras(GrammarParser.CicloHacerMientrasContext ctx) {
        return statementBuilder.visitCicloHacerMientras(ctx);
    }

    @Override
    public AstNode visitCicloIterador(GrammarParser.CicloIteradorContext ctx) {
        return statementBuilder.visitCicloIterador(ctx);
    }

    @Override
    public AstNode visitIncremento(GrammarParser.IncrementoContext ctx) {
        return statementBuilder.visitIncremento(ctx);
    }

    @Override
    public AstNode visitControlFlujo(GrammarParser.ControlFlujoContext ctx) {
        return statementBuilder.visitControlFlujo(ctx);
    }

    @Override
    public AstNode visitLectura(GrammarParser.LecturaContext ctx) {
        return statementBuilder.visitLectura(ctx);
    }

    @Override
    public AstNode visitEscritura(GrammarParser.EscrituraContext ctx) {
        return statementBuilder.visitEscritura(ctx);
    }

    @Override
    public AstNode visitRetorno(GrammarParser.RetornoContext ctx) {
        return statementBuilder.visitRetorno(ctx);
    }

    @Override
    public AstNode visitLlamadaFuncion(GrammarParser.LlamadaFuncionContext ctx) {
        return statementBuilder.visitLlamadaFuncion(ctx);
    }

    @Override
    public AstNode visitExpresion(GrammarParser.ExpresionContext ctx) {
        return expressionBuilder.visitExpresion(ctx);
    }

    @Override
    public AstNode visitExpresionLogicaOr(GrammarParser.ExpresionLogicaOrContext ctx) {
        return expressionBuilder.visitExpresionLogicaOr(ctx);
    }

    @Override
    public AstNode visitExpresionLogicaAnd(GrammarParser.ExpresionLogicaAndContext ctx) {
        return expressionBuilder.visitExpresionLogicaAnd(ctx);
    }

    @Override
    public AstNode visitExpresionIgualdad(GrammarParser.ExpresionIgualdadContext ctx) {
        return expressionBuilder.visitExpresionIgualdad(ctx);
    }

    @Override
    public AstNode visitExpresionRelacional(GrammarParser.ExpresionRelacionalContext ctx) {
        return expressionBuilder.visitExpresionRelacional(ctx);
    }

    @Override
    public AstNode visitExpresionAditiva(GrammarParser.ExpresionAditivaContext ctx) {
        return expressionBuilder.visitExpresionAditiva(ctx);
    }

    @Override
    public AstNode visitExpresionMultiplicativa(GrammarParser.ExpresionMultiplicativaContext ctx) {
        return expressionBuilder.visitExpresionMultiplicativa(ctx);
    }

    @Override
    public AstNode visitExpresionUnaria(GrammarParser.ExpresionUnariaContext ctx) {
        return expressionBuilder.visitExpresionUnaria(ctx);
    }

    @Override
    public AstNode visitExpresionPostfija(GrammarParser.ExpresionPostfijaContext ctx) {
        return expressionBuilder.visitExpresionPostfija(ctx);
    }

    @Override
    public AstNode visitExpresionPrimaria(GrammarParser.ExpresionPrimariaContext ctx) {
        return expressionBuilder.visitExpresionPrimaria(ctx);
    }
}
