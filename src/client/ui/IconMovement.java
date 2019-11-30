package client.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.BoardModel;

/**
 * This class listens for mouse-input in terms of Icon Movement,
 * and creates movement based on where the left-click button of the 
 * mouse is released after dragging an icon.
 * @author Patrik Skuza
 * @author Oscar Strandmark
 */
public class IconMovement implements MouseListener, MouseMotionListener {

		private JLabel lblComponent; //Component to be moved
		private BoardModel model; //Model of the board
		
	        /**
		 * The constructor gets the BoardModel.
		 * @param model The BoardModel for icons, background etc.
		 */
		public IconMovement(BoardModel model) {
			this.model = model;
			
		}
		
	        // Method gets the x and y positions of the component (JLabel with an ImageIcon on top) while dragging it around the board.
		public void mouseDragged(MouseEvent event) {
			event.getComponent().setLocation((event.getX() + event.getComponent().getX()), (event.getY() + event.getComponent().getY()));
		}

	        // Gets the x and y positions of the component when mouse button is pressed.
		public void mousePressed(MouseEvent event) {
			lblComponent = (JLabel) event.getComponent();
		}

	        /**
		 * When the left-click button is released on the mouse while holding a component (c is not null),
		 * send the new x and y positions to the model.
		 */
		public void mouseReleased(MouseEvent event) {
			
			if(event.getButton() == 1 && lblComponent != null) {
				
				model.sendIconMove((ImageIcon)lblComponent.getIcon(), lblComponent.getX(), lblComponent.getY());
				lblComponent = null;
			}
			
			// If the mouse-button is a right-click on a component (Icon), show a PopMenu relative to where the
			// right-click was initialized.
			if(event.getButton() == 3) {
				PopAltMenu popMenu = new PopAltMenu(event.getComponent(),model);
				popMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
		
	        // Other methods that need to be implemented due to MouseListener, MouseMotionListener.
		public void mouseMoved(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
