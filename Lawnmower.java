import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Class definition for a Lawnmower object (for mowing down zombies that
 * have made it all the way to the house)
 * 
 * @author bcanada
 * @version 2014.01.17
 */
public class Lawnmower extends SmoothMover
{
    public boolean lawnmowerIsMoving;
    
    public Lawnmower()
    {
        super( new Vector( 0, 0.0 ) );
        lawnmowerIsMoving = false;
        
    } // end no-arg constructor for Lawnmower
    
    /**
     * Act - do whatever the Lawnmower wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        checkForZombie();
        move();
        
        if ( lawnmowerIsMoving )
        {
            super.addForce( new Vector( 0, 0.1 ) );
            
            if ( this.getX() > getWorld().getWidth() + 100 )
            {
                getWorld().removeObject( this );
                
            } // end inner if
            
        } // end outer if
        
    } // end method act
    
    /**
     * If a zombie comes into contact with a lawnmower, make
     * the zombie die, and "restart" the lawnmower acceleration
     * 
     * TODO: Come up with a better name for this method??
     */
    public void checkForZombie()
    {
        
        List<Zombie> zombies = getObjectsAtOffset( 2, -40, Zombie.class );

        if ( zombies.size() > 0 )
        {
            Zombie nearestZombie = zombies.get( 0 );
       
            if ( !nearestZombie.getIsDying() )
            {
                super.stop();
                Greenfoot.playSound( "limbs_pop_fixed.wav" );
                //getWorld().removeObject( nearestZombie );
                
                nearestZombie.die();
                
                if ( !lawnmowerIsMoving )
                {
                    Greenfoot.playSound( "lawnmower.wav" );
                    lawnmowerIsMoving = true;
                    
                } // end twice-nested inner if
                
            } // end nested inner if
            
        } // end outer if
        
    } // end method checkForZombie
    
} // end class Lawnmower
