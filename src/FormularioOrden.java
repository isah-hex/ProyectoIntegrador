import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormularioOrden {
    public static void main(String[] args) {
        String fechaRecepcion = JOptionPane.showInputDialog(null, "Fecha de recepcion (YYYY-MM-DD):");
        String fechaEstimada  = JOptionPane.showInputDialog(null, "Fecha entrega estimada (YYYY-MM-DD, opcional):");
        String estado         = JOptionPane.showInputDialog(null, "Estado (Pendiente/En proceso/Terminado/Entregado):");
        String idEquipoStr    = JOptionPane.showInputDialog(null, "ID del Equipo:");
        String idTecnicoStr   = JOptionPane.showInputDialog(null, "ID del Tecnico responsable:");

        if (fechaRecepcion == null || idEquipoStr == null || idTecnicoStr == null) {
            JOptionPane.showMessageDialog(null, "Datos incompletos.");
            return;
        }

        try {
            Date fRec = Date.valueOf(fechaRecepcion.trim());
            Date fEst = (fechaEstimada == null || fechaEstimada.trim().isEmpty())
                    ? null : Date.valueOf(fechaEstimada.trim());
            int idEquipo  = Integer.parseInt(idEquipoStr.trim());
            int idTecnico = Integer.parseInt(idTecnicoStr.trim());

            String sql = "INSERT INTO ORDEN_SERVICIO (fecha_recepcion, fecha_entrega_estimada, estado, id_equipo, id_tecnico) VALUES (?, ?, ?, ?, ?)";

            try (Connection con = ConexionDB.obtenerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDate(1, fRec);
                if (fEst != null) ps.setDate(2, fEst);
                else ps.setNull(2, java.sql.Types.DATE);
                ps.setString(3, (estado == null || estado.isEmpty()) ? "Pendiente" : estado);
                ps.setInt(4, idEquipo);
                ps.setInt(5, idTecnico);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Orden de servicio registrada.");
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha invalido. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + e.getMessage());
        }
    }
}