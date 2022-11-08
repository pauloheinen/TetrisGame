import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Random;

public class Piece implements Serializable
{
    private final Point pos;
    private final Color color;

    public Piece( int x, int y )
    {
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = random.nextInt( 8000 ) / 10000f;
        final float luminance = 0.9f;

        color = Color.getHSBColor( hue, saturation, luminance );

        pos = new Point( x, y );
    }

    public Point getPos()
    {
        return this.pos;
    }

    public void draw( Graphics g )
    {
        g.setColor( color );
        g.fillRoundRect( pos.x * Map.TILE_SIZE, pos.y * Map.TILE_SIZE, Map.TILE_SIZE, Map.TILE_SIZE, 4, 4 );

        g.setColor( Color.BLACK);
        g.drawRoundRect( pos.x * Map.TILE_SIZE, pos.y * Map.TILE_SIZE, Map.TILE_SIZE, Map.TILE_SIZE, 4, 4 );
    }

    public void move( KeyEvent e )
    {
        int key = e.getKeyCode();

        switch ( key )
        {
            case KeyEvent.VK_DOWN ->
                    pos.y = pos.y + 1;
            case KeyEvent.VK_RIGHT ->
                    pos.x = pos.x + 1;
            case KeyEvent.VK_LEFT ->
                    pos.x = pos.x - 1;
        }
    }
}
