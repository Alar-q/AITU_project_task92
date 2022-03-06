package neuro.optimizer;

import linal.core.MatFloat;
import neuro.NeuralNetwork;
import neuro.Layer;
import neuro.Loss;
import neuro.Optimizer;

public class SGD extends Optimizer {
    protected Loss loss;

    protected final float DEFAULT_LR = 0.1f;
    protected LRDecay lrDecay;

    public SGD(NeuralNetwork nn, Loss loss){
        super(nn);
        this.loss = loss;
        lrDecay = new LRDecay(DEFAULT_LR);
    }

    @Override
    public void step(MatFloat T, MatFloat P){
        //Count the error by given loss function
        loss.grad(T, P, lrDecay.lr());
        //Count gradients
        nn._P_()._backward_(loss.getGrad());
        //Change weights
        update();
        //Reduce learning rate
        lrDecay.update();
    }
    /** Default */
    protected void update(){
        for(Layer l: nn.getLayers()){
            l.W().sub(l.W_grad());
            l.B().sub(l.B_grad());
        }
    }

    public SGD setLRDecay(LRDecay lrDecay){
        this.lrDecay = lrDecay;
        return this;
    }
    public SGD setLRDecay(float lr, float final_lr, int max_epochs, LRDecay.Type type){
        return setLRDecay(new LRDecay(lr, final_lr, max_epochs, type));
    }
    public SGD setLR(float lr){
        return setLRDecay(new LRDecay(lr));
    }

}
