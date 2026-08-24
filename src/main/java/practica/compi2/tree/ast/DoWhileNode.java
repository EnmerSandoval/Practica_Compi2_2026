package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record DoWhileNode(int line, int column, BlockNode body, AstNode condition) implements AstNode {

    @Override
    public String label() {
        return "facere";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(body, condition);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("facere")).append(" {\n");
        body.toPigLatin(sb);
        sb.append('}').append(' ').append(new PigLatinUtil().translateWord("dum")).append(" (");
        condition.toPigLatin(sb);
        sb.append(");");
    }
}
