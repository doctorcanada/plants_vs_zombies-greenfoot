import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defines the attributes and behaviors of a bucket-wearing walking Zombie
 * 
 * TODO: I should "remove" the bucket (convert to basic zombie) after a certain number of hits, 
 *       and use a different sound for the collision
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class BucketZombie extends Zombie
{
    /*
     * Declare instance variables 
     */
    private GreenfootImage[] imgFrames; 
    private int numFrames = 51; 
    
    private int currentImgNum;
    private int cycleCount;
    
    /**
     * Initialize BucketZombie object
     */
    public BucketZombie()
    {
        // setup array of images
        imgFrames = new GreenfootImage[ numFrames ];
        
        // initialize current image animation frame number to randomly selected value
        currentImgNum = Greenfoot.getRandomNumber( numFrames );
        
        // initialize cycle count  
        cycleCount = 1;
        
        hitPoints = 15;
        
        // initialize animation frames for this object
        for ( int i = 0 ; i < imgFrames.length; i++ )
        {
            imgFrames[ i ] = new GreenfootImage("bucketZombie" + i  + ".png");
            
        } // end for
             
        width = imgFrames[ 0 ].getWidth();
        height = imgFrames[ 0 ].getHeight();
    }
    
    /**
     * Act - do whatever the BasicZombie wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        /* 
         * Assuming standard execution speed, update new animation frame
         * every 3rd cycle through the object's act() method
         */
        if ( cycleCount % 3 == 0 ) // i.e., "if the current value of cycleCount is divisible by 3..."
        {
            updateImage();
            cycleCount = 1;
        }
        else // otherwise, increment the value of cycleCount by 1
        {
            cycleCount++;
        } // end if-else
        
        checkForHit();
       
        if ( !isDying )
        {
            move();
        }
        else // if dying, then...
        {
            super.animateDeath();
        } // end if/else
        
    } // end method act

    /**
     * Updates the object's image to the next animation frame in sequence
     */
    public void updateImage()
    {
        setImage( imgFrames[ currentImgNum ] );
        
        // Reset currentImgNum for the next animation cycle
        if ( currentImgNum == (numFrames - 1) ) 
        {
            currentImgNum = 0;
        }
        else
        {
            currentImgNum++;
        } // end if-else
        
    } // end method updateImage  
    
} // end class BucketZombie
