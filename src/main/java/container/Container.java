package container;

import model.contract.Contract;
import util.sorter.ContainerSorter;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public interface Container<T> {
    public void add ( T object );

    public void deleteAt ( int id );

    public Container<T> search ( Predicate<? super T> predicate );

    public T[] getAll ( );

    public int capacity ( );

    public void sort ( ContainerSorter<T> sorter, Comparator<? super T> comparator );
}
