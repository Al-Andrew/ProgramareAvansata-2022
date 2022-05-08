package pa.graphics;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ImagePanel imagePanel;
    JPanel interactionPanel;
    HighlightData highlights = new HighlightData();

    public MainFrame() {
        super("Laborator 8");
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imagePanel = new ImagePanel(highlights);
        add(imagePanel, BorderLayout.CENTER);
        interactionPanel = new InteractionPanel(imagePanel, highlights);
        add(interactionPanel, BorderLayout.LINE_END);

        pack();
    }

}
