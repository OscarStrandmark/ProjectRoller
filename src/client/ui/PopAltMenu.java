package client.ui;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import client.BoardModel;
/**
 * Class that handles creating the context-menu for icons.
 * 
 * @author Patrik Skuza
 */
public class PopAltMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;

	        // Adds an alternative for "OpenValue"-menu and "Delete Icon" in the right click menu
	        // of an icon.
		public PopAltMenu(Component c, BoardModel model) {
			JMenuItem altMenuValue = new JMenuItem(new OpenValue(c,model));
			add(altMenuValue);
			
			JMenuItem altMenuDelete = new JMenuItem(new DeleteIcon(c,model));
			add(altMenuDelete);

		}
	}
