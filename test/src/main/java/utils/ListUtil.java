package utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {

    public static void main(String[] args) {


        ArrayList<Integer> l1 = new ArrayList<>();
        Random random = new Random();

        for (int j = 0; j < 5; j++) {
            int i = random.nextInt(100);
            l1.add(i);
        }

        System.out.println(l1.toString());
        xzSort(l1);
        System.out.println(l1.toString());

    }

    private static void mpSort(ArrayList<Integer> l1) {
        for (int i = 0; i < l1.size(); i++) {
            for (int j = l1.size()-1; j >i; j--) {
                if (l1.get(j) > l1.get(j - 1)) {
                    swap(l1, j, j - 1);
                }
            }
        }
    }



    private static void xzSort(ArrayList<Integer> l1) {
        for (int i = 0; i < l1.size()-1; i++) {
            int maxIndex =i;
            for (int j = i+1; j <l1.size(); j++) {
                if (l1.get(maxIndex) > l1.get(j)) {
                    maxIndex=j;
                }
            }
            swap(l1,maxIndex,i);
        }

    }





    private static <E> void swap(List<E> list, int index1, int index2) {
        //定义第三方变量
        E e = list.get(index1);
        //交换值
        list.set(index1, list.get(index2));
        list.set(index2, e);
    }


    /**
     * 对数组进行去重 返回去重后的数组。
     *
     * @param l1
     * @param <T>
     * @return
     */
    public static <T> List<T> listDuplicate(List<T> l1) {
        for (int i = 0; i < l1.size(); i++) {
            for (int j = l1.size() - 1; j > i; j--) {
                String a = l1.get(i).toString();
                String b = l1.get(j).toString();
                if (a.equals(b)) {
                    l1.remove(j);
                }
            }
        }
        return l1;
    }

}
