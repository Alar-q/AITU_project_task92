package neuro;

import linal.core.MatFloat;

public abstract class Loss {
    public enum Type{
        MSE, CrossEntropy;
    }
    protected MatFloat grad;

    /** Возвращает потерю на нейронах */
    public abstract MatFloat grad(MatFloat T, MatFloat P, float scale);
    public abstract MatFloat grad(MatFloat T, MatFloat P);

    /** оценка ошибки */
    public abstract float loss(MatFloat T, MatFloat P);

    public MatFloat getGrad(){
        return grad;
    }
}
