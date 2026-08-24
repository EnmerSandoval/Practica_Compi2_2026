package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ReturnNode(int line, int column, AstNode value) implements AstNode {

    @Override
    public String label() {
        return "reddere";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(value);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("reddere"));
        if (value != null) {
            sb.append(' ');
            value.toPigLatin(sb);
        }
        sb.append(';');
    }
}
