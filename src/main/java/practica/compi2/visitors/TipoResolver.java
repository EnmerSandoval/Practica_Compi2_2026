package practica.compi2.visitors;

import practica.compi2.GrammarParser;

public final class TipoResolver {

    String nombreDe(GrammarParser.TipoVariableContext ctx) {
        if (ctx.NUMERUS() != null) {
            return "numerus";
        }
        if (ctx.TEXTUM() != null) {
            return "textum";
        }
        if (ctx.DECIMALIS() != null) {
            return "decimalis";
        }
        if (ctx.LITTERA() != null) {
            return "littera";
        }
        if (ctx.VERUM() != null) {
            return "verum";
        }
        if (ctx.FALSUS() != null) {
            return "falsus";
        }
        return nombreDe(ctx.tipo());
    }

    String nombreDe(GrammarParser.TipoContext ctx) {
        if (ctx.NUMERUS() != null) {
            return "numerus";
        }
        if (ctx.TEXTUM() != null) {
            return "textum";
        }
        if (ctx.DECIMALIS() != null) {
            return "decimalis";
        }
        if (ctx.LITTERA() != null) {
            return "littera";
        }
        if (ctx.BOOL() != null) {
            return "bool";
        }
        if (ctx.booleano() != null) {
            return ctx.booleano().VERUM() != null ? "verum" : "falsus";
        }
        return ctx.identificador().getText();
    }
}
