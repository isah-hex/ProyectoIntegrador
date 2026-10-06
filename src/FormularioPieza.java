import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormularioPieza {
    public static void main(String[] args) {
        String nombre = JOptionPane.showInputDialog(null, "Nombre de la pieza:");
        String desc   = JOptionPane.showInputDialog(null, "Descripcion:");
        String precio = JOptionPane.showInputDialog(null, "Precio unitario:");

        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre es obligatorio.");
            return;
        }

        try {
            double p = (precio == null || precio.isEmpty()) ? 0 : Double.parseDouble(precio);

            String sql = "INSERT INTO PIEZA (nombre_pieza, descripcion, precio_unitario) VALUES (?, ?, ?)";

            try (Connection con = ConexionDB.obtenerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, desc);
                ps.setDouble(3, p);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Pieza registrada.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Precio invalido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + e.getMessage());
        }
    }
}