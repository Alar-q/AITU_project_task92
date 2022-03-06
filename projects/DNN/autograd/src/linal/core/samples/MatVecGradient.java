package linal.core.samples;

import Test.Test;
import autograd.Node;
import linal.core.MatFloat;

/**
 * Заметка:
 *      Вектор суммируется с матрицей по x оси
 * */
public class MatVecGradient {
    public static void main(String[] args){
        new MatVecGradient().main();
    }

    void main() {
        MatFloat Y_mat = new MatFloat(new float[][]{
                {1, 2, 5},
                {3, 4, 6}});

        MatFloat Bias_mat = new MatFloat(new float[][]{
                {0.5f, 0.3f, 0.2f}});

        MatFloat D = new MatFloat(new float[][]{
                {2, 2, 2},
                {2, 2, 2}});

        Node Y = new Node(Y_mat).setName("Y");
        Node B = new Node(Bias_mat).setName("B");
        Node P = Y._add_(B, 0).setName("P");

        /**
         *  Answer should be:
         *  F(B) = P * D = (Y + B) * D,  D - const
         *  (ax)' = a, C'= 0
         *  F'(B) = P'(B)*D + P(B)*D' = P'(B) * D
         *  P'(B) = 1
         *
         *  B - вектор. Производная F'(b) = F'(y1(b), y2(b), y3(b)) = y1'(b) + y2'(b) + y3'(b)
         *  */

        P._backward_(D);
        System.out.println(P);
        System.out.println(B);

        //Градиент вычисляется правильно
        for (int i=0; i<100; i++) {
            //Здесь мы должны как-то менять переменные Y или B
            //Y.setMat(other);
            //B.setMat(other);
            Y._add_(B, P, 0);
            P._backward_(D);

            System.out.println(P);
            System.out.println(B);
        }
    }
}
