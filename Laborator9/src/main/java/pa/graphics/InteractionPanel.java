package pa.graphics;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import pa.entity.City;
import pa.repository.JPACityRepository;
import pa.repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InteractionPanel extends JPanel {
    JTextField selectCity = new JTextField();
    JLabel labelSelectCity = new JLabel("City Name:");
    JButton highlightButton = new JButton("Highlight");

    JButton solveButton = new JButton("Solve constraints");

    private final HighlightData highlightData;
    private final ImagePanel imagePanel;

    public InteractionPanel(ImagePanel imagePanel, HighlightData highlightData){
        this.imagePanel = imagePanel;
        this.highlightData = highlightData;
        setPreferredSize(new Dimension(300, 800));
        selectCity.setPreferredSize(new Dimension(250, 40));
        highlightButton.addActionListener(this::highlightButtonAction);
        solveButton.addActionListener(this::solveConstraintsAction);

        add(labelSelectCity);
        add(selectCity);
        add(highlightButton);
        add(solveButton);
    }

    private void highlightButtonAction(java.awt.event.ActionEvent e) {
        String name;

        name = selectCity.getText();

        if(name.isBlank())
            return;

        JPACityRepository cities = new JPACityRepository();
        List selected = cities.findNamed(name);

        for(var cty : selected) {
            highlightData.addToCityList((City)cty);
        }
        highlightData.setSelected((City)selected.get(0));
        updateUI();
        imagePanel.updateUI();
    }

    private void solveConstraintsAction(java.awt.event.ActionEvent e) {

        updateUI();
        imagePanel.updateUI();
    }


    private void clearButtonAction(java.awt.event.ActionEvent e) {
        //for (var lbl : resultLabels) {
        //    labelHolder.remove(lbl);
        //}
        //labelHolder.updateUI();
        //resultLabels.clear();
        //remove(clearButton);
        updateUI();
        imagePanel.updateUI();
    }

}
