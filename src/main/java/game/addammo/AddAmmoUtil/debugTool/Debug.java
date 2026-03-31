package game.addammo.AddAmmoUtil.debugTool;

public final class Debug {
    
    public static void print(String input){
        String output = "Debug info >>" + input;
        System.out.println(output);
    }

    public static void insertSimpleMark(){
        System.out.println( "Debug info >> inserted mark here");
    }

    
}   
