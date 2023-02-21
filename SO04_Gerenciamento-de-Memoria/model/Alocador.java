package model;

import java.util.ArrayList;
import java.util.List;

import util.MemoryUtils;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 13/07/2022
 * Ultima alteracao: 21/07/2022
 * Nome: Alocador
 * Funcao: Forcene as principais funcoes no gerenciamento de
 * memoria
 * **************************************************************
 */
public abstract class Alocador {
  /*
   * UMA PARTICAO EH UMA PARTE DA MEMORIA
   * INICIO [1,0]
   * FIM [2, 3]
   * ([1,0],[2,3])
   * [1,0,2,3]
   */

  /**
   * Pega a memoria, divide em particoes e retorna as particoes disponiveis
   * 
   * @param memory
   * @param processo
   * @return Lista de particoes
   */
  public abstract List<int[]> particionar();

  /*
   * Verifica se o processo cabe na particao
   */
  public boolean processFitInMemory(int[] partition, BCP processo) {
    if (processo.getLength() <= MemoryUtils.getPatitionLength(partition)) {
      return true;
    }
    return false;
  }

  /**
   * Coloca um processo em uma particao
   * e remove a particao das disponiveis
   * 
   * @param partitions
   * @param partition
   * @param processo
   */
  public void putProcessInMemory(ArrayList<int[]> partitions, int[] partition, BCP processo) {
    processo.setAddress(partition);
    partitions.remove(partition);
  }

}