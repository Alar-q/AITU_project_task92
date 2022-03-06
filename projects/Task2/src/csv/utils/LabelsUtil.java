package csv.utils;

import static csv.utils.LabelsUtil.Label.*;

public class LabelsUtil {

    public enum Label {
        UP,         // Ход графика вверх с шумом
        DOWN,       // Ход графика вниз с шумом
        OTHER,      // остается в общем на каком-то уровне, но с шумом
        STAGNATION; // Абсолютно без изменений
    }

    public static int getLabelID(Label label){
        if(label == UP)
            return 0;
        else if(label == DOWN)
            return 1;
        else if(label == OTHER)
            return 2;
        else // label == STAG
            return 3;
    }
    public static int getLabelID(String label){
        if(label.equals("UP"))
            return 0;
        else if(label.equals("DOWN"))
            return 1;
        else if(label.equals("OTHER"))
            return 2;
        else // label == OTHER
            return 3;
    }

    public static Label getLabelByID(int id){
        if(id == 0)
            return Label.UP;
        else if(id == 1)
            return DOWN;
        else if(id == 2)
            return OTHER;
        else // label == OTHER
            return STAGNATION;
    }

    public static Label randLabel(){
        int type = (int) Math2.randVal(0, 4);
        if(type == 0)
            return UP;
        else if(type == 1)
            return DOWN;
        else if(type == 2)
            return Label.OTHER;
        else
            return Label.STAGNATION;
    }


    public static int maxID(int[] t){
        int id = 0;
        float max = t[id];
        for(int i=1; i<t.length; i++){
            if(max < t[i]){
                id = i;
                max = t[id];
            }
        }
        return id;
    }



}