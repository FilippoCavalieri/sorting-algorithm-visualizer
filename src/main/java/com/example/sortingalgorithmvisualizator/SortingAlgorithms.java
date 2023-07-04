package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;

public class SortingAlgorithms {

    private static <E> void swap(List<E> list, int index1, int index2){
        E tmp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, tmp);
    }
    private static <E extends Comparable<? super E>> int foundMax(List<E> list, int n){
        int p = 0; //Hp: first element is the max
        for(int i = 1; i < n; ++i){
            if(list.get(i).compareTo(list.get(p)) > 0)
                p = i;
        }
        return p;
    }
    public static <E extends Comparable<? super E>> void selectionSort(List<E> list){
        int listSize = list.size();
        int p;
        while(listSize-- > 1){
            p = foundMax(list, listSize);
            if(p < listSize - 1)
                swap(list, p , listSize - 1);
        }
    }

    public static <E extends Comparable<? super E>> void bubbleSort(List<E> list){

    }

    public static <E extends Comparable<? super E>> void insertionSort(List<E> list){

    }

    public static <E extends Comparable<? super E>> void quickSort(List<E> list){

    }

    public static <E extends Comparable<? super E>> void mergeSort(List<E> list){

    }


}
