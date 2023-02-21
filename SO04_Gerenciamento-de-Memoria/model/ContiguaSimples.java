package model;

import java.util.Arrays;
import java.util.List;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 05/09/2022
 * Ultima alteracao: 16/09/2022
 * Nome: ContiguaSimples
 * Funcao: Representa o algoritmo de Alocamenteo Contiguo Simples
 * **************************************************************
 */
public class ContiguaSimples extends Alocador {
  @Override
  public List<int[]> particionar() {
    List<int[]> partitions = Arrays.asList(new int[] { 1, 0, 6, 9 });
    return partitions;
  }
}
