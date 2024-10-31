import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CustomException extends ArithmeticException {
    public CustomException(String message) {
        super(message);
    }
}

public class MatrixVectorGUI extends JFrame {
    private JTextField sizeField;
    private JTable matrixTable;
    private JTextArea outputArea;

    public MatrixVectorGUI() {
        setTitle("Matrix Vector Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("Enter matrix size n (n <= 15):"));
        sizeField = new JTextField();
        panel.add(sizeField);

        JButton createTableButton = new JButton("Create Matrix Table");
        panel.add(createTableButton);
        createTableButton.addActionListener(e -> createMatrixTable());

        JButton calculateButton = new JButton("Calculate Vector Y");
        panel.add(calculateButton);
        calculateButton.addActionListener(e -> calculateVector());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        add(panel);
    }

    private void createMatrixTable() {
        try {
       
            int n = Integer.parseInt(sizeField.getText().trim());
            System.out.println("Matrix size entered: " + n);

            if (n <= 0 || n > 15) throw new CustomException("Matrix size should be 1-15.");

          
            Object[][] tableData = new Object[n][n];
            String[] columnNames = new String[n];
            for (int i = 0; i < n; i++) columnNames[i] = "Col " + i;

            matrixTable = new JTable(tableData, columnNames);
            JOptionPane.showMessageDialog(this, new JScrollPane(matrixTable), "Matrix Data Entry", JOptionPane.PLAIN_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid matrix size format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (CustomException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Custom Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateVector() {
        try {
            int n = Integer.parseInt(sizeField.getText().trim());
            double[][] X = new double[n][n];
            double[] Y = new double[n];

          
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    String value = (String) matrixTable.getValueAt(i, j);
                    if (value == null || value.isEmpty()) {
                        throw new NumberFormatException("Matrix data is incomplete.");
                    }
                    X[i][j] = Double.parseDouble(value);
                }
            }

            
            for (int i = 0; i < n; i++) {
                double sum = 0;
                boolean foundNegative = false;

                for (int j = 0; j < n; j++) {
                    sum += Math.abs(X[i][j]);
                    if (X[i][j] < 0) {
                        foundNegative = true;
                        break;
                    }
                }

                if (foundNegative) {
                    Y[i] = sum;
                } else {
                    if (sum == 0) throw new CustomException("Sum of elements in row " + i + " is zero.");
                    Y[i] = -1;
                }
            }

            outputArea.setText("Vector Y:\n");
            for (int i = 0; i < n; i++) {
                outputArea.append("Y[" + i + "] = " + Y[i] + "\n");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid matrix element format. Please enter numeric values only.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (CustomException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Custom Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatrixVectorGUI gui = new MatrixVectorGUI();
            gui.setVisible(true);
        });
    }
}
