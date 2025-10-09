package ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Game;
import ADDAMMOLOL_0_1_x.AddAmmoUI.initUI;

public final class LogPanel extends JPanel implements initUI{
    JLabel currentRound,lastRoundResult,infoUpLabel,infoDownLabel ;
    public LogPanel(){
        initualizeUI();
    }
    @Override
    public void initualizeUI(){
        currentRound = new JLabel("Round: "+ Game.round);
        currentRound.setForeground(Color.WHITE);
        lastRoundResult = new JLabel("Last Round: Just started.");
        lastRoundResult.setForeground(Color.WHITE);
        infoUpLabel = new JLabel(">> It's your turn. Now input action ID in the text field above ");
        infoUpLabel.setForeground(Color.WHITE);
        infoDownLabel = new JLabel(">>");
        infoDownLabel.setForeground(Color.WHITE);

        add(currentRound);
        add(lastRoundResult);
        add(infoUpLabel);
        add(infoDownLabel);
    }
    
    
    public static final int AT_FIRST = 1;
    public static final int AT_SECEND = 2;
    public static final int AT_THIRD = 3;
    public static final int AT_FOURTH = 4;

    public void updateLog(int infoLoc ,String updateString, int infoType){
        switch(infoLoc){
            case AT_FIRST:{
                updateLog_First(updateString,infoType);
                break; 
            }
            case AT_SECEND:{
                updateLog_Secend(updateString, infoType);
                break;
            }
            case AT_THIRD:{
                updateLog_Third(updateString,infoType);
                break;
            }
            case AT_FOURTH:{
                updateLog_Fourth(updateString,infoType);
                break;
            }
            default:

        }

    }

    public static final int ERROR_MSG = 10;
    public static final int NOTIFY_MSG = 11;
    public static final int INFO_MSG = 12;

    public static final int FAILED_MSG = 20;
    public static final int SUCCESSSED_MSG = 21;

    private void updateLog_First(String updateString, int infoType){
        currentRound.setText(updateString);
        currentRound.setForeground(Color.WHITE);
    }
    private void updateLog_Secend(String updateString, int infoType){
        switch(infoType){
            case ERROR_MSG:{
                lastRoundResult.setForeground(Color.RED);
                //updateString = "WARNING: An internal exception occurred!";
                break;
            }
            case NOTIFY_MSG:{
                lastRoundResult.setForeground(Color.GREEN);
                break;
            }
            case INFO_MSG:{
                lastRoundResult.setForeground(Color.WHITE);
                break;
            }
        }
        lastRoundResult.setText("<LAST ROUND>> "+ updateString);
    }
    private void updateLog_Third(String updateString, int infoType){
        switch (infoType){
            case ERROR_MSG:{
                infoUpLabel.setForeground(Color.RED);
                break;
            }
            case NOTIFY_MSG:{
                infoUpLabel.setForeground(Color.YELLOW);
                break;
            }
            case INFO_MSG:{
                infoUpLabel.setForeground(Color.WHITE);
                break;
            }
            case FAILED_MSG:{
                infoUpLabel.setForeground(Color.ORANGE);
                break;
            }
            case SUCCESSSED_MSG:{
                infoUpLabel.setForeground(Color.GREEN);
            }

        }
        infoUpLabel.setText(">>"+updateString);

    }
    private void updateLog_Fourth(String updateString, int infoType){
         switch (infoType){
            case ERROR_MSG:{
                infoDownLabel.setForeground(Color.RED);
                break;
            }
            case NOTIFY_MSG:{
                infoDownLabel.setForeground(Color.YELLOW);
                break;
            }
            case INFO_MSG:{
                infoDownLabel.setForeground(Color.WHITE);
                break;
            }
            case FAILED_MSG:{
                infoDownLabel.setForeground(Color.ORANGE);
                break;
            }
            case SUCCESSSED_MSG:{
                infoDownLabel.setForeground(Color.GREEN);
            }

        }
        infoDownLabel.setText(">>" +updateString);
    }
}
