package com.studentdata.studentdata;

import org.hibernate.mapping.Collection;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestClass {

    public static void main(String[] args) {
    String str = "JayJavaJ2EE";


        Map<String,Integer> map = new HashMap<>();

        int[] a = {1,2,3,4,10,11,12,23};
//        List<Integer> a = new ArrayList<>();
//        a.add(1); a.add(2); a.add(3); a.add(4); a.add(5); a.add(6); a.add(10); a.add(11); a.add(12); a.add(13);

        List<Map<String,Integer>>list = new ArrayList<>();
//        list.stream().map().
        int temp;

        for (int i=0; i<a.length; i++) {
            for (int j=i; j<a.length;j++) {
                if(a[i] > a[j]) {
                    temp = a[j];
                }
            }
        }
    }


}
