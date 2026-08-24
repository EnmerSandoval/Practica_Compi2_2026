package practica.compi2.semantic;

import java.util.List;

public record FunctionSymbol(String name, String returnType, List<VariableSymbol> parameters, int line, int column) {
}
