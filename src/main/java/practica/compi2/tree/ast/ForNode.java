package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ForNode(int line, int column, VariableDeclarationNode init, AstNode condition, AstNode increment, BlockNode body) implements AstNode {

    @Override
    public String label() {
        return "per";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(init, condition, increment, body);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("per")).append(" (");
        init.toPigLatin(sb);
        sb.append(' ');
        condition.toPigLatin(sb);
        sb.append("; ");
        if (increment instanceof AssignmentNode assignment) {
            assignment.target().toPigLatin(sb);
            sb.append(" = ");
            assignment.value().toPigLatin(sb);
        } else {
            increment.toPigLatin(sb);
        }
        sb.append(") {\n");
        body.toPigLatin(sb);
        sb.append('}');
    }
}
