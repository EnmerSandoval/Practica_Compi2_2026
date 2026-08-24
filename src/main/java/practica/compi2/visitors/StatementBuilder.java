package practica.compi2.visitors;

import practica.compi2.GrammarParser;
import practica.compi2.tree.ast.*;

import java.util.ArrayList;
import java.util.List;

public class StatementBuilder {

    private final AstBuilderVisitor visitor;
    private final Posiciones posiciones = new Posiciones();

    StatementBuilder(AstBuilderVisitor visitor) {
        this.visitor = visitor;
    }

    AstNode visitBloque(GrammarParser.BloqueContext ctx) {
        List<AstNode> statements = new ArrayList<>();
        for (var instruccionCtx : ctx.instruccion()) {
            statements.add(visitor.visit(instruccionCtx));
        }
        return new BlockNode(posiciones.linea(ctx), posiciones.columna(ctx), statements);
    }

    AstNode visitInstruccion(GrammarParser.InstruccionContext ctx) {
        if (ctx.declaracionVariable() != null) {
            return visitor.visit(ctx.declaracionVariable());
        }
        if (ctx.definicionEstructura() != null) {
            return visitor.visit(ctx.definicionEstructura());
        }
        if (ctx.asignacion() != null) {
            return visitor.visit(ctx.asignacion());
        }
        if (ctx.incrementoInstruccion() != null) {
            return visitor.visit(ctx.incrementoInstruccion());
        }
        if (ctx.condicional() != null) {
            return visitor.visit(ctx.condicional());
        }
        if (ctx.cicloMientras() != null) {
            return visitor.visit(ctx.cicloMientras());
        }
        if (ctx.cicloHacerMientras() != null) {
            return visitor.visit(ctx.cicloHacerMientras());
        }
        if (ctx.cicloIterador() != null) {
            return visitor.visit(ctx.cicloIterador());
        }
        if (ctx.llamadaFuncion() != null) {
            return new ExpressionStatementNode(posiciones.linea(ctx), posiciones.columna(ctx), visitor.visit(ctx.llamadaFuncion()));
        }
        if (ctx.lectura() != null) {
            return visitor.visit(ctx.lectura());
        }
        if (ctx.escritura() != null) {
            return visitor.visit(ctx.escritura());
        }
        if (ctx.retorno() != null) {
            return visitor.visit(ctx.retorno());
        }
        return visitor.visit(ctx.controlFlujo());
    }

    AstNode visitAsignacion(GrammarParser.AsignacionContext ctx) {
        AstNode target = visitor.visit(ctx.accesoVariable());
        AstNode value = visitor.visit(ctx.expresion());
        return new AssignmentNode(posiciones.linea(ctx), posiciones.columna(ctx), target, value);
    }

    AstNode visitAccesoVariable(GrammarParser.AccesoVariableContext ctx) {
        int ln = posiciones.linea(ctx);
        int col = posiciones.columna(ctx);
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

    AstNode visitIncrementoInstruccion(GrammarParser.IncrementoInstruccionContext ctx) {
        AstNode target = visitor.visit(ctx.accesoVariable());
        String operator = ctx.MAS_MAS() != null ? "++" : "--";
        AstNode postfix = new PostfixExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), target, operator);
        return new ExpressionStatementNode(posiciones.linea(ctx), posiciones.columna(ctx), postfix);
    }

    AstNode visitCondicional(GrammarParser.CondicionalContext ctx) {
        AstNode condition = visitor.visit(ctx.expresion());
        BlockNode thenBlock = (BlockNode) visitor.visit(ctx.bloque());
        AstNode elseBranch = ctx.ramas() != null ? visitor.visit(ctx.ramas()) : null;
        return new IfNode(posiciones.linea(ctx), posiciones.columna(ctx), condition, thenBlock, elseBranch);
    }

    AstNode visitRamas(GrammarParser.RamasContext ctx) {
        if (ctx.expresion() != null) {
            AstNode condition = visitor.visit(ctx.expresion());
            BlockNode block = (BlockNode) visitor.visit(ctx.bloque());
            AstNode next = ctx.ramas() != null ? visitor.visit(ctx.ramas()) : null;
            return new IfNode(posiciones.linea(ctx), posiciones.columna(ctx), condition, block, next);
        }
        return visitor.visit(ctx.bloque());
    }

    AstNode visitCicloMientras(GrammarParser.CicloMientrasContext ctx) {
        AstNode condition = visitor.visit(ctx.expresion());
        BlockNode body = (BlockNode) visitor.visit(ctx.bloque());
        return new WhileNode(posiciones.linea(ctx), posiciones.columna(ctx), condition, body);
    }

    AstNode visitCicloHacerMientras(GrammarParser.CicloHacerMientrasContext ctx) {
        BlockNode body = (BlockNode) visitor.visit(ctx.bloque());
        AstNode condition = visitor.visit(ctx.expresion());
        return new DoWhileNode(posiciones.linea(ctx), posiciones.columna(ctx), body, condition);
    }

    AstNode visitCicloIterador(GrammarParser.CicloIteradorContext ctx) {
        int ln = posiciones.linea(ctx);
        int col = posiciones.columna(ctx);
        String name = ctx.identificador().getText();
        AstNode startValue = visitor.visit(ctx.expresion(0));
        VariableDeclarationNode init = new VariableDeclarationNode(ln, col, name, "numerus", false, null, startValue);
        AstNode condition = visitor.visit(ctx.expresion(1));
        AstNode increment = visitor.visit(ctx.incremento());
        BlockNode body = (BlockNode) visitor.visit(ctx.bloque());
        return new ForNode(ln, col, init, condition, increment, body);
    }

    AstNode visitIncremento(GrammarParser.IncrementoContext ctx) {
        if (ctx.accesoVariable() != null) {
            AstNode target = visitor.visit(ctx.accesoVariable());
            AstNode value = visitor.visit(ctx.expresion());
            return new AssignmentNode(posiciones.linea(ctx), posiciones.columna(ctx), target, value);
        }
        AstNode target = new AccessNode(posiciones.linea(ctx), posiciones.columna(ctx), null, ctx.identificador().getText(), null);
        String operator = ctx.MAS_MAS() != null ? "++" : "--";
        return new PostfixExpressionNode(posiciones.linea(ctx), posiciones.columna(ctx), target, operator);
    }

    AstNode visitControlFlujo(GrammarParser.ControlFlujoContext ctx) {
        if (ctx.PERGE() != null) {
            return new ContinueNode(posiciones.linea(ctx), posiciones.columna(ctx));
        }
        return new BreakNode(posiciones.linea(ctx), posiciones.columna(ctx));
    }

    AstNode visitLectura(GrammarParser.LecturaContext ctx) {
        AstNode target = ctx.accesoVariable() != null ? visitor.visit(ctx.accesoVariable()) : null;
        return new ReadNode(posiciones.linea(ctx), posiciones.columna(ctx), target);
    }

    AstNode visitEscritura(GrammarParser.EscrituraContext ctx) {
        List<AstNode> expressions = new ArrayList<>();
        for (var expr : ctx.expresion()) {
            expressions.add(visitor.visit(expr));
        }
        return new WriteNode(posiciones.linea(ctx), posiciones.columna(ctx), expressions);
    }

    AstNode visitRetorno(GrammarParser.RetornoContext ctx) {
        AstNode value = ctx.expresion() != null ? visitor.visit(ctx.expresion()) : null;
        return new ReturnNode(posiciones.linea(ctx), posiciones.columna(ctx), value);
    }

    AstNode visitLlamadaFuncion(GrammarParser.LlamadaFuncionContext ctx) {
        String name = ctx.identificador().getText();
        List<AstNode> arguments = new ArrayList<>();
        for (var expr : ctx.expresion()) {
            arguments.add(visitor.visit(expr));
        }
        return new FunctionCallNode(posiciones.linea(ctx), posiciones.columna(ctx), name, arguments);
    }
}
