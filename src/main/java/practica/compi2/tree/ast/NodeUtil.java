package practica.compi2.tree.ast;

import practica.compi2.visitors.AstNode;

import java.util.ArrayList;
import java.util.List;

public final class NodeUtil {
    NodeUtil() {
    }

    public static List<AstNode> children(Object... items) {
        List<AstNode> list = new ArrayList<>();
        collect(items, list);
        return list;
    }

    private static void collect(Object item, List<AstNode> out) {
        if (item == null) {
            return;
        }
        if (item instanceof AstNode node) {
            out.add(node);
        } else if (item instanceof Object[] array) {
            for (Object o : array) {
                collect(o, out);
            }
        } else if (item instanceof List<?> list) {
            for (Object o : list) {
                collect(o, out);
            }
        }
    }
}
