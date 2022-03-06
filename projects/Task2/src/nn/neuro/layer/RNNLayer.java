package nn.neuro.layer;

import nn.autograd.Node;
import nn.func.Function;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.Layer;

public class RNNLayer extends Layer {

    Node I, H, P;
    // Size W=(in x out), Wh=(out x out)
    Node W, Wh;
    //Size B = Bh = (1 x out)
    Node B;

    public RNNLayer(int nIn, int nOut, Function activation){
        super(nIn, nOut, activation);
    }
    public RNNLayer(MatFloat[] W3D, MatFloat[] B3D, Function activation){
        super(W3D, B3D, activation);
    }

    @Override
    public void initWB() {
        if (W3D == null || B3D == null) {
            //Glorot по умолчанию
            W = new Node(Glorot(nIn, nOut));
            B = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));

            Wh = new Node(Glorot(nOut, nOut));

            W3D = new D3D(W, Wh);
            B3D = new D3D(B);
        }
    }


    private Node m1, m2, m3, m4;
    /**
     * @param Input на начальном слою задаем его сами,
     *              дальше назначается как выход предыдущего слоя
     * */
    @Override
    public void build_gradient(Node[] Input) {
        _set_I3D_(Input);
        I = Input[0];
        H = new Node(new MatFloat(B.row(), B.col()));
        /**
         *  P = I*W + H*W_h + B
         * */
        m1 = I._dot_(W);
        m2 = H._dot_(Wh);
        m3 = m1._add_(m2);
        m4 = m3._add_(B);
        P = m4._func_(activation);

        _set_P3D_(P);
    }

    @Override
    public void forward() {
        H.mat().set(P.mat());
        I._dot_(W, m1);
        H._dot_(Wh, m2);
        m1._add_(m2, m3);
        m3._add_(B, m4);
        m4._func_(activation, P);
    }
}
