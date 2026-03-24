package game.addammo.AddAmmoMain.executor;

import static game.addammo.AddAmmoMain.RoundStats.*;

import game.addammo.AddAmmoMain.comparator.Relationship;
import game.addammo.AddAmmoMain.comparator.ToDoRegister;
import game.addammo.AddAmmoUtil.AM_RNGenerator;

public class Executor {

    public Executor(ToDoRegister<Relationship> toDoEvents){
        int counter = 0;
    
        for (Relationship r : toDoEvents){
            if(r.stats == UNDEFINED){
                r.stats = AM_RNGenerator.rateGenerator(50)?WIN:LOST;
                toDoEvents.edit(counter, new Relationship(r.from, r.to, r.stats));
                toDoEvents.edit(counter+1 , new Relationship(r.to, r.from, r.stats == WIN?LOST:WIN));
            }
            r.execute();
            counter ++;
            //System.out.println("hello??");
        }
    }
}
