package practica.compi2.words;

import java.awt.*;

public enum Words {

    ESTO(Color.RED),
    SERIES(Color.BLUE),
    NUMERUS(Color.CYAN),
    TEXTUM(Color.YELLOW),
    DECIMALIS(Color.PINK),
    LITTERA(Color.ORANGE),
    VERUM(Color.GREEN),
    FALSUSM(Color.GREEN),
    STRUCTURA(Color.MAGENTA),

    SI(Color.PINK),
    ALITER(Color.GREEN),
    DUM(Color.ORANGE),
    FACERE(Color.CYAN),
    PER(Color.YELLOW),
    PERGE(Color.GREEN),
    INTERRUMPE(Color.BLUE),

    ACTIO(Color.ORANGE),
    RATIO(Color.PINK),
    REDDERE(Color.YELLOW),

    VARIABILES(Color.CYAN),
    MUNERA(Color.ORANGE),
    MAIOR(Color.GREEN),
    FINIS(Color.MAGENTA),

    NON(Color.RED);

    private final Color color;

    Words(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public Words getToken(String txt) {
        for (Words w : values()){
            if(w.name().equals(txt)){
                return w;
            }
        }
        return null;
    }
}