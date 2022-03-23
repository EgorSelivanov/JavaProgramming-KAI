import java.util.ArrayList;

public class ZerosAndOnes {
    public static void main(String[] args) {
        ArrayList<Integer> al0 = new ArrayList();//создане списка для 0
        ArrayList<Integer> al1 = new ArrayList();//создане списка для 1

        int number = 0;
        for (String x : args) {
            try {
                number = Integer.parseInt(x);
            }
            catch (Exception ex)
            {
                System.out.println("Letter entered! Enter 0 or 1");
                return;
            }
            if (number == 0) {
                al0.add(number);//добавление в список 0
            } else if (number == 1){
                al1.add(number);//добавление в список 1
            } else{
                System.out.println("Enter 0 or 1!");
                return;
            }
        }
        for (Object o : al0) System.out.print(o + " ");
        for (Object o : al1) System.out.print(o + " ");
    }
}
