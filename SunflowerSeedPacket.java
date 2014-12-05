import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines a SunflowerSeedPacket object. 
 * 
 * TODO: Obviously this shares much in common with the PeaShooterSeedPacket class,
 *       so in the future, I will move the redundant code into the parent class (SeedPacket)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class SunflowerSeedPacket extends SeedPacket
{
    /*
     * TODO: I'm thinking I need a "cost" variable here... currently
     * this is implemented elsewhere in the project
     */
    private GreenfootImage imgReady     = new GreenfootImage("SeedPacket_Sunflower.png");
    private GreenfootImage imgSelected  = new GreenfootImage("SeedPacket_Sunflower_selected.png");
    
    /**
     * When the seed packet is created, it's given the image without the yellow selection border
     * ("ready" here means "ready to be selected" via a mouse click -- see the act() method)
     */
    public SunflowerSeedPacket()
    {
        setImage( imgReady );
    } // end SunflowerSeedPacket constructor 
    
    /**
     * Act - do whatever the SunflowerSeedPacket wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * 
     * When the user clicks on the object, the "click status" is set to true,
     * and the image is updated to include a yellow selection border around the
     * image perimeter (currently, the border is part of the image itself)
     */
    public void act() 
    {
        // handle mouse click events
        /*
         * TODO: Pretty sure I need to fix the seedpacket selection code
         *       to make "un-doing" a selection more obvious to the user
         */ 
        if ( Greenfoot.mouseClicked( this ) ) 
        {
            // Use explicit CASTING to create a reference to my World subclass (i.e., Game)
            Game myGame = (Game)getWorld();
            
            System.out.println( "seed packet was clicked");
            
            myGame.setSunflowerSeedPacketClickStatus( true );
            
            setImage( imgSelected );
            
        } // end if
        
    } // end method act
    
    /**
     * resets the image to the "un-selected" version of the image
     */
    public void resetImage()
    {
        setImage( imgReady );
        
    } // end method resetImage
    
} // end class SunflowerSeedPacket
