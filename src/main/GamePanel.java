package main;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

import entity.Fish;
import entity.Platform;
import entity.Player;
import tile.TileManager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Panel principal du jeu contenant la map principale
 *
 */
public class GamePanel extends JPanel implements Runnable{

	//Param�tres de l'�cran
	final int ORIGINAL_TILE_SIZE = 8; 							// une tuile de taille 16x16
	final int SCALE = 4; 										// �chelle utilis�e pour agrandir l'affichage
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; 	// 48x48
	public final int MAX_SCREEN_COL = 24;
	public final int MAX_SCREE_ROW = 16; 					 	// ces valeurs donnent une r�solution 4:3
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;	// 576 pixels

	// FPS : taux de rafraichissement
	int m_FPS;

	// Cr�ation des diff�rentes instances (Player, KeyHandler, TileManager, GameThread ...)
	KeyHandler m_keyH;
	Thread m_gameThread;
	Player m_player;
	public Platform m_platform1,m_platform2;
	List<Fish> fishList;
	private TileManager m_tileM;



	/**
	 * Constructeur
	 */
	public GamePanel() {
		m_FPS = 60;				
		m_keyH = new KeyHandler();
		m_player = new Player(this, m_keyH);
		m_platform1 = new Platform(this, m_keyH,290);
		m_platform2 = new Platform(this, m_keyH,330);

		fishList = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int initialX = 100 + random.nextInt(350); 
			fishList.add(new Fish(this, m_keyH, initialX));
		}

		set_tileM(new TileManager(this));

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);
		
	}

	/**
	 * Lancement du thread principal
	 */
	public void startGameThread() {
		m_gameThread = new Thread(this);
		m_gameThread.start();
	}

	public void run() {

		double drawInterval = 1000000000/m_FPS; // rafraichissement chaque 0.0166666 secondes
		double nextDrawTime = System.nanoTime() + drawInterval; 

		while(m_gameThread != null) { //Tant que le thread du jeu est actif

			//Permet de mettre � jour les diff�rentes variables du jeu
			this.update();

			//Dessine sur l'�cran le personnage et la map avec les nouvelles informations. la m�thode "paintComponent" doit obligatoirement �tre appel�e avec "repaint()"
			this.repaint();

			//Calcule le temps de pause du thread
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;

				if(remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/**
	 * Mise a jour des donn�es des entit�s
	 */
	public void update() {
		m_player.update();
		m_platform2.update(295,410);

		for (Fish fish:fishList) {
			fish.update();
		}	}

	/**
	 * Affichage des �l�ments
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		get_tileM().draw(g2);
		for (Fish fish : fishList) {
			fish.draw(g2);
		}
		m_player.draw(g2);
		m_platform2.draw(g2);


		g2.dispose();
	}

	public TileManager get_tileM() {
		return m_tileM;
	}

	public void set_tileM(TileManager m_tileM) {
		this.m_tileM = m_tileM;
	}

}
