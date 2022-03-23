package examples;

public class SubClass extends SuperClass{
    int x;
    public SubClass (int Xsup, int Xsub, boolean B) // Конструктор
    {
        super (Xsub, B); // Вызов конструктора базового класса
        x= Xsup;
    }
    // Получить объект типа Dan с данными
    public Dan get () {return new Dan(x, super.x, super.getB());}

    public void get (Dan d) {d.xSub= x; d.xSup= super.x; d.b= super.getB();}
}
