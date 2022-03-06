import autograd.Node;
import func.Linear;
import func.Tanh;
import linal.core.Core;
import linal.core.MatFloat;
import linal.dims.D3D;
import neuro.layer.ConvLayer;
import neuro.layer.layer.DenseLayer;

public class Main {
    public static void main(String[] args){
        new Main().main();
    }

    MatFloat I = new MatFloat(new float[][]{
            {1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 0, 0, 0},
            {1, 1, 0.5f, 1, 1},
            {1, 1, 0.5f, 1, 1},
    });

    MatFloat I2 = new MatFloat(new float[][]{
            {1, 1, 0, 0, 0},
            {1, 1, 0, 1, 1},
            {0, 1, 0, 0, 0},
            {1, 1, 2f, 1, 1},
            {1, 1, 0.5f, 1, 0},
    });

    MatFloat kernel = new MatFloat(new float[][]{
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1},
    });
    MatFloat kernel2 = new MatFloat(new float[][]{
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1},
    });

    MatFloat bias1 = new MatFloat(new float[][]{
            {4, 2},
            {2, 2},
    });
    MatFloat bias2 = new MatFloat(new float[][]{
            {1, 1},
            {1, 1},
    });

    /**
     *
     *
     *  Bias size = Y size
     *
     *
     * */
    void main(){
        Node[] M = new Node[]{
                new Node(I),
                new Node(I2)
        };
        D3D M3D = new D3D(M).setName("M3D");

        /** initialize */
        ConvLayer CNN = new ConvLayer(5, 5, 1, 2, 3, 3, 2, new Tanh());
        CNN.isFlatten(true);
        CNN.build_gradient(M3D);

        DenseLayer denseLayer = new DenseLayer(8, 3, new Tanh());
        denseLayer.build_gradient(CNN._P_());

        /** Predict */
        /* Представим что это новый вход */
        CNN.setInput(new MatFloat[]{
                M[1].mat()
        });

        CNN.forward();
        denseLayer.forward();

        /** Train */
        denseLayer.backward(new MatFloat[]{MatFloat.ones(1, 3)}); // Loss by the fact
        CNN.backward(); //Получается, что у нас CNN никогда не последний слой

        System.out.println(CNN.getFlatten());
        System.out.println(CNN._P_());
        System.out.println(denseLayer._P_());
    }


}
