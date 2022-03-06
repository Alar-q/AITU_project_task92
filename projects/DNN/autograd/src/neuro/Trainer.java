package neuro;

import linal.core.MatFloat;

public class Trainer {
    private NeuralNetwork nn;
    private Optimizer optimizer;

    public Trainer(NeuralNetwork nn, Optimizer optimizer){
        this.nn = nn;
        this.optimizer = optimizer;
    }

    public void fit(MatFloat I, MatFloat T) {
        //Predict
        nn.predict(I);
        //update Weights and Biases
        optimizer.step(T, nn.P());
    }




}
