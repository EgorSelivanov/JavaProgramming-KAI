package main.java;

import java.util.ArrayList;

public class BeanComponent {
    private String strWithNumbers;
    private String zerosAndOnes;
    private int countOfReload;

    public BeanComponent() {
        this.countOfReload = 0;
    }

    public String getStrWithNumbers() {
        return strWithNumbers;
    }

    public void setStrWithNumbers(String strWithNumbers) {
        this.strWithNumbers = strWithNumbers;
    }

    public String getZerosAndOnes() {
        return zerosAndOnes;
    }

    public void setZerosAndOnes(String zerosAndOnes) {
        this.zerosAndOnes = zerosAndOnes;
    }

    public int getCountOfReload() {
        return countOfReload;
    }

    public void setCountOfReload(int countOfReload) {
        this.countOfReload = countOfReload;
    }

    public void processArray() {
        ArrayList<Integer> al = new ArrayList();//создане списка для 0

        String[] arrayZerosAndOnes = strWithNumbers.split(" ");

        int number = 0;
        for (String x : arrayZerosAndOnes) {
            try {
                number = Integer.parseInt(x);
            }
            catch (Exception ex)
            {
                zerosAndOnes = "Letter entered! Enter 0 or 1";
                return;
            }
            if (number == 0) {
                al.add(0, number);//добавление в список 0
            } else if (number == 1){
                al.add(number);//добавление в список 1
            } else{
                zerosAndOnes = "Enter only 0 or 1!";
                return;
            }
        }

        zerosAndOnes = al.toString();
    }
}
