import java.util.*;
import java.util.List;

public class Bricks {

    private byte[][] shape;
    /*
     *二维数组定义砖块的形状，第一个下标标示轮廓的高，第二个标示轮廓的宽
     *{{0，1，1},
     *  1，1，0}} -> new shape[2][3],
     * 定位标识在shape[0][0]处。
     * 
     */
    private int x, y ;//下落的砖块坐标，即上述定位标识所在处
    //private Color color;

    public byte[][] getShape(){
        return shape;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }


    public Bricks(byte[][] shape){
        this.shape = shape;
        //this.color = color;
        this.x = 4;//初始位置在棋盘(4,0)处
        this.y = 0;
    }

    public void rotate() {//砖块旋转方法：

        byte[][] newShape = new byte[shape[0].length][shape.length];//旋转后的轮廓形状的长宽互换

        for (int i = 0; i < shape.length; i++) {
            for(int j = 0; j <shape[0].length; j++){

                newShape[j][shape.length-i-1] = shape[i][j];
            }
        }
        shape = newShape;

    }

    public static ArrayList<byte[][]> ShapeQueue = new ArrayList<>();//以下是基于装袋随机的砖块生成
    public static Bricks RandomBricks(){//产生随机的砖块的方法
  
            if(ShapeQueue.isEmpty()) 
                ShapeQueue = ShapeQueueRefill();//随机队列为空，则重新生成新随机队列

            byte[][] shape = ShapeQueue.remove(0);//从arraylist头移除的元素作为下一个放在棋盘上的砖块
            return new Bricks(shape);
        }

    private static ArrayList<byte[][]> ShapeQueueRefill() {//队列重装填方法
        ArrayList<byte[][]> ShapeQueue = new ArrayList<>();

        byte[][][] ShapeForm = {//第一个下标作为初始基本砖块的编号
            {{1, 1, 1, 1}},//1x4长条

            {{0, 0, 0}, {1, 1, 1}, {1, 0, 0}},
            {{1, 1, 1}, {0, 0, 1}, {0, 0, 0}},//两种L型砖块

            {{0, 1, 0}, {1, 1, 1}, {0, 0, 0}},//T型砖块

            {{1, 1}, {1, 1}},//正方形

            {{1, 1, 0}, {0, 1, 1},},
            {{0, 1, 1}, {1, 1, 0},}//两种“之”字型的砖块
        };

        List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < ShapeForm.length/*最长为7 */; i++) {
                indices.add(i);
            }
        Collections.shuffle(indices); // 将indices容器内的元素随机打乱（即打乱形状索引）
            for (int index : indices) {
                ShapeQueue.add(ShapeForm[index]);//按索引依次取回对应的形状数组
            }

        return ShapeQueue;
    }
}