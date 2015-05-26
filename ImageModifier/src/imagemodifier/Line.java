/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemodifier;

/**
 *
 * @author Tilok
 */
public class Line {
    
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    
    public Line(int width, int height, int imgWidth, int imgHeight, int x1, int x2, int y1, int y2)
    {
        this.x1 = x1 - (int)((width - imgWidth)/2);
        this.x2 = x2 - (int)((width - imgWidth)/2);
        this.y1 = y1 - (int)((height - imgHeight)/2);
        this.y2 = y2 - (int)((height - imgHeight)/2);
    }
    
    public int GetX1()
    {
        return x1;
    }
    
    public int GetX2()
    {
        return x2;
    }
    
    public int GetY1()
    {
        return y1;
    }
    
    public int GetY2()
    {
        return y2;
    }
}
