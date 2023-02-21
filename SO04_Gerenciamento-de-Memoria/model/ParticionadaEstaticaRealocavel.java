package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.GuiUtils;
import util.MemoryUtils;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 05/09/2022
 * Ultima alteracao: 16/09/2022
 * Nome: ParticionadaEstaticaRealocavel
 * Funcao: Representa o algoritmo de Alocamenteo particionada
 * estatica realocavel
 * **************************************************************
 */
public class ParticionadaEstaticaRealocavel extends Alocador {

  @Override
  public List<int[]> particionar() {
    List<int[]> partitions = Arrays.asList(new int[] { 1, 0, 6, 9 });
    return partitions;
  }

  @Override
  public void putProcessInMemory(ArrayList<int[]> partitions, int[] partition, BCP processo) {
    partitions.remove(partition);
    int[] pointStartPartition = { partition[0], partition[1] };
    int[] pointEndPartition = MemoryUtils.somarPosition(pointStartPartition, processo.getLength() - 1);
    int[] partitionProcesso = { partition[0], partition[1], pointEndPartition[0], pointEndPartition[1] };
    processo.setAddress(partitionProcesso);
    processo.setColor(GuiUtils.getColor(-1));
    int[] pointNextStartParticion = MemoryUtils.getNextMemoryBlock(pointEndPartition);
    int[] restOfUserMemory = { pointNextStartParticion[0], pointNextStartParticion[1], partition[2], partition[3] };
    if(pointNextStartParticion[0]!=7)
    partitions.add(restOfUserMemory);
  }

}
