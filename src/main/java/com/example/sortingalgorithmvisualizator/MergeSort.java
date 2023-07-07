package com.example.sortingalgorithmvisualizator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {
    private static void merge(int[] array, int begFirst, int endFirst,
                              int endSecond) {
        int[] tmp = new int[endSecond - begFirst + 1];
        int i = begFirst, j = endFirst + 1, k = 0;
        for(int t = begFirst; k < tmp.length; ++k, ++t){
            tmp[k] = array[t];
        }
        k = 0;
        while(i <= endFirst && j <= endSecond){
            if(array[i] < array[j]){
                tmp[k] = array[i];
                i++;
            }
            else{
                tmp[k] = array[j];
                j++;
            }
            k++;
        }
        while(i <= endFirst){
            tmp[k] = array[i];
            i++;
            k++;
        }
        while(j <= endSecond){
            tmp[k] = array[j];
            j++;
            k++;
        }
        for(i = begFirst; i <= endSecond; ++i){
            array[i] = tmp[i];
        }
    }

    private static void mergeSortRec(int[] array, int first, int last) {
        int mid;
        if (first < last) {
            mid = (first + last) / 2;
            mergeSortRec(array, first, mid);
            mergeSortRec(array, mid + 1, last);
            merge(array, first, mid, last);
        }
    }

    public static void mergeSort(int[] array) {
        mergeSortRec(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        int[] array = new int[] {4,3,67,3,89,1,6,7};
        System.out.println(array.toString());
        //mergeSort(array);
        merge(array, 2, 2, 3);
        System.out.println(array.toString());
    }
}
