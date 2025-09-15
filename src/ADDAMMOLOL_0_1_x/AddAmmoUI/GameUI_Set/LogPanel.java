package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogPanel extends JPanel implements initUI{
    JLabel currentRound,lastRoundResult,inforUpLabel,inforDownLabel ;
    public LogPanel(){
        initualizeUI();
    }
    @Override
    public void initualizeUI(){
        currentRound = new JLabel("Round: 0");
        currentRound.setForeground(Color.WHITE);
        lastRoundResult = new JLabel("Last Round: Just started.");
        lastRoundResult.setForeground(Color.WHITE);
        inforUpLabel = new JLabel(">> It's your turn. Now input action ID in the text field above ");
        inforUpLabel.setForeground(Color.WHITE);
        inforDownLabel = new JLabel(">>");
        inforDownLabel.setForeground(Color.WHITE);

        add(currentRound);
        add(lastRoundResult);
        add(inforUpLabel);
        add(inforDownLabel);
    }
}
