package hello_csv;

import java.io.File;
import java.util.Scanner;

public class Hello_CSV {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Pupochek\\Downloads\\Данные Задание 2\\01\\weight_0001-30.05.csv";
        Scanner sc = new Scanner(new File(path));
        while (sc.hasNextLine()) {
            String d = sc.nextLine();
            System.out.println(d);
        }
        sc.close();
    }
}
