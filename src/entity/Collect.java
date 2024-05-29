package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Collect extends Entity{

	private String type; 
	private boolean isVisible;


	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Collect(GamePanel a_gp, KeyHandler a_keyH, int x,int y, String fruitType) {
		super(a_gp, a_keyH);
		this.m_x=x;
		this.m_y=y;
		this.type = fruitType;
		isVisible=true;
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

	


	@Override
	public void draw(Graphics2D a_g2) {
		if (isVisible) {
			BufferedImage l_image = m_idleImage;
			if (type=="Bouteille") {
			a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE*2, m_gp.TILE_SIZE*2, null);
			}else {
			a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);

			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	


}
