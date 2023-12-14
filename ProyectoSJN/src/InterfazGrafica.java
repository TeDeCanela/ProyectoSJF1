/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author nmh14
 */
public class InterfazGrafica extends JFrame {

    private JTable tablaProcesos;
    private DefaultTableModel tablaMuestraP;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaProcesos;
    private JLabel etiquetaProcesoActual;
    private JTextArea txtPEjecucion;

    public InterfazGrafica() {
        setTitle("Simulador de Proceso de SJF");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        etiquetaProcesoActual = new JLabel("Procesos en espera");
        tablaMuestraP = new DefaultTableModel();
        tablaProcesos = new JTable(tablaMuestraP);
        txtPEjecucion = new JTextArea();
        txtPEjecucion.setEditable(false);
        
        tablaMuestraP.addColumn("ID");
        tablaMuestraP.addColumn("Tiempo");
        tablaMuestraP.addColumn("Memoria");
        
        JScrollPane scrollPane = new JScrollPane(tablaProcesos);
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        
        contenedorTabla.add(etiquetaProcesoActual, BorderLayout.NORTH);
        contenedorTabla.add(scrollPane, BorderLayout.CENTER);
        contenedorTabla.add(txtPEjecucion, BorderLayout.SOUTH);
        add(contenedorTabla);
    }

    public void actualizarListaProcesos(ArrayList<Proceso> procesos) {
        tablaMuestraP.setRowCount(0);
        for (Proceso proceso : procesos) {
            tablaMuestraP.addRow(new Object[]{proceso.getId(), proceso.getTiempo(), proceso.getMemoria()});
        }
    }

    public void actualizarProcesoActual(Proceso proceso) {
        int filaProcesoActual = getProcesoNuevo(proceso.getId());
        if (filaProcesoActual >= 0) {
            tablaProcesos.setRowSelectionInterval(filaProcesoActual, filaProcesoActual);
        }
    }

    private int getProcesoNuevo(int idProceso) {
        for (int i = 0; i < tablaMuestraP.getRowCount(); i++) {
            Object valorCelda = tablaMuestraP.getValueAt(i, 0);
            if (valorCelda != null && Integer.parseInt(valorCelda.toString()) == idProceso) {
                return i;
            }
        }
        return -1;
    }

   public void mostrarProcesoEnEjecucion(String infoProceso) {
        txtPEjecucion.setText(infoProceso);
    }
}
