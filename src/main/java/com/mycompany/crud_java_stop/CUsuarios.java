/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java_stop;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;


/**
 *
 * @author emers
 */
public class CUsuarios {

    private int Codigo;
    private String NombreUsuario;
    private String Correo_electronicoUsuario;
    private String ContraseñaUsuario;
    private String DireccionUsuario;

    // Constructor vacío
    public CUsuarios() {
    }

    // Getters y setters
    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getCorreo_electronicoUsuario() {
        return Correo_electronicoUsuario;
    }

    public void setCorreo_electronicoUsuario(String Correo_electronicoUsuario) {
        this.Correo_electronicoUsuario = Correo_electronicoUsuario;
    }

    public String getContraseñaUsuario() {
        return ContraseñaUsuario;
    }

    public void setContraseñaUsuario(String ContraseñaUsuario) {
        this.ContraseñaUsuario = ContraseñaUsuario;
    }

    public String getDireccionUsuario() {
        return DireccionUsuario;
    }

    public void setDireccionUsuario(String DireccionUsuario) {
        this.DireccionUsuario = DireccionUsuario;
    }

    // Método para insertar un usuario
    public void InsertarUsuario(JTextField paramNombre, JTextField paramCorreo_electronico, JTextField paramContraseña, JTextField paramDireccion) {
        setNombreUsuario(paramNombre.getText());
        setCorreo_electronicoUsuario(paramCorreo_electronico.getText());
        setContraseñaUsuario(paramContraseña.getText());
        setDireccionUsuario(paramDireccion.getText());

        CConexion objetoConexion = new CConexion();
        Connection conexion = objetoConexion.estableceConexion();

        CallableStatement cs = null;
        try {
            conexion.setAutoCommit(true);

            String consulta = "INSERT INTO Usuario (Nombre, Correo_electronico, Contraseña, Direccion) VALUES (?, ?, ?, ?)";
            cs = conexion.prepareCall(consulta);
            cs.setString(1, getNombreUsuario());
            cs.setString(2, getCorreo_electronicoUsuario());
            cs.setString(3, getContraseñaUsuario());
            cs.setString(4, getDireccionUsuario());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Usuario insertado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha insertado el Usuario correctamente. Error: " + e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                if (conexion != null && !conexion.isClosed()) conexion.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public void MostrarUsuario(JTable paramTablaTotalUsuario) throws SQLException {
    CConexion objetoConexion = new CConexion();
    DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("id");
    modelo.addColumn("Nombre");
    modelo.addColumn("Correo electrónico");
    modelo.addColumn("Contraseña");
    modelo.addColumn("Dirección");

    paramTablaTotalUsuario.setModel(modelo);

    String sql = "SELECT * FROM Usuario;";
    String[] datos = new String[5];
    Statement st = null;
    ResultSet rs = null;
    Connection conexion = null;
    int rowCount = 0;  // Contador de filas

    try {
        // Establecer la conexión
        conexion = objetoConexion.estableceConexion();
        st = conexion.createStatement();
        rs = st.executeQuery(sql);

        // Agregar filas al modelo
        while (rs.next()) {
            datos[0] = rs.getString(1); // id
            datos[1] = rs.getString(2); // Nombre
            datos[2] = rs.getString(3); // Correo electrónico
            datos[3] = rs.getString(4); // Contraseña
            datos[4] = rs.getString(5); // Dirección
            modelo.addRow(datos);
            rowCount++; // Contar filas
        }

        // Mostrar el número de filas obtenidas
        JOptionPane.showMessageDialog(null, "Número de filas obtenidas: " + rowCount);

        paramTablaTotalUsuario.setModel(modelo); // Asignar modelo a la tabla

        // Si no se obtienen filas, mostrar un mensaje de advertencia
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron registros en la base de datos.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros, error: " + e.getMessage());
    } finally {
        // Cerrar el ResultSet, Statement y Connection
        if (rs != null) rs.close();
        if (st != null) st.close();
        if (conexion != null && !conexion.isClosed()) conexion.close();
    }
}

    // Método para seleccionar un usuario de un JTable
    public void SeleccionarUsuario(JTable paramTablaUsuario, JTextField paramNombre, JTextField paramCorreo_electronico, JTextField paramContraseña, JTextField paramDireccion) {
        try {
            int fila = paramTablaUsuario.getSelectedRow();

            if (fila >= 0) {
                paramNombre.setText(paramTablaUsuario.getValueAt(fila, 1).toString());
                paramCorreo_electronico.setText(paramTablaUsuario.getValueAt(fila, 2).toString());
                paramContraseña.setText(paramTablaUsuario.getValueAt(fila, 3).toString());
                paramDireccion.setText(paramTablaUsuario.getValueAt(fila, 4).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección, error: " + e.toString());
        }
    }
    
    // Método para modificar un usuario
    public void ModificarUsuario(JTextField paramCodigo, JTextField paramNombre, JTextField paramCorreo_electronico, JTextField paramContraseña, JTextField paramDireccion) {
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreUsuario(paramNombre.getText());
        setCorreo_electronicoUsuario(paramCorreo_electronico.getText());
        setContraseñaUsuario(paramContraseña.getText());
        setDireccionUsuario(paramDireccion.getText());

        CConexion objetoConexion = new CConexion();

        String consulta = "UPDATE Usuario SET Nombre = ?, Correo_electronico = ?, Contraseña = ?, Direccion = ? WHERE id = ?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombreUsuario());
            cs.setString(2, getCorreo_electronicoUsuario());
            cs.setString(3, getContraseñaUsuario());
            cs.setString(4, getDireccionUsuario());
            cs.setInt(5, getCodigo());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Modificación exitosa");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar, error: " + e.toString());
        }
    }
    
    // Método para eliminar un usuario
    public void EliminarUsuario(JTextField paramCodigo) {
        setCodigo(Integer.parseInt(paramCodigo.getText()));

        CConexion objetoConexion = new CConexion();

        String consulta = "DELETE FROM Usuario WHERE id = ?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el Usuario");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        }
    }
}