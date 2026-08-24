package practica.compi2.visitors;

import org.antlr.v4.runtime.ParserRuleContext;

public final class Posiciones {

    int linea(ParserRuleContext ctx) {
        return ctx.getStart().getLine();
    }

    int columna(ParserRuleContext ctx) {
        return ctx.getStart().getCharPositionInLine();
    }
}
