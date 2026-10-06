import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FormularioEquipo {
    public static void main(String[] args) {
        String[] opciones = {"Crear", "Listar con detalle", "Actualizar", "Eliminar", "Volver"};
        while (true) {
            String sel = (String) JOptionPane.showInputDialog(
                null, "CRUD EQUIPO", "Equipos",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (sel == null || sel.equals("Volver")) return;
            try {
                switch (sel) {
                    case "Crear"              -> crear();
                    case "Listar con detalle" -> listar();
                    case "Actualizar"         -> actualizar();
                    case "Eliminar"           -> eliminar();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
            }
        }
    }

    private static void crear() throws SQLException {
        EquipoDAO.Equipo e = new EquipoDAO.Equipo();
        e.modelo                  = pedir("Modelo:");
        e.color                   = pedir("Color:");
        e.numero_serie            = pedir("Numero de serie:");
        e.descripcion_condiciones = pedir("Descripcion/condiciones:");
        String cs = pedir("ID del cliente:");
        String ts = pedir("ID tipo equipo (1=Laptop,2=Desktop,3=Smartphone,4=Tablet,5=Impresora):");
        String ms = pedir("ID marca (1=Asus,2=Apple,3=Dell,4=Lenovo,5=HP,6=Acer):");
        if (cs == null || ts == null || ms == null) return;
        e.id_cliente     = Integer.parseInt(cs);
        e.id_tipo_equipo = Integer.parseInt(ts);
        e.id_marca       = Integer.parseInt(ms);
        int id = EquipoDAO.crear(e);
        JOptionPane.showMessageDialog(null, "Equipo creado con id " + id);
    }

    private static void listar() throws SQLException {
        List<EquipoDAO.EquipoView> lista = EquipoDAO.listarConDetalle();
        StringBuilder sb = new StringBuilder("Equipos (JOIN CLIENTE+MARCA+TIPO):\n\n");
        for (EquipoDAO.EquipoView v : lista) sb.append(v).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void actualizar() throws SQLException {
        String s = pedir("ID a actualizar:");
        if (s == null) return;
        int id = Integer.parseInt(s);
        EquipoDAO.Equipo e = EquipoDAO.leer(id);
        if (e == null) { JOptionPane.showMessageDialog(null, "No existe"); return; }
        e.modelo                  = pedirConValor("Modelo:", e.modelo);
        e.color                   = pedirConValor("Color:", e.color);
        e.numero_serie            = pedirConValor("N serie:", e.numero_serie);
        e.descripcion_condiciones = pedirConValor("Descripcion:", e.descripcion_condiciones);
        e.id_cliente              = Integer.parseInt(pedirConValor("ID cliente:", String.valueOf(e.id_cliente)));
        e.id_tipo_equipo          = Integer.parseInt(pedirConValor("ID tipo equipo:", String.valueOf(e.id_tipo_equipo)));
        e.id_marca                = Integer.parseInt(pedirConValor("ID marca:", String.valueOf(e.id_marca)));
        JOptionPane.showMessageDialog(null,
            EquipoDAO.actualizar(e) ? "Actualizado" : "No se pudo actualizar");
    }

    private static void eliminar() throws SQLException {
        String s = pedir("ID a eliminar:");
        if (s == null) return;
        int id = Integer.parseInt(s);
        int r = JOptionPane.showConfirmDialog(null, "Eliminar equipo " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            try {
                JOptionPane.showMessageDialog(null,
                    EquipoDAO.eliminar(id) ? "Eliminado" : "No encontrado");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                    "No se puede eliminar: el equipo tiene ordenes de servicio.");
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