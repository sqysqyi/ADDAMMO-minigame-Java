package game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Actions;

public class ActionLabel {
    public LabelName LName;
    public int indensity;

    public ActionLabel(LabelName name, int indensity){
        this.LName = name;
        this.indensity = indensity;
    }

    /**
     * this is used to create a new label with its name and indensity
     * 
     * if indensity is empty, the default value is 0 
     * @param LName 
     * @param indensity
     * @return a new action label
     */
    public static ActionLabel L(LabelName LName, int indensity){

        return new ActionLabel(LName, indensity);
    }
    public static ActionLabel L(LabelName LName){
        return new ActionLabel(LName, 0);
    }
    
    public enum LabelName {
        /**
         * tag to those actions that never cause any dangerous results
         */
        ABSOLUTE_SAFE, // absolute safe

        /**
         * tag to those may responsed and damanged by polices
         */
        CRIMINALS, // criminalss
        /**
         * tag to polices
         */
        POLICES, // polices
        /**
         * tag to thieves
         */
        THIEVES, // thieves
        /**
         * tag to specialists
         */
        SPECIALISTS, // specialists
        /**
         * tag to devices, they usually last more than only 1 round
         */
        DEVICES, // devices

        /**
         * tag to those that could be stolen by a thief
         */
        CAN_BE_STOLEN, // can be stolen
        /**
         * tag to those that can be removed, especailly by specialists like engineer
         */
        CAN_BE_REMOVED, // can be removed
        /**
         * tag to those will be blocked but won't damaged by polices
         */
        MERCY,

        /**
         * tag to those that definitely active target's mine
         */
        SHORT_RANGE_ATTACKING, // short range attacking
        /**
         * tag to those that may active target's mine
         */
        MID_RANGE_ATTACKING, // mid range attacking
        /**
         * tag to those that wont active target's mine
         */
        LONG_RANGE_ATTACKING, // long range attacking

        /**
         * actions attacking from air, or defend attacks from sky
         */
        AIR_RAID, // air raid
        /**
         * actions attacking face to face, or normal defense
         */
        ENCOUNTER, // encounter
        /**
         * atctions attacking from undergound
         */
        FROM_UNDERGOUND, // from undergound

        /**
         * not coming yet, for some offensive actions
         */
        NSFW;// yea you know xD

    }

}
