package GUI;

import algoritmoaestrela.Estado;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
 
public class Painel extends JPanel{
    
    //  Dados:
    
    private Estado estado;
    private ArrayList<Estado> caminho;
    private ArrayList<String> posObstaculos;
    private int xInicial;
    private int yInicial;
    private int xFinal;
    private int yFinal;
    private final int tipo;
    
    //  Posições:
    
    private int maxX;
    private int maxY;
    private int profAtual;
    private int xAtual;
    private int yAtual;
    private int grid;
    private int quadro;
    
    private Estado obj;
    
    private ArrayList<ArrayList<Estado>> niveis;
    
    public Painel(Estado estado){
        this.estado = estado;
        setPositions();
        tipo = 1;
        
        setPosicoes();
    }
    
    public Painel(ArrayList<Estado> caminho){
        this.caminho = caminho;
        tipo = 2;
        
        setPosicoes();
    }
    
    public Painel(Estado estado, ArrayList<String> posObstaculos, int grid, 
            int xInicial, int yInicial, int xFinal, int yFinal){
        this.estado = estado;
        this.posObstaculos = posObstaculos;
        this.grid = grid;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
        tipo = 3;
        
        setPosicoes();
    }
    
    private void setPosicoes(){
        maxX=0;
        maxY=0;
        profAtual=0;
        xAtual=30;
        yAtual=30;
    }
    
    
    
    /*  Função SetPositions:
     *  -Define posições para cada Estado Árvore
     *      --Profundidade do Estado
     *      --Posição Horizontal
     */
    
    private void setPositions(){
        niveis = new ArrayList();
        
        setProfundidade(estado);
        for(int i=niveis.size()-1; i>=0; i--){
            ArrayList<Estado> nivel = niveis.get(i);
            setLargura(nivel);
        }
    }
    
    /*  Função SetProfundidade:
     *  -Calcula Distância do Estado Inicial
     */
    
    private void setProfundidade(Estado e){
        Estado pai = e.getPai();
        int prof = 0;
        
        if(pai != null)
            prof = pai.getProfundidade()+1;
        
        if(niveis.size()!=prof+1)
            niveis.add(new ArrayList());
        
        niveis.get(prof).add(e);
        e.setProfundidade(prof);
        
        ArrayList<Estado> filhos = e.getFilhos();
        for(int i=0; i<filhos.size(); i++)
            setProfundidade(filhos.get(i));
    }
    
    /*  Função SetLargura:
     *  -Calcula Posição Horizontal dentro da Profundidade Atual
     */
    
    private void setLargura(ArrayList<Estado> nivel){
        for(int i=0; i<nivel.size(); i++){
            Estado e = nivel.get(i);                                            
            ArrayList<Estado> filhos = e.getFilhos();                           
            
            double x = 0;
            if(filhos.size() > 0){
                Estado pf = filhos.get(0);
                Estado uf = filhos.get(filhos.size()-1);
                
                x = ((pf.getLarg()+uf.getLarg())/2);
            }
            
            if(i==0)                                                       
                e.setLarg(x);
            else{
                Estado ant = nivel.get(i-1);
                e.setLarg(ant.getLarg()+x+1);
            }
            
        }
    }
    
    //Função que chama o desenho
    
    public void desenhar(){
        paint(getGraphics());                                                   
    }
    
    /*  Função Paint
     *  -Desenha Árvore + Legenda caso Frame seja do Tipo 1
     *  -Desenha Caminho caso Frame seja do Tipo 2
     */
   
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        switch(tipo){
            case 1:
                drawArvore(estado, g2d);
                drawLegenda(g2d);
            break;
            case 2:
                drawCaminho(g2d);
            break;
            case 3:
                setMinimum();
                drawBusca(estado, g2d);
                drawLegenda2(g2d);
                drawGrid(g2d);
            break;
        }
    }
    
    
    
    //  Função que Desenha Árvore
    
    private void drawArvore(Estado e, Graphics2D g2d){
        if(e == null){
            return;                                                             
        }
        g2d.setStroke(new BasicStroke(1f));
        g2d.setColor(Color.black);
            
        g2d.setFont(new Font("default",Font.PLAIN, 12));
        int x = 30+(int) (185*e.getLarg());
        int y = 30+50*e.getProfundidade();
        
        if(x>maxX){
            maxX = x;
        }
        if(y>maxY){
            maxY = y;
        }
        
        g2d.setColor(Color.orange);
        if(e.isFechado()){
            g2d.setColor(Color.red);                                            
        }
        if(e.isAtual()){
            g2d.setColor(Color.YELLOW);
                
            if(e.getProfundidade()>=profAtual){
                profAtual = e.getProfundidade();
                xAtual=x-300;
                if(xAtual<0){
                    xAtual=0;
                }
                yAtual=y-300;
                if(yAtual<0){
                    yAtual=0;
                }
            }
        }
        if(e.isObjetivo()){
            g2d.setColor(Color.GREEN);
        }
        
        g2d.fill3DRect(x-5, y-20, 150, 30, true);
        g2d.setColor(Color.black);
        g2d.draw3DRect(x-5, y-20, 150, 30, true);
        g2d.drawString(e.toString()+" - "+e.getCustos(),x,y);
        
        ArrayList<Estado> filhos = e.getFilhos();
        for(int i=0; i<filhos.size(); i++){
            int xx = 30+(int)(185*filhos.get(i).getLarg());
            g2d.setColor(Color.GRAY);
            int yy = 30+50*filhos.get(i).getProfundidade();
            g2d.drawLine(x+75,y+10,xx+75,yy-20);
            drawArvore(filhos.get(i), g2d);
            
            int mx = (x+xx+100)/2;
            int my = (y+yy-10)/2;
            my+=5;
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("default",Font.BOLD, 12));
            g2d.drawString("1",mx,my);
        }
    }
    
    //  Função que Desenha Legenda
    
    private void drawLegenda(Graphics2D g2d){
        g2d.setFont(new Font("default",Font.PLAIN, 14));
        
        g2d.setColor(Color.red);
        g2d.fill3DRect(20, maxY+30, 30, 30, true);
        g2d.setColor(Color.orange);
        g2d.fill3DRect(170, maxY+30, 30, 30, true);
        g2d.setColor(Color.YELLOW);
        g2d.fill3DRect(320, maxY+30, 30, 30, true);
        g2d.setColor(Color.GREEN);
        g2d.fill3DRect(470, maxY+30, 30, 30, true);
        g2d.setColor(Color.black);
        g2d.draw3DRect(20, maxY+30, 30, 30, true);
        g2d.draw3DRect(170, maxY+30, 30, 30, true);
        g2d.draw3DRect(320, maxY+30, 30, 30, true);
        g2d.draw3DRect(470, maxY+30, 30, 30, true);
        g2d.drawString("Visitados",60,maxY+50);
        g2d.drawString("Abertos",210,maxY+50);
        g2d.drawString("Atual",360,maxY+50);
        g2d.drawString("Objetivo",510,maxY+50);
    }
    
    private void drawLegenda2(Graphics2D g2d){
        g2d.setFont(new Font("default",Font.PLAIN, 14));
        
        g2d.setColor(Color.green);
        g2d.fill3DRect(20, maxY+30, 30, 30, true);
        g2d.setColor(Color.red);
        g2d.fill3DRect(170, maxY+30, 30, 30, true);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill3DRect(320, maxY+30, 30, 30, true);
        g2d.setColor(Color.orange);
        g2d.fill3DRect(470, maxY+30, 30, 30, true);
        
        
        g2d.setColor(Color.yellow);
        g2d.fill3DRect(20, maxY+65, 30, 30, true);
        g2d.setColor(Color.gray);
        g2d.fill3DRect(170, maxY+65, 30, 30, true);
        
        g2d.setColor(Color.black);
        
        g2d.draw3DRect(20, maxY+30, 30, 30, true);
        g2d.draw3DRect(170, maxY+30, 30, 30, true);
        g2d.draw3DRect(320, maxY+30, 30, 30, true);
        g2d.draw3DRect(470, maxY+30, 30, 30, true);
        g2d.draw3DRect(20, maxY+65, 30, 30, true);
        g2d.draw3DRect(170, maxY+65, 30, 30, true);
        
        g2d.drawString("Inicial",60,maxY+50);
        g2d.drawString("Final",210,maxY+50);
        g2d.drawString("Aberto",360,maxY+50);
        g2d.drawString("Atual",510,maxY+50);
        g2d.drawString("Caminho",60,maxY+85);
        g2d.drawString("Fechado",210,maxY+85);
    }
    
    //  Função que Desenha Caminho
    
    private void drawCaminho(Graphics2D g2d){
        int n = 0;
        for(int i=caminho.size()-1; i>=0; i--){
            g2d.setStroke(new BasicStroke(1f));
            g2d.setColor(Color.black);

            g2d.setFont(new Font("default",Font.PLAIN, 12));
            int x = 160;
            int y = 30+50*n;

            if(x>maxX){
                maxX = x;
            }
            if(y>maxY){
                maxY = y;
            }
           
            g2d.setColor(Color.YELLOW);

            g2d.fill3DRect(x-5, y-20, 115, 30, true);
            g2d.setColor(Color.black);
            g2d.draw3DRect(x-5, y-20, 115, 30, true);
            g2d.drawString(caminho.get(i).toString(),x,y);

            if(i>0){
                int xx = 160;
                int yy = 30+50*(n+1);
                g2d.drawLine(x+50,y+10,xx+50,yy-20);
                
                int mx = (x+xx+100)/2;
                int my = (y+yy-10)/2;
                my+=5;
                g2d.setFont(new Font("default",Font.BOLD, 12));
                g2d.drawString(caminho.get(i-1).getCustos(),mx+5,my);
            }
            n++;
        }
        
        obj = caminho.get(caminho.size()-1);
        String c = "Custo Total = "+obj.getCusto();
        
        g2d.setFont(new Font("default",Font.PLAIN, 20));
        
        g2d.setColor(Color.black);
        g2d.drawString(c,140,maxY+50);
    }
    
    //  Função que Determina tamanho mínimo para Grid e tamanho quadrado
    
    private void setMinimum(){
        maxX = 500;
        maxY = 500;
        
        quadro = 500/grid;
        if(quadro<25){
            quadro = 25;
            maxX = 20+25*grid;
            maxY = 20+25*grid;
        }
    }
    
    //  Função que Desenha Busca no Grid
    
    private void drawBusca(Estado e, Graphics2D g2d){
        if(e == null){
            return;                                                             
        }
        g2d.setStroke(new BasicStroke(1f));
        
        int font = 8;
        int c = quadro-25;
        while(c>5){
            font++;
            c-=5;
        }
            
        g2d.setFont(new Font("default",Font.PLAIN, font));
        int x = 10+e.getPosX()*quadro;
        int y = 10+e.getPosY()*quadro;
        
        if(e.isFechado()){
            g2d.setColor(Color.GRAY);
        }else{
            g2d.setColor(Color.LIGHT_GRAY);
        }
        if(e.isCaminho()){
            g2d.setColor(Color.YELLOW);
        }
        
        if(e.isAtual()){
            g2d.setColor(Color.ORANGE);
            
            if(e.getProfundidade()>=profAtual){
                profAtual = e.getProfundidade();
                xAtual=x-250;
                if(xAtual<0){
                    xAtual=0;
                }
                yAtual=y-250;
                if(yAtual<0){
                    yAtual=0;
                }
            }
        }
        if(e.isObjetivo()){
            obj = e;
        }
        
        g2d.fill3DRect(x, y, quadro, quadro, true);
        
        if(!e.isFechado() && e!=estado && (!e.isCaminho())){        
            g2d.setColor(Color.black);
            double xx = (x+2);
            double yy = (y+font*1.2);
            g2d.drawString("G="+e.getPercorrido(),(int) xx,(int)yy);
            g2d.drawString("H="+e.getDistancia(),(int) xx,(int)yy+font);
            g2d.drawString("T="+e.getCusto(),(int) xx,(int)yy+font*2);
        }
        ArrayList<Estado> filhos = e.getFilhos();
        for(int i=0; i<filhos.size(); i++){
            drawBusca(filhos.get(i), g2d);
        }
        
    }
    
    //  Função que Desenha o Grid
    
    private void drawGrid(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(1f));
        
        g2d.setColor(Color.green);
        g2d.fill3DRect(10+xInicial*quadro, 10+yInicial*quadro, quadro, quadro, true);
        
        int font = 8;
        int c = quadro-25;
        while(c>5){
            font++;
            c-=5;
        }
        
        g2d.setFont(new Font("default",Font.PLAIN, font));
        g2d.setColor(Color.black);
        double xx = (12+xInicial*quadro);
        double yy = (10+yInicial*quadro+font*1.2);
        g2d.drawString("G="+estado.getPercorrido(),(int) xx,(int)yy);
        g2d.drawString("H="+estado.getDistancia(),(int) xx,(int)yy+font);
        g2d.drawString("T="+estado.getCusto(),(int) xx,(int)yy+font*2);
        
        g2d.setColor(Color.red);
        g2d.fill3DRect(10+xFinal*quadro, 10+yFinal*quadro, quadro, quadro, true);
        
        g2d.setColor(Color.black);
        
        xx = (12+xFinal*quadro);
        yy = (10+yFinal*quadro+font*1.2);
        String gt = "?";
        if(obj != null){
            gt = String.valueOf(obj.getCusto());
        }
        g2d.drawString("G="+gt,(int) xx,(int)yy);
        g2d.drawString("H=0.0",(int) xx,(int)yy+font);
        g2d.drawString("T="+gt,(int) xx,(int)yy+font*2);
        
        g2d.setColor(Color.black);
        for(int i=0; i<posObstaculos.size(); i++){
            String[] ss = posObstaculos.get(i).split(",");
            int x = Integer.valueOf(ss[0]);
            int y = Integer.valueOf(ss[1]);
            
            g2d.fill3DRect(10+x*quadro, 10+y*quadro, quadro, quadro, true);
        }
        
        for(int i=0; i<grid; i++){
            for(int j=0; j<grid; j++){
                g2d.draw3DRect(10+i*quadro, 10+j*quadro, quadro, quadro, true);
            }
        }
    }
    
    /*Getters & Setters*/

    public int getMaxX() {
        if(tipo == 1){
            return maxX+150;
        }
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY+100;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getxAtual() {
        return xAtual;
    }

    public void setxAtual(int xAtual) {
        this.xAtual = xAtual;
    }

    public int getyAtual() {
        return yAtual;
    }

    public void setyAtual(int yAtual) {
        this.yAtual = yAtual;
    }

    public Estado getObj() {
        return obj;
    }

    public void setObj(Estado obj) {
        this.obj = obj;
    }
    
    
}
