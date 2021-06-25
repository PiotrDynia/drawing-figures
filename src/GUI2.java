import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class GUI2 extends JFrame {
    private DrawPanel shapePanel;
    private List<JButton> buttonList;
    private JLabel valueLabel;

    public GUI2() {
        this.setLayout(new GridLayout(1, 2, 10, 10));
        this.setTitle("GUI Projekt 2 - zadanie 2");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);

        shapePanel = new DrawPanel();
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(2, 0, 10, 10));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        buttonList = Arrays.asList(
                new JButton("Rectangle"),
                new JButton("Oval"),
                new JButton("Triangle")
        );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 0, 5, 5));
        buttonList.get(0).setBackground(Color.CYAN);

        buttonList
                .forEach(button -> button.addActionListener(e -> {
                    shapePanel.setShape(button.getText());
                    button.setBackground(Color.CYAN);
                }));

        for (JButton jButton : buttonList) {
            buttonPanel.add(jButton);
        }

        valueLabel = new JLabel();
        valueLabel.setText(shapePanel.getValue() + "px\u00b2");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        textPanel.add(valueLabel, BorderLayout.CENTER);

        editPanel.add(textPanel);
        editPanel.add(buttonPanel);
        this.getContentPane().add(shapePanel);
        this.getContentPane().add(editPanel);
        this.pack();
        this.setVisible(true);
    }


    class DrawPanel extends JPanel {
        private int value;
        private int x1, y1, x2, y2;
        private String shape;

        public DrawPanel() {
            super();
            this.addMouseListener(new DrawPanelMouseListener());
            this.addMouseMotionListener(new DrawPanelMouseListener());
            this.setSize(600, 300);
            Color color = Color.red;
            shape = "Rectangle";
            value = 0;
        }

        public void setShape(String shape) {
            this.shape = shape;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.RED);

            int px = Math.min(x1, x2);
            int py = Math.min(y1, y2);
            int pw = Math.abs(x1 - x2);
            int ph = Math.abs(y1 - y2);
            int[] xs = {x1, x2, pw / 2};
            int[] ys = {y1, y2, ph / 2};
            switch (shape) {
                case "Rectangle" -> {
                    g2d.drawRect(px, py, pw, ph);
                    buttonList.get(1).setBackground(null);
                    buttonList.get(2).setBackground(null);
                    valueLabel.setText(pw * ph + "px\u00b2");
                    repaint();
                }
                case "Oval" -> {
                    g2d.drawOval(px, py, pw, ph);
                    buttonList.get(0).setBackground(null);
                    buttonList.get(2).setBackground(null);
                    valueLabel.setText((int) ((pw / 2) * Math.pow(Math.PI, 2)) + "px\u00b2");
                    repaint();
                }
                case "Triangle" -> {
                    g2d.drawPolygon(xs, ys, 3);
                    buttonList.get(1).setBackground(null);
                    buttonList.get(0).setBackground(null);
                    valueLabel.setText((int) (0.5 * pw * ph) + "px\u00b2");
                    repaint();
                }
            }
        }
        public int getValue() {
            return value;
        }

        class DrawPanelMouseListener extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                repaint();
            }
        }
    }
}
