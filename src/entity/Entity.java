package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

/**
 * Entit� de base du jeu
 *
 */
public abstract class Entity {
	public int m_x, m_y;				//position sur la map
	public int m_speed;					//D�placement de l'entit�
	public BufferedImage m_idleImage;	//Une image de l'entit�
	public BufferedImage m_reverseImage;
	
	GamePanel m_gp;
	KeyHandler m_keyH;
	
	 public Entity(GamePanel gp, KeyHandler keyH) {
	        this.m_gp = gp;
	        this.m_keyH = keyH;
	    }
	
	public void update() {
		
	}

	public abstract void draw(Graphics2D a_g2);

	public int getM_y() {
		return m_y;
	}

	public void setM_y(int m_y) {
		this.m_y = m_y;
	}

	public int getM_x() {
		return m_x;
	}

	public void setM_x(int m_x) {
		this.m_x = m_x;
	}
}
