import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class definition for a SpawnedSun object
 * (see Sun superclass)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class SpawnedSun extends Sun
{
    private int lifeLeft = 300;
    
    private int cycleCount = 1;
    private GreenfootImage[] imgFrames = new GreenfootImage[10];
    private double[] xShift = new double[10];
    private double[] yShift = new double[10];
    
    private double xInitial;
    private double yInitial;
    
    private Sunflower spawningSunflower;
    
    /*
    public SpawnedSun( double x, double y )
    {
        // call the no-argument constructor (defined below)
        // (this avoids writing duplicated code)
        this();
        xInitial = x;
        yInitial = y;
        setLocation( x, y );
    }
    */

    /**
     * Constructor for spawnedSun
     * TODO: There's probably too much going on here for a constructor
     * 
     * @param sunflower     which sunflower object is spawning the sun object
     */
    public SpawnedSun( Sunflower sunflower )
    {
        spawningSunflower = sunflower;
        
        for ( int imgIndex = 0; imgIndex < 9 ; imgIndex++ )
        {
            imgFrames[imgIndex] = new GreenfootImage("sun.png");
            
            // compute the x- and y- location shifts 
            // (used for the initial animation when the sun is "spawned" by the sunflower)
            double angleAtImgIndex = (-1)*( Math.PI / 180 )*( 120 + 18*(imgIndex) );
            xShift[imgIndex] = (0.8) * (1 + imgIndex) * Math.cos( angleAtImgIndex );
            yShift[imgIndex] = (0.8) * (1 + imgIndex) * Math.sin( angleAtImgIndex );
            
            // compute the scale for this image frame 
            // (for simplicity, it's computed as a linear function of the imgIndex value!)
            int imgScaleAsInt = (int)(4 + imgIndex*10);
            
            // rescale the image to the desired height and width
            imgFrames[imgIndex].scale( imgScaleAsInt, imgScaleAsInt );
        }
        
        // last animation frame will just be the original image
        // (I did it this way because the original image is 94 x 93 pixels, as
        //  opposed to having the same width and height)
        imgFrames[9] = new GreenfootImage("sun.png");
        
        setImage( imgFrames[0] );
        super.stop();
        
    } // end spawnedSun constructor
    
    /**
     * Act - do whatever the SpawnedSun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        xInitial = getX();
        yInitial = getY();
        
        spawningSunflower.updateCanSpawn( false );
        
        // "spawning" animation for first 10 cycles through the act() method 
        if ( cycleCount < 10 ) 
        {
            setImage( imgFrames[ cycleCount ] );
            
            setLocation( (int)(xInitial + xShift[cycleCount]), (int)(yInitial + yShift[cycleCount]) );
            cycleCount++;
        } // end if
        
        // rotate by 1 degree to give it a little bit of an animation
        // (actual game animation frames are more complicated -- this will do for now)
        setRotation( getRotation() + 1 );
        
        // Add your action code here.
        if ( Greenfoot.mouseClicked( this ) || (isMovingToTray == true) )
        {
            super.moveToTray();
            spawningSunflower.updateCanSpawn( true );
        } else {
            // if not clicked, keep going with the initial movement vector (i.e., vertical down)
            move();
            
            lifeLeft--;
            if ( lifeLeft <= 25 )
            {
                this.getImage().setTransparency( Math.max( 0, 10 * lifeLeft ) );
            }
            else if ( lifeLeft == 0 )
            {
                getWorld().removeObject( this );
            } // end inner if/else
            
        } // end outer if/else
        
    } // end method act
    
} // end class SpawnedSun
