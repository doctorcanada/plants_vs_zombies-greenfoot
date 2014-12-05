import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Demonstration of how to implement Pea as a subclass of SmoothMover. 
 * 
 * This is incomplete by design -- it intended only to help give you ideas 
 * and possible starting points for your own program!
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Pea extends SmoothMover
{
    private boolean exploding;
    private int explodeCycleCount;
    
    public Pea()
    {
        // a Vector object constructor has two input parameters: 
        // direction (i.e., angle) and length (magnitude). 
        super( new Vector( 0, 3.0 ) );
        exploding = false;
    } // end Pea constructor
    
    /**
     * Act - do whatever the Pea wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // When a pea is shot, it moves in the current direction based on
        // its current movement vector. 
        
        
        // this is used to override the default wrap-around behavior of SmoothMover objects
        /* 
         * NOTE: the SmoothMover's exactX field must have protected access if we want to
         * be able to use it as if it were a field of the Pea class. However, we typically define
         * all fields as private, so in order to avoid "breaking encapsulation," we access
         * the value of exactX by means of the public getExactX method defined in SmoothMover. 
         * 
         * (Please see the SmoothMover source code, and don't be afraid to experiment!)
         */
        if ( getExactX() >= getWorld().getWidth() - 5 ) 
        {
            getWorld().removeObject( this );
        } // end if
        
        // Still, because Pea is a subclass of SmoothMover, this is how the above if-statement 
        // could look if the SmoothMover's exactX field had protected access (instead of private):
        /*
         *   if( exactX >= getWorld().getWidth() - 5 ) 
         *   {
         *     getWorld().removeObject( this );
         *   }
         * 
         */
        
        if ( exploding )
        {
            setImage( "peaSplat.png" );
            explodeCycleCount--;
            
            if ( explodeCycleCount == 0 )
            {
                getWorld().removeObject( this );
            } // end inner if
        }
        else
        {
            move();
        } // end outer if/else
        
    }  // end method act
    
    /**
     * Set method for updating the exploding status as true or false
     * 
     * @param explodeStatus 
     */
    public void setExplode( boolean explodeStatus )
    {
        exploding = explodeStatus;
        if ( exploding ) 
        {
            explodeCycleCount = 10;
        }
    }
    
    /**
     * Get method for retrieving the exploding status for the given Pea
     * (TODO: Check to see if anything is actually calling this method)
     * 
     * @return the Pea object's exploding status
     */
    public boolean getExplode()
    {
        return exploding;
    } // end method getExplode()
    
} // end class Pea
