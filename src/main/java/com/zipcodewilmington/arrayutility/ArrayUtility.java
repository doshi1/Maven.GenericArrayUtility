package com.zipcodewilmington.arrayutility;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Created by leon on 3/6/18.
 */
public class ArrayUtility<T> {
    T[] array;
    public ArrayUtility(T[] inputArray) {
        this.array = inputArray;
    }

    public int getNumberOfOccurrences(T value) {
        return (int) Arrays.stream(array)
                .filter(item -> item.equals(value))
                .count();
    }

    public T[] removeValue(T valueToRemove) {
        return (T[])Arrays.stream(array)
                .filter(item-> !item.equals(valueToRemove))
                .toArray(this::getNewArray);
    }

    private T[] getNewArray(int size){
        return (T[]) Array.newInstance(array.getClass().getComponentType(), size);
    }

    public T getMostCommonFromMerge(T[] arrayToMerge) {
        array = getMergedArray(arrayToMerge);
        int max = getMaxDuplicateCount();
        return Arrays.stream(array)
                .filter(item -> getNumberOfOccurrences(item) >= max)
                .findFirst().orElse(null);

    }

    public int countDuplicatesInMerge(T[] arrayToMerge, T valueToEvaluate) {
        array = getMergedArray(arrayToMerge);
        return (int)countDuplicates(valueToEvaluate);
    }

    private int countDuplicates(T valueToEvaluate){
        return (int)Arrays.stream(array)
                .filter(item->item.equals(valueToEvaluate))
                .count();

    }

    private T[] getMergedArray(T[] arrayToMerge){
        return Stream.concat(Arrays.stream(array), Arrays.stream(arrayToMerge))
                .toArray(this::getNewArray);

    }

    private int getMaxDuplicateCount(){
        return (int)Arrays.stream(array)
                .map(item->countDuplicates(item))
                .max(Comparator.naturalOrder()).orElse(0);
    }
}
