import java.net.URL;
import java.util.Calendar;
import java.util.Formatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 13/07/2022
 * Ultima alteracao: 21/07/2022
 * Nome: Controller
 * Funcao: Controla as alteracoes da interface
 * **************************************************************
 */
public class Controller implements Initializable {

  public Label lblTime;
  public Button btnTelCall;
  public VBox btnTelZero;
  public VBox btnTelOne;
  public VBox btnTelTwo;
  public VBox btnTelThree;
  public VBox btnTelFour;
  public VBox btnTelFive;
  public VBox btnTelSix;
  public VBox btnTelSeven;
  public VBox btnTelEigth;
  public VBox btnTelNine;
  public VBox btnTelStar;
  public VBox btnTelHastag;
  public Button btnFechar;
  public Button btnArrastar;

  public Button btnHome;
  public Button btnBack;
  public Button btnRecent;

  public Button btnApps;

  public VBox appPhone;
  public VBox appMen;

  public Button btnPhone;
  public Button btnMen;

  public Label lblNumber;
  public Label lblNumber2;
  public Button btnTelDel;
  public TabPane tabPaneSystem;
  public TabPane tabPaneOS;

  public ImageView imgCel;
  public WebView webApps;
  public VBox appInternet;
  public Button btnInternet;
  public Button appWhats;
  public Button appInsta;
  public Button appUber;
  public Button appTwitter;
  public Button appYoutube;
  public Button appFace;
  public WebView webPage;
  public TextField tfUrl;
  public Button btnRecarregar;
  public Button btnTelCallStop;
  public Button btnSo;
  public VBox appSo;
  public ProgressBar pbMOS;

  private static double xOffset = 0;
  private static double yOffset = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // ----------- INSTANCIAS ----------- // 
    EventHandler<MouseEvent> pressionar = (e) -> {
      Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      xOffset = primaryStage.getX() - e.getScreenX();
      yOffset = primaryStage.getY() - e.getScreenY();
    };
    EventHandler<MouseEvent> soltar = (e) -> {
      Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      primaryStage.setX(e.getScreenX() + xOffset);
      primaryStage.setY(e.getScreenY() + yOffset);
    };
    Runnable relogio = () -> {
      while (true) {
        Formatter format = new Formatter();
        Calendar gfg_calender = Calendar.getInstance();
        format.format("%tl:%tM", gfg_calender,
            gfg_calender);
        Platform.runLater(() -> {
          lblTime.setText(format.toString());
        });
        try {
          sleep(1000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    };
    Runnable pistao = () -> {
      try {
      while (pbMOS.getProgress() < 0.5f) {
        sleep(50);
        pbMOS.setProgress(pbMOS.getProgress() + 0.01);
      }
      sleep(1000);
      pbMOS.setProgress(0.80);
      while (pbMOS.getProgress() < 1f) {
        sleep(100);
        pbMOS.setProgress(pbMOS.getProgress() + 0.01);
      }
      tabPaneOS.getSelectionModel().select(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  };
    
    // ----------- ATOS ----------- //
    imgCel.setOnMousePressed(pressionar);
    imgCel.setOnMouseDragged(soltar);
    btnArrastar.setOnMousePressed(pressionar);
    btnArrastar.setOnMouseDragged(soltar);
    
    new Thread(relogio).start();
    new Thread(pistao).start();
  }

  /**
   * Eventos de acao
   * 
   * @param e
   */
  @FXML
  public void onClick(ActionEvent e) {
    Jukebox.stop();
    if (e.getSource().equals(btnFechar)) {
      Platform.exit();
      System.exit(0);
      return;
    }
    if (e.getSource().equals(btnRecarregar)) {
      webPage.getEngine().load(tfUrl.getText());
      return;
    }
    if (e.getSource().equals(btnHome)) {
      tabPaneSystem.getSelectionModel().select(0);
      return;
    }
    if (e.getSource().equals(btnPhone)) {
      tabPaneSystem.getSelectionModel().select(1);
      return;
    }
    if (e.getSource().equals(btnMen)) {
      tabPaneSystem.getSelectionModel().select(2);
      return;
    }
    if (e.getSource().equals(btnApps)) {
      tabPaneSystem.getSelectionModel().select(3);
      webApps.getEngine().load("");
      return;
    }
    if (e.getSource().equals(btnInternet)) {
      tfUrl.setText("http://www.marlosmarques.com/");
      webPage.getEngine().load("http://www.marlosmarques.com/");
      tabPaneSystem.getSelectionModel().select(5);
      return;
    }
    if (e.getSource().equals(btnTelDel)) {
      String number = lblNumber.getText();
      if (number.length() > 0)
        lblNumber.setText(number.substring(0, number.length() - 1));
      return;
    }
    if (e.getSource().equals(appWhats)) {
      abrirApp("https://web.whatsapp.com/");
      return;
    }
    if (e.getSource().equals(appInsta)) {
      abrirApp("http://www.instagram.com/");
      return;
    }
    if (e.getSource().equals(appFace)) {
      abrirApp("http://m.facebook.com/");
      return;
    }
    if (e.getSource().equals(appTwitter)) {
      abrirApp("http://m.twitter.com/");
      return;
    }
    if (e.getSource().equals(appUber)) {
      abrirApp("https://www.uber.com/br/pt-br/");
      return;
    }
    if (e.getSource().equals(appYoutube)) {
      abrirApp("http://m.youtube.com/");
      return;
    }
    if (e.getSource().equals(btnTelCall)) {
      if (!lblNumber.getText().isEmpty()) {
        tabPaneSystem.getSelectionModel().select(6);
        String numero = lblNumber.getText();
        Platform.runLater(() -> {
          lblNumber.setText("");
          lblNumber2.setText(numero);
        });
        new Thread(() -> {
          try {
            sleep(1000);
            Jukebox.play("telocupado.wav");
          } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
          }
        }).start();
      }
      return;
    }
    if (e.getSource().equals(btnTelCallStop)) {
      tabPaneSystem.getSelectionModel().select(1);
      return;
    }
    if (e.getSource().equals(btnSo))
      tabPaneSystem.getSelectionModel().select(7);
  }

  /**
   * Eventos de clique do mouse
   * 
   * @param e
   */
  @FXML
  public void onMouseClick(MouseEvent e) {
    if (e.getSource().equals(btnTelOne))
      lblNumber.setText(lblNumber.getText() + "1");
    if (e.getSource().equals(btnTelTwo))
      lblNumber.setText(lblNumber.getText() + "2");
    if (e.getSource().equals(btnTelThree))
      lblNumber.setText(lblNumber.getText() + "3");
    if (e.getSource().equals(btnTelFour))
      lblNumber.setText(lblNumber.getText() + "4");
    if (e.getSource().equals(btnTelFive))
      lblNumber.setText(lblNumber.getText() + "5");
    if (e.getSource().equals(btnTelSix))
      lblNumber.setText(lblNumber.getText() + "6");
    if (e.getSource().equals(btnTelSeven))
      lblNumber.setText(lblNumber.getText() + "7");
    if (e.getSource().equals(btnTelEigth))
      lblNumber.setText(lblNumber.getText() + "8");
    if (e.getSource().equals(btnTelNine))
      lblNumber.setText(lblNumber.getText() + "9");
    if (e.getSource().equals(btnTelZero))
      lblNumber.setText(lblNumber.getText() + "0");
    if (e.getSource().equals(btnTelHastag))
      lblNumber.setText(lblNumber.getText() + "#");
    if (e.getSource().equals(btnTelStar))
      lblNumber.setText(lblNumber.getText() + "*");
    if (e.getSource().equals(appPhone)) {
      tabPaneSystem.getSelectionModel().select(1);
      return;
    }
    if (e.getSource().equals(appMen)) {
      tabPaneSystem.getSelectionModel().select(2);
      return;
    }
    if (e.getSource().equals(appInternet)) {
      webPage.getEngine().load("http://www.marlosmarques.com/");
      tabPaneSystem.getSelectionModel().select(5);
      return;
    }
    if (e.getSource().equals(appSo)) {
      tabPaneSystem.getSelectionModel().select(7);
    }
  }

  /**
   * Abre um app trocando para aba de navegador e
   * passando o link do app
   * 
   * @param link
   */
  private void abrirApp(String link) {
    webApps.getEngine().load(link);
    tabPaneSystem.getSelectionModel().select(4);
  }
}