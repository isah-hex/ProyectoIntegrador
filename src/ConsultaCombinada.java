import javax.swing.*;
import java.sql.*;

public class ConsultaCombinada {
    public static void main(String[] args) {
        String sql =
            "SELECT o.id_orden, " +
            "       CONCAT(c.nombre,' ',c.apellido) AS cliente, " +
            "       m.nombre_marca AS marca, e.modelo AS equipo, " +
            "       CONCAT(t.nombre,' ',t.apellido) AS tecnico, " +
            "       o.estado, " +
            "       IFNULL(r.diagnostico,'-') AS diagnostico, " +
            "       IFNULL(r.costo_mano_obra, 0) AS mano_obra, " +
            "       IFNULL(p.monto, 0) AS pago " +
            "FROM ORDEN_SERVICIO o " +
            "INNER JOIN EQUIPO  e ON o.id_equipo  = e.id_equipo " +
            "INNER JOIN CLIENTE c ON e.id_cliente = c.id_cliente " +
            "INNER JOIN MARCA   m ON e.id_marca   = m.id_marca " +
            "INNER JOIN TECNICO t ON o.id_tecnico = t.id_tecnico " +
            "LEFT  JOIN REPARACION r ON r.id_orden = o.id_orden " +
            "LEFT  JOIN PAGO       p ON p.id_orden = o.id_orden " +
            "ORDER BY o.id_orden";

        StringBuilder sb = new StringBuilder("Órdenes con detalle cruzado (5+ tablas):\n\n");
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append("Orden #").append(rs.getInt("id_orden"))
                  .append("\n  Cliente: ").append(rs.getString("cliente"))
                  .append("\n  Equipo : ").append(rs.getString("marca")).append(" ").append(rs.getString("equipo"))
                  .append("\n  Técnico: ").append(rs.getString("tecnico"))
                  .append("\n  Estado : ").append(rs.getString("estado"))
                  .append("\n  Diag.  : ").append(rs.getString("diagnostico"))
                  .append("\n  M.Obra : $").append(rs.getBigDecimal("mano_obra"))
                  .append("\n  Pago   : $").append(rs.getBigDecimal("pago"))
                  .append("\n----------------------------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
            System.out.println(sb);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}