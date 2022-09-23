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

        /*
         create table Users (
         iduser int,
         name varchar(255),
         phone varchar(10),
         city varchar(100)
        )

        insert into users values (1, 'ronaldo','9872637483', 'manchester');
        insert into users values (2, 'Messi','9872637483', 'paris');
         */

        


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

        /*
        create procedure crearUsuario ( in p_iduser int,
								in p_name varchar(255), 
								in p_phone varchar(10),
								in p_city varchar(100) )
        begin 
        insert into users values (p_iduser, p_name,p_phone, p_city);
        end
         */


        CallableStatement callSp = con.prepareCall("call crearUsuario(?,?,?,?)");
        callSp.setInt(1, 4 );
        callSp.setString(2, "neymar");
        callSp.setString(3, "998374634");
        callSp.setString(4, "paris");
        ResultSet resultado = callSp.executeQuery();
        System.out.println(resultado);

        System.out.println("############# Consultas con callable statement - Actualizar usuario ###############");

        /*
         create procedure actualizarUsuario ( in p_iduser int,
									 in p_name varchar(255), 
									 in p_phone varchar(10),
									 in p_city varchar(100) )
        begin 
            update users 
            set name=p_name, phone=p_phone, city=p_city
            where iduser=p_iduser;
        end
         */


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