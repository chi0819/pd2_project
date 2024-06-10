import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowSizeDetector extends JFrame {    
    
    public WindowSizeDetector() {

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = getSize();
                System.out.println("Window resized to: " + newSize.width + " x " + newSize.height);
            }
        });
    }
}