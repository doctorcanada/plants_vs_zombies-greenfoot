import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Class definition for Peashooter objects; a Peashooter bounces back and forth (animates),
 * and if a Zombie is visible in the same row, will start shooting peas
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Peashooter extends Plant
{
    /*
     * Declare instance variables for each peashooter object
     */
    private GreenfootImage[] imgFrames; 
    private int numFrames = 23; 
    
    private int currentImgNum;
    private int cycleCount;
    
    private int x;
    private int y;
    
    private boolean startShooting;
    
    /**
     * Initialize peashooter object
     */
    public Peashooter()
    {
        // setup array of images
        imgFrames = new GreenfootImage[ numFrames ];
        
        // initialize current image animation frame number to zero
        currentImgNum = 0;
        
        // initialize cycle count  
        cycleCount = 1;
        
        // initialize animation frames for this object
        for ( int i = 0 ; i < imgFrames.length; i++ )
        {
            imgFrames[ i ] = new GreenfootImage("peashooter" + i  + ".png");
            
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
        if ( cycleCount % 5 == 0 ) // i.e., "if the current value of cycleCount is divisible by 5..."
        {
            updateImage();
            cycleCount = 1;
        }
        else // otherwise, increment the value of cycleCount by 1
        {
            cycleCount++;
        } // end if-else
        
        checkForZombiesInSameRow();
        
    } // end method act  
    
    /**
     * Updates the image for the next animation frame in sequence
     */
    public void updateImage()
    {
        setImage( imgFrames[ currentImgNum ] );
        
        // For correct animation, only shoot the pea at frame 19
        if ( currentImgNum == 19 && startShooting == true )
        {
            shootPea();
        }
        
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
     * Defines the logic for checking to see if a Zombie is in the same
     * row as the Peashooter; if so, the Peashooter will start shooting peas
     * once per full cycle of animation 
     */
    public void checkForZombiesInSameRow()
    {
        startShooting = false;
        
        this.x = getX();
        this.y = getY();
        
        List<Zombie> zombiesInWorld = getWorld().getObjects( Zombie.class );
        
        for ( Zombie zombie : zombiesInWorld )
        {
            // TODO: need a better check for zombie in same row
            //       ...maybe need to get indexOf this zombie
            if ( ( zombie.getX() < getWorld().getWidth() - 1 ) 
                 &&
                 ( Math.abs( zombie.getY() - this.y ) <= 25 ) )
            {
                startShooting = true;
            } // end if
            
        } // end for
        
    } // end method checkForZombiesInSameRow
    
    /**
     * Method for spawning a new Pea object to be "shot" at a Zombie in the same row
     * as the current Peashooter object
     */
    public void shootPea()
    {
        Pea pea = new Pea();
        int xOffset = 40; // so that it appears to be shooting from the peashooter's mouth
        getWorld().addObject( pea, getX() + xOffset, getY() - 5 );
        
    } // end method shootPea
    
} // end class Peashooter
