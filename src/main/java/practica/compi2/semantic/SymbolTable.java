package practica.compi2.semantic;

import java.util.Map;

public record SymbolTable(Scope globalScope, Map<String, StructSymbol> structs, Map<String, FunctionSymbol> functions) {
}
