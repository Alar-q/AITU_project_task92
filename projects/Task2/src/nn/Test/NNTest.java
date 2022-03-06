package nn.Test;

import nn.func.Sigmoid;
import nn.linal.core.MatFloat;
import nn.neuro.layer.DenseLayer;
import nn.neuro.optimizer.LRDecay;
import nn.neuro.loss.CrossEntropy;
import nn.neuro.NeuralNetwork;
import nn.neuro.Trainer;
import nn.neuro.Optimizer;
import nn.neuro.optimizer.SGDMomentum;

/**
 */
public class NNTest {
    public static void main(String[] args){
        System.out.println("---DNN TEST---");
        new NNTest().main();
    }

    int epochs = 1000;

    MatFloat[] I = new MatFloat[]{new MatFloat(new float[][]{
            {0.7f, 0.1f, 0.9f}
    })};

    MatFloat T = new MatFloat(new float[][]{
            {1, 0, 1}
    });

    void main(){
        NeuralNetwork nn = new NeuralNetwork()
                .setInputSize(1, 3)
                .addLayer(new DenseLayer(3, 100, new Sigmoid(), 0.5f))
                .addLayer(new DenseLayer(100, 3, new Sigmoid()))
                .build();

        Optimizer optimizer = new SGDMomentum(nn, new CrossEntropy(), 0.8f)
                .setLRDecay(new LRDecay(0.1f, 0.01f, epochs, LRDecay.Type.LINEAR));
        Trainer trainer = new Trainer(nn, optimizer);

        for(int i=0; i<epochs; i++) {
            trainer.fit(I, T);
            System.out.println(nn.P());
        }
    }
}
