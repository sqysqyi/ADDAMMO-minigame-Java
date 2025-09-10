package ADDAMMOLOL_0_1_x.AddAmmoUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ADDAMMOLOL_0_1_x.AddAmmoMain.GameStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.PlayerStats;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Start;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.Actions;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Actions.ActionsLib;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Enemy;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Player;
import ADDAMMOLOL_0_1_x.AddAmmoMain.Players.Players;

public class Game extends JPanel implements FrameSize{
    //UI渲染的参数声明
    JPanel selectTable;
    JTextField playerInputTextField;
    JPanel tablePanel;
    JLabel[][] tableElements = new JLabel[9][4];
    JPanel secendPanel,actionDiscriptionPanel,playerStatsPanel;
    JLabel playerStatsTitleLabel,enemyStatsTitleLabel;
    JLabel playerHP_Label,playerAmmoleftLabel;
    JLabel enemyHP_Label,enemyAmmoLeftLabel;

    JPanel logPanel;
    int playerHP,playerAmmoleft;

    //逻辑参数声明
    private int playerSelectedActionID, enemySelectedActionID,enemy2SelectedActionID,enemy3SelectedActionID;
    private Actions playerActions,enemyActions,enemy2Actions,enemy3Actions;
    private Players player,enemy,enemy2,enemy3;
    private PlayerStats playerStats, enemyStats,enemy2Stats,enemy3Stats;
    private GameStats playerGameStats,enemyGameStats,enemy2GameStats,enemy3GameStats;
    private int binusDamage;
    public static final int MAX_HP = 3;

    public Game() throws Exception{
        
/*******************************************************
 * 
 *  gamebar
 * ----------------------------------------------------
 * 
 *  player stats             |    action discription
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
        //gameStart();
    }

    //游戏过程中的胜负逻辑判断
    public void gameStart(){
        while (true){
            
            player.getPlayerStats().resetDmgDefThief();
            enemy.getPlayerStats().resetDmgDefThief();
            binusDamage = 0;
            
            playerSelectedActionID = 101;//player.actionsSelecting(player.getHP(),player.getAmmoLeft(),playerGameStats); 
                playerActions = player.selectActions(playerSelectedActionID);
            player.setPlayerActions(playerActions);//玩家选择的action

            enemySelectedActionID = enemy.actionsSelecting(player.getHP(),player.getAmmoLeft(),playerGameStats);
                enemyActions = enemy.selectActions(enemySelectedActionID);
            enemy.setPlayerActions(enemyActions);//enemy电脑自动选择action

            System.out.println("The enemy selected: " + enemy.getPlayerActions().getActionNameString() + ", cost " + 
                                enemy.getPlayerActions().getAmmoCost() + " ammo");
            System.out.println("You've selected: " + player.getPlayerActions().getActionNameString() + ", cost " +
                                player.getPlayerActions().getAmmoCost() + " ammo");
            //playerAmmoleftLabel.setText(""+player.getAmmoLeft());
            //playerHP_Label.setText(""+player.getHP());

            //actions属性转移。
            player.generalActivating();
            enemy.generalActivating();

            Players moreDangerousPlayer = Players.dangerousComparing(player, enemy);
            Players lessDangerousPlayer;//参比对象主要还是以player而不是enemy类

        
            if(moreDangerousPlayer == null){//平局检查
                lessDangerousPlayer = null;
                System.out.println("nothing happened xD");
                player.checkHealing();
                enemy.checkHealing();

            }else{
                
                lessDangerousPlayer = moreDangerousPlayer==player?enemy:player;
                moreDangerousPlayer.winActivating();


                /*以下是伤害计算 */
                int damageDealt = moreDangerousPlayer.damageDealtTo(lessDangerousPlayer);

                if(moreDangerousPlayer.getPlayerStats().isPolice()){//如果警察获胜
                    
                    if(lessDangerousPlayer.getPlayerStats().isPolice()){//又如果对面也是警察，不打
                        damageDealt = 0;
                    }
                    if(lessDangerousPlayer.getPlayerActions().getDangerous() <= 0){//如果对面根本不危险，不执法
                        damageDealt = 0;
                        lessDangerousPlayer.checkHealing();
                    }
                    
                }else if(moreDangerousPlayer.getPlayerStats().isThief()){//如果小偷获胜

                    if(lessDangerousPlayer.getPlayerActions().getID() == 101){//如果对面是加子弹
                        
                        moreDangerousPlayer.setAmmoLeft(moreDangerousPlayer.getAmmoLeft() + lessDangerousPlayer.getAmmoLeft());//偷走对面所有子弹
                        lessDangerousPlayer.setAmmoLeft(0);

                    }else if(lessDangerousPlayer.getPlayerActions().isStealable()){//又如果对面的东西可偷
                        moreDangerousPlayer.setPlayerActions(lessDangerousPlayer.getPlayerActions());//自己的action替换为对面的
                        moreDangerousPlayer.winActivating();//再执行一次动作内容
                        lessDangerousPlayer.ammoRetureTo(moreDangerousPlayer);//返还实行动作的子弹，因为东西是对面那里偷来的
                        moreDangerousPlayer.generalActivating();//同理
                        damageDealt = moreDangerousPlayer.damageDealtTo(lessDangerousPlayer);//用偷来的东西对被偷者造成伤害

                    }else if(lessDangerousPlayer.getPlayerActions().isStealable() == false){//如果不可偷
                        
                        if(lessDangerousPlayer.getPlayerStats().isPolice()){//不管对面是警察，子弹照偷不误
                            moreDangerousPlayer.setAmmoLeft(moreDangerousPlayer.getAmmoLeft() + lessDangerousPlayer.getAmmoLeft());//偷走对面所有子弹
                            lessDangerousPlayer.setAmmoLeft(0);

                        }
                    }
                }

                moreDangerousPlayer.checkHealing();
                
                lessDangerousPlayer.setHP( lessDangerousPlayer.getHP() - damageDealt - binusDamage);//败者食尘 xD
            }
            

            System.out.println("You now have "+player.getAmmoLeft()+" ammo left and "+player.getHP()+" HP left");
            System.out.println("The enemy now has "+enemy.getAmmoLeft() + " ammo left and "+ enemy.getHP() +" HP left");

            playerHP_Label.setText("HP: "+player.getHP());
            playerAmmoleftLabel.setText("Ammo: "+player.getAmmoLeft());

            //System.out.println("<Debug message> "+moreDangerousPlayer + " | " + player + " | " + enemy);
            System.out.println();
            
            if(player.getHP() <= 0 ){
                System.out.println("Game Over! You lost...");
                break;
            }else if(enemy.getHP() <= 0){
                System.out.println("Enemy defeated! You win! ");
                break;
            }
        }
    }

    
    //初始化玩家属性
    public void initPlayer(int totalPlayer) throws Exception {
            playerStats = new PlayerStats();
            playerGameStats = new GameStats();
        player = new Player(3, 1, 
                    playerStats, 
                    playerGameStats,
                    playerActions,
                    "player");
        //actions playerActions = new actions();
        
        switch (totalPlayer) {
            case 4:
                enemy3Stats = new PlayerStats();
                enemy3GameStats = new GameStats();
                enemy3 = new Enemy(3, 1, 
                        enemy3Stats, 
                        enemy3GameStats, 
                        enemy3Actions, 
                        "enemy3");
            
            case 3:
                enemy2Stats = new PlayerStats();
                enemy2GameStats = new GameStats();
                enemy2 = new Enemy(3, 1, 
                        enemy2Stats, 
                        enemy2GameStats, 
                        enemy2Actions, 
                        "enemy2");
                throw new Exception("unsupported player numbers, only \"2\" is ok...");
            case 2:
                enemyStats = new PlayerStats();
                enemyGameStats = new GameStats();
                enemy = new Enemy(3,1, 
                        enemyStats,
                        enemyGameStats,
                        enemyActions,
                        "enemy");
                break;
        
            default:
                throw new Exception("invalid player numbers");
        }
        playerHP = player.getHP();
        playerAmmoleft = player.getAmmoLeft();
    }

    //初始化UI界面
    public void initUI(){
        initFramework();
        initPlayerStatsPanel();
        initSelectTable();
        initGameFlowPanel();
        initLogPanel();
        
    }

    //初始化页面布局，在initUI()中
    private void initFramework(){
        setLayout(new BorderLayout());
        //setBorder(new LineBorder(Color.BLUE, 5));

        selectTable = new JPanel();
        logPanel = new JPanel();
        
        secendPanel = new JPanel();
            actionDiscriptionPanel = new JPanel();
            playerStatsPanel = new JPanel();
        
        secendPanel.setPreferredSize(new Dimension(FrameSize.P2_SIZE_W, FrameSize.P2_SIZE_H));
        secendPanel.setBackground(Color.BLACK);
        secendPanel.setLayout(new BorderLayout());
        
            actionDiscriptionPanel.setPreferredSize(new Dimension(FrameSize.ADP_SIZE_W, FrameSize.ADP_SIZE_H));
            actionDiscriptionPanel.setBorder(new LineBorder(Color.BLUE, 5));
            
            actionDiscriptionPanel.setBackground(Color.BLACK);
        secendPanel.add(actionDiscriptionPanel, BorderLayout.EAST);
        
            playerStatsPanel.setPreferredSize(new Dimension(FrameSize.PSP_SIZE_W, FrameSize.PSP_SIZE_H));//-5同理
            playerStatsPanel.setBackground(Color.BLACK);
            playerStatsPanel.setLayout(new GridLayout(4, 4, 5, 5));
            playerStatsPanel.setBorder(new LineBorder(Color.BLUE, 5));
            
            
        secendPanel.add(playerStatsPanel,BorderLayout.WEST);
        add(secendPanel,BorderLayout.NORTH);
        
        selectTable.setPreferredSize(new Dimension(FrameSize.ST_SIZE_W, FrameSize.ST_SIZE_H));
        selectTable.setBackground(Color.GRAY);
        selectTable.setBorder(new LineBorder(Color.BLUE, 5));
        add(selectTable,BorderLayout.CENTER);

        logPanel.setPreferredSize(new Dimension(FrameSize.LP_SIZE_W, FrameSize.LP_SIZE_H));
        logPanel.setBackground(Color.BLACK);
        logPanel.setBorder(new LineBorder(Color.WHITE, 5));
        add(logPanel,BorderLayout.SOUTH);
        
    }

    //初始化玩家状态面板，在initUI()中；
    private void initPlayerStatsPanel(){
        playerStatsTitleLabel = new JLabel("Your stats");
            playerStatsTitleLabel.setOpaque(false);
            playerStatsTitleLabel.setBackground(Color.DARK_GRAY);
            playerStatsTitleLabel.setForeground(Color.WHITE);
            //playerStatsTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
            //playerStatsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // 文本居中
        
        enemyStatsTitleLabel = new JLabel("Enemy Stats");
            enemyStatsTitleLabel.setForeground(Color.WHITE);
            
        playerHP_Label = new JLabel("HP: "+ player.getHP());
            playerHP_Label.setForeground(Color.WHITE);
            //playerHP_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
            //playerHP_Label.setHorizontalAlignment(SwingConstants.CENTER);        

        enemyHP_Label = new JLabel("HP: "+enemy.getHP());
            enemyHP_Label.setForeground(Color.WHITE);
              
        playerAmmoleftLabel = new JLabel("Ammo: " + player.getAmmoLeft());
            playerAmmoleftLabel.setForeground(Color.WHITE);
            //playerAmmoleftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            //playerAmmoleftLabel.setHorizontalAlignment(SwingConstants.CENTER);

        enemyAmmoLeftLabel = new JLabel("Ammo: " + enemy.getAmmoLeft());
            enemyAmmoLeftLabel.setForeground(Color.WHITE);
        
        playerStatsPanel.add(playerStatsTitleLabel);
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(enemyStatsTitleLabel);
        playerStatsPanel.add(new JLabel());
        
        playerStatsPanel.add(playerHP_Label);
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(enemyHP_Label); 
        playerStatsPanel.add(new JLabel());
        
        playerStatsPanel.add(playerAmmoleftLabel);
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(enemyAmmoLeftLabel);
        playerStatsPanel.add(new JLabel());
        
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(new JLabel());
        playerStatsPanel.add(new JLabel());
            
    }

    //初始化动作选择表，在initUI()中
    private void initSelectTable(){
        selectTable.setLayout(new BorderLayout());
            playerInputTextField = new JTextField(1){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(Color.GRAY);
                        g.drawString("Action code", 5,
                                getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
                    }
                }
            };
            playerInputTextField.setPreferredSize(new Dimension(Start.FRAME_WIDTH-10, 20));

            
        selectTable.add(playerInputTextField, BorderLayout.NORTH);

            tablePanel = new JPanel(new GridLayout(9,4,2,2));
            tablePanel.setPreferredSize(new Dimension(Start.FRAME_WIDTH, Start.FRAME_HEIGHT/2-90));
            
            for(int i = 0; i< tableElements.length; i++){
                for (int j = 0; j < tableElements[i].length; j++) {
                    if(j==0){
                        tablePanel.add(new JLabel("series: "+(i+1)));
                    }else{
                        int tempID = 100*(i+1)+j;
                        tablePanel.add(new JLabel("| "+tempID+ ": "+ActionsLib.getActionName(tempID) + "\t  ("+ActionsLib.getActionAmmoCost(tempID)+")"));
                    }
                }
            }
        selectTable.add(tablePanel, BorderLayout.SOUTH);


    }

    //初始化游戏对战流面板，在initUI()中
    private void initGameFlowPanel(){
        //coming soon
    }

    //初始化日志面板，在initUI()中
    private void initLogPanel(){
        //coming soon
    }

    //above are UI-related initualizations

    private void labelChangeColor(JLabel recolorLabel){
        //写不出来，操了
    }

    public void healingWhileDamaged(Players healingPlayer,int damageReceived){//
        binusDamage = damageReceived;//喝药时被打受到双倍伤害
        if(binusDamage == 0){
            healingPlayer.setHP(healingPlayer.getHP() + 1);//回复一滴血
        }
        if(healingPlayer.getHP() >= MAX_HP){
            healingPlayer.setHP(MAX_HP);
        }
    }
}
