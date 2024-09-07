package org.noopi.view.swing.menubar;

public enum Item {
    NEW("Nouveau fichier"),
    OPEN("Ouvrir"),
    SAVE("Enregistrer"),
    SAVE_AS("Enregistrer sous");
    
    private String label;

    Item(String lb) {
        label = lb;
    }
    
    public String label() {
        return label;
    }
}
