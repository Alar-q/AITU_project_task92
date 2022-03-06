package converter;

import utils.LabelsUtil;
import utils.Time24;
import utils.Visualization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.Math2.parseFloat;
import static main.Main.PATH;
import static main.Main.TIME_STEP;

/** одинаковые временные шаги */
public class Converter {

    public Converter(){
        main();
    }

    void main(){
        List<Float> vals = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        convert(PATH, vals, labels);
        Visualization.showLabels(vals, labels, PATH, true);
        Visualization.show(vals, PATH);
    }

    /**
     * Выдает значения с одинаковыми временными шагами: (t0, t1, t2), dt=const
     * */
    void convert(String path, List<Float> vals, List<Integer> labels) {
        try(Scanner sc = new Scanner(new File(path))) {
            float sum = 0;
            float last_val = -123;
            int last_time = 0;
            int counter = 0;
            int[] l_counter = new int[4];
            while(sc.hasNextLine()) {
                String d = sc.nextLine();
                //h:m:s;val;label
                String[] str = d.split(";");
                int time_secs = new Time24(str[0]).total_secs();
                int secs = time_secs - last_time;
                last_time = time_secs;
                float val = parseFloat(str[1]);
                int label = Integer.parseInt(str[2]);

                //if first time
                if(last_val == -123) last_val = val;
                float mean = Math.abs(val + last_val)/2;

                for(int i=0; i<secs; i++){
                    sum+=mean;
                    l_counter[label]++;
                    counter++;
                    if(counter== TIME_STEP){
                        vals.add(sum/(float)counter);
                        labels.add(LabelsUtil.maxID(l_counter));
                        //System.out.println(sum/(float)counter + ", " + LabelsUtil.maxID(l_counter));
                        counter = 0;
                        sum=0;
                        l_counter = new int[4];
                    }
                }

                last_val = val;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
