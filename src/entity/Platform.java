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
public class Platform extends Entity{


	private int m_direction;
    public boolean m_collision;

	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Platform(GamePanel a_gp,KeyHandler a_keyH,int y) {
        super(a_gp,a_keyH);
		this.setDefaultValues(y);
		this.getPlayerImage();
        m_collision = true;

	}

	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues(int y) {
		m_x = 300;
		m_y =y;
		m_speed =2;
		m_direction =1;

	}

	/**
	 * Récuperation de l'image du personnage
	 */
	public void getPlayerImage() {
		//gestion des expections 
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/tiles/platform.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mise à jour des données du joueur
	 * en verifiant que le dép ne sort pas de l'ecran 
	 */
	public void update(int limitG,int limitD) {
		
       m_x+=m_speed*m_direction;
		  if ((m_x>limitD) || (m_x<limitG)) {
	            m_direction *= -1;
	        }
		

	}

	 public int getTopY() {
	        return m_y;
	    }
	 
	 //verifie si le joueur est au dessus de la plateform
	  public boolean isPlayerAbove(Player p) {
	        int Py_min = p.getM_y() + m_gp.TILE_SIZE; 
	        return Py_min<=getTopY(); 
	   
	  }
	  
	  

	/**
	 * Affichage du l'image du joueur dans la fen�tre du jeu
	 * @param a_g2 Graphics2D 
	 */
	@Override
	public void draw(Graphics2D a_g2) {
		// r�cup�re l'image du joueur
		BufferedImage l_image = m_idleImage;
		// affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE*3, m_gp.TILE_SIZE, null);
	}

	


}
