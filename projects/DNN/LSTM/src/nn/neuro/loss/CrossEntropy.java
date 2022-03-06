package nn.neuro.loss;

import nn.linal.core.Core;
import nn.linal.core.MatFloat;
import nn.neuro.Loss;

import static nn.func.Softmax.Softmax;

public class CrossEntropy extends Loss {
    public static float f(float t, float p){return (-t*(float)Math.exp(p) - (1f-t)*(float)Math.log(1-p));}
    @Override
    public MatFloat grad(MatFloat T, MatFloat P, float scale){
        if(grad == null)
            grad = new MatFloat(P.row(), P.col());
        Softmax(P, grad);
        return Core.sub(T, grad, -scale, grad);
    }

    @Override
    public MatFloat grad(MatFloat T, MatFloat P){
        return grad(T, P, 1f);
    }

    @Override
    public float loss(MatFloat T, MatFloat P) {
        return 0;
    }

}
