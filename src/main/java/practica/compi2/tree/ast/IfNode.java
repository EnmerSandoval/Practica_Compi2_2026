package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record IfNode(int line, int column, AstNode condition, BlockNode thenBlock, AstNode elseBranch) implements AstNode {

    @Override
    public String label() {
        return "si";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(condition, thenBlock, elseBranch);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("si")).append(" (");
        condition.toPigLatin(sb);
        sb.append(") {\n");
        thenBlock.toPigLatin(sb);
        sb.append('}');

        AstNode branch = elseBranch;
        while (branch != null) {
            if (branch instanceof IfNode elseIf) {
                sb.append(' ').append(new PigLatinUtil().translateWord("aliter")).append(" (");
                elseIf.condition().toPigLatin(sb);
                sb.append(") {\n");
                elseIf.thenBlock().toPigLatin(sb);
                sb.append('}');
                branch = elseIf.elseBranch();
            } else if (branch instanceof BlockNode elseBlock) {
                sb.append(' ').append(new PigLatinUtil().translateWord("aliter")).append(" {\n");
                elseBlock.toPigLatin(sb);
                sb.append('}');
                branch = null;
            } else {
                branch = null;
            }
        }
        sb.append(' ').append(new PigLatinUtil().translateWord("finis")).append(';');
    }
}
