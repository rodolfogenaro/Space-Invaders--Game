/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_poo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import entidades.*;
import interface_grafica.*;
/**
 *
 * @author manod
 */
public class Projeto_POO extends Application {
    
    private int timer=0, limitadorTiros=0, limitadorSeguidor=0; //limitadores de tiros de naves
    private int score=0, auxScore, nivelNaves;
    private boolean fimdejogo, proxfase;
    
    private AudioClip explosion = new AudioClip(getClass().getResource("sounds/explosion.wav").toString());
    private AudioClip invaderkilled = new AudioClip(getClass().getResource("sounds/invaderkilled.wav").toString());
    private AudioClip fastinvader = new AudioClip(getClass().getResource("sounds/fastinvader1.wav").toString());
    private AudioClip shoot = new AudioClip(getClass().getResource("sounds/shoot.wav").toString());
    
    private Canhao jogador = new Canhao("canhao.png", shoot);
    private List<Naves> invasores;
    private List<Barreira> bases;
    
    private Cenario grafico;
    
    /**
     * Este método é responsável por toda a engine do jogo, inclusive os componentes de interface gráfica que aqui estavam na Parte 1 do projeto foram para classe "Cenario"
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {   
        primaryStage.setTitle("Space Invaders");
        
        primaryStage.getIcons().add(new Image("iconSpaceInvaders.png"));
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );
         
        Canvas canvas = new Canvas( 800, 600 );
        root.getChildren().add( canvas );
        
        //antigo construtor de "Cenario"
        jogador.setEmJogo(true);
        fimdejogo = false;
        proxfase = false;
        nivelNaves = 30;
        iniInvasores();
        iniBases();
        
        ArrayList<String> input = new ArrayList<String>();
 
        theScene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
 
                    // only add once... prevent duplicates
                    if ( !input.contains(code) )
                        input.add( code );
                }
            });
 
        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            });
         
        GraphicsContext gc = canvas.getGraphicsContext2D();
        grafico = new Cenario(gc);
        
        final long startNanoTime = System.nanoTime();
        
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
 
                // prenche fundo com um retangulo do tamanho da janela de preto
                grafico.telaPreta();
                
                if (jogador.getEmJogo() == true) //mostra as vidas restantes e score apenas se a fase ainda não acabou
                    grafico.emJogo(jogador, score);
                //atualiza imagem do canhao
                if (input.contains("LEFT"))
                    jogador.setDx(-3);
                else if (input.contains("RIGHT"))
                    jogador.setDx(3);
                else
                    jogador.setDx(0);
                jogador.atualizaPosicao();
                if (jogador.getEmJogo() == true)
                    grafico.render(jogador);//jogador.render(gc);
                //tiros de canhao
                if (input.contains("SPACE")) {
                    if (jogador.getEmJogo() == true)
                        jogador.TiroCanhao();
                    else if (jogador.getEmJogo() == false) {
                        jogador.setEmJogo(true);
                        jogador.setVidas(3);
                    }
                }
                List<Tiro> Ctiros = jogador.getTiros();
                for (int j = 0; j < Ctiros.size(); j++)
                {
                    Tiro ct = Ctiros.get(j);
                    if (ct.getVisivel())
                        ct.atualizaPosicao();
                    else {
                        if (ct.getVelocidade() == -8) //caso seja tiro de canhao
                            jogador.setTCanhao(false);
                        Ctiros.remove(j);
                    }
                    
                    if (jogador.getEmJogo() == true)
                        grafico.render(ct); //ct.render(gc);
                }
                //atualiza imagens das naves
                for (int i=0; i < invasores.size(); i++)
                {
                    Naves n = invasores.get(i);
                    int indTiro = new Random().nextInt(invasores.size()); //randomiza os tiros de naves
                    if (n.getVisivel()) {
                        //verifica trajeto
                        if (n.getVelocidade()==10 && (n.getX()+2*n.getImagemNaves().getWidth()+10)>800) //60 (2 vezes largura das naves) + 10 da veloicade
                            trajetoNaves(10);
                        else if (n.getVelocidade()==-10 && (n.getX()-10)<0)
                            trajetoNaves(-10);
                        //atualiza a posicao das naves
                        n.atualizaPosicao();
                    }
                    else {
                        invasores.remove(i);
                        invaderkilled.play(); //morte de invasor sound effect
                        i--; //verifica se removeu uma nave para entao voltar uma unidade de indice i
                        score += 10;
                        alteraTempoLimiteNaves();
                    }
                    
                    if (jogador.getEmJogo() == true)
                        grafico.render(n); //n.render(gc);
                    //tiros das naves
                    if (i == indTiro) { //tiro de nave aleatoria, limitado por um contador que diminiui o limite a cada baixa de nave
                        limitadorTiros++;
                        if (limitadorTiros >= 140 - 2*(55 - invasores.size()) ) {
                            limitadorTiros=0;
                            n.TiroCanhao();
                        }
                    } 
                    if(jogador.getX() == n.getX()) { //tiro de nave na mesma coluna que o canhao
                        limitadorSeguidor++;
                        if (limitadorSeguidor >= 80) { //tiros de naves que estao na mesma coluna que o canhao, porem estes nao aumentam a frequencia
                            limitadorSeguidor=0;
                            n.TiroCanhao();
                        }
                    }
                    List<Tiro> Ntiros = n.getTiros();
                    for (int k=0; k < Ntiros.size(); k++)
                    {
                        Tiro nt = Ntiros.get(k);
                
                        if (nt.getVisivel())
                            nt.atualizaPosicao();
                        else
                            Ntiros.remove(k);
                        
                        if (jogador.getEmJogo() == true)
                            grafico.render(nt); //nt.render(gc);
                    }
                }
                //movimentacao dos invasores sound effect
                if (invasores.size() > 0 && jogador.getEmJogo() == true) {
                    Naves n = invasores.get(0);
                    if (n.getTimer() >= n.getTempoLimite()-1)
                        fastinvader.play();
                }
                //atualiza imagem da barreira (bases)
                for (int p=0; p < bases.size(); p++)
                {
                    Barreira b = bases.get(p);
                    if (b.getVisivel() == false)
                        bases.remove(p);
                    
                    if (jogador.getEmJogo() == true)
                        grafico.render(b);//b.render(gc);
                }
                checaColisao();
                
                if (invasores.size() <= 0) { //jogador venceu a partida
                    jogador.setEmJogo(false);
                    fimdejogo = true;
                    proxfase = true;
                }
        
                if (fimdejogo == true) { //confere se o jagador ja perdeu para entao reiniciar o sistema
                    fimdejogo = false;
                    
                    //explosion sound effect
                    explosion.play();
                    
                    //reseta as naves
                    invasores.clear();
                    iniInvasores();
                    //reseta as bases
                    bases.clear();
                    iniBases();
            
                    jogador.setX(160); //reinicia posicao inicial do canhao (jogador)
            
                    //reseta score
                    auxScore = score;
                    if (proxfase == true) {
                        nivelNaves += 30; //faz com que os invasores sejam iniciados em posicoes um pouco mais abaixo caso o jogador venceu a fase
                    }
                    else {
                        nivelNaves = 30;
                        score = 0;
                    }
                    proxfase = false;
                }
                
                if (jogador.getEmJogo() == false) //caso a fase tenha acabado, mudar cenario para "Menu"
                    grafico.emMenu(auxScore);
            }
        }.start();
        
        
        primaryStage.show();
    }
    
    /**
     * Este método inicializa a matriz 5x11 de invasores (naves) no jogo
     */
    public void iniInvasores() {
        invasores = new ArrayList<Naves>();
        
        for(int i=0; i < 11; i++) //11 colunas
        {
            for(int j=0; j < 5; j++) //5 linhas
            {
                //56 (numero de naves inicial mais 1) e o limite do temporizador
                if (j==0)
                    invasores.add(new Naves(20+i*60, nivelNaves+j*60, 10, 56, false, "invasor1.png"));
                else if (j==1 || j==2)
                    invasores.add(new Naves(20+i*60, nivelNaves+j*60, 10, 56, false, "invasor2.png"));
                else if (j==3 || j==4)
                    invasores.add(new Naves(20+i*60, nivelNaves+j*60, 10, 56, false, "invasor3.png"));
            }
        }
    }
    
    /**
     * Este método inicializa as barreiras por suas pedaços, sendo que cada pedaço tem uma "vida" 
     */
    public void iniBases() {
        bases = new ArrayList<Barreira>();
        
        //linha de cima
        for (int i=0; i < 4; i++) //4 barreiras completas
        {
            for (int j=0; j < 10; j++) //9 (10-2) "terrenos" por barreira
            {
                if (j>0 && j<9)
                bases.add(new Barreira(100+8*j+160*i, 460));
            }
        }
        //linha do meio
        for (int i=0; i < 4; i++) //4 barreiras completas
        {
            for (int j=0; j < 10; j++) //10 "terrenos" por barreira
            {
                bases.add(new Barreira(100+8*j+160*i, 470));
            }
        }
        //linha de baixo
        for (int i=0; i < 4; i++) //4 barreiras completas
        {
            for (int j=0; j < 10; j++) //4 (10-6) "terrenos" por barreira
            {
                if (j<3 || j>6)
                    bases.add(new Barreira(100+8*j+160*i, 480));
            }
        }
    }
    
    /**
     * Este método altera o "delay" do movimento dos invasores, diminuindo a cada baixa
     */
    public void alteraTempoLimiteNaves() {
        for (int m=0; m < invasores.size(); m++)
        {
            Naves nTemp = invasores.get(m);
            nTemp.setTempoLimite(invasores.size()+1); //+1 e para nao parar totalmente
        }
    }
    
    /**
     * Este método é responsável pelo movimento em zigue-zague dos invasores
     * @param pVelocidade (parametro de velocidade)
     */
    public void trajetoNaves(int pVelocidade) {
        //deslocamento das naves na direcao do canhao
        if (pVelocidade == 10) {
            for (int l=0; l < invasores.size(); l++)
            {
                Naves tempN = invasores.get(l);
                tempN.setDelocarBaixo(true);
                tempN.setVelocidade(-10);
            }
        }
        else if (pVelocidade == -10) {
            for (int l=0; l < invasores.size(); l++)
            {
                Naves tempN = invasores.get(l);
                tempN.setDelocarBaixo(true);
                tempN.setVelocidade(10);
            }
        } 
    }
    
    /**
     * Este método checa se houve alguma colisão entre as entidades
     */
    public void checaColisao() {
        Rectangle2D formatoCanhao = jogador.getBoundary();
        Rectangle2D formatoNaves;
        Rectangle2D formatoTiro;
        Rectangle2D formatoBases;
        
        //colisao entre canhao e naves
        for(int j=0; j < invasores.size(); j++)
        {
            Naves tNaves = invasores.get(j);
            formatoNaves = tNaves.getBoundary();
            if (formatoCanhao.intersects(formatoNaves)) {
                jogador.setVisivel(false);
                tNaves.setVisivel(false);
                jogador.setEmJogo(false);
                fimdejogo = true;
            }
        }
        
        //colisao do tiro de canhao
        List <Tiro> tirosC = jogador.getTiros();
        for(int i=0; i < tirosC.size(); i++)
        {
            Tiro tiroTempC = tirosC.get(i);
            formatoTiro = tiroTempC.getBoundary();
            //colisao entre tiro e naves
            for(int k=0; k < invasores.size(); k++)
            {
                Naves navesTemp = invasores.get(k);
                formatoNaves = navesTemp.getBoundary();
                if (formatoTiro.intersects(formatoNaves)) {
                    navesTemp.setVisivel(false);
                    tiroTempC.setVisivel(false);
                }
            }
            //colisao entre tiro e bases
            for (int p=0; p < bases.size(); p++)
            {
                Barreira basesTemp = bases.get(p);
                formatoBases = basesTemp.getBoundary();
                if (formatoTiro.intersects(formatoBases)) {
                    basesTemp.setVisivel(false);
                    tiroTempC.setVisivel(false);
                }
            }
        }
        
        //colisao de tiros de naves
        for (int l=0; l < invasores.size(); l++)
        {
            Naves Tnave = invasores.get(l);
            List<Tiro> tirosN = Tnave.getTiros();
            for (int m=0; m < tirosN.size(); m++)
            {
                Tiro tiroTempN = tirosN.get(m);
                formatoTiro = tiroTempN.getBoundary();
                //colisao entre tiro e canhao
                if (formatoTiro.intersects(formatoCanhao)) {
                    tiroTempN.setVisivel(false);
                    jogador.diminuiVidas();
                    if (jogador.getVidas() == 0) {
                        jogador.setVisivel(false);
                        jogador.setEmJogo(false);
                        fimdejogo = true;
                    }
                }
                for (int q=0; q < bases.size(); q++)
                {
                    Barreira Tbase = bases.get(q);
                    formatoBases = Tbase.getBoundary();
                    if (formatoTiro.intersects(formatoBases)) {
                        tiroTempN.setVisivel(false);
                        Tbase.setVisivel(false);
                    }
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
