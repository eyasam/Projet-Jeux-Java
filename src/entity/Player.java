package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

/**
 * D�fintition du comportement d'un joueur
 *
 */
public class Player extends Entity{

	GamePanel m_gp;
	KeyHandler m_keyH;

	private boolean sauter= false;
	private boolean tomber = false;
	private double gravity = 0.5;
	private int m_chute = 0;;


	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.setDefaultValues();
		this.getPlayerImage();
	}

	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues() {
		m_x = 100;
		m_y = 100;
		m_speed =2;
		sauter = false;
		tomber = true; 
		m_chute=0;
	}

	/**
	 * Récuperation de l'image du personnage
	 */
	public void getPlayerImage() {
		//gestion des expections 
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/player/superhero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mise à jour des données du joueur
	 * en verifiant que le dép ne sort pas de l'ecran 
	 */
	public void update() {
		int limit_Y= m_gp.SCREEN_HEIGHT - m_gp.TILE_SIZE; //max en bas
		int limit_X= m_gp.SCREEN_WIDTH - m_gp.TILE_SIZE; //max a droite

		int newY_B=m_y+m_speed;
		if (m_keyH.m_bas && (newY_B<=limit_Y) && (!isObstacle(m_x,newY_B+m_gp.TILE_SIZE))) { // assurer que la tuile est juste en dessous du joueur dans la direction du déplacement
			m_y +=m_speed;
		}

		int newX_G=m_x-m_speed;
		if (m_keyH.m_gauche && (newX_G>=0) && (!isObstacle(newX_G,m_y))) {
			m_x -= m_speed;
		}

		int newX_D=m_x+m_speed;
		if (m_keyH.m_droite && (newX_D<=limit_X) && (!isObstacle(newX_D+m_gp.TILE_SIZE,m_y))) {
			m_x+= m_speed;
		}

		if (m_keyH.m_saut && !sauter && !tomber) {
			sauter = true;
		}

		if (sauter) {
			m_chute= -10;// valeur negative puisque vers le c une chute
			sauter = false;
			tomber = true;
		}

		if (tomber) {
			m_chute+=gravity; //acceleration de la chute
			if (m_chute>10) {
				m_chute=10;// on suppose que 10 est le max de la chute
			}
			m_y+=m_chute;
		}
		
		
		if (m_y>limit_Y) {
			m_y=limit_Y;
			tomber=false;
		} else {
			tomber = true;
		}
	}

	/*
	 * Vérifier si la tuile est un obstacle
	 *@param x : coordonnee horizontale de la tuile
	 *@param y : coordonnee verticale de la tuile
	 * */
	private boolean isObstacle(int x,int y) {
		boolean test_col = false;

		//indice de la tuile 
		int tileX =x/m_gp.TILE_SIZE;
		int tileY =y/m_gp.TILE_SIZE;

		if ((tileX>=0) && 
				(tileX<m_gp.MAX_SCREEN_COL) && 
				(tileY>=0) && 
				(tileY<m_gp.MAX_SCREE_ROW)) {
			// t c le num de la tuile (num brick=1)
			int t = m_gp.get_tileM().getTileNum(tileX,tileY);
			test_col= m_gp.get_tileM().isCollision(t);
			/*
	        if (test_col) {
	        	System.out.println("\nOUPSSS Collision : \ntuile x : "+tileX +", tuile y :"+tileY);
		        System.out.println("c la tuile num :  "+t);
	        }*/
			return test_col;
		}
		return test_col;
	}


	/**
	 * Affichage du l'image du joueur dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Graphics2D a_g2) {
		// r�cup�re l'image du joueur
		BufferedImage l_image = m_idleImage;
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
	}


}
