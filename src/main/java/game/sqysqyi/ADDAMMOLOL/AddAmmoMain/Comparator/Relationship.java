package game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Players;

public class Relationship {
    public Players from,to;
    public RoundStats stats;
    
    public Relationship(Players from, Players to, RoundStats win_or_lost){
        this.from = from;
        this.to = to;
        this.stats = win_or_lost;
    }

    public void execute(){
        from.getPlayerActions().actionEvent.doWhen(stats, from, new Players[]{to});
    }

}
