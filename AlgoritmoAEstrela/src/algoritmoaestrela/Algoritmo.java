package algoritmoaestrela;

import GUI.PainelArvore;
import java.util.ArrayList;

public class Algoritmo {
    
    //  Parâmetros Problema:
    
    private final int grid;
    private final int obstaculos;
    private final int xInicial;
    private final int yInicial;
    private final int xFinal;
    private final int yFinal;
    
    //  Estados:
    
    private Estado inicial;
    private Estado atual;
    private ArrayList<Estado> abertos;
    private ArrayList<Estado> fechados;
    private ArrayList<String> posObstaculos;
    
    private ArrayList<Estado> caminho;
    
    private Estado obj;
    
    //  Frames Resultados:
    
    private PainelArvore painelArvore;
    
    /*  Construtor Classe:
     *  - Recebe parâmetros da GUI
     */

    public Algoritmo(int grid, int obstaculos, int xInicial, int yInicial, int xFinal, int yFinal) {
        this.grid = grid;
        this.obstaculos = obstaculos;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }
    
    /*  Função Executa:
     *  - Cria estado inicial
     *  - Inicializa Lista Abertos
     *  - Gera Movimentos e Estados Possíveis
     *  - Executa Passo até obter o objetivo ou rejeição do problema
     */
    
    public boolean executa() {
        abertos = new ArrayList();
        fechados = new ArrayList();
        
        geraObstaculos();
        
        reset();
        
        while(!objetivo())
            if(!passo())
                return false;
        
        return true;
    }
    
    /*  Função GeraObstáculos:
     *  - Gera localização para todos os Obstáculos
     */
    
    private void geraObstaculos(){
        posObstaculos = new ArrayList();
        
        String ini = xInicial+","+yInicial;
        String fim = xFinal+","+yFinal;
        
        int nObst = grid*grid*obstaculos/100;
        System.out.println(nObst+" Obstáculos gerados");
        
        if(nObst>grid*grid-2){
            nObst=grid*grid-2;
        }
        
        for(int i=0; i<nObst; i++){
            int x = (int)(Math.random() * grid);
            int y = (int)(Math.random() * grid);
            
            String xy = x+","+y;
            
            if((!posObstaculos.contains(xy) && (!xy.equalsIgnoreCase(ini)) 
                    && (!xy.equalsIgnoreCase(fim))))
                posObstaculos.add(xy);
            else
                i--;
        }
        
        for(int i=0; i<posObstaculos.size(); i++){
            System.out.println("OBS"+i+"="+posObstaculos.get(i));
        }
    }
    
    /*  Função Reset:
     *  -Retoma estado inicial do problema
     *  -Limpa lista Abertos
     */
    
    public void reset(){
        inicial = new Estado(xInicial, yInicial, 0, estimaCusto(xInicial, xFinal));
        
        abertos.removeAll(abertos);
        fechados.removeAll(fechados);
        
        atual = null;
        
        if(painelArvore != null && painelArvore.isVisible()){
            painelArvore.atualiza(inicial);
        }
    }
    
    /*  Função EstimaCusto
     *  -Retorna Distância entre Ponto do Estado e Objetivo
     */
    
    private double estimaCusto(int x, int y){
        return Math.sqrt(Math.pow(x-xFinal, 2)+Math.pow(y-yFinal, 2));
    }
    
    /*  Função Objetivo:
     *  -Verifica se Estado Atual é o Estado Objetivo
     */
    
    private boolean objetivo(){
        int x = -1;
        int y = -1;
        if(atual != null){
            x = atual.getPosX();
            y = atual.getPosY();
        }
        
        if(x == xFinal && y == yFinal){
            imprimeObj();
            atual.setObjetivo(true);
            obj = atual;
            return true;
        }
        return false;
    }
    
    /*  Função ImprimeObjetivo
     *  -Imprime Caminho Estado Inicial até Estado Obejtivo
     */
    
    private void imprimeObj(){
        String ret = "";
        caminho = new ArrayList();
        
        Estado est = atual;
        while(est != null){
            ret="\n    "+est.toString()+ret;
            
            caminho.add(est);
            est = est.getPai();
        }
        System.out.println("\nCaminho até Objetivo:"+ret);
    }
    
    /*  Função Passo:
     *  -Abre novos Estados
     *  -Define Estado Atual como Fechado
     *  -Escolhe novo Estado Atual
     *      --Caso não existam opções retorna falha
     */
    
    private boolean passo(){
        if(atual == null){
            atual = inicial;
            atual.setAtual(true);
            
        }else{
            atual.setFechado(true);
            atual.setAtual(false);
            
            if(abertos.size()>0){
                fechados.add(atual);
                atual = abertos.get(0);
                atual.setAtual(true);
                abertos.remove(0);
            }else{
                atual = null;
            }
        }
        
        if(atual == null){
            return false;
        }else{
            abreEstados();
            return true;
        }
    }
    
    /*  Função AbreEstados:
     *  -Para cada movimento Possível
     *      --Verifica se aplicado ao Estado Atual, gera um Estado Possível
     *      --E se este Estado já não foi acessado no Caminho
     *      --Insere Estados Possíveis na Lista de Abertos
     */
    
    private void abreEstados(){
        ArrayList<Estado> novos = new ArrayList();
        
        int x = atual.getPosX();
        int y = atual.getPosY();
        
        Estado novo = geraNovo(x,y+1);
        if(novo!=null)
            novos.add(novo);
        
        novo = geraNovo(x,y-1);
        if(novo!=null)
            novos.add(novo);
        
        novo = geraNovo(x+1,y);
        if(novo!=null)
            novos.add(novo);
        
        novo = geraNovo(x-1,y);
        if(novo!=null)
            novos.add(novo);
        
        imprimeGerados(novos);
        //atual.getFilhos().addAll(novos);
        insereNovos(novos);
    }
    
    /*  Função GeraNovo:
     *  -Gera novo Estado
     */
    
    private Estado geraNovo(int x, int y){
        Estado novo = verificaPossivel(x,y);
        if(novo != null && verificaNovo(novo)){
            atual.getFilhos().add(novo);
            return novo;
        }
        return null;
    }
    
    /*  Função VerificaPossivel:
     *  -Verifica se Estado é Válido
     */
    
    private Estado verificaPossivel(int x, int y){
        if(posObstaculos.contains(x+","+y))
            return null;
        
        if(x<0 || x>=grid || y<0 || y>=grid)
            return null;
        
        return new Estado(atual, x, y, atual.getPercorrido()+1, estimaCusto(x, y));
    }
    
    /*  Função VerificaNovo
     *  -Verifica se Estado já foi percorrido neste caminho
     *  -(Estado Inicial até Novo Estado)
     */
    
    private boolean verificaNovo(Estado novo){
        ArrayList<String> cam = new ArrayList();
        Estado e = novo;
        
        while(e.getPai()!=null){
            e=e.getPai();
            cam.add(e.toString());
        }
        
        if(cam.contains(novo.toString())){
            return false;
        }
        
        int nx = novo.getPosX();
        int ny = novo.getPosY();
        double nc = novo.getCusto();
        
        for(int i=0; i<fechados.size(); i++){
            int x = fechados.get(i).getPosX();
            int y = fechados.get(i).getPosY();
            double c = fechados.get(i).getCusto();
            
            
            if(nx == x && ny == y){
                if(nc>=c){
                    return false;
                }else{
                    fechados.remove(i);
                    return true;
                }
            }
        }
        
        for(int i=0; i<abertos.size(); i++){
            int x = abertos.get(i).getPosX();
            int y = abertos.get(i).getPosY();
            double c = abertos.get(i).getCusto();
            
            
            if(nx == x && ny == y){
                if(nc>=c){
                    return false;
                }else{
                    abertos.remove(i);
                    return true;
                }
            }
        }
        
        return true;
    }
    
    /*  Função InsereNovos e AddNovo:
     *  -Insere Estados com menor Custo na frente da Lista de Abertos  
     */
    
    private void insereNovos(ArrayList<Estado> novos){
        for(int i=0; i<novos.size(); i++)
            addNovo(novos.get(i));
    }
    
    private void addNovo(Estado novo){
        double custo = novo.getCusto();
        
        for(int i=0; i<abertos.size(); i++)
            if(abertos.get(i).getCusto()>custo){
                abertos.add(i, novo);
                return;
            }
        
        abertos.add(novo);
    }
    
    /*  Função ImprimeGerados
     *  -Imprime Estados Abertos pelo Estado Atual
     */
    
    private void imprimeGerados(ArrayList<Estado> novos){
        String print = "Novos estados gerados: [";
        for(int i=0; i<novos.size(); i++){
            Estado e = novos.get(i);
            print+="("+e.toString()+" - "+e.getCustos()+")";
        }
        print+="]";
        System.out.println(print);
    }
    
    //Funções Botões GUI:
    
    public void mostrarArvore(){
        if(painelArvore != null){
            painelArvore.dispose();
        }
        reset();
        painelArvore = new PainelArvore(inicial, this);
        painelArvore.setVisible(true);
    }
    
    public boolean passos(){
        setCaminho(false);
        if(!passo()){
            painelArvore.atualiza(inicial);
            return true;
        }
        painelArvore.atualiza(inicial);
        setCaminho(true);
        return(objetivo());
    }
    
    private void setCaminho(boolean b){
        Estado e = atual;
        while(e != null){
            e.setCaminho(b);
            e=e.getPai();
        }
    }
    
    public void mostrarCaminho(){
        if(painelArvore != null){
            painelArvore.dispose();
        }
        painelArvore = new PainelArvore(caminho);
        painelArvore.setVisible(true);
    }
    
    public void mostrarBusca(){
        if(painelArvore != null){
            painelArvore.dispose();
        }
        reset();
        painelArvore = new PainelArvore(inicial, posObstaculos, grid, xInicial, yInicial, xFinal, yFinal, this);
        painelArvore.setVisible(true);
    }

    public Estado getObj() {
        return obj;
    }

    public void setObj(Estado obj) {
        this.obj = obj;
    }
    
    
}
