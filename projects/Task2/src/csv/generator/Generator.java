package csv.generator;

import csv.utils.LabelsUtil;
import csv.utils.Time24;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static csv.main.Gen_Test.*;
import static csv.utils.Math2.*;

public class Generator {

    public Generator(){
        main();
    }

    void main(){
        try{new PrintWriter(new FileWriter(PATH, false));}catch (IOException e) {e.printStackTrace();}

        Time24 time = new Time24(0, 0, 0);

        /** Одна операция длится [30; 120) минут, -> таких операций в сутки 36 минимум */
        for(int i=0; i<OPERATION_DAILY; i++){
            int duration_sec = (int)randVal(DURATION_SEC[0], DURATION_SEC[1]);
            int label = (int) randVal(0, 3);

            int n = n(label, duration_sec);

            /** 6 секунд делится на 4 операции как: [2, 1, 1, 2] равномерно */
            int[] dts = noisy_secs(duration_sec, n);
            /**min - от, max - до*/
            float[] vals = noisy_line(min(label), max(label), n, BIAS);

            for(int j=0; j<n; j++){
                time.add(dts[j]);
                if(time.lessThan(24, 0 ,0)) {
                    float val = vals[j];//Math.min(0, vals[j]);
                    save(time, val, label);
                }
            }
        }
    }

    void save(Time24 time, float val, int label){
        try(PrintWriter pw = new PrintWriter(new FileWriter(PATH, true))) {
            pw.println(time + ";" + String.format("%.1f",val) + ";" + label);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    int n(int label, int duration_sec){
        int n = (int)randVal(1, duration_sec);
        if(LabelsUtil.Label.STAGNATION == LabelsUtil.getLabelByID(label))
            n = 1;

        return n;
    }

    float last_val = 0.0f;

    /** ОТ */
    float min(int label){
        if(LabelsUtil.Label.UP == LabelsUtil.getLabelByID(label))
            return MIN;
        else if(LabelsUtil.Label.DOWN == LabelsUtil.getLabelByID(label))
            return MAX;
        else
            return last_val;
    }

    /** ДО */
    float max(int label){
        if(LabelsUtil.Label.UP == LabelsUtil.getLabelByID(label)){
            last_val = MAX;
            return MAX;
        }
        else if(LabelsUtil.Label.DOWN == LabelsUtil.getLabelByID(label)){
            last_val = MIN;
            return MIN;
        }
        else
            return last_val;
    }
}
