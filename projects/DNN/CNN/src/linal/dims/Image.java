package linal.dims;

import linal.core.Core;
import linal.core.MatFloat;

/** 3D Matrix
 *  Не хочу писать тензор, но было бы намного лучше
 * */
public class Image {
    private MatFloat[] image;
    private int row, col, ch;

    public Image(int row, int col, int ch){
        image = new MatFloat[ch];
        for(MatFloat mat: image)
            mat = new MatFloat(row, col);
    }
    public Image(MatFloat[] image){
        setImage(image);
    }

    /* Getters / Setters */

    public void setImage(MatFloat[] image){
        this.image = image;
    }
    public MatFloat getChannel(int ch){
        return image[ch];
    }
    public void setChannel(int i, MatFloat mat){
        image[i] = mat;
    }
    public MatFloat[] getChannels(){
        return image;
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


    /* Пусть по будет здесь */

    /** Не стал дальше реализовывать.
     *  Для многокальной свертки лучше создать Тензор */

    public static Image conv(Image im1, Image kernels, int step, Image dst){
        for(int ch=0; ch<im1.ch(); ch++)
            Core.conv(im1.getChannel(ch), kernels.getChannel(ch), step, dst.getChannel(ch));
        return dst;
    }

    public static Image conv(Image im1, Image kernels, int step){
        int by_row = Core.conv_dst_size(im1.row(), kernels.row(), step);
        int by_col = Core.conv_dst_size(im1.row(), kernels.col(), step);
        Image dst = new Image(by_row, by_col, im1.ch());
        for(int i=0; i<im1.ch(); i++)
            dst.setChannel(i, new MatFloat(by_row, by_col));

        for(int ch=0; ch<im1.ch(); ch++)
            Core.conv(im1.getChannel(ch), kernels.getChannel(ch), step, dst.getChannel(ch));
        return dst;
    }
}
