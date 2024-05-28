package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Fruit extends Entity{

	private int m_direction;
	public boolean m_collision;
	private String fruitType; 
	private boolean isVisible;


	public Fruit(GamePanel a_gp, KeyHandler a_keyH, int y, String fruitType) {
		super(a_gp, a_keyH);
		this.fruitType = fruitType;
		setDefaultValues(y);
		getFruitImage();
		m_collision = true;
		isVisible = true;

	}

	private void getFruitImage() {
		try {
			String imagePath = "/tiles/" + fruitType.toLowerCase() + ".png";
			m_idleImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDefaultValues(int y) {
		switch (fruitType) {
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
		m_y = y;
		m_speed = 2;
		m_direction = 1;

	}

	@Override
	public void draw(Graphics2D a_g2) {
		if (isVisible) {
			BufferedImage l_image = m_idleImage;
			a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
		}
	}
	
	public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
