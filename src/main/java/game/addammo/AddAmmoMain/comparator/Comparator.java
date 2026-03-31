package game.addammo.AddAmmoMain.comparator;

import static game.addammo.AddAmmoMain.RoundStats.*;
import static game.addammo.AddAmmoMain.actions.ActionLabel.LabelName.*;

import game.addammo.AddAmmoMain.PlayerMngr;
import game.addammo.AddAmmoMain.RoundStats;
import game.addammo.AddAmmoMain.actions.ActionLabelsContainer;
import game.addammo.AddAmmoMain.players.Players;
import game.addammo.AddAmmoMain.players.Players.PlayerStats;
import game.addammo.AddAmmoUtil.AM_RNGenerator;

/**
 * Currently available comparator(s) is only comparator_1v1.
 * Use <code>Comparator.loadComparator(playerManager)</code> to load a matched comparator.
 */
public abstract class Comparator implements RelationshipBuilder{

    ToDoRegister<Relationship> toDoList = new ToDoRegister<>();
    public static Comparator loadComparator(PlayerMngr playerManager){
        switch (playerManager.playersCount()){
            case 2:{
                return new Comparator_1v1(playerManager);
            }
            case 3:{
                return new Comparator_1v2(playerManager);
            }
            default:{
                throw new IllegalArgumentException("Not found a matched comparator, this might be a bug");
            }
        }
        
    }
    
    public abstract void building();

    public ToDoRegister<Relationship> getToDoList(){
        return toDoList;
    }
    
    protected Players findMajorPlayer(Players player1, Players player2){
        ActionLabelsContainer   tempContainer1 = player1.getPlayerActions().labelsContainer,
                                tempContainer2 = player2.getPlayerActions().labelsContainer;
        if(tempContainer1.hasLabel(POLICES) ||tempContainer2.hasLabel(POLICES)){
            if(tempContainer1.getIntensity(POLICES) != tempContainer2.getIntensity(POLICES)){
                return tempContainer1.getIntensity(POLICES) > tempContainer2.getIntensity(POLICES)?player1:player2;
            }   
        }
        if(tempContainer1.hasLabel(THIEVES) || tempContainer2.hasLabel(THIEVES)){
            if(tempContainer1.getIntensity(THIEVES) != tempContainer2.getIntensity(THIEVES)){
                return tempContainer1.getIntensity(THIEVES) > tempContainer2.getIntensity(THIEVES)?player1:player2;
            }    
        }
        if(tempContainer1.hasLabel(CRIMINALS) || tempContainer2.hasLabel(CRIMINALS)){
            if(tempContainer1.getIntensity(CRIMINALS) != tempContainer2.getIntensity(CRIMINALS)){
                return tempContainer1.getIntensity(CRIMINALS) > tempContainer2.getIntensity(CRIMINALS)?player1:player2;
            }
        }
        
        return AM_RNGenerator.rateGenerator(50)?player1:player2;
    }
    
    public Comparator(){};

}


class Comparator_1v1 extends Comparator{

    PlayerMngr managerBuffer;
        Players thisPlayer,opponent;
    ToDoRegister<Relationship> toDoList = super.toDoList;
    
    public Comparator_1v1 (PlayerMngr playerManager){
        this.managerBuffer = playerManager;
        thisPlayer = managerBuffer.getPlayers(0);
        opponent = managerBuffer.getPlayers(1);
    }
    @Override
    public void building() {
        comparing(thisPlayer, opponent);
        comparing(opponent, thisPlayer);
    }

    private void comparing(Players player1, Players player2){
        ActionLabelsContainer  tempContainer1 = player1.getPlayerActions().labelsContainer,
                                tempContainer2 = player2.getPlayerActions().labelsContainer;

        if (PlayerStats.equalWithOnce(player1.getPlayerStats(), player2.getPlayerStats())){
            // 先把同行剔除
            toDoList.add(new Relationship(player1, player2, TIED));
            return;
        }

        if(player1.getPlayerStats().isPolice){
            //非criminals则忽略
            if(!tempContainer2.hasLabel(CRIMINALS)){
                toDoList.add(new Relationship(player1, player2, TIED));
                return;
            }else{
                //是crims
                if(!player2.getPlayerStats().isThief){
                    //非thief类crims
                    if(tempContainer1.getIntensity(POLICES) > tempContainer2.getIntensity(CRIMINALS)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                    }else if(tempContainer1.getIntensity(POLICES) == tempContainer2.getIntensity(CRIMINALS)){
                        toDoList.add(new Relationship(player1, player2, UNDEFINED));//需要在Executor中接着处理
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                    }
                    return;
                }else{
                    //thief类crims
                    if(tempContainer1.getIntensity(POLICES) >= tempContainer2.getIntensity(CRIMINALS)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                    }
                    return;
                }
            }   
        }

        if(tempContainer1.hasLabel(CRIMINALS)){
            //是盗贼的情况,必须争胜负
            if(player1.getPlayerStats().isThief){
                if(!tempContainer2.hasLabel(CAN_BE_STOLEN)){
                    //盗贼盗窃非可被盗物品，视为平
                    toDoList.add(new Relationship(player1, player2, TIED));
                    return;
                }else if(tempContainer2.hasLabel(CRIMINALS)){
                    if(tempContainer1.getIntensity(CRIMINALS) > tempContainer2.getIntensity(CRIMINALS)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                    }
                    return;
                }else if(tempContainer2.hasLabel(POLICES)){
                    if(tempContainer1.getIntensity(CRIMINALS) > tempContainer2.getIntensity(POLICES)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                    }else if(tempContainer1.getIntensity(CRIMINALS) == tempContainer2.getIntensity(POLICES)){
                        toDoList.add(new Relationship(player1, player2, UNDEFINED));
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                    }
                    return;
                }else{
                    //偷不明白，放弃，视为平
                    toDoList.add(new Relationship(player1, player2, WIN));
                    return;
                }
            }else{
                if(tempContainer2.hasLabel(CRIMINALS)){
                    if(tempContainer1.getIntensity(CRIMINALS) > tempContainer2.getIntensity(CRIMINALS)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                        return;
                    }else if(tempContainer1.getIntensity(CRIMINALS) == tempContainer2.getIntensity(CRIMINALS)){
                        if(player1.getPlayerStats().rawDmg != 0 && player2.getPlayerStats().rawDmg != 0){
                            toDoList.add(new Relationship(player1, player2, UNDEFINED));
                            return;
                        }else if(player1.getPlayerStats().rawDmg == 0 && player2.getPlayerStats().rawDmg == 0){
                            toDoList.add(new Relationship(player1, player2, TIED));
                            return;
                        }else{
                            RoundStats stats = player1.getPlayerStats().rawDmg != 0?WIN:LOST;
                            toDoList.add(new Relationship(player1, player2, stats));
                            return;
                        }
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                        return;
                    }
                }else if(tempContainer2.hasLabel(POLICES)){
                    if(tempContainer1.getIntensity(CRIMINALS) > tempContainer2.getIntensity(POLICES)){
                        toDoList.add(new Relationship(player1, player2, WIN));
                    }else if(tempContainer1.getIntensity(CRIMINALS) == tempContainer2.getIntensity(POLICES)){
                        toDoList.add(new Relationship(player1, player2, UNDEFINED));
                    }else{
                        toDoList.add(new Relationship(player1, player2, LOST));
                    }
                    return;
                }else{
                    //对手啥都不是，视为成功
                    toDoList.add(new Relationship(player1, player2, WIN));
                    return;
                }       
            }
        }
        //既不是警察也不是罪犯，你总得是什么东西罢
        if(true){
            if(tempContainer2.hasLabel(POLICES)){
                toDoList.add(new Relationship(player1, player2, TIED));
            }else if(tempContainer2.hasLabel(CRIMINALS)){
                toDoList.add(new Relationship(player1, player2, LOST));
            }else{
                toDoList.add(new Relationship(player1, player2, TIED));
            }
            return;
        }
            
    }
}

/**
 * work in progress, DO NOT USE THIS CLASS!!!
 */
@Deprecated
class Comparator_1v2 extends Comparator{

    public Comparator_1v2(PlayerMngr playerManager) {
        // TODO Auto-generated constructor stub
        building();
    }
    @Override
    public void building() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'building'");
        
    }
    
}
