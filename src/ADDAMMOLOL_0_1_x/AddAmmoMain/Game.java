package ADDAMMOLOL_0_1_x.AddAmmoMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;
import ADDAMMOLOL_0_1_x.AddAmmoUI.FrameSize;
import ADDAMMOLOL_0_1_x.AddAmmoUI.GameUI_Set.*;


public class Game extends JPanel implements FrameSize, ActionListener {
    // UI渲染的参数声明；
    // 现已移至GameUI_Set文件夹中；
    ActionDiscriptionPane actionDiscriptionPane;
    LogPanel logPanel;
    PlayerStatsPanel playerStatsPanel;
    SelectTablePanel selectTablePanel;
    JPanel secendPanel;

    int playerHP, playerAmmoleft;

    // 逻辑参数声明
    private int playerSelectedActionID, enemySelectedActionID, enemy2SelectedActionID, enemy3SelectedActionID;
    private Actions playerActions, enemyActions, enemy2Actions, enemy3Actions;
    private Players player, enemy, enemy2, enemy3;
    private PlayerStats playerStats, enemyStats, enemy2Stats, enemy3Stats;
    private GameStats playerGameStats, enemyGameStats, enemy2GameStats, enemy3GameStats;
    private int binusDamage;
    public static final int MAX_HP = 3;
    public static final int DEFAULT_AMMO = 1;

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
        initPlayer(2);
        initUI();

        selectTablePanel.setInputListener(this);

        initGame();
    }

    // 游戏过程中的胜负逻辑判断
    public void initGame() {
       
        player.getPlayerStats().resetDmgDefThief();
        enemy.getPlayerStats().resetDmgDefThief();
        
        binusDamage = 0;
    }
    public void resetStats(){
        initGame();//只是复用方法，不代表包含关系
    }

    // 初始化玩家属性
    public void initPlayer(int totalPlayer) throws Exception {
        playerStats = new PlayerStats();
        playerGameStats = new GameStats();
        player = new Player(MAX_HP, DEFAULT_AMMO,
                playerStats,
                playerGameStats,
                playerActions,
                "player");
        // actions playerActions = new actions();

        switch (totalPlayer) {
            case 4:
                enemy3Stats = new PlayerStats();
                enemy3GameStats = new GameStats();
                enemy3 = new Enemy(MAX_HP, DEFAULT_AMMO,
                        enemy3Stats,
                        enemy3GameStats,
                        enemy3Actions,
                        "enemy3");

            case 3:
                enemy2Stats = new PlayerStats();
                enemy2GameStats = new GameStats();
                enemy2 = new Enemy(MAX_HP, DEFAULT_AMMO,
                        enemy2Stats,
                        enemy2GameStats,
                        enemy2Actions,
                        "enemy2");
                throw new Exception("unsupported player numbers, only \"2\" is ok...");
            case 2:
                enemyStats = new PlayerStats();
                enemyGameStats = new GameStats();
                enemy = new Enemy(MAX_HP, DEFAULT_AMMO,
                        enemyStats,
                        enemyGameStats,
                        enemyActions,
                        "enemy");
                break;

            default:
                throw new Exception("invalid player numbers");
        }
    }

    // 初始化UI界面
    public void initUI() {
        logPanel = new LogPanel();
        playerStatsPanel = new PlayerStatsPanel();
        selectTablePanel = new SelectTablePanel();
        actionDiscriptionPane = new ActionDiscriptionPane();
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

    public  void duoPlayerRound(int playerSelectedActionID) {
        {
            player.getPlayerStats().resetDmgDefThief();
            enemy.getPlayerStats().resetDmgDefThief();
            binusDamage = 0;
        }

        // playerSelectedActionID = 101 ;//
        // player.actionsSelecting(player.getHP(),player.getAmmoLeft(),playerGameStats);
        playerActions = player.selectActions(playerSelectedActionID);
        player.setPlayerActions(playerActions);// 玩家选择的action

        enemySelectedActionID = enemy.actionsSelecting(player.getHP(), player.getAmmoLeft(), playerGameStats);
        enemyActions = enemy.selectActions(enemySelectedActionID);
        enemy.setPlayerActions(enemyActions);// enemy电脑自动选择action

        System.out.println("The enemy selected: " + enemy.getPlayerActions().getActionNameString() + ", cost " +
                enemy.getPlayerActions().getAmmoCost() + " ammo");
        System.out.println("You've selected: " + player.getPlayerActions().getActionNameString() + ", cost " +
                player.getPlayerActions().getAmmoCost() + " ammo");
        // playerAmmoleftLabel.setText(""+player.getAmmoLeft());
        // playerHP_Label.setText(""+player.getHP());

        // actions属性转移。
        player.generalActivating();
        enemy.generalActivating();

        Players moreDangerousPlayer = Players.dangerousComparing(player, enemy);
        Players lessDangerousPlayer;// 参比对象主要还是以player而不是enemy类

        if (moreDangerousPlayer == null) {// 平局检查
            lessDangerousPlayer = null;
            System.out.println("nothing happened xD");
            player.checkHealing();
            enemy.checkHealing();

        } else {
            System.out.println("开始判断危险人物");
            lessDangerousPlayer = moreDangerousPlayer == player ? enemy : player;
            moreDangerousPlayer.winActivating();

            /* 以下是伤害计算 */
            int damageDealt = moreDangerousPlayer.damageDealtTo(lessDangerousPlayer);

            if (moreDangerousPlayer.getPlayerStats().isPolice()) {// 如果警察获胜

                if (lessDangerousPlayer.getPlayerStats().isPolice()) {// 又如果对面也是警察，不打
                    damageDealt = 0;
                }
                if (lessDangerousPlayer.getPlayerActions().getDangerous() <= 0) {// 如果对面根本不危险，不执法
                    damageDealt = 0;
                    lessDangerousPlayer.checkHealing();
                }

            } else if (moreDangerousPlayer.getPlayerStats().isThief()) {// 如果小偷获胜

                if (lessDangerousPlayer.getPlayerActions().getID() == 101) {// 如果对面是加子弹

                    moreDangerousPlayer
                            .setAmmoLeft(moreDangerousPlayer.getAmmoLeft() + lessDangerousPlayer.getAmmoLeft());// 偷走对面所有子弹
                    lessDangerousPlayer.setAmmoLeft(0);
                    System.out.println("checkpoint of stealing ammo");

                } else if (lessDangerousPlayer.getPlayerActions().isStealable()) {// 又如果对面的东西可偷
                    moreDangerousPlayer.setPlayerActions(lessDangerousPlayer.getPlayerActions());// 自己的action替换为对面的
                    moreDangerousPlayer.winActivating();// 再执行一次动作内容
                    lessDangerousPlayer.ammoRetureTo(moreDangerousPlayer);// 返还实行动作的子弹，因为东西是对面那里偷来的
                    moreDangerousPlayer.generalActivating();// 同理
                    damageDealt = moreDangerousPlayer.damageDealtTo(lessDangerousPlayer);// 用偷来的东西对被偷者造成伤害

                } else if (lessDangerousPlayer.getPlayerActions().isStealable() == false) {// 如果不可偷

                    if (lessDangerousPlayer.getPlayerStats().isPolice()) {// 不管对面是警察，子弹照偷不误
                        moreDangerousPlayer.setAmmoLeft(moreDangerousPlayer.getAmmoLeft() + lessDangerousPlayer.getAmmoLeft());// 偷走对面所有子弹
                        lessDangerousPlayer.setAmmoLeft(0);

                    }
                }
            }

            moreDangerousPlayer.checkHealing();

            lessDangerousPlayer.setHP(lessDangerousPlayer.getHP() - damageDealt - binusDamage);// 败者食尘 xD
            
            System.out.println("Debug infor: (moreDanger) "+moreDangerousPlayer.toString());
            System.out.println("Debug infor: (lessDanger) "+lessDangerousPlayer.toString());

        }

        System.out.println("Debug info: "+ player.toString());
        System.out.println("Debug info: "+ enemy.toString());
        

        System.out.println("You now have " + player.getAmmoLeft() + " ammo left and " + player.getHP() + " HP left");
        System.out.println("The enemy now has " + enemy.getAmmoLeft() + " ammo left and " + enemy.getHP() + " HP left");

        
        // playerHP_Label.setText("HP: "+player.getHP());
        // playerAmmoleftLabel.setText("Ammo: "+player.getAmmoLeft());

        // System.out.println("<Debug message> "+moreDangerousPlayer + " | " + player +
        // " | " + enemy);
        System.out.println();

        if (player.getHP() <= 0) {
            System.out.println("Game Over! You lost...");

        } else if (enemy.getHP() <= 0) {
            System.out.println("Enemy defeated! You win! ");
        }
    }

    @Override//重写actionlistener中的方法
    public void actionPerformed(ActionEvent e) {
        while (true) {
            try {
                String input = selectTablePanel.getInput().trim();
                if (input.isEmpty()) {
                    System.out.println("please input the action ID");
                    break;
                }

                int actionID = Integer.parseInt(input);
                int preSelections = player.actionsSelecting(actionID, player.getAmmoLeft(), player.getGameStats());
                if(preSelections == -1){
                    System.out.println("Failed to active the selected action, Because you have "+player.getAmmoLeft()+
                                        " ammo out of "+ ActionsLib.searchActions(actionID).getAmmoCost()+" ammo requires.");
                    break;
                }else{

                    duoPlayerRound(actionID);
                    playerStatsPanel.update(player,enemy);
                    break;
                }//用于子弹剩余判断，顺便提前诱发空指针
                 
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! ");
                break;
            } catch (NullPointerException ex) {
                System.out.println("The action is NOT exist yet, try another one");
                ex.printStackTrace();
                break;
            } catch (Exception ex){
                System.out.println("Nice job! How'd f you find such the input-related bug? Report it to me asap!!!");
                break;
            }
             finally {
                selectTablePanel.clearInput();
            }
        }

    }

}
