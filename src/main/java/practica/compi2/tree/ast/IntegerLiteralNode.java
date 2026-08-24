package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record IntegerLiteralNode(int line, int column, long value) implements AstNode {

    @Override
    public String label() {
        return "numerus " + value;
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
