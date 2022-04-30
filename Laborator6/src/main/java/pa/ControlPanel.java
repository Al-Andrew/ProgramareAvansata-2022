package pa;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton imgBtn = new JButton("Save image");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        //setLayout(new GridLayout(1, 4));
        exitBtn.addActionListener(this::exitGame);
        add(exitBtn);
        saveBtn.addActionListener(this::saveGameJSON);
        add(saveBtn);
        loadBtn.addActionListener(this::loadGameJSON);
        add(loadBtn);
        imgBtn.addActionListener(this::saveGameImage);
        add(imgBtn);
    }

    private void exitGame(java.awt.event.ActionEvent e) {
        frame.dispose();
    }
    private void saveGameImage(java.awt.event.ActionEvent e) {
        BufferedImage imagebuf = null;
        try {
            imagebuf = new Robot().createScreenCapture(frame.drawingPanel.getBounds());
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        Graphics2D graphics2D = imagebuf.createGraphics();
        frame.drawingPanel.paint(graphics2D);
        try {
            FileDialog dialog = new FileDialog(frame, "Select a location for save", FileDialog.SAVE);
            dialog.show();
            ImageIO.write(imagebuf,"png", new File(dialog.getFile()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveGameJSON(java.awt.event.ActionEvent e) {
        FileDialog dialog = new FileDialog(frame, "Select a location for save", FileDialog.SAVE);
        dialog.show();
        try{
            FileWriter writer = new FileWriter(dialog.getFile());
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
            mapper.writeValue(writer, frame.gameData);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void loadGameJSON(java.awt.event.ActionEvent e) {
        FileDialog dialog = new FileDialog(frame, "Select a location for save", FileDialog.LOAD);
        dialog.show();
        try{
            FileReader reader = new FileReader(dialog.getFile());
            ObjectMapper mapper = new ObjectMapper();

            var gameData = mapper.readValue(reader, SticksAndStonesGameData.class);
            gameData.setFrame(frame);
            frame.configPanel.resetGame(gameData);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
