package game.addammo.InternalTests;

public class Main_t implements Runnable{
    
    @Override
    public void run() {
        new Start_t().run();
    }
}