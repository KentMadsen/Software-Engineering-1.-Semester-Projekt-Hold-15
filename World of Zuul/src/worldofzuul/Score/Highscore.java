/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul.Score;

import java.util.*;
import java.io.*;
import java.nio.file.Files;

/**
 *
 * @author Bytoft, Mikkel
 * @author Christensen, Martin Steen
 * @author Hansen, Søren Vest
 * @author Johansen, Emil Højgaard
 * @author Madsen, Kent vejrup
 * @author Thy, Mads Heimdal
 */
public class Highscore extends HighscoreSystem 
{
    private boolean Debug = false;
    
    private final String DatabasePath = ".\\db";
    private final String HS_Database = DatabasePath + "\\Highscore";
    
    /**
     * 
     */
    public Highscore()
    {
        setCurrentPlayerName( "player" );
        
        if( worldofzuul.IO.Directories.Exist( new File( HS_Database ) ) == false )
        {
            worldofzuul.IO.Directories.Create( HS_Database, 
                                               true );
        }
        
    }
    
    /**
     * 
     * @param name 
     */
    public Highscore( String name )
    {
        this();
        
        if( hsText.ParseCharacters( name ) == false )
        {
            return;
        }
        
        setCurrentPlayerName( name );
        
        loadPlayers();
        
        
    }
    
    /**
     * Saves a character's, current score
     * @param CharacterName
     * @return True: Saved, False: Error occured
     */
    public boolean saveCurrentCharacter( )
    {
       StringBuilder builder = new StringBuilder(); 
        
       builder.append( getCurrentPlayerName() );
       builder.append( ',' );
       builder.append( Integer.toString( getCurrentPlayerPoints() ) );
              
       File file = new File( HS_Database + "\\" + getCurrentPlayerName() );
       
       try
       {
            PrintWriter pw = new PrintWriter( file, "UTF-8" );
            pw.println( builder.toString() );
            pw.close();
       }
       catch( Exception ex )
       {
            return false;   
       }
       
       return true;
    }
    
    /**
     * Loads other Character's that are saved
     */
    public final void loadPlayers()
    {
        File playerFilesDirectory = new File( HS_Database );
        File[] listedPlayerFiles = worldofzuul.IO.List.ListFiles( playerFilesDirectory );
        
        for( File user : listedPlayerFiles )
        {
            try
            {
                List<String> linesRead = Files.readAllLines( user.toPath() );
                
                for( String currentline : linesRead )
                {
                    String[] result = currentline.split( "," );
                    
                    addPlayers( result[0], 
                                Integer.parseInt( result[1] ) );  
                }
                
            }
            catch ( Exception ex )
            {
                        
            }
            
        }
        
        
    }
    
    // Get
    public boolean getDebug()
    {
        return this.Debug;
    }
    
    // Set
    public void setDebug( boolean State )
    {
        this.Debug = State;
    }
    
    // Functions ------------------------------------------------------------------------------------ //
   
    private static class hsText
    {   
            /**
            * 
            * @param InputName
            * @return 
            */
        public static boolean ParseCharacters( String InputName )
        {
            //
            boolean Continue;
            
            for( char c : InputName.toCharArray() )
            {
                Continue = allowedCharacter( c );

                if( Continue == false )
                    return false;
            }

            return true;
        } // End ParseCharacters
     
       /**
        * 
        * @param inputValue
        * @return 
        */
        public static boolean allowedCharacter( char inputValue )
        {
            
            if ( inputValue >= 'A' && 
                 inputValue <= 'z' )
            {
                return true;
            }
            
            if( inputValue >= '0' && 
                inputValue <= '9' )
            {
                return true;
            }
            
            if( ( inputValue == '-' ) || 
                ( inputValue == '_' ) )
            {
                return true;
            }
            
            return false;
        } // End AllowedCharacter
        
    } // End Parsing
    
    private static class hsDebug
    {
        public static void Output( String Text, boolean DebugState )
        {
            
            if( DebugState == true )
            {
                System.out.println(Text);
            }
            
        }
    } // End hsDebug    

}  // End Class Main