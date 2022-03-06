package main;

import csv.converter.Converter;
import csv.generator.Generator;
import csv.utils.LabelsUtil;
import csv.utils.Visualization;
import javafx.application.Application;
import javafx.stage.Stage;
import nn.func.Sigmoid;
import nn.func.Softmax;
import nn.func.Tanh;
import nn.linal.core.MatFloat;
import nn.neuro.NeuralNetwork;
import nn.neuro.Optimizer;
import nn.neuro.Trainer;
import nn.neuro.layer.DenseLayer;
import nn.neuro.layer.LSTMLayer;
import nn.neuro.loss.CrossEntropy;
import nn.neuro.loss.MSE;
import nn.neuro.optimizer.SGD;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        main();
        stage.show();stage.close();
    }


    NeuralNetwork nn;
    Trainer trainer;
    Optimizer optimizer;

    void main() {
        nn = new NeuralNetwork()
                .setInputSize(1, 2)
                .addLayer(new LSTMLayer(2, 20, new Tanh()))
                .addLayer(new LSTMLayer(20, 4, new Tanh()))
                .build();
        optimizer = new SGD(nn, new MSE());
        trainer = new Trainer(nn, optimizer);

        String path = "C:\\Java\\6.csv";
        List<Float> vals = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        Converter.convert(path, vals, labels);

        for (int i=0; i<10000; i++) {
            if(i%1000==0) {
                System.out.println(i);
                new Generator();
                vals.clear();
                labels.clear();
                Converter.convert(path, vals, labels);
            }


            train(vals, labels);

            //Visualization.show(vals, path);
        }
        //Visualization.showLabels(vals, labels, path, true);

        for(int i=0; i<5;i++) {
            new Generator();
            vals.clear();
            labels.clear();
            Converter.convert(path, vals, labels);
            check(vals, labels);
        }
    }

    void train(List<Float> vals, List<Integer> labels){
        for(int i=1; i<vals.size(); i++) {
            setI(vals.get(i-1), vals.get(i));
            setT(labels.get(i));
            System.out.println(T.setName("T"));
            trainer.fit(I, T);
            System.out.println(nn.P().setName("P"));
        }

    }

    int c=0;
    void check(List<Float> vals, List<Integer> labels){
        List<Integer> nn_label = new ArrayList<>();
        for(int i=0; i<vals.size(); i++) {
            if(i==0)
                setI(vals.get(i), vals.get(i));
            else setI(vals.get(i-1), vals.get(i));
            setT(labels.get(i));
            nn.predict(I);
            int l = maxCol(nn.P());
            nn_label.add(l);
        }
        Visualization.showLabels(vals, labels, "SRC"+c, true);
        Visualization.showLabels(vals, nn_label, "NN"+c++, true);
    }

    MatFloat Input = new MatFloat(1, 2);

    MatFloat[] I = new MatFloat[]{Input};
    MatFloat T = new MatFloat(new float[][]{
            {0, 0, 0, 0}
    });
    void setT(int label){
        T.set(0);
        T.set(0, label, 1);
    }
    /** Добавляет в конец */
    void setI(float val0, float val1){
        Input.set(0,0, val0);
        Input.set(0, 1, val1);
    }

    int maxCol(MatFloat vector){
        float max = vector.get(0, 0);
        int id=0;
        for(int i=0; i<vector.col(); i++){
            if (max<vector.get(0, i)){
                max = vector.get(0, i);
                id = i;
            }
        }
        return id;
    }

}
