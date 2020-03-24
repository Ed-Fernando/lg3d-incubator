package org.jdesktop.lg3d.apps.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.SwingNode;


/**
 * a 2D UI tester for the Browser3D framework.
 * 
 * @author demers
 */
public class BrowserApp
{ 
  
  /**
   * Simple 2D test frame work for Browser3DFactory 
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    try
    {
      JFrame frame1 = new JFrame();
      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame1.setSize( 600, 400 );
      
      final Browser3D browser = Browser3DFactroy.getBrowser3D();
      browser.setURL("http://www.google.com");
            
      JPanel jPanel = new JPanel();
      jPanel.setPreferredSize( new Dimension(400,300) );
      jPanel.setLayout( new BorderLayout() );
      jPanel.add(browser.getBrowserPanel(), BorderLayout.CENTER);
  
      JPanel panel = new JPanel();
      JButton backButton = new JButton("Back");
      JButton forwardButton = new JButton("Forward");

      backButton.addActionListener( new ActionListener() 
      {
        public void actionPerformed( ActionEvent actionEvent ) 
        {          
          if( browser.getHistoryTracker().hasBack() )
          {
            HistoryItem back = browser.getHistoryTracker().moveBack();
          
            browser.setURL( back.getUrl() );
          }// end if
          
        }
      });
      
      forwardButton.addActionListener( new ActionListener() 
          {
            public void actionPerformed( ActionEvent actionEvent ) 
            {
              if( browser.getHistoryTracker().hasForward() )
              {  
                HistoryItem forward = browser.getHistoryTracker().moveForward();
              
                browser.setURL( forward.getUrl() );
              }// end if
            }
          });
      
      panel.add( backButton );
      panel.add( forwardButton );
      
      frame1.add( panel, BorderLayout.NORTH );
      frame1.add( jPanel, BorderLayout.CENTER );
      
      frame1.setVisible( true );

    }// end try
    catch( Throwable t )
    {
      t.printStackTrace();
    }// end catch
  }// end main
}
