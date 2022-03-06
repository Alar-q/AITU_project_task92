package func;

import linal.core.MatFloat;

public class Softmax extends Function {
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
    public MatFloat der(MatFloat dX, MatFloat dYdX) {
        for(int r = 0; r< dX.row(); r++)
            for(int c = 0; c< dX.col(); c++)
                dYdX.set(r, c, Sigmoid.der(dX.get(r, c)));
        return dYdX;
    }

    @Override
    public MatFloat der(MatFloat dX) {
        return der(dX, new MatFloat(dX.row(), dX.col()));
    }
}
