package zerosAndOnes.program;

import zerosAndOnes.exception.ArrayHasMoreElementsException;
import zerosAndOnes.exception.StringCharactersException;
import zerosAndOnes.exception.StringHasCharException;
import zerosAndOnes.interfaces.IHandlingCommand;
import zerosAndOnes.interfaces.IParameters;

import java.util.ArrayList;

public class HandlingCommand implements IHandlingCommand, IParameters {
    @Override
    public ArrayList<Integer> processParameters(String[] args) throws
            StringCharactersException, StringHasCharException, ArrayHasMoreElementsException {

        if (args.length > IParameters.numberOfParameters)
        {
            throw new ArrayHasMoreElementsException();
        }

        ArrayList<Integer> arrayList = new ArrayList();
        int number;
        for (String x : args) {
            try {
                number = Integer.parseInt(x);
            }
            catch (Exception ex)
            {
                if (x.length() == 1)
                    throw new StringHasCharException();
                else
                    throw new StringCharactersException();
            }
            if (number == 0) {
                arrayList.add(0, number);
            } else if (number == 1){
                arrayList.add(arrayList.size(), number);
            }
            else throw new StringCharactersException();
        }
        return arrayList;
    }
}
