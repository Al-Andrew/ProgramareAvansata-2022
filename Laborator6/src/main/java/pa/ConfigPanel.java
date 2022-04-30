package pa;

import com.sun.javafx.geom.Dimension2D;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel gridSizeLabel;
    JLabel sticksCountLabel;
    JSpinner rowsSpinner;
    JSpinner colsSpinner;
    JSpinner sticksSpinner;
    SpinnerNumberModel rowsSpinnerModel;
    SpinnerNumberModel colsSpinnerModel;
    SpinnerNumberModel sticksSpinnerModel;
    JButton createButton;
    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        gridSizeLabel = new JLabel("Grid size:");
        sticksCountLabel = new JLabel("Sticks:");
        rowsSpinnerModel = new SpinnerNumberModel(10, 2, 30, 1);
        rowsSpinner = new JSpinner(rowsSpinnerModel);
        colsSpinnerModel = new SpinnerNumberModel(10, 2, 30, 1);
        colsSpinner = new JSpinner(colsSpinnerModel);
        sticksSpinnerModel = new SpinnerNumberModel(30, 10, 100, 5);
        sticksSpinner = new JSpinner(sticksSpinnerModel);
        createButton = new JButton("Create");

        createButton.addActionListener(this::createGame);
        add(gridSizeLabel);
        add(rowsSpinner);
        add(colsSpinner);
        add(sticksCountLabel);
        add(sticksSpinner);
        add(createButton);
    }

    public int getRows() {
        return rowsSpinnerModel.getNumber().intValue();
    }

    public int getCols() {
        return colsSpinnerModel.getNumber().intValue();
    }

    public int getSticks() {
        return sticksSpinnerModel.getNumber().intValue();
    }

    public void resetGame(SticksAndStonesGameData gd) {
        frame.gameData = gd;
        frame.gameData.populateRandomSticks(getSticks());
        //System.out.println(frame.gameData.getSticks());
        frame.drawingPanel.init();
        frame.drawingPanel.updateUI();
        frame.drawingPanel.paintComponent(getGraphics());
        frame.setSize(500, 600);
    }

    private void createGame(java.awt.event.ActionEvent e) {
        resetGame(new SticksAndStonesGameData(getRows(), getCols(), frame));
        this.updateUI();
        //TODO(Al-Andrew): pop-up for loss of game data
    }
}
