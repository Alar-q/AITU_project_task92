package func;

import linal.core.MatFloat;

/** F = Y(X) = X */
public class Linear extends Function {

    /**X_size = Y_size*/
    @Override
    public MatFloat f(MatFloat X, MatFloat Y){
        for(int r=0; r<X.row(); r++)
            for(int c=0; c<X.col(); c++)
                Y.set(r, c, X.get(r, c));
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
                dYdX.set(r, c, 1);
        return dYdX;
    }

    @Override
    public MatFloat der(MatFloat dX) {
        return der(dX, new MatFloat(dX.row(), dX.col()));
    }
}
