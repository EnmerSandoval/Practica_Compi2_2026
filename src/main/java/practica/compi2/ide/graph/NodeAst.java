package practica.compi2.ide.graph;

import practica.compi2.visitors.AstNode;

import java.util.ArrayList;
import java.util.List;

public class NodeAst implements NodeGraph {

    private final AstNode nodo;

    private List<NodeGraph> hijosCache;

    public NodeAst(AstNode nodo) {
        this.nodo = nodo;
    }

    @Override
    public String text() {
        return nodo.label();
    }

    @Override
    public List<NodeGraph> children() {
        if (hijosCache == null) {
            hijosCache = new ArrayList<>();
            for (AstNode hijo : nodo.children()) {
                hijosCache.add(new NodeAst(hijo));
            }
        }
        return hijosCache;
    }
}
