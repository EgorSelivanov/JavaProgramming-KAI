package zerosAndOnes.program;

import zerosAndOnes.exception.ArrayHasMoreElementsException;
import zerosAndOnes.exception.StringCharactersException;
import zerosAndOnes.exception.StringHasCharException;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        HandlingCommand command = new HandlingCommand();

        ArrayList<Integer> list;
        try {
            list = command.processParameters(args);
        } catch (StringCharactersException | StringHasCharException | ArrayHasMoreElementsException e) {
            System.out.println(e);
            return;
        }

        for (Object o : list) System.out.print(o + " ");
    }
}
