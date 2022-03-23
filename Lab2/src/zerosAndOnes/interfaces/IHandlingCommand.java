package zerosAndOnes.interfaces;

import zerosAndOnes.exception.ArrayHasMoreElementsException;
import zerosAndOnes.exception.StringCharactersException;
import zerosAndOnes.exception.StringHasCharException;

import java.util.ArrayList;

public interface IHandlingCommand {
    ArrayList<Integer> processParameters(String[] args) throws
            StringCharactersException, StringHasCharException, ArrayHasMoreElementsException;
}
