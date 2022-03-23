package examples;

import examples.interfaces.IConst;
import examples.interfaces.IConst1;
import examples.interfaces.IConst2;

public class ClassInt implements IConst {
    public int get (boolean b){ return b ? IConst1.verConst : IConst2.verConst;}
}
