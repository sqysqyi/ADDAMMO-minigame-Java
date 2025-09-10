import javax.swing.*;

public class MainThread {//主进程
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500,600);

        Panel panel = new Panel();
        frame.setContentPane(panel);
        frame.pack(); // 自动调整窗口大小以适应内容

        frame.setLocationRelativeTo(null); // 窗口居中
        frame.setVisible(true);
        frame.setTitle("Tetris!");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
