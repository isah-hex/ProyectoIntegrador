import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormularioTecnico {
    public static void main(String[] args) {
        String nombre       = JOptionPane.showInputDialog(null, "Nombre del Tecnico:");
        String apellido     = JOptionPane.showInputDialog(null, "Apellido:");
        String especialidad = JOptionPane.showInputDialog(null, "Especialidad:");
        String telefono     = JOptionPane.showInputDialog(null, "Telefono:");

        if (nombre == null || apellido == null ||
            nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y apellido son obligatorios.");
            return;
        }

        String sql = "INSERT INTO TECNICO (nombre, apellido, especialidad, telefono) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, especialidad);
            ps.setString(4, telefono);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Tecnico registrado correctamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}