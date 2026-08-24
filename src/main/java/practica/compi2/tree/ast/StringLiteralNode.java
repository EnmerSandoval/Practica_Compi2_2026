package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record StringLiteralNode(int line, int column, String value) implements AstNode {

    @Override
    public String label() {
        return "textum " + value;
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
