package model;

import java.util.LinkedList;
import java.util.Optional;

public class FastFit extends MemoryAllocationStrategy {

  @Override
  public void allocate(LinkedList<Partition> partitions, BCP process) throws Exception {
    Optional<Partition> pegarPartition = partitions.stream()
        .filter((part) -> part.getType() == Partition.Type.H && part.getLength() >= process.getLength())
        .reduce((partA, partB) -> partA.getLength() <= partB.getLength() ? partA : partB);
    pegarPartition.orElseThrow(() -> new Exception("NÃO FOI POSSIVEL ALOCAR ESSE PROCESSO"));
    pegarPartition.ifPresent(partition -> {
      Partition partitionFree = new Partition(Partition.Type.H, partition.getStart() + process.getLength(),
          partition.getEnd());
      int i = partitions.indexOf(partition);
      partitions.remove(partition);
      partition.changeType();
      partition.setEnd(partition.getStart() + process.getLength() - 1);
      partitions.add(i, partitionFree);
      partitions.add(i, partition);
      process.setPartition(partition);
    });
  }

}
