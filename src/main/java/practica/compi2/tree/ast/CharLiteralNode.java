package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record CharLiteralNode(int line, int column, String value) implements AstNode {

    @Override
    public String label() {
        return "littera " + value;
    }

    @Override
    public List<AstNode> children() {
        return List.of();
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(value);
    }
}
