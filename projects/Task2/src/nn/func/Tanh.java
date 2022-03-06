package nn.func;

import nn.linal.core.MatFloat;

public class Tanh implements Function {

    public static float tanh(float x){
        return (2f/(1f+(float)Math.exp(-2f*x))) - 1f ;
    }
    public static float der(float y){
        return 1f - y*y;
    }

    /**X_size = Y_size*/
    @Override
    public MatFloat f(MatFloat X, MatFloat Y){
        for(int r=0; r<X.row(); r++)
            for(int c=0; c<X.col(); c++)
                Y.set(r, c, tanh(X.get(r, c)));
        return Y;
    }

    @Override
    public MatFloat f(MatFloat X) {
        return f(X, new MatFloat(X.row(), X.col()));
    }

    @Override
    public MatFloat der(MatFloat Y, MatFloat dYdX) {
        for(int r = 0; r< Y.row(); r++)
            for(int c = 0; c< Y.col(); c++)
                dYdX.set(r, c, der(Y.get(r, c)));
        return dYdX;
    }

    @Override
    public MatFloat der(MatFloat Y) {
        return der(Y, new MatFloat(Y.row(), Y.col()));
    }
}
