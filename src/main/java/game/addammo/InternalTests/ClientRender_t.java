package game.addammo.InternalTests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import game.addammo.AddAmmoMain.Game;
import game.addammo.AddAmmoUI.FrameSize;
import game.addammo.AddAmmoUI.game.ActionDiscriptionPane;
import game.addammo.AddAmmoUI.game.LogPanel;
import game.addammo.AddAmmoUI.game.PlayerStatsPanel;
import game.addammo.AddAmmoUI.game.SelectTablePanel;

public class ClientRender_t extends JPanel implements FrameSize, ActionListener {
    
    ActionDiscriptionPane actionDiscriptionPane;
    LogPanel logPanel;
    PlayerStatsPanel playerStatsPanel;
    SelectTablePanel selectTablePanel;
    JPanel secendPanel;
    
    public ClientRender_t() {
        initUI();
        selectTablePanel.setInputListener(this);

    }
    
    
    
    private void initUI() {
        logPanel = new LogPanel();
        playerStatsPanel = new PlayerStatsPanel();
        selectTablePanel = new SelectTablePanel();
        actionDiscriptionPane = new ActionDiscriptionPane();

        selectTablePanel.setTextChangeListener(text -> updateActionDiscription(text));
        Game.isGameOver = false;
        initFramework();
    }

    // 初始化页面布局，在initUI()中
    private void initFramework() {
        setLayout(new BorderLayout());
        // setBorder(new LineBorder(Color.BLUE, 5));

        secendPanel = new JPanel();

        secendPanel.setPreferredSize(new Dimension(FrameSize.P2_SIZE_W, FrameSize.P2_SIZE_H));
        secendPanel.setBackground(Color.BLACK);
        secendPanel.setLayout(new BorderLayout());

        actionDiscriptionPane.setPreferredSize(new Dimension(FrameSize.ADP_SIZE_W, FrameSize.ADP_SIZE_H));
        actionDiscriptionPane.setBorder(new LineBorder(Color.BLUE, 5));
        actionDiscriptionPane.setBackground(Color.BLACK);

        secendPanel.add(actionDiscriptionPane, BorderLayout.EAST);

        playerStatsPanel.setPreferredSize(new Dimension(FrameSize.PSP_SIZE_W, FrameSize.PSP_SIZE_H));// -5同理
        playerStatsPanel.setBackground(Color.BLACK);
        playerStatsPanel.setLayout(new GridLayout(4, 4, 5, 5));
        playerStatsPanel.setBorder(new LineBorder(Color.BLUE, 5));

        secendPanel.add(playerStatsPanel, BorderLayout.WEST);
        add(secendPanel, BorderLayout.NORTH);

        selectTablePanel.setPreferredSize(new Dimension(FrameSize.ST_SIZE_W, FrameSize.ST_SIZE_H));
        selectTablePanel.setBackground(Color.GRAY);
        selectTablePanel.setBorder(new LineBorder(Color.BLUE, 5));
        add(selectTablePanel, BorderLayout.CENTER);

        logPanel.setPreferredSize(new Dimension(FrameSize.LP_SIZE_W, FrameSize.LP_SIZE_H));
        logPanel.setBackground(Color.BLACK);
        logPanel.setBorder(new LineBorder(Color.WHITE, 5));
        logPanel.setLayout(new GridLayout(4, 1));
        add(logPanel, BorderLayout.SOUTH);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test");
        while (true) {
            String input = selectTablePanel.getInput().trim();
            try {

                if (input.isEmpty()) {
                    System.out.println("please input the action ID");
                    logPanel.updateLog(LogPanel.AT_FOURTH, "Please input the action ID ):<", LogPanel.ERROR_MSG);
                    break;
                }

                int actionID = Integer.parseInt(input);
                System.out.println(actionID);
                /*int preSelections = player.actionsSelecting(actionID ,player, player.getPlayerStats());
                if (preSelections == -1) {//返回-1代表子弹不足
                    String msg = "Failed to active the selected action: "
                            + ActionX.searchActions(actionID).getActionNameString() + " ,Because you have "
                            + player.getAmmoLeft() +
                            " ammo out of " + ActionX.searchActions(actionID).getAmmoCost() + " ammo requires.";
                    System.out.println(msg);
                    logPanel.updateLog(LogPanel.AT_FOURTH, msg, LogPanel.ERROR_MSG);
                    break;
                } else if( preSelections == -2) {//代表没有发射架强行发射导弹
                    String msg = "Sorry, but u need a the launcher before using the missile :(";
                    System.out.println(msg);
                    logPanel.updateLog(LogPanel.AT_FOURTH, msg, LogPanel.ERROR_MSG);
                    break;

                } else if( preSelections == -3) {//代表选择的动作不存在
                    System.out.println("The action is NOT exist yet, try another one");
                    logPanel.updateLog(LogPanel.AT_FOURTH, "/!\\ The action is NOT exist yet, try another one",
                        LogPanel.ERROR_MSG);
                    break;
                
                } else {

                    //mainRound(actionID);
                    //playerStatsPanel.update(player, enemy);
                    logPanel.updateLog(LogPanel.AT_FOURTH, "You just selected -> " + ActionX.getActionName(actionID),
                            LogPanel.INFO_MSG);
                    break;
                } // 用于子弹剩余判断，顺便提前诱发空指针
                */

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! ");
                logPanel.updateLog(LogPanel.AT_FOURTH, "/!\\ Invaild input! -> \"" + input + " \"", LogPanel.ERROR_MSG);
                break;
            } catch (Exception ex) {
                System.out.println("Nice job! How'd f you find such the input-related bug? Report it to me asap!!!");
                logPanel.updateLog(LogPanel.AT_FOURTH,
                        "/!\\ Nice job! How'd f you find such the input-related bug? Report it to me asap!!!",
                        LogPanel.ERROR_MSG);
                break;
            } finally {
                selectTablePanel.clearInput();
            }
        }

    }

    public void updateActionDiscription(String text) {
        if (text == null || text.trim().isEmpty()) {
            actionDiscriptionPane.setDiscription("null");
            return;
        }
        try {
            int ID = Integer.parseInt(text.trim());
            actionDiscriptionPane.updateDiscription(ID);
        } catch (Exception ex) {
            actionDiscriptionPane.setDiscription("null");
        }
    }
    
}
