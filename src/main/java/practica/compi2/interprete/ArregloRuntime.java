package practica.compi2.interprete;

public final class ArregloRuntime {

    private final Valor[] elementos;
    private final String tipoElemento;

    public ArregloRuntime(Valor[] elementos, String tipoElemento) {
        this.elementos = elementos;
        this.tipoElemento = tipoElemento;
    }

    public Valor[] elementos() {
        return elementos;
    }

    public String tipoElemento() {
        return tipoElemento;
    }

    public int tamano() {
        return elementos.length;
    }
}
