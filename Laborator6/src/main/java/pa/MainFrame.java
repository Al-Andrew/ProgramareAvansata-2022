package pa;

import javax.swing.*;

import java.awt.*;

import static javax.swing.SwingConstants.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel drawingPanel;
    SticksAndStonesGameData gameData;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }
    private void init() {
        configPanel = new ConfigPanel(this);
        add(configPanel, BorderLayout.NORTH);
        drawingPanel = new DrawingPanel(this);
        add(drawingPanel, BorderLayout.CENTER);
        controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
}