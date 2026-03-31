package game.addammo.AddAmmoMain;

import static game.addammo.AddAmmoMain.RoundStats.*;
import static game.addammo.StartConfig.*;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import game.addammo.AddAmmoMain.actions.Action;
import game.addammo.AddAmmoMain.comparator.Comparator;
import game.addammo.AddAmmoMain.executor.Executor;
import game.addammo.AddAmmoMain.players.*;
import game.addammo.AddAmmoMain.players.Players.PlayerStats;
import game.addammo.AddAmmoMain.players.enemies.Enemy;
import game.addammo.AddAmmoMain.players.enemies.Recorder;
import game.addammo.AddAmmoMain.players.enemies.Enemy.GraspRequest;
import game.addammo.AddAmmoUI.FrameSize;
import game.addammo.AddAmmoUI.game.LogPanel;

//->AddAmmoMain.Game

/**
 * main game logic
 */
public class Game implements Runnable, FrameSize{
    
    //DialogPanel restartNew;

    // 逻辑参数声明
    private Action playerActions, enemyActions, enemy2Actions, enemy3Actions;
    public Player player;
    public Enemy enemy, enemy2, enemy3;
    @SuppressWarnings("unused")
    private PlayerStats playerStats, enemyStats, enemy2Stats, enemy3Stats;

    public Comparator gameComparator;
    public PlayerMngr playerManager;
    private GameClient gameClient;
    private BlockingQueue<Integer> playerInputQueue;
    private Consumer<Runnable> uiUpdater;

    private volatile boolean paused = false;

    public int MAX_HP = GameClient.getCurrentMaxHP() == 0 ? 3 : GameClient.getCurrentMaxHP();
    public int DEFAULT_AMMO = GameClient.getCurrentDefaultAmmo() == 0 ? 1 : GameClient.getCurrentDefaultAmmo();

    public static final int NUMBER_OF_ENEMIES = 1;

    public static int round = 1;
    public static boolean isGameOver = false;

    public static int global_dangerous = 0;
    public static int global_peace = 0;

    public Game(BlockingQueue<Integer> playerInputQueue, Consumer<Runnable> uiUpdater, GameClient client) throws Exception {

        this.playerInputQueue = playerInputQueue;
        this.uiUpdater = uiUpdater;
        this.gameClient = client;
        initPlayer(NUMBER_OF_ENEMIES + 1);
        initGame();
        
    }

    public Player getUser(){
        return player;
    }

    public void pause(){
        paused = true;
    }
    public void continueGame(){
        synchronized(this.getClass()){
            paused = false;
            this.getClass().notifyAll();
        }
    }

   
    // 游戏过程中的胜负逻辑判断
    private void initGame() {
        isGameOver = false;
        round = 1;
    }

    // 初始化玩家属性
    private void initPlayer(int totalPlayer) {
        playerManager = PlayerMngr.managerInit();

        if (MAX_HP == 0)
            MAX_HP = defaultMaxHP;
        if (DEFAULT_AMMO == 0)
            DEFAULT_AMMO = defaultAmmo;

        //playerStats = new PlayerStats();
        player = new Player(MAX_HP,
                DEFAULT_AMMO,
                playerActions,
                "player");
        playerManager.tryAdd(player);

        switch (totalPlayer) {
            case 4:
                enemy3 = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemy3Actions,
                        "enemy3");
                playerManager.tryAdd(enemy3);
                
            case 3:
                enemy2 = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemy2Actions,
                        "enemy2");
                playerManager.tryAdd(enemy2);
                throw new UnsupportedOperationException("unsupported player numbers, only \"2\" is ok...");
            case 2:
                enemy = new Enemy(MAX_HP, 
                        DEFAULT_AMMO,
                        enemyActions,
                        "enemy");
                playerManager.tryAdd(enemy);
                break;

            default:
                throw new UnsupportedOperationException("invalid player numbers");
        }

        
        gameComparator = Comparator.loadComparator(playerManager);
    }

    /**
     * the CORE method of this game, which only run in a 1v1 dual
     * 
     * @param playerSelectedActionID accept player input action ID
     * @since AddAmmo v0.1.4
     */
    private void mainRound(int playerSelectedActionID) {
        player.getPlayerStats().resetDmgDefThief();
        enemy.getPlayerStats().resetDmgDefThief();

        player.actionsSelecting(playerSelectedActionID,null,playerStats);
        playerActions = player.selectActions(playerSelectedActionID);
        player.setPlayerActions(playerActions);// 玩家选择的action

        int enemySelectedActionID = enemy.actionsSelecting(0 /*always 0 here */, player, playerStats);
        enemyActions = enemy.selectActions(enemySelectedActionID);
        enemy.setPlayerActions(enemyActions);// enemy电脑自动选择action

        player.getPlayerActions().getEvent().doWhen(
            NONE, 
            player);

        enemy.getPlayerActions().getEvent().doWhen(
            NONE, 
            enemy);

        gameComparator.building();
        new Executor(gameComparator.getToDoList().toExecutorTask());
        enemy.grasping(new GraspRequest() {
            @Override
            public void graspTo(Recorder recorder) {
                recorder.add(recorder.newRecord(
                    player.getPlayerActions(), 
                    enemy.getPlayerActions(), 
                    resultResolve()
                ));
            }

            @Override
            public RoundStats resultResolve() {
                return enemy.myLastRoundStats(gameComparator);
            }
            
        });
        gameComparator.getToDoList().reset();

        if (player.getHP() <= 0) {
            System.out.println("Game Over! You lost...");
            //logPanel.updateLog(LogPanel.AT_THIRD, "Game over! You lost...", LogPanel.INFO_MSG);
            isGameOver = true;

        } else if (enemy.getHP() <= 0) {
            System.out.println("Enemy defeated! You win! ");
            //logPanel.updateLog(LogPanel.AT_THIRD, "Enemy deafeated! You win! ", LogPanel.INFO_MSG);
            isGameOver = true;
        }
        player.getPlayerStats().mineExpired();
        enemy.getPlayerStats().mineExpired();
    }
    

    @Override
    public void run() {
        while (!isGameOver) {
            try {
                if(paused){
                    synchronized(this.getClass()){
                        while (paused) wait();  
                    }
                }
                int playerSelectedActionID = playerInputQueue.take();
                mainRound(playerSelectedActionID);
                refreshUI();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshUI() {
        uiUpdater.accept(() -> {
            gameClient.playerStatsPanel.update(player, enemy);
            gameClient.logPanel.updateLog(LogPanel.AT_FOURTH, "You just selected -> " + player.getPlayerActions().getActionNameString(),
                    LogPanel.INFO_MSG);

            if(isGameOver && playerManager.isSomeoneDead()){
                gameClient.logPanel.updateLog(
                    LogPanel.AT_FOURTH, 
                    "Game Over! " + (player.getHP() <= 0 ? "You lost..." : "You win!"), 
                    LogPanel.INFO_MSG);
            }
        });
    }


}


