package main;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;

import entity.Fish;
import entity.Fruit;
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
	public Platform m_platform;
	List<Fish> fishList;
	private TileManager m_tileM;
	private List<Fruit> fruitList;


	private int map_indice = 0;
    private String[] mapFiles = {"/maps/map2.txt", "/maps/map.txt"}; 

	/**
	 * Constructeur
	 */
	public GamePanel() {
		m_FPS = 60;				
		m_keyH = new KeyHandler();
		m_player = new Player(this, m_keyH);
		
		objects_map1();
	
		set_tileM(new TileManager(this));

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);
		
		fruitList = new ArrayList<>();
		fruitList.add(new Fruit(this, m_keyH, 200,"Strawberry"));
		fruitList.add(new Fruit(this, m_keyH, 150,"Orange"));
		fruitList.add(new Fruit(this, m_keyH, 300,"Pastheque"));
		
	}

	private void objects_map1() {
		fishList = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int initialX = 100 + random.nextInt(350); 
			fishList.add(new Fish(this, m_keyH, initialX));
		}

		m_platform = new Platform(this, m_keyH,330);
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

		if (map_indice == 0) {
			m_platform.update(295,410);

            for (Fish fish :fishList) {
                fish.update();
            }
        } 
		
		 if ((m_player.getM_x()>=(SCREEN_WIDTH - TILE_SIZE)) && ((map_indice == 0))) {
				fishList.clear();	
		        m_tileM.changeMap(mapFiles[1]);
		        m_player.setM_x(0); 
		        m_player.setM_y(TILE_SIZE);
	        }
		 
		 for (Fruit fruit : fruitList) {
				if (fruit.isVisible() && checkCollision(m_player, fruit)) {
					fruit.setVisible(false); // Faites disparaître le fruit s'il y a collision
					System.out.println("test");
					}}
				
	}
	



	/**
	 * Affichage des �l�ments
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		get_tileM().draw(g2);
		
		m_player.draw(g2);
		
		if (map_indice == 0) {
	        for (Fish fish : fishList) {
	            fish.draw(g2);
	        }
	        m_platform.draw(g2);
	        for (Fruit fruit : fruitList) {
				fruit.draw(g2);
			}
	        
	    } 
	    
	    g2.dispose();
	}

	private boolean checkCollision(Player player, Fruit fruit) {
		int playerX = m_player.getM_x();
		int playerY = m_player.getM_y();
		int fruitX = fruit.getM_x();
		int fruitY = fruit.getM_y();
		int tolerance = 24;
		// Vérifiez si les positions se chevauchent (vous pouvez ajuster les conditions pour votre logique spécifique)
	    return Math.abs(playerX - fruitX) < tolerance && Math.abs(playerY - fruitY) < tolerance;
	}
	
	public TileManager get_tileM() {
		return m_tileM;
	}

	public void set_tileM(TileManager m_tileM) {
		this.m_tileM = m_tileM;
	}

}
