module org.noopi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.noopi to javafx.fxml;
    exports org.noopi;
}
