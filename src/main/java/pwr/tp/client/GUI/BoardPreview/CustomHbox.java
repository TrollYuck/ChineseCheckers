package pwr.tp.client.GUI.BoardPreview;

import javafx.scene.layout.HBox;

/**
 * CustomHbox is a custom HBox layout with predefined width, height, spacing, and alignment.
 */
public class CustomHbox extends HBox {
    /**
     * Constructs a CustomHbox with predefined width, height, spacing, and alignment.
     */
    public CustomHbox() {
        super();
        setPrefWidth(595);
        setPrefHeight(35);
        setSpacing(15);
        setAlignment(javafx.geometry.Pos.CENTER);
    }
}
