package linal.core;

/**
 *
 * For brevity, I did not write a verification code for the dimensions of matrices.
 *
 * It is recommended to always specify dst to avoid garbage collection.
 *
 */
public class Core {
    /**
     * returns dst
     */
    public static MatFloat add(MatFloat m1, MatFloat m2, float scale, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++)
                dst.set(r, c, (m1.get(r, c) + m2.get(r, c)) * scale);
        return dst;
    }

    /**
     * returns dst
     */
    public static MatFloat add(MatFloat m1, MatFloat m2, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++)
                dst.set(r, c, (m1.get(r, c) + m2.get(r, c)));
        return dst;
    }

    /**
     * returns new Matrix
     */
    public static MatFloat add(MatFloat m1, MatFloat m2) {
        MatFloat dst = new MatFloat(m1.row(), m1.col());
        return add(m1, m2, dst);
    }

    public static MatFloat sub(MatFloat m1, MatFloat m2, float scale, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++) {
                /*
                float a = (m1.get(r, c) - m2.get(r, c));
                System.out.println("m1 = " + m1.get(r, c) + ", m2 = " + m2.get(r, c));

                float b = a * scale;
                System.out.println("a = " + a + ", b = " + b);
                 */
                dst.set(r, c, (m1.get(r, c) - m2.get(r, c)) * scale);
            }
        return dst;
    }

    public static MatFloat sub(MatFloat m1, MatFloat m2, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++)
                dst.set(r, c, (m1.get(r, c) - m2.get(r, c)));
        return dst;
    }

    public static MatFloat sub(MatFloat m1, MatFloat m2) {
        MatFloat dst = new MatFloat(m1.row(), m1.col());
        return sub(m1, m2, dst);
    }

    public static MatFloat dot(MatFloat m1, MatFloat m2, float scale, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++) {
            for (int c = 0; c < m2.col(); c++) {
                float sum = 0;
                for (int s = 0; s < m1.col(); s++)
                    sum += m1.get(r, s) * m2.get(s, c);
                dst.set(r, c, sum * scale);
            }
        }
        return dst;
    }

    public static MatFloat dot(MatFloat m1, MatFloat m2, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++) {
            for (int c = 0; c < m2.col(); c++) {
                float sum = 0;
                for (int s = 0; s < m1.col(); s++)
                    sum += m1.get(r, s) * m2.get(s, c);
                dst.set(r, c, sum);
            }
        }
        return dst;
    }

    public static MatFloat dot(MatFloat m1, MatFloat m2) {
        MatFloat dst = new MatFloat(m1.row(), m2.col());
        dot(m1, m2, dst);
        return dst;
    }

    public static MatFloat mul(MatFloat m1, MatFloat m2, float scale, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++)
                dst.set(r, c, m1.get(r, c) * m2.get(r, c) * scale);
        return dst;
    }

    /**
     * Elementwise matrix multiplication, m1_size = m2_size
     */
    public static MatFloat mul(MatFloat m1, MatFloat m2, MatFloat dst) {
        for (int r = 0; r < m1.row(); r++)
            for (int c = 0; c < m1.col(); c++)
                dst.set(r, c, m1.get(r, c) * m2.get(r, c));
        return dst;
    }

    /**
     * Elementwise matrix multiplication,
     * returns new Matrix
     */
    public static MatFloat mul(MatFloat m1, MatFloat m2) {
        return mul(m1, m2, new MatFloat(m1.row(), m1.col()));
    }

    public static MatFloat scale(MatFloat src, float scalar, MatFloat dst) {
        for (int r = 0; r < src.row(); r++)
            for (int c = 0; c < src.col(); c++)
                dst.set(r, c, src.get(r, c) * scalar);
        return dst;
    }

    public static MatFloat scale(MatFloat src, float scalar) {
        MatFloat dst = new MatFloat(src.row(), src.col());
        scale(src, scalar, dst);
        return dst;
    }

    /**
     * Negation, returns new Matrix
     */
    public static MatFloat neg(MatFloat mat) {
        return scale(mat, -1f);
    }

    /**
     * Negation
     */
    public static MatFloat neg(MatFloat mat, MatFloat dst) {
        return scale(mat, -1f, dst);
    }

    /**
     * copy не затрагивает изначальную матрицу
     */
    public static float[][] copy(float[][] src, float[][] dst) {
        for (int r = 0; r < src.length; r++)
            for (int c = 0; c < src[0].length; c++)
                dst[r][c] = src[r][c];
        return dst;
    }

    /**
     * copy не затрагивает изначальную матрицу
     */
    public static MatFloat copy(MatFloat src, MatFloat dst) {
        copy(src.getArr2(), dst.getArr2());
        return dst;
    }

    /**
     * copy не затрагивает изначальную матрицу
     */
    public static MatFloat copy(float[][] src, MatFloat dst) {
        copy(src, dst.getArr2());
        return dst;
    }

    /**
     * copy не затрагивает изначальную матрицу, returns new Matrix
     */
    public static MatFloat copy(MatFloat src) {
        return copy(src, new MatFloat(src.row(), src.col()));
    }

    public static MatFloat t(MatFloat src, MatFloat dst) {
        for (int r = 0; r < src.row(); r++)
            for (int c = 0; c < src.col(); c++)
                dst.set(c, r, src.get(r, c));
        return dst;
    }

    public static MatFloat t(MatFloat src) {
        MatFloat dst = new MatFloat(src.col(), src.row());
        t(src, dst);
        return dst;
    }

    public static MatFloat pow(MatFloat src, MatFloat dst, int pow) {
        for (int r = 0; r < src.row(); r++)
            for (int c = 0; c < src.col(); c++)
                dst.set(r, c, (float) Math.pow(src.get(r, c), pow));
        return dst;
    }

    public static MatFloat pow(MatFloat src, int pow) {
        MatFloat dst = new MatFloat(src.row(), src.col());
        pow(src, dst, pow);
        return dst;
    }


    public static MatFloat fromRowVectors(MatFloat... vecs) {
        MatFloat mat = new MatFloat(vecs.length, vecs[0].col());
        for (int r = 0; r < vecs.length; r++) {
            for (int c = 0; c < vecs[0].row(); c++) {
                mat.set(r, c, vecs[r].get(r, c));
            }
        }
        return mat;
    }

    public static MatFloat mean(MatFloat src) {
        return new MatFloat(new float[]{src.mean()});
    }


    /**
     * MatFloat mat = new MatFloat(3, 3);
     * Core.rand(mat, 0f, 1f);
     * System.out.println(mat);
     * System.out.println(Core.mean(mat));
     */
    public static void rand(MatFloat m, float min, float max) {
        for (int r = 0; r < m.row(); r++)
            for (int c = 0; c < m.col(); c++)
                m.set(r, c, randVal(min, max));
    }

    public static void rand(MatFloat m) {
        rand(m, 0f, 1f);
    }

    /**
     * [min, max)
     */
    public static float randVal(float min, float max) {
        return (float) Math.random() * (max - min) + min;
    }

    /**
     * [0, 1)
     */
    public static float randVal() {
        return randVal(0f, 1f);
    }


    /**
     * Суммирование вектора и матрицы.
     * Конечно можно сделать маску, но это затратно.
     * m_size = dst_size.
     *
     * @param v    row vector
     * @param axis 0 - y(|), 1 - x(->)
     */
    public static MatFloat add(MatFloat m, MatFloat v, MatFloat dst, int axis) {
        if (axis == 0) {
            for (int r = 0; r < m.row(); r++)
                for (int c = 0; c < m.col(); c++)
                    dst.set(r, c, (m.get(r, c) + v.get(0, c)));
        } else if (axis == 1) {
            for (int r = 0; r < m.row(); r++)
                for (int c = 0; c < m.col(); c++)
                    dst.set(r, c, (m.get(r, c) + v.get(0, r)));
        }
        return dst;
    }

    /**
     * @param dst  row vector
     * @param axis 0 - y(|), 1 - x(->)
     */
    public static MatFloat sum(MatFloat m, MatFloat dst, int axis) {
        float sum;
        if (axis == 0) {
            for (int c = 0; c < m.col(); c++) {
                sum = 0;
                for (int r = 0; r < m.row(); r++) {
                    sum += m.get(r, c);
                }
                dst.set(0, c, sum);
            }
        } else {
            for (int r = 0; r < m.row(); r++) {
                sum = 0;
                for (int c = 0; c < m.col(); c++) {
                    sum += m.get(r, c);
                }
                dst.set(0, r, sum);
            }
        }
        return dst;
    }

    public static MatFloat sum(MatFloat m, int axis) {
        MatFloat dst = new MatFloat(1, axis == 1 ? m.row() : m.col());
        return sum(m, dst, axis);
    }


    /* There is can be step_col and step_row, but not in this implementation */
    public static MatFloat conv(MatFloat mat, MatFloat kernel, int step, MatFloat dst){
        int by_row = conv_dst_size(mat.row(), kernel.row(), step);
        int by_col = conv_dst_size(mat.col(), kernel.col() ,step);
        float n=kernel.row()*kernel.col(), sum;
        for(int r=0; r<by_row; r++){
            for(int c=0; c<by_col; c++){
                sum = 0;
                for(int i=0; i<kernel.row(); i++)
                    for(int j=0; j<kernel.col(); j++)
                        sum += kernel.get(i, j) * mat.get(r+r*(step-1)+i, c+c*(step-1)+j);
                dst.set(r, c, sum/(n));
            }
        }
        return dst;
    }

    /** Размерность по измерению результата свертки
     *  mat=5x3 conv=3x3 step=1 -> 3x1
     * */
    public static int conv_dst_size(int mat, int kernel, int step){
        return 1 + ((mat - kernel) / step);
    }
    public static MatFloat conv(MatFloat mat, MatFloat kernel, int step){
        int by_row = conv_dst_size(mat.row(), kernel.row(), step);
        int by_col = conv_dst_size(mat.col(), kernel.col() ,step);
        MatFloat dst = new MatFloat(by_row, by_col);
        return conv(mat, kernel, step, dst);
    }


    public static MatFloat border(MatFloat src, int biasX, int biasY, MatFloat dst){
        for(int r=0; r<src.row()+biasY*2; r++){
            for(int c=0; c<biasX; c++){
                dst.set(r, c, 0);
            }
            for(int c=src.col()+biasX; c<src.row()+biasX*2; c++){
                dst.set(r, c, 0);
            }
        }
        for(int c=0; c<src.col()+biasX*2; c++){
            for(int r=0; r<biasY; r++){
                dst.set(r, c, 0);
            }
            for(int r=src.row()+biasY; r<src.row()+biasY*2; r++){
                dst.set(r, c, 0);
            }
        }
        for(int r=biasY; r<src.row()+biasY; r++){
            for(int c=biasX; c<src.row()+biasX; c++){
                dst.set(r, c, src.get(r-biasY, c-biasX));
            }
        }
        return dst;
    }
    public static MatFloat border(MatFloat src, int biasX, int biasY){
        MatFloat dst = new MatFloat(src.row()+biasY*2, src.col()+biasX*2);
        return border(src, biasX, biasY, dst);
    }
    public static MatFloat border(MatFloat src, int bias, MatFloat dst){
        return border(src, bias, bias, dst);
    }
    public static MatFloat border(MatFloat src, int bias){
        return border(src, bias, bias);
    }

    public static MatFloat rotate180(MatFloat src, MatFloat dst){
        rotate_arr2_180(src.getArr2(), dst.getArr2());
        return dst;
    }
    public static void rotate_arr2_180(float[][] src, float[][] dst){
        int row = src.length;
        int col = src[0].length;
        for(int i=0; i<src.length; i++)
            for(int j=0; j<src[0].length; j++)
                dst[row-i-1][col-j-1] = src[i][j];
    }
    public static void m3D2vec(MatFloat[] D3D, MatFloat vec){
        for(int d = 0; d< D3D.length; d++){
            MatFloat mat = D3D[d];
            int start = d*mat.col()*mat.row();
            for(int r=0; r<mat.row(); r++){
                for(int c=0; c<mat.col(); c++){
                    vec.set(0, start+c+r*mat.col(), mat.get(r, c));
                }
            }
        }
    }
    public static void mat2vec(MatFloat mat, MatFloat vec){
        for(int r=0; r<mat.row(); r++){
            for(int c=0; c<mat.col(); c++){
                vec.set(0, c+mat.col()*r, mat.get(r, c));
            }
        }
    }
}