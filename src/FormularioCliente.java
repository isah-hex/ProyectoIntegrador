import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FormularioCliente {
    public static void main(String[] args) {
        String[] opciones = {"Crear", "Listar", "Buscar por ID", "Actualizar", "Eliminar", "Volver"};
        while (true) {
            String sel = (String) JOptionPane.showInputDialog(
                null, "CRUD CLIENTE", "Clientes",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (sel == null || sel.equals("Volver")) return;
            try {
                switch (sel) {
                    case "Crear"         -> crear();
                    case "Listar"        -> listar();
                    case "Buscar por ID" -> buscar();
                    case "Actualizar"    -> actualizar();
                    case "Eliminar"      -> eliminar();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
            }
        }
    }

    private static void crear() throws SQLException {
        ClienteDAO.Cliente c = new ClienteDAO.Cliente();
        c.nombre    = pedir("Nombre:");
        c.apellido  = pedir("Apellido:");
        c.telefono  = pedir("Telefono:");
        c.correo    = pedir("Correo:");
        c.direccion = pedir("Direccion:");
        if (c.nombre == null || c.apellido == null) return;
        int id = ClienteDAO.crear(c);
        JOptionPane.showMessageDialog(null, "Cliente creado con id " + id);
    }

    private static void listar() throws SQLException {
        List<ClienteDAO.Cliente> lista = ClienteDAO.listar();
        StringBuilder sb = new StringBuilder("Clientes:\n");
        for (ClienteDAO.Cliente c : lista) sb.append(c).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void buscar() throws SQLException {
        String s = pedir("ID del cliente:");
        if (s == null) return;
        int id = Integer.parseInt(s);
        ClienteDAO.Cliente c = ClienteDAO.leer(id);
        JOptionPane.showMessageDialog(null, c == null ? "No encontrado" :
            c.nombre + " " + c.apellido + "\n" + c.telefono + "\n" + c.correo + "\n" + c.direccion);
    }

    private static void actualizar() throws SQLException {
        String s = pedir("ID a actualizar:");
        if (s == null) return;
        int id = Integer.parseInt(s);
        ClienteDAO.Cliente c = ClienteDAO.leer(id);
        if (c == null) { JOptionPane.showMessageDialog(null, "No existe"); return; }
        c.nombre    = pedirConValor("Nombre:", c.nombre);
        c.apellido  = pedirConValor("Apellido:", c.apellido);
        c.telefono  = pedirConValor("Telefono:", c.telefono);
        c.correo    = pedirConValor("Correo:", c.correo);
        c.direccion = pedirConValor("Direccion:", c.direccion);
        JOptionPane.showMessageDialog(null,
            ClienteDAO.actualizar(c) ? "Actualizado" : "No se pudo actualizar");
    }

    private static void eliminar() throws SQLException {
        String s = pedir("ID a eliminar:");
        if (s == null) return;
        int id = Integer.parseInt(s);
        int r = JOptionPane.showConfirmDialog(null, "Eliminar cliente " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            try {
                JOptionPane.showMessageDialog(null,
                    ClienteDAO.eliminar(id) ? "Eliminado" : "No encontrado");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                    "No se puede eliminar: el cliente tiene equipos asociados.");
            }
        }
    }

    private static String pedir(String msg) {
        return JOptionPane.showInputDialog(null, msg);
    }
    private static String pedirConValor(String msg, String actual) {
        String r = JOptionPane.showInputDialog(null, msg, actual);
        return (r == null || r.isEmpty()) ? actual : r;
    }
}