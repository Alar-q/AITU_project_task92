package nn.neuro;

import nn.linal.core.MatFloat;
import nn.neuro.NeuralNetwork;

public abstract class Optimizer {
    protected NeuralNetwork nn;

    public Optimizer(NeuralNetwork nn){
        this.nn = nn;
    }

    public abstract void step(MatFloat T);
}
