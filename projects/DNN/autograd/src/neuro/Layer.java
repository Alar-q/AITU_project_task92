package neuro;

import autograd.Node;
import func.Function;
import linal.core.MatFloat;
import neuro.layer.dropout.Dropout;

/** W and B обязательно должны быть инициализированны в конструкторе */
public abstract class Layer {
    protected Node I, W, B, P;
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
    public Layer(MatFloat W, MatFloat B, Function activation){
        this.activation = activation;
        this.W = new Node(W);
        this.B = new Node(B);
        setnOut(B.row());
    }

    public abstract void initWB(int batches);

    /** Важно знать какого вида будет вход */
    public abstract void build_gradient(Node I);

    public void output(){
        forward();
        dropout.dropout(P.mat()); //Если нет, он не работает
    }

    public abstract void forward();




    /** Getters / Setters */

    public Node _P_(){
        return P;
    }
    public MatFloat getP(){
        return P.mat();
    }
    /** Use only if it is first layer */
    public void _set_P_(Node P){
        this.P = P;
    }
    /** Use only if it is first layer */
    public void setP(MatFloat P){
        this.P.setMat(P);
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

    public MatFloat W(){
        return W.mat();
    }
    public MatFloat B(){
        return B.mat();
    }
    public MatFloat W_grad(){
        return W.grad();
    }
    public MatFloat B_grad(){
        return B.grad();
    }
    public Node _W_(){
        return W;
    }
    public Node _B_(){
        return B;
    }

    public Dropout getDropout(){
        return dropout;
    }

}
