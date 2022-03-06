package nn.neuro.layer;

import nn.autograd.Node;
import nn.func.Function;
import nn.func.Sigmoid;
import nn.func.Tanh;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.Layer;

public class LSTMLayer extends Layer {
    private final Function sigmoid = new Sigmoid();
    private final Function tanh = new Tanh();

    public LSTMLayer(int nIn, int nOut, Function activation){
        super(nIn, nOut, activation);
    }
    public LSTMLayer(int nIn, int nOut, Function activation, float dropout){
        super(nIn, nOut, activation, dropout);
    }
    public LSTMLayer(MatFloat[] W3D, MatFloat[] B3D, Function activation){
        super(W3D, B3D, activation);
    }

    // H - last_P like
    Node I, H, X, P;
    Node C; //State
    Node        f,  i,  C1, o;
    Node W, Wh, Wf, Wi, Wc, Wo;
    Node   B,   Bf, Bi, Bc, Bo;

    @Override
    public void initWB() {
        if (W3D == null || B3D == null) {
            //Glorot по умолчанию
            W = new Node(Glorot(nIn, nOut));
            B = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));

            Wh = new Node(Glorot(nOut, nOut));

            Wf = new Node(Glorot(nOut, nOut));
            Bf = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));
            Wi = new Node(Glorot(nOut, nOut));
            Bi = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));
            Wc = new Node(Glorot(nOut, nOut));
            Bc = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));
            Wo = new Node(Glorot(nOut, nOut));
            Bo = new Node(new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut));

            W3D = new D3D(W, Wh, Wf, Wi, Wc, Wo);
            B3D = new D3D(  B,   Bf, Bi, Bc, Bo);

        }
    }


    //посредственные узлы
    private Node m1, m2, m3, m4;
    private Node m5, m6;
    private Node m7, m8;
    private Node m9, m10;
    private Node m11;
    private Node m12, m13;
    private Node m14;
    private Node c1, c2;

    /**
     * @param Input на начальном слою задаем его сами,
     *              дальше назначается как выход предыдущего слоя
     * */
    @Override
    public void build_gradient(Node[] Input) {
        _set_I3D_(Input);
        I = Input[0];
        H = new Node(new MatFloat(B.row(), B.col()));
        C = new Node(new MatFloat(B.row(), B.col()));
        /**
         *  P = I*W + H*W_h + B
         * */
        m1 = I._dot_(W);
        m2 = H._dot_(Wh);
        m3 = m1._add_(m2);
        m4 = m3._add_(B);
        //Дальше это значение используется в 4-х местах
        X = m4._func_(tanh);

        //forget
        m5 = X._dot_(Wf);
        m6 = m5._add_(Bf);
        f = m6._func_(sigmoid);

        //input
        m7 = X._dot_(Wi);
        m8 = m7._add_(Bi);
        i = m8._func_(sigmoid);

        m9 = X._dot_(Wc);
        m10 = m9._add_(Bc);
        C1 = m10._func_(tanh);

        //Change state
        c1 = C._mul_(f);
        m11 = i._mul_(C1);
        c2 = c1._add_(m11);

        //output
        m12 = X._dot_(Wo);
        m13 = m12._add_(Bo);
        o = m13._func_(sigmoid);

        m14 = c2._func_(activation);
        P = o._mul_(m14);

        _set_P3D_(P);
    }

    @Override
    public void forward() {
        //System.out.println("FORWARD START");
        //System.out.println(P.setName("P"));
        //System.out.println(H.setName("H"));
        H.mat().set(P.mat());
        /**
         *  P = I*W + H*W_h + B
         * */
        I._dot_(W, m1);
        H._dot_(Wh, m2);
        m1._add_(m2, m3);
        m3._add_(B, m4);
        //Дальше это значение используется в 4-х местах
        m4._func_(tanh, X);

        //forget
        X._dot_(Wf, m5);
        m5._add_(Bf, m6);
        m6._func_(sigmoid, f);

        //input
        X._dot_(Wi, m7);
        m7._add_(Bi,m8);
        m8._func_(sigmoid, i);

        X._dot_(Wc, m9);
        m9._add_(Bc, m10);
        m10._func_(tanh, C1);

        //Change state
        C._mul_(f, c1);
        i._mul_(C1, m11);
        c1._add_(m11, c2);

        //output
        X._dot_(Wo, m12);
        m12._add_(Bo, m13);
        m13._func_(sigmoid, o);

        c2._func_(activation, m14);
        o._mul_(m14, P);

        /*
        System.out.println(H.setName("H"));
        System.out.println(C.setName("State"));
        System.out.println(f.setName("forget"));
        System.out.println(i.setName("input"));
        System.out.println(o.setName("output"));
        System.out.println(C1.setName("~state~"));
        System.out.println(c2.setName("new state"));
        System.out.println(m14.setName("tanh(state)"));
        System.out.println("FORWARD END");

         */

        C.mat().set(c2.mat());
        //System.out.println(C);
    }
}
