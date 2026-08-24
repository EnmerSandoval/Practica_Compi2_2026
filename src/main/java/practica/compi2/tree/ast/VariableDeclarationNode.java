package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record VariableDeclarationNode(int line, int column, String name, String type, boolean isArray,
                                      Integer arraySize, AstNode initializer) implements AstNode{

    @Override
    public String label() {
        String base = (isArray ? "series " : "esto ") + name + " : " + type;
        return isArray ? base + "[" + arraySize + "]" : base;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(initializer);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord(isArray ? "series" : "esto")).append(' ');
        sb.append(new PigLatinUtil().translateWord(name));
        if (isArray) {
            sb.append('[').append(arraySize).append(']');
        }
        sb.append(" : ").append(new PigLatinUtil().translateWord(type));
        if (initializer != null) {
            sb.append(' ');
            initializer.toPigLatin(sb);
        }
        sb.append(';');
    }
}
