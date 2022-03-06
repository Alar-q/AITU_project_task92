package csv.utils;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static csv.utils.Math2.parseFloat;

/** Парсит h:m:s;val в одномерные минуты */
public class Viewer extends Application {

    @Override
    public void start(Stage stage) {
        main();
        stage.show();stage.close();
    }

    private String path = "C:\\Users\\Pupochek\\Documents\\AITU PC\\Данные Задание 2\\01\\1.csv";

    void main(){
        List<Float> vals = view(path);
        Visualization.show(vals, "not marked");
    }

    List<Float> view(String path) {
        List<Float> vals = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(path))) {
            float sum = 0;
            float last_val = -123;
            int last_time = 0;
            int counter = 0;

            while(sc.hasNextLine()) {
                String d = sc.nextLine();
                //h:m:s;val;label
                String[] str = d.split(";");
                int time_secs = new Time24(str[0]).total_secs();
                int secs = time_secs - last_time;
                last_time = time_secs;
                float val = parseFloat(str[1]);

                //if first time
                if(last_val == -123) last_val = val;
                float mean = Math.abs(val + last_val)/2;

                for(int i=0; i<secs; i++){
                    sum+=mean;
                    counter++;
                    if(counter==60){
                        vals.add(sum/(float)counter);
                        counter = 0;
                        sum=0;
                    }
                }

                last_val = val;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return vals;
    }

}
