package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

public final class ActionDiscriptionPane extends JScrollPane implements initUI {
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

    public void updateDiscription(int ID){
        if(ActionsLib.searchActions(ID) != null){
            discriptionArea.setText("Action ID: "+ID + "\n"+
                                    "Action name: " + ActionsLib.getActionName(ID) + "\n"+
                                    "Action discription: "+ ActionsLib.searchActionDescription(ID));              
        }else{
            discriptionArea.setText("null");
        }
    }
    public void setDiscription(String text){
        discriptionArea.setText(text);
    }


}
