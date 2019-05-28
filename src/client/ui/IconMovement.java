package client.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.BoardModel;

/**
 * Listener-class used for movement of icons on the board.
 * 
 * @author Oscar Strandmark
 */
public class IconMovement implements MouseListener, MouseMotionListener {

		private JLabel c;
		private BoardModel model;
		
		public IconMovement(BoardModel model) {
			this.model = model;
			
		}
		
		public void mouseDragged(MouseEvent event) {
			event.getComponent().setLocation((event.getX() + event.getComponent().getX()), (event.getY() + event.getComponent().getY()));
		}

		public void mousePressed(MouseEvent event) {
			c = (JLabel) event.getComponent();
		}

		public void mouseReleased(MouseEvent event) {
			
			if(event.getButton() == 1 && c != null) {
				
				model.sendIconMove((ImageIcon)c.getIcon(), c.getX(), c.getY());
				c = null;
			}
			
			if(event.getButton() == 3) {
				PopAltMenu popMenu = new PopAltMenu(event.getComponent(),model);
				popMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
		
		public void mouseMoved(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
