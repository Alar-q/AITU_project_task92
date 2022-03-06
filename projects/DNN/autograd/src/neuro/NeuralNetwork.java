package neuro;

import autograd.Node;
import func.Function;
import linal.core.MatFloat;
import neuro.layer.DenseLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Первый слой содержит только Input значения.
 * */
public class NeuralNetwork {

    private List<Layer> layers;

    private int batches = 1;

    public NeuralNetwork(){ // 2 - 4 - 4 - 2
        layers = new ArrayList<>();
    }

    public NeuralNetwork addDense(DenseLayer denseLayer){
        //Первый слой - просто слой нейронов со значениями
        if(layers.size() < 2)
            layers.add(new DenseLayer(1, denseLayer.getnIn(), null));

        layers.add(denseLayer);
        return this;
    }
    public NeuralNetwork addDense(int nIn, int nOut, Function activation, float dropout){
        return addDense(new DenseLayer(nIn, nOut, activation, dropout));
    }
    public NeuralNetwork addDense(int nIn, int nOut, Function activation){
        return addDense(new DenseLayer(nIn, nOut, activation));
    }
    /** Прочесть уже готовую нейронку */
    public NeuralNetwork addDense(MatFloat W, MatFloat B, Function activation){
        return addDense(new DenseLayer(W, B, activation));
    }

    public NeuralNetwork build(){
        //След 3 строчки строят градиент
        //Первый слой - просто слой нейронов со значениями
        layers.get(0)._set_P_(new Node(new MatFloat(batches, layers.get(0).getnOut())));

        for(Layer l: layers)
            l.initWB(batches);

        //Без этого не получится построить autograd
        for(int i = 1; i< layers.size(); i++)
            layers.get(i).build_gradient(layers.get(i-1)._P_());

        return this;
    }

    /** returns origin Prediction of last layer */
    public MatFloat predict(MatFloat I){
        layers.get(0).setP(I);
        for(int i = 1; i< layers.size(); i++){
            layers.get(i).output();
        }
        return layers.get(layers.size()-1).getP();
    }

    /** Getters / Setters */

    public NeuralNetwork setBatches(int batches){
        this.batches = batches;
        return this;
    }

    /** returns origin Prediction of last layer */
    public MatFloat P(){
        return layers.get(layers.size()-1).getP();
    }
    /** returns origin Node Prediction of last layer */
    public Node _P_(){
        return layers.get(layers.size()-1)._P_();
    }

    public List<Layer> getLayers(){
        return layers;
    }

    /** Может еще появятся функции выполняемые только во время обучения */
    public void learning(boolean isLearning){
        dropout(isLearning);
    }
    private NeuralNetwork dropout(boolean isDropout){
        for(Layer l: layers){
            l.getDropout().setDropout(isDropout);
        }
        return this;
    }
}
