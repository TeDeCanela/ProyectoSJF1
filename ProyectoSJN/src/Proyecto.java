/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author nmh14
 */
public class Proyecto {

    public static void main(String args[]) {
        InterfazGrafica interfaz = new InterfazGrafica();
        ProcesoMenor procesoX = new ProcesoMenor();
        HiloGeneradorProceso hiloGP = new HiloGeneradorProceso(procesoX, interfaz);
        HiloAdministradorProcesos hiloAP = new HiloAdministradorProcesos(procesoX, interfaz);
        interfaz.setVisible(true);
        hiloGP.start();
        hiloAP.start();

    }
}

class Proceso {

    private int id;
    private int memoria;
    private int tiempo;

    public Proceso(int id, int tiempo, int memoria) {
        this.id = id;
        this.memoria = memoria;
        this.tiempo = tiempo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getId() {
        return id;
    }

    public int getMemoria() {
        return memoria;
    }

    public int getTiempo() {
        return tiempo;
    }

}

class ProcesoMenor {

    private ArrayList<Proceso> listaProcesos;

    public ProcesoMenor() {
        this.listaProcesos = new ArrayList<>();

    }

    public synchronized void agregarProceso(Proceso p) {
        listaProcesos.add(p);
        Collections.sort(listaProcesos, Comparator.comparingInt(Proceso::getTiempo));
    }

    public synchronized Proceso getProceso() {
        if (!listaProcesos.isEmpty()) {
            Proceso p = listaProcesos.get(0);
            listaProcesos.remove(0);
            return p;
        }
        return null;
    }

    public synchronized ArrayList<Proceso> getLista() {
        return new ArrayList<>(listaProcesos);
    }
}

class HiloGeneradorProceso extends Thread {

    private ProcesoMenor procesoMenor;
    private InterfazGrafica interfaz;

    public HiloGeneradorProceso(ProcesoMenor p, InterfazGrafica interfaz) {
        this.procesoMenor = p;
        this.interfaz = interfaz;
    }

    @Override
    public void run() {
        int id = 0;

        while (true) {
            int tiempo = (int) (Math.random() * 1000 + 1);
            int memoria = (int) (Math.random() * 525 + 2);
            Proceso proceso = new Proceso(id, tiempo, memoria);
            procesoMenor.agregarProceso(proceso);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("Proceso generado-ID: " + id + ", Tiempo: " + tiempo + ", Memoria: " + memoria);
            interfaz.actualizarListaProcesos(procesoMenor.getLista());
            interfaz.actualizarProcesoActual(proceso);

            id++;
        }
    }
}

class HiloAdministradorProcesos extends Thread {

    private ProcesoMenor pMenor;
    private InterfazGrafica interfaz;

    HiloAdministradorProcesos(ProcesoMenor p, InterfazGrafica i) {
        this.pMenor = p;
        this.interfaz = i;

    }

    public void procesar() {
        while (true) {
            Proceso proceso = pMenor.getProceso();
            if (proceso != null) {
                interfaz.mostrarProcesoEnEjecucion("Proceso " + proceso.getId() + " con tiempo de " + proceso.getTiempo() + " en ejecución");
                try {
                    Thread.sleep(proceso.getTiempo() * 10);
                } catch (Exception e) {
                }
                System.out.println("Proceso " + proceso.getId() + " completado.");

            }
        }

    }

    @Override
    public void run() {
        while (true) {
            procesar();
        }

    }
}
