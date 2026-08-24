package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record BinaryExpressionNode(int line, int column, String operator, AstNode left, AstNode right) implements AstNode {

    @Override
    public String label() {
        return operator;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(left, right);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        left.toPigLatin(sb);
        sb.append(' ').append(operator).append(' ');
        right.toPigLatin(sb);
    }
}
