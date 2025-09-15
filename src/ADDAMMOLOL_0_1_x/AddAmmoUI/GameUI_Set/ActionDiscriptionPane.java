package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ActionDiscriptionPane extends JScrollPane implements initUI {
    JTextArea discriptionArea;

    public ActionDiscriptionPane(){
        initualizeUI();
    }
    
    @Override
    public void initualizeUI(){
        discriptionArea = new JTextArea("null");
            discriptionArea.setFont(new Font("classic", Font.BOLD, 16));
            discriptionArea.setLineWrap(true);
            discriptionArea.setWrapStyleWord(true);
            discriptionArea.setEditable(false);
            discriptionArea.setBackground(Color.BLACK);
            discriptionArea.setForeground(Color.WHITE);

        setViewportView(discriptionArea);
        //actionDiscriptionPane.add(discriptionAre
    }
}
