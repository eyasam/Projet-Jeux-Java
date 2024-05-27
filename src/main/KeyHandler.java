package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'�v�nements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{

	public boolean m_haut, m_bas, m_gauche, m_droite;
	
	/**
     * méthode appelée lorsqu une touche est pressée puis relâchée rapidement
     * @param e : event
     */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/**
     * méthode appelée lorsqu une touche du clavier est pressée
     * @param e : event
     */
	@Override
	public void keyPressed(KeyEvent e) {
		// r�cup�re le code du boutton appuye
		int code = e.getKeyCode();
		System.out.println(code);
		if (code ==90) { //Z
			m_haut= true; 
        }
        if (code ==83) { //S arriere
        	m_bas= true; 
        }
        if (code ==81) { //Q gauche
        	m_gauche= true; 
        }
        if (code ==68) { //D droite
        	m_droite= true; 
        }
       
	}

	
	/**
	 * méthode appelée lorsqu une touche du clavier est relâchée
	 * @param e : event 
	 */
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code =e.getKeyCode();
		if (code ==90) { //Z
			m_haut= false; 
        }
        if (code ==83) { //S
        	m_bas= false; 
        }
        if (code ==81) { //Q
        	m_gauche= false; 
        }
        if (code ==68) { //D
        	m_droite= false; 
        }
      
	}

}
