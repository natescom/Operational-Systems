package model;

import java.util.LinkedList;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 04/10/2022
 * Ultima alteracao: 06/10/2022
 * Nome: MemoryAllocationStrategy
 * Funcao: Define as funcoes de uma estragegia de alocacao de memoria
 * **************************************************************
 */
public abstract class MemoryAllocationStrategy {
  /**
   * Aloca um espaco na memoria para o processo
   * @param partitions
   * @param process
   * @throws Exception
   */
  public abstract void allocate(LinkedList<Partition> partitions, BCP process) throws Exception;

  /**
   * Desaloca um espaco na memoria, eh igual para todo protocolo
   * @param partitions
   * @param process
   * @throws Exception
   */
  public synchronized void deallocate(LinkedList<Partition> partitions, BCP process) throws Exception {
    Partition partitionProcess = process.getPartition();
    int i = partitions.indexOf(partitionProcess);
    if(i == -1) throw new Exception("PARTICAO NAO ENCONTRADA");
    if(i == 0){
      Partition neighborNext = partitions.get(i+1);
      partitionProcess.changeType();
      if(neighborNext.getType() == Partition.Type.H){
        partitionProcess.setEnd(neighborNext.getEnd());
        partitions.remove(neighborNext);
      }
    }else if(i == partitions.size()-1){
      Partition neighborPrevious = partitions.get(i-1);
      if(neighborPrevious.getType() == Partition.Type.H){
        neighborPrevious.setEnd(partitionProcess.getEnd());
        partitions.remove(partitionProcess);
      }
    }else{
      Partition neighborA = partitions.get(i-1);
      Partition neighborB = partitions.get(i+1);
      if(neighborA.getType() == Partition.Type.H){
        neighborA.setEnd(partitionProcess.getEnd());
        partitions.remove(partitionProcess);
        if(neighborB.getType() == Partition.Type.H){
          neighborA.setEnd(neighborB.getEnd());
          partitions.remove(neighborB);
        }
      }else if(neighborB.getType() == Partition.Type.H){
        partitionProcess.changeType();
        partitionProcess.setEnd(neighborB.getEnd());
        partitions.remove(neighborB);
      }else{
        partitionProcess.changeType();
      }
    }
  };

}
