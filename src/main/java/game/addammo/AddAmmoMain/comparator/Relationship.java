package game.addammo.AddAmmoMain.comparator;

import game.addammo.AddAmmoMain.RoundStats;
import game.addammo.AddAmmoMain.players.Players;

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
