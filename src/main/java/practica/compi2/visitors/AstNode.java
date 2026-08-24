package practica.compi2.visitors;

import java.util.List;

public interface AstNode {
    int line();
    int column();
    String label();
    List<AstNode> children();
    void toPigLatin(StringBuffer sb);
}
