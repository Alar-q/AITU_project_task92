package main;

public class Time24 {
    private int[] time;

    public Time24(int seconds){
        time = SECONDS_2_HMS(seconds);
    }

    /** h:m:s */
    public Time24(String time){
        String[] str = time.split(":");
        int h = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        int s = Integer.parseInt(str[2]);
        this.time = new int[3];
        setH(h);
        setM(m);
        setS(s);
    }
    public Time24(int h, int m, int s){
        time = new int[3];
        setH(h);
        setM(m);
        setS(s);
    }

    public void add(int sec){
        int totalSecs = total_secs() + sec;
        time = SECONDS_2_HMS(totalSecs);
    }
    public void sub(int sec){
        int totalSecs = total_secs() - sec;
        time = SECONDS_2_HMS(totalSecs);
    }

    public Time24 getCopyOfAmount(Time24 t){
        Time24 new_t = new Time24(time[0], time[1], time[2]);
        new_t.add(t.total_secs());
        return new_t;
    }

    public boolean lessThan(Time24 t){
        return total_secs() < t.total_secs();
    }
    public boolean lessThan(int h, int m, int s){
        return total_secs() < new Time24(h, m, s).total_secs();
    }
    public boolean moreThan(Time24 t){
        return total_secs() > t.total_secs();
    }
    public boolean moreThan(int h, int m, int s){
        return total_secs() > new Time24(h, m, s).total_secs();
    }

    private int[] SECONDS_2_HMS(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        return new int[]{hours, minutes, seconds};
    }


    public int total_secs(){
        return  h() * 3600 + m() * 60 + s();
    }
    public int[] time(){
        return time;
    }

    public void setCopy(Time24 time){
        setH(time.h());
        setM(time.m());
        setS(time.s());
    }

    public void setH(int h){
        time[0] = h;
    }
    public void setM(int m){
        time[1] = m;
    }
    public void setS(int s){
        time[2] = s;
    }

    public int h(){
        return time[0];
    }
    public int m(){
        return time[1];
    }
    public int s(){
        return time[2];
    }



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(time[0]).append(":").append(time[1]).append(":").append(time[2]);
        return sb.toString();
    }

}
