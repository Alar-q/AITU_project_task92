package autograd.samples;

import autograd.Node;
import func.Linear;
import linal.core.Core;
import linal.core.MatFloat;


/** Пример построения функции с градиентом.
 *  Y = f(I*W + Bias), where f - it is a f(x) elementwise function
 *  M = I*W
 *  S = M + Bias
 *  Узлы можно представить в виде бинарного дерева.
 *  Каждый узел желательно использовать повторно.
 *  */
public class Function_Building {
    public static void main(String[] args) {
        new Function_Building().run();
    }

    Node M, S, Y;

    Node I = new Node(new MatFloat(new float[][]{
        {1, 2}}));

    Node W = new Node(new MatFloat(new float[][]{
        {1, 2},
        {3, 4}}));

    Node Bias = new Node( new MatFloat(new float[][]{
        {0.5f, 0.5f}}));

    void run(){
        // Этот метод нужен для повторного использования узлов
        build();
        setNames();

        MatFloat Output = function();

        Y._backward_();

        System.out.println(Output);
        System.out.println(Y);

        System.out.println(W);
        System.out.println(Bias);
    }

    void build(){
        M = I._dot_(W);
        S = M._add_(Bias);
        Y = S._func_(new Linear());
    }

    MatFloat function(){
            I._dot_(W, M);
            M._add_(Bias, Y);
            Y._func_(new Linear(), Y);
        // Y - выход функции (Node), но нам нужна сама матрица
        return Core.copy(Y.mat()).setName("Output");
    }


    public void setNames(){
        I.setName("I");
        W.setName("W");
        M.setName("M");
        S.setName("S");
        Bias.setName("Bias");
        Y.setName("Y");
    }

    /** PS: На самом деле можно писать и просто так,
     *      но это не оптимально */
    void PS(){
        M = I._dot_(W);
        S = M._add_(Bias);
        Y = S._func_(new Linear());
        Y._backward_();
    }
}
