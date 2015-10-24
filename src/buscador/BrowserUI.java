package buscador;

import Estadistica.VEstadistica;
import Paralelo.Palabra;
import Secuencial.BuscadorSecuencial;
import Secuencial.EstadisticaPalabra;
import Secuencial.Resultado;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.HyperlinkEvent;
import modelo.Archivo;
import modelo.PaginasWeb;
import org.hyperic.sigar.SigarException;

public class BrowserUI extends javax.swing.JFrame {

    public long tiempoSec;// tiempo total de la busqueda secuencial
    public long tiempoConc;// tiempo total de la busqueda concurrente

    boolean modo = false;// true es paralelo

    ArrayList<String> urlWebPages;
    Archivo archivo;
    Palabra tareaPalabra;
    ArrayList<Resultado> mergedResulta;
    ArrayList<PaginasWeb> sitiosWebConcurrente;
    public ArrayList<EstadisticaPalabra> tiemposPalabrasConcurrente = new ArrayList<>();

    //variables para secuencial
    BuscadorSecuencial buscador;
    ArrayList<PaginasWeb> sitiosWebSecuencial;
    public ArrayList<Resultado> resultadosSecuencial;
    public ArrayList<EstadisticaPalabra> tiemposPalabrasSecuencial = new ArrayList<>();

    /**
     * Creates new form BrowserUI
     */
    public BrowserUI() {
        initComponents();
        this.archivo = new Archivo();
        this.urlWebPages = new ArrayList<>();
        setUrlWebPages();
        initArrayPaginasWeb();
    }

    private void setUrlWebPages() {
        this.urlWebPages = archivo.leer();
    }

    public void ejecutarConcurrente(ArrayList<String> link, String palabra) {
        //System.out.println(link + palabra);
        Palabra myRecursiveTask = new Palabra(link, palabra);

        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        long startTime = System.currentTimeMillis();// empieza el tiempo de busqueda de los terminos
        this.mergedResulta = forkJoinPool.invoke(myRecursiveTask);
        long estimatedTime = System.currentTimeMillis() - startTime;// finaliza el tiempo de busqueda de los terminos        
        this.tiempoConc = estimatedTime;
        System.out.println("termino" + mergedResulta.size());
        addResultado(mergedResulta, true);
        addResultado(true);
        this.tiemposPalabrasConcurrente = this.calcularTiempoPalabra(palabra, mergedResulta);
    }

    public void ejecutarSecuencial(String terminos) throws IOException, SigarException {
        this.buscador = new BuscadorSecuencial();
        long startTime = System.currentTimeMillis();// empieza el tiempo de busqueda de los terminos
        this.resultadosSecuencial = buscador.searchManager(terminos);
        long estimatedTime = System.currentTimeMillis() - startTime;// finaliza el tiempo de busqueda de los terminos
        this.tiempoSec = estimatedTime;
        addResultado(this.resultadosSecuencial, false);
        addResultado(false);
        this.tiemposPalabrasSecuencial = this.calcularTiempoPalabra(terminos, this.resultadosSecuencial);
    }

    /**
     * Agrega un objeto de tipo resultado al objeto Paginas Web correcto
     *
     * @param resultado
     */
    public void addResultado(ArrayList<Resultado> resultado, boolean isConcurrente) {
        for (Resultado resultadoAux : resultado) {
            addResultadoAux(resultadoAux, isConcurrente);
        }

    }

    public void addResultadoAux(Resultado resultado, boolean isConcurrente) {
        ArrayList<PaginasWeb> paginaWeb;
        if (isConcurrente) {
            paginaWeb = sitiosWebConcurrente;
        } else {
            paginaWeb = sitiosWebSecuencial;
        }
        for (PaginasWeb pagina : paginaWeb) {
            if (pagina.getUrl().equals(resultado.getUrl())) {
                pagina.addItemListaResultados(resultado);
                pagina.setIncidencias(pagina.getIncidencias() + resultado.getCoincidencias());
            }
        }
    }

    public void addResultado(boolean modo) {
        String resultado = "";
        panelResultados.setText("");
        String url = "";
        String titulo = "";
        String extracto = "";
        boolean imprimir = false;
        ArrayList<PaginasWeb> paginas;
        if (modo == true) {
            paginas = this.sitiosWebConcurrente;
        } else {
            paginas = this.sitiosWebSecuencial;
        }
        for (PaginasWeb resultadoBusqueda : paginas) {
            for (Resultado resultadoPalabra : resultadoBusqueda.getListaResultados()) {
                if (resultadoPalabra.getCoincidencias() != 0) {
                    imprimir = true;
                    url = resultadoPalabra.getUrl();
                    titulo = resultadoPalabra.getTitulo();
                    extracto = resultadoPalabra.getTextoCoincidencia();
                }
            }
            String resul = "";
            if (imprimir) {
//                System.out.println("-------------------------");
//                System.out.println("Titulo: " + titulo);
//                System.out.println("Url: " + url);
//                System.out.println("Extracto: " + extracto);
//                System.out.println("-------------------------");

                //resultado = panelResultados.getText();
                titulo = "<h2>" + titulo + "<h2/><br>";
                url = "<a href='" + url + "'>" + url + "<a/><br>";
                extracto = "<p>" + extracto + "<p/><br><br>";
                resul = titulo + url + extracto;
                resultado = resultado + resul;
                System.out.println(resultado + resul);

            }
            panelResultados.setText(resultado);
        }

    }

    public final void initArrayPaginasWeb() {
        this.sitiosWebConcurrente = new ArrayList<>();
        this.sitiosWebSecuencial = new ArrayList<>();
        for (String url : urlWebPages) {
            PaginasWeb nPaginasWeb = new PaginasWeb(url, new ArrayList<Resultado>());
            this.sitiosWebConcurrente.add(nPaginasWeb);
            this.sitiosWebSecuencial.add(nPaginasWeb);
            //panelResultados.setText("<h1>hola<h1/>");
        }
    }

    /**
     * calcula el tiempo de la coincidencia de cada palabra del termino de la
     * busqueda es decir, el tiempo que dura la busqueda con una determinada
     * palabra
     *
     * @param terminoBusqueda el termino de busqueda
     */
    public ArrayList<EstadisticaPalabra> calcularTiempoPalabra(String terminoBusqueda, ArrayList<Resultado> arrayResultados) {
        //limpiar el texto de espacios y dividirlo por palabras
        String terminoBusquedaAux = "";
        ArrayList<EstadisticaPalabra> tiemposPalabras = new ArrayList<>();
        for (int x = 0; x < terminoBusqueda.length(); x++) {
            if (terminoBusqueda.charAt(x) != ' ') {
                terminoBusquedaAux += terminoBusqueda.charAt(x);
            }
        }
        terminoBusqueda = terminoBusquedaAux.replace("|", " ");
        String[] terminos = terminoBusqueda.split("\\s");//dividir el texto en palabras
        //
        for (int i = 0; i < terminos.length; i++) {
            EstadisticaPalabra palabra = new EstadisticaPalabra();
            palabra.setPalabra(terminos[i]);
            for (int j = 0; j < arrayResultados.size(); j++) {
                if (arrayResultados.get(j).getCoincidencias() > 0
                        && arrayResultados.get(j).getPalabra().equals(terminos[i])) {
                    palabra.setTiempo(palabra.getTiempo() + arrayResultados.get(j).getTiempo());                    
                }
            }
            tiemposPalabras.add(palabra);
        }
        for (int i = 0; i < tiemposPalabras.size(); i++) {
            System.out.println("------------------dsdsdfsd-------");
            System.out.println("Palabra: " + tiemposPalabras.get(i).getPalabra()
                    + "\nTiempo: " + tiemposPalabras.get(i).getTiempo() + " milisegungos");
        }
        return tiemposPalabras;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTerminos = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnModo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelResultados = new javax.swing.JTextPane();
        btnEstadisticas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscador");
        setPreferredSize(new java.awt.Dimension(1000, 725));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnModo.setText("Secuencial");
        btnModo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoActionPerformed(evt);
            }
        });

        jLabel1.setText("Modo:");

        panelResultados.setEditable(false);
        panelResultados.setContentType("text/html"); // NOI18N
        panelResultados.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                panelResultadosHyperlinkUpdate(evt);
            }
        });
        jScrollPane1.setViewportView(panelResultados);

        btnEstadisticas.setText("Estadisticas");
        btnEstadisticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadisticasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTerminos, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 391, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModo))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(437, 437, 437)
                .addComponent(btnEstadisticas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModo)
                    .addComponent(jLabel1)
                    .addComponent(txtTerminos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstadisticas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        System.out.println("Modo:" + modo + "(true:paralelo, false:secuencial)");
        String terminos = txtTerminos.getText();
        setUrlWebPages();
        //panelResultados.setText("<h1>hola<h1/>");
        if (modo == true) {
            ejecutarConcurrente(this.urlWebPages, terminos);
        } else {
            try {
                ejecutarSecuencial(terminos);
            } catch (IOException ex) {
                Logger.getLogger(BrowserUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SigarException ex) {
                Logger.getLogger(BrowserUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void panelResultadosHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_panelResultadosHyperlinkUpdate
        // TODO add your handling code here:
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(evt.getURL().toURI());
            } catch (URISyntaxException | IOException ex) {
            }
        }
    }//GEN-LAST:event_panelResultadosHyperlinkUpdate

    private void btnModoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModoActionPerformed
        if (modo == false) {
            modo = true;
            this.btnModo.setText("Concurrente");
        } else {
            modo = false;
            this.btnModo.setText("Secuencial");
        }
    }//GEN-LAST:event_btnModoActionPerformed

    private void btnEstadisticasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadisticasActionPerformed
        try {
            VEstadistica ventanaEstadisticas = new VEstadistica();
            ventanaEstadisticas.resultadosSecuencial = this.resultadosSecuencial;// resultados
            ventanaEstadisticas.mergedResulta = this.mergedResulta;//resultados
            ventanaEstadisticas.sitiosWebSecuencial = this.sitiosWebSecuencial;
            ventanaEstadisticas.sitiosWebConcurrente = this.sitiosWebConcurrente;
            ventanaEstadisticas.tiemposPalabrasSecuencial = this.tiemposPalabrasSecuencial;
            ventanaEstadisticas.tiemposPalabrasConcurrente = this.tiemposPalabrasConcurrente;
            ventanaEstadisticas.tiempoSec = this.tiempoSec;
            ventanaEstadisticas.tiempoConc = this.tiempoConc;

            ventanaEstadisticas.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(BrowserUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se mostró");
        }
    }//GEN-LAST:event_btnEstadisticasActionPerformed

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
            java.util.logging.Logger.getLogger(BrowserUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BrowserUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BrowserUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BrowserUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BrowserUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEstadisticas;
    private javax.swing.JButton btnModo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane panelResultados;
    private javax.swing.JTextField txtTerminos;
    // End of variables declaration//GEN-END:variables
}
