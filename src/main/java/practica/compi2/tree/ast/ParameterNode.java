package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ParameterNode(int line, int column, String name, String type) implements AstNode {

    @Override
    public String label() {
        return "esto " + name + " : " + type;
    }

    @Override
    public List<AstNode> children() {
        return List.of();
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("esto")).append(' ');
        sb.append(new PigLatinUtil().translateWord(name)).append(" : ").append(new PigLatinUtil().translateWord(type));
    }
}
