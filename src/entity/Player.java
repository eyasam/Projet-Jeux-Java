package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import main.GamePanel;
import main.KeyHandler;

/**
 * D�fintition du comportement d'un joueur
 *
 */
public class Player extends Entity{


	private boolean sauter= false;
	private boolean tomber = false;
	private double gravity = 0.5;
	private double m_chute = 0.0;;
	private boolean m_visage;
	private int m_vie = 100; 		



	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		super(a_gp, a_keyH);
		this.setDefaultValues();
		this.getPlayerImage();
	}

	/**
	 * Initialisation des donn�es membres avec des valeurs par d�faut
	 */
	protected void setDefaultValues() {
		m_x = 40;
		m_y = 100;
		m_speed =2;
		sauter = false;
		tomber = true; 
		m_chute=0;
		m_visage = false;
	}

	/**
	 * Récuperation de l'image du personnage
	 */
	public void getPlayerImage() {
		//gestion des expections 
		try {
			m_idleImage = ImageIO.read(getClass().getResource("/player/klipartz.png"));
			m_reverseImage = ImageIO.read(getClass().getResource("/player/klipartzrev.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mise à jour des données du joueur
	 * en verifiant que le dép ne sort pas de l'ecran 
	 */
	@Override
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
			m_visage = true;

		}

		int newX_D=m_x+m_speed;
		if (m_keyH.m_droite && (newX_D<=limit_X) && (!isObstacle(newX_D+m_gp.TILE_SIZE,m_y))) {
			m_x+= m_speed;
			m_visage = false;

		}

		if (m_keyH.m_haut && !sauter && !tomber) {
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


		if (m_y > limit_Y) {
			m_y = limit_Y;
			tomber = false;
		} else if (isObstacle(m_x, m_y + m_gp.TILE_SIZE)) {
			tomber = false;
		} else if (isOnPlatform(m_gp.m_platform)) {
			tomber = false;
		} else {
			tomber = true;
		}



		if (restart_fall() ) {
			decrementVie();
			setDefaultValues();
			if (m_vie <= 0) {
				GameOverDialog();
				System.out.print("GAME OVER");
			}
		}
		
		for (Enemy e:m_gp.enemyList) {
	        if (checkCollision(e)) {
	            decrementVie(); 
	            setDefaultValues(); 
	            break; 
	        }}

	}



	private void decrementVie() {
		m_vie -= 25;
		if (m_vie < 0) {
			m_vie = 0;
		}
		if (m_vie == 0) {
			GameOverDialog(); 
		}
	}


	private boolean isOnPlatform(Platform p) {
		if (m_x < p.m_x + m_gp.TILE_SIZE * 3 && m_x + m_gp.TILE_SIZE > p.m_x &&
				m_y + m_gp.TILE_SIZE <= p.m_y && m_y + m_gp.TILE_SIZE + m_chute >= p.m_y) {
			m_y = p.m_y - m_gp.TILE_SIZE;
			return true;

		}
		return false;
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
	@Override
	public void draw(Graphics2D a_g2) {
		// r�cup�re l'image du joueur
		BufferedImage l_image = m_idleImage;
		if (m_visage) {
			l_image= m_reverseImage;
		} else {
			l_image= m_idleImage;
		}

		a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);

		//bar
		int largeur = 100;
		int hauteur = 10;
		int vie_restant = (int) ((double) m_vie/100 * largeur);

		if(m_vie<25) {
			a_g2.setColor(Color.RED);
		}else if(m_vie<50) {
			a_g2.setColor(Color.ORANGE);
		}else{
			a_g2.setColor(Color.GREEN);
		}

		a_g2.fillRect(m_x,m_y-20, vie_restant,hauteur);

		//background
		a_g2.setColor(Color.BLACK);
		a_g2.drawRect(m_x,m_y-20,largeur,hauteur); 

		//pourcentage
		a_g2.setColor(Color.WHITE);
		a_g2.drawString(m_vie + "%", m_x + (largeur/2)-10, m_y-25);
	}


	private void GameOverDialog() {
		int option = JOptionPane.showOptionDialog(null, 
				"GAME OVER", 
				"Game Over", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				new String[]{"Restart", "Exit"}, 
				"Restart");
		if (option == JOptionPane.YES_OPTION) {
			m_vie=100;
			setDefaultValues();
		} else {
			System.exit(0);
		}
	}




	private boolean restart_fall() {
		int tileX = m_x / m_gp.TILE_SIZE;
		int tileY = m_y / m_gp.TILE_SIZE;

		return (m_gp.get_tileM().getTileNum(tileX, tileY) == 2)||(m_gp.get_tileM().getTileNum(tileX, tileY) == 11);
	}

	public void incrementVie(int x) {
		m_vie+=x;

		if (m_vie > 100) {
			m_vie=100;
			}
	}

	public boolean checkCollision(Enemy e) {

	    int x = Math.abs(m_x - e.getM_x());
	    int y = Math.abs(m_y - e.getM_y());
	    
	    int tolerence=15;
	    
	    return (x < tolerence && y < tolerence);
	}


}
