package examples;

public class Test {
    public static void main (String[] args) {
        SuperClass supC= new SuperClass (5, true );// Создать объект суперкласса
        System.out.println ("supC.x= "+ supC.x + " supC.b= " + supC.getB());
        // Создать объект подкласса
        SubClass subC= new SubClass (55, 555, false );
        // Получить объект данных
        Dan d1= new Dan (0, 0, true);
        subC.get(d1);
        System.out.println ("subC.x= "+ d1.xSub +" supC.x= "+ d1.xSup + " subC.b= " + d1.b);
    }
}
