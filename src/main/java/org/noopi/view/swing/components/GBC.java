package org.noopi.view.swing.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC extends GridBagConstraints{
    
    // CONSTRUCTEURS

    public GBC(){
        super();
    }

    public GBC(int gridx, int gridy){
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this(gridx, gridy);
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    // REQUETES

    public GBC weight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GBC insets(int v){
        return insets(v, v, v, v);
    }

    public GBC insets(int top, int left, int bottom, int right){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBC ipad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }

    public GBC anchor(int a){
        this.anchor = a;
        return this;
    }

    public GBC fill(int f) {
        this.fill = f;
        return this;
    }
}
