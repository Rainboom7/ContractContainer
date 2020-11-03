package util.sorter;

import model.contract.Contract;

import java.util.Comparator;

public class BubbleContractSorter implements ContainerSorter<Contract>{

    @Override
    public void  sort ( Contract[] contracts , Comparator<? super Contract> comparator ) {
        int n = contracts.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (comparator.compare (contracts[j],contracts[j+1]  ) > 0)
                {
                    Contract temp = contracts[j];
                    contracts[j] = contracts[j+1];
                    contracts[j+1] = temp;
                }
    }
}
