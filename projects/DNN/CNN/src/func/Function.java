package func;

import linal.core.MatFloat;

/**
 * Passes elements of a matrix element by element through functions
 */

public abstract class Function {
    /** X - src, Y - dst , returns Y */
    public abstract MatFloat f(MatFloat X, MatFloat Y);

    /** Need to create a new matrix like:
        MatFloat Y = new MatFloat(row, col);
        return f(X, Y);
     */
    public abstract MatFloat f(MatFloat X);

    /** dX - src, dYdX - dst(derivative itself) */
    public abstract MatFloat der(MatFloat dX, MatFloat dYdX);

    /** Need to create a new matrix */
    public abstract MatFloat der(MatFloat dX);

    /** y(x) = E(f(x))
     *  y'(x) = dydx = E'(f) * f'(x)
     */
}
