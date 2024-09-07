package org.noopi.view.swing.components.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class GraphicArrow extends JComponent{
    
    // ATTRIBUTS STATIQUES

    private static final int ARROW_BASE = 60;

    private static final int ARROW_THICK = 10;

    private static final int ARROW_HEIGHT = 50;

    private static final int HEIGHT = 30;

    private static final int PREFFERED_HEIGHT = HEIGHT + ARROW_HEIGHT;

    private static final int PREFFERED_WIDTH = ARROW_BASE;

    // CONSTRUCTEUR

    public GraphicArrow(){
        setPreferredSize(new Dimension(PREFFERED_WIDTH, PREFFERED_HEIGHT));
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int xA = 0;
        int yA = HEIGHT;

        int xB = ARROW_BASE/2;
        int yB = 0;

        int xC = ARROW_BASE;
        int yC = yA;

        int[] x = {xA, xB, xC};
        int[] y = {yA, yB, yC};

        g.setColor(Color.RED);

        int nbSummers = 3;

        g.fillPolygon(x, y, nbSummers);
        
        int xD = xB - ARROW_THICK / 2;
        int yD = yA;
        
        g.fillRect(xD, yD, ARROW_THICK, ARROW_HEIGHT);
    }
}
