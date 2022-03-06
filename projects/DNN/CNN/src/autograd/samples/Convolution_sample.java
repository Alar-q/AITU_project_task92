package autograd.samples;

import autograd.Node;
import linal.core.MatFloat;

public class Convolution_sample {
    public static void main(String[] args){
        new Convolution_sample().main();
    }

    MatFloat I = new MatFloat(new float[][]{
            {1, 1, 0.5f, 1, 1},
            {1, 1, 0.5f, 1, 1},
            {1, 1, 0.5f, 1, 1},
            {1, 1, 0.5f, 1, 1},
    });
    MatFloat kernel = new MatFloat(new float[][]{
            {-1, 0, 1},
    });
    void main(){
        Node M = new Node(I);
        Node Kernel = new Node(kernel);
        Node Y = M._conv_(Kernel, 1);
        Y._backward_();

        System.out.println(Y.setName("Y"));
        System.out.println(Kernel.setName("Kernel"));
        System.out.println(M.setName("M"));
    }
}
