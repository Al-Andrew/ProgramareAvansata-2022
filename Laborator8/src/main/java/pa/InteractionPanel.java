package pa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InteractionPanel extends JPanel {
    JTextField textFieldCity1 = new JTextField();
    JLabel labelCity1 = new JLabel("City 1:");
    JTextField textFieldCity2 = new JTextField();
    JLabel labelCity2 = new JLabel("City 2:");
    JButton computeButton = new JButton("Compute distance");
    JPanel labelHolder = new JPanel();
    java.util.List<JLabel> resultLabels = new ArrayList<>();
    JButton clearButton = new JButton("Clear");
    private final ImagePanel imagePanel;

    public InteractionPanel(ImagePanel imagePanel){
        this.imagePanel = imagePanel;
        setPreferredSize(new Dimension(300, 738));
        textFieldCity1.setPreferredSize(new Dimension(250, 40));
        textFieldCity2.setPreferredSize(new Dimension(250, 40));
        computeButton.addActionListener(this::computeButtonAction);
        clearButton.addActionListener(this::clearButtonAction);
        labelHolder.setLayout(new BoxLayout(labelHolder, BoxLayout.PAGE_AXIS));

        add(labelCity1);
        add(textFieldCity1);
        add(labelCity2);
        add(textFieldCity2);
        add(computeButton);
        add(labelHolder);
    }

    private void computeAndUpdateUi() {
        String city1Name, city2Name;

        city1Name = textFieldCity1.getText();
        city2Name = textFieldCity2.getText();

        if(city1Name.isBlank() || city2Name.isBlank())
            return;

        CityDAO dao = new CityDAODatabase();
        City c1 = dao.findByName(city1Name);
        City c2 = dao.findByName(city2Name);

        if(c1 == null || c2 == null )
            return;

        imagePanel.addCityPair(c1, c2);
        double distance = Util.computeDistanceBetween(c1, c2);
        DecimalFormat df = new DecimalFormat("0.00");
        var lbl = new JLabel("<html>The distance between " + city1Name + " and " + city2Name +  " is <br>" + df.format(distance) + " km.</html>");
        resultLabels.add(lbl);
        labelHolder.add(lbl);
        labelHolder.updateUI();
        add(clearButton);
        updateUI();
        imagePanel.updateUI();
    }

    private void computeButtonAction(java.awt.event.ActionEvent e) {
        computeAndUpdateUi();
    }

    private void clearButtonAction(java.awt.event.ActionEvent e) {
        for (var lbl : resultLabels) {
            labelHolder.remove(lbl);
        }
        labelHolder.updateUI();
        resultLabels.clear();
        remove(clearButton);
        updateUI();
        imagePanel.clearPairs();
        imagePanel.updateUI();
    }

}
