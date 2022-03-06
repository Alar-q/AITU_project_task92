package nn.neuro.layer;

import nn.autograd.Node;
import nn.linal.core.MatFloat;
import nn.linal.core.dims.D3D;
import nn.neuro.Layer;

/** Хранит Node Input (для графа нужен)
 *  Его выход - самый первый Input
 * */
public class InputLayer extends Layer {
    public InputLayer(MatFloat I){
        super(I.row(), I.col(), null);
        this.P3D = new D3D(I);
    }

    @Override
    public void initWB() {}

    @Override
    public void build_gradient(Node[] I) {}

    @Override
    public void forward() {}
}
