package main;

import java.io.FileWriter;
import java.io.PrintWriter;

import static main.Main.*;
import static main.Main.Label.*;
import static main.Math2.*;

/**
 * Сейчас задача создать синтетические данные
 * Каждый csv - одни сутки
 * Данные имеют вид время-значение
 * Мне нужно сгенерировать точно так же
 * время (h:m:s), where h[0, 24], m[0, 60], s[0, 60]
 * value - float number [0.0; inf)
 * */

public class Generator {
    public static void main(String args[]) {
        new Generator().run();
    }


    public static String path = "C:\\Java\\3.csv";

    public void run() {
        //Очищаю файл
        try{new FileWriter(path, false);}catch (Exception e){e.printStackTrace();}

        Time24 duration = new Time24(0, 0, 0);
        Time24 time = new Time24(0, 0, 0);
        float last_val = randVal(0, 0.4f); //Обычно в таком диапозоне начинается график

        float min = 0, max = 10, bias = 1;

        for(int i=0; i<5; i++) {
            Label label = randLabel();
            //Для стагнации нужно поменьше времени, наверное
            duration.setH((int) randVal(1, 5));
            duration.setM((int) randVal(0, 60));
            duration.setS((int) randVal(0, 60));

            System.out.println(label + ": " + duration);

            ///Количество изменений: каждые 1 - 3 секунды должно что-то происходить
            int n = (int) randVal(duration.total_secs() / 3f, duration.total_secs());
            //dtime между этими изменениями
            int[] dts = noisy_secs(duration.total_secs(), n);
            float[] vals = null;

            /**
             * time+dts[]; vals[]; type
             * */

            if (label == STAGNATION) {
                vals = new float[]{last_val};
                dts = new int[]{duration.total_secs()};
            }
            else if (label == OTHER) {
                vals = noisy_line(last_val, last_val, n, bias);
            } else if (label == UP) {
                vals = noisy_line(min, max, n, bias);
                last_val = max;
            } else if (label == DOWN) {
                vals = noisy_line(max, min, n, bias);
                last_val = min;
            }

            try(PrintWriter pw = new PrintWriter(new FileWriter(path, true))) {
                for (int j=0; j<dts.length; j++) {
                    time.add(dts[j]);
                    if(time.moreThan(24, 0, 0)) break;
                    pw.println(time + ";"
                            + String.format("%.1f",vals[j]) + ";"
                            + getLabelID(label));
                }
            }
            catch (Exception e) {e.printStackTrace();}



            time.add(duration.total_secs());
        }

    }

}
