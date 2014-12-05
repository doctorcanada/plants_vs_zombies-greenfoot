import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines what different screen images (like the Title screen, etc.) can do
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Screen extends Actor
{        
    /**
     * Screen object constructor -- assu,e that any new screen image will 
     * be transparent first (and will "fade in" later)
     * 
     * @param screenImage    the screen's updated image
     */
    public Screen( GreenfootImage screenImage )
    {
        // set up the title screen
        setScreen( screenImage );
        getImage().setTransparency( 0 );
    } // end screen constructor
    
    /**
     * Sets transparency (really opacity) for the current screen image
     * 
     * @param alpha    the percentage opacity level (0 = transparent, 100 = opaque)
     */
    public void setAlpha( int alpha )
    {
        //this.alpha = alpha;
        getImage().setTransparency( alpha );
        
    } // end method setAlpha
    
    /**
     * Sets the screen to the desired image
     * 
     * @param screenImage    the screen's updated image
     */
    public void setScreen( GreenfootImage screenImage )
    {
        setImage( screenImage );
        
    } // end method setScreen
    
} // end class Screen
