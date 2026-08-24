package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.List;

public record ArrayInitializerNode(int line, int column, List<AstNode> elements) implements AstNode {

    @Override
    public String label() {
        return "{" + elements.size() + " elementos}";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(elements);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append('{');
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            elements.get(i).toPigLatin(sb);
        }
        sb.append('}');
    }
}
