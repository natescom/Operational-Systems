import java.util.function.Function;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 29/07/2021
 * Ultima alteracao: 29/07/2021
 * Nome: Principal
 * Funcao: Inicializa o programa
 * **************************************************************
 */
public class Principal extends Application {

  public static void main(String[] args) throws Exception {
    launch(args);
    /*
    LinkedList<Partition> partitions = new LinkedList<>();
    Runnable printTable = () -> {
      List<int[]> addresses = partitions.stream()
        .filter(t -> t.getType() == Partition.Type.P)
        .map(t -> t.getAddress())
        .collect(Collectors.toList());
      createMemoryTable(addresses.toArray(new int[addresses.size()][2]));
    };
    Partition memory = new Partition(Partition.Type.H, 0, 69);
    partitions.add(memory);
    MemoryAllocationStrategy mas = new FirstFit();
    BCP process = new BCP(17);
    BCP process2 = new BCP(8);
    BCP process3 = new BCP(5);
    BCP process4 = new BCP(15);
    BCP process5 = new BCP(2);
    System.out.println("======================");
    System.out.println("ALOCANDO PROCESSO 1");
    mas.allocate(partitions, process);
    printTable.run();
    System.out.println("======================");
    System.out.println("ALOCANDO PROCESSO 2");
    mas.allocate(partitions, process2);

    printTable.run();
    
    System.out.println("======================");
    System.out.println("DESALOCANDO PROCESSO 1");
    mas.deallocate(partitions, process); // DESALOCANDO
    printTable.run();
    System.out.println("======================");
    System.out.println("ALOCANDO PROCESSO 3");
    mas.allocate(partitions, process3);
    printTable.run();
    System.out.println("======================");
    System.out.println("ALOCANDO PROCESSO 4");
    mas.allocate(partitions, process4);
    printTable.run();
    System.out.println("======================");
    System.out.println("DESALOCANDO PROCESSO 2");
    mas.deallocate(partitions, process2);
    printTable.run();
    //mas.allocate(partitions, process5);
    //printTable.run();
    //System.out.println("");
    */
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Sistemas Operacionais - Gerenciador de Memoria");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); // Ao fechar a janela todos os processos sao fechados tambem
    primaryStage.show();
  }

  /*
   * Cria uma tabela no console imprimindo as particoes
   */
  public static void createTable(int[]... particoesUsadas) {
    System.out.println("======================");
    for (int i = -1; i < 7; i++) {
      for (int j = -1; j < 10; j++) {
        if (i == -1) {
          if (j != -1) {
            System.out.print(j + " ");
          } else {
            System.out.print("  ");
          }
        } else {
          if (j == -1) {
            System.out.print(i + " ");
          } else {
            int valorNumero = (i * 10 + j);
            Function<int[][],String> pertence = (partitionUsadas) -> {
              for (int j2 = 0; j2 < partitionUsadas.length; j2++) {
                if (partitionUsadas[j2][0] <= valorNumero && valorNumero <= partitionUsadas[j2][1]) {
                  return j2+" ";
                }
              }
              return "- ";
            };
            System.out.print(pertence.apply(particoesUsadas));
            
          }
        }
      }
      System.out.println("");
    }
  }

  /*
   * Cria uma tabela no console marcando os espacos usados
   */
  public static void createTable(int start, int end) {
    System.out.printf("Do %d ao %d\n", start, end);
    for (int i = -1; i < 7; i++) {
      for (int j = -1; j < 10; j++) {
        if (i == -1) {
          if (j != -1) {
            System.out.print(j + " ");
          } else {
            System.out.print("  ");
          }
        } else {
          if (j == -1) {
            System.out.print(i + " ");
          } else {
            if (start <= (i * 10 + j) && (i * 10 + j) <= end) {
              System.out.print("X ");
            } else {
              System.out.print("- ");
            }
          }
        }
      }
      System.out.println("");
    }
  };


}