package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record PostfixExpressionNode(int line, int column, AstNode operand, String operator) implements AstNode {

    @Override
    public String label() {
        return operator;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(operand);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        operand.toPigLatin(sb);
        sb.append(operator);
    }
}
