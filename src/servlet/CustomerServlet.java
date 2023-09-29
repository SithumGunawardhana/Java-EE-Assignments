package servlet;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer");
        ResultSet resultSet = statement.executeQuery();

        resp.addHeader("Content-Type","application/json");
        JsonArrayBuilder allCustomers = Json.createArrayBuilder();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String salary = resultSet.getString(4);


            JsonObjectBuilder customerObject = Json.createObjectBuilder();


            customerObject.add("id", id);
            customerObject.add("name", name);
            customerObject.add("address", address);
            customerObject.add("salary", salary);

            allCustomers.add(customerObject.build());

        }

        resp.getWriter().print(allCustomers.build());


    }catch (ClassNotFoundException | SQLException e){

        throw new RuntimeException(e);
    }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cusId=req.getParameter("cusId");
        String cusName=req.getParameter("cusName");
        String cusAddress=req.getParameter("cusAddress");
        String cusSalary=req.getParameter("cusSalary");

        resp.addHeader("Content-Type","application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
            PreparedStatement stm = connection.prepareStatement("INSERT INTO customer values (?,?,?,?)");

            stm.setObject(1,cusId);
            stm.setObject(2,cusName);
            stm.setObject(3,cusAddress);
            stm.setObject(4,cusSalary);


            if (stm.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state","ok");
                response.add("message","sucsessfully added ! ");
                response.add("data","");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }


        } catch (ClassNotFoundException|SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state","Error");
            objectBuilder.add("message",e.getMessage());
            objectBuilder.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id=req.getParameter("cusId");
        resp.addHeader("Content-Type","application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer where id=?");

            pstm.setObject(1,id);
            if (pstm.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state","ok");
                response.add("message","sucsessfully deleted ! ");
                response.add("data","");
                resp.setStatus(200);
                resp.getWriter().print(response.build());

            }


        } catch (ClassNotFoundException | SQLException e) {

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state","Error");
            objectBuilder.add("message",e.getMessage());
            objectBuilder.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());

        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String cusId=req.getParameter("cusId");
        String cusName=req.getParameter("cusName");
        String cusAddress=req.getParameter("cusAddress");
        String cusSalary=req.getParameter("cusSalary");

        resp.addHeader("Content-Type","application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");

            PreparedStatement pstm = connection.prepareStatement("UPDATE customer set name=?,address=?,salary=? where id=?");

            pstm.setObject(4,cusId);
            pstm.setObject(1,cusName);
            pstm.setObject(2,cusAddress);
            pstm.setObject(3,cusSalary);

            if(pstm.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state","ok");
                response.add("message","sucsessfully updated ! ");
                response.add("data","");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }

        } catch (ClassNotFoundException|SQLException e) {

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state","Error");
            objectBuilder.add("message",e.getMessage());
            objectBuilder.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());


        }

    }
}
