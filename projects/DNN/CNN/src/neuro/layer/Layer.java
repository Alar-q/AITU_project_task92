package neuro.layer;

import autograd.Node;
import func.Function;
import linal.core.MatFloat;
import linal.dims.D3D;
import linal.dims.Image;
import neuro.dropout.Dropout;

/** W and B обязательно должны быть инициализированны в конструкторе */
public abstract class Layer {
    public enum Type{
        DenseLayer, CNNLayer, RNNLayer;
    }

    protected D3D I, W, B, P;
    protected int nIn, nOut;
    protected final Function activation;
    protected Dropout dropout; // Всегда инициализируется

    /** Dropout */
    public Layer(int nIn, int nOut, Function activation, float dropout){
        this.activation = activation;
        setnIn(nIn);
        setnOut(nOut);
        this.dropout = new Dropout(dropout);
    }

    public Layer(int nIn, int nOut, Function activation){
        this(nIn, nOut, activation, 0); // dropout = 0 значит его не будет
    }
    public Layer(D3D W, D3D B, Function activation){
        this.activation = activation;
        this.W = W;
        this.B = B;
        setnOut(B.row());
    }

    public abstract void initWB();

    /** Важно знать какого вида будет вход */
    public abstract void build_gradient(D3D I);

    public abstract void forward();
    public abstract void backward(MatFloat[] backward_grad);
    public abstract void backward();


    /** Getters / Setters */

    public D3D _P_(){
        return P;
    }
    /** Use only if it is first layer */
    public void _set_P_(D3D P){
        this.P = P;
    }
    /** Use only if it is first layer */

    public int getnIn(){
        return nIn;
    }
    public void setnIn(int nIn){
        this.nIn = nIn;
    }
    public int getnOut(){
        return nOut;
    }
    public void setnOut(int nOut) {
        this.nOut = nOut;
    }

    public MatFloat[] W_grad(){
        return W.getChannels();
    }
    public MatFloat[] B_grad(){
        return B.getGrads();
    }
    public D3D _W_(){
        return W;
    }
    public D3D _B_(){
        return B;
    }
    public void setInput(MatFloat[] Input){
        for(int i=0; i<Input.length; i++)
            I.set(i, Input[i]);
    }
    public void setInput(D3D I){
        this.I = I;
    }

    public Dropout getDropout(){
        return dropout;
    }


    protected static MatFloat Glorot(int row, int col){
        return MatFloat.rand(row, col, -2f/(row+col), 2f/(row+col));
    }
}
