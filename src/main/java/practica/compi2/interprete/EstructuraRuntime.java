package practica.compi2.interprete;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EstructuraRuntime {

    private final String tipo;
    private final Map<String, Valor> campos = new LinkedHashMap<>();

    public EstructuraRuntime(String tipo) {
        this.tipo = tipo;
    }

    public String tipo() {
        return tipo;
    }

    public Map<String, Valor> campos() {
        return campos;
    }
}
