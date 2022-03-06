package Test;

public class Main {
    public static void main(String[] args){
        new Main().main();
    }
    void main(){
        Parent parent = new Parent();
        parent.m();
        Parent child = new Child();
        child.m();
    }


}
