package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record StructDefinitionNode(int line, int column, String name,
                                   List<StructAttributeNode> attributes) implements AstNode {

    @Override
    public String label() {
        return "structura " + name;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(attributes);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("structura")).append(' ').append(new PigLatinUtil().translateWord(name));
        sb.append(" {\n");
        for (StructAttributeNode attribute : attributes) {
            sb.append("    ");
            attribute.toPigLatin(sb);
            sb.append('\n');
        }
        sb.append('}').append(' ').append(new PigLatinUtil().translateWord("finis")).append(';');
    }
}
