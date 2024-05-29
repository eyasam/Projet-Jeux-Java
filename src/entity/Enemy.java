package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Enemy extends Entity{

	private int m_direction;
	
	public Enemy(GamePanel gp, KeyHandler keyH,int x,int y) {
		super(gp, keyH);
		//m_x = 300;
		m_x=x;
		m_y=y;
		//m_y = 20;
        m_speed=2;
        m_direction=0; 
        getEnemyImage()	;
        }

	private void getEnemyImage() {
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/player/enemy1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update() {
	    m_y += m_speed * m_direction;

        if (m_y <= 20) {
            m_direction = 1; // Déplacement vers la droite
        } else if (m_y >= 100) { // Ajustez 300 selon les limites de votre carte
            m_direction = -1; // Déplacement vers la gauche

        }  
        
        
	}

	@Override
	public void draw(Graphics2D a_g2) {
		BufferedImage l_image = m_idleImage;
		l_image= m_idleImage;
		
		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE*2, m_gp.TILE_SIZE*2, null);		
	}

}
