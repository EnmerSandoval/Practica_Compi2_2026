package practica.compi2.semantic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Scope {
    private final String label;
    private final Scope parent;
    private final Map<String, VariableSymbol> variables = new LinkedHashMap<>();
    private final List<Scope> children = new ArrayList<>();

    public Scope(String label, Scope parent) {
        this.label = label;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public Scope parent() {
        return parent;
    }

    public String label() {
        return label;
    }

    public Map<String, VariableSymbol> variables() {
        return variables;
    }

    public List<Scope> children() {
        return children;
    }

    public boolean declaredInThisScope(String name) {
        return variables.containsKey(name);
    }

    public void declare(VariableSymbol symbol) {
        variables.put(symbol.name(), symbol);
    }

    public VariableSymbol resolve(String name) {
        for (Scope scope = this; scope != null; scope = scope.parent) {
            VariableSymbol found = scope.variables.get(name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
