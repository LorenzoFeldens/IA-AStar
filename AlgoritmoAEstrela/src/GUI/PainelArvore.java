package GUI;

import algoritmoaestrela.Algoritmo;
import algoritmoaestrela.Estado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PainelArvore extends javax.swing.JFrame {
    private Painel jPanel1;
    private Algoritmo algoritmo;
    private ArrayList<String> posObstaculos;
    private int grid;
    private int xInicial;
    private int yInicial;
    private int xFinal;
    private int yFinal;
    
    private int tipo;
    
    //  Construtor para Exibição Árvore
    
    public PainelArvore(Estado estado, Algoritmo algoritmo) {
        this.algoritmo = algoritmo;
        this.tipo = 1;
        jPanel1 = new Painel(estado);
        this.setTitle("Árvore Solução");
        initComponents2();
    }
    
    //  Construtor para Exibição Caminho
    
    public PainelArvore(ArrayList<Estado> caminho) {
        jPanel1 = new Painel(caminho);
        this.setTitle("Caminho Solução");
        initComponents1();
    }
    
    //  Construtor para Exibição Resultado Busca
    
    public PainelArvore(Estado estado, ArrayList<String> posObstaculos, int grid, 
            int xInicial, int yInicial, int xFinal, int yFinal, Algoritmo algoritmo) {
        this.algoritmo = algoritmo;
        this.posObstaculos = posObstaculos;
        this.grid = grid;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
        this.tipo = 2;
        jPanel1 = new Painel(estado, posObstaculos, grid, xInicial, yInicial, xFinal, yFinal);
        this.setTitle("Resultado Busca");
        initComponents2();
        algoritmo.reset();
    }
    
    /*  Função Atualiza:
     *  -Redesenha jPane1 (que contém Árvore) a cada Passo
     */
    
    public void atualiza(Estado estado){
        if(tipo == 1)
            jPanel1 = new Painel(estado);
        else
            jPanel1 = new Painel(estado, posObstaculos, grid, xInicial, yInicial, xFinal, yFinal);
        
        jPanel1.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(jPanel1);
        jPanel1.desenhar();
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX(), jPanel1.getMaxY()));
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX(), jPanel1.getMaxY()));
        
        jScrollPane1.getViewport().setViewPosition(new Point(jPanel1.getxAtual(),jPanel1.getyAtual()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setToolTipText("");
        jScrollPane1.setAutoscrolls(true);

        jButton1.setText("Próximo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        algoritmo.reset();
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(algoritmo.passos()){
            jButton1.setEnabled(false);
            
            Estado obj = algoritmo.getObj();
            if(obj!=null)
                JOptionPane.showMessageDialog(null, "Objetivo Encontrado!\n"
                        + "Custo Total = "+obj.getCusto());
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void initComponents1(){
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        jPanel1.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(jPanel1);

        jScrollPane1.setToolTipText("");
        jScrollPane1.setAutoscrolls(true);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        
        jPanel1.desenhar();
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX()+150, jPanel1.getMaxY()+70));
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX()+150, jPanel1.getMaxY()+70));
    }
    
    private void initComponents2() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        jPanel1.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(jPanel1);

        jScrollPane1.setToolTipText("");
        jScrollPane1.setAutoscrolls(true);

        jButton1.setText("Próximo");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });

        jButton2.setText("Reset");
        jButton2.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton2ActionPerformed(evt);
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        
        jPanel1.desenhar();
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX()+150, jPanel1.getMaxY()+70));
        jPanel1.setPreferredSize(new Dimension(jPanel1.getMaxX()+150, jPanel1.getMaxY()+70));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
