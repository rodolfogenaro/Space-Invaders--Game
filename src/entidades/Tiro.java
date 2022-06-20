/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 *
 * @author manod
 */
public class Tiro extends Entidades {
    private int x, y;
    private boolean visivel;
    private int velocidade;
    private Image imagemTiro; 
    
    Tiro (int x, int y, int pVelocidade, String endereco) {
        this.x = x + 20; //posicao do meio do canhao
        this.y = y;
        velocidade = pVelocidade;
        visivel = true;
        
        imagemTiro = new Image(endereco);
    }
    
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, imagemTiro.getWidth(), imagemTiro.getHeight());
    }
    
    @Override
    public void atualizaPosicao() {
        this.y += velocidade;
        if (this.y < 0 || this.y > 600) //verifica se o tiro saiu da tela
            visivel = false;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    /**
     * Este método retorna a posição atual desse tiro em particular, no eixo Y (de cima para baixo)
     * @return y
     */
    public int getY() {
        return this.y;
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
     * Este método retorna a velocidade desse tiro em particular no eixo Y (de cima para baixo)
     * @return velocidade
     */
    public int getVelocidade() {
        return velocidade;
    }
    
    /**
     * Este método afirma a nova velocidade desse tiro em particular
     * @param pVelocidade (parametro de velocidade)
     */
    public void setVelocidade(int pVelocidade) {
        velocidade = pVelocidade;
    }
    
    /**
     * Este método retorna a variavel que guarda a imagem desse tiro em específico
     * @return 
     */
    public Image getImagemTiro() {
        return imagemTiro;
    }
}
