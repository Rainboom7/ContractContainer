package util.sorter;

import model.contract.Contract;

import java.util.Comparator;

public interface ContainerSorter<T> {
    public void  sort ( Contract[] contracts , Comparator<? super Contract> comparator );
}
