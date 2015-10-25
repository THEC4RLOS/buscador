/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estadistica;

import Paralelo.Palabra;
import Secuencial.BuscadorSecuencial;
import Secuencial.EstadisticaPalabra;
import Secuencial.Resultado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import modelo.Archivo;
import modelo.CPU;
import modelo.PaginasWeb;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author manfred
 */
public class VEstadistica extends javax.swing.JFrame {

    public long tiempoSec;// tiempo total de la busqueda secuencial
    public long tiempoConc;// tiempo total de la busqueda concurrente
    public ArrayList<String> paginaWeb;

    ///variables para estadisticas de secuencial
    public ArrayList<Resultado> resultadosSecuencial;
    public ArrayList<EstadisticaPalabra> tiemposPalabrasSecuencial = new ArrayList<>();
    public ArrayList<PaginasWeb> sitiosWebSecuencial;
    public ArrayList<CPU> estadisticaCPULogicoSecuencial;

    /// variables para estadisticas de concurrente
    public ArrayList<Resultado> mergedResulta;
    public ArrayList<PaginasWeb> sitiosWebConcurrente;
    public ArrayList<EstadisticaPalabra> tiemposPalabrasConcurrente = new ArrayList<>();
    public ArrayList<CPU> estadisticaCPULogicoConcurrente;

    ///
    /**
     * Creates new form VEstadistica
     */
    public VEstadistica() throws IOException {

        initComponents();
        tiempoSTF.setText(Long.toString(tiempoSec));
        tiempoCTF.setText(Long.toString(tiempoConc));
        txtPaginasWeb.setVisible(false);
    }

    public void graficar(boolean isConcurrente, ArrayList<PaginasWeb> sitiosWeb, ArrayList<EstadisticaPalabra> tiemposPalabras, ArrayList<Resultado> resultados) {

        DefaultCategoryDataset barChartDatos = new DefaultCategoryDataset();// grafico de secuencial

        for (PaginasWeb paginas : sitiosWeb) {
            barChartDatos.setValue(paginas.getIncidencias(), "Sitios", paginas.getListaResultados().get(0).getTitulo());
        }

        //generar los datos de las tablas
        DefaultTableModel modeloTablaIncidencias;
        if (isConcurrente == false) {
            modeloTablaIncidencias = (DefaultTableModel) tablaIncidenciaSec.getModel();
        } else {
            modeloTablaIncidencias = (DefaultTableModel) tablaIncidenciaSConc.getModel();
        }
        Object[] fila = new Object[modeloTablaIncidencias.getColumnCount()];
        int cont = 0;
        for (EstadisticaPalabra palabra : tiemposPalabras) {
            fila[0] = palabra.getPalabra();
            fila[1] = palabra.getTiempo();
            modeloTablaIncidencias.addRow(fila);
            cont++;
            System.out.println(palabra.getPalabra());
        }
        System.out.println(cont);
//         Grafico
//        titulo-titulo arriba
        JFreeChart grafico = ChartFactory.createBarChart3D("Incidencias por sitio", "Sitios", "Numero de Incidencias", barChartDatos, PlotOrientation.HORIZONTAL, false, true, false);
        //

        CategoryPlot barChartCP = grafico.getCategoryPlot();
        barChartCP.setRangeGridlinePaint(Color.cyan);

        ChartPanel barPanel = new ChartPanel(grafico);
        if (isConcurrente == true) {
            lienzoConc.removeAll();
            lienzoConc.add(barPanel, BorderLayout.CENTER);
            lienzoConc.validate();
        }

        if (isConcurrente == false) {
            lienzoSec.removeAll();
            lienzoSec.add(barPanel, BorderLayout.CENTER);
            lienzoSec.validate();
        }
    }

    public XYDataset createDataset(ArrayList<CPU> estadisticaModo) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        ArrayList<String> link = new ArrayList<>();

        for (CPU estadisticaPorLink : estadisticaModo) {
            System.out.println("link:" + estadisticaPorLink.strLink);
            link.add(estadisticaPorLink.strLink);
            //int cpu : estadisticaPorLink.porcentajeCpus
            for (int i = 0; i < estadisticaPorLink.porcentajeCpus.size(); i++) {
                XYSeries series = new XYSeries("CPU " + i);
                System.out.println("CPU " + i + " Numero link: " + estadisticaPorLink.intLink + " porcentaje: " + estadisticaPorLink.porcentajeCpus.get(i));
                series.add(estadisticaPorLink.intLink, estadisticaPorLink.porcentajeCpus.get(i));
                if (estadisticaModo.size()<=1)
                    series.add(2, estadisticaPorLink.porcentajeCpus.get(i));                
                dataset.addSeries(series);
            }
        }
        if (paginaWeb == null) {
            paginaWeb = link;
        } else if (link.size() > paginaWeb.size()) {
            paginaWeb = link;
        }
        return dataset;
    }

    private JPanel createChartPanel(ArrayList<CPU> modo) {
        String chartTitle = "CPU Logicos";
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        XYDataset dataset = createDataset(modo);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        return new ChartPanel(chart);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IncidenciasB = new javax.swing.JButton();
        CPUB = new javax.swing.JButton();
        lienzoSec = new javax.swing.JPanel();
        pTablaSec = new javax.swing.JScrollPane();
        tablaIncidenciaSec = new javax.swing.JTable();
        pTablaSec1 = new javax.swing.JScrollPane();
        tablaIncidenciaSConc = new javax.swing.JTable();
        lienzoConc = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        tiempoCTF = new javax.swing.JTextField();
        tiempoSTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPaginasWeb = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        IncidenciasB.setText("Incidencias");
        IncidenciasB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IncidenciasBActionPerformed(evt);
            }
        });

        CPUB.setText("CPU");
        CPUB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CPUBActionPerformed(evt);
            }
        });

        lienzoSec.setBackground(new java.awt.Color(204, 204, 204));
        lienzoSec.setLayout(new java.awt.BorderLayout());

        tablaIncidenciaSec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Incidencia", "Tiempo(ms)"
            }
        ));
        pTablaSec.setViewportView(tablaIncidenciaSec);

        tablaIncidenciaSConc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Incidencia", "Tiempo(ms)"
            }
        ));
        pTablaSec1.setViewportView(tablaIncidenciaSConc);

        lienzoConc.setBackground(new java.awt.Color(204, 204, 204));
        lienzoConc.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Secuencial");

        jLabel2.setText("Concurrente");

        jLabel3.setText("Tiempos Total de Ejecución ");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel4.setText("Concurrente:");

        jLabel5.setText("Secuencial:");

        tiempoCTF.setEditable(false);
        tiempoCTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiempoCTFActionPerformed(evt);
            }
        });

        tiempoSTF.setEditable(false);

        jLabel6.setText("ms");

        jLabel7.setText("ms");

        txtPaginasWeb.setColumns(20);
        txtPaginasWeb.setRows(5);
        jScrollPane1.setViewportView(txtPaginasWeb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lienzoSec, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lienzoConc, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tiempoSTF)
                                            .addComponent(tiempoCTF, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CPUB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(IncidenciasB, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(216, 216, 216))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(247, 247, 247))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addComponent(pTablaSec, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pTablaSec1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(192, 192, 192))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(IncidenciasB)
                        .addGap(18, 18, 18)
                        .addComponent(CPUB)
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tiempoCTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tiempoSTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pTablaSec, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(pTablaSec1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(20, 20, 20)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lienzoSec, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(lienzoConc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IncidenciasBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IncidenciasBActionPerformed
        graficar(false, sitiosWebSecuencial, tiemposPalabrasSecuencial, resultadosSecuencial);
        graficar(true, sitiosWebConcurrente, tiemposPalabrasConcurrente, mergedResulta);

    }//GEN-LAST:event_IncidenciasBActionPerformed

    private void tiempoCTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiempoCTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoCTFActionPerformed

    private void CPUBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CPUBActionPerformed
        // TODO add your handling code here:
        if (estadisticaCPULogicoConcurrente != null) {
            JPanel panel = createChartPanel(estadisticaCPULogicoConcurrente);
            lienzoConc.removeAll();
            lienzoConc.add(panel, BorderLayout.CENTER);
            lienzoConc.validate();
        }
        if (estadisticaCPULogicoSecuencial != null) {
            JPanel panel = createChartPanel(estadisticaCPULogicoSecuencial);
            lienzoSec.removeAll();
            lienzoSec.add(panel, BorderLayout.CENTER);
            lienzoSec.validate();
        }

        for (int i = 0; i < paginaWeb.size(); i++) {
            txtPaginasWeb.setText((i+1) + "   " + paginaWeb.get(i));
        }
    }//GEN-LAST:event_CPUBActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VEstadistica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VEstadistica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VEstadistica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VEstadistica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VEstadistica().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(VEstadistica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CPUB;
    private javax.swing.JButton IncidenciasB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel lienzoConc;
    private javax.swing.JPanel lienzoSec;
    private javax.swing.JScrollPane pTablaSec;
    private javax.swing.JScrollPane pTablaSec1;
    private javax.swing.JTable tablaIncidenciaSConc;
    private javax.swing.JTable tablaIncidenciaSec;
    private javax.swing.JTextField tiempoCTF;
    private javax.swing.JTextField tiempoSTF;
    private javax.swing.JTextArea txtPaginasWeb;
    // End of variables declaration//GEN-END:variables
}
