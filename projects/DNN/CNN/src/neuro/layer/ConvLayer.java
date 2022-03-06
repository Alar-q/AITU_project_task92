package neuro.layer;

import autograd.Node;
import func.Function;
import linal.core.Core;
import linal.core.MatFloat;
import linal.dims.D3D;

import static autograd.Methods._m3D2vec_;


/**
 *  Если следующий слой полносвязный, то у нас расходятся Node-ы Y & Flatten
 *  И придется в backward-e вызывать backward Y._backward_(Flatten_grad)
 * */
public class ConvLayer extends Layer{
    public final Type type = Type.CNNLayer;

    public boolean isFlatten = false;
    public ConvLayer isFlatten(boolean b){
        isFlatten = b;
        return this;
    }

    private D3D YC,YB; //Convolution, Bias, ->P=function

    private int step;

    /** row vector: get(0, 0, id) */
    private D3D Flatten;

    /** Без батчей, здесь каналы входной матрицы - цветовые каналы, например */

    /*int IRow, int ICol, int Orow, int Ocol, */
    public ConvLayer(D3D kernels, D3D B, int step, Function function){
        super(kernels, B, function);

        init_(kernels, B, step);
    }

    private int Irow, Icol;
    private int I_channels, kernels_N;
    private int kernelRow, kernelCol;
    public ConvLayer(int Irow, int Icol, int I_channels, int kernels_N, int kernelRow, int kernelCol, int step, Function activation){
        super(Irow*Icol* I_channels,
                Core.conv_dst_size(Irow, kernelRow, step)*Core.conv_dst_size(Icol, kernelCol, step) * kernels_N,
                activation);
        this.Irow = Irow;
        this.Icol = Icol;
        this.I_channels = I_channels;
        this.kernels_N = kernels_N;
        this.kernelCol = kernelCol;
        this.kernelRow = kernelRow;
        this.step = step;
    }


    @Override
    public void initWB() {
        int outRow = Core.conv_dst_size(Irow, kernelRow, step);
        int outCol = Core.conv_dst_size(Icol, kernelCol, step);
        W = new D3D(kernelRow, kernelCol, kernels_N);
        B = new D3D(outRow, outCol, kernels_N);
        for(int i = 0; i< kernels_N; i++){
            W.set(i, MatFloat.rand(W.row(), W.col(),0, 1));
            B.set(i, MatFloat.rand(B.row(), B.col(),0, 1));
        }
    }
    private void init_(D3D kernels, D3D B, int step) {
        this.step = step;

        int out_row = B.row();
        int out_col = B.col();
        int out_ch = B.ch();

        YC = new D3D(out_row, out_col, out_ch);
        YB = new D3D(out_row, out_col, out_ch);
        P = new D3D(out_row, out_col, out_ch);


        if (isFlatten) {
            Flatten = new D3D(new Node[]{
                    new Node(new MatFloat(1, kernels.ch() * out_col * out_row)
                    )});
            Flatten.setName("Flatten");

        }
    }

    private int kernels2input;
    @Override
    public void build_gradient(D3D M) {
        setInput(M);
        initWB();
        init_(W, B, step);
        kernels2input = W.ch() / (M.ch());
        for(int in=0; in<M.ch(); in++) {
            int KernelID = in * kernels2input;
            for (int k=KernelID; k < KernelID+kernels2input; k++) {
                YC._set_(k, M._conv_(in, W._get_(k), step));
                YB._set_(k,  YC._add_(k, B._get_(k)));
                P._set_(k, YB._func_(k, activation));
            }
        }
        W.setName("Kernels");
        B.setName("Biases");
        I.setName("Input");
        P.setName("Prediction - Output");
    }


    @Override
    public void forward() {
        for(int in=0; in<I.ch(); in++) {
            int KernelID = in * kernels2input;
            for (int k=KernelID; k < KernelID+kernels2input; k++) {
                I._conv_(in, W._get_(k), step, YC._get_(k));
                YC._add_(k, B._get_(k), YB._get_(k));
                YB._func_(k, activation, P._get_(k));
            }
        }
        if(isFlatten)
            update_flatten();
    }

    /** находим градиенты каждой свертки и фильтров
     *  Эта хрень используется только если это последний слой ps2часа ночи*/
    @Override
    public void backward(MatFloat[] backward_grad) {
        for(int i=0; i<P.ch(); i++){
            System.out.println(P._get_(i));
            P._get_(i)._backward_(backward_grad[i]);
        }
    }
    @Override
    public void backward(){
        if(isFlatten) {
            backward_from_flatten();
        }
        for(int i=0; i<P.ch(); i++){
            P._get_(i)._backward_(P.getGrad(i));
        }
    }

    public void backward_from_flatten(){
        for(int d = 0; d< P.ch(); d++){
            MatFloat y_grad = P.getGrad(d);
            int start = d*y_grad.col()*y_grad.row();
            for(int r=0; r<y_grad.row(); r++){
                for(int c=0; c<y_grad.col(); c++){
                    y_grad.set(r, c, Flatten.getGrad(0).get(0, start+c+r*y_grad.col()));
                }
            }
        }
    }
    public void update_flatten(){
        _m3D2vec_(P._getChannels_(), Flatten._get_(0));
    }
    public Node getFlatten(){
        return Flatten._get_(0);
    }

    @Override
    public D3D _P_(){
        return isFlatten ? Flatten : P;
    }

}

