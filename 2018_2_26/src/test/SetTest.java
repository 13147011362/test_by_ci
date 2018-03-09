package test;

import java.util.HashSet;

/**
 * @author Administrator
 * @create 2018\2\26 0026
 * @since 1.0.0
 */
public class SetTest {
    class A{
        private String a ;

        @Override
        public boolean equals(Object obj) {
            if(obj == null){
                return false;
            }
            if(((A)obj).getA() == null){
                 return  false;
            }
            return  ((A)obj).getA().equals(this.getA());

        }

        public A() {
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public A(String a) {

            this.a = a;
        }
    }

    public static void main(String[] args) {
//        HashSet<String > se  = new HashSet<String >();
//        se.add("1");
//        se.add("3");
//        se.add("2");
//        String a = new String ("123");
//        String b = new String ("123");
//        String  c= "123";
//        System.out.println(a.equals(b));
//        System.out.println(a.equals(c));
//        System.out.println("a = [" + a.hashCode() + "]");
//        System.out.println("b = [" + b.hashCode() + "]");
//        System.out.println(se);
        A a = new SetTest().new A();
        a.setA("bb");
        A b = new SetTest().new A();
        b.setA("bb");
        System.out.println(a.equals(b));
    }


}