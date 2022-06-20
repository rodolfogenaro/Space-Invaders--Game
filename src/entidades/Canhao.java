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
import javafx.scene.media.AudioClip;

/**
 *
 * @author manod
 */
public class Canhao extends Entidades {
    private int x, dx;
    private List <Tiro> tiros;
    private boolean tCanhao = false;
    private boolean visivel;
    private int vidas=3;
    private boolean emJogo;
    private Image imagemCanhao;
    private AudioClip shoot; //new AudioClip(getClass().getResource("sounds/shoot.wav").toString())
    
    public Canhao(String endereco, AudioClip shoot) {
        this.x = 160;
        visivel = true;
        
        tiros = new ArrayList<Tiro>();
        
        imagemCanhao = new Image(endereco); //pega endereco de imagem para salvar nessa variavel e ser graficada na classe "Cenario"
        this.shoot = shoot;
    }
    
    @Override
    public void atualizaPosicao() {
        if ((x-3 >= 5 && dx == -3) || (x+3 <= 800-43 && dx == 3)) //impede que o canhao se mova para fora da tela
            x += dx;
    }
   
    /**
     * Este método verifica se já existe um tiro de canhão na tela e "cria" um novo tiro caso não houver outro presente
     */
    public void TiroCanhao() {
        if (tCanhao == false) { //verifica se ja existe um tiro de canhao em movimento
            tCanhao = true;
            this.tiros.add(new Tiro(x-3, 510, -8, "tiro.png")); //-8 e a velocidade do tiro de canhao
            shoot.play();
        }
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, 510, imagemCanhao.getWidth(), imagemCanhao.getHeight());
    }
    
    /**
     * Este método retorna os tiros do canhão
     * @return tiros
     */
    public List<Tiro> getTiros() {
        return tiros;
    }
    
    /**
     * Este método afirma se há um tiro de canhão já presente na tela ou não
     * @param tCanhao (tiro de canhao)
     */
    public void setTCanhao(boolean tCanhao) {
        this.tCanhao = tCanhao;
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
     * Este método retorna o número de vidas restantes do canhão (jogador)
     * @return vidas
     */
    public int getVidas() {
        return vidas;
    }
    
    /**
     * Este método reseta o número de vidas do canhão (jogador)
     * @param vidas
     */
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    
    /**
     * Este método diminui a quantidade de vidas restantes do canhão (jogador) caso este morra (receba um tiro)
     */
    public void diminuiVidas() {
        vidas--;
    }
    
    /**
     * Este método retorna se o canhão (jogador) está ou não morto
     * @return emJogo
     */
    public boolean getEmJogo() {
        return emJogo;
    }
    
    /**
     * Este método afirma se o canhão (jogador) está ou não morto
     * @param pEmJogo (parametro para emJogo)
     */
    public void setEmJogo(boolean pEmJogo) {
        emJogo = pEmJogo;
    }
    
    /**
     * Este método afirma a nova posição do canhão (jogaodor) em relação ao eixo X, logo após o término do jogo
     * @param x (x do canhão)
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Este método seta a "velocidade" do jogador (Canhao) que é apenas ativada quando as teclas de seta para esquerda ou direita estão sendo pressionadas
     * @param dx 
     */
    public void setDx(int dx) {
        this.dx = dx;
    }
    
    /**
     * Este método retorna a variavel que guarda a imagem do jogador (Canhao)
     * @return 
     */
    public Image getImagemCanhao() {
        return imagemCanhao;
    }
}
