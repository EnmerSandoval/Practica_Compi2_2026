package practica.compi2.interprete;

public final class SenalRetorno extends RuntimeException {

    private final Valor valor;

    SenalRetorno(Valor valor) {
        super(null, null, false, false);
        this.valor = valor;
    }

    Valor valor() {
        return valor;
    }
}
