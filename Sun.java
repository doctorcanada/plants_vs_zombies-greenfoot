import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class definition for a generic Sun object
 * (see specialized subclasses for SpawnedSun and FallingSun)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Sun extends SmoothMover
{
    protected boolean isMovingToTray;
    protected double deltaX, deltaY;
    
    public Sun()
    {
        super( new Vector( 0, 0.0 ) );
    } // end Sun constructor
    
    /**
     * Act - do whatever the Sun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here. 
    } // end method act 
    
    /**
     * Provides the logic for moving a clicked Sun object
     * into the scoreboard (i.e., the "tray")
     */
    public void moveToTray()
    {
        // compute the change in x
        deltaX = 50 - this.getExactX();
        deltaY = 40 - this.getExactY();
        
        super.stop();
        if ( Math.sqrt( Math.pow(deltaX,2) + Math.pow(deltaY,2) ) <= 25.0 )
        {
            // if small distance (<= 5px), then assume the sun has arrived at its destination 
            isMovingToTray = false; 
            //super.stop();
            
            // increment the # of sun points by 25
            ((Game)getWorld()).addSunPoints( 25 );
            Greenfoot.playSound( "points.wav" );
            getWorld().removeObject( this );
        }
        else
        {
            // borrowed the computation below from updatePolar() method of the Vector helper class
            int direction = (int) Math.toDegrees(Math.atan2( deltaY, deltaX ));
            super.addForce( new Vector( direction , 30.0 ) );
            isMovingToTray = true;
        } // end if/else
        
        // move the sun using whatever the current force vector is
        move();      
        
    } // end method moveToTray
    
} // end class Sun
