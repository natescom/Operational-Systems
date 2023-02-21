package model;

import java.util.LinkedList;
import java.util.stream.Collectors;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 04/10/2022
 * Ultima alteracao: 06/10/2022
 * Nome: FirstFit
 * **************************************************************
 */
public class FirstFit extends MemoryAllocationStrategy {

  @Override
  public void allocate(LinkedList<Partition> particions, BCP process) throws Exception{
    try{
      Partition partition = particions.stream()
      .filter(p -> p.getType() == Partition.Type.H && p.getLength() >= process.getLength())
      .limit(1)
      .collect(Collectors.toList()).get(0);

      Partition partitionFree = new Partition(Partition.Type.H, partition.getStart() + process.getLength(), partition.getEnd());
      process.setPartition(partition);
      particions.add((particions.indexOf(partition)+1),partitionFree);

      partition.changeType();
      partition.setEnd(partition.getStart() + process.getLength() - 1);
    } catch (Exception e) {
      throw new Exception("NÃO FOI POSSIVEL ALOCAR ESSE PROCESSO");
    }
    

  }

}
