package simpleTest;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class simpleTest{
    private JLabel[] labels = new JLabel[16];

    public static void main(String[] args) {
        test();
        SwingUtilities.invokeLater(() -> new simpleTest().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("数字高亮示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // 输入框（带占位符提示）
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(Color.GRAY);
                    g.drawString("请输入 1-16 的数字", 5,
                            getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
                }
            }
        };
        frame.add(textField, BorderLayout.NORTH);

        // 表格
        JPanel gridPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        for (int i = 0; i < 16; i++) {
            labels[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            labels[i].setFont(new Font("SansSerif", Font.BOLD, 20));
            gridPanel.add(labels[i]);
        }
        frame.add(gridPanel, BorderLayout.CENTER);

        // 实时监听输入框内容变化
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateHighlight() {
                String text = textField.getText().trim();
                int num = -1;
                if (!text.isEmpty()) {
                    try {
                        num = Integer.parseInt(text);
                    } catch (NumberFormatException ignored) {}
                }
                for (int i = 0; i < labels.length; i++) {
                    labels[i].setForeground((i + 1 == num) ? Color.GREEN : Color.BLACK);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updateHighlight(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateHighlight(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateHighlight(); }
        });

        frame.setVisible(true);
    }

    public static void test(){
        String strings = "aaa";
        switch (strings) {
            case "aaa":
                System.out.println("aaa");
                break;
        
            default:
                break;
        }
    }
}
