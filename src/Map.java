import org.apache.commons.lang3.SerializationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Map extends JPanel implements KeyListener, ActionListener, MouseListener
{
    public final KeyEvent DOWN = new KeyEvent( this,
            KeyEvent.KEY_PRESSED,
            System.currentTimeMillis(),
            0,
            KeyEvent.VK_DOWN,
            (char) KeyEvent.VK_DOWN
    );

    int counter = 0;
    private int score = 0;

    private final Block playerPieces = new Block();
    private final Block blockPieces = new Block();

    // map's configs
    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;
    public static final int GAMESPEED = 500;
    public static final int DELAY = 1000 / 60;
    public static final int TILE_SIZE = 20;
    public static final int ROWS = WIDTH / TILE_SIZE;
    public static final int COLUMNS = HEIGHT / TILE_SIZE;

    private final Timer timer;
    private boolean gameOver = false;

    public Map()
    {
        setBackground( Color.darkGray );
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );

        playerPieces.createBlock();

        timer = new Timer( DELAY, this );
        timer.start();

        playerPieces.autoMove( this );

        countFPS();
    }

    private void verifyRow( int y )
    {
        try
        {
            y = y-1;
            if ( y != 0 )
            {
                int rowCounter = 0;

                Piece search = new Piece( 0, y );

                for ( int x = 0; x < ROWS; x++ )
                {
                    search.getPos().x = x;

                    for ( Piece b : blockPieces.getPieces() )
                    {
                        if ( search.getPos().equals( b.getPos() ) )
                        {
                            rowCounter++;
                        }
                    }
                }

                if ( rowCounter == ROWS )
                {
                    for ( int x = 0; x < ROWS; x++ )
                    {
                        search.getPos().x = x;

                        blockPieces.getPieces().removeIf( piece -> piece.getPos().equals( search.getPos() ) );
                    }
                    score++;

                    dropRow( y, search );
                    verifyRow( y+1 );
                }
                else
                {
                    verifyRow( y );
                }
            }
        }
        catch ( Exception ignored ) {}
    }

    private void dropRow( int y, Piece search )
    {
        y = y-1;
        if ( y != 0 )
        {
            search.getPos().y = y;

            for ( int x = 0; x < ROWS; x++ )
            {
                search.getPos().x = x;

                for ( Piece b : blockPieces.getPieces() )
                {
                    if ( search.getPos().equals( b.getPos() ) )
                    {
                        b.move( DOWN );
                    }
                }
            }

            dropRow( y, search );
        }
    }

    private void countFPS()
    {
        new Thread( () ->
        {
            int newCounter;

            while (true)
            {
                newCounter = counter;
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println( "framerate: " + ( counter - newCounter ) );
            }
        } ).start();
    }

    private void drawScore( Graphics g )
    {
        // setting fonts config
        String s = Integer.toString( score );

        g.setFont( new Font( Font.MONOSPACED, Font.ITALIC, 18 ) );
        g.setColor( Color.yellow );
        g.drawString( s, WIDTH-20, HEIGHT-5 );
    }

    public void tick()
    {
        // this gets called once every tick, before the repainting process happens
        // prevent the player from moving off the edge of the board sideways
        boolean hit = false;

        for ( Piece p : playerPieces.getPieces() )
        {
            if ( p.getPos().y == Map.COLUMNS -1 )
            {
                hit = true;
                break;
            }

            for ( Piece b : blockPieces.getPieces() )
            {
                if ( p.getPos().y +1 == b.getPos().y && p.getPos().x == b.getPos().x )
                {
                    hit = true;
                    break;
                }
            }
        }

        if ( hit )
        {
            playerPieces.getPieces().forEach( p ->
            {
                if ( p.getPos().y == 0 )
                {
                    gameOver = true;
                }
            } );

            if ( !gameOver )
            {
                blockPieces.getPieces().addAll( SerializationUtils.clone( playerPieces.getPieces() ) );

                playerPieces.getPieces().clear();

                playerPieces.createBlock();

                verifyRow( COLUMNS );
            }
        }
    }

    private void gameOver( Graphics g )
    {
        if ( gameOver )
        {
            g.setFont( new Font( Font.MONOSPACED, Font.BOLD, 45 ) );
            g.setColor( Color.RED );
            g.drawString( "GAME OVER", 20, HEIGHT/2 );

            g.setFont( new Font( Font.MONOSPACED, Font.BOLD, 20 ) );
            g.drawString( "Click to play again", 35, HEIGHT/2+20 );

            timer.stop();
        }
    }

    private void move( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_SPACE )
        {
            rotate();
            return;
        }

        int pressed = e.getKeyCode();
        int i = 0;

        for ( Piece p : playerPieces.getPieces() )
        {
            if ( pressed == KeyEvent.VK_DOWN && p.getPos().y < Map.COLUMNS -1 )
            {
                i++;
            }
            else if ( pressed == KeyEvent.VK_RIGHT && p.getPos().x < Map.ROWS -1 )
            {
                i++;
            }
            else if ( pressed == KeyEvent.VK_LEFT && p.getPos().x > 0 )
            {
                i++;
            }

            if ( !blockPieces.getPieces().isEmpty() )
            {
                for ( Piece b : blockPieces.getPieces() )
                {
                    if ( pressed == KeyEvent.VK_DOWN && b.getPos().y == p.getPos().y +1 && b.getPos().x == p.getPos().x )
                    {
                        i++;
                    }
                    else if ( pressed == KeyEvent.VK_RIGHT && b.getPos().x == p.getPos().x +1 && b.getPos().y == p.getPos().y )
                    {
                        i++;
                    }
                    else if ( pressed == KeyEvent.VK_LEFT && b.getPos().x == p.getPos().x -1 && b.getPos().y == p.getPos().y )
                    {
                        i++;
                    }
                }
            }
        }

        if ( i == playerPieces.getPieces().size() )
        {
            playerPieces.move( e );
        }
    }

    private void rotate()
    {
        playerPieces.rotate( blockPieces.getPieces() );
    }

    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        blockPieces.draw( g );
        playerPieces.draw( g );

        drawScore( g );
        gameOver( g );

        // this smooths the animations
        Toolkit.getDefaultToolkit().sync();

        g.dispose();

        counter++;
    }

    @Override
    public void keyPressed( KeyEvent e )
    {
        move( e );
    }

    @Override
    public void keyReleased( KeyEvent e ) {}

    @Override
    public void keyTyped( KeyEvent e ) {}

    @Override
    public void actionPerformed( ActionEvent e )
    {
        tick();

        repaint();
    }

    @Override
    public void mouseClicked( MouseEvent e )
    {
        if ( gameOver )
        {
            gameOver = false;
            score = 0;
            counter = 0;

            playerPieces.getPieces().clear();
            blockPieces.getPieces().clear();

            playerPieces.createBlock();

            timer.start();
        }
    }

    @Override
    public void mousePressed( MouseEvent e ) {}

    @Override
    public void mouseReleased( MouseEvent e ) {}

    @Override
    public void mouseEntered( MouseEvent e ) {}

    @Override
    public void mouseExited( MouseEvent e ) {}
}
