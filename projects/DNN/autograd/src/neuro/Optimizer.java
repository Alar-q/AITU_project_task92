package neuro;

import linal.core.MatFloat;
import neuro.NeuralNetwork;

public abstract class Optimizer {
    protected NeuralNetwork nn;

    public Optimizer(NeuralNetwork nn){
        this.nn = nn;
    }

    public abstract void step(MatFloat T, MatFloat P);
}
