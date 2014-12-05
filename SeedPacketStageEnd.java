import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class defines the behavior of whatever seed packet shows up at
 * the end of each stage (for the current demo, this would be the "cherry bomb"
 * seed packet)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class SeedPacketStageEnd extends Actor
{
    public void setAlpha( int alpha )
    {
        //this.alpha = alpha;
        getImage().setTransparency( alpha );
        
    } // end method setAlpha
    
} // end class SeedPacketStageEnd
