package algoritmoaestrela;

import java.util.ArrayList;

public class Estado {
    private int posX;
    private int posY;
    private int percorrido;
    private double distancia;
    
    private double custo;
    
    // Estado Pai, para obter Caminho Solução:
    private Estado pai;
    
    //  Variáveis para ajuste parte Gráfica:
    
    private ArrayList<Estado> filhos;
    
    private int profundidade;
    private double larg;
    
    private boolean fechado;
    private boolean atual;
    private boolean objetivo;
    
    private boolean caminho;
    
    //  Construtor Estado Inicial

    public Estado(int posX, int posY, int percorrido, double distancia) {
        this.posX = posX;
        this.posY = posY;
        this.percorrido = percorrido;
        double dist = distancia*100;
        this.distancia = Math.floor(distancia * 100) / 100;
        
        custo = Math.floor((percorrido+this.distancia) * 100) / 100;
        pai = null;
                
        filhos = new ArrayList();
        
        fechado = false;
        atual = false;
        objetivo = false;
        caminho = false;
    }
    
    //  Construtor Estado com Pai

    public Estado(Estado pai, int posX, int posY, int percorrido, double distancia) {
        this.pai = pai;
        this.posX = posX;
        this.posY = posY;
        this.percorrido = percorrido;
        this.distancia = Math.floor(distancia * 100) / 100;
        
        custo = Math.floor((percorrido+this.distancia) * 100) / 100;
        
        filhos = new ArrayList();
        
        fechado = false;
        atual = false;
        objetivo = false;
        caminho = false;
    }
    
    @Override
    public String toString(){
        return "("+posX+" ,"+posY+")";
    }
    
    public String getCustos(){
        return custo+"="+percorrido+"+"+distancia;
    }
    
    //  Getter e Setters da Classe:

    public Estado getPai() {
        return pai;
    }

    public void setPai(Estado pai) {
        this.pai = pai;
    }

    public ArrayList<Estado> getFilhos() {
        return filhos;
    }

    public void setFilhos(ArrayList<Estado> filhos) {
        this.filhos = filhos;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public double getLarg() {
        return larg;
    }

    public void setLarg(double larg) {
        this.larg = larg;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPercorrido() {
        return percorrido;
    }

    public void setPercorrido(int percorrido) {
        this.percorrido = percorrido;
    }
    
    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public boolean isAtual() {
        return atual;
    }

    public void setAtual(boolean atual) {
        this.atual = atual;
    }

    public boolean isObjetivo() {
        return objetivo;
    }

    public void setObjetivo(boolean objetivo) {
        this.objetivo = objetivo;
    }

    public boolean isCaminho() {
        return caminho;
    }

    public void setCaminho(boolean caminho) {
        this.caminho = caminho;
    }
    
    
}
