package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record WriteNode(int line, int column, List<AstNode> expressions) implements AstNode {

    @Override
    public String label() {
        return ">>";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(expressions);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        for (AstNode expression : expressions) {
            sb.append(PigLatinUtil.ESCRITURA).append(' ');
            expression.toPigLatin(sb);
            sb.append(' ');
        }
        sb.append(';');
    }
}
