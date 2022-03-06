package linal.dims;

import autograd.Node;
import func.Function;
import linal.core.Core;
import linal.core.MatFloat;

import static autograd.Node.Operation.DOT;

public class D3D {
    private Node[] image;

    private MatFloat[] values;
    private MatFloat[] grads;

    private int row, col, ch;

    public D3D(int row, int col, int ch){
        setRow(row);
        setCol(col);
        setChannels(ch);
        image = new Node[ch];
        values = new MatFloat[ch];
        grads = new MatFloat[ch];
        for(int i=0; i<ch; i++){
            MatFloat val = new MatFloat(row, col);
            image[i] = new Node(val);
            values[i] = val;
            grads[i] = image[i].grad();
        }
    }
    public D3D(Node[] image){
        setImage(image);
    }


    /* Getters / Setters */

    public void setImage(Node[] image){
        this.image = image;
        row = image[0].row();
        col = image[0].col();
        ch = image.length;
        values = new MatFloat[image.length];
        grads = new MatFloat[image.length];
        for(int i=0; i<image.length; i++){
            MatFloat val = image[i].mat();
            values[i] = val;
            grads[i] = image[i].grad();
        }

    }

    public Node _dot_(int id, Node other){
        return image[id]._dot_(other);
    }
    public void _dot_(int id, Node other, Node dst){
        image[id]._dot_(other, dst);
    }
    public Node _conv_(int id, Node kernel, int step){
        return image[id]._conv_(kernel, step);
    }
    public void _conv_(int id, Node kernel, int step, Node dst){
        image[id]._conv_(kernel, step, dst);
    }
    public Node _add_(int id, Node other){
        return image[id]._add_(other);
    }
    public void _add_(int id, Node other, Node dst){
        image[id]._add_(other, dst);
    }
    public Node _func_(int id, Function function){
        return image[id]._func_(function);
    }
    public void _func_(int id, Function function, Node dst){
        image[id]._func_(function, dst);
    }

    public Node _get_(int ch){
        return image[ch];
    }
    public void _set_(int ch, Node mat){
        image[ch] = mat;
    }
    public void set(int ch, MatFloat mat){
        image[ch].setMat(mat);
    }
    public Node[] _getChannels_(){
        return image;
    }
    public MatFloat[] getChannels(){
        return values;
    }
    public MatFloat[] getGrads(){
        return grads;
    }
    public MatFloat getGrad(int i){
        return grads[i];
    }
    public void setRow(int row){
        this.row = row;
    }
    public int row(){
        return row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public int col(){
        return col;
    }
    public void setChannels(int ch){
        this.ch = ch;
    }
    public int ch(){
        return ch;
    }

    public void clean(){
        for(Node n: image){
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
        for(int i=0; i<image.length; i++){
            sb.append(image[i]).append("\n");
        }
        return sb.toString();
    }
}
