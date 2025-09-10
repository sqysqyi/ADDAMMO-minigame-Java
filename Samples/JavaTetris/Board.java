import java.awt.*;

public class Board {
    private static final int WIDTH = 10;
    private static final int DEPTH = 20;
    private Bricks entity,nextEntity;
    private int[][] board = new int[DEPTH][WIDTH];//10x20的标准俄罗斯方块棋盘

    public void setCurrentEntity(Bricks entity) {
        this.entity = entity;
    }

    public Bricks getCurrentEntity() {
        return entity;
    }

    public void setNextEntity(Bricks nextEntity){
        this.nextEntity = nextEntity;
    }
    public Bricks getNextEntity(){
        return nextEntity;
    }
    public int getWidth(){
        return WIDTH;
    }

    public void RandomNewBrick() {
        this.entity = nextEntity;
        this.nextEntity = Bricks.RandomBricks();
    }

    public Board(){//初始化游戏区，即全部置零。        
        for (int d = 0; d < DEPTH; d++) {
            for(int w = 0; w < WIDTH; w++){
                board[d][w] = 0;
            }
        }
        nextEntity = Bricks.RandomBricks();
    }
    //棋盘底层如下：
    /*       |0|1|2|3|4|5|6|7|8|9| WIDTH
       0#   {{0,0,0,0,0,0,0,0,0,0},
       1#    {0,0,0,1,1,0,0,0,0,0},
       2#    {0,0,1,1,0,0,0,0,0,0},
       3#    {0,0,0,0,1,0,0,0,0,0},
       4#    {0,0,0,0,1,0,0,0,0,0},
       5#    {0,0,0,0,1,1,1,0,0,0},
              ...(12 more)
      18#    {0,0,0,0,0,0,0,0,0,0},
      19#    {0,0,0,0,0,0,0,0,0,0},}
      DEPTH
    */
    public boolean canPlace(Bricks entities) {//检测是否可以放置
        byte[][] shape = entity.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int x = entity.getX() + j;
                    int y = entity.getY() + i;
                    if (x < 0 || x >= WIDTH || y < 0 || y >= DEPTH || board[y][x] != 0) {
                        //遍历每一个方块实体的坐标，只要超过长宽边界，或者这一格已经被占（!= 0），则返回false不可放置
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < DEPTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j] != 0) {
                    g.setColor(Color.green); // 设置方块颜色
                    g.fillRect(j * 30, i * 30, 28, 28);
                }
            }
        }//设置砖块固定下来后在棋盘上的颜色
        

        if ( entity != null) {
            g.setColor(Color.yellow);
            byte[][] shape = entity.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) {
                        int x = entity.getX() + j;
                        int y = entity.getY() + i;
                        g.fillRect(x * 30, y * 30, 28, 28); // 绘制当前方块
                    }
                }
            }
        }//设置砖块在下落过程中的颜色

        /*if (nextEntity != null) {
            g.setColor(Color.cyan);
            byte[][] shape = nextEntity.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) {
                        int x = j + 12; // 偏移绘制到右侧
                        int y = i + 1;
                        g.fillRect(x * 30, y * 30, 28, 28);
                    }
                }
            }
        }//设置预览区的砖块颜色*/
    }
    
    public int clearLine(){//满行消除
        int lineCleared = 0;

        for(int i = DEPTH -1 ; i >= 0; i--){//从下到上遍历扫描棋盘，
            boolean isFull = true ;
            for (int j = 0 ; j< WIDTH; j++){
                if(board[i][j] == 0){
                    isFull = false;
                    break;
                }
            }
            
            if (isFull){
                for (int j = 0; j < WIDTH ; j++){
                    board[i][j] = 0;

                }//清除该行，消除即置零
                lineCleared ++;
                for (int k = i; k > 0;k-- ){
                    board[k] = board[k-1];
                }
                board[0] = new int[WIDTH];
                i++;//回退到上一行重新判断
            }
        }
        return lineCleared;//返回消除行数，用于计分
    }

    public void placeBricks(Bricks entity) {
        byte[][] shape = entity.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    board[entity.getY() + i][entity.getX() + j] = shape[i][j];
                }
            }
        }
    }//将砖块对应的数值赋值给棋盘数组的对应元素，实现砖块的固定

    public void drawPreview(Graphics g) {//绘制预览区方块
        if (nextEntity != null) {
            g.setColor(Color.yellow);
            byte[][] shape = nextEntity.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] != 0) {
                        int x = j +1 ; // 预览区域开始位置，偏移右侧
                        int y = i+1 ;
                        g.fillRect(x * 30, y * 30, 28, 28); // 绘制预览砖块
                    }
                }
            }
        }
    }
}