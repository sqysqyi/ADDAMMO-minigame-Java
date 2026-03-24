package game.addammo.AddAmmoMain.actions;

/**
 * @see ActionLabel
 */
public class ActionLabelsContainer {
    private ActionLabel[] labels;


    public ActionLabelsContainer(){};
    public ActionLabelsContainer(ActionLabel[] containLabels){
        this.labels = containLabels;
    }

    public static ActionLabelsContainer createLabelsContainer(ActionLabel[] labels){
        if(labels == null){
            return new ActionLabelsContainer(new ActionLabel[0]);
        }
        return new ActionLabelsContainer(labels);
    }

    public boolean hasLabel(ActionLabel.LabelName labelName){
        for(ActionLabel label : labels){
            if(label.LName == labelName) return true;

        }
        return false;
    }

    public int getIntensity(ActionLabel.LabelName labelName){
        for(ActionLabel label : labels){
            if(label.LName == labelName) return label.indensity;
        }
        return 0;
    }
}
