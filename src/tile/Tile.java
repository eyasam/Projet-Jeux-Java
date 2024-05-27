package tile;

import java.awt.image.BufferedImage;

/**
 * 
 * Element graphique de la carte
 */
public class Tile {
	public BufferedImage m_image;		//image
	public boolean m_collision;			//d�but de gestion de collision entre �l�ments
	
	Tile(){
		m_collision = false;
	}
	
	public BufferedImage getM_image() {
		return m_image;
	}

	public void setM_image(BufferedImage m_image) {
		this.m_image = m_image;
	}

	public boolean isM_collision() {
		return m_collision;
	}

	public void setM_collision(boolean m_collision) {
		this.m_collision = m_collision;
	}

}
