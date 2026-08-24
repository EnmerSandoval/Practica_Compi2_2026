package practica.compi2.semantic;

import java.util.Map;

public final class TypeSystem {

    public static final String BOOLEANO = "booleano";
    public static final String LITTERA = "littera";
    public static final String NUMERUS = "numerus";
    public static final String DECIMALIS = "decimalis";
    public static final String TEXTUM = "textum";

    private static final Map<String, Integer> RANK = Map.of(
            BOOLEANO, 1,
            LITTERA, 2,
            NUMERUS, 3,
            DECIMALIS, 4,
            TEXTUM, 5
    );

    public boolean isPrimitive(String typeName) {
        return RANK.containsKey(typeName);
    }

    public int rank(String typeName) {
        return RANK.getOrDefault(typeName, -1);
    }


    public String normalize(String rawType) {
        if ("verum".equals(rawType) || "falsus".equals(rawType) || "bool".equals(rawType)) {
            return BOOLEANO;
        }
        return rawType;
    }


    public boolean isAssignable(String source, String target) {
        if (source.equals(target)) {
            return true;
        }
        if (!isPrimitive(source) || !isPrimitive(target)) {
            return false;
        }
        if (source.equals(TEXTUM) || target.equals(TEXTUM)) {
            return false;
        }
        if (source.equals(BOOLEANO) || target.equals(BOOLEANO)) {
            return false;
        }
        return rank(source) <= rank(target);
    }
}
