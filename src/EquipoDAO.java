import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public static class EquipoView {
        public int id_equipo;
        public String modelo, color, numero_serie, descripcion_condiciones;
        public String cliente, marca, tipo;
        @Override public String toString() {
            return id_equipo + " - " + marca + " " + modelo + " (" + numero_serie + ") de " + cliente;
        }
    }

    public static class Equipo {
        public int id_equipo, id_cliente, id_tipo_equipo, id_marca;
        public String modelo, color, numero_serie, descripcion_condiciones;
    }

    public static int crear(Equipo e) throws SQLException {
        String sql = "INSERT INTO EQUIPO (modelo, color, numero_serie, descripcion_condiciones, " +
                     "id_cliente, id_tipo_equipo, id_marca) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.modelo);
            ps.setString(2, e.color);
            ps.setString(3, e.numero_serie);
            ps.setString(4, e.descripcion_condiciones);
            ps.setInt(5, e.id_cliente);
            ps.setInt(6, e.id_tipo_equipo);
            ps.setInt(7, e.id_marca);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { e.id_equipo = rs.getInt(1); return e.id_equipo; }
            }
        }
        return -1;
    }

    public static List<EquipoView> listarConDetalle() throws SQLException {
        List<EquipoView> lista = new ArrayList<>();
        String sql =
            "SELECT e.id_equipo, e.modelo, e.color, e.numero_serie, e.descripcion_condiciones, " +
            "       CONCAT(c.nombre,' ',c.apellido) AS cliente, " +
            "       m.nombre_marca AS marca, te.nombre_tipo AS tipo " +
            "FROM EQUIPO e " +
            "INNER JOIN CLIENTE     c  ON e.id_cliente     = c.id_cliente " +
            "INNER JOIN MARCA       m  ON e.id_marca       = m.id_marca " +
            "INNER JOIN TIPO_EQUIPO te ON e.id_tipo_equipo = te.id_tipo_equipo " +
            "ORDER BY e.id_equipo";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EquipoView v = new EquipoView();
                v.id_equipo               = rs.getInt("id_equipo");
                v.modelo                  = rs.getString("modelo");
                v.color                   = rs.getString("color");
                v.numero_serie            = rs.getString("numero_serie");
                v.descripcion_condiciones = rs.getString("descripcion_condiciones");
                v.cliente                 = rs.getString("cliente");
                v.marca                   = rs.getString("marca");
                v.tipo                    = rs.getString("tipo");
                lista.add(v);
            }
        }
        return lista;
    }

    public static Equipo leer(int id) throws SQLException {
        String sql = "SELECT * FROM EQUIPO WHERE id_equipo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Equipo e = new Equipo();
                    e.id_equipo               = rs.getInt("id_equipo");
                    e.modelo                  = rs.getString("modelo");
                    e.color                   = rs.getString("color");
                    e.numero_serie            = rs.getString("numero_serie");
                    e.descripcion_condiciones = rs.getString("descripcion_condiciones");
                    e.id_cliente              = rs.getInt("id_cliente");
                    e.id_tipo_equipo          = rs.getInt("id_tipo_equipo");
                    e.id_marca                = rs.getInt("id_marca");
                    return e;
                }
            }
        }
        return null;
    }

    public static boolean actualizar(Equipo e) throws SQLException {
        String sql = "UPDATE EQUIPO SET modelo=?, color=?, numero_serie=?, descripcion_condiciones=?, " +
                     "id_cliente=?, id_tipo_equipo=?, id_marca=? WHERE id_equipo=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.modelo);
            ps.setString(2, e.color);
            ps.setString(3, e.numero_serie);
            ps.setString(4, e.descripcion_condiciones);
            ps.setInt(5, e.id_cliente);
            ps.setInt(6, e.id_tipo_equipo);
            ps.setInt(7, e.id_marca);
            ps.setInt(8, e.id_equipo);
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM EQUIPO WHERE id_equipo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}