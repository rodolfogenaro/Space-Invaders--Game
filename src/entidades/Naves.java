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
     * Este m??todo adiciona um tiro de canh??o do invasor em particular, podendo assim ter mais de um
     */
    public void TiroCanhao() {
        this.tiros.add(new Tiro(x-5, y+20, 2, "tiro.png")); //2 e a velocidade do tiro de naves
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    /**
     * Este m??todo retorna a posi????o atual do invasor em particular, no eixo Y (de cima para baixo)
     * @return y
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Este m??todo retorna a velocidade atual do invasor em particular
     * @return velocidade
     */
    public int getVelocidade() {
        return velocidade;
    }
    
    /**
     * Este m??todo afirma a nova velocidade do invasor em particular
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
     * Este m??todo atualiza o "delay" de movimento dessa nave em particular
     * @param pTempoLimite (parametro de tempoLimite)
     */
    public void setTempoLimite(int pTempoLimite) {
        tempoLimite = pTempoLimite;
    }
    /**
     * Este m??todo afirma que os invasores n??o podem mais se mover na horizontal (est??o encostados na "parede" da tela)
     * @param pDeslocarBaixo (parametro de DeslocarBaixo)
     */
    public void setDelocarBaixo(boolean pDeslocarBaixo) {
        deslocarBaixo = pDeslocarBaixo;
    }
    
    /**
     * Este m??todo retorna os tiros de canhao desse invasor em particular
     * @return tiros
     */
    public List<Tiro> getTiros() {
        return tiros;
    }
    
    /**
     * Este m??todo retorna o contador desse invasor em particular, que ?? limitado pelo "Tempo Limite" ("delay")
     * @return timer
     */
    public int getTimer() {
        return timer;
    }
    
    /**
     * Este m??todo retorna a variavel que limita a velocidade do invasor (Nave), que ser?? utilizada para reproduzir o som de movimenta????o
     * @return tempoLimite
     */
    public int getTempoLimite() {
        return tempoLimite;
    }
    
    /**
     * Este m??todo retorna a vari??vel que guarda a imagem do invasor (Nave) em espec??fico
     * @return 
     */
    public Image getImagemNaves() {
        return imagemNaves;
    }
}
