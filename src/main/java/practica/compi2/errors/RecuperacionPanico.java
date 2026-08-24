package practica.compi2.errors;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import practica.compi2.GrammarParser;

public class RecuperacionPanico extends DefaultErrorStrategy  {

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        int siguiente = recognizer.getInputStream().LA(1);
        while (siguiente != GrammarParser.PUNTO_COMA && siguiente != GrammarParser.LLAVE_DERECHA && siguiente != Token.EOF) {
            recognizer.consume();
            siguiente = recognizer.getInputStream().LA(1);
        }

        if (siguiente == Token.EOF) {
            throw new RecuperacionException(
                    "no se pudo recuperar de un error de sintaxis: se llego al final del archivo sin encontrar ';' ni '}'", e);
        }

        endErrorCondition(recognizer);
    }
}
