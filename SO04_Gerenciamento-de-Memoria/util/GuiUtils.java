package util;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.BCP;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 13/07/2022
 * Ultima alteracao: 21/07/2022
 * Nome: GuiUtils
 * Funcao: Ferramentas para auxiliar as alteracoes da interface
 * **************************************************************
 */
public abstract class GuiUtils {
  private static int colorid = -1;

  /**
   * Seleciona uma das cores predefinidas
   * 
   * @param colorindex Indice da cor desejada ou -1 para cor aleatoria
   * @return
   */
  public static String getColor(int colorindex) {
    String alpha = "";
    if (colorindex == -1) {
      colorid = colorid >= 5 ? 0 : colorid + 1;
      colorindex = colorid;
    } else {
      alpha = "35";
    }
    switch (colorindex) {
      case 0:
        return "#6affa4" + alpha; // VERDE
      case 1:
        return "#a9def9" + alpha; // AZUL
      case 2:
        return "#CAB8FF" + alpha;
      case 3:
        return "#ffda9e" + alpha;
      case 4:
        return "#d8f79a" + alpha;
      case 5:
        return "#fcf6bd" + alpha; // AMARELO
      default:
        return "#B57EDC" + alpha;
    }
  }

  /**
   * Cria um elemento da interface que representa um processo
   * 
   * @param processo
   * @return
   */
  public static VBox createProcessBox(BCP processo) {
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(5));
    vbox.setAlignment(Pos.TOP_CENTER);
    String css = String.format("-fx-border-color: black; -fx-background-color: %s;", processo.getColor());
    ArrayList<Node> nodes = new ArrayList<Node>();
    Label title = new Label("Processo " + processo.getId());
    title.setFont(new Font(15));
    nodes.add(title);
    nodes.add(new Separator());
    nodes.add(new Label(processo.getLength() + " Blocos"));
    nodes.add(new ProgressBar());
    vbox.setStyle(css);
    vbox.getChildren().addAll(nodes);
    return vbox;
  }

  /**
   * Cria um elemento que representa a cor da particao como uma legenda
   * 
   * @param partitionIndex
   * @return
   */
  public static HBox createLegendaItem(int partitionIndex) {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(5));
    hbox.setSpacing(5);
    hbox.setAlignment(Pos.CENTER_LEFT);
    String style = String.format("-fx-border-color: black; -fx-background-color: %s;", getColor(partitionIndex));

    Label title = new Label("Partition " + (partitionIndex + 1));
    AnchorPane aPane = new AnchorPane();
    aPane.setStyle(style);
    aPane.setPrefSize(24, 24);

    hbox.getChildren().addAll(Arrays.asList(aPane, title));
    return hbox;
  }

}
