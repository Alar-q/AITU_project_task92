package nn.linal.core.samples;

import nn.linal.core.Core;
import nn.linal.core.MatFloat;

public class AddMatVec {
    public static void main(String[] args){new AddMatVec().main();}
    void main(){
        MatFloat W = new MatFloat(new float[][]{
                {1, 2, 5},
                {3, 4, 6}});

        MatFloat Bias = new MatFloat(new float[][]{
                {0.5f, 0.3f, 0.2f}});

        MatFloat P = new MatFloat(2, 3);
        System.out.println(Core.add(W, Bias, P, 0));
    }
}
