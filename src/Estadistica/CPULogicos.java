package Estadistica;

import Secuencial.Resultado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.CPUInfo;
import modelo.PaginasWeb;
import org.hyperic.sigar.CpuPerc;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class CPULogicos extends JFrame {

    public CPULogicos() {
        super("Estadistica de los CPU Logicos");

        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel() {
        String chartTitle = "CPU Logicos";
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        return new ChartPanel(chart);
    }

    public XYDataset createDataset() {
        /*ArrayList<PaginasWeb> arreglo;
        System.out.println("Tamaño Arreglo: " + arreglo.size());
        for (PaginasWeb pagina: arreglo){
            
            for (Resultado resultado: pagina.getListaResultados()){
                CPUInfo cpuInfo = resultado.infoCPUs;
                ArrayList<CpuPerc[]> usoProcesadores = cpuInfo.getUsoProcesadores();
                    
                for (CpuPerc[] uso: usoProcesadores){
                    
                    for (int i = 0; i < uso.length; i++){
                        System.out.println("Consumo de CPU " + i + "\t" + CpuPerc.format(uso[i].getUser()));
                    }
                }
            }
        }*/
                XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("CPU 1");
        XYSeries series2 = new XYSeries("CPU 2");
        //XYSeries series3 = new XYSeries("Object 3");
        series1.add(1, 13);
        series1.add(2, 30);
        series1.add(3, 33);
        series1.add(4, 59);
        series1.add(5, 80);
/*
        series1.add((int)1, 90);
        series1.add((int)2, 30);
        series1.add((int)3, 25);
        series1.add((int)4, 56);
        series1.add((int)5, 5);
*/
        series2.add((int)1, 10);
        series2.add((int)2, 24);
        series2.add((int)3, 12);
        series2.add((int)4, 28);
        series2.add((int)5, 80);
        
        /*
        series3.add(1.2, 4.0);
        series3.add(2.5, 4.4);
        series3.add(3.8, 4.2);
        series3.add(4.3, 3.8);
        series3.add(4.5, 4.0);
        */
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        //dataset.addSeries(series3);

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CPULogicos().setVisible(true);
            }
        });
    }
}
