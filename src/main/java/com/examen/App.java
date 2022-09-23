package com.examen;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test",
            "root",
            "" // windows no lleva contrasenna si es de xamp 
        );

        


        System.out.println("############# Consultas con statement - listar usuarios ###############");   
       
        //crear statment
        Statement stmt = con.createStatement();

        //ejecutar el query
        ResultSet result = stmt.executeQuery("select * from users");

        //recorre el resultado
        while(result.next()){
            System.out.println(result.getString("idUser"));
            System.out.println(result.getString("name"));
            System.out.println(result.getString("phone"));
            System.out.println(result.getString("city"));
        }

        System.out.println("############# Consultas con prepared statement - eliminar usuario ###############");   

        PreparedStatement preparedStatement = con.prepareStatement("delete from Users where iduser=?");

        preparedStatement.setDouble(1, 2);

        int delnum = preparedStatement.executeUpdate();
        System.out.println(delnum);
  


        System.out.println("############# Consultas con callable statement - crear usuario ###############");
        CallableStatement callSp = con.prepareCall("call crearUsuario(?,?,?,?)");
        callSp.setInt(1, 4 );
        callSp.setString(2, "neymar");
        callSp.setString(3, "998374634");
        callSp.setString(4, "paris");
        ResultSet resultado = callSp.executeQuery();
        System.out.println(resultado);

        System.out.println("############# Consultas con callable statement - Actualizar usuario ###############");
        CallableStatement callSp1 = con.prepareCall("call actualizarUsuario(?,?,?,?)");
        callSp1.setInt(1, 1 );
        callSp1.setString(2, "cristiano");
        callSp1.setString(3, "9983746334");
        callSp1.setString(4, "manchester");
        ResultSet resultado2 = callSp1.executeQuery();
        System.out.println(resultado2);
        

        



        //cerrar conexion
        con.close();

        
    }

}