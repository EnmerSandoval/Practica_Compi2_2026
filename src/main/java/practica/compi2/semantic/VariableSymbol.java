package practica.compi2.semantic;

public record VariableSymbol(String name, String type, boolean isArray, Integer arraySize, boolean isParameter,
                             int line, int column) {
}
