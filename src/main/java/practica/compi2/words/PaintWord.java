package practica.compi2.words;

import org.antlr.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import practica.compi2.GrammarLexer;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Map;

public class PaintWord {
    private static final Color CADENA = new Color(163, 21, 21);
    private static final Color NUMERO = new Color(11, 106, 57);
    private static final Color COMENTARIO = new Color(95, 95, 95);
    private static final Color NORMAL = new Color(30, 30, 30);
    private static final Map<String, String> ALIAS_TOKEN = Map.of(
            "NO", "NON",
            "FIN_PROGRAMA", "FINIS"
    );

    public void colorear(JTextPane textPane) {
        String texto = textPane.getText();
        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, NORMAL);
        doc.setCharacterAttributes(0, texto.length(), normal, true);

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(texto));
        lexer.removeErrorListeners();

        Token token = lexer.nextToken();
        while (token.getType() != Token.EOF) {
            aplicarEstilo(doc, token);
            token = lexer.nextToken();
        }
    }

    private void aplicarEstilo(StyledDocument doc, Token token) {
        int tipo = token.getType();
        Words palabra = palabraReservada(tipo);
        Color color = colorPara(tipo, palabra);
        if (color == null) {
            return;
        }

        SimpleAttributeSet estilo = new SimpleAttributeSet();
        StyleConstants.setForeground(estilo, color);
        StyleConstants.setBold(estilo, palabra != null);
        StyleConstants.setItalic(estilo, esComentario(tipo));

        int inicio = token.getStartIndex();
        int largo = token.getStopIndex() - token.getStartIndex() + 1;
        if (inicio >= 0 && largo > 0) {
            doc.setCharacterAttributes(inicio, largo, estilo, true);
        }
    }

    private Color colorPara(int tipo, Words palabra) {
        if (palabra != null) {
            return palabra.getColor();
        }
        if (tipo == GrammarLexer.CADENA || tipo == GrammarLexer.CARACTER) {
            return CADENA;
        }
        if (tipo == GrammarLexer.NUMERO || tipo == GrammarLexer.DECIMAL) {
            return NUMERO;
        }
        if (esComentario(tipo)) {
            return COMENTARIO;
        }
        return null;
    }

    private Words palabraReservada(int tipo) {
        String nombreRegla = GrammarLexer.VOCABULARY.getSymbolicName(tipo);
        if (nombreRegla == null) {
            return null;
        }
        String nombrePalabra = ALIAS_TOKEN.getOrDefault(nombreRegla, nombreRegla);
        return Words.getToken(nombrePalabra);
    }

    private boolean esComentario(int tipo) {
        return tipo == GrammarLexer.COMENTARIO_LINEA || tipo == GrammarLexer.COMENTARIO_BLOQUE
                || tipo == GrammarLexer.COMENTARIO_HASH;
    }

}