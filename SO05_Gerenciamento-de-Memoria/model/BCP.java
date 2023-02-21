package model;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 13/07/2022
 * Ultima alteracao: 21/07/2022
 * Nome: BCP
 * Funcao: Representa um processo
 * **************************************************************
 */
public class BCP extends Thread {
  private final int length; // Quanto espaco esse processo usa
  private Partition partition;
  
  // GUI //
  private VBox box;
  private String color = "";
  private Runnable onPost; // Ao termino da thread

  public BCP(int length) {
    this.length = length;
  }

  public Partition getPartition() {
    return partition;
  }

  public void setPartition(Partition partition) {
      this.partition = partition;
  }

  public int getLength() {
    return length;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  public void setBox(VBox box) {
    this.box = box;
  }

  public VBox getBox() {
    return box;
  }

  public void setOnPost(Runnable onPost) {
    this.onPost = onPost;
  }

  @Override
  public void run() {
    ProgressBar progressBar = (ProgressBar) box.getChildren().get(3);
    float durationTotal = length * 500;
    float progressUnit = 0.01f;
    long slp = (long) (durationTotal * progressUnit);
    Platform.runLater(() -> progressBar.setProgress(0));
    while (progressBar.getProgress() < 1) {
      try {
        sleep(slp);
        progressBar.setProgress(progressBar.getProgress() + progressUnit);
      } catch (InterruptedException e) {
        //e.printStackTrace();
      }
    }
    onPost.run();
  }

}
