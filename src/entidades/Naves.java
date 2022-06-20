/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 *
 * @author manod
 */
public class Naves extends Entidades {
    
    private int x, y;
    private boolean visivel;
    private int velocidade;
    private int countString;
    private int timer=0, tempoLimite; 
    private boolean deslocarBaixo;
    private List <Tiro> tiros;
    private Image imagemNaves, imagemNaves_2, aux;
    private int trocaImagem=0;
    
    public Naves (int x, int y, int pVelocidade, int pTempoLimite, boolean pDeslocarBaixo, String endereco) {
        this.x = x; //posicao do meio do canhao
        this.y = y;
        
        velocidade = pVelocidade;
        tempoLimite = pTempoLimite;
        deslocarBaixo = pDeslocarBaixo;
        
        tiros = new ArrayList<Tiro>();
        visivel = true;
        
        imagemNaves = new Image(endereco);
        imagemNaves_2 = new Image("M" + endereco);
    }
    
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, imagemNaves.getWidth(), imagemNaves.getHeight()+5);
    }
    
    @Override
    public void atualizaPosicao() {
        timer++;
        if (deslocarBaixo == true) {
            deslocarBaixo = false;
            this.y += 15;
        }
        if (timer >= tempoLimite) { //dimiuni o temporizador a cada nave derrubada
            timer=0;
            //animacao dos invasores
            if (trocaImagem == 0) {
                trocaImagem=1;
                aux = imagemNaves;
                imagemNaves = imagemNaves_2;
                imagemNaves_2 = aux;
            }
            else if (trocaImagem == 1) {
                trocaImagem=0;
                aux = imagemNaves;
                imagemNaves = imagemNaves_2;
                imagemNaves_2 = aux;
            }
            this.x += velocidade;
        }
    }
    
    /**
     * Este método adiciona um tiro de canhão do invasor em particular, podendo assim ter mais de um
     */
    public void TiroCanhao() {
        this.tiros.add(new Tiro(x-5, y+20, 2, "tiro.png")); //2 e a velocidade do tiro de naves
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    /**
     * Este método retorna a posição atual do invasor em particular, no eixo Y (de cima para baixo)
     * @return y
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Este método retorna a velocidade atual do invasor em particular
     * @return velocidade
     */
    public int getVelocidade() {
        return velocidade;
    }
    
    /**
     * Este método afirma a nova velocidade do invasor em particular
     * @param pVelocidade (parametro de velocidade)
     */
    public void setVelocidade(int pVelocidade) {
        velocidade = pVelocidade;
    }
    
    @Override
    public boolean getVisivel() {
        return visivel;
    }
    
    @Override
    public void setVisivel(boolean pVisivel) {
        visivel = pVisivel;
    }
    
    /**
     * Este método atualiza o "delay" de movimento dessa nave em particular
     * @param pTempoLimite (parametro de tempoLimite)
     */
    public void setTempoLimite(int pTempoLimite) {
        tempoLimite = pTempoLimite;
    }
    /**
     * Este método afirma que os invasores não podem mais se mover na horizontal (estão encostados na "parede" da tela)
     * @param pDeslocarBaixo (parametro de DeslocarBaixo)
     */
    public void setDelocarBaixo(boolean pDeslocarBaixo) {
        deslocarBaixo = pDeslocarBaixo;
    }
    
    /**
     * Este método retorna os tiros de canhao desse invasor em particular
     * @return tiros
     */
    public List<Tiro> getTiros() {
        return tiros;
    }
    
    /**
     * Este método retorna o contador desse invasor em particular, que é limitado pelo "Tempo Limite" ("delay")
     * @return timer
     */
    public int getTimer() {
        return timer;
    }
    
    /**
     * Este método retorna a variavel que limita a velocidade do invasor (Nave), que será utilizada para reproduzir o som de movimentação
     * @return tempoLimite
     */
    public int getTempoLimite() {
        return tempoLimite;
    }
    
    /**
     * Este método retorna a variável que guarda a imagem do invasor (Nave) em específico
     * @return 
     */
    public Image getImagemNaves() {
        return imagemNaves;
    }
}
