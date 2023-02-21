import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {

  /****************************************************************
   * Autor: Nathan Ferraz da Silva
   * Matricula: 201911925
   * Inicio: 13/07/2022
   * Ultima alteracao: 21/07/2022
   * Nome: Principal
   * Funcao: Inicializa o programa
   * **************************************************************
   */
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    stage.setTitle("Marl OS Mobile");
    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); // Ao fechar a janela todos os processos sao fechados tambem
    stage.show();

  }
}
