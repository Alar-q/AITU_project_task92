package nn.neuro;

import nn.linal.core.MatFloat;

/**
 * Выход нейронной сети в моем случае всегда представляет собой вектор.
 * У CNN - flatten слой,
 * Input не превышает 3D: у CNN ширина х высота х каналы
 * Изначально надо было писать Tensor, а не Матрицу.
 * из-за CNN не получается сделать красивую абстракцию пакетного обучения,
 * В таком случае нужно 4-е измерение.
 *
 * RNN: входные и выходные - вектора, но веса внутри 3D.
 * */

public class Trainer {
    private NeuralNetwork nn;
    private Optimizer optimizer;

    public Trainer(NeuralNetwork nn, Optimizer optimizer){
        this.nn = nn;
        this.optimizer = optimizer;
    }

    /**
     * fit обучает только на одном примере, их нужно менять каждый раз
     * Нет пакетного обучения
     * */
    public void fit(MatFloat[] I, MatFloat T) {
        //Predict
        nn.predict(I);
        //update Weights and Biases
        optimizer.step(T);
    }




}
