package calandarProgram;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
//import java.awt.event.*;
//import java.util.*;

public class CalandarSidePane {
	
	public static void main (String args[]){

	    JTextPane textPane = new JTextPane();
	    StyledDocument doc = textPane.getStyledDocument();
	
	    javax.swing.text.Style style = textPane.addStyle("I'm a Style", null);
	    StyleConstants.setForeground(style, Color.black);
	
	    try { doc.insertString(doc.getLength(), "HEADER",style); }
	    catch (BadLocationException e1){}
	
	    StyleConstants.setForeground(style, Color.gray);
	
	    try { doc.insertString(doc.getLength(), "BODY",style); }
	    catch (BadLocationException e){}
	
	    JFrame frame = new JFrame("Date info");
	    frame.getContentPane().add(textPane);
	    frame.pack();
	    frame.setVisible(true);

	}

}
