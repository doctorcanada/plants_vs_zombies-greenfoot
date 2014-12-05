import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class to define a bucket-wearing "waiting zombie" that appears
 * at the beginning of the stage
 * 
 * TODO: Take redundant/common code from this class and move it to
 * the ZombieWaiting superclass
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class BucketZombieWaiting extends ZombieWaiting
{
    private GreenfootImage[] imgFrames; // <-- should this be part of the superclass?
    
    private int numFrames = 31; // <-- make this a constant?
    
    private int currentImgNum;
    private int cycleCount;
    
    /**
     * Initialize stationary zombie object
     */
    public BucketZombieWaiting()
    {
        // setup array of images
        imgFrames = new GreenfootImage[ numFrames ];
        
        // initialize current image animation frame to a random index
        currentImgNum = Greenfoot.getRandomNumber( 31 );
        
        // initialize cycle count 
        cycleCount = 1;
        
        // initialize animation frames for this object
        for ( int i = 0 ; i < imgFrames.length; i++ )
        {
            imgFrames[ i ] = new GreenfootImage("bucketZombieWaiting" + i  + ".png");
            
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
        }
        
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
