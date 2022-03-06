package main;

public class Math2 {
    /**
     * [min, max)
     */
    public static float randVal(float min, float max) {
        return (float) Math.random() * (max - min) + min;
    }

    /** Возвращает распределенные данные для построение линии */
    public static float[] line(float from, float to, int steps){
        float[] seq = new float[steps];
        float k = -(from-to)/(steps-1);
        for(int t=0; t<steps; t++)
            seq[t] = from + (k * t);
        return seq;
    }

    public static float[] noisy_line(float from, float to, int steps, float max_bias){
        float[] seq = new float[steps];
        float k = -(from-to)/(steps-1);
        seq[0] = from;
        for(int t=1; t<steps-1; t++)
            seq[t] = from + (k * t) + randVal(-max_bias, max_bias);
        seq[steps-1] = to;
        return seq;
    }

    /** the total sum of seconds is known as well as the number of changes during this time24 */
    public static int[] noisy_secs(int seconds, int n){
        int[] res = new int[n];
        int sum = 0;
        for(int i=0; i<n-1; i++){
            res[i] = (int) randVal((float)seconds/(float)n, (float)seconds/(float)n+1);
            sum+=res[i];
        }
        res[n-1] = seconds - sum;
        return res;
    }

    public static float parseFloat(String str){
        String[] spl = str.split(",");
        float a = Float.parseFloat(spl[0]);
        if(spl.length > 1)
            a += Float.parseFloat(spl[1]) / (float) Math.pow(10, spl[1].length());
        return a;
    }
}
