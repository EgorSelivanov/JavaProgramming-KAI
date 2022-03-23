package com.company.app;

import com.company.app.exceptions.StringCharactersException;
import com.company.app.exceptions.StringHasCharException;
import com.company.app.interfaces.IHandlingCommand;

import java.util.ArrayList;

public class SorterZerosAndOnes implements IHandlingCommand {
    @Override
    public ArrayList<Integer> processParameters(String line) throws
            StringCharactersException, StringHasCharException {

        ArrayList<Integer> arrayList = new ArrayList();

        String[] inputParameters = line.split(" ");
        int number;
        for (String x : inputParameters) {
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
