package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record FunctionDeclarationNode(int line, int column, String name, String returnType, List<ParameterNode> parameters, List<VariableDeclarationNode> localVariables, List<AstNode> body) implements AstNode {

    @Override
    public String label() {
        return (returnType != null ? "ratio " + returnType + " " : "actio ") + name;
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(parameters, localVariables, body);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        if (returnType != null) {
            sb.append(new PigLatinUtil().translateWord("ratio")).append(' ').append(new PigLatinUtil().translateWord(returnType)).append(' ');
        } else {
            sb.append(new PigLatinUtil().translateWord("actio")).append(' ');
        }
        sb.append(new PigLatinUtil().translateWord(name)).append('(');
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            parameters.get(i).toPigLatin(sb);
        }
        sb.append(") {\n");
        if (!localVariables.isEmpty()) {
            sb.append(new PigLatinUtil().translateWord("VARIABILES")).append("[\n");
            for (VariableDeclarationNode localVariable : localVariables) {
                sb.append("    ");
                localVariable.toPigLatin(sb);
                sb.append('\n');
            }
            sb.append("]\n");
        }
        for (AstNode statement : body) {
            statement.toPigLatin(sb);
            sb.append('\n');
        }
        sb.append('}').append(' ').append(new PigLatinUtil().translateWord("finis")).append(';');
    }
}
