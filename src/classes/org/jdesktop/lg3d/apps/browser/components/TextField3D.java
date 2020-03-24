/*
 * Created on Aug 3, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.displayserver.nativewindow.NativeWindowFuzzyEdgePanel;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.SwingNodeRenderer;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class TextField3D extends Component3D
{
  JTextField textField = new JTextField();
  
  public TextField3D( float width, float height )
  {
    this( "", width, height );
  }
  
  public TextField3D(String text, float width, float height)
  { 
    final Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
    int nativeWidth = toolkit3d.widthPhysicalToNative( width );
    int nativeHeight = toolkit3d.widthPhysicalToNative( height );
    
    textField.setText(text);
    
    textField.setVisible( true );
    textField.setPreferredSize(new Dimension(nativeWidth, nativeHeight));
    
    JPanel panel = new JPanel(new BorderLayout());

    
    textField.setBackground(Color.WHITE);
    textField.setForeground(Color.BLACK);
    
    
    panel.add(textField);
    
    SwingNode swingNode = new SwingNode(new NonTransparentSwingNodeRenderer());
    swingNode.setJPanel(panel);
    
    this.addChild( swingNode );
    
  }// end TextField3D
  
  public void setText(String text)
  {
    textField.setText(text);
  }// end setText
  
  public String getText()
  {
    return textField.getText();
  }// end getText
}
