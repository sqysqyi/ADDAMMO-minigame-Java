package game.addammo.AddAmmoMain.comparator;

import java.util.Iterator;

import game.addammo.AddAmmoMain.Game;

public class ToDoRegister<R> implements Iterable<R>{
    
    public static int size = (1 + Game.NUMBER_OF_ENEMIES) * (1 + Game.NUMBER_OF_ENEMIES);
    Object[] regItems = new Object[size];
    int pointer = 0;

    public boolean add(R element){
        try{
            regItems[pointer++] = element;
            return true;
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        } 
    } 

    public boolean edit(int pointer, R target){
        try {
            if(regItems[pointer] != null){
                regItems[pointer] = target;
                return true;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public R getNext(int pointer){
        try {
            if(regItems[pointer + 1] != null){
                return (R)regItems[pointer + 1];
            }
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean reset(){
        try {
            regItems = new Object[size];
            pointer = 0;
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ToDoRegister<R> toExecutorTask(){
        return this;
    }

    public ToDoRegister(){

    }

    @Override
    public Iterator<R> iterator() {
        return new Iterator<R>() {

            private int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < size && regItems[cursor] != null;
            }

            @SuppressWarnings("unchecked")
            @Override
            public R next() {    
                return (R)regItems[cursor++];
            }
            
        };
    }
}
