package linal.idk;

import autograd.Node;
import linal.core.MatFloat;

public class IDK {
    public static void _vec2m3D_(Node vec, Node[] D3D){
        for(int d = 0; d< D3D.length; d++){
            MatFloat mat = D3D[d].mat();
            int start = d*mat.col()*mat.row();
            for(int r=0; r<mat.row(); r++){
                for(int c=0; c<mat.col(); c++){
                    mat.set(r, c, vec.mat().get(0, start+c+r*mat.col()));
                }
            }
        }
    }
    public static void _vec2m3D_(MatFloat vec, Node[] D3D){
        for(int d = 0; d< D3D.length; d++){
            MatFloat mat = D3D[d].mat();
            int start = d*mat.col()*mat.row();
            for(int r=0; r<mat.row(); r++){
                for(int c=0; c<mat.col(); c++){
                    mat.set(r, c, vec.get(0, start+c+r*mat.col()));
                }
            }
        }
    }
    public static void _vec2m3D_(MatFloat vec, MatFloat[] D3D){
        for(int d = 0; d< D3D.length; d++){
            MatFloat mat = D3D[d];
            int start = d*mat.col()*mat.row();
            for(int r=0; r<mat.row(); r++){
                for(int c=0; c<mat.col(); c++){
                    mat.set(r, c, vec.get(0, start+c+r*mat.col()));
                }
            }
        }
    }
}
