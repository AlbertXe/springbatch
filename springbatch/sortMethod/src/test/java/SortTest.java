import org.junit.Test;

public class SortTest {

    @Test
    public void test01() { //插入排序   1个数是排序好的，将第二个数与他前面 1,2。。比较，移动位置
        int[] a = {2, 3, 5, 4};
        for (int i = 1; i < a.length; i++) {//从第2位开始开始插入
            int insertNum = a[i];
            int len = a.length;
            int j = i-1;//插入数的 之前的数
            while (j >= 0 && insertNum < a[j]) {
                a[j + 1] = a[j];
//                a[j]  = insertNum;
                j--;
            }
            a[j+1] = insertNum;
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
    @Test
    public void test2() {//希尔排序
        int[] a = {2, 4, 9, 6, 4, 5, 6};
        int d = a.length;
        while (d != 0) {
            d = d/2;//3
            for (int x = 0; x <d ; x++) {//分的组数 3组
                for (int i = x+d ; i <a.length ; i+=d) {//取每组的第二个数， 和 插入类似
                    int j = i-d; //第一个数
                    int temp = a[i];
                    while (j >= 0 && temp < a[j]) {
                        a[j+d] = a[j];
                        j -= d;
                    }
                    a[j+d] = temp;
                }
            }
        }
        for (int i = 0; i <a.length ; i++) {
            System.out.println(a[i]);
        }
    }

}
