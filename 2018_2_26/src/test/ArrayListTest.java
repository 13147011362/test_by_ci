package test;

import java.util.ArrayList;

public class ArrayListTest {

    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<String>( );
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        System.out.println(arrayList);
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("a");
        arrayList1.add("b");
        arrayList1.add("c");
       // arrayList.addAll(arrayList1);
        arrayList.addAll(3,arrayList1);
        System.out.println(arrayList);


    }


}
