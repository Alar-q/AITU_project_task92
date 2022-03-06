package autograd;

import linal.core.MatFloat;

public class Methods {
    public static void _m3D2vec_(Node[] D3D, Node vec){
        for(int d = 0; d< D3D.length; d++){
            MatFloat mat = D3D[d].mat();
            int start = d*mat.col()*mat.row();
            for(int r=0; r<mat.row(); r++){
                for(int c=0; c<mat.col(); c++){
                    vec.mat().set(0, start+c+r*mat.col(), mat.get(r, c));
                }
            }
        }
    }
}
