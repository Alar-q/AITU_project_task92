package nn.neuro;

import nn.autograd.Node;
import nn.func.Function;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.layer.dropout.Dropout;

public abstract class Layer {
    public enum Type{
        DenseLayer, CNNLayer, RNNLayer, LSTMLayer;
    }

    protected D3D W3D, B3D;
    protected D3D I3D, P3D;
    protected int nIn, nOut; //Что в них вписывать на сверточных?
    protected final Function activation;
    protected Dropout dropout; // Всегда при трене инициализируется

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
    //
    public Layer(MatFloat[] W3D, MatFloat[] B3D, Function activation){
        this.activation = activation;
        this.W3D = new D3D(W3D);
        this.B3D = new D3D(B3D);
        setnOut(B3D[0].row());
    }

    public abstract void initWB();

    /** Важно знать какого вида будет вход + для графа нужно */
    public abstract void build_gradient(Node[] I);

    public void output(){
        forward();
        //Если нет dropout-a, он не работает
        dropout.dropout(P3D.getMats());
    }

    public abstract void forward();




    /** Getters / Setters */

    public Node[] _P_(){
        return P3D._get_Nodes_();
    }
    public MatFloat[] getP(){
        return P3D.getMats();
    }
    /** Use only if it is first layer */
    public void _set_P3D_(Node ... P){
        this.P3D = new D3D(P);
    }
    public void _set_I3D_(Node ... I){
        this.I3D = new D3D(I);
    }
    /** Use only if it is first layer */
    public void setP(MatFloat[] P){
        this.P3D.setMats(P);
    }

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

    public MatFloat[] W(){
        return W3D.getMats();
    }
    public MatFloat[] B(){
        return B3D.getMats();
    }
    public MatFloat[] W_grad(){
        return W3D.getGrads();
    }
    public MatFloat[] B_grad(){
        return B3D.getGrads();
    }
    public Node[] _W_(){
        return W3D._get_Nodes_();
    }
    public Node[] _B_(){
        return B3D._get_Nodes_();
    }

    public Dropout getDropout(){
        return dropout;
    }


    public static MatFloat Glorot(int row, int col){
        return new MatFloat(row, col).rand(-2f/(row+col), 2f/(row+col));
    }
}
