package linal.core;

/**
 *
 * The class can also perform some element-wise operations
 * like addition, subtraction, multiplication, exponentiation or mean value of matrix.
 * Все методы действуют только на саму матрицу.
 *
 * For brevity, I did not write a verification code for the dimensions of matrices.
 *
    MatFloat mat = new MatFloat(2, 2);
    for(int i=0; i<2; i++)
        for(int j=0; j<2; j++)
            mat.set(i, j, 2f);

    MatFloat mat1 = new MatFloat(new float[][]{ {1, 2, 3},
                                                {1, 2, 3},
                                                {1, 2, 3}});
    MatFloat mat2 = new MatFloat(2, 2);
    mat2.set(1,2,1,2);

 */

public class MatFloat {
    private int row, col;
    private float[][] mat;

    /** Static methods */
    public static MatFloat ones(int row, int col){
        return matOfVals(row, col, 1);
    }
    public static MatFloat zeros(int row, int col){
        return new MatFloat(row, col);
    }
    public static MatFloat matOfVals(int row, int col, float val){
        MatFloat m = new MatFloat(row, col);
        return m.set(val);
    }
    /**Identity matrix*/
    public static MatFloat eye(int row, int col){
        MatFloat mat = MatFloat.zeros(row, col);
        for(int r=0; r<row; r++)
            mat.set(r, r, 1);
        return mat;
    }

    /** Constructors */
    public MatFloat(int row, int col){
        mat = new float[row][col];
        setCol(col);
        setRow(row);
    }

    /**Initiates like a copy of the given matrix and does not modify the given*/
    public MatFloat(float[][] mat){
        this(mat.length, mat[0].length);
        set(mat);
    }
    public MatFloat(float[] mat){ //Vector
        this(1, mat.length);
        set(mat);
    }
    public MatFloat(MatFloat mat){
        this(mat.getArr2());
    }

    /**
     * Math segment
     * */
    public MatFloat add(MatFloat m){
        Core.add(this, m, this);
        return this;
    }
    public MatFloat sub(MatFloat m){
        Core.sub(this, m, this);
        return this;
    }
    public MatFloat scale(float scalar){
        Core.scale(this, scalar, this);
        return this;
    }
    public MatFloat mul(MatFloat mat){
        Core.mul(this,mat,this);
        return this;
    }
    public MatFloat pow(int pow){
        Core.pow(this, this, pow);
        return this;
    }
    public float mean(){
        return sum()/(col*row);
    }
    public float sum(){
        float dst = 0;
        for(int r=0; r<row; r++)
            for(int c=0; c<col; c++)
                dst+=get(r, c);
        return dst;
    }
    public MatFloat rand(){
        Core.rand(this);
        return this;
    }public MatFloat rand(float min, float max){
        Core.rand(this, min, max);
        return this;
    }
    public MatFloat ones(){
        set(1);
        return this;
    }

    /**
     * Getters / Setters
     * */
    public MatFloat set(int r, int c, float ob){
        mat[r][c] = ob;
        return this;
    }
    public MatFloat set(float[][] mat){
        Core.copy(mat, this.mat);
        return this;
    }
    public MatFloat set(MatFloat m){
        return Core.copy(m, this);
    }
    public MatFloat set(float... mat){
        for(int r = 0; r < row; r++)
            for (int c = 0; c < col; c++)
                this.mat[r][c] = mat[r * col + c];
        return this;
    }
    /**Set All*/
    public MatFloat set(float val){
        for(int r=0; r<row(); r++)
            for(int c=0; c<col(); c++)
                set(r, c, val);
        return this;
    }

    public float get(int r, int c){
        return mat[r][c];
    }

    public int row(){
        return row;
    }
    public int col(){
        return col;
    }

    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }

    public float[][] getArr2(){
        return mat;
    }

    private String name;
    public MatFloat setName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(name!=null)
            sb.append(name).append(" : \n");
        for(int r=0; r<row; r++){
            sb.append("| ");
            for(int c=0; c<col; c++)
                sb.append(mat[r][c]).append(" ");
            sb.append("|\n");
        }
        return sb.toString();
    }
}
