package game.sqysqyi.ADDAMMOLOL.AddAmmoMain;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Players;

public class PlayerMngr {
    private Players[] players;
    private int addingPointer = 0;
    
    public static PlayerMngr managerInit(){
        return new PlayerMngr();
    }

    public void tryAdd(Players player){
        try{
            players[addingPointer++] = player;
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    public void stopAdding(){
        addingPointer = 0;
    }

    /**
     * get specefic player infos in player manager 
     * @param index if index is 0, always refers the player itself
     * @return the Player reference.
     */
    public Players getPlayers(int index){
        return players[index];
    }
    
    public int playersCount(){
        return players.length;
    }

    public PlayerMngr(){
        players = new Players[Game.NUMBER_OF_ENEMIES + 1];
    };


}
