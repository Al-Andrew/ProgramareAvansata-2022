package pa;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 500, canvasHeight = 500;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        //init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }

    final void init() {
        this.rows = frame.gameData.getRows();
        this.cols = frame.gameData.getCols();
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);

    }

    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);

        // Horizontals
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            g.drawLine(x1, y1, x2, y2);
        }
        // Verticals
        for (int col = 0; col < cols; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padY + boardHeight;

            g.drawLine(x1, y1, x2, y2);
        }

        // Intersections
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;
                g.setColor(Color.LIGHT_GRAY);
                g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }

        if(frame.gameData == null)
            return;
        //Sticks
        for(var stick : frame.gameData.getSticks()) {
            g.setColor(Color.black);
            Stroke stickStroke = new BasicStroke(3.0f);
            g.setStroke(stickStroke);
            g.drawLine(padX + stick.startX * cellWidth, padY + stick.startY * cellHeight, padX + stick.endX * cellWidth, padY + stick.endY * cellHeight);
        }
        //Stones
        for(var stone : frame.gameData.getStones()) {
            g.setColor(stone.color == StoneColor.BLUE?Color.blue : Color.red);
            g.fillOval(stone.x * cellWidth + padX - 10, stone.y * cellHeight + padY - 10,
                    stoneSize, stoneSize);
        }

    }
}