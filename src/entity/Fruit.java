package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Fruit extends Entity{

	private String type; 
	private boolean isVisible;


	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Fruit(GamePanel a_gp, KeyHandler a_keyH, int y, String fruitType) {
		super(a_gp, a_keyH);
		this.type = fruitType;
		setDefaultValues(y);
		getFruitImage();
	}

	private void getFruitImage() {
		try {
			String imagePath = "/tiles/" + type + ".png";
			m_idleImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDefaultValues(int y) {
		isVisible=true;
		switch (type) {
		case "Strawberry":
			m_x = 270;
			break;
		case "Pastheque":
			m_x = 500;
			break;
		case "Orange":
			m_x = 175;
			break;
		}
		m_y=y;

	}

	@Override
	public void draw(Graphics2D a_g2) {
		if (isVisible) {
			BufferedImage l_image = m_idleImage;
			a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
		}
	}
	


}
