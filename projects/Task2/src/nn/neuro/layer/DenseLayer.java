package nn.neuro.layer;

import nn.autograd.Node;
import nn.func.Function;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.Layer;

/** P = act(Y) = act(S + B) = act(IW + B) */
public class DenseLayer extends Layer {
    private Node I, P;
    private Node W, B;
    private Node S, Y;

    public DenseLayer(int nIn, int nOut, Function activation){
        super(nIn, nOut, activation); //Layer(nIn, nOut, activation, 0)
    }
    public DenseLayer(int nIn, int nOut, Function activation, float dropout){
        super(nIn, nOut, activation, dropout);
    }
    public DenseLayer(MatFloat[] W3D, MatFloat[] B3D, Function activation){
        super(W3D, B3D, activation);
    }

    @Override
    public void initWB() {
        if (W3D == null || B3D == null) {
            //Glorot по умолчанию
            W = new Node(Glorot(nIn, nOut));
            MatFloat B_row = new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut);
            B = new Node(B_row);

            this.W3D = new D3D(W);
            this.B3D = new D3D(B);
        }
    }

    /** Важно знать какого вида будет вход */
    @Override
    public void build_gradient(Node[] Input){
        _set_I3D_(Input);
        I = Input[0];

        S = I._dot_(W);
        Y = S._add_(B, 0);
        P = Y._func_(activation);

        _set_P3D_(P);
    }

    @Override
    public void forward(){
        I._dot_(W, S);
        S._add_(B, Y);
        Y._func_(activation, P);
    }


}
