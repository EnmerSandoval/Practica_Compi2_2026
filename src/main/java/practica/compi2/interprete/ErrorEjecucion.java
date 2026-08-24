package practica.compi2.interprete;

public final class ErrorEjecucion extends RuntimeException {

    private final int linea;
    private final int columna;

    public ErrorEjecucion(String mensaje, int linea, int columna) {
        super(mensaje);
        this.linea = linea;
        this.columna = columna;
    }

    public int linea() {
        return linea;
    }

    public int columna() {
        return columna;
    }
}
