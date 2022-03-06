package nn.func;

import nn.linal.core.MatFloat;

/**
 * Passes elements of a matrix element by element through functions
 */
public interface Function {
    /** X - src, Y - dst , returns Y */
    public abstract MatFloat f(MatFloat X, MatFloat Y);

    /** Need to create a new matrix like:
        MatFloat Y = new MatFloat(row, col);
        return f(X, Y);
     */
    public abstract MatFloat f(MatFloat X);

    /** Y - src, dYdX - dst(derivative itself) */
    public abstract MatFloat der(MatFloat Y, MatFloat dYdX);

    /** Need to create a new matrix */
    public abstract MatFloat der(MatFloat Y);

    /** y(x) = E(f(x))
     *  y'(x) = dydx = E'(f) * f'(x)
     */
}
