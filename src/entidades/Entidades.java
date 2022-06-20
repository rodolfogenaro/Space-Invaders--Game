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
public abstract class Entidades {
    /**
     * Este método retorna as medidas da entidade para checar colisões
     * @return Rectangle
     */
    public abstract Rectangle2D getBoundary();
    /**
     * Este método renderiza a imagem da entidade
     * @return gc
     */
    public abstract int getX();
    /**
     * Este método atualiza a posição da entidade a cada repetição do programa
     */
    public abstract void atualizaPosicao();
    /**
     * Este método retorna o atributo "visivel" da entidade, o qual revela se está presente ou não na tela
     * @return boolean visivel
     */
    public abstract boolean getVisivel();
    /**
     * Este método decide se a entidade deve ou não aparecer na tela
     * @param pVisivel (parametro de visivel)
     */
    public abstract void setVisivel(boolean pVisivel);
}
