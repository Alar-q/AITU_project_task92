import nn.func.Sigmoid;
import nn.func.Tanh;
import nn.linal.core.MatFloat;
import nn.neuro.NeuralNetwork;
import nn.neuro.Optimizer;
import nn.neuro.Trainer;
import nn.neuro.layer.DenseLayer;
import nn.neuro.layer.LSTMLayer;
import nn.neuro.loss.CrossEntropy;
import nn.neuro.optimizer.SGD;

/**
 * Переобучение модели,
 * можно и апроксиммировать какую-нибудь функцию
 * */
public class Main {
    public static void main(String[] args){
        new Main().main();
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
        Input.set(0,0, val0/12f);
        Input.set(0, 1, val1/12f);
    }

    void main(){
        NeuralNetwork nn = new NeuralNetwork()
                .setInputSize(1, 2)
                .addLayer(new LSTMLayer(2, 20, new Tanh()))
                .addLayer(new DenseLayer(20, 4, new Sigmoid()))
                .build();
        Optimizer optimizer = new SGD(nn, new CrossEntropy());
        Trainer trainer = new Trainer(nn, optimizer);

        setI(0, 1);
        setT(1);
        System.out.println(I[0].setName("I"));
        System.out.println(T.setName("T"));

        for(int e=0; e<10000; e++) {
            trainer.fit(I, T);
        }

        System.out.println(nn.P().setName("P"));
    }
}
