package func;

import linal.core.MatFloat;

public class Tanh extends Function {

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
    public MatFloat der(MatFloat dX, MatFloat dYdX) {
        for(int r = 0; r< dX.row(); r++)
            for(int c = 0; c< dX.col(); c++)
                dYdX.set(r, c, der(dX.get(r, c)));
        return dYdX;
    }

    @Override
    public MatFloat der(MatFloat dX) {
        return der(dX, new MatFloat(dX.row(), dX.col()));
    }
}
