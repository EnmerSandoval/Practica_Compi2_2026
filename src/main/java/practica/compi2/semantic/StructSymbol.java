package practica.compi2.semantic;

import java.util.LinkedHashMap;

public record StructSymbol(String name, LinkedHashMap<String, AttributeInfo> attributes, int line, int column) {
}