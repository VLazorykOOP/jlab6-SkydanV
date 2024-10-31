import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintFrame extends JFrame {
    private Color currentColor = Color.BLACK;
    private int currentThickness = 1;
    private final ArrayList<Line> lines = new ArrayList<>();
    private Point startPoint = null;

    public PaintFrame() {
        setTitle("Малювання пером");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                for (Line line : lines) {
                    g2.setColor(line.color);
                    g2.setStroke(new BasicStroke(line.thickness));
                    g2.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (startPoint != null) {
                    lines.add(new Line(startPoint, e.getPoint(), currentColor, currentThickness));
                    startPoint = null;
                    drawingPanel.repaint();
                }
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint != null) {
                    lines.add(new Line(startPoint, e.getPoint(), currentColor, currentThickness));
                    startPoint = e.getPoint();
                    drawingPanel.repaint();
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();

        JMenu colorMenu = new JMenu("Колір");
        JMenuItem redItem = new JMenuItem("Червоний");
        JMenuItem greenItem = new JMenuItem("Зелений");
        JMenuItem blueItem = new JMenuItem("Синій");

        redItem.addActionListener(e -> currentColor = Color.RED);
        greenItem.addActionListener(e -> currentColor = Color.GREEN);
        blueItem.addActionListener(e -> currentColor = Color.BLUE);

        colorMenu.add(redItem);
        colorMenu.add(greenItem);
        colorMenu.add(blueItem);
        menuBar.add(colorMenu);

        JMenu thicknessMenu = new JMenu("Товщина");
        JMenuItem thinItem = new JMenuItem("Тонка");
        JMenuItem mediumItem = new JMenuItem("Середня");
        JMenuItem thickItem = new JMenuItem("Товста");

        thinItem.addActionListener(e -> currentThickness = 1);
        mediumItem.addActionListener(e -> currentThickness = 5);
        thickItem.addActionListener(e -> currentThickness = 10);

        thicknessMenu.add(thinItem);
        thicknessMenu.add(mediumItem);
        thicknessMenu.add(thickItem);
        menuBar.add(thicknessMenu);

        setJMenuBar(menuBar);
        add(drawingPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaintFrame frame = new PaintFrame();
            frame.setVisible(true);
        });
    }

 
    private static class Line {
        Point start, end;
        Color color;
        int thickness;

        Line(Point start, Point end, Color color, int thickness) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.thickness = thickness;
        }
    }
}
