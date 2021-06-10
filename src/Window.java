import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Window extends JFrame implements MouseWheelListener {

    static final int[] WN_SIZE = {1200, 700};
    static boolean sorting = false;

    static DataManager manager = new DataManager();
    static DrawCanvas canvas = new DrawCanvas();
    ButtonPanel btn_pnl = new ButtonPanel();

    public Window() {
        super();
        // Window setup
        setSize(new Dimension(WN_SIZE[0], WN_SIZE[1]));
        setTitle("Sorting algorithms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Layout
        setLayout(new GridLayout(2, 1));
        // Canvas
        add(canvas);
        // Button panel
        add(btn_pnl);
        // Mouse wheel listener
        getContentPane().addMouseWheelListener(this);
        // Finalise setup
        setVisible(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (DataManager.step_delay + e.getWheelRotation() > 0) {
            DataManager.step_delay += e.getWheelRotation();
        } else {
            DataManager.step_delay = 1;
        }
        btn_pnl.sleep_lbl.setText("Sleep time: " + DataManager.step_delay + "ms");
    }

    private static class ButtonPanel extends JPanel implements ActionListener, DocumentListener {

        static String[] text = {
                "Shuffle data",
                "Bubble sort",
                "Insertion sort",
                "Merge sort"
        };
        JButton[] buttons = new JButton[text.length];
        JLabel sleep_lbl = new JLabel("Sleep time: 1ms");
        JTextField size_field = new JTextField();

        public ButtonPanel() {
            super();
            setLayout(new GridBagLayout());
            BtnConstraints c = new BtnConstraints();
            // Buttons
            for (int i = 0; i < text.length; i++) {
                buttons[i] = new JButton(text[i]);
                buttons[i].addActionListener(this);
                c.button(i + 1);
                add(buttons[i], c);
            }
            // Sleep time label
            c.sleep_lbl();
            add(sleep_lbl, c);
            // Data set size text field
            size_field.setText(String.valueOf(DataManager.set_length));
            size_field.getDocument().addDocumentListener(this);
            c.size_field();
            add(size_field, c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!sorting) {
                sorting = true;
                if (e.getSource() == buttons[0]) {
                    // Shuffle data set
                    manager.shuffle();
                    canvas.repaint();
                } else if (e.getSource() == buttons[1]) {
                    // Bubble sort
                    manager.bubble_sort();
                } else if (e.getSource() == buttons[2]) {
                    // Insertion sort
                    manager.insertion_sort();
                } else if (e.getSource() == buttons[3]) {
                    // Merge sort
                    manager.merge_sort();
                }
                sorting = false;
            }
        }

        public void update() {
            if (!sorting) {
                try {
                    int potential_size = Integer.parseInt(size_field.getText());
                    DataManager.set_length = Math.max(potential_size, 1);
                    manager = new DataManager();
                    canvas.repaint();
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            update();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            update();
        }
    }

    private static class BtnConstraints extends GridBagConstraints {

        public void button(int number) {
            gridx = (number % 2) + 1;
            gridy = Math.floorDiv(number - 1, 2) + 1;
            gridwidth = 1;
            gridheight = 1;
            weightx = 0.5;
            weighty = 1;
            anchor = LINE_START;
            fill = BOTH;
            insets = new Insets(0, 0, 0, 0);
        }

        public void sleep_lbl() {
            gridx = 3;
            gridy = 1;
            gridwidth = 1;
            gridheight = 1;
            weightx = 1;
            weighty = 0;
            anchor = PAGE_START;
            fill = BOTH;
            insets = new Insets(0, 0, 0, 0);
        }

        public void size_field() {
            gridx = 3;
            gridy = 2;
            gridwidth = 1;
            gridheight = 1;
            weightx = 1;
            weighty = 0;
            anchor = PAGE_START;
            fill = BOTH;
            insets = new Insets(0, 0, 0, 0);
        }
    }
}
