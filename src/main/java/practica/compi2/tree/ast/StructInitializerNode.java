package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record StructInitializerNode(int line, int column, String typeName,
                                    List<StructFieldInitNode> fields) implements AstNode {

    @Override
    public String label() {
        return (typeName != null ? typeName : "?") + " {" + fields.size() + "}";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(fields);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        if (typeName != null) {
            sb.append(new PigLatinUtil().translateWord(typeName)).append(' ');
        }
        sb.append('{');
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            fields.get(i).toPigLatin(sb);
        }
        sb.append('}');
    }
}
