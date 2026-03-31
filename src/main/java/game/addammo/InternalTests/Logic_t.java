package game.addammo.InternalTests;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Logic_t implements Runnable {
    BlockingQueue<Integer> idQueue;
    Consumer<Integer> uiUpdator;

    public Logic_t(){}

    @Override
    public void run() {
        
    }
}
