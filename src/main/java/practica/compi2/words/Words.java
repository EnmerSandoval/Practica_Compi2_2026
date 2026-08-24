package practica.compi2.words;

import java.awt.*;

public enum Words {

    ESTO(new Color(196, 26, 22)),
    SERIES(new Color(0, 0, 200)),
    NUMERUS(new Color(0, 131, 143)),
    TEXTUM(new Color(153, 122, 0)),
    DECIMALIS(new Color(173, 20, 87)),
    LITTERA(new Color(196, 98, 16)),
    VERUM(new Color(43, 129, 41)),
    FALSUS(new Color(43, 129, 41)),
    BOOL(new Color(0, 131, 143)),
    STRUCTURA(new Color(136, 23, 152)),

    SI(new Color(173, 20, 87)),
    ALITER(new Color(43, 129, 41)),
    DUM(new Color(196, 98, 16)),
    FACERE(new Color(0, 131, 143)),
    PER(new Color(153, 122, 0)),
    PERGE(new Color(43, 129, 41)),
    INTERRUMPE(new Color(0, 0, 200)),

    ACTIO(new Color(196, 98, 16)),
    RATIO(new Color(173, 20, 87)),
    REDDERE(new Color(153, 122, 0)),

    VARIABILES(new Color(0, 131, 143)),
    MUNERA(new Color(196, 98, 16)),
    MAIOR(new Color(43, 129, 41)),
    FINIS(new Color(136, 23, 152)),

    NON(new Color(196, 26, 22));

    private final Color color;

    Words(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static Words getToken(String txt) {
        for (Words w : values()) {
            if (w.name().equals(txt)) {
                return w;
            }
        }
        return null;
    }
}