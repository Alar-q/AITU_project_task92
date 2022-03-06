package nn.neuro.loss;

import nn.linal.core.Core;
import nn.linal.core.MatFloat;
import nn.neuro.Loss;

public class MSE extends Loss {

    public static float MSE(float t, float p){
        return (float) Math.pow((t - p), 2);
    }

    public static float MSE(MatFloat T, MatFloat P){
        return Core.sub(T, P).pow(2).mean();
    }

    @Override
    public MatFloat grad(MatFloat T, MatFloat P, float scale){
        if(grad == null)
            grad = new MatFloat(P.row(), P.col());
        return Core.sub(T, P, -scale, grad);
    }

    @Override
    public MatFloat grad(MatFloat T, MatFloat P){
        return grad(T, P, 1f);
    }

    @Override
    public float loss(MatFloat T, MatFloat P) {
        return MSE(T, P);
    }
}
