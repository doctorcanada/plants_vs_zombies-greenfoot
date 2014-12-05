import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines the alert (or tutorial) message that is displayed
 * at the bottom of the screen
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class AlertMessage extends Actor
{
    // TODO: There's probably a smarter way to implement this
    private String[] imgMessages = { "StartReady.png", 
                                     "StartSet.png", 
                                     "StartPlant.png", 
                                     "message_clickOnFallingSun.png",
                                     "message_yourHouse.png", 
                                     "ZombiesWon.png" };
    
    public AlertMessage( int messageIndex )
    {
        // TODO: Probably should rework this to avoid calling 
        //       methods from within the constructor, but doesn't
        //       seem to be throwing any exceptions as of yet
        setImgMessage( messageIndex );
        
    } // end constructor
    
    /**
     * Act - do whatever the AlertMessage wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    { 
    } 
    
    /**
     * Sets the message image for the given event 
     * 
     * @param messageIndex    the array index of the message to be displayed
     */
    public void setImgMessage( int messageIndex )
    {
        setImage( imgMessages[ messageIndex ] );
    } // end method setImgMessage
        
} // end class AlertMessage
