package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record WhileNode(int line, int column, AstNode condition, BlockNode body) implements AstNode {

    @Override
    public String label() {
        return "dum";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(condition, body);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("dum")).append(" (");
        condition.toPigLatin(sb);
        sb.append(") {\n");
        body.toPigLatin(sb);
        sb.append('}').append(' ').append(new PigLatinUtil().translateWord("finis")).append(';');
    }
}
