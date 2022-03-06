package nn.neuro.optimizer;

import nn.autograd.Node;
import nn.linal.core.MatFloat;
import nn.neuro.NeuralNetwork;
import nn.neuro.Layer;
import nn.neuro.Loss;
import nn.neuro.Optimizer;

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
    public void step(MatFloat T){
        //Count the error by given loss function
        loss.grad(T, nn.P(), lrDecay.lr());
        //Count gradients. Вообще-то всегда только один выход
        nn._P_()._backward_(loss.getGrad());
        //Change weights
        update();
        //Reduce learning rate
        lrDecay.update();
    }
    /** Default */
    protected void update(){
        for(int i=1; i<nn.getLayers().size(); i++) {
            Layer l = nn.getLayers().get(i);

            for (int p=0; p<l.W().length; p++)
                l.W()[p].sub(l.W_grad()[p]);

            for (int p=0; p<l.B().length; p++)
                l.B()[p].sub(l.B_grad()[p]);

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
