package practica.compi2.interprete;

import java.util.HashMap;
import java.util.Map;

public final class EntornoEjecucion {

    private final EntornoEjecucion padre;
    private final Map<String, Valor> variables = new HashMap<>();

    public EntornoEjecucion(EntornoEjecucion padre) {
        this.padre = padre;
    }

    public void declarar(String nombre, Valor valor) {
        variables.put(nombre, valor);
    }

    public Valor obtener(String nombre) {
        for (EntornoEjecucion e = this; e != null; e = e.padre) {
            if (e.variables.containsKey(nombre)) {
                return e.variables.get(nombre);
            }
        }
        return null;
    }

    public void asignar(String nombre, Valor valor) {
        for (EntornoEjecucion e = this; e != null; e = e.padre) {
            if (e.variables.containsKey(nombre)) {
                e.variables.put(nombre, valor);
                return;
            }
        }
        declarar(nombre, valor);
    }
}
