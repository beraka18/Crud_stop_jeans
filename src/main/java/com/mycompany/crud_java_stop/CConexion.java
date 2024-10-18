/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java_stop;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author emers
 */
public class CConexion {
    
    Connection conectar = null;
    
    String Usuario = "root";
    String Contraseña = "";
    String bd = "crud_adaptacion_a_pagina_web_stop";  // Elimina ".usuario" de la base de datos
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;  // "cadena" es en minúsculas
    
    public Connection estableceConexion(){
    
        try {
            // Carga el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conéctate a la base de datos usando la cadena de conexión
            conectar = DriverManager.getConnection(cadena, Usuario, Contraseña);  // "conectar" es en minúsculas
            JOptionPane.showMessageDialog(null, "La conexión se ha realizado con éxito");
            
        } catch (Exception e) {
            // Error de conexión
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos, error: " + e.toString());
        }
        return conectar;
    }
}