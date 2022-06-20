/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interface_grafica;

import entidades.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 *
 * @author manod
 */
public class Cenario {
    GraphicsContext gc;
    
    public Cenario(GraphicsContext gc) {
        this.gc = gc;
    }
    
    /**
     * Este método prenche fundo com um retangulo do tamanho da janela de preto
     */
    public void telaPreta() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 600);
    }
    
    /**
     * Este método mostra as vidas restantes e score apenas se a fase ainda não acabou
     * @param jogador
     * @param score 
     */
    public void emJogo(Canhao jogador, int score) {
        gc.setFill(Color.WHITE);
        gc.fillText( "LIVES:", 20, 580 );
        for (int i=0; i < jogador.getVidas(); i++)
            gc.drawImage(new Image("canhao.png"), 70+i*42, 560);

        String pointsScore = "SCORE: " + score;
        gc.setFill(Color.WHITE);
        gc.fillText( pointsScore, 240, 580 );
    }
    
    /**
     * Caso a fase tenha acabado, este método muda o cenario para "Menu"
     * @param auxScore 
     */
    public void emMenu(int auxScore) {
        String pointsScore = "SCORE: " + auxScore;
        gc.setFill(Color.WHITE);
        gc.fillText( pointsScore, 350, 580 );
        gc.setFont(Font.font(40));
        gc.fillText("FIM DE JOGO", 270, 250);
        gc.setFont(Font.getDefault());
        gc.fillText("APERTE A TECLA ESPAÇO PARA REINICIAR", 250, 300);
    }
    
    /**
     * Este método é responsável por representar o canhão (jogador) na forma de uma imagem .png
     * @param jogador 
     */
    public void render(Canhao jogador) {
        gc.drawImage(jogador.getImagemCanhao(), jogador.getX(), 510);
    }
    
    /**
     * Este método é responsável por representar a base (barreira) em específico na forma de um retângulo verde
     * @param base 
     */
    public void render(Barreira base) {
        gc.setFill(Color.rgb(28, 198, 31));
        gc.fillRect(base.getX(), base.getY(), 8, 10);
    }
    
    /**
     * Este método é responsável por representar o invasor (nave) em específico na forma de uma imagem .png
     * @param invasor 
     */
    public void render(Naves invasor) {
        gc.drawImage(invasor.getImagemNaves(), invasor.getX(), invasor.getY());
    }
    
    /**
     * Este método é responsável por representar o tiro da entidade em específico na forma de uma imagem .png
     * @param tiro 
     */
    public void render(Tiro tiro) {
        gc.drawImage(tiro.getImagemTiro(), tiro.getX(), tiro.getY());
    }
}
