import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines the starburst that is displayed during the animation
 * sequence at the end of the stage
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Starburst extends Actor
{
    public void setAlpha( int alpha )
    {
        //this.alpha = alpha;
        getImage().setTransparency( alpha );
        
    } // end method setAlpha
    
} // end class Starburst
    
