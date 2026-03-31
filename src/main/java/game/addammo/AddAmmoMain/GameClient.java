package game.addammo.AddAmmoMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import game.addammo.AddAmmoMain.actions.ActionX;
import game.addammo.AddAmmoMain.players.Player;
import game.addammo.AddAmmoUI.FrameSize;
import game.addammo.AddAmmoUI.GameConfigPanel;
import game.addammo.AddAmmoUI.StartingMenuPanel;
import game.addammo.AddAmmoUI.StartingMenuPanel.StartingButtonsListener;
import game.addammo.AddAmmoUI.game.ActionDiscriptionPane;
import game.addammo.AddAmmoUI.game.LogPanel;
import game.addammo.AddAmmoUI.game.PlayerStatsPanel;
import game.addammo.AddAmmoUI.game.SelectTablePanel;
import game.addammo.AddAmmoUtil.debugTool.Debug;

public class GameClient extends JFrame implements ActionListener{
    // UI渲染的参数声明；
    // 现已移至GameUI_Set文件夹中；
    GameClient gameClient = this;

    StartingMenuPanel startingMenuPanel;
    GameConfigPanel configPanel;
    DialogPanel dialogPanel;
    
    JRootPane rootPane,tempRootPane;
    ActionDiscriptionPane actionDiscriptionPane;
    LogPanel logPanel;
    PlayerStatsPanel playerStatsPanel;
    SelectTablePanel selectTablePanel;
    JPanel secendPanel;

    private BlockingQueue<Integer> toProcessActionQueue = new LinkedBlockingQueue<>();
    private KeyEventDispatcher dispatcher;
    private Game game;
    private Player user;
    private boolean isPaused = false;

    private static int MaxHP = 3;
    private static int DefaultAmmo = 1;
    public static final int setMaxPlayers = 2;

    public GameClient(String title) {
        super(title);
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
        initWelcome();
        
    }

    private void initGame() {
        try {
            game = new Game(toProcessActionQueue, this::refreshUI, this);
            Thread logicThread = new Thread(game, "Logic Thread");
            logicThread.start();
            user = game.getUser();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            game = null;
        }

    }


    private void refreshUI(Runnable task) {
        SwingUtilities.invokeLater(task);
    }
    
    private void initUI() {
        rootPane = new JRootPane();
        logPanel = new LogPanel();
        playerStatsPanel = new PlayerStatsPanel();
        selectTablePanel = new SelectTablePanel();
        actionDiscriptionPane = new ActionDiscriptionPane();

        selectTablePanel.setTextChangeListener(text -> updateActionDiscription(text));
        initFramework(rootPane);
        
        selectTablePanel.setInputListener(this);
    }

    // 初始化页面布局，在initUI()中
    private void initFramework(JRootPane rootPane) {
        rootPane.setLayout(new BorderLayout());
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
        rootPane.add(secendPanel, BorderLayout.NORTH);

        selectTablePanel.setPreferredSize(new Dimension(FrameSize.ST_SIZE_W, FrameSize.ST_SIZE_H));
        selectTablePanel.setBackground(Color.GRAY);
        selectTablePanel.setBorder(new LineBorder(Color.BLUE, 5));
        rootPane.add(selectTablePanel, BorderLayout.CENTER);

        logPanel.setPreferredSize(new Dimension(FrameSize.LP_SIZE_W, FrameSize.LP_SIZE_H));
        logPanel.setBackground(Color.BLACK);
        logPanel.setBorder(new LineBorder(Color.WHITE, 5));
        logPanel.setLayout(new GridLayout(4, 1));
        rootPane.add(logPanel, BorderLayout.SOUTH);

        setContentPane(rootPane);
    }

    private void initWelcome(){
        startingMenuPanel = new StartingMenuPanel();
        startingMenuPanel.addStartingButtonsListener(new StartingButtonsListener() {
            
            @Override
            public void startButtonClicked() {
                gameClient.remove(startingMenuPanel);
                startGame();
            }

            @Override
            public void configButtonClicked() {
                startingMenuPanel.removeAll();

                startingMenuPanel.setLayout(new FlowLayout());

                configPanel = new GameConfigPanel();
                configPanel.addConfigConfirmListener(() -> {
                    gameClient.remove(gameClient.getContentPane());
                    startGame();
                });
                startingMenuPanel.add(configPanel);
                startingMenuPanel.revalidate();
                startingMenuPanel.repaint();
            }
        });
        gameClient.setContentPane(startingMenuPanel);

    }

    private void startGame(){
        this.remove(this.getContentPane());
        this.initGame();
        this.initUI();
        initKeyEventHandler();

        rootPane.setFocusable(true);
        rootPane.requestFocusInWindow();
        rootPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    tryCallDialogPanel();
                    Debug.print("hello?");
                }
            }
        });
        
        this.revalidate();
        this.repaint();
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
    
    private void startNewConfigGame_From(DialogPanel sourcePanel) {
        sourcePanel.setVisible(false);
        tempRootPane.remove(sourcePanel);
        configPanel = new GameConfigPanel();
        configPanel.addConfigConfirmListener(() ->{
            gameClient.remove(gameClient.getContentPane());
            startGame();
        });

        tempRootPane.add(configPanel);
        revalidate();
        repaint();
    }
    
    public static int getCurrentMaxHP(){
        return MaxHP;
    }
    public static int getCurrentDefaultAmmo(){
        return DefaultAmmo;
    }
    public static void setMaxHP(int hp){
        MaxHP = hp;
    }
    public static void setDefaultAmmo(int ammo){
        DefaultAmmo = ammo;
    }
    
    private void initKeyEventHandler(){
        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        if(game == null) return;
        else{
            dispatcher = e ->{
                if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    tryCallDialogPanel();
                    return true;
                }
                return false;
            };
            kfm.addKeyEventDispatcher(dispatcher);
        }
    }
    @SuppressWarnings("unused")
    private void destroyKeyEventHandler(){//no in-game exit method so this is temperary 
        if(dispatcher != null){
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
            dispatcher = null;
        }
    }
    
    public void tryCallDialogPanel() {
        if (game != null) {
            if(!isPaused){
                showDialogPanel();
            }else{
                continueGame();
            }  
        }
    }
    
    private void showDialogPanel(){
        
        isPaused = true;

        game.pause();

        rootPane = (JRootPane) getContentPane();
        tempRootPane = new JRootPane();
        tempRootPane.setLayout(new FlowLayout());
        dialogPanel = new DialogPanel(
            DialogPanel.INFO_DIALOG, 
            "Game is paused. Do you want to continue or start a new game?",
            this::continueGame,
            this::startNewGame);
        tempRootPane.add(dialogPanel);
        setContentPane(tempRootPane);
        revalidate();
        repaint();
    }
    private void continueGame(){
        if(!isPaused) return;

        isPaused = false;
        setContentPane(rootPane);
        game.continueGame();
        revalidate();
        repaint();
        
        rootPane.requestFocusInWindow();
    }
    private void startNewGame(){
        if(!isPaused) return;
        isPaused = false;
        game = null;
        startNewConfigGame_From(dialogPanel);
        revalidate();
        repaint();
    }
    @Override
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
                int preSelections = user.actionsSelecting(actionID ,null, user.getPlayerStats());
                if (preSelections == -1) {//返回-1代表子弹不足
                    String msg = "Failed to active the selected action: "
                            + ActionX.searchActions(actionID).getActionNameString() + " ,Because you have "
                            + user.getAmmoLeft() +
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

                    toProcessActionQueue.put(actionID);
                    
                    break;
                }
                

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! ");
                logPanel.updateLog(LogPanel.AT_FOURTH, "/!\\ Invaild input! -> \"" + input + " \"", LogPanel.ERROR_MSG);
                break;
            } catch (InterruptedException iex){
                System.out.println("Unexpected error occurred while processing your action. Please try again.");
                logPanel.updateLog(LogPanel.AT_FOURTH,
                        "/!\\ Unexpected error occurred while processing your action. Please try again.",
                        LogPanel.ERROR_MSG);
                Thread.currentThread().interrupt(); // 恢复中断状态
                iex.printStackTrace();
                break;
            } catch (Exception ex) {
                logPanel.updateLog(LogPanel.AT_FOURTH,
                        "/!\\ Nice job! How'd f you find such the input-related bug? Report it to me asap!!!",
                        LogPanel.ERROR_MSG);
                ex.printStackTrace();
                break;
            } finally {
                selectTablePanel.clearInput();
            }
        }
    }  

}

final class DialogPanel extends JPanel {

    JButton cancelButton = new JButton(), confirmButton = new JButton();

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

    public DialogPanel(int type, String message) {
        this(type, message, null, null);
    }

    /**
     * @param type
     * @param message
     * @param continueTask 继续当前游戏的任务
     * @param newStartTask 开始新游戏的任务
     */
    public DialogPanel(int type, String message, Runnable continueTask, Runnable newStartTask) {

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

            newStartTask.run();
            // onConfirmClicked();
        });
        cancelButton.addActionListener(e -> {
            // 返回之前仍在进行的游戏
            continueTask.run();
        });
    }
}
