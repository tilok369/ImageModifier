/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemodifier;

/**
 *
 * @author Tilok
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class ImageUtility {
    
    private int IMG_WIDTH;
    private int IMG_HEIGHT;
    
    public ImageUtility()
    {
        
    }
    
    public void SetWidth(int width)
    {
        IMG_WIDTH = width;
    }
    
    public void SetHeight(int height)
    {
        IMG_HEIGHT = height;
    }
    
    public int GetWidth()
    {
        return IMG_WIDTH;
    }
    
    public int GetHeight()
    {
        return IMG_HEIGHT;
    }
    
    public BufferedImage GetResizeImage(BufferedImage originalImage)
    {
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	g.dispose();
 
	return resizedImage;
    }
    
    public BufferedImage GetBorderImage(BufferedImage image)
    {
        BufferedImage borderImage = new BufferedImage(image.getWidth()+6,image.getHeight()+6,BufferedImage.TYPE_INT_ARGB );
        
        Graphics graphic = borderImage.getGraphics();  
        graphic.setColor(Color.RED);
        graphic.fillRect(0,0,borderImage.getWidth(),borderImage.getHeight());
        graphic.drawImage(image,4,4,null);
        
        return borderImage;
    }
    
    public BufferedImage DrawLineOnImage(BufferedImage image, Stack<Line> lines)
    {
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));
        for(int i=0; i < lines.size(); i++)
        {
            Line l = lines.elementAt(i);
            g.drawLine(l.GetX1(), l.GetY1(), l.GetX2(), l.GetY2());
        }
        
        return image;
    }
    
    public boolean ValidRegion(int width, int height, int imgWidth, int imgHeight, int x, int y)
    {
        int boundX = (int)((width - imgWidth)/2);
        int boundY = (int)((height - imgHeight)/2);
        
        if((x >= boundX && x <= boundX + imgWidth) && (y >= boundY && y <= boundY + imgHeight))
            return true;
        else return false;
    }
    
    public void DrawArrow(Graphics2D g, int x1, int y1, int x2, int y2)
    {       
        double angle = Math.atan2((double)(y2 - y1), (double)(x2 - x1));
        int arrowHeight = 7;
        int halfWidth = 5;
        Point2D aroBase = new Point2D.Double(x2 - arrowHeight * Math.cos(angle), y2 - arrowHeight * Math.sin(angle));
        Point2D end1 = new Point2D.Double(aroBase.getX()-halfWidth*Math.cos(angle-Math.PI/2), aroBase.getY()-halfWidth*Math.sin(angle-Math.PI/2));
                  //locate one of the points, use angle-pi/2 to get the 
                  //angle perpendicular to the original line(which was 'angle')
                 
        Point2D end2 = new Point2D.Double(aroBase.getX()-halfWidth*Math.cos(angle+Math.PI/2), aroBase.getY()-halfWidth*Math.sin(angle+Math.PI/2));
        
        g.setStroke(new BasicStroke(2));
        g.drawLine((int)end1.getX(), (int)end1.getY(), x2, y2);
        g.drawLine((int)end2.getX(), (int)end2.getY(), x2, y2);
        //g.drawLine((int)end2.getX(), (int)end2.getY(), (int)end1.getX(), (int)end1.getY());
                  
    }
}
