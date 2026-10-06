import javax.swing.JOptionPane;

public class MenuPrincipal {
    public static void main(String[] args) {
        String[] opciones = {
            "CRUD Cliente",
            "CRUD Equipo",
            "Registrar Técnico",
            "Registrar Orden de Servicio",
            "Registrar Pieza",
            "Registrar Pago",
            "Consulta combinada (multi-tabla)",
            "Salir"
        };
        while (true) {
            String sel = (String) JOptionPane.showInputDialog(
                null, "Selecciona una opción:", "Menú Principal - Taller",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (sel == null || sel.equals("Salir")) break;
            switch (sel) {
                case "CRUD Cliente"                     -> FormularioCliente.main(null);
                case "CRUD Equipo"                      -> FormularioEquipo.main(null);
                case "Registrar Técnico"                -> FormularioTecnico.main(null);
                case "Registrar Orden de Servicio"      -> FormularioOrden.main(null);
                case "Registrar Pieza"                  -> FormularioPieza.main(null);
                case "Registrar Pago"                   -> FormularioPago.main(null);
                case "Consulta combinada (multi-tabla)" -> ConsultaCombinada.main(null);
            }
        }
    }
}