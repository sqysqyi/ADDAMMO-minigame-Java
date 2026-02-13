package ADDAMMOLOL_0_1_x.AddAmmoUtil;
import java.util.Random;

public class AM_RNGenerator {
    private static Random rand;
    static{
        rand = new Random();
    }

    public static boolean isActivated = false;
    
    /**
     * 
     * @param rate number 0-100 accept
     * @return
     */
    public static boolean rateGenerator(int rate){
        System.out.println("RNG activated!");
    
        int target = rand.nextInt(100)+1;
        return target <= rate;
    }

    /**
     * 
     * @param rate number 0-1000 accept
     * @return
     */
    public static boolean newRNG(int rate){
        int target = rand.nextInt(1000) + 1;
        return target <= rate;

    }
}
