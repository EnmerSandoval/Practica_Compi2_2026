package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record AccessNode(int line, int column, AstNode base, String name, AstNode index) implements AstNode {

    @Override
    public String label() {
        String propio = name + (index != null ? "[]" : "");
        return base == null ? propio : base.label() + "." + propio;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(base, index);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        if (base != null) {
            base.toPigLatin(sb);
            sb.append('.');
        }
        sb.append(new PigLatinUtil().translateWord(name));
        if (index != null) {
            sb.append('[');
            index.toPigLatin(sb);
            sb.append(']');
        }
    }
}
