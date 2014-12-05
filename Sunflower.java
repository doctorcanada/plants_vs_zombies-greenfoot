import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class definition for Sunflower objects; a Sunflower bounces back and forth (animates),
 * and will occasionally spawn a Sun object
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Sunflower extends Plant
{
    /*
     * Declare instance variables for each sunflower object
     */
    private GreenfootImage[] imgFrames; 
    private int numFrames = 53; 
    
    private int currentImgNum;
    private int cycleCount;
    
    private int x;
    private int y;
    
    private boolean canSpawn;

    /**
     * Initialize sunflower object
     */
    public Sunflower()
    {
        // setup array of images
        imgFrames = new GreenfootImage[ numFrames ];
        
        // initialize current image animation frame number to zero
        currentImgNum = 0;
        
        // initialize cycle count  
        cycleCount = 1;
        
        // set status of ability to spawn sun
        // (if a sun has been spawned, then we can't spawn another sun
        //  until it has been clicked or otherwise disappears)
        canSpawn = true;
        
        // initialize animation frames for this object
        for ( int i = 0 ; i < imgFrames.length; i++ )
        {
            imgFrames[ i ] = new GreenfootImage("sunflower" + i  + ".png");
            
        } // end for
        
    } // end no-arg constructor
    
    /**
     * Act - do whatever the Sunflower wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        /* 
         * Assuming standard execution speed, update new animation frame
         * every fifth cycle through the object's act() method
         */
        if ( cycleCount % 3 == 0 ) // i.e., "if the current value of cycleCount is divisible by 5..."
        {
            updateImage();
            cycleCount++;
        }
        else // otherwise, increment the value of cycleCount by 1
        {
            cycleCount++;
        } // end if-else
        
        if ( cycleCount % 500 == 0 && (canSpawn == true) )
        {
            spawnSun( this );
            //canSpawn = false; 
        }
        
    } // end method act  
    
    /**
     * Updates the image for the next animation frame in sequence
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
    
    /**
     * Method for spawning a SpawnedSun object for the given sunflower
     * 
     * @param sunflower   the sunflower that will spawn the sun
     */
    public void spawnSun( Sunflower sunflower )
    {
        getWorld().addObject( new SpawnedSun( sunflower ), getX(), getY() );
        
    } // end method spawnSun
    
    /**
     * Method for updating whether or not the sunflower has "recharged" enough
     * to spawn a new SpawnedSun object
     */
    public void updateCanSpawn( boolean spawnStatus )
    {
        canSpawn = spawnStatus;
        
    } // end method updateCanSpawn
    
} // end class Sunflower
