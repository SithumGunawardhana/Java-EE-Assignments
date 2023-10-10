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

@WebServlet(urlPatterns = "/pages/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
            PreparedStatement statement = connection.prepareStatement("SELECT *  from item");
            ResultSet resultSet = statement.executeQuery();

            resp.addHeader("Content-Type","application/json");
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (resultSet.next()){
                String code=resultSet.getString(1);
                String name=resultSet.getString(2);
                String qty=resultSet.getString(3);
                String unitPrice=resultSet.getString(4);


                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code",code);
                objectBuilder.add("name",name);
                objectBuilder.add("qty",qty);
                objectBuilder.add("uniPrice",unitPrice);
                arrayBuilder.add(objectBuilder.build());

            }
            resp.getWriter().print(arrayBuilder.build());

        } catch (ClassNotFoundException|SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("iCode");
        String name=req.getParameter("iName");
        int qty= Integer.parseInt(req.getParameter("iQty"));
        double iUniPrice= Double.parseDouble(req.getParameter("iUniPrice"));


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");

            resp.addHeader("Content-Type","application/json");

            statement.setObject(1,code);
            statement.setObject(2,name);
            statement.setObject(3,qty);
            statement.setObject(4,iUniPrice);

            if(statement.executeUpdate()>0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("Status","ok");
                response.add("message","Item Added !");
                response.add("data","");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }


        } catch (ClassNotFoundException | SQLException e) {


            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status","ok");
            objectBuilder.add("message",e.getMessage());
            objectBuilder.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("iCode");
        resp.addHeader("Content-Type","application/json");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3","root","Sithum1234");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM item where code=?");

            statement.setObject(1,code);



            if(statement.executeUpdate()>0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", "ok");
                response.add("message", "Success full deleted ! ");
                response.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }

        } catch (ClassNotFoundException | SQLException e) {

            JsonObjectBuilder obb = Json.createObjectBuilder();
            obb.add("status","ok");
            obb.add("message",e.getMessage());
            obb.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(obb.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code=req.getParameter("iCode");
        String name=req.getParameter("iName");
        String qty=req.getParameter("iQty");
        String unitPrice=req.getParameter("iUniPrice");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company3", "root", "Sithum1234");
            PreparedStatement statement = connection.prepareStatement("UPDATE item SET name=?,qty=?,up=? WHERE code=?");
            statement.setObject(1,name);
            statement.setObject(2,qty);
            statement.setObject(3,unitPrice);
            statement.setObject(4,code);

            resp.addHeader("Content-Type","application/json");


            if(statement.executeUpdate()>0) {
                JsonObjectBuilder respon = Json.createObjectBuilder();
                respon.add("status", "ok");
                respon.add("message", "Updated ! ");
                respon.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(respon.build());
            }



        } catch (ClassNotFoundException |SQLException e) {
            JsonObjectBuilder obb = Json.createObjectBuilder();
            obb.add("status","ok");
            obb.add("message",e.getMessage());
            obb.add("data","");
            resp.setStatus(400);
            resp.getWriter().print(obb.build());
        }

    }

}
