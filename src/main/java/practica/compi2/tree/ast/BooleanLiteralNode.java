package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record BooleanLiteralNode(int line, int column, boolean value) implements AstNode {

    @Override
    public String label() {
        return value ? "verum" : "falsus";
    }

    @Override
    public List<AstNode> children() {
        return List.of();
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord(value ? "verum" : "falsus"));
    }
}
