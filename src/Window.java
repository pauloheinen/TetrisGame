import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    Map panel1;

    public Window()
    {
        panel1 = new Map();

        initComponents();

        setTitle( "Tetris Game :D" );
        setBounds( new Rectangle( 500, 535 ) );
        setResizable( false );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo( null );
        setAutoRequestFocus( true );
        setVisible( true );

        add( panel1, BorderLayout.WEST );

        pack();
    }

    public void initComponents()
    {
        addKeyListener( panel1 );
        addMouseListener( panel1 );
    }
}
