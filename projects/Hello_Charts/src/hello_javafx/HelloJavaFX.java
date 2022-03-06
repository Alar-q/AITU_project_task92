package hello_javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * add VM option:
 * --module-path "JavFX SDK\lib" --add-modules javafx.controls,javafx.fxml
 * */
public class HelloJavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // установка надписи
        Text text = new Text("Hello!");
        text.setLayoutY(80);    // установка положения надписи по оси Y
        text.setLayoutX(8);   // установка положения надписи по оси X

        Group group = new Group(text);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.setWidth(300);
        stage.setHeight(250);
        stage.show();
    }
}
