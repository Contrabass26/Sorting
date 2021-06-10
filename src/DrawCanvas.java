import javax.swing.*;
import java.awt.*;

public class DrawCanvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Get bar width and height in pixels
        // Could change is window is resized
        int bar_width = (getWidth() - 20) / DataManager.set_length; // Buffer of 10px each side = 20
        int bar_height_scale = (getHeight() - 20) / DataManager.set_length; // Buffer of 10px each side = 20
        // Draw bars
        for (int i = 0; i < DataManager.set_length; i++) {
            int x = 10 + i * bar_width;
            int y = Window.manager.get(i) * bar_height_scale;
            g.fillRect(x, getHeight() - y + 10, bar_width, y);
        }
    }
}
