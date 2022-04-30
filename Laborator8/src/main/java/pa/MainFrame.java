package pa;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame {
    ImagePanel imagePanel;
    JPanel interactionPanel;

    public MainFrame() {
        super("Laborator 8");
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);
        interactionPanel = new InteractionPanel(imagePanel);
        add(interactionPanel, BorderLayout.LINE_END);

        pack();
    }

}
