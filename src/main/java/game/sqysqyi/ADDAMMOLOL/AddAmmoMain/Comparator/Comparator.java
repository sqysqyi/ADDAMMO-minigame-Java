package game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.PlayerMngr;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.ActionLabelsContainer;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Players;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Players.PlayerStats;
import game.sqysqyi.ADDAMMOLOL.AddAmmoUtil.AM_RNGenerator;

import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions.ActionLabel.LabelName.*;
import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats.TIED;

public abstract class Comparator implements RelationshipBuilder{

    ToDoRegister<Relationship> toDoRegister = new ToDoRegister<>();
    public static Comparator loadComparator(PlayerMngr playerManager){
        switch (playerManager.playersCount()){
            case 2:{
                return new Comparator_1v1(playerManager);
            }
            default:{
                throw new IllegalArgumentException("Not found a matched comparator, this might be a bug");
            }
        }
        
    }
    
    public abstract void building();
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
        
        
        /*if(Players.PlayerStats.equalWithOnce(player1.getPlayerStats(),player2.getPlayerStats())){
            //同行，返回谁都无所谓
            return AM_RNGenerator.rateGenerator(50)?player1:player2;
        }else if(player1.getPlayerStats().isPolice && player2.getPlayerActions().labelsContainer.hasLabel(CRIMINALS)){
            //警匪大战，警只要大于等于匪即可制服对方，无需掷骰子
            return player1.getPlayerActions().labelsContainer.getIntensity(POLICES) >= 
                    player2.getPlayerActions().labelsContainer.getIntensity(CRIMINALS)?player1:player2;
        }else if(player2.getPlayerStats().isPolice && player1.getPlayerActions().labelsContainer.hasLabel(CRIMINALS)){
            //同上
            return player2.getPlayerActions().labelsContainer.getIntensity(POLICES) >= 
                    player1.getPlayerActions().labelsContainer.getIntensity(CRIMINALS)?player2:player1;
        }else if(player1.getPlayerStats().isThief && player2.getPlayerActions().labelsContainer.hasLabel(CRIMINALS)){
            //盗贼尝试盗窃其它非盗贼的罪犯，须大于对手
            return player1.getPlayerActions().labelsContainer.getIntensity(THIEVES) > 
                    player2.getPlayerActions().labelsContainer.getIntensity(CRIMINALS)?player1:player2;
        }else if(player2.getPlayerStats().isThief && player1.getPlayerActions().labelsContainer.hasLabel(CRIMINALS)){
            //同上
            return player2.getPlayerActions().labelsContainer.getIntensity(THIEVES) > 
                    player1.getPlayerActions().labelsContainer.getIntensity(CRIMINALS)?player2:player1;
        }else if(player1.getPlayerActions().labelsContainer.hasLabel(SPECIALISTS) || player2.getPlayerActions().labelsContainer.hasLabel(SPECIALISTS)){
            //特殊职业者优先
            return player1.getPlayerActions().labelsContainer.hasLabel(SPECIALISTS)?player1:player2;
        }else{
            return AM_RNGenerator.rateGenerator(50)?player1:player2;
        }*/
    }
    
    public Comparator(){};

}


class Comparator_1v1 extends Comparator{

    PlayerMngr managerBuffer;
        Players thisPlayer,opponent;
    ToDoRegister<Relationship> toDoList;
    
    public Comparator_1v1 (PlayerMngr playerManager){
        this.managerBuffer = playerManager;
        thisPlayer = managerBuffer.getPlayers(0);
        opponent = managerBuffer.getPlayers(1);
    }
    @Override
    public void building() {
        //Players major = findMajorPlayer(thisPlayer,opponent);
        comparing(thisPlayer, opponent);
        comparing(opponent, thisPlayer);
    }

    

    private void comparing(Players player1, Players player2){
        //int player1Dangerous = player1.getPlayerActions().getDangerous();
        //int player2Dangerous = player2.getPlayerActions().getDangerous();

        if (PlayerStats.equalWithOnce(player1.getPlayerStats(), player2.getPlayerStats())){
            // 先把同行剔除
            toDoList.add(new Relationship(player1, player2, TIED));
            return;
        }

        //平局情况：
        if(player1.getPlayerStats().isPolice){
            if(!player2.getPlayerActions().labelsContainer.hasLabel(CRIMINALS)){
                toDoList.add(new Relationship(player1, player2, TIED));
                return;
            }
        }
            
    }
}
