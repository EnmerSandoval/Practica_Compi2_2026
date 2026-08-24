package practica.compi2.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import practica.compi2.GrammarParser;
import practica.compi2.tree.ast.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionBuilder {


    private final AstBuilderVisitor visitor;
    private final Posiciones posiciones = new Posiciones();

    ExpressionBuilder(AstBuilderVisitor visitor) {
        this.visitor = visitor;
    }

    AstNode visitExpresion(GrammarParser.ExpresionContext ctx) {
        return visitor.visit(ctx.expresionLogicaOr());
    }

    AstNode visitExpresionLogicaOr(GrammarParser.ExpresionLogicaOrContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionLogicaAnd(GrammarParser.ExpresionLogicaAndContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionIgualdad(GrammarParser.ExpresionIgualdadContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionRelacional(GrammarParser.ExpresionRelacionalContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionAditiva(GrammarParser.ExpresionAditivaContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionMultiplicativa(GrammarParser.ExpresionMultiplicativaContext ctx) {
        return buildLeftAssociative(ctx);
    }

    AstNode visitExpresionUnaria(GrammarParser.ExpresionUnariaContext ctx) {
        if (ctx.expresionPostfija() != null) {
            return visitor.visit(ctx.expresionPostfija());
        }
        if (ctx.getChildCount() == 0) {
            return null;
        }
        String operator = ctx.getChild(0).getText();
        AstNode operand = visitor.visit(ctx.expresionUnaria());
        return new UnaryExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), operator, operand);
    }

    AstNode visitExpresionPostfija(GrammarParser.ExpresionPostfijaContext ctx) {
        AstNode primary = visitor.visit(ctx.expresionPrimaria());
        if (ctx.MAS_MAS() != null) {
            return new PostfixExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), primary, "++");
        }
        if (ctx.MENOS_MENOS() != null) {
            return new PostfixExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), primary, "--");
        }
        return primary;
    }

    AstNode visitExpresionPrimaria(GrammarParser.ExpresionPrimariaContext ctx) {
        int ln = posiciones.linea(ctx);
        int col = posiciones.columna(ctx);
        if (ctx.NUMERO() != null) {
            return new IntegerLiteralNode(ln, col, Long.parseLong(ctx.NUMERO().getText()));
        }
        if (ctx.DECIMAL() != null) {
            return new DecimalLiteralNode(ln, col, Double.parseDouble(ctx.DECIMAL().getText()));
        }
        if (ctx.CADENA() != null) {
            return new StringLiteralNode(ln, col, ctx.CADENA().getText());
        }
        if (ctx.CARACTER() != null) {
            return new CharLiteralNode(ln, col, ctx.CARACTER().getText());
        }
        if (ctx.VERUM() != null) {
            return new BooleanLiteralNode(ln, col, true);
        }
        if (ctx.FALSUS() != null) {
            return new BooleanLiteralNode(ln, col, false);
        }
        if (ctx.llamadaFuncion() != null) {
            return visitor.visit(ctx.llamadaFuncion());
        }
        if (ctx.inicializadorEstructura() != null) {
            return visitor.visit(ctx.inicializadorEstructura());
        }
        if (ctx.inicializadorEstructuraAnonimo() != null) {
            List<StructFieldInitNode> fields = new ArrayList<>();
            for (var fieldCtx : ctx.inicializadorEstructuraAnonimo().asignacionAtributo()) {
                fields.add((StructFieldInitNode) visitor.visit(fieldCtx));
            }
            return new StructInitializerNode(ln, col, null, fields);
        }
        if (ctx.PARENTESIS_IZQUIERDO() != null) {
            return visitor.visit(ctx.expresion());
        }
        AstNode base = null;
        String name = ctx.identificador().getText();
        AstNode index = null;
        for (var sufijo : ctx.accesoSufijo()) {
            if (sufijo.PUNTO() != null) {
                base = new AccessNode(ln, col, base, name, index);
                name = sufijo.identificador().getText();
                index = null;
            } else {
                index = visitor.visit(sufijo.expresion());
            }
        }
        return new AccessNode(ln, col, base, name, index);
    }

    private AstNode buildLeftAssociative(ParserRuleContext ctx) {
        if (ctx.getChildCount() == 0) {
            return null;
        }
        AstNode result = visitor.visit(ctx.getChild(0));
        int childCount = ctx.getChildCount();
        for (int i = 1; i < childCount; i += 2) {
            String operator = ctx.getChild(i).getText();
            AstNode right = visitor.visit(ctx.getChild(i + 1));
            result = new BinaryExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), operator, result, right);
        }
        return result;
    }
}
