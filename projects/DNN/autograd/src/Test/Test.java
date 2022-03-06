package Test;

import autograd.Node;
import linal.core.MatFloat;

public class Test {
    public static void main(String[] args){
        new Test().main();
    }

    void main() {
        MatFloat Y_mat = new MatFloat(new float[][]{
                {1, 2, 5},
                {3, 4, 6}});

        MatFloat Bias_mat = new MatFloat(new float[][]{
                {0.5f, 0.3f, 0.2f}});

        Node Y = new Node(Y_mat).setName("Y");
        Node B = new Node(Bias_mat).setName("B");
        Node P = Y._add_(B, 0).setName("P");
        P._backward_(new MatFloat(new float[][]{
                {2, 2, 2},
                {2, 2, 2}}));
        System.out.println(P);
        System.out.println(B);

        for (int i=0; i<100; i++) {
            Y._add_(B, P, 0);
            P._backward_(new MatFloat(new float[][]{
                    {2, 2, 2},
                    {2, 2, 2}}));

            System.out.println(P);
            System.out.println(B);
        }
    }
}
