package practica.compi2.ide.graph;

import java.util.List;

public interface NodeGraph {

    String text();
    List<NodeGraph> children();
}
