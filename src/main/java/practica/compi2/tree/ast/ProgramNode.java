package practica.compi2.tree.ast;

import practica.compi2.util.PigLatinUtil;
import practica.compi2.visitors.AstNode;

import java.util.List;

public record ProgramNode(int line, int column, List<AstNode> globalVariables, List<AstNode> functions,
                          BlockNode mainBlock) implements AstNode {

    @Override
    public String label() {
        return "program";
    }

    @Override
    public List<AstNode> children() {
        return new NodeUtil().children(globalVariables, functions, mainBlock);
    }

    @Override
    public void toPigLatin(StringBuffer sb) {
        if (!globalVariables.isEmpty()) {
            sb.append(new PigLatinUtil().translateWord("VARIABILES")).append(">\n");
            for (AstNode globalVariable : globalVariables) {
                globalVariable.toPigLatin(sb);
                sb.append('\n');
            }
            sb.append("<\n\n");
        }
        if (!functions.isEmpty()) {
            sb.append(new PigLatinUtil().translateWord("MUNERA")).append(">\n");
            for (AstNode function : functions) {
                function.toPigLatin(sb);
                sb.append("\n\n");
            }
            sb.append("<\n\n");
        }
        sb.append(new PigLatinUtil().translateWord("MAIOR")).append(">\n");
        mainBlock.toPigLatin(sb);
        sb.append(new PigLatinUtil().translateWord("FINIS")).append(';');
    }
}
