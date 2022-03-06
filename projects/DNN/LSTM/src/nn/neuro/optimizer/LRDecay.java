package nn.neuro.optimizer;

import static nn.neuro.optimizer.LRDecay.Type.*;

public class LRDecay {
    public enum Type{
        NONE, LINEAR, EXPONENTIAL;
    }

    private float lr, start_lr,  final_lr;
    private int max_epochs;
    private float coefficient;
    private Type type;

    public LRDecay(float lr, float final_lr, int max_epochs, Type type){
        setLr(lr);
        setStart_lr(lr);
        setFinal_lr(final_lr);
        setMax_epochs(max_epochs);
        setType(type);
        setCoefficient(type);
    }
    public LRDecay(float lr){
        this(lr, 0, 0, NONE);
    }
    public void update(){
        if(type == LINEAR){
            linear();
        }
        else if(type == EXPONENTIAL){
            exponential();
        }
    }

    /** [0, max_epochs) */
    private void setCoefficient(Type type){
        if(type == LINEAR){
            coefficient = (start_lr - final_lr) / (float) (max_epochs);
        }
        else if(type == EXPONENTIAL){
            coefficient = (float) Math.pow(final_lr / start_lr, 1f / (float)(max_epochs));
        }
    }

    private void linear(){
        lr -= coefficient;
    }
    private void exponential(){
        lr *= coefficient;
    }

    public float lr(){
        return lr;
    }

    public void setLr(float lr){
        this.lr = lr;
    }
    public void setStart_lr(float start_lr){
        this.start_lr = start_lr;
    }
    public void setFinal_lr(float final_lr){
        this.final_lr = final_lr;
    }
    public void setMax_epochs(int max_epochs){
        this.max_epochs = max_epochs;
    }
    public void setType(Type type){
        this.type = type;
    }
}
