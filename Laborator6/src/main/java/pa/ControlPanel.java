package pa;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //setLayout(new GridLayout(1, 4));
        exitBtn.addActionListener(this::exitGame);
        add(exitBtn);
        add(saveBtn);
        add(loadBtn);
    }

    private void exitGame(java.awt.event.ActionEvent e) {
        frame.dispose();
    }

}
