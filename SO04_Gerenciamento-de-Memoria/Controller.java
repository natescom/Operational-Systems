import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.ParentBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Alocador;
import model.BCP;
import model.ContiguaSimples;
import model.ParticionadaEstaticaAbsoluta;
import model.ParticionadaEstaticaRealocavel;
import util.MemoryUtils;
import util.GuiUtils;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 05/09/2022
 * Ultima alteracao: 16/09/2022
 * Nome: Controller
 * Funcao: Controla as alteracoes da interface
 * **************************************************************
 */
public class Controller implements Initializable {

  public Button btnNovoProcesso;
  public MenuButton mbAlocacao;
  public MenuItem miContigua;
  public MenuItem miEsAbsoluta;
  public MenuItem miEsRelocavel;
  public GridPane gpMemoria;
  public VBox vbProcess;
  public VBox vbLegenda;
  public Label lblAlert;

  private Node[][] memoryGui = new Node[7][10];

  private ArrayList<int[]> partitions;
  private ArrayList<BCP> processos;
  private Alocador alocador;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    new Thread(() -> {
      try {
        Thread.sleep(500);
        initGrid();
        for (int i = 0; i < partitions.size(); i++) {
          paintBlocks(partitions.get(i), GuiUtils.getColor(i));
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    processos = new ArrayList<>();
    alocador = new ContiguaSimples();
    partitions = new ArrayList<int[]>(alocador.particionar());

    EventHandler<MouseEvent> evtNovoProcesso = event -> {
      if (partitions.isEmpty()) {
        lblAlert.setText("Nao ha particoes livres");
        return;
      }

      int[] partition = partitions.get(0);
      int length = MemoryUtils.getPatitionLength(partition);
      if (length <= 0) length = 1;
      BCP processo = new BCP(1+new Random().nextInt(length));

      if (alocador.processFitInMemory(partition, processo)) {
        alocador.putProcessInMemory(partitions, partition, processo);
        if (processo.getColor() == "")
          processo.setColor(findProcessColor(processo));
        processo.setBox(GuiUtils.createProcessBox(processo));

        // GUI: PINTAR BLOCOS DOS PROCESSOS //
        paintBlocks(processo.getBlocks(), processo.getColor());
        vbProcess.getChildren().add(processo.getBox());
        processo.setOnPost(() -> {
          processos.remove(processo);
          if(alocador instanceof ParticionadaEstaticaRealocavel)
          partitions.add(0,processo.getAddress());
          else
          partitions.add(partition);
          paintBlocksFormat(partition);
          Platform.runLater(() -> vbProcess.getChildren().remove(processo.getBox()));
        });

        processos.add(processo);
        processo.start();
        lblAlert.setText("");
      } else {
        lblAlert.setText("Processo grande demais");
      }

    };

    EventHandler<ActionEvent> evtSelecionarItem = event -> {
      MenuItem item = (MenuItem) event.getSource();
      mbAlocacao.setText(item.getText());
      alocador = getAlocador(mbAlocacao.getItems().indexOf(item));
      partitions = new ArrayList<int[]>(alocador.particionar());

      
      // GUI:
      ObservableList<Node> processosGui = vbProcess.getChildren();
      ObservableList<Node> legendaGui = vbLegenda.getChildren();
      processos.forEach((t) -> {Platform.runLater(() -> 
        processosGui.remove(t.getBox()));
        t.stop();
      });
      processos.clear();
      legendaGui.clear();
      for (int i = 0; i < partitions.size(); i++) {
        String color = (alocador instanceof ParticionadaEstaticaRealocavel) ? "#00000000" : GuiUtils.getColor(i);
        paintBlocks(partitions.get(i), color);
        legendaGui.add(GuiUtils.createLegendaItem(i));
      }
      if(alocador instanceof ParticionadaEstaticaRealocavel) legendaGui.clear();
    };

    btnNovoProcesso.setOnMouseClicked(evtNovoProcesso);
    miContigua.setOnAction(evtSelecionarItem);
    miEsAbsoluta.setOnAction(evtSelecionarItem);
    miEsRelocavel.setOnAction(evtSelecionarItem);
  }

  /**
   * Separa um alocador de acordo um indice
   * 
   * @param i
   * @return
   */
  private Alocador getAlocador(int i) {
    switch (i) {
      case 0:
        return new ContiguaSimples();
      case 1:
        return new ParticionadaEstaticaAbsoluta();
      default:
        return new ParticionadaEstaticaRealocavel();
    }
  }

  /**
   * Define a cor do processo de acordo a particao
   * 
   * @param process
   * @return
   */
  private String findProcessColor(BCP process) {
    int[] blocks = process.getBlocks();
    Node node = memoryGui[blocks[0]][blocks[1]];
    return node.getStyle()
        .replaceAll("-fx-background-color: ", "")
        .substring(0, 7)
        .concat("ff");
  }

  /**
   * Pinta os blocos da interface
   * 
   * @param blocks
   * @param color
   */
  private void paintBlocks(int[] blocks, String color) {
    int[] blockCurrent = new int[] { blocks[0], blocks[1] };
    int[] blockFinal = new int[] { blocks[2], blocks[3] };
    boolean continuar = true;
    while (continuar) {
      if (Arrays.equals(blockCurrent, blockFinal)) {
        continuar = false;
      }
      Node node = memoryGui[blockCurrent[0]][blockCurrent[1]];
      Platform.runLater(() -> node.setStyle("-fx-background-color: " + color));
      blockCurrent = MemoryUtils.getNextMemoryBlock(blockCurrent);
    }
  }

  /**
   * Retonar os blocos de uma particao a sua cor natural
   * depois de serem realocados
   * 
   * @param blocks
   */
  private void paintBlocksFormat(int[] blocks) {
    int[] blockCurrent = new int[] { blocks[0], blocks[1] };
    int[] blockFinal = new int[] { blocks[2], blocks[3] };
    boolean continuar = true;
    while (continuar) {
      if (Arrays.equals(blockCurrent, blockFinal)) {
        continuar = false;
      }
      Node node = memoryGui[blockCurrent[0]][blockCurrent[1]];
      String colorp = node.getStyle().replaceAll("-fx-background-color: ", "").substring(0, 7);
      String color = colorp.equalsIgnoreCase("#000000") ? (colorp+"00") : (colorp+"35");
      Platform.runLater(() -> node.setStyle("-fx-background-color: " + color));
      blockCurrent = MemoryUtils.getNextMemoryBlock(blockCurrent);
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
