package util.sorter;

import annotations.Injectable;
import model.contract.Contract;

import java.util.Comparator;
@Injectable
public class MergeContractSorter implements ContainerSorter<Contract> {
    @Override
    public void sort ( Contract[] contracts, Comparator<? super Contract> comparator ) {
        mergeSort ( contracts, comparator, 0, contracts.length-1 );
    }

   private void merge ( Contract[] contracts, Comparator<? super Contract> comparator, int l, int m, int r ) {
        int n1 = m - l + 1;
        int n2 = r - m;

        var L = new Contract[ n1 ];
        var R = new Contract[ n2 ];

        for (int i = 0; i < n1; ++i)
            L[ i ] = contracts[ l + i ];
        for (int j = 0; j < n2; ++j)
            R[ j ] = contracts[ m + 1 + j ];


        int i = 0, j = 0;

        int k = l;
        while ( i < n1 && j < n2 ) {
            if ( comparator.compare ( L[ i ], R[ i ] ) <= 0 ) {
                contracts[ k ] = L[ i ];
                i++;
            } else {
                contracts[ k ] = R[ j ];
                j++;
            }
            k++;
        }

        while ( i < n1 ) {
            contracts[ k ] = L[ i ];
            i++;
            k++;
        }

        while ( j < n2 ) {
            contracts[ k ] = R[ j ];
            j++;
            k++;
        }
    }

    private void mergeSort ( Contract[] contracts, Comparator<? super Contract> comparator, int l, int r ) {
        if ( l < r ) {
            int m = ( l + r ) / 2;

            mergeSort ( contracts, comparator, l, m );
            mergeSort ( contracts, comparator, m + 1, r );

            merge ( contracts, comparator, l, m, r );
        }
    }
}
