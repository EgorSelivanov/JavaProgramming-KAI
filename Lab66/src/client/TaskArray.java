package client;


import java.io.Serializable;
import java.util.ArrayList;

public class TaskArray implements compute.Task<String>, Serializable {
    private String[] args;

    public TaskArray(String[] args){
        this.args = args;
    }

    @Override
    public String execute() {
        ArrayList<Integer> digit1 = new ArrayList<Integer>();
        ArrayList<Integer> digit2 = new ArrayList<Integer>();

        for(int i = 1; i < args.length; i++){
            if(Integer.parseInt(args[i])%2 == 0){
                digit1.add(Integer.parseInt(args[i]));
            } else if(Integer.parseInt(args[i])%2 != 0) {
                digit2.add(Integer.parseInt(args[i]));
            }
        }

        int sum1= 0, sum2= 0;
        for(int i = 0; i < digit1.size(); i++){
            sum1 += digit1.get(i);
        }

        for(int i = 0; i < digit2.size(); i++){
            sum2 += digit2.get(i);
        }

        System.out.println("Sum 1: "+sum1);
        System.out.println("Sum 2: "+sum2);

        String answer = "Сумма 1: "+sum1+"\nСумма 2: "+sum2;
        return answer;
    }

}
