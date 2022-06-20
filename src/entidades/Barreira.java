/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javafx.geometry.Rectangle2D;

/**
 *
 * @author manod
 */
public class Barreira extends Entidades {
    private boolean visivel;
    private int x, y;
    
    public Barreira(int x, int y) {
        this.x = x;
        this.y = y;
        visivel = true;
    }
    
    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, 8, 10);
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    /**
     * Este método retorna a posição desse pedaço de barreira em relação ao eixo Y (de cima para baixo)
     * @return y
     */
    public int getY() {
        return y;
    }
    
    @Override
    public void atualizaPosicao() {
        //não é necessário atualizar pois não movimento deste
    }
    
    @Override
    public boolean getVisivel() {
        return visivel;
    }
    
    @Override
    public void setVisivel(boolean pVisivel) {
        visivel = pVisivel;
    }
}
