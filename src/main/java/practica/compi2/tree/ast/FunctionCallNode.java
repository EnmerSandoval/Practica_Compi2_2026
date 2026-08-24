package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record FunctionCallNode(int line, int column, String name, List<AstNode> arguments) implements AstNode {

    @Override
    public String label() {
        return name + "(" + arguments.size() + " args)";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(arguments);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        sb.append(new PigLatinUtil().translateWord(name)).append('(');
        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            arguments.get(i).toPigLatin(sb);
        }
        sb.append(')');
    }
}
