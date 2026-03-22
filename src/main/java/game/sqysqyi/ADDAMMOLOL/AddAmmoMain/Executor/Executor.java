package game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Executor;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.Relationship;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.ToDoRegister;

public class Executor {

    public Executor(ToDoRegister<Relationship> toDoEvents){
        for (Relationship r : toDoEvents){
            r.execute();
            System.out.println("hello??");
        }
    }
}
