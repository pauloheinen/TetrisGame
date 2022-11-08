import org.apache.commons.lang3.SerializationUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Block
{
    private String character;

    private ArrayList<Piece> pieces = new ArrayList<>();

    public void createBlock()
    {
        // a piece may be T, L, I, O, Z, S, J, .

        int randomChar = new Random().nextInt( 8 );
        switch ( randomChar )
        {
            //.
            case 0 ->
                    {
                        character = ".";

                        pieces.add( new Piece( 2, -1 ) );
                    }
            //T
            case 1 ->
                    {
                        character = "T";

                        pieces.add( new Piece( 1, -2 ) );
                        pieces.add( new Piece( 2, -2 ) );
                        pieces.add( new Piece( 3, -2 ) );
                        pieces.add( new Piece( 2, -1 ) );
                    }
            //L
            case 2 ->
                    {
                        character = "L";

                        pieces.add( new Piece( 2, -1 ) );
                        pieces.add( new Piece( 3, -1 ) );
                        pieces.add( new Piece( 4, -1 ) );
                        pieces.add( new Piece( 4, -2 ) );
                    }
            //J
            case 3 ->
                    {
                        character = "J";

                        pieces.add( new Piece( 3, -1 ) );
                        pieces.add( new Piece( 2, -1 ) );
                        pieces.add( new Piece( 1, -1 ) );
                        pieces.add( new Piece( 1, -2 ) );
                    }
            //O
            case 4 ->
                    {
                        character = "O";

                        pieces.add( new Piece( 2, -1 ) );
                        pieces.add( new Piece( 2, -2 ) );
                        pieces.add( new Piece( 3, -1 ) );
                        pieces.add( new Piece( 3, -2 ) );
                    }
            //I
            case 5 ->
                    {
                        character = "I";

                        pieces.add( new Piece( 2, -4 ) );
                        pieces.add( new Piece( 2, -3 ) );
                        pieces.add( new Piece( 2, -2 ) );
                        pieces.add( new Piece( 2, -1 ) );
                    }
            //Z
            case 6 ->
                    {
                        character = "Z";

                        pieces.add( new Piece( 2, -2 ) );
                        pieces.add( new Piece( 3, -2 ) );
                        pieces.add( new Piece( 3, -1 ) );
                        pieces.add( new Piece( 4, -1 ) );
                    }
            //S
            case 7 ->
                    {
                        character = "S";

                        pieces.add( new Piece( 4, -2 ) );
                        pieces.add( new Piece( 3, -2 ) );
                        pieces.add( new Piece( 3, -1 ) );
                        pieces.add( new Piece( 2, -1 ) );
                    }
        }
    }

    public void draw( Graphics g )
    {
        for ( Piece node : pieces )
        {
            node.draw( g );
        }
    }

    public void autoMove( Component c )
    {
        new Thread( () ->
        {
            while ( true )
            {
                try
                {
                    Thread.sleep( Map.GAMESPEED );
                } catch ( InterruptedException e )
                {
                    e.printStackTrace();
                }
                move( new KeyEvent( c,
                        KeyEvent.KEY_PRESSED,
                        System.currentTimeMillis(),
                        0,
                        KeyEvent.VK_DOWN,
                        (char) KeyEvent.VK_DOWN )
                    );
            }
        } ).start();
    }

    public void rotate( ArrayList<Piece> bArray )
    {
        ArrayList<Piece> temp = SerializationUtils.clone( pieces );

        switch ( character )
        {
            case "I" ->
                    {
                        character = "-";

                        pieces.get( 0 ).getPos().x -= 1;
                        pieces.get( 0 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x += 1;
                        pieces.get( 2 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().x += 2;
                        pieces.get( 3 ).getPos().y -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "I";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "-" ->
                    {
                        character = "I";

                        pieces.get( 0 ).getPos().x += 1;
                        pieces.get( 0 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x -= 1;
                        pieces.get( 2 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().x -= 2;
                        pieces.get( 3 ).getPos().y += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "-";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "L" ->
                    {
                        character = "L2";

                        pieces.get( 0 ).getPos().x += 1;
                        pieces.get( 0 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x -= 1;
                        pieces.get( 2 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().y += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "L";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "L2" ->
                    {
                        character = "L3";

                        pieces.get( 0 ).getPos().x += 1;
                        pieces.get( 0 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x -= 1;
                        pieces.get( 2 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().x -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "L2";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "L3" ->
                    {
                        character = "L4";

                        pieces.get( 0 ).getPos().x -= 1;
                        pieces.get( 0 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x += 1;
                        pieces.get( 2 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().y -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "L3";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "L4" ->
                    {
                        character = "L";

                        pieces.get( 0 ).getPos().x -= 1;
                        pieces.get( 0 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x += 1;
                        pieces.get( 2 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().x += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "L4";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "J" ->
                    {
                        character = "J2";

                        pieces.get( 0 ).getPos().x -= 1;
                        pieces.get( 0 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x += 1;
                        pieces.get( 2 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().x += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "J";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "J2" ->
                    {
                        character = "J3";

                        pieces.get( 0 ).getPos().x -= 1;
                        pieces.get( 0 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x += 1;
                        pieces.get( 2 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().y += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "J2";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "J3" ->
                    {
                        character = "J4";

                        pieces.get( 0 ).getPos().x += 1;
                        pieces.get( 0 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x -= 1;
                        pieces.get( 2 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().x -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "J3";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "J4" ->
                    {
                        character = "J";

                        pieces.get( 0 ).getPos().x += 1;
                        pieces.get( 0 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x -= 1;
                        pieces.get( 2 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().y -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "J4";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "T" ->
                    {
                        character = "T2";

                        pieces.get( 0 ).getPos().x += 2;
                        pieces.get( 1 ).getPos().x += 1;
                        pieces.get( 1 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().y += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "T";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "T2" ->
                    {
                        character = "T3";

                        pieces.get( 0 ).getPos().y += 2;
                        pieces.get( 1 ).getPos().x -= 1;
                        pieces.get( 1 ).getPos().y += 1;
                        pieces.get( 2 ).getPos().x -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "T2";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "T3" ->
                    {
                        character = "T4";

                        pieces.get( 0 ).getPos().x -= 2;
                        pieces.get( 1 ).getPos().x -= 1;
                        pieces.get( 1 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().y -= 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "T3";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "T4" ->
                    {
                        character = "T";

                        pieces.get( 0 ).getPos().y -= 2;
                        pieces.get( 1 ).getPos().x += 1;
                        pieces.get( 1 ).getPos().y -= 1;
                        pieces.get( 2 ).getPos().x += 2;

                        if ( verifyHit( bArray ) )
                        {
                            character = "T4";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "Z" ->
                    {
                        character = "Z2";

                        pieces.get( 0 ).getPos().x += 2;
                        pieces.get( 1 ).getPos().x += 1;
                        pieces.get( 1 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().x -= 1;
                        pieces.get( 3 ).getPos().y += 1;

                        if ( verifyHit( bArray ) )
                        {
                            character = "Z";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "Z2" ->
                    {
                        character = "Z";

                        pieces.get( 0 ).getPos().x -= 2;
                        pieces.get( 1 ).getPos().x -= 1;
                        pieces.get( 1 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().x += 1;
                        pieces.get( 3 ).getPos().y -= 1;

                        if ( verifyHit( bArray ) )
                        {
                            character = "Z2";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "S" ->
                    {
                        character = "S2";

                        pieces.get( 0 ).getPos().y += 2;
                        pieces.get( 1 ).getPos().x += 1;
                        pieces.get( 1 ).getPos().y += 1;
                        pieces.get( 3 ).getPos().x += 1;
                        pieces.get( 3 ).getPos().y -= 1;

                        if ( verifyHit( bArray ) )
                        {
                            character = "S";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
            case "S2" ->
                    {
                        character = "S";

                        pieces.get( 0 ).getPos().y -= 2;
                        pieces.get( 1 ).getPos().x -= 1;
                        pieces.get( 1 ).getPos().y -= 1;
                        pieces.get( 3 ).getPos().x -= 1;
                        pieces.get( 3 ).getPos().y += 1;

                        if ( verifyHit( bArray ) )
                        {
                            character = "S2";

                            pieces.clear();
                            pieces = new ArrayList<>( temp );
                        }
                    }
        }
    }

    private boolean verifyHit( ArrayList<Piece> bArray )
    {
        boolean hit = false;

        for ( Piece p : pieces )
        {
            if ( p.getPos().y > Map.COLUMNS -1 || p.getPos().x > Map.ROWS -1 || p.getPos().x < 0 )
            {
                hit = true;
                break;
            }

            if ( ! bArray.isEmpty() )
            {
                for ( Piece b : bArray )
                {
                    if ( p.getPos().equals( b.getPos() ) )
                    {
                        hit = true;
                        break;
                    }
                }
            }
        }

        return hit;
    }

    public void move( KeyEvent e )
    {
        for ( Piece p : pieces )
        {
            p.move( e );
        }
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
