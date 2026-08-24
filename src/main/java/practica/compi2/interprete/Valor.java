package practica.compi2.interprete;

import practica.compi2.semantic.TypeSystem;

public final class Valor {

    private final String tipo;
    private final Object dato;

    private Valor(String tipo, Object dato) {
        this.tipo = tipo;
        this.dato = dato;
    }

    public static Valor numero(long v) {
        return new Valor(TypeSystem.NUMERUS, v);
    }

    public static Valor decimal(double v) {
        return new Valor(TypeSystem.DECIMALIS, v);
    }

    public static Valor texto(String v) {
        return new Valor(TypeSystem.TEXTUM, v);
    }

    public static Valor caracter(char v) {
        return new Valor(TypeSystem.LITTERA, v);
    }

    public static Valor booleano(boolean v) {
        return new Valor(TypeSystem.BOOLEANO, v);
    }

    public static Valor arreglo(ArregloRuntime v) {
        return new Valor("arreglo", v);
    }

    public static Valor estructura(EstructuraRuntime v) {
        return new Valor(v.tipo(), v);
    }

    public String tipo() {
        return tipo;
    }

    public long comoNumero() {
        return ((Number) dato).longValue();
    }

    public double comoDecimal() {
        return ((Number) dato).doubleValue();
    }

    public String comoTexto() {
        return (String) dato;
    }

    public char comoCaracter() {
        return (Character) dato;
    }

    public boolean comoBooleano() {
        return (Boolean) dato;
    }

    public ArregloRuntime comoArreglo() {
        return (ArregloRuntime) dato;
    }

    public EstructuraRuntime comoEstructura() {
        return (EstructuraRuntime) dato;
    }

    public String representar() {
        return switch (tipo) {
            case TypeSystem.NUMERUS -> String.valueOf(comoNumero());
            case TypeSystem.DECIMALIS -> String.valueOf(comoDecimal());
            case TypeSystem.TEXTUM -> comoTexto();
            case TypeSystem.LITTERA -> String.valueOf(comoCaracter());
            case TypeSystem.BOOLEANO -> comoBooleano() ? "verum" : "falsus";
            case "arreglo" -> "[" + comoArreglo().tamano() + " elementos]";
            default -> comoEstructura().tipo() + comoEstructura().campos();
        };
    }
}

