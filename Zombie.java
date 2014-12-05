import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Defines what any (walking) Zombie can do -- for specific
 * zombie behaviors, see subclasses BasicZombie, BucketZombie,
 * and ConeheadZombie
 * 
 * TODO: Still need to include graphics and code to enable
 *       a Zombie to eat the plants that it comes in contact with
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Zombie extends SmoothMover
{
    // Note: I use protected fields here so that 
    // they can be used directly by all subclasses of Zombie
    protected int hitPoints;
    
    protected boolean isDying;
    
    protected int opacity = 255;
    protected int rotation = 0;
    protected int width;
    protected int height;
    
    /**
     * Initialize the zombie, whatever type it is
     */
    public Zombie()
    {
        super( new Vector( 180, 0.1 ) );
        isDying = false; 
        
    } // end constructor for Zombie
    
    /**
     * Method for detecting whether a Pea hit the current Zombie
     */
    public void checkForHit()
    {
        //Actor hitPea = getOneIntersectingObject( Pea.class );
        List<Pea> peas = getObjectsInRange( 25, Pea.class );
        Pea hitPea = null;
        
        for ( Pea thisPea : peas )
        {
            hitPea = thisPea;
            System.out.println( "hitPea = " + hitPea );
        } // end for
        
        if ( hitPea != null )
        //if ( isTouching( Pea.class ) )
        {
            if ( !hitPea.getExplode() )
            {
                System.out.println( "I've been hit!" );
                
                if ( Greenfoot.getRandomNumber( 100 ) < 50 ) {
                    Greenfoot.playSound( "splat2.wav" );
                }
                else
                {
                    Greenfoot.playSound( "splat3.wav" );
                } // end twice-nested inner if
                
                //removeTouching( Pea.class );
                hitPea.setExplode( true );
                hitPoints--; // same as hitPoints = hitPoints - 1;
                
            } // end inner if
            
        } // end outer if
        
        if ( hitPoints == 0 )
        {
            //getWorld().removeObject( this );
            this.die();
        } // end if
        
    } // end
    
    /**
     * Method for initiating the "death sequence" for the current Zombie
     * (death animation, object removal, etc.)
     */
    public void die()
    {
        // set zombie death events in motion
        isDying = true;
    } // end method die
    
    /**
     * Check to see if the current Zombie is dying
     * 
     * @return status of whether the current Zombie is in its "death sequence" or not
     */
    public boolean getIsDying()
    {
        return isDying;
    } // end method getIsDying
    
    /**
     * Defines what happens during the Zombie's "death sequence"
     */
    public void animateDeath()
    {    
        List<Zombie> zombiesRemaining = getWorld().getObjects( Zombie.class );
        
        if ( rotation < 90 )
        {
            rotation += 3;
            setRotation( Math.min( 90, rotation ) );
            setLocation( getExactX() + 2.5 , getExactY() + 2.5 );
        } // end if

        opacity -= 5;

        if ( opacity <= 0 )
        {
            // check to see if this is last zombie to die
            // if so, pop in the next stage's seed packet
            if ( zombiesRemaining.size() == 1 )
            {
                System.out.println( "last zombie down! adding new seed packet..." );          
                getWorld().addObject( new SeedPacketStageEnd(), this.getX(), this.getY() );
            } // end inner if
            
            getWorld().removeObject( this );
            
        } // end outer if
        
        width = (int)( width * 0.95 );
        
        getImage().scale( Math.max( 20, width), height);
        getImage().setTransparency( Math.max( 0, opacity ) );

    } // end method animateDeath
    
} // end class Zombie
