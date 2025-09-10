import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Panel extends JPanel {
    private Board board;
    private Timer timer;
    private boolean isOver = false;
    private boolean isPaused = false;
    private JPanel gamePanel;
    private JPanel previewPanel,funcPanel,scorePanel;
    private JLabel scoreLabel,stateLabel,difficultyLabel;
    private int score = 0;
    private int delay = 500;
    private int difficulty = 1;
    
    private void checkGameOver() {
        if (!board.canPlace(board.getCurrentEntity())) {
            timer.stop(); // 停止游戏计时器
            stateLabel.setText("Game Over!");
            int option = JOptionPane.showConfirmDialog(this, 
                "Game Over! Restart?",
                "Game Over!",
                JOptionPane.YES_NO_OPTION);
            
            isOver =true; // 确定游戏结束状态
            if (option == JOptionPane.YES_OPTION){
                reset();
            } 
        }
        if (score >= 29400000){
            timer.stop();
            stateLabel.setText("WIN ");
            int option = JOptionPane.showConfirmDialog(this, "You've reached max score and win!Wanna restart?","Win !",JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION){
                reset();
            }
        }
    }

    private void reset(){
        isOver = false;
        score = 0;
        scoreLabel.setText("Score  " + score);
        board = new Board();
        board.RandomNewBrick();
        checkDifficulty();
        timer.setDelay(delay);
        timer.start();
        stateLabel.setText("Running");
        repaint();
    }//用于结束游戏后的重置

    private void checkDifficulty(){
        int newDifficulty = (Math.max(1,score/1000 +1));
        if (newDifficulty != difficulty){
            difficulty = newDifficulty;
            delay = Math.max(100, 500 - (difficulty - 1) * 50); // 难度越高，速度越快，最低100ms
            timer.setDelay(delay); // 更新定时器速度
            difficultyLabel.setText("Difficulty  " + difficulty); // 更新显示
        }  
    }

    public Panel(){
        board = new Board();//加载棋盘
        setLayout(new BorderLayout());//构建窗口布局

        gamePanel = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.draw(g);//？？？
            }
        };
        gamePanel.setPreferredSize(new Dimension(300, 600));//游戏区大小300x600 pixel，对应（10x20）x30
        gamePanel.setBackground(Color.black);
        gamePanel.setBorder(new LineBorder(Color.blue,5));//蓝色线框包围

        JPanel rightPanel = new JPanel();//加载右侧初始面板
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(200, 600));

        previewPanel = new JPanel(){//预览区布局
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.drawPreview(g);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));
            }
        };
        previewPanel.setPreferredSize(new Dimension(150, 120));
        previewPanel.setBackground(Color.black);
        previewPanel.setBorder(new LineBorder(Color.black, 5));
        rightPanel.add(previewPanel,BorderLayout.NORTH);//在右侧初始面板基础上添加预览区，在上（北）

        scorePanel = new JPanel();//新建计分板布局
        scorePanel.setPreferredSize(new Dimension(200, 100));
        scorePanel.setBackground(Color.black);
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        scorePanel.setLayout(new GridBagLayout()); 
        scoreLabel = new JLabel("Score " + score);
        scoreLabel.setFont(new Font("ArcadeClassic", Font.PLAIN, 30));
        scoreLabel.setForeground(Color.lightGray);
        scorePanel.add(scoreLabel);//在计分板中添加分数标签
        rightPanel.add(scorePanel, BorderLayout.CENTER);//计分板位于右侧基础面板的中间

        funcPanel = new JPanel();//新建功能区布局
        funcPanel.setPreferredSize(new Dimension(200, 300));
        funcPanel.setBackground(Color.black);
        funcPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
            stateLabel = new JLabel("Running");//状态标签
            stateLabel.setFont(new Font("ArcadeClassic",Font.PLAIN,30));
            stateLabel.setForeground(Color.lightGray);
            funcPanel.add(stateLabel);//添加状态标签
        //分隔线
            difficultyLabel = new JLabel("Difficulty "+ difficulty+"/11");
            difficultyLabel.setFont(new Font("Arial",Font.BOLD,20));
            difficultyLabel.setForeground(Color.lightGray);
            funcPanel.add(difficultyLabel);//添加难度标签
        //功能区默认在下（南）
        rightPanel.add(funcPanel, BorderLayout.SOUTH);

        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST); // 将右侧基础面板理所应当的放在右侧xD

        board.RandomNewBrick();//开始生成随机砖块
        // 监听键盘事件        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {    
                if(e.getKeyCode() == KeySetting.getKey("pause")){//空格暂停
                    isPaused = !isPaused;
                }                
                if(e.getKeyCode() == KeySetting.getKey("settings")){
                    isPaused = true;
                    new SettingsDialog((JFrame)SwingUtilities.getWindowAncestor(Panel.this)).setVisible(true);
                    isPaused = false;
                }

                if (isPaused){
                    timer.stop();
                    stateLabel.setText("Paused");
                }else{
                    timer.start();
                    stateLabel.setText("Running");
                }
                
                if(isPaused || isOver){
                    return;
                }



                Bricks entities = board.getCurrentEntity();//方向键控制
                if (e.getKeyCode() == KeySetting.getKey("left")) {
                    entities.setX(entities.getX() - 1);
                    if (!board.canPlace(entities)) {
                        entities.setX(entities.getX() + 1);
                    }
                }else if(e.getKeyCode() == KeySetting.getKey("right")){
                    entities.setX(entities.getX() + 1);
                    if (!board.canPlace(entities)) {
                        entities.setX(entities.getX() - 1);
                    }
                }else if(e.getKeyCode() == KeySetting.getKey("down")){
                    entities.setY(entities.getY() + 1);
                    if (!board.canPlace(entities)) {//加入此处判断来解决长按下导致的触底判断失效，与定时器致触底实现统一
                        entities.setY(entities.getY() - 1);
                        board.placeBricks(entities);
                        score += 100 * board.clearLine();
                        scoreLabel.setText("Score "+ score);
                        checkDifficulty();
                        difficultyLabel.setText("Difficulty "+ difficulty+ "/11");
                            
                        board.RandomNewBrick();
                    }
                }else if(e.getKeyCode() == KeySetting.getKey("rotate")){//翻转控制
                    entities.rotate();
                    while (!board.canPlace(board.getCurrentEntity())) {
                        if(entities.getX() + entities.getShape()[0].length > board.getWidth()){
                            entities.setX(entities.getX()-1);
                        }else if(entities.getX() < 0){
                            entities.setX(entities.getX() +1);//在边界处旋转不行时，通过偏移一个方格来实现
                        }else{
                            for(int t = 0 ; t<3; t++){
                                    entities.rotate();
                            } // 旋转失败，恢复
                            break;    
                        }
                    }
                }else if(e.getKeyCode() == KeyEvent.VK_M){
                    if(e.getKeyCode() == KeyEvent.VK_C)
                    stateLabel.setText("aw man");
                }
                repaint();
            }
        });
    
    setFocusable(true);
        // 每隔500毫秒更新一次游戏
        delay = 525 - difficulty * 25;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOver || isPaused)
                    return;//彻底停止

                Bricks entities = board.getCurrentEntity();
                entities.setY(entities.getY() + 1);
                if (!board.canPlace(entities)) {//与上述下键控制逻辑相似
                    entities.setY(entities.getY() - 1);
                    board.placeBricks(entities);
                    score += 100 * board.clearLine();
                    scoreLabel.setText("Score " + score);//消除一行+100分
                    checkDifficulty();
                    difficultyLabel.setText("Difficulty  "+ difficulty+ "/11");
                    delay = 550 - 50 * difficulty;
                    board.RandomNewBrick();
                    checkGameOver();
                }                
                repaint();
                
            }
        });
        timer.start();

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gamePanel.repaint();
        previewPanel.repaint();

        g.setColor(Color.BLACK);
        g.setFont(new Font("ArcadeClassic", Font.PLAIN, 30));
        g.drawString("Score  " + score, 350, 400); 
    }
}