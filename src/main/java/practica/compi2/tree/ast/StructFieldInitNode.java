package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record StructFieldInitNode(int line, int column, String name, String elementType, Integer arraySize,
                                  AstNode value) implements AstNode {

    @Override
    public String label() {
        return elementType != null ? name + " : " + elementType + "[" + arraySize + "]" : name;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(value);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord(name)).append(": ");
        if (elementType != null) {
            sb.append(new PigLatinUtil().translateWord(elementType)).append('[').append(arraySize).append(']');
        } else {
            value.toPigLatin(sb);
        }
    }
}
