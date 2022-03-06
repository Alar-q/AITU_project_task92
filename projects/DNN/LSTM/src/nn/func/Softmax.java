package nn.func;

import nn.linal.core.MatFloat;

public class Softmax implements Function {
    /** by rows */
    public static MatFloat Softmax(MatFloat src, MatFloat dst){
        for(int r=0; r<src.row(); r++) {
            float sum = 0;
            for (int c = 0; c < src.col(); c++) {
                dst.set(r, c, (float) Math.exp(src.get(r, c))); //e^x
                sum += dst.get(r, c); // sum += e^x
            }
            for(int c = 0; c < src.col(); c++) {
                dst.set(r, c, dst.get(r, c) / sum); // e^x1 / (e^x1 + e^x2 + e^x3)
            }
        }
        return dst;
    }

    @Override
    public MatFloat f(MatFloat X, MatFloat Y) {
        return Softmax(X, Y);
    }

    @Override
    public MatFloat f(MatFloat X) {
        return f(X, new MatFloat(X.row(), X.col()));
    }

    @Override
    public MatFloat der(MatFloat Y, MatFloat dYdX) {
        for(int r = 0; r< Y.row(); r++)
            for(int c = 0; c< Y.col(); c++)
                dYdX.set(r, c, Sigmoid.der(Y.get(r, c)));
        return dYdX;
    }

    @Override
    public MatFloat der(MatFloat Y) {
        return der(Y, new MatFloat(Y.row(), Y.col()));
    }
}
