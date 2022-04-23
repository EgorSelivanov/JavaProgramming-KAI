package client;

import compute.Task;

import java.io.Serializable;
import java.util.ArrayList;

public class ZerosAndOnes implements Task<ArrayList<Integer>>, Serializable {
    private String[] args;

    public ZerosAndOnes(String[] args){
        this.args = args;
    }

    @Override
    public ArrayList<Integer> execute() {
        ArrayList<Integer> al0 = new ArrayList();//создане списка для 0
        ArrayList<Integer> al1 = new ArrayList();//создане списка для 1

        int number = 0;
        for (int i = 1; i < args.length; i++) {
            try {
                number = Integer.parseInt(args[i]);
            } catch (Exception ex) {
                System.out.println("Letter entered! Enter 0 or 1");
                return null;
            }
            if (number == 0) {
                al0.add(number);//добавление в список 0
            } else if (number == 1) {
                al1.add(number);//добавление в список 1
            } else {
                System.out.println("Enter 0 or 1!");
                return null;
            }
        }

        al0.addAll(al1);

        return al0;
    }
}
