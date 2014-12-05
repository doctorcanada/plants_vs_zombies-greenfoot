import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Plants vs Zombies "Greenfoot Edition" Demo, as shown on
 * 17-Jan-2014 to the Spring 2014 CSCI 146 at the
 * University of South Carolina Beaufort
 *
 * @author bcanada
 * @version 2014.01.17
 */
public class Game extends World
{
    // Declare instance variables 
    private static final int GAME_SPEED = 50;
    
    private int[][] sodSpaceCenterY; 
    private int[][] sodSpaceCenterX; 
    
    private int sodSpaceHeight = 100;     
    private int sodSpaceWidth = 80;  
    
    private int sodSpaceOffsetY = 120;
    private int sodSpaceOffsetX = 80;
    
    private boolean[][] sodSpaceOccupied;
    
    // create arrays (or arrayLists, as appropriate) for storing references to objects when needed
    private Lawnmower[] mower;
    
    // may not be necessary...
    private PeaShooterSeedPacket peaShooterSeedPacket;
    private SunflowerSeedPacket sunflowerSeedPacket;
    
    // fields for storing the "click status" of seed packets
    private boolean peaShooterSeedPacketClicked;
    private boolean sunflowerSeedPacketClicked;
    
    // for "ready, set, plant" and other messages
    private static final int ALERT_READY       = 0;
    private static final int ALERT_SET         = 1;
    private static final int ALERT_PLANT       = 2;
    private static final int ALERT_CLICK_SUN   = 3;
    private static final int ALERT_YOUR_HOUSE  = 4;
    private static final int ALERT_ZOMBIES_WIN = 5;
    private AlertMessage alertMessage;
    private boolean fallingSunAlertAlreadyDisplayed;
    private boolean fallingSunAlertRemoved;
    
    // keep track of this class's cycle count for event timing purposes
    private int stageCycleCount = 0;
    
    // fields needed for background scrolling
    private int bgOffset = 0;
    private int bgOffsetDelta = 2;
    
    // declare fields to store object references for various images
    private GreenfootImage frontYardImage   = new GreenfootImage( "yard_5rows.png" );
    private GreenfootImage blackScreen      = new GreenfootImage( "BlackScreen.png" );
    private GreenfootImage whiteScreen      = new GreenfootImage( "whiteScreen.png" );
    private GreenfootImage thankYouScreen   = new GreenfootImage( "thankYouScreen.png" );
    private GreenfootImage devIdentScreen1  = new GreenfootImage( "GreenPortzIdent.png" );
    private GreenfootImage devIdentScreen2  = new GreenfootImage( "PopCapIdent.png" );
    private GreenfootImage titleScreen      = new GreenfootImage( "PVZtitleScreen.png" );
    private GreenfootImage fullBGimage      = new GreenfootImage( "yard_full5rows.png" );
    
    // fields needed for title screen 
    private Screen screen;
    private int alpha = 0;
    
    // fields associated with game music
    private GreenfootSound titleScreenMusic = new GreenfootSound( "pvz_titleScreen.mp3" );
    private GreenfootSound frontYardMusic   = new GreenfootSound( "pvz_daytimeFrontyard.mp3" );
    private GreenfootSound scrollMusic      = new GreenfootSound( "pvz_menuTheme.mp3" );
    private GreenfootSound winMusic         = new GreenfootSound( "winmusic.wav" );
    private GreenfootSound loseMusic        = new GreenfootSound( "losemusic.wav" );
    private GreenfootSound zombieLaughMusic = new GreenfootSound( "losemusic.wav" );
    private GreenfootSound music;
    private int musicVolume = 100;
    private boolean musicIsPlaying;
    
    // fields associated with game sounds
    private boolean awoogaAlreadyPlayed = false;
    private GreenfootSound awooga = new GreenfootSound( "awooga.wav" ); 
    private GreenfootSound[] zombieGroan;
    
    
    // for game status -- eventually I'll need to change these to enums
    private static final int TITLE_SCREEN      = 0;
    private static final int BG_SCROLL_SETUP   = 1;
    private static final int BG_SCROLL_ANIMATE = 2;
    private static final int STAGE_SETUP       = 3; 
    private static final int READY_MESSAGE     = 4;
    private static final int BEGIN_PLAY        = 5;
    private int gameStatus;
    
    // current # of sun points
    private int sunPoints = 0;
    private Text sunPointsLabel;
    
    // fields for end of stage stuff
    private boolean starburstOnScreen = false;
    private Starburst starburst = new Starburst();
    
    /**
     * Constructor for objects of class Game.
     * 
     */
    public Game()
    {    
         // Create a new world with 800x600 cells with a cell size of 1x1 pixels. 
        super(800, 600, 1, false);
        
        // set game speed
        Greenfoot.setSpeed( GAME_SPEED );
        
        // we are now in title screen mode, so set gameStatus accordingly
        gameStatus = TITLE_SCREEN; 
        screen = new Screen( blackScreen );
        addObject( screen, 400, 300 );

        setPaintOrder ( SeedPacketStageEnd.class,
                        Starburst.class,   
                        Screen.class,       
                        Sun.class, 
                        AlertMessage.class,
                        Pea.class, 
                        Zombie.class, 
                        Plant.class, 
                        Text.class, 
                        SeedPacket.class,
                        SeedPacketTray.class );
        
        // create 2-D arrays to keep track of the x- and y-
        // coordinates of each sodSpace's center point
        sodSpaceCenterX = new int[5][9];
        sodSpaceCenterY = new int[5][9];
        
        // flag for whether a space on the board is occupied (by a plant)
        sodSpaceOccupied = new boolean[5][9];
        
        // create array of five lawnmowers
        mower = new Lawnmower[5];
        
        // instantiate new lawnmower objects 
        // (this isn't required for every object we want to place into the world, but doing it 
        //  this way enables us to better illustrate object composition, plus it may give us
        //  somewhat better control over each object that is placed in the world)
        mower[0] = new Lawnmower();
        mower[1] = new Lawnmower();
        mower[2] = new Lawnmower();
        mower[3] = new Lawnmower();
        mower[4] = new Lawnmower();
        
        // create composition ("has-a") reference to the seedpacket tray and the seedpacket objects
        peaShooterSeedPacket = new PeaShooterSeedPacket();
        sunflowerSeedPacket = new SunflowerSeedPacket();
        
        // initialize array of zombie groans (as strings that will be arguments to the playSound() method)
        zombieGroan = new GreenfootSound[13];
        
        zombieGroan[0]  = new GreenfootSound( "groan.wav" );
        zombieGroan[1]  = new GreenfootSound( "groan2.wav" );
        zombieGroan[2]  = new GreenfootSound( "groan3.wav" );
        zombieGroan[3]  = new GreenfootSound( "groan4.wav" );
        zombieGroan[4]  = new GreenfootSound( "groan5.wav" );
        zombieGroan[5]  = new GreenfootSound( "lowgroan.wav" );
        zombieGroan[6]  = new GreenfootSound( "lowgroan2.wav" );
        zombieGroan[7]  = new GreenfootSound( "sukhbir.wav" );
        zombieGroan[8]  = new GreenfootSound( "sukhbir2.wav" );
        zombieGroan[9]  = new GreenfootSound( "sukhbir3.wav" );
        zombieGroan[10] = new GreenfootSound( "sukhbir4.wav" );
        zombieGroan[11] = new GreenfootSound( "sukhbir5.wav" );
        zombieGroan[12] = new GreenfootSound( "sukhbir6.wav" );
        
        /* 
         * We can use a nested for-loop to initialize the 
         * 2-D array of sodSpace coordinates 
         */
        for ( int i = 0 ; i < 5 ; i++ ) // The outer loop iterates through the rows
        {
            for ( int j = 0 ; j < 9 ; j++ ) // And the inner loop iterates through the columns of the current row
            {
                // Note the Offset values -- Why do we use them? Think about this carefully!
                sodSpaceCenterX[i][j] = sodSpaceOffsetX + ( sodSpaceWidth * j );
                sodSpaceCenterY[i][j] = sodSpaceOffsetY + ( sodSpaceHeight * i );
                
            } // end inner for loop
        } // end outer for loop
        
    }
    
    /**
     * Define act() method to provide a demonstration of how to implement
     * mouse clicking in your program. Use this for inspiration and ideas only!
     */
    public void act() 
    {
        stageCycleCount++;         
        /*
         * TODO: Really should make this a switch statement eventually 
         */
        if ( gameStatus == TITLE_SCREEN ) 
        {
            displayTitleScreen();
        }
        else if ( gameStatus == BG_SCROLL_SETUP )
        {
            bgScrollSetup();
        }        
        else if ( gameStatus == BG_SCROLL_ANIMATE )
        {
            bgScrollAnimate();
        }
        else if ( gameStatus == STAGE_SETUP ) 
        {
            setUpStage();
            gameStatus = READY_MESSAGE;
            // reset stageCycleCount for upcoming stage
            stageCycleCount = 0;            
        }
        else if ( gameStatus == READY_MESSAGE )
        {
            startStage();
            sunPoints = 0;
        }
        else if ( gameStatus == BEGIN_PLAY ) 
        {
            
            if ( !musicIsPlaying ) {
                music.playLoop();
                musicIsPlaying = true; // TODO: really should put all of this in a "play" method
            }
            
            /*
             * NOTE! If one seed packet is clicked (and therefore active),
             * you will need to "un-click"/de-activate any other active seed packets
             */
            checkForStageCompletion();
            
            checkForZombiesWin();
            
            // TODO: right now "starburstOnScreen" is the surrogate flag for
            //       whether the stage is complete (true) or incomplete (false)
            if ( !starburstOnScreen )
            {
                 checkForPeashooterSeedPacketClick();
                 checkForSunflowerSeedPacketClick();
                 letSunFallRandomly(); 
                 makeZombieSounds();
            }       
        }   
        
    } // end method act()
    
    public void checkForZombiesWin()
    {
        List<Zombie> zombiesRemaining = getObjects( Zombie.class );
          
        if ( !zombiesRemaining.isEmpty() )
        {
            for ( Zombie currentZombie : zombiesRemaining )
            {
                if ( currentZombie.getX() < -10 )
                {
                    System.out.println( "THE ZOMBIES ATE YOUR BRAINS!!" );
                    Greenfoot.stop();
                    Greenfoot.playSound( "losemusic.wav" );
                    Greenfoot.playSound( "scream.wav" );
                    alertMessage = new AlertMessage( ALERT_ZOMBIES_WIN );
                    addObject( alertMessage, 400, 300 ); 
                }
            }
        }
    }
    
    public void makeZombieSounds()
    {
        List<Zombie> zombiesRemaining = getObjects( Zombie.class );
          
        if ( !zombiesRemaining.isEmpty() )
        {
            Zombie leftmostZombie = zombiesRemaining.get(0);
            
            for ( Zombie currentZombie : zombiesRemaining )
            {
                // if currentZombie's X-coordinate is LESS than leftmostZombie's X-coordinate, 
                // then update leftmostZombie to now "point" to the currentZombie
                if ( currentZombie.getExactX() < leftmostZombie.getExactX() )
                {
                    leftmostZombie = currentZombie;
                }  
            } // end enhanced for loop

            // if we haven't played the "awooga" sound (i.e. 'the zombies... are coming'):
            if ( !awoogaAlreadyPlayed ) 
            {
                // if the LEFTmost zombie is just about to enter the playing field,
                // then play the awooga sound
                if ( ( leftmostZombie.getExactX() >= getWidth() + 20 ) 
                        && 
                     ( leftmostZombie.getExactX() <= getWidth() + 40 ) )
                {
                    awooga.play();
                    awoogaAlreadyPlayed = true;
                }
                
            }
            else
            {
                // otherwise, play a randomly selected "zombie groan" sound 
                // at some random interval
                int randomGroanInterval = 500*(1 + Greenfoot.getRandomNumber( 3 ));
        
                if ( stageCycleCount % randomGroanInterval == 0 )
                { 
                    int groanIndex = Greenfoot.getRandomNumber( 13 );
                    zombieGroan[ groanIndex ].play();   
                }
            } // end (inner) if-else
            
        } // end outer if
        
    } // end method makeZombieSounds
    
    /**
     * Checks to see if the criteria for finishing the demo stage have been met
     */
    public void checkForStageCompletion()
    {
        /*
         * Count the zombies remaining -- if none, then end the stage
         */
        List<Zombie> zombiesRemaining = getObjects( Zombie.class );
        
        if ( zombiesRemaining.isEmpty() )
        {
            // print diagnostic message
            //System.out.println( "No more zombies!" );
               
            List<SeedPacketStageEnd> seedPacketStageEndList = getObjects( SeedPacketStageEnd.class );
            SeedPacketStageEnd seedPacketStageEnd = seedPacketStageEndList.get(0); 
            
            if ( Greenfoot.mouseClicked( seedPacketStageEnd ) )
            {
                
                if ( music.equals( frontYardMusic ) )
                {
                    music.stop();
                    musicIsPlaying = false;
                } // end twice-nested if
                
                if ( !musicIsPlaying )
                {
                    // start the stage ending cycle
                    //List<SeedPacketStageEnd> seedPacketStageEndList = getObjects( SeedPacketStageEnd.class );
                    //SeedPacketStageEnd seedPacketStageEnd = seedPacketStageEndList.get(0); 
                    addObject( starburst, seedPacketStageEnd.getX(), seedPacketStageEnd.getY() );
                    starburstOnScreen = true;
                    
                    music = winMusic;
                    music.play();
                    musicIsPlaying = true;
                    
                    removeObjects( getObjects( SmoothMover.class ) );
                    removeObjects( getObjects( AlertMessage.class ) );
                    
                    // reset this for stage end timings
                    stageCycleCount = 0;
                } // end twice-nested if

            } // end nested if 1
            
            if ( starburstOnScreen )
            {
                // TODO: replace this with an animation with scaling followed by light filling the screen
                if ( stageCycleCount % 2 == 0 )
                {
                    starburst.setRotation( starburst.getRotation() + 1 );
                } // end twice-nested if
                
                if ( stageCycleCount == 100 )
                {
                    // fade in the white screen
                    alpha = 0;
                    screen = new Screen( whiteScreen );
                    screen.setAlpha( alpha );
                    addObject( screen, 400, 300 );
                }
                else if ( stageCycleCount > 100 && stageCycleCount < 150 )
                {
                    alpha += 5; // equivalent to alpha = alpha + 10;
                    alpha = Math.min( 255, alpha );
                    screen.setAlpha( alpha );
                }
                else if ( stageCycleCount == 150 )
                {
                    alpha = 255;
                    screen.setAlpha( alpha );
                }
                else if ( stageCycleCount == 300 )
                {
                    // what to do here?
                    Greenfoot.playSound( "lightfill.wav" );
                }
                else if ( stageCycleCount >= 300 && stageCycleCount < 700 )
                {
                    alpha -= 1;
                    alpha = Math.max( 0, alpha );
                    starburst.setAlpha( alpha );
                    seedPacketStageEnd.setAlpha( alpha );    
                } // end twice-nested multi-way if/else
                
                if ( stageCycleCount == 700 )
                {
                    // fade in the white screen
                    alpha = 0;
                    removeObjects( getObjects( SeedPacketTray.class ) );
                    removeObjects( getObjects( SeedPacket.class ) );
                    removeObjects( getObjects( Text.class ) );
                    removeObjects( getObjects( Plant.class ) );
                    setBackground( whiteScreen );
                    screen.setScreen( thankYouScreen );
                    screen.setAlpha( alpha );
                }
                else if ( stageCycleCount > 750 && stageCycleCount < 800 )
                {
                    alpha += 5 ; // equivalent to alpha = alpha + 10;
                    alpha = Math.min( 255, alpha );
                    screen.setAlpha( alpha );
                }
                else if ( stageCycleCount == 800 )
                {
                    alpha = 255;
                    screen.setAlpha( alpha );
                } // end twice-nested multi-way if/else
                
            } // end inner if
            
        } // end outer if
        
    } // end method checkForStageCompletion
    
    /**
     * Method for allowing sunshine sprites to fall randomly
     * from the top of the screen
     */
    public void letSunFallRandomly() 
    {
        // change this so falling suns appear at random intervals
        int sunfallInterval = 200*(1 + Greenfoot.getRandomNumber( 3 ));
        
        if ( stageCycleCount % sunfallInterval == 0 )
        {
            // pick a random x-coordinate between 100 and 699
            int fallingSunStartX = 100 + Greenfoot.getRandomNumber( 600 );
            addObject( new FallingSun(), fallingSunStartX, -50 );
            
            if ( !fallingSunAlertAlreadyDisplayed && !fallingSunAlertRemoved )
            {
                //System.out.println( "Click on a falling sun to collect it!" );
                fallingSunAlertAlreadyDisplayed = true;
                fallingSunAlertRemoved = false;
                
                alertMessage = new AlertMessage( ALERT_CLICK_SUN );
                addObject( alertMessage, 400, 550 ); 
            } // end inner if
            
        } // end outer if
        
    } // end method letSunFallRandomly

    /**
     * This method sets up the scrolling background at the 
     * beginning of each stage. 
     * 
     */
    public void bgScrollSetup()
    {
        GreenfootImage bg = getBackground(); 
        bg.drawImage(fullBGimage, 0, 0);  
        
        /*
         * TODO: Randomize the waiting zombie positions
         */
        addObject( new BasicZombieWaiting(), 1180 , 152 );
        addObject( new BucketZombieWaiting(), 1120 , 272 );
        addObject( new BasicZombieWaiting(), 1200 , 332 );
        addObject( new BucketZombieWaiting(), 1220 , 440 );
        addObject( new BasicZombieWaiting(), 1210 , 480 );

        alertMessage = new AlertMessage( ALERT_YOUR_HOUSE );
        addObject( alertMessage, 400, 550 );
            
        music = scrollMusic;
        music.setVolume( 100 );
        music.playLoop();
        gameStatus = BG_SCROLL_ANIMATE;
        
    } // end method bgScrollSetup

    /**
     * This method handles the scrolling background at the 
     * beginning of each stage. I'm not particularly thrilled
     * with how I implemented this, but it was good enough
     * to get the effect across for a class demo.
     * 
     * TODO: To be improved with smoother animation
     */
    public void bgScrollAnimate()
    {
        if ( stageCycleCount == 100 )
        {
            removeObject( alertMessage );
        }
        else if ( stageCycleCount > 100 && stageCycleCount < 350 )
        {
            bgOffset -= bgOffsetDelta;
            bgOffset = Math.max( bgOffset, -500 );
            scrollBGimage( bgOffset );
        }
        else if ( stageCycleCount > 450 && stageCycleCount < 600  )
        {
            bgOffset += bgOffsetDelta;
            bgOffset = Math.min( bgOffset, -218 );
            scrollBGimage( bgOffset );
        }
        else if ( stageCycleCount == 600 )
        {
            // remove all waiting zombies from screen
            List<ZombieWaiting> waitingZombieList = getObjects( ZombieWaiting.class );
            for ( ZombieWaiting zombie : waitingZombieList ) {
                System.out.println( "" + zombie + " is being removed!" );
                removeObject( zombie );
            }
            
            // reset the screen with the usual background
            GreenfootImage bg = getBackground(); 
            bg.drawImage( frontYardImage, 0, 0); 
            gameStatus = STAGE_SETUP;
        }
        /*
        else if ( stageCycleCount > 600 )  
        {
            // fade out the music (this needs to be in its own method, I think)
            //if ( stageCycleCount % 2 == 0 )
            //{
                musicVolume -= 1;
                musicVolume = Math.max( 0, musicVolume); // ensures volume doesn't go below zero
                if ( musicVolume > 0 )
                {
                    music.setVolume( musicVolume );
                }
                else
                {
                    music.setVolume( 0 );
                    music.stop();
                    musicIsPlaying = false;
                    
                    stageCycleCount = 0;
                    gameStatus = STAGE_SETUP;
                }
            //}
        }
        */
    } // end method bgScrollAnimate

    /**
     * Scrolls the background image by the provided offset
     * 
     * @param offset   an integer specifying the number of pixels to move 
     *                 the background for each animation frame
     */
    public void scrollBGimage( int offset )
    {
        GreenfootImage bg = getBackground(); 
        bg.drawImage(fullBGimage, offset, 0);  
        
        // get all objects and move them by the offset delta value
        List<Actor> currentObjects = getObjects( null );
        
        for ( Actor thisObject : currentObjects )
        {
            if ( stageCycleCount > 100 && stageCycleCount < 350 )
            {
                thisObject.setLocation( thisObject.getX() - bgOffsetDelta , thisObject.getY() );
            }
            else if ( stageCycleCount > 450 && stageCycleCount < 700  )
            {
                thisObject.setLocation( thisObject.getX() + bgOffsetDelta , thisObject.getY() );
            } // end inner if/else
            
        } // end for-each loop
        
    } // end method scrollBGimage
    
    /**
     * Sets up the stage for game play
     */
    public void setUpStage()
    {
        // initialize the seed packet tray
        sunPointsLabel = new Text( "" + sunPoints + "   " );
        addObject(sunPointsLabel, 40, 70);
        addObject( new SeedPacketTray(), 237, 43 );
        addObject( peaShooterSeedPacket, 124, 41 );
        addObject( sunflowerSeedPacket, 184, 41 );
        
        // initialize the lawnmowers
        addObject( mower[0], 10, 150 );
        addObject( mower[1], 10, 250 );
        addObject( mower[2], 10, 350 );
        addObject( mower[3], 10, 450 );
        addObject( mower[4], 10, 550 );
        
        /*
         * Add zombies at the predefined coordinates for this stage 
         * TODO: Change these to RANDOM coordinates
         */
        addObject( new BasicZombie(), 950, 100 );
        addObject( new BasicZombie(), 1250, 100 );
        addObject( new ConeheadZombie(), 980, 200 );
        addObject( new BasicZombie(), 960, 300 );
        addObject( new BucketZombie(), 1020, 400 );
        addObject( new BasicZombie(), 990, 500 );

        // Initialize the alert message to be displayed
        alertMessage = new AlertMessage( ALERT_READY );
        addObject( alertMessage, 400, 450 ); 
        
        // TODO: Probably should make this a GreenfootSound object
        Greenfoot.playSound( "readysetplant_thump.wav" );
        
    } // end method setUpStage
    
    /**
     * Controls the timing of events that occur at the beginning of each stage 
     * ( scrolling, messages, etc. )
     * 
     * TODO: Create a similar method for timing of events during the game 
     *       ( zombie waves, etc. )
     */
    public void startStage()
    {
        musicVolume -= 1;
        musicVolume = Math.max( 0, musicVolume); // ensures volume doesn't go below zero
        if ( musicVolume > 0 )
        {
            music.setVolume( musicVolume );
        }
        else
        {
            music.setVolume( 0 );
            music.stop();
           // musicIsPlaying = false;
        }
        /*
         * TODO: Change 40, 80, and 150 to constant ints, defined in the fields section above
         */
        if ( stageCycleCount == 40 )
        {
            // TODO: Probably should make this a GreenfootSound object
            Greenfoot.playSound( "readysetplant_thump.wav" );
            alertMessage.setImgMessage( ALERT_SET );
            
        }   
        else if ( stageCycleCount == 80 )
        {
            // TODO: Probably should make this a GreenfootSound object
            Greenfoot.playSound( "readysetplant_boom.wav" );
            alertMessage.setImgMessage( ALERT_PLANT );
            
        }  
        else if ( stageCycleCount == 150 )
        {
            
            removeObject( alertMessage );
            gameStatus = BEGIN_PLAY;
           
            // initialize the music for the stage
            music = frontYardMusic;
            music.setVolume( 100 ); 
        }  // end multi-way if/else
        
    } // end method startStage
    
    /**
     * Updates the status of whether or not the Peashooter's seed packet
     * has been clicked (and prints a debug message to stdout)
     * 
     * @param status    
     */
    public void setPeaShooterSeedPacketClickStatus( boolean status )
    {
        peaShooterSeedPacketClicked = status;
        System.out.println( "peaShooterSeedPacketClicked = " + peaShooterSeedPacketClicked );
    } // end method setPeaShooterSeedPacketClickStatus
    
    /**
     * Updates the status of whether or not the Sunflower's seed packet
     * has been clicked (and prints a debug message to stdout)
     *       
     * @param status   
     */
    public void setSunflowerSeedPacketClickStatus( boolean status )
    {
        sunflowerSeedPacketClicked = status;
        System.out.println( "sunflowerSeedPacketClicked = " + sunflowerSeedPacketClicked );
    } // end method setPeaShooterSeedPacketClickStatus
    
    /**
     * Logic for handling how to check for a peashooter seed packet click event
     * 
     * TODO: Eventually I need to make a more 
     *       generic method for ANY type of seed packet
     */
    public void checkForPeashooterSeedPacketClick()
    {
        /*
         * TODO: Make sure you have "de-activated" any other seed packets
         */
        if ( peaShooterSeedPacketClicked && (sunPoints >= 100) ) {
            
            if ( Greenfoot.mouseClicked( this ) ) {
                
                // Create local variables to store the location of the last mouse click
                int mouseClickX = Greenfoot.getMouseInfo().getX();
                int mouseClickY = Greenfoot.getMouseInfo().getY();
                
                // We need to search for the closest sodSpace center location (relative to our mouse click).
                // To do this, we first need to set up some local variables...
                double bestDistance = 9999.0; // initialize this to some large value (large enough never to be exceeded)
                double currentDistance = 0.0; // initialize to zero
                int iBest = 0;                // keeps track of the closest sodSpace row position
                int jBest = 0;                // keeps track of the closest sodSpace column position
                
                // We can use a nested for loop to search for the nearest sodSpace center point
                // (relative to the location of the mouse click)
                for ( int i = 0 ; i < 5 ; i++ )
                {
                    for ( int j = 0 ; j < 9 ; j++ )
                    {
                        // Compute the Euclidean (L2) distance between the mouse click point
                        // and the current sodSpace center point (for row i and column j)
                        currentDistance = Math.sqrt( Math.pow( mouseClickX - sodSpaceCenterX[i][j], 2 ) + 
                                                       Math.pow( mouseClickY - sodSpaceCenterY[i][j], 2 ) );
                        
                        // Updates the location of the closest sodSpace center 
                        // (Note there is no else clause... instead we just keep 
                        // iterating and updating until we have traversed the entire
                        // 2-D array!)
                        if ( currentDistance < bestDistance ) 
                        {
                            // update values of local variables
                            bestDistance = currentDistance;
                            
                            // update row and column location of the sodSpace center
                            // that is the closest of those searched so far 
                            iBest = i;
                            jBest = j;
                            
                        } // end if
                    } // end inner for-loop
                } // end outer for-loop
                
                // Now that we have determined which sodSpace center is closest to where you clicked,
                // place the object at that position, but first check to see if any plant has already 
                // been placed at this point; if not, then mark the position accordingly
                if ( !sodSpaceOccupied[iBest][jBest] ) 
                {
                    addObject( new Peashooter(), sodSpaceCenterX[iBest][jBest], sodSpaceCenterY[iBest][jBest] );
                    sodSpaceOccupied[iBest][jBest] = true;
                    Greenfoot.playSound( "plant.wav" );
                    
                    // decrease sunpoints accordingly
                    addSunPoints( -100 );
                    
                    // reset seed packet
                    peaShooterSeedPacketClicked = false;
                    List<PeaShooterSeedPacket> peaShooterSeedPacketList = getObjects( PeaShooterSeedPacket.class );
                    for ( PeaShooterSeedPacket thisSeedPacket : peaShooterSeedPacketList ) 
                    {
                        thisSeedPacket.resetImage();
                    } // end for-each loop

                } // end if space is NOT occupied at the given coordinates....
                
            } // end if game board was clicked...
            
        } // end if peaShooter seed packet was clicked...
    
    } // end method checkForPeashooterSeedPacketClick
    
    /**
     * Logic for handling how to check for a sunflower seed packet click event
     * 
     * TODO: Eventually I need to make a more 
     *       generic method for ANY type of seed packet
     */
    public void checkForSunflowerSeedPacketClick()
    {
        /*
         * TODO: Need to merge this into a single SeedPacketClick() method to reduce code duplication  
         */
        if ( sunflowerSeedPacketClicked && (sunPoints >= 50) ) {
            
            if ( Greenfoot.mouseClicked( this ) ) {
                
                // Create local variables to store the location of the last mouse click
                int mouseClickX = Greenfoot.getMouseInfo().getX();
                int mouseClickY = Greenfoot.getMouseInfo().getY();
                
                // We need to search for the closest sodSpace center location (relative to our mouse click).
                // To do this, we first need to set up some local variables...
                double bestDistance = 9999.0; // initialize this to some large value (large enough never to be exceeded)
                double currentDistance = 0.0; // initialize to zero
                int iBest = 0;                // keeps track of the closest sodSpace row position
                int jBest = 0;                // keeps track of the closest sodSpace column position
                
                // We can use a nested for loop to search for the nearest sodSpace center point
                // (relative to the location of the mouse click)
                for ( int i = 0 ; i < 5 ; i++ )
                {
                    for ( int j = 0 ; j < 9 ; j++ )
                    {
                        // Compute the Euclidean (L2) distance between the mouse click point
                        // and the current sodSpace center point (for row i and column j)
                        currentDistance = Math.sqrt( Math.pow( mouseClickX - sodSpaceCenterX[i][j], 2 ) + 
                                                       Math.pow( mouseClickY - sodSpaceCenterY[i][j], 2 ) );
                        
                        // Updates the location of the closest sodSpace center 
                        // (Note there is no else clause... instead we just keep 
                        // iterating and updating until we have traversed the entire
                        // 2-D array!)
                        if ( currentDistance < bestDistance ) 
                        {
                            // update values of local variables
                            bestDistance = currentDistance;
                            
                            // update row and column location of the sodSpace center
                            // that is the closest of those searched so far 
                            iBest = i;
                            jBest = j;
                            
                        } // end i
                    } // end inner for-loop
                } // end outer for-loop
                
                // Now that we have determined which sodSpace center is closest to where you clicked,
                // place the object at that position, but first check to see if any plant has already 
                // been placed at this point; if not, then mark the position accordingly
                if ( !sodSpaceOccupied[iBest][jBest] ) 
                {
                    addObject( new Sunflower(), sodSpaceCenterX[iBest][jBest], sodSpaceCenterY[iBest][jBest] );
                    Greenfoot.playSound( "plant2.wav" );
                    sodSpaceOccupied[iBest][jBest] = true;
                    
                    // decrease sunpoints accordingly
                    addSunPoints( -50 );
                    
                    // reset seed packet
                    sunflowerSeedPacketClicked = false;
                    List<SunflowerSeedPacket> sunflowerSeedPacketList = getObjects( SunflowerSeedPacket.class );
                    for ( SunflowerSeedPacket thisSeedPacket : sunflowerSeedPacketList ) 
                    {
                        thisSeedPacket.resetImage();
                    } 

                } // end if
                
            } // end if game board was clicked...
            
        } // end if sunflower seed packet was clicked...
    
    } // end method checkForSunflowerSeedPacketClick
    
    /**
     * Updates the "sun points" text field after a sun has been clicked
     * 
     * @param pointsAdded   number of points to add to the text field
     */
    public void addSunPoints( int pointsAdded )
    {
        sunPoints += pointsAdded ;
        sunPointsLabel.setText( "" + sunPoints + "   ");
        
        if ( fallingSunAlertAlreadyDisplayed && !fallingSunAlertRemoved )
        {
            removeObject( alertMessage );
            fallingSunAlertRemoved = true;
        }
    }
    
    /**
     * Displays the sequence of title screen images for the game, 
     * and prompts for user to click start when ready to begin playing.
     */
    public void displayTitleScreen()
    {
        /*
         * TODO: Store the transition points in an array?
         */
        if ( stageCycleCount == 1 ) 
        {
            alpha = 0;
            screen.setScreen( devIdentScreen1 );
            screen.setAlpha( alpha );
            
            // start playing music
            music = titleScreenMusic;
            music.playLoop();
            musicIsPlaying = true;
        }
        else if ( stageCycleCount > 1 && stageCycleCount < 25 )
        {
            alpha += 10; // equivalent to alpha = alpha + 10;
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 25 && stageCycleCount < 175  )
        {
            alpha = 255;
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 175 && stageCycleCount < 200 )
        {
            alpha -= 10; // equivalent to alpha = alpha - 10;
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 200 && stageCycleCount < 210 )
        {
            alpha = 0;
            screen.setAlpha( alpha );
            screen.setScreen( devIdentScreen2 );
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 210 && stageCycleCount < 235 )
        {
            alpha += 10; // equivalent to alpha = alpha + 10;
            screen.setAlpha( alpha );
        }    
        else if ( stageCycleCount >= 235 && stageCycleCount < 385  )
        {
            alpha = 255;
            screen.setAlpha( alpha );
            //setBackground( frontYardImage );
        }
        else if ( stageCycleCount >= 385 && stageCycleCount < 410 )
        {
            alpha -= 10; // equivalent to alpha = alpha - 10;
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 410 && stageCycleCount < 420 )
        {
            alpha = 0;
            screen.setAlpha( alpha );
            screen.setScreen( titleScreen );
            screen.setAlpha( alpha );
        }
        else if ( stageCycleCount >= 420 && stageCycleCount < 445 )
        {
            alpha += 10; // equivalent to alpha = alpha + 10;
            screen.setAlpha( alpha );
        }    
        else if ( stageCycleCount == 445 )
        {
            alpha = 255;
            screen.setAlpha( alpha );
            //setBackground( frontYardImage );
            //setBackground( blackScreen );
            if ( !Greenfoot.mouseClicked( screen ) )
            {
                // this keeps the stageCycleCount in a "holding pattern" 
                // while waiting for a mouse click
                stageCycleCount--;
            }
        }
        else if ( stageCycleCount > 445 && stageCycleCount < 750 ) 
        {
            alpha -= 4; // equivalent to alpha = alpha - 10;
            screen.setAlpha( Math.max(0, (int)alpha) );
            
            // start fading out music
            /*
             * TODO: I'm testing to see if the "zombie laugh" music might be more effective...
             *       but I don't want to delete this code...
             */
            /*
            musicVolume -= 1;
            musicVolume = Math.max( 0, musicVolume); // ensures volume doesn't go below zero
            if ( musicVolume > 0 )
            {
                music.setVolume( musicVolume );
            }
            else
            {
                music.setVolume( 0 );
                music.stop();
                musicIsPlaying = false;
            }
            */
            
           if ( musicIsPlaying && music.equals( titleScreenMusic ) )
           {
               music.stop();
               music = zombieLaughMusic;
               music.play();
               musicIsPlaying = false;
           }
           
           // start the evil zombie laugh here...
           if ( stageCycleCount == 550 ) 
           {
               Greenfoot.playSound( "evillaugh.wav" );
           }
        }
        else if ( stageCycleCount >= 750 ) 
        {
            // reset everything for game BG_SCROLL_SETUP mode
            stageCycleCount = 0;
            removeObject( screen );
            gameStatus = BG_SCROLL_SETUP;
        }
    } // end method displayTitleScreen();
    
    /**
     * Method for handling all events that need to take
     * place when the "run" button is clicked
     */
    public void started()
    {
        // if music was playing, restart music from pause point
        if ( musicIsPlaying ) 
        {
            if ( music.equals( winMusic ) )
            {
                music.play();
            }
            else
            {
                music.playLoop();
            } // end inner if/else
            
        } // end outer if
        
    } // end method started
    
    /**
     * Method for handling all events that need to take
     * place when the "pause" button is clicked
     */
    public void stopped()
    {
        // stop whatever music is playing
        music.pause();
    } // end method stopped
    
} // end class Game
