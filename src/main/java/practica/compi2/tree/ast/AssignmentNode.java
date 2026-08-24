package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record AssignmentNode(int line, int column, AstNode target, AstNode value) implements AstNode {

    @Override
    public String label() {
        return "=";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(target, value);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        target.toPigLatin(sb);
        sb.append(" = ");
        value.toPigLatin(sb);
        sb.append(';');
    }
}
