package com.application.examples;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Generics {
    int number = 0;
    ArrayList<String> arrayList;
    Integer newNumber = 1;
    Set<Integer> set;
    List<String> list;
}
