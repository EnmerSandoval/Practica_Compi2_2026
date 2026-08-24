package practica.compi2.visitors;

import practica.compi2.GrammarParser;
import practica.compi2.tree.ast.*;

import java.util.ArrayList;
import java.util.List;

public class DeclarationBuilder {

    private final AstBuilderVisitor visitor;
    private final Posiciones posiciones = new Posiciones();
    private final TipoResolver tipoResolver = new TipoResolver();

    DeclarationBuilder(AstBuilderVisitor visitor) {
        this.visitor = visitor;
    }

    AstNode visitDeclaracionVariable(GrammarParser.DeclaracionVariableContext ctx) {
        int ln = posiciones.linea(ctx);
        int col = posiciones.columna(ctx);
        String name = ctx.identificador().getText();
        if (ctx.SERIES() != null) {
            int size = Integer.parseInt(ctx.NUMERO().getText());
            String type = tipoResolver.nombreDe(ctx.tipo());
            AstNode initializer = null;
            if (ctx.inicializadorArray() != null) {
                List<AstNode> elements = new ArrayList<>();
                for (var expr : ctx.inicializadorArray().expresion()) {
                    elements.add(visitor.visit(expr));
                }
                initializer = new ArrayInitializerNode(ln, col, elements);
            }
            return new VariableDeclarationNode(ln, col, name, type, true, size, initializer);
        }
        String type = tipoResolver.nombreDe(ctx.tipoVariable());
        AstNode initializer = ctx.inicializador() != null ? visitor.visit(ctx.inicializador().expresion()) : null;
        if (initializer == null && "bool".equals(type)) {
            // "bool" (a diferencia de verum/falsus, que ya cargan su
            // propio valor) no trae un valor implicito; sin inicializador
            // explicito se asume verdadero.
            initializer = new BooleanLiteralNode(ln, col, true);
        }
        return new VariableDeclarationNode(ln, col, name, type, false, null, initializer);
    }

    AstNode visitDefinicionEstructura(GrammarParser.DefinicionEstructuraContext ctx) {
        String name = ctx.identificador().getText();
        List<StructAttributeNode> attributes = new ArrayList<>();
        for (var attrCtx : ctx.atributoEstructura()) {
            attributes.add((StructAttributeNode) visitAtributoEstructura(attrCtx));
        }
        return new StructDefinitionNode(posiciones.linea(ctx), posiciones.columna(ctx), name, attributes);
    }

    AstNode visitAtributoEstructura(GrammarParser.AtributoEstructuraContext ctx) {
        boolean isArray = ctx.SERIES() != null;
        String name = ctx.identificador().getText();
        String type = tipoResolver.nombreDe(ctx.tipo());
        return new StructAttributeNode(posiciones.linea(ctx), posiciones.columna(ctx), name, type, isArray);
    }

    AstNode visitInicializadorEstructura(GrammarParser.InicializadorEstructuraContext ctx) {
        String typeName = ctx.identificador().getText();
        return new StructInitializerNode(posiciones.linea(ctx), posiciones.columna(ctx), typeName, camposDe(ctx.asignacionAtributo()));
    }

    AstNode visitAsignacionAtributo(GrammarParser.AsignacionAtributoContext ctx) {
        String name = ctx.identificador().getText();
        if (ctx.CORCHETE_IZQUIERDO() != null) {
            String elementType = tipoResolver.nombreDe(ctx.tipo());
            int size = Integer.parseInt(ctx.NUMERO().getText());
            return new StructFieldInitNode(posiciones.linea(ctx), posiciones.columna(ctx), name, elementType, size, null);
        }
        AstNode value = visitor.visit(ctx.expresion());
        return new StructFieldInitNode(posiciones.linea(ctx), posiciones.columna(ctx), name, null, null, value);
    }

    private List<StructFieldInitNode> camposDe(List<GrammarParser.AsignacionAtributoContext> ctxs) {
        List<StructFieldInitNode> fields = new ArrayList<>();
        for (var fieldCtx : ctxs) {
            fields.add((StructFieldInitNode) visitAsignacionAtributo(fieldCtx));
        }
        return fields;
    }
}
