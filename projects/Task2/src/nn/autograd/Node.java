package nn.autograd;

import nn.func.Function;
import nn.linal.core.Core;
import nn.linal.core.MatFloat;

import static nn.autograd.Node.Operation.*;


/**
 * Node - обертка MatFloat с автоматическим расчетом градиента
 * Производная по узлу значит, что мы считаем узел в качестве переменной
 * Все методы которые возвращают или как-то влияют на Node я обозначаю _name_
 * */
public class Node {

    public final boolean out = false;

    public enum Operation {
        NONE, ADD, SUB, MUL, DIV, NEG, DOT, FUNC,
        ADD0, ADD1;
    }

    private MatFloat mat;
    private final MatFloat grad;
    private final Operation creation_op;

    private final Node[] depends_on;


    public Node(MatFloat mat, Operation creation_op, Node[] depends_on) {
        this.mat = mat;
        this.creation_op = creation_op;
        this.depends_on = depends_on;

        grad = MatFloat.zeros(mat.row(), mat.col()).setName("Gradient");
    }
    public Node(MatFloat mat){
        this(mat, NONE, null);
    }


    /* Как еще можно реализовать повторное использование объектов Node/MatFloat ?
     * Здесь по идее надо повторно использовать созданную матрицу
     * Накопление градиента нужно сбрасывать, если было какое-то вычисление
     * */

    public Node _add_(Node other){
        if(out) System.out.println("_add_");
        need_clean_grad();
        return new Node(Core.add(mat, other.mat), ADD, new Node[]{this, other});
    }
    public void _add_(Node other, Node dst){
        if(out) System.out.println("_add_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.add(mat, other.mat, dst.mat);
    }
    public Node _add_(Node vector, int axis){
        if(out) System.out.println("_add_v_");
        need_clean_grad();
        MatFloat m = Core.add(mat, vector.mat, new MatFloat(mat.row(), mat.col()), axis);
        return new Node(m, axis==0?ADD0:ADD1, new Node[]{this, vector});
    }
    public void _add_(Node vector, Node dst, int axis){
        if(out) System.out.println("_add_v_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.add(mat, vector.mat, dst.mat, axis);
    }

    public Node _sub_(Node other){
        if(out) System.out.println("_sub_");
        need_clean_grad();
        return new Node(Core.sub(mat, other.mat), SUB, new Node[]{this, other});
    }
    public void _sub_(Node other, Node dst){
        if(out) System.out.println("_sub_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.sub(mat, other.mat, dst.mat);
    }

    public Node _mul_(Node other){
        if(out) System.out.println("_mul_");
        need_clean_grad();
        return new Node(Core.mul(mat, other.mat), MUL, new Node[]{this, other});
    }
    public void _mul_(Node other, Node dst){
        if(out) System.out.println("_mul_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.mul(mat, other.mat, dst.mat);
    }

    public Node _neg_(){
        if(out) System.out.println("_neg_");
        need_clean_grad();
        return new Node(Core.neg(mat), NEG, new Node[]{this});
    }
    public void _neg_(Node dst){
        if(out) System.out.println("_neg_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.neg(mat, dst.mat);
    }

    public Node _dot_(Node other){
        if(out) System.out.println("_dot_");
        need_clean_grad();
        return new Node(Core.dot(mat, other.mat), DOT, new Node[]{this, other});
    }
    public void _dot_(Node other, Node dst){
        if(out) System.out.println("_dot_");
        need_clean_grad();
        dst.need_clean_grad();
        Core.dot(mat, other.mat, dst.mat);
    }

    /** Поэлементное применение функции ко всем элементам матрицы  */
    private Function func;
    public Node _setFunc_(Function func){
        this.func = func;
        return this;
    }
    /** Поэлементное применение функции ко всем элементам матрицы */
    public Node _func_(Function func){
        if(out) System.out.println("_func_");
        need_clean_grad();
        return new Node(func.f(mat), FUNC, new Node[]{this})._setFunc_(func);
    }
    /** Поэлементное применение функции ко всем элементам матрицы
     *  Здесь на самом деле можно было бы и убрать Function in argument
     * */
    public void _func_(Function func, Node dst){
        if(out) System.out.println("_func_");
        need_clean_grad();
        dst.need_clean_grad();
        func.f(mat, dst.mat);
    }

    public void _backward_(MatFloat backward_grad) {
        if(clean_grad) {
            clean_grad();
        }
        if(out) System.out.println("BACKWARD");

        grad.add(backward_grad);

        if (creation_op == ADD) {
            if(out) System.out.println("ADD");
            depends_on[0]._backward_(grad);
            depends_on[1]._backward_(grad);
        }
        else if(creation_op == ADD0){
            //суммирую
            depends_on[0]._backward_(grad);
            depends_on[1]._backward_(Core.sum(grad,0));
        }
        else if(creation_op == ADD1){
            depends_on[0]._backward_(grad);
            depends_on[1]._backward_(Core.sum(grad,1));
        }
        else if (creation_op == SUB) {
            if(out) System.out.println("SUB");
            depends_on[0]._backward_(grad);
            depends_on[1]._backward_(Core.neg(grad));
        }
        else if (creation_op == MUL) {
            if(out) System.out.println("MUL");
            depends_on[0]._backward_(Core.mul(depends_on[1].mat, grad));
            depends_on[1]._backward_(Core.mul(depends_on[0].mat, grad));
        }
        else if (creation_op == NEG) {
            if(out) System.out.println("NEG");
            depends_on[0]._backward_(Core.neg(grad));
        }
        else if (creation_op == DOT) {
            if(out) System.out.println("DOT");
            depends_on[0]._backward_(Core.dot(grad, Core.t(depends_on[1].mat)));
            depends_on[1]._backward_(Core.t(Core.dot(Core.t(grad), depends_on[0].mat)));
        }
        else if(creation_op == FUNC){
            if(out) System.out.println("FUNC");
            depends_on[0]._backward_(Core.mul(func.der(mat), grad));
        }
    }

    /** Первая производная всегда равна 1 */
    public void _backward_(){
        _backward_(MatFloat.ones(mat.row(), mat.col()));
    }

    //Если функция расчитывается второй раз, прошлый градиент нужно сбросить
    private boolean clean_grad;
    private void need_clean_grad(){
        clean_grad = true;
    }
    private void clean_grad(){
        grad.set(0);
        clean_grad = false;

        if(depends_on != null) {
            depends_on[0].clean_grad();
            if (depends_on.length > 1)
                depends_on[1].clean_grad();
        }
    }

    /** Getters / Setters */

    public MatFloat mat(){
        return mat;
    }
    public void setMat(MatFloat mat){
        this.mat = mat;
    }
    public MatFloat grad() {
        return grad;
    }

    public int row(){
        return mat.row();
    }
    public int col(){
        return mat.col();
    }

    private String name;
    public Node setName(String name){
        this.name = name;
        return this;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(name!=null)
            sb.append("---").append(name).append("---\n");
        sb.append("Value : \n")
                .append(mat)
                .append("\n")
                .append(grad);
        return sb.toString();
    }

}
