package nn.linal.core.dims;

import nn.autograd.Node;
import nn.linal.core.MatFloat;

/** хранит матрицы
 *  Можно представить в виде 3D пространства (Тензор 3-го ранга).
 *  Неудобный класс. Хочу сделать так, что использовать его придется только в родительском классе Layer.
 *  */
public class D3D {
    private Node[] nodes;

    private MatFloat[] values;
    private MatFloat[] grads;

    private int ch;

    public D3D(Node ... nodes){
        _set_Nodes_(nodes);
    }
    public D3D(MatFloat ... mats){
        Node[] n = new Node[mats.length];
        for(int i = 0; i< mats.length; i++){
            n[i] = new Node(mats[i]);
        }
        _set_Nodes_(n);
    }


    /* Getters / Setters */

    /**
     *  Переназначает Nodes класса на значения аргумента
     *  this.Nodes = Nodes
     * */
    private void _set_Nodes_(Node ... nodes1){
        this.nodes = nodes1;
        ch = nodes1.length;
        values = new MatFloat[nodes1.length];
        grads = new MatFloat[nodes1.length];
        for(int i=0; i<nodes1.length; i++){
            MatFloat val = nodes1[i].mat();
            values[i] = val;
            grads[i] = nodes1[i].grad();
        }
    }



    public Node _get_Node_(int ch){
        return nodes[ch];
    }
    public void _set_Node_(int ch, Node mat){
        nodes[ch] = mat;
    }
    public void setMat(int ch, MatFloat mat){
        nodes[ch].setMat(mat);
        values[ch] = mat;
    }
    public Node[] _get_Nodes_(){
        return nodes;
    }
    public MatFloat[] getMats(){
        return values;
    }
    public void setMats(MatFloat[] mats){
        for(int i=0; i<mats.length; i++)
            setMat(i, mats[i]);
    }

    public MatFloat[] getGrads(){
        return grads;
    }

    public MatFloat getGrad(int i){
        return grads[i];
    }
    public void setCh_Num(int ch){
        this.ch = ch;
    }
    public int ch(){
        return ch;
    }

    public void clean(){
        for(Node n: nodes){
            n.mat().set(0);
        }
    }

    private String name;
    public D3D setName(String name){
        this.name = name;
        return this;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(name!=null)
            sb.append("---").append(name).append("---\n");
        for(int i = 0; i< nodes.length; i++){
            sb.append(nodes[i]).append("\n");
        }
        return sb.toString();
    }
}
