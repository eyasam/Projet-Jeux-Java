package main;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Enemy;
import entity.Fish;
import entity.Collect;
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
	public Platform m_platform,m_platform2;
	public List<Enemy> enemyList;

	List<Fish> fishList;
	private TileManager m_tileM;
	private List<Collect> collectList2,collectList1;


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
		objects_map2();

		set_tileM(new TileManager(this));

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(m_keyH);
		this.setFocusable(true);

	}

	private void objects_map1() {
		fishList = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int initialX = 100 + random.nextInt(350); 
			fishList.add(new Fish(this, m_keyH, initialX));
		}

		m_platform = new Platform(this, m_keyH,330,true);

		collectList1 = new ArrayList<>();
		collectList1.add(new Collect(this, m_keyH,270,200,"Strawberry"));
		collectList1.add(new Collect(this, m_keyH,175,150,"Orange"));
		collectList1.add(new Collect(this, m_keyH,500,300,"Pastheque"));
	}
	private void objects_map2() {
		enemyList=new ArrayList<>();
		enemyList.add(new Enemy(this, m_keyH,300,20));
		enemyList.add(new Enemy(this, m_keyH,420,20));
		
		collectList2 = new ArrayList<>();
		collectList2.add(new Collect(this, m_keyH,160, 30,"Bouteille"));
		collectList2.add(new Collect(this, m_keyH,560, 30,"Bouteille"));
		collectList2.add(new Collect(this, m_keyH,600, 260,"Bouteille"));
		collectList2.add(new Collect(this, m_keyH,530, 310,"Bouteille"));
		collectList2.add(new Collect(this, m_keyH,300, 260,"Bouteille"));
		collectList2.add(new Collect(this, m_keyH,180, 310,"Bouteille"));




		
			
		
		m_platform2 = new Platform(this, m_keyH,330,false);


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
		//System.out.print("map :"+map_indice);
		System.out.println("X :"+m_player.m_x);
		System.out.println("Y :"+m_player.m_y);

		m_player.update();

		if (map_indice == 0) {
			m_platform.update(295,410);

			for (Fish fish :fishList) {
				fish.update();
			}
			for (Collect c :collectList1) {
				if (c.isVisible() && checkCollision(c)) {
					m_player.incrementVie(15);
					c.setVisible(false); 
					System.out.println("a disparu");
				}}
		

		if ((m_player.getM_x()>=(SCREEN_WIDTH - TILE_SIZE))) {
			fishList.clear();	
			collectList1.clear();	
			m_tileM.loadMap(mapFiles[1]);
			m_player.setM_x(0); 
			m_player.setM_y(TILE_SIZE);
			map_indice+=1;

		}}else{

			m_platform2.update(295,410);

			for (Enemy e :enemyList) {
				e.update();
			}
			for (Collect c :collectList2) {
				if (c.isVisible() && checkCollision(c)) {
					m_player.incrementVie(15);
					c.setVisible(false); 
					System.out.println("a disparu");
				}
			}
			
			  if (m_player.getM_x()<=20 && m_player.getM_y()> 410) {
				    System.out.print("fin");
			        EndWindow();
			    }
		}

		

	}

	public void GameOverDialog() {
		int option = JOptionPane.showOptionDialog(null, 
				"GAME OVER", 
				"Game Over", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				new String[]{"Restart", "Exit"}, 
				"Restart");
		if (option == JOptionPane.YES_OPTION) {
			m_player.setM_vie(100);
			m_player.setDefaultValues();
			enemyList.clear();
			collectList2.clear();
			
			m_tileM.loadMap(mapFiles[0]);
			
		} else {
			System.exit(0);
		}
	}
	
	private void EndWindow() {
		int option = JOptionPane.showOptionDialog(null, 
	            "Congratulations!!", 
	            "END", 
	            JOptionPane.YES_NO_OPTION, 
	            JOptionPane.INFORMATION_MESSAGE, 
	            null, 
	            new String[]{"Exit"}, 
	            "Exit");
	    if (option == JOptionPane.YES_OPTION) {
	        System.exit(0);
	    }

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
			for (Fish fish :fishList) {
				fish.draw(g2);
			}
			m_platform.draw(g2);
			for (Collect c :collectList1) {
				c.draw(g2);
			}}

		
			else{
				m_platform2.draw(g2);

			for (Enemy e :enemyList) {
					e.draw(g2);
				}
			for (Collect c :collectList2) {
				c.draw(g2);
			}
			} 

		g2.dispose();
	}

	/*
	 *  verifie s'il y a une collision entre le joueur et un fruit donne (avec une tolerance de 24)
	 * */

	private boolean checkCollision(Collect c) {
		int player_x=m_player.m_x;
		int player_y=m_player.m_y;

		int tolerance = 24;

		boolean test_x=Math.abs(player_x-c.m_x)<tolerance;
		boolean test_y=Math.abs(player_y -c.m_y)<tolerance;

		return (test_x && test_y);
	}

	public TileManager get_tileM() {
		return m_tileM;
	}

	public void set_tileM(TileManager m_tileM) {
		this.m_tileM = m_tileM;
	}

}
