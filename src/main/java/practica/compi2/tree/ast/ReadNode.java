package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ReadNode(int line, int column, AstNode target) implements AstNode {

    @Override
    public String label() {
        return "<<";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(target);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        if (target != null) {
            target.toPigLatin(sb);
            sb.append(' ');
        }
        sb.append(PigLatinUtil.LECTURA).append(';');
    }
}
