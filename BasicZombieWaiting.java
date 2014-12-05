import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class to define a basic (non-bucket-wearing) "waiting zombie" that appears
 * at the beginning of the stage
 * 
 * TODO: Take redundant/common code from this class and move it to
 * the ZombieWaiting superclass
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class BasicZombieWaiting extends ZombieWaiting
{
    private GreenfootImage[] imgFrames; // <-- should this be part of the superclass?
    
    private int numFrames = 16; // <-- make this a constant?
    
    private int currentImgNum;
    private int cycleCount;
    
    /**
     * Initialize stationary zombie object
     */
    public BasicZombieWaiting()
    {
        // setup array of images
        imgFrames = new GreenfootImage[ numFrames ];
        
        // initialize current image animation frame number to zero
        currentImgNum = Greenfoot.getRandomNumber( 16 );
        
        // initialize cycle count  (consider making this a constant?)
        cycleCount = 1;
        
        // initialize animation frames for this object
        for ( int i = 0 ; i < imgFrames.length; i++ )
        {
            imgFrames[ i ] = new GreenfootImage("basicZombieWaiting" + i  + ".png");
            
        } // end for
        
    } // end no-arg constructor
    
    /**
     * Act - do whatever the Peashooter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        /* 
         * Assuming standard execution speed, update new animation frame
         * every fifth cycle through the object's act() method
         */
        if ( cycleCount % 5 == 0 ) {
            updateImage();
            cycleCount = 1;
        }
        else
        {
            cycleCount++;
        } // end if/else
        
    } // end method act  
    
    /**
     * Updates the image for the next pass through the game loop
     */
    public void updateImage()
    {
        setImage( imgFrames[ currentImgNum ] );

        if ( currentImgNum == (numFrames - 1) ) 
        {
            currentImgNum = 0;
        }
        else
        {
            currentImgNum++;
        } // end if/else
        
    } // end method updateImage  
    
} // end class BasicZombieWaiting
