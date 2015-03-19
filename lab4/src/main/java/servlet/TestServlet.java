package servlet;

import entity.TestTableEntity;
import model.CalcBean;

import javax.ejb.EJB;
import javax.ejb.SessionBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ruslan on 19.03.2015.
 */
@WebServlet(name = "TestServlet")
public class TestServlet extends HttpServlet {

    @EJB
    private CalcBean calcBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {




//        TestTableEntity testTableEntity =

        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Hello world!!" +
                "1 + 2 = " + calcBean.calc(1, 2) +
                "</body>\n" +
                "</html>");
    }
}
