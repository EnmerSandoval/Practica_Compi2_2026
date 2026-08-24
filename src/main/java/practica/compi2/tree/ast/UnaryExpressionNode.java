package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record UnaryExpressionNode(int line, int column, String operator, AstNode operand) implements AstNode {

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
        boolean isWord = "non".equals(operator);
        sb.append(isWord ? new PigLatinUtil().translateWord(operator) + " " : operator);
        operand.toPigLatin(sb);
    }
}
