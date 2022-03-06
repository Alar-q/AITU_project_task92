package nn.neuro;

import nn.autograd.Node;
import nn.linal.core.MatFloat;
import nn.neuro.layer.InputLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Первый слой содержит только Input значения.
 * */
public class NeuralNetwork {

    private List<Layer> layers;

    //private int batches = 1;

    public NeuralNetwork(){ // 2 - 4 - 4 - 2
        layers = new ArrayList<>();
    }

    /**
     * Пересмотреть для сверток
     * */
    public NeuralNetwork addLayer(Layer layer){
        layers.add(layer);
        return this;
    }

    public NeuralNetwork setInputSize(int row, int col){
        layers.add(new InputLayer(new MatFloat(row, col)));
        return this;
    }

    public NeuralNetwork build(){
        for(Layer l: layers) l.initWB();

        //Без этого не получится построить autograd
        for(int i = 1; i<layers.size(); i++)
            layers.get(i).build_gradient(layers.get(i-1)._P_());
        return this;
    }

    /** returns origin Prediction of last layer */
    public MatFloat[] predict(MatFloat[] I){
        layers.get(0).setP(I);
        for(int i = 1; i< layers.size(); i++){
            layers.get(i).output();
        }
        return layers.get(layers.size()-1).getP();
    }

    /** Getters / Setters */

    /*
    public NeuralNetwork setBatches(int batches){
        this.batches = batches;
        return this;
    }
     */

    /** returns origin Prediction of last layer
     *  У меня нет батчей, потому что для свертки придется делать 4-е измерение
     * */
    public MatFloat P(){
        return layers.get(layers.size()-1).getP()[0];
    }
    /** returns origin Node Prediction of last layer */
    public Node _P_(){
        return layers.get(layers.size()-1)._P_()[0];
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
