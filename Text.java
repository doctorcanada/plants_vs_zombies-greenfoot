import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;

/**
 * This class provides objects that show text on the screen; in the current
 * demo, this is really only used to display the number of "sun points" that
 * the player has.
 * 
 * NOTE: This was adapted from the Text class as shown 
 * in the book-examples/ch10/marbles scenario
 * 
 * @author: bcanada
 * @version: 2014.01.17
 * 
 */
public class Text extends Actor
{
    /**
     * One-arg constructor to specify the length of the field, 
     * with text added later
     * 
     * @param length    the length of the text field (number of characters)
     */
    public Text(int length)
    {
        setImage(new GreenfootImage(length * 12, 16));
        
    } // end one-arg overloaded Text constructor (with int parameter)

    /**
     * One-arg constructor that calls the ABOVE constructor and
     * then immediately sets the text to the specified String paramter
     * 
     * @param text    a String containing the text to be displayed in the object
     */
    public Text(String text)
    {
        // call the other constructor
        this (text.length());
        setText(text);
        
    } // end one-arg overloaded Text constructor (with String parameter)

    /**
     * Set the text of an existing Text object to the specified 
     * String parameter
     * 
     * @param text    a String containing the text to be displayed in the object
     */
    public void setText(String text)
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text, 2, 12);

        /* calculate width of text in pixels */
        // FontRenderContext frc = ((Graphics2D)getImage().getAwtImage().getGraphics()).getFontRenderContext();
        // int textWidth = (int)image.getFont().getStringBounds(text, frc).getWidth();      
        
    } // end method setText
    
    /**
     * Adapt location to make placement left-justified. Uses the setLocation
     * method of the Actor superclass.
     */
    public void setLocation(int x, int y)
    {
        super.setLocation(x + getImage().getWidth() / 2, y);
        
    } // end method setLocation
    
} // end class Text
