package com.zipcodewilmington.arrayutility;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by leon on 3/6/18.
 */
public class ArrayUtility <T> {
    private T[] inputArray;

    public ArrayUtility(T[] inputArray) {
        this.inputArray = inputArray;
    }

    public T mergeArrays(T[] array1, T[] array2){

        T concatObj = (T) Stream.concat(Arrays.stream(array1), Arrays.stream(array2));

        return concatObj;
    }

    public  T getMostCommonFromMerge(T[] arrayToMerge) {

        Object[] concatObj = Stream.concat(Arrays.stream(inputArray), Arrays.stream(arrayToMerge))
               .toArray(Object[]::new);

        List<Object> concatList = Arrays.asList(concatObj);
        T number = (T) mostCommon(concatList);
        return number ;
    }

    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public Integer countDuplicatesInMerge(T[] arrayToMerge, T valueToEvaluate) {

        Stream<T> resultingStream = (Stream<T>) mergeArrays(inputArray,arrayToMerge);

        ArrayList<T> toArray = resultingStream.collect(Collectors.toCollection(ArrayList::new));

        return Collections.frequency(toArray, valueToEvaluate);

    }



    public Integer getNumberOfOccurrences(T valueToEvaluate) {

        Stream<T> stream = Stream.of(inputArray);

        Map<Object, Long> countValue = stream
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return Math.toIntExact(countValue.get(valueToEvaluate));

    }

    public T[] removeValue(T valueToRemove)
    {
        T[] newArray = Arrays
                .copyOf(inputArray, inputArray.length - getNumberOfOccurrences(valueToRemove));

        return Stream.of(inputArray)
                .filter(val -> val != valueToRemove)
                .collect(Collectors.toList())
                .toArray(newArray);

    }
}


