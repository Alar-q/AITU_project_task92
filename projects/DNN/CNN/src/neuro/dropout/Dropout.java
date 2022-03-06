package neuro.dropout;

import linal.core.MatFloat;

/**
 * При инициализации dropout будет автоматически работать в метода Layer.output
 * */
public class Dropout {
    protected float dropout_chance;

    protected boolean isDropout;

    public Dropout(float dropout_chance){
        setDropout_Chance(dropout_chance);
    }

    /** 0 or 1 means there is no dropout */
    public void setDropout_Chance(float dropout_chance) {
        if (dropout_chance == 0 || dropout_chance == 1) {
            setDropout(false);
        } else {
            this.dropout_chance = dropout_chance;
            setDropout(true);
        }
    }

    /** Changes Matrix if dropout is on */
    public void dropout(MatFloat P){
        if(isDropout){
            for(int r=0; r<P.row(); r++) {
                for (int c = 0; c < P.col(); c++) {
                    P.set(r, c, (float) Math.random() < dropout_chance ? 0 : P.get(r, c) * (1f / dropout_chance));
                }
            }
        }
    }

    public void setDropout(boolean isDropout){
        this.isDropout = isDropout;
    }

    public float getDropout_Chance(){
        return dropout_chance;
    }

}
