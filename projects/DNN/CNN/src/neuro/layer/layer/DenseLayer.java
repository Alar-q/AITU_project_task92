package neuro.layer.layer;

import autograd.Node;
import func.Function;
import linal.core.MatFloat;
import linal.dims.D3D;
import neuro.layer.Layer;

/**
 * Здесь вообще по идее не нужен D3D. Он здесь имеет размер 1 x width x height
 * P = act(Y) = act(S + B) = act(IW + B) */
public class DenseLayer extends Layer {
    public final Type type = Type.DenseLayer;

    private D3D S, Y;

    public DenseLayer(int nIn, int nOut, Function activation){
        super(nIn, nOut, activation); //Layer(nIn, nOut, activation, 0)
    }
    public DenseLayer(int nIn, int nOut, Function activation, float dropout){
        super(nIn, nOut, activation, dropout);
    }
    public DenseLayer(D3D W, D3D B, Function activation){
        super(W, B, activation);
    }

    @Override
    public void initWB() {
        if (W == null || B == null) {
            //Glorot по умолчанию
            W = new D3D(new Node[]{new Node(Glorot(nIn, nOut))});
            //(Пакетное обучение) Гораздо легче здесь просто создать маску
            MatFloat B_row = new MatFloat(1, nOut).rand(-1f / nOut, 1f / nOut);
            //MatFloat B = MatFloat.zeros(batches, nOut);Core.add(B, B_row, B, 0)
            this.B = new D3D(new Node[]{new Node(B_row)});
        }
    }

    /** Важно знать какого вида будет вход */
    @Override
    public void build_gradient(D3D I){
        setInput(I);
        initWB();
        S = new D3D(new Node[]{ new Node(new MatFloat(1, nOut))});
        Y = new D3D(new Node[]{ new Node(new MatFloat(1, nOut))});
        S._set_(0, I._dot_(0, W._get_(0)));
        Y._set_(0, S._add_(0, B._get_(0)));
        P = new D3D(new Node[]{Y._func_(0, activation)});
    }

    @Override
    public void forward(){
        I._dot_(0, W._get_(0), S._get_(0));
        S._add_(0, B._get_(0), Y._get_(0));
        Y._func_(0, activation, P._get_(0));
    }

    /** Здесь backward - вектор по-любому */
    @Override
    public void backward(MatFloat[] backward_grad){
        P._get_(0)._backward_(backward_grad[0]);
    }
    @Override
    public void backward(){
        P._get_(0)._backward_();
    }



}
