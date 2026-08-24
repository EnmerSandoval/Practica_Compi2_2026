package practica.compi2.ide.graph;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {

    private static final int ANCHO_CAJA = 120;
    private static final int ALTO_CAJA = 30;
    private static final int ESPACIO_X = 20;
    private static final int ESPACIO_Y = 60;

    private final NodeGraph raiz;
    private final Map<NodeGraph, Point> posiciones = new HashMap<>();
    private int contador = 0;

    public TreePanel(NodeGraph raiz) {
        this.raiz = raiz;
        setBackground(Color.WHITE);
        ubicar(raiz, 0);
        setPreferredSize(new Dimension(contador * (ANCHO_CAJA + ESPACIO_X) + 40, profundidad(raiz) * ESPACIO_Y + 60));
    }

    private double ubicar(NodeGraph nodo, int nivel) {
        double x;
        if (nodo.children().isEmpty()) {
            x = 20 + contador * (ANCHO_CAJA + ESPACIO_X);
            contador++;
        } else {
            double primero = -1;
            double ultimo = -1;
            for (NodeGraph hijo : nodo.children()) {
                double hx = ubicar(hijo, nivel + 1);
                if (primero < 0) {
                    primero = hx;
                }
                ultimo = hx;
            }
            x = (primero + ultimo) / 2;
        }
        posiciones.put(nodo, new Point((int) x, 20 + nivel * ESPACIO_Y));
        return x;
    }

    private int profundidad(NodeGraph nodo) {
        int max = 0;
        for (NodeGraph hijo : nodo.children()) {
            max = Math.max(max, profundidad(hijo));
        }
        return max + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujar(raiz, g);
    }

    private void dibujar(NodeGraph nodo, Graphics g) {
        Point p = posiciones.get(nodo);

        for (NodeGraph hijo : nodo.children()) {
            Point ph = posiciones.get(hijo);
            g.setColor(Color.BLACK);
            g.drawLine(p.x + ANCHO_CAJA / 2, p.y + ALTO_CAJA, ph.x + ANCHO_CAJA / 2, ph.y);
        }

        g.setColor(Color.WHITE);
        g.fillRect(p.x, p.y, ANCHO_CAJA, ALTO_CAJA);
        g.setColor(Color.BLACK);
        g.drawRect(p.x, p.y, ANCHO_CAJA, ALTO_CAJA);

        FontMetrics fm = g.getFontMetrics();
        String texto = recortar(nodo.text(), fm, ANCHO_CAJA - 10);
        int tx = p.x + (ANCHO_CAJA - fm.stringWidth(texto)) / 2;
        int ty = p.y + ALTO_CAJA / 2 + fm.getAscent() / 2 - 2;
        g.drawString(texto, tx, ty);

        for (NodeGraph hijo : nodo.children()) {
            dibujar(hijo, g);
        }
    }

    private String recortar(String texto, FontMetrics fm, int maxAncho) {
        if (fm.stringWidth(texto) <= maxAncho) {
            return texto;
        }
        String recorte = texto;
        while (recorte.length() > 1 && fm.stringWidth(recorte + "..") > maxAncho) {
            recorte = recorte.substring(0, recorte.length() - 1);
        }
        return recorte + "..";
    }
}
