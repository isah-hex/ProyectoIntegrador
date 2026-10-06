import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public static class Cliente {
        public int id_cliente;
        public String nombre, apellido, telefono, correo, direccion;
        @Override public String toString() {
            return id_cliente + " - " + nombre + " " + apellido;
        }
    }

    public static int crear(Cliente c) throws SQLException {
        String sql = "INSERT INTO CLIENTE (nombre, apellido, telefono, correo, direccion) VALUES (?,?,?,?,?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.nombre);
            ps.setString(2, c.apellido);
            ps.setString(3, c.telefono);
            ps.setString(4, c.correo);
            ps.setString(5, c.direccion);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { c.id_cliente = rs.getInt(1); return c.id_cliente; }
            }
        }
        return -1;
    }

    public static Cliente leer(int id) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE id_cliente = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public static List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE ORDER BY id_cliente";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public static boolean actualizar(Cliente c) throws SQLException {
        String sql = "UPDATE CLIENTE SET nombre=?, apellido=?, telefono=?, correo=?, direccion=? WHERE id_cliente=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.nombre);
            ps.setString(2, c.apellido);
            ps.setString(3, c.telefono);
            ps.setString(4, c.correo);
            ps.setString(5, c.direccion);
            ps.setInt(6, c.id_cliente);
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private static Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.id_cliente = rs.getInt("id_cliente");
        c.nombre     = rs.getString("nombre");
        c.apellido   = rs.getString("apellido");
        c.telefono   = rs.getString("telefono");
        c.correo     = rs.getString("correo");
        c.direccion  = rs.getString("direccion");
        return c;
    }
}