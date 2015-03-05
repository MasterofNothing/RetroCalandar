package calendarProgram;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickTest
{
    public static void main(String[] args)
    {
        new MouseClickTest();
    }

    MouseClickTest()
    {
        JFrame jFrame = new JFrame("Test");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(6,7));
        for(int i=0;i<42;i++) jFrame.add(new CustomPanel());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    class CustomPanel extends JPanel implements MouseListener
    {
		private static final long serialVersionUID = 4678218611857074785L;
		
		boolean isHighlighted;
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border redBorder = BorderFactory.createLineBorder(Color.RED,5);
        CustomPanel()
        {
            addMouseListener(this);
            setBorder(blackBorder);
            setFocusable(true);
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(100, 100);
        }

        @Override public void mouseClicked(MouseEvent e)
        {
            if(isHighlighted) setBorder(blackBorder);
            else setBorder(redBorder);
            isHighlighted=!isHighlighted;
        }

        @Override public void mousePressed(MouseEvent e){}
        @Override public void mouseReleased(MouseEvent e){}
        @Override public void mouseEntered(MouseEvent e){}
        @Override public void mouseExited(MouseEvent e){}
    }
}