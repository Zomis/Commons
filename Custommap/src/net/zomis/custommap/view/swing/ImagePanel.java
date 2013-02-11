package net.zomis.custommap.view.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.zomis.custommap.CustomFacade;
import net.zomis.custommap.view.general.ViewObject;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = -9204348375174860457L;
	private BufferedImage image;

	private ViewObject viewObj;
    public ImagePanel(ViewObject viewObj) {
    	this.viewObj = viewObj;
    }
    
    public void setImageFromResourceName(String name) {
    	try {
    		image = ImageIO.read(new File(name + ".png"));
//    		this.repaint();
    	}
    	catch (IOException ex) {
    		CustomFacade.getLog().e("Zomis", "File not found: " + name);
    	}
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
            g.drawImage(image, 0, 0, this.viewObj.getWidth(), this.viewObj.getHeight(), null);
//        	g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }

}
