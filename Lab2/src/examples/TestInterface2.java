package examples;

import examples.interfaces.IConst;
import examples.interfaces.IConst1;
import examples.interfaces.IConst2;

public class TestInterface2 {
    public static void main (String[] args) {
        ClassInt cI= new ClassInt();
        System.out.print ("cI.verConst= " + cI.verConst);
        System.out.println (" ClassInt.verConst= " + ClassInt.verConst);
//-----
        IConst1 iC1= cI;
        System.out.print ("iC1.verConst= " + iC1.verConst);
        IConst2 iC2= cI;
        System.out.print (" iC2.verConst= " + iC2.verConst);
        IConst iC= cI;
        System.out.println (" iC.verConst= " + iC.verConst);
//--------------
        System.out.print ("IConst1.verConst= " + IConst1.verConst);
        System.out.print (" IConst2.verConst= " + IConst2.verConst);
        System.out.println (" IConst.verConst= " + IConst.verConst);
//--------------
        System.out.println ("cI.get(true)= " + cI.get(true) + " cI.get(false)= "+ cI.get(false));
    }
}
