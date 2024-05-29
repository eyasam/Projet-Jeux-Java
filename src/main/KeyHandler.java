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
		if (code ==90 || code ==38 || code ==32) { //Z ou espace ou fleche 
			m_haut= true; 
        }
        if (code ==83 || code ==40) { //S arriere ou fleche 
        	m_bas= true; 
        }
        if (code ==81 || code ==37) { //Q gauche ou fleche  <-
        	m_gauche= true; 
        }
        if (code ==68 || code ==39) { //D droite ou fleche ->
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
		if (code ==90 || code ==38 || code ==32) { //Z ou espace ou fleche
			m_haut= false; 
        }
        if (code ==83 || code ==40) { //S arriere ou fleche 
        	m_bas= false; 
        }
        if (code ==81 || code ==37) { //Q gauche ou fleche  <-
        	m_gauche= false; 
        }
        if (code ==68 || code ==39) { //D droite ou fleche ->
        	m_droite= false; 
        }
      
	}

}
