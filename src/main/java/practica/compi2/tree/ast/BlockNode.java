package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record BlockNode(int line, int column, List<AstNode> statements) implements AstNode {

    @Override
    public String label() {
        return "bloque";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(statements);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        for (AstNode statement : statements) {
            statement.toPigLatin(sb);
            sb.append('\n');
        }
    }
}
