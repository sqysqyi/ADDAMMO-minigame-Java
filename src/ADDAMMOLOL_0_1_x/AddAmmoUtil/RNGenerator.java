package ADDAMMOLOL_0_1_x.AddAmmoUtil;
import java.util.Random;

public class RNGenerator {
    public static boolean rateGenerator(double rateBetweenOneToHundred){
        System.out.println("RNG activated!");
        Random rand = new Random();
        int target = rand.nextInt(100)+1;
        if(target <= rateBetweenOneToHundred){
            return true;
        } 
        return false;
    }
}
