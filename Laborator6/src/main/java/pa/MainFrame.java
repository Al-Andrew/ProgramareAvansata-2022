package pa;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        this.setResizable(false);
        configPanel = new ConfigPanel(this);
        add(configPanel, BorderLayout.NORTH);
        drawingPanel = new DrawingPanel(this);
        add(drawingPanel, BorderLayout.CENTER);
        controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gameData != null)
                    gameData.addStoneAt(e.getX(), e.getY());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
}