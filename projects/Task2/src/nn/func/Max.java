package nn.func;

import nn.linal.core.MatFloat;

public class Max implements Function {
    @Override
    public MatFloat f(MatFloat X, MatFloat Y) {
        for(int r=0; r<X.row(); r++){
            int cmax = 0;
            for(int c=0; c<X.col(); c++)
                if(X.get(r, cmax) < X.get(r, c))
                    cmax = c;

            for(int c=0; c<X.col(); c++)
                Y.set(r, c, 0);
            Y.set(r, cmax, 1);
        }
        return Y;
    }

    @Override
    public MatFloat f(MatFloat X) {
        return f(X, new MatFloat(X.row(), X.col()));
    }

    @Override
    public MatFloat der(MatFloat Y, MatFloat dYdX) {
        return null;
    }

    @Override
    public MatFloat der(MatFloat Y) {
        return null;
    }

}
