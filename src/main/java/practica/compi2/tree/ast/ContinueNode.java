package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ContinueNode(int line, int column) implements AstNode {

    @Override
    public String label() {
        return "perge";
    }

    @Override
    public List<AstNode> children() {
        return List.of();
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord("perge")).append(';');
    }
}
