package nn.neuro.optimizer;

import nn.linal.core.Core;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.NeuralNetwork;
import nn.neuro.Layer;
import nn.neuro.Loss;

/** Нужно немного изменить
 *  Здесь нужен 4-мерный тензор
 *  Веса каждого слоя - 3D матрица, а мы должны хранить градиенты каждого слоя -> 4D
 * */
public class SGDMomentum extends SGD{
    private float momentum;

    private D3D old_gradsW;
    private D3D old_gradsB;

    public SGDMomentum(NeuralNetwork nn, Loss loss, float momentum){
        super(nn, loss);
        this.momentum = momentum;
        //init_old_grads();
    }

    /*
    private void init_old_grads(){
        old_gradsW = new MatFloat[nn.getLayers().size()];
        old_gradsB = new MatFloat[nn.getLayers().size()];
        for(int i=1;  i < nn.getLayers().size(); i++){
            Layer l = nn.getLayers().get(i);
            old_gradsW[i] = MatFloat.zeros(l.W().row(), l.W().col());
            old_gradsB[i] = MatFloat.zeros(l.B().row(), l.B().col());
        }
    }
     */

    /** w -= (grad + momentum * old_grad )
     * 1. ∇1
     * 2. ∇2 + μ × ∇1
     * 3. ∇3 + μ × (∇2 + μ × ∇1) = μ × (∇2 + μ2 × ∇1)
     * В этой реализации оптимизированно по сборщику мусора,
     * Но не оптимизированно по количеству циклов
     * */

    /*
    @Override
    protected void update(){
        // w -= (grad + momentum * old_grad )
        for(int i=1; i < nn.getLayers().size(); i++){
            Layer l = nn.getLayers().get(i);

            // old_grad *= momentum
            Core.scale(old_gradsW[i], momentum, old_gradsW[i]);
            Core.scale(old_gradsB[i], momentum, old_gradsB[i]);

            // old_grad += grad
            Core.add(l.W_grad(), old_gradsW[i], old_gradsW[i]);
            Core.add(l.B_grad(), old_gradsB[i], old_gradsB[i]);

            l.W().sub(old_gradsW[i]);
            l.B().sub(old_gradsB[i]);

        }
    }
    */




}
