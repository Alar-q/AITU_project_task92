package neuro.samples;

import func.Linear;
import func.Tanh;
import linal.core.MatFloat;
import neuro.optimizer.LRDecay;
import neuro.NeuralNetwork;
import neuro.Trainer;
import neuro.loss.CrossEntropy;
import neuro.Optimizer;
import neuro.optimizer.SGDMomentum;

public class Building_Example {
    public static void main(String[] args){
        new Building_Example().main();
    }

    int epochs = 10;

    void main(){
        NeuralNetwork nn = new NeuralNetwork()
                .addDense(3, 10, new Tanh())
                .addDense(10, 3, new Linear())
                .setBatches(2)
                .build();

        Optimizer optimizer = new SGDMomentum(nn, new CrossEntropy(), 0.8f)
                .setLRDecay(new LRDecay(0.1f, 0.01f, epochs, LRDecay.Type.LINEAR));
        Trainer trainer = new Trainer(nn, optimizer);

        MatFloat I = new MatFloat(new float[][]{
                {0.2f, 0.3f, 0.3f},
                {0.2f, 0.3f, 0.3f}}).setName("I");
        MatFloat T = new MatFloat(new float[][]{
                {0, 0, 1},
                {1, 0, 0}}).setName("T");


        for(int i=0; i<epochs; i++) {
            trainer.fit(I, T);
            System.out.println(nn.P().setName("P"));
        }
    }


    /* Если нужно будет посчитать время вычисления */
    long time;
    void startTime(){
        time = System.nanoTime();
    }
    void endTime(){
        System.out.println((System.nanoTime() - time)/1000000000d);
    }
}
