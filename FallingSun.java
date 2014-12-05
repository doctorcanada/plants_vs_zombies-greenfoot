import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class definition for a FallingSun object
 * (see Sun superclass)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class FallingSun extends Sun
{
    private int lifeLeft = 150;
    
    /**
     * Constructor for initializing a FallingSun object at the specified coordinates
     * 
     * @param x     column pixel for starting position
     * @param y     row pixel for starting position
     */
    public FallingSun( double x, double y )
    {
        setLocation( x, y );
        super.stop();
        super.addForce( new Vector( 0.0, 1.5 ) );
        
    } // end 2-arg constructor for FallingSun

    /**
     * No-arg constructor for FallingSun
     * TODO: Does anything actually call this? Better check...
     * 
     */
    public FallingSun()
    {
        super.stop();
        super.addForce( new Vector( 0.0, 1.5 ) );
        
    } // end no-arg constructor for FallingSun
    
    /**
     * Act - do whatever the FallingSun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // rotate by 1 degree to give it a little bit of an animation
        setRotation( getRotation() + 1 );
        
        // Add your action code here.
        if ( Greenfoot.mouseClicked( this ) || (isMovingToTray == true) )
        {
            super.moveToTray(); 
        } 
        else 
        {
            // if not clicked, keep going with the initial movement vector (i.e., vertical down)
            if ( getY() < 500 ) 
            {
                move();
            }
            else 
            {
                lifeLeft--;
                if ( lifeLeft <= 25 )
                {
                    this.getImage().setTransparency( Math.max( 0, 10 * lifeLeft ) );
                }
                else if ( lifeLeft == 0 )
                {
                    getWorld().removeObject( this );
                } // end twice-nested if/else
                
            } // end nested if/else
            
        } // end outer if/else
        
    } // end method act 
    
} // end class FallingSun
