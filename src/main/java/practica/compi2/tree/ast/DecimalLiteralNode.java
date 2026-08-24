package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record DecimalLiteralNode(int line, int column, double value) implements AstNode {

    @Override
    public String label() {
        return "decimalis " + value;
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
