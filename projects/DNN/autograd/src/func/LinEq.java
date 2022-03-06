package func;

import linal.core.Core;
import linal.core.MatFloat;

/** Linear equation Ax = b where vectors are row vectors
 * Все размерности векторов и матриц известны сразу, никакие размерности не вычисляются на "ходу"
 * f(X) = XA + B = Y, where X,Y,B - row-vector,
 * Весь метод транспонированный какой-то
 * */

public class LinEq extends Function {
    protected MatFloat A, B; //A same as W

    /**
     * W.row = X.col, W.col = Y.col, A = W
     * B.size = Y.size = P.size
     * rX = rY
     * */
    public LinEq(int X_row, int X_col, int Y_row, int Y_col){
        A = new MatFloat(X_col, Y_col).rand();
        B = new MatFloat(Y_row, Y_col).rand();
    }

    public LinEq(MatFloat A, MatFloat B){
        setA(A);
        setB(B);
    }

    @Override
    public MatFloat f(MatFloat X, MatFloat Y) {
        Core.dot(X, A, Y).add(B); //Ax + B = Y + B
        return Y;
    }

    /**
     * W.row = X.col, W.col = Y.col
     * B.size = Y.size = P.size
     * rX = rY
     * */
    @Override
    public MatFloat f(MatFloat X) {
        return f(X, new MatFloat(X.row(), A.col()));
    }

    @Override
    public MatFloat der(MatFloat dX, MatFloat dYdX) {
        //Размерность должна быть такой же как и у матрицы A
        return Core.t(dX, dYdX);
    }
    @Override
    public MatFloat der(MatFloat dX) {
        MatFloat m = new MatFloat(dX.col(), dX.row());
        return der(dX, m);
    }


    /**
     * Getters and Setters
     * */
    public void setA(MatFloat A){
        this.A = A;
    }
    public void setB(MatFloat B){
        this.B = B;
    }
    public MatFloat getA(){
        return A;
    }
    public MatFloat getB(){
        return B;
    }
}
