package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record ExpressionStatementNode(int line, int column, AstNode expression) implements AstNode {

    @Override
    public String label() {
        return "expresion";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(expression);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        expression.toPigLatin(sb);
        sb.append(';');
    }
}
