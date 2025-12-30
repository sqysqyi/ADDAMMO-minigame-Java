package ADDAMMOLOL_0_1_x.AddAmmoMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.*;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.*;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy.GraspRequest;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players.PlayerStats;
import ADDAMMOLOL_0_1_x.AddAmmoUI.FrameSize;
import ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.Game.*;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_RNGenerator;
import ADDAMMOLOL_0_1_x.AddAmmoUtil.AM_Recorder;
import Samples.ADDAMMOLOL_0_0_1d.mainPlayer;

//->AddAmmoMain.Game

public class Game extends JPanel implements FrameSize, ActionListener {
    // UI渲染的参数声明；
    // 现已移至GameUI_Set文件夹中；
    ActionDiscriptionPane actionDiscriptionPane;
    LogPanel logPanel;
    PlayerStatsPanel playerStatsPanel;
    SelectTablePanel selectTablePanel;
    JPanel secendPanel;
    //DialogPanel restartNew;

    // 逻辑参数声明
    private Actions playerActions, enemyActions, enemy2Actions, enemy3Actions;
    private Players player;
    private Enemy enemy, enemy2, enemy3;
    private PlayerStats playerStats, enemyStats, enemy2Stats, enemy3Stats;
    private int bonusDamage;

    private int MAX_HP = Start.setMaxHP == 0 ? 3 : Start.setMaxHP;
    private int DEFAULT_AMMO = Start.setDefaultAmmo == 0 ? 1 : Start.setDefaultAmmo;

    public static final int NUMBER_OF_ENEMIES = 1;

    public static int round = 1;
    public static boolean isGameOver = false;

    public Game() throws Exception {

        /*******************************************************
         * 
         * gamebar
         * ----------------------------------------------------
         * 
         * player stats | action discription
         * 
         * ----------------------------------------------------
         * 
         * selece table
         * 
         * ----------------------------------------------------
         * 
         * game log
         * 
         * 
         */
        initPlayer(NUMBER_OF_ENEMIES + 1);
        initGame();
        initUI();

        selectTablePanel.setInputListener(this);

    }

    public void setSubCompVisible(boolean visible) {

        // actionDiscriptionPane.setVisible(visible);
        logPanel.setVisible(visible);
        // playerStatsPanel.setVisible(visible);
        selectTablePanel.setVisible(visible);
        secendPanel.setVisible(visible);
    }

    // 游戏过程中的胜负逻辑判断
    private void initGame() {

        bonusDamage = 0;
        isGameOver = false;
        round = 1;
    }

    private void resetStats() {
        initGame();// 只是复用方法，不代表包含关系
    }

    // 初始化玩家属性
    private void initPlayer(int totalPlayer) throws Exception {

        if (MAX_HP == 0)
            MAX_HP = 3;
        if (DEFAULT_AMMO == 0)
            DEFAULT_AMMO = 1;

        //playerStats = new PlayerStats();
        player = new Player(MAX_HP,
                DEFAULT_AMMO,
                playerActions,
                "player");

        switch (totalPlayer) {
            case 4:
                enemy3 = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemy3Actions,
                        "enemy3");

            case 3:
                enemy2 = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemy2Actions,
                        "enemy2");
                throw new Exception("unsupported player numbers, only \"2\" is ok...");
            case 2:
                enemy = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemyActions,
                        "enemy");
                break;

            default:
                throw new Exception("invalid player numbers");
        }
    }

    // 初始化UI界面
    private void initUI() {
        logPanel = new LogPanel();
        playerStatsPanel = new PlayerStatsPanel();
        selectTablePanel = new SelectTablePanel();
        actionDiscriptionPane = new ActionDiscriptionPane();

        selectTablePanel.setTextChangeListener(text -> updateActionDiscription(text));
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

    // above are UI-related initualizations

    /**
     * the CORE method of this game, which only run in a 1v1 dual
     * 
     * @param playerSelectedActionID accept player input action ID
     */
    private void duoPlayerRound(int playerSelectedActionID) {
        {
            player.getPlayerStats().resetDmgDefThief();
            enemy.getPlayerStats().resetDmgDefThief();
            bonusDamage = 0;
        }
        //System.out.println(bonusDamage);
        logPanel.updateLog(
                LogPanel.AT_FIRST,
                "Round: " + ++round,
                0);
        logPanel.updateLog(
                LogPanel.AT_SECEND,
                "",
                LogPanel.INFO_MSG);
        // playerSelectedActionID = 101 ;//
        // player.actionsSelecting(player.getHP(),player.getAmmoLeft(),playerGameStats);
        playerActions = player.selectActions(playerSelectedActionID);
        player.setPlayerActions(playerActions);// 玩家选择的action

        int enemySelectedActionID = enemy.actionsSelecting(player.getHP(), player.getAmmoLeft(), playerStats);
        enemyActions = enemy.selectActions(enemySelectedActionID);
        enemy.setPlayerActions(enemyActions);// enemy电脑自动选择action

        System.out.println("The enemy selected: " + enemy.getPlayerActions().getActionNameString() + ", cost " +
                enemy.getPlayerActions().getAmmoCost() + " ammo");
        System.out.println("You've selected: " + player.getPlayerActions().getActionNameString() + ", cost " +
                player.getPlayerActions().getAmmoCost() + " ammo");

        // actions属性转移。
        player.generalActivating();
        enemy.generalActivating();

        Players roundWinner = Players.Comparing(player, enemy);
        Players roundLoser;// 参比对象主要还是以player而不是enemy类

        if (roundWinner == null) {// 平局检查
            roundLoser = null;
            System.out.println("nothing happened xD");
            logPanel.updateLog(LogPanel.AT_SECEND, "Nothing happened, or not? ", LogPanel.NOTIFY_MSG);

            player.checkHealing(0);
            enemy.checkHealing(0);

            enemy.grasping(new GraspRequest() {
                @Override
                public void graspTo(AM_Recorder recorder){
                    recorder.add(recorder.newRecord(
                        player.getPlayerActions(),
                        enemy.getPlayerActions(),
                        resultResolve()
                    ));

                };
                @Override
                public int resultResolve(){
                    return 0;
                }
            });

        } else {
            roundLoser = roundWinner == player ? enemy : player;
            roundWinner.winActivating();

            /* 以下是伤害计算 */
            int damageDealt = roundWinner.damageDealtTo(roundLoser);

            if (roundWinner.getPlayerStats().isPolice()) {// 如果警察获胜
                //原平局检查已被移至Players类中，
                if (roundWinner == player) {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "You as the \"" + player.getPlayerActions().getActionNameString() +
                                    "\" successed in stop the enemy as the \""
                                    + enemy.getPlayerActions().getActionNameString() + "\"and dealt" +
                                    damageDealt + " damage",
                            LogPanel.INFO_MSG);
                } else {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "You unfortunately as the \"" + player.getPlayerActions().getActionNameString() +
                                    "\" and arrested by the enemy as the \""
                                    + enemy.getPlayerActions().getActionNameString() + "\" and received"
                                    + damageDealt + " damage",
                            LogPanel.NOTIFY_MSG);
                }

            } else if (roundWinner.getPlayerStats().isThief()) {// 如果小偷获胜

                if (roundLoser.getPlayerActions().getID() == 101) {// 如果对面是加子弹

                    roundWinner.setAmmoLeft(
                            roundWinner.getAmmoLeft() + roundLoser.getAmmoLeft());// 偷走对面所有子弹
                    roundLoser.setAmmoLeft(0);
                    
                    if (roundWinner == player) {
                        logPanel.updateLog(
                                LogPanel.AT_SECEND,
                                "You just stole all the enemy's ammo!",
                                LogPanel.INFO_MSG);
                    } else {
                        logPanel.updateLog(
                                LogPanel.AT_SECEND,
                                "Sadly you lost all your ammo :(",
                                damageDealt);
                    }

                } else if (roundLoser.getPlayerActions().isStealable()) {// 又如果对面的东西可偷
                    roundWinner.setPlayerActions(roundLoser.getPlayerActions());// 自己的action替换为对面的
                    roundWinner.winActivating();// 再执行一次动作内容
                    roundLoser.ammoRetureTo(roundWinner);// 返还实行动作的子弹，因为东西是对面那里偷来的
                    roundWinner.generalActivating();// 同理
                    damageDealt = roundWinner.damageDealtTo(roundLoser);// 用偷来的东西对被偷者造成伤害

                    if (roundWinner == player) {
                        logPanel.updateLog(
                                LogPanel.AT_SECEND,
                                "You just stole the enemy's \""
                                        + roundLoser.getPlayerActions().getActionNameString() + "\" action.",
                                LogPanel.INFO_MSG);
                    } else {
                        logPanel.updateLog(
                                LogPanel.AT_SECEND,
                                "Your \"" + damageDealt + "\" was stolen",
                                LogPanel.INFO_MSG);
                    }

                } else if (roundLoser.getPlayerActions().isStealable() == false) {// 如果不可偷

                    if (roundLoser.getPlayerStats().isPolice()) {// 不管对面是警察，子弹照偷不误
                        roundWinner
                                .setAmmoLeft(roundWinner.getAmmoLeft() + roundLoser.getAmmoLeft());// 偷走对面所有子弹
                        roundLoser.setAmmoLeft(0);

                        if (roundWinner == player) {
                            logPanel.updateLog(
                                    LogPanel.AT_SECEND,
                                    "You just robbed the enemy's all left ammo",
                                    LogPanel.INFO_MSG);
                        } else {
                            logPanel.updateLog(
                                    LogPanel.AT_SECEND,
                                    "Your lost all left ammo",
                                    LogPanel.INFO_MSG);
                        }
                    } else {
                        logPanel.updateLog(
                                LogPanel.AT_SECEND,
                                "Seems nothing got stolen",
                                LogPanel.INFO_MSG);
                    }
                }
            } else {
                if (roundWinner == player) {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "You just dealt " + damageDealt + " damage",
                            LogPanel.INFO_MSG);
                    logPanel.updateLog(
                            LogPanel.AT_SECEND,
                            "You just dealt " + damageDealt + " damage",
                            LogPanel.INFO_MSG);
                } else {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "You just received " + damageDealt + " damage",
                            LogPanel.NOTIFY_MSG);
                    logPanel.updateLog(
                            LogPanel.AT_SECEND,
                            "You just reveived " + damageDealt + " damage",
                            LogPanel.INFO_MSG);
                }

            }

            roundWinner.checkHealing(0);// 现阶段只可能是0
            roundLoser.checkHealing(damageDealt);

            roundLoser.setHP(roundLoser.getHP() - damageDealt);// 败者食尘 xD

            if (AM_RNGenerator.isActivated) {
                if (roundWinner == player) {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "RNGenerator just activated and you are lucky :)",
                            LogPanel.INFO_MSG);
                } else {
                    logPanel.updateLog(
                            LogPanel.AT_THIRD,
                            "The dice of fate just rolled and you lost this round",
                            LogPanel.NOTIFY_MSG);
                }
                AM_RNGenerator.isActivated = false;
            }

            specificProcess(roundWinner.getPlayerActions().getSpecificSign());
            enemy.grasping(new GraspRequest() {

                @Override
                public void graspTo(AM_Recorder recorder) {
                    recorder.add(recorder.newRecord(
                        player.getPlayerActions(), 
                        enemy.getPlayerActions(), 
                        resultResolve()
                    ));
                }
                @Override
                public int resultResolve(){
                    if(roundWinner == enemy) return 1;
                    else if(roundWinner == null) return 0;
                    else return -1;
                }
            });

        }

        System.out.println("You now have " + player.getAmmoLeft() + " ammo left and " + player.getHP() + " HP left");
        System.out.println("The enemy now has " + enemy.getAmmoLeft() + " ammo left and " + enemy.getHP() + " HP left");
        System.out.println();

        if (player.getHP() <= 0) {
            System.out.println("Game Over! You lost...");
            logPanel.updateLog(LogPanel.AT_THIRD, "Game over! You lost...", LogPanel.INFO_MSG);
            isGameOver = true;

        } else if (enemy.getHP() <= 0) {
            System.out.println("Enemy defeated! You win! ");
            logPanel.updateLog(LogPanel.AT_THIRD, "Enemy deafeated! You win! ", LogPanel.INFO_MSG);
            isGameOver = true;
        }

    }

    @SuppressWarnings("unused")
    private void healingWhileDamaged(mainPlayer healingPlayer, int damageReceived) {
        bonusDamage = damageReceived;// 喝药时被打受到双倍伤害
        if (bonusDamage == 0) {
            healingPlayer.setHP(healingPlayer.getHP() + 1);// 回复一滴血
        }
        if (healingPlayer.getHP() >= MAX_HP) {
            healingPlayer.setHP(MAX_HP);
        }
    }

    @Override // 重写actionlistener中的方法,用于输入框读取数值
    public void actionPerformed(ActionEvent e) {
        while (true) {
            String input = selectTablePanel.getInput().trim();
            try {

                if (input.isEmpty()) {
                    System.out.println("please input the action ID");
                    logPanel.updateLog(LogPanel.AT_FOURTH, "Please input the action ID ):<", LogPanel.ERROR_MSG);
                    break;
                }

                int actionID = Integer.parseInt(input);
                int preSelections = player.actionsSelecting(actionID, player.getAmmoLeft(), player.getPlayerStats());
                if (preSelections == -1) {//返回-1代表子弹不足
                    String msg = "Failed to active the selected action: "
                            + ActionsLib.searchActions(actionID).getActionNameString() + " ,Because you have "
                            + player.getAmmoLeft() +
                            " ammo out of " + ActionsLib.searchActions(actionID).getAmmoCost() + " ammo requires.";
                    System.out.println(msg);
                    logPanel.updateLog(LogPanel.AT_FOURTH, msg, LogPanel.ERROR_MSG);
                    break;
                } else if( preSelections == -2) {//代表没有发射架强行发射导弹
                    String msg = "Sorry, but u need a the launcher before using the missile :(";
                    System.out.println(msg);
                    logPanel.updateLog(LogPanel.AT_FOURTH, msg, LogPanel.ERROR_MSG);
                    break;

                } else {

                    duoPlayerRound(actionID);
                    playerStatsPanel.update(player, enemy);
                    logPanel.updateLog(LogPanel.AT_FOURTH, "You just selected -> " + ActionsLib.getActionName(actionID),
                            LogPanel.INFO_MSG);
                    break;
                } // 用于子弹剩余判断，顺便提前诱发空指针

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! ");
                logPanel.updateLog(LogPanel.AT_FOURTH, "/!\\ Invaild input! -> \"" + input + " \"", LogPanel.ERROR_MSG);
                break;
            } catch (NullPointerException ex) {
                System.out.println("The action is NOT exist yet, try another one");
                logPanel.updateLog(LogPanel.AT_FOURTH, "/!\\ The action is NOT exist yet, try another one",
                        LogPanel.ERROR_MSG);
                //ex.printStackTrace();
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

    /* 
    public void restartNewConfigingGame() {// 暂时没用，放着

        if (restartNew != null) {
            remove(restartNew);
        } // 防止在重新配置新游戏的界面重复点击创建新游戏导致重复生成对话框

        restartNew = new DialogPanel("Are you sure to restart a new game? ");

        restartNew.addButtonSelectingListener(new DialogPanel.ButtonSelectingListener() {

            @Override

            public void onConfirmClicked() {

                restartNew.setVisible(false);
                remove(restartNew);

                GameConfigPanel configPanel = new GameConfigPanel();

                setLayout(new FlowLayout());
                add(configPanel);
            }

            @Override
            public void onCancelClicked() {
                remove(restartNew);

                setLayout(new BorderLayout());
                setBackground(Color.BLACK);

                setSubCompVisible(true);

                revalidate();
                repaint();
            }
        });

        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);

        add(restartNew);

        setSubCompVisible(false);

        revalidate();
        repaint();
    }*/

    public void continueGame_From(DialogPanel sourcePanel) {
        remove(sourcePanel);

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        setSubCompVisible(true);

        revalidate();
        repaint();
    }
    public void startNewConfigGame_From(DialogPanel sourcePanel) {
        sourcePanel.setVisible(false);
        remove(sourcePanel);
        //GameConfigPanel configPanel = new GameConfigPanel();

        setLayout(new FlowLayout());
        //add(configPanel);
    }

    private void specificProcess(int specificSign){
        switch(specificSign){
            case(-1):{
                break;
            }
            case(0):{
                break;
            }
            case(1):{
                break;
            }
            default:{
                break;
            }
        }

    }
}

final class DialogPanel extends JPanel {

    JButton cancelButton = new JButton(), confirmButton = new JButton();
    private ButtonSelectingListener buttonSelectingListener;

    private final int dialogWidth = 400;
    private final int dialogHeight = 200;

    public static final int INFO_DIALOG = 0;
    public static final int WARNING_DIALOG = 1;

    public DialogPanel() {
        this(0, "message");

    }

    public DialogPanel(String message) {
        this(0, message);
    }

    /**
     * @param type
     * @param message
     */
    public DialogPanel(int type, String message) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(dialogWidth, dialogHeight));
        setBorder(new LineBorder(Color.BLUE, 3));

        JPanel titlePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(dialogWidth, 30));
        switch (type) {
            case INFO_DIALOG: {
                titlePanel.setBackground(Color.GRAY);
                titlePanel.setForeground(Color.WHITE);
                titlePanel.add(new JLabel(" (?) Info  "));
                break;
            }
            case WARNING_DIALOG: {
                titlePanel.setBackground(Color.RED);
                titlePanel.setForeground(Color.WHITE);
                titlePanel.add(new JLabel("/!\\ Warning  "));
                break;
            }
        }
        add(titlePanel, BorderLayout.NORTH);

        textPanel.setPreferredSize(new Dimension(dialogWidth, 130));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(new JLabel(message));
        add(textPanel, BorderLayout.CENTER);

        buttonPanel.setPreferredSize(new Dimension(dialogWidth, 40));
        buttonPanel.setLayout(new GridLayout(1, 4, 0, 10));
        buttonPanel.add(new JLabel());
        confirmButton.setText("Confirm");
        buttonPanel.add(confirmButton);
        cancelButton.setText("Cancel");
        buttonPanel.add(cancelButton);
        buttonPanel.add(new JLabel());
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {

            onConfirm();
            // onConfirmClicked();
        });
        cancelButton.addActionListener(e -> {
            // 返回之前仍在进行的游戏
            onCancel();
        });
    }

    private void onConfirm() {
        if (buttonSelectingListener != null) {
            buttonSelectingListener.onConfirmClicked();
        }
    }

    private void onCancel() {
        if (buttonSelectingListener != null) {
            buttonSelectingListener.onCancelClicked();
        }
        for (ActionListener listener : cancelButton.getActionListeners()) {
            cancelButton.removeActionListener(listener);
        }
        for (ActionListener listener : confirmButton.getActionListeners()) {
            confirmButton.removeActionListener(listener);
        }
    }

    public interface ButtonSelectingListener {

        void onConfirmClicked();

        void onCancelClicked();
    }

    public void addButtonSelectingListener(ButtonSelectingListener listener) {
        this.buttonSelectingListener = listener;
    }


}
