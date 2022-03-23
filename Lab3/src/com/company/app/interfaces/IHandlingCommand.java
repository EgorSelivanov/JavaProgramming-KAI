package com.company.app.interfaces;

import com.company.app.exceptions.StringCharactersException;
import com.company.app.exceptions.StringHasCharException;

import java.util.ArrayList;

public interface IHandlingCommand {
    ArrayList<Integer> processParameters(String line) throws
            StringCharactersException, StringHasCharException;
}
