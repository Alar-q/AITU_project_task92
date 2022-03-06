import func.Linear;
import func.Sigmoid;
import func.Tanh;
import linal.core.MatFloat;
import neuro.optimizer.LRDecay;
import neuro.loss.CrossEntropy;
import neuro.NeuralNetwork;
import neuro.Trainer;
import neuro.Optimizer;
import neuro.optimizer.SGDMomentum;

/**
 *  Нужно дописать Loss
 *  Градиенты должны идти от Loss
 *  Дописать learningRate
 *  Желательно было бы дописать дропаут, снижение скорости обучения
 */
public class Main {
    public static void main(String[] args){
        new Main().main();
    }

    int epochs = 100000;

    MatFloat I = new MatFloat(new float[][]{
            {0.2f, 0.3f, 0.3f},
            {0.7f, 0.1f, 0.9f}}).setName("I");
    MatFloat T = new MatFloat(new float[][]{
            {0, 0, 1},
            {0, 1, 0}}).setName("T");

    void main(){
        NeuralNetwork nn = new NeuralNetwork()
                .addDense(3, 100, new Tanh(), 0.5f)
                .addDense(100, 3, new Sigmoid())
                .setBatches(2)
                .build();

        Optimizer optimizer = new SGDMomentum(nn, new CrossEntropy(), 0.8f)
                .setLRDecay(new LRDecay(0.1f, 0.01f, epochs, LRDecay.Type.LINEAR));
        Trainer trainer = new Trainer(nn, optimizer);

        for(int i=0; i<epochs; i++) {
            trainer.fit(I, T);
            System.out.println(nn.P().setName("P"));
        }
    }


    long time;
    void startTime(){
        time = System.nanoTime();
    }
    void endTime(){
        System.out.println((System.nanoTime() - time)/1000000000d);
    }
}
