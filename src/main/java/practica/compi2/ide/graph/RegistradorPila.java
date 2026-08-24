package practica.compi2.ide.graph;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class RegistradorPila implements ParseTreeListener {

    private final String[] nombresReglas;
    private final Deque<String> pila = new ArrayDeque<>();
    private final List<PasoPila> pasos = new ArrayList<>();

    public RegistradorPila(String[] nombresReglas) {
        this.nombresReglas = nombresReglas;
    }

    public List<PasoPila> pasos() {
        return pasos;
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        pila.push("*" + nombresReglas[ctx.getRuleIndex()]);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        String marca = "*" + nombresReglas[ctx.getRuleIndex()];
        List<String> aRemover = new ArrayList<>();
        while (!pila.isEmpty()) {
            String tope = pila.pop();
            if (tope.equals(marca)) {
                break;
            }
            aRemover.add(tope);
        }

        String nombreRegla = nombresReglas[ctx.getRuleIndex()];
        pila.push(nombreRegla);
        pasos.add(new PasoPila(snapshot(), "replace " + nombreRegla));
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() == Token.EOF) {
            pasos.add(new PasoPila(snapshot(), "accept"));
            return;
        }
        pila.push(node.getText());
        pasos.add(new PasoPila(snapshot(), "shift " + node.getText()));
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        pasos.add(new PasoPila(snapshot(), "error en '" + node.getText() + "'"));
    }

    private List<String> snapshot() {
        List<String> lista = new ArrayList<>(pila);
        java.util.Collections.reverse(lista);
        return lista;
    }
}
