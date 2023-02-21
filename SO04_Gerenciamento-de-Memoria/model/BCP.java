package model;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import util.MemoryUtils;

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
  private int[] address; // Qual espaco da memoria esta alocado
  private int[] blocks; // Qual espaco da memoria ele ocupa
  // GUI //
  private VBox box;
  private String color = "";
  private Runnable onPost; // Ao termino da thread

  public BCP(int length) {
    this.length = length;
  }

  public int getLength() {
    return length;
  }

  public int[] getAddress() {
    return address;
  }

  public int[] getBlocks() {
    return blocks;
  }

  public void setAddress(int[] address) {
    this.address = address;
    int[] endPoint = MemoryUtils.somarPosition(new int[] { address[0], address[1] }, length - 1);
    blocks = new int[] { address[0], address[1], endPoint[0], endPoint[1] };
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
    progressBar.setProgress(0);
    float durationTotal = length * 500;
    float progressUnit = 0.01f;
    long slp = (long) (durationTotal * progressUnit);
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
