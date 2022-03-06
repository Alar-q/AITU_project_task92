package neuro.layer;

import autograd.Node;
import func.Function;
import linal.core.MatFloat;
import neuro.Layer;

/** P = act(Y) = act(S + B) = act(IW + B) */
public class DenseLayer extends Layer {
    private Node S, Y;

    public DenseLayer(int nIn, int nOut, Function activation){
        super(nIn, nOut, activation); //Layer(nIn, nOut, activation, 0)
    }
    public DenseLayer(int nIn, int nOut, Function activation, float dropout){
        super(nIn, nOut, activation, dropout);
    }
    public DenseLayer(MatFloat W, MatFloat B, Function activation){
        super(W, B, activation);
    }

    @Override
    public void initWB(int batches) {
        if (W == null || B == null) {
            //Glorot по умолчанию
            W = new Node(Glorot(nIn, nOut));
            //(Пакетное обучение) Гораздо легче здесь просто создать маску
            MatFloat B_row = new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut);
            //MatFloat B = MatFloat.zeros(batches, nOut);Core.add(B, B_row, B, 0)
            this.B = new Node(B_row);
        }
    }

    /** Важно знать какого вида будет вход */
    @Override
    public void build_gradient(Node I){
        this.I = I;
        S = I._dot_(W);
        Y = S._add_(B, 0);
        P = Y._func_(activation);
    }

    @Override
    public void forward(){
        I._dot_(W, S);
        S._add_(B, Y, 0);
        Y._func_(activation, P);
    }


    private static MatFloat Glorot(int row, int col){
        return new MatFloat(row, col).rand(-2f/(row+col), 2f/(row+col));
    }
}
