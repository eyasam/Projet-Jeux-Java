package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Fish extends Entity {
	private long dernier_saut;
	private Random random;
	private int saut; 

	public Fish(GamePanel a_gp, KeyHandler a_keyH, int x) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.setDefaultValues(x);
		this.getFishImage();
		this.random = new Random();
		this.dernier_saut = System.currentTimeMillis();
	}

	/**
	 * Récuperation de l'image du poisson
	 */
	public void getFishImage() {
		//gestion des exceptions 
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/tiles/fish.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDefaultValues(int x) {
		m_x = x;
		m_y = 440;
	}

	@Override
	public void draw(Graphics2D a_g2) {
		BufferedImage l_image = m_idleImage;
		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
	}

	@Override
	public void update() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime-dernier_saut)>=1500) {
			saut=random.nextInt(50); 
			m_y-=saut;
			dernier_saut=currentTime;
		}

		if ((m_y<440) && (currentTime-dernier_saut)>=500) { // il faut qu il descent
			m_y+=saut;
		}
	}


}



