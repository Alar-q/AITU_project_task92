package autograd;

import linal.core.Core;
import linal.core.MatFloat;

import static autograd.Node.Operation.CONV;

/**
 *  Много переменных, поэтому я вынес
 * реализацию свертки в отдельный класс
 * */
public class Convolution extends Node{
    private int step;

    private MatFloat grad_zero_padding;
    private MatFloat kernel_180;
    private MatFloat kernel_grad;
    private MatFloat mat_grad;

    public Convolution(Node M, Node Kernel, int step){
        super(Core.conv(M.mat(), Kernel.mat(), step), CONV, new Node[]{M, Kernel});
        setConvStep(step);
        create_kernel_grad(Kernel.mat());
        create_mat_grad(M.mat());
        create_zero_padding();
    }

    /** CONVOLUTION.
     * Все эти методы нужно вынести в отдельный класс */

    /** Кажется не должно быть public */
    private Node setConvStep(int step){
        this.step = step;
        return this;
    }
    private Node create_kernel_grad(MatFloat kernel){
        kernel_grad = new MatFloat(kernel.row(), kernel.col());
        kernel_180 = new MatFloat(kernel.row(), kernel.col());
        return this;
    }
    private Node create_mat_grad(MatFloat mat){
        mat_grad = new MatFloat(mat.row(), mat.col());
        return this;
    }
    private Node create_zero_padding(){
        grad_zero_padding = new MatFloat(mat_grad.row()+(kernel_grad.row()-1), mat_grad.col()+(kernel_grad.col()-1));
        return this;
    }
    @Override
    protected MatFloat kernel_der(MatFloat backward_grad){
        return Convolution.kernel_der(depends_on[0].mat(), step, backward_grad, kernel_grad);
    }
    @Override
    protected MatFloat mat_der(MatFloat backward_grad, MatFloat kernel){
        set_zero_padding(backward_grad);
        Core.rotate180(kernel, kernel_180);
        Convolution.mat_der(grad_zero_padding, kernel_180, mat_grad);
        return mat_grad;
    }
    private void set_zero_padding(MatFloat backward_grad){
        int bR = kernel_grad.row()-1, bC = kernel_grad.col()-1;
        for(int r=bR; r<backward_grad.row()+bR; r++) {
            for (int c = bC; c < backward_grad.col() + bC; c++) {
                grad_zero_padding.set(r,c,backward_grad.get(r-bR, c-bC));
            }
        }
    }

    /**/

    public static MatFloat kernel_der(MatFloat mat, int step, MatFloat Y_grad, MatFloat kernel_grad){
        int pass = Math.min(1, step-1); //0 or 1
        for(int r=0; r<kernel_grad.row(); r++){
            for(int c=0; c<kernel_grad.col(); c++){
                float sum = 0;
                for(int i=0, passR=0; i<Y_grad.row(); i++, passR+=pass){
                    for(int j=0, passC=0; j<Y_grad.col(); j++, passC+=pass){
                        sum += Y_grad.get(i, j) * mat.get(i+passR+r, j+passC+c);
                    }
                }
                kernel_grad.set(r, c, sum);

            }
        }
        return kernel_grad;
    }

    public static MatFloat mat_der(MatFloat Y_grad_0, MatFloat kernel_, MatFloat mat_der){
        return Core.conv(Y_grad_0, kernel_, 1, mat_der);
    }



}
