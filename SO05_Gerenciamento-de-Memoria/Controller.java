import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.BCP;
import model.BestFit;
import model.FastFit;
import model.FirstFit;
import model.MemoryAllocationStrategy;
import model.Partition;
import model.WorstFit;
import util.GuiUtils;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 05/09/2022
 * Ultima alteracao: 06/10/2022
 * Nome: Controller
 * Funcao: Controla as alteracoes da interface
 * **************************************************************
 */
public class Controller implements Initializable {

  public Button btnNovoProcesso;
  public MenuButton mbAlocacao;
  public MenuItem miFirstFit;
  public MenuItem miBestFit;
  public MenuItem miWorstFit;
  public MenuItem miFastFit;
  public GridPane gpMemoria;
  public VBox vbProcess;
  public Label lblAlert;
  public TextField inputTamanho;
  public CheckBox checkAuto;

  private Node[][] memoryGui;

  private ArrayList<BCP> processos;

  private Partition memory;
  private LinkedList<Partition> partitions;
  private MemoryAllocationStrategy mas;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    memoryGui = new Node[7][10];
    processos = new ArrayList<>();
    memory = new Partition(Partition.Type.H, 10, 69);
    partitions = new LinkedList<>(Arrays.asList(memory));
    mas = new FirstFit();

    new Thread(() -> {
      try {
        Thread.sleep(500);
        initGrid();
        paintBlocks(memory.getAddress()[0], memory.getAddress()[1], "#D3D3D3");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();


    EventHandler<MouseEvent> evtNovoProcesso = event -> {
      int tamanhoDoProcesso = checkAuto.isSelected() || inputTamanho.getText().equals("")
          ? 5 * (1 + new Random().nextInt(6))
          : Integer.parseInt(inputTamanho.getText());
      BCP processo = new BCP(tamanhoDoProcesso);
      try {
        mas.allocate(partitions, processo);

        if (processo.getColor() == "") processo.setColor(GuiUtils.getColor(-1));
        processo.setBox(GuiUtils.createProcessBox(processo));

        // GUI: PINTAR BLOCOS DOS PROCESSOS //
        paintBlocks(processo.getPartition().getAddress()[0], processo.getPartition().getAddress()[1], processo.getColor());
        vbProcess.getChildren().add(processo.getBox());
        processo.setOnPost(() -> {
          try {
            mas.deallocate(partitions, processo);
            processos.remove(processo);

            resetBlockColor(processo.getPartition().getAddress()[0], processo.getPartition().getAddress()[1]);
            Runnable removerCaixaProcesso = () -> {
              vbProcess.getChildren().remove(processo.getBox());
              //lblAlert.setText("Partitions:" + partitions.size());
            };
            Platform.runLater(removerCaixaProcesso);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });

        processos.add(processo);
        processo.start();

        lblAlert.setText("");
      } catch (Exception e1) {
        // e1.printStackTrace();
      }

    };

    EventHandler<ActionEvent> evtSelecionarItem = event -> {
      MenuItem item = (MenuItem) event.getSource();
      mbAlocacao.setText(item.getText());
      mas = getAloccationStrategy(mbAlocacao.getItems().indexOf(item));
    };

    EventHandler<ActionEvent> evtClickCheck = event -> {
      inputTamanho.setDisable(checkAuto.isSelected());
    };

    inputTamanho.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("[0-9]*")) {
          inputTamanho.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });
    checkAuto.setOnAction(evtClickCheck);
    btnNovoProcesso.setOnMouseClicked(evtNovoProcesso);
    miFirstFit.setOnAction(evtSelecionarItem);
    miBestFit.setOnAction(evtSelecionarItem);
    miWorstFit.setOnAction(evtSelecionarItem);
    miFastFit.setOnAction(evtSelecionarItem);
  }

  private MemoryAllocationStrategy getAloccationStrategy(int i) {
    switch (i) {
      case 0:
        return new FirstFit();
      case 1:
        return new BestFit();
      case 2:
        return new WorstFit();
      default:
        return new FastFit();
    }
  }

  /**
   * Pinta os blocos da interface
   * 
   * @param blocks
   * @param color
   */
  private void paintBlocks(int blockStart, int blockFinal, String color) {
    for (int i = blockStart; i <= blockFinal; i++) {
      int linha = i / 10;
      int coluna = i % 10;
      Node node = memoryGui[linha][coluna];
      Platform.runLater(() -> node.setStyle("-fx-background-color: " + color));
    }
  }

  /**
   * Retonar os blocos de uma particao a sua cor natural
   * depois de serem realocados
   * 
   * @param blocks
   */
  private void resetBlockColor(int blockStart, int blockFinal) {
    for (int i = blockStart; i <= blockFinal; i++) {
      int linha = i / 10;
      int coluna = i % 10;
      Node node = memoryGui[linha][coluna];
      Platform.runLater(() -> node.setStyle("-fx-background-color: #D3D3D3"));
    }
  }

  /**
   * Inicializa o tabela da memoria
   */
  private void initGrid() {
    for (Node node : gpMemoria.getChildren()) {
      int row = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
      int column = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);
      memoryGui[row][column] = node;
    }
  }
}
