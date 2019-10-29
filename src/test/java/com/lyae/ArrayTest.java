package com.lyae;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArrayTest {
    public static void main(String[] args) {
        List<String> lst = new ArrayList<>();

        System.out.println(lst.add("1"));
        System.out.println(lst.add("2"));
        System.out.println(lst.add("3"));

        System.out.println(lst.size());

        System.out.println(lst.remove(0));

        System.out.println(lst.size());

        Map<String,String> mmm = null;

        System.out.println( ""+mmm!=null);

    }

}
