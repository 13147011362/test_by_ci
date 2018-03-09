package test;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Administrator
 * @create 2018\2\26 0026
 * @since 1.0.0
 */
public class LiniListTest {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        Iterator iter = linkedList.iterator();
        while (iter.hasNext()) {
            String book = (String) iter.next();
               linkedList.remove(1);

        }
        System.out.println(linkedList);
    }
}
