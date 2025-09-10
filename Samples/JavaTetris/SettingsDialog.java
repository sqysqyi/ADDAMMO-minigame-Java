import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {
    private final JPanel keyConfigPanel;
    private final JButton saveButton,defaultButton;
    private JLabel label;
    private JTextField keyField;

    public SettingsDialog(JFrame owner) {
        super(owner, "Key Settings", true);
        setLayout(new BorderLayout());

        keyConfigPanel = new JPanel(new GridLayout(6, 2, 10, 10));//6行2列的文字布局
        keyConfigPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//页边距，不要也行

        String[] actions = {"left", "right", "down", "rotate", "pause", "settings"};
        for (String action : actions) {//遍历actions集里的元素，赋值给action
            label = new JLabel("Key for " + action + ":");
            keyField = new JTextField(KeyEvent.getKeyText(KeySetting.getKey(action)));
            keyField.setEditable(false);

            keyField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    keyField.setText(KeyEvent.getKeyText(e.getKeyCode()));
                    KeySetting.setKey(action, e.getKeyCode());
                }
            });

            keyField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent f){
                    label.setText("> Key for "+action+":");

                }
                @Override
                public void focusLost(FocusEvent f){
                    label.setText("Key for "+action+":");
                }
            });

            keyConfigPanel.add(label);
            keyConfigPanel.add(keyField);
        }

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            KeySetting.saveSettings();
            dispose();
        });
        defaultButton = new JButton("default");
        defaultButton.addActionListener(e -> {
            KeySetting.setDefaultKey();
            int comfirm = JOptionPane.showConfirmDialog(this, 
            "All key-settings are reset.",
            "Default reset",
            JOptionPane.OK_OPTION,
            JOptionPane.INFORMATION_MESSAGE);

            if(comfirm == JOptionPane.YES_OPTION){
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(defaultButton);

        add(keyConfigPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }
}
