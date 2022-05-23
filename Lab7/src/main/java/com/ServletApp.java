package main.java.com;

import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * @author 4310 Селиванов Е.О.
 * Вариант 14: 57 = 111001
 * 5) При перезагрузке страницы сервлета должно отображаться: 1 – значение счётчика
 *  обращений к странице сервлета после его запуска.
 * 6) Организовать вывод результатов работы сервлета: 1 – данные полученные от сервлета
 * должны быть каким-то образом размещены в видимой таблице, в таблице
 * допускается произвольное число столбцов и строк
 * 7) Реализовать при обновлении страницы сервлета: 1 -
 * увеличение размера текста в таблице до заданной величины, после чего на странице
 * должна появляться надпись (не в таблице), информирующая о том, что дальнейшее
 * увеличение не возможно.
 * 8) Реализовать возможность сброса размера текста в таблице через параметр строки
 *      url-запроса: 0 - до значения по умолчанию.
 * 9) Среди параметров, передаваемых в сервлет, нужно передавать Ф.И.О студента, и номер его
 * группы, которые должны отображаться следующим образом: : 0 - в заголовке web-страницы возвращаемой
 * сервлетом клиенту.
 * 11) При необходимости могут быть изменены порты, по которым контейнер сервлетов
 *  Tomcat слушает запросы. Для изменения портов нужно в среде NetBeans войти в
 *  меню «Сервис\Серверы»: 1 — изменить на произвольный.
 */
public class ServletApp extends HttpServlet {
    private int counterOfReload;
    private static final int defaultSize = 5;
    private int currentSize;
    private String studentName;
    private String studentGroupNumber;
    private String zerosAndOnes;
    private PrintWriter out;

    public ServletApp() {
        counterOfReload = 0;
        currentSize = defaultSize;
    }


    /** Пример запроса:
     * http://localhost:8000/servletApp?name=Egor Selivanov&number=4310&defaultSize=false&array=0 1 1 0 1 1 1 0 0 1 0 0 0 1
     * @param request servlet request
     * @param response servlet response
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        studentName = request.getParameter("name");
        studentGroupNumber = request.getParameter("number");

        String parameterSize = request.getParameter("defaultSize");
        if (parameterSize != null && parameterSize.equals("true")) {
            currentSize = defaultSize;
        }

        printStudentInformation();
        processArray(request.getParameter("array"));
        printTable();

        checkFontSize();
        out.println("</body>");
        out.println("</html>");

        counterOfReload++;
    }

    private void printStudentInformation() {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title> ФИО: " + studentName + " Группа №" + studentGroupNumber + "</title>");
        out.println("</head>");
        out.println("<body>");
    }

    private void processArray(String parameter) {
        ArrayList<Integer> al = new ArrayList();//создане списка для 0

        String[] arrayZerosAndOnes = parameter.split(" ");

        int number = 0;
        for (String x : arrayZerosAndOnes) {
            try {
                number = Integer.parseInt(x);
            }
            catch (Exception ex)
            {
                zerosAndOnes = "Letter entered! Enter 0 or 1";
            }
            if (number == 0) {
                al.add(0, number);//добавление в список 0
            } else if (number == 1){
                al.add(number);//добавление в список 1
            } else{
                zerosAndOnes = "Enter 0 or 1!";
            }
        }

        zerosAndOnes = al.toString();
    }

    private void printTable() {
        out.println("<h" + currentSize + "><table border>"+
                "<tr>" + "<td>Счетчик обращений</td>" + "<td>Текущий параметр тега h</td>" + "<td>Обработанный массив</td>" +
                "<tr>" + "<td>" + counterOfReload + "</td>" + "<td>" + currentSize + "</td>" + "<td>" + zerosAndOnes + "</td>" +
                "</table></h" + currentSize + ">");

    }

    private void checkFontSize() {
        if (currentSize == 1) {
            out.println("<h1> Максимальный размер текста достигнут! </h1>");
        }
        else {
            currentSize--;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }
}
