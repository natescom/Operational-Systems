package util;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 13/07/2022
 * Ultima alteracao: 21/07/2022
 * Nome: MemoryUtils
 * Funcao: Ferramentas para gerenciar a memoria simulada
 * **************************************************************
 */
public abstract class MemoryUtils {
  public final static int[] SYSTEM_PARTITION = { 0, 0, 0, 9 };
  private final static int nDeCol = 10;

  /**
   * Retorna as coordenadas do proximo bloco
   * 
   * @param currentBlock
   * @return
   */
  public static int[] getNextMemoryBlock(int[] currentBlock) {
    return (currentBlock[1] == 9) ? new int[] { currentBlock[0] + 1, 0 }
        : new int[] { currentBlock[0], currentBlock[1] + 1 };
  }

  /**
   * Descobre o tamanho da particao
   * 
   * @param partition
   * @return
   */
  public static int getPatitionLength(int[] partition) {
    return ((nDeCol - partition[1]) + (partition[2] - partition[0] - 1) * nDeCol + partition[3] + 1);
  }

  /**
   * Descobre a cordenada a partir de uma distancia
   * 
   * @param coordenates
   * @param value
   * @return
   */
  public static int[] somarPosition(int[] coordenates, int value) {
    int[] retorno = { coordenates[0], coordenates[1] };
    int aumento = value;
    while (aumento > 0) {
      retorno[1]++;
      if (retorno[1] >= nDeCol) {
        retorno[1] = 0;
        retorno[0]++;
      }
      aumento--;
    }
    return retorno;
  }
}
