import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormularioPago {
    public static void main(String[] args) {
        String fecha  = JOptionPane.showInputDialog(null, "Fecha de pago (YYYY-MM-DD):");
        String monto  = JOptionPane.showInputDialog(null, "Monto:");
        String metodo = JOptionPane.showInputDialog(null, "Metodo (Efectivo/Tarjeta/Transferencia):");
        String idOrden= JOptionPane.showInputDialog(null, "ID de la Orden:");

        if (fecha == null || monto == null || idOrden == null) {
            JOptionPane.showMessageDialog(null, "Datos incompletos.");
            return;
        }

        try {
            Date f  = Date.valueOf(fecha.trim());
            double m = Double.parseDouble(monto.trim());
            int io   = Integer.parseInt(idOrden.trim());

            String sql = "INSERT INTO PAGO (fecha_pago, monto, metodo_pago, id_orden) VALUES (?, ?, ?, ?)";
            try (Connection con = ConexionDB.obtenerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDate(1, f);
                ps.setDouble(2, m);
                ps.setString(3, metodo);
                ps.setInt(4, io);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Pago registrado.");
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Fecha o numero invalido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + e.getMessage());
        }
    }
}