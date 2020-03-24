package org.jdesktop.lg3d.apps.periodictable;

/**
  * Project Looking Glass
  *
  * $RCSfile: ImageFactory.java,v $
  *
  * Copyright (c) 2006 William Tracy, All Rights Reserved
  *
  * Redistributions in source code form must reproduce the above
  * copyright and this condition.
  *
  * The contents of this file are subject to the GNU General Public
  * License, Version 2 (the "License"); you may not use this file
  * except in compliance with the License. A copy of the License is
  * available at http://www.opensource.org/licenses/gpl-license.php.
  */

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import java.net.*;

import javax.imageio.*;

/** Generates the images for PeriodicTable3D. The images are written to tempfiles, from
  * where the images can be read by ImagePanels. 
  * @author William Tracy
  */
public class ImageFactory {
  /** Width in pixels of each element panel.
    */
  public static final int WIDTH = 56;
  /** Height in pixels of each element panel.
    */
  public static final int HEIGHT = 68;
  /** Main group elements (everything except the inner transition elements).
    */
  protected File imageFile;
  protected File thumbnailImageFile;
  /** Left inner transition elements.
    */
  protected File innerImageFile1;
  protected File thumbnailInnerImageFile;
  /** Used for right half of inner transition elements.
    */ 
  protected File innerImageFile2;

  /** Creates a new ImageFactory, and generates initial temp files.
    * @throws IOException if the temp files cannot be written to.
    */
  public ImageFactory() throws IOException {
    imageFile = File.createTempFile("pt3d", ".png");
    innerImageFile1 = File.createTempFile("pt3d", ".png");
    innerImageFile2 = File.createTempFile("pt3d", ".png");
    thumbnailImageFile = File.createTempFile("pt3d-thumb", ".png");
    thumbnailInnerImageFile = File.createTempFile("pt3d-thumb", ".png");

    imageFile.deleteOnExit();
    innerImageFile1.deleteOnExit();
    innerImageFile2.deleteOnExit();
    thumbnailImageFile.deleteOnExit();
    thumbnailInnerImageFile.deleteOnExit();

    ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
    writer.setOutput(ImageIO.createImageOutputStream(imageFile));
    writer.write(createImage());

    writer.setOutput(ImageIO.createImageOutputStream(innerImageFile1));
    writer.write(createInnerImage1());

    writer.setOutput(ImageIO.createImageOutputStream(innerImageFile2));
    writer.write(createInnerImage2());
    
    writer.setOutput(ImageIO.createImageOutputStream(thumbnailImageFile));
    writer.write(thumbnail(createImage()));
    
    writer.setOutput(ImageIO.createImageOutputStream(thumbnailInnerImageFile));
    writer.write(thumbnail(createInnerImage2()));
  }

  protected BufferedImage thumbnail(BufferedImage original) {
	  BufferedImage thumb = new BufferedImage(original.getWidth() / 30,
			  								  original.getHeight() / 30,
			  								  original.getType());
	  Graphics2D g2d = thumb.createGraphics();
	  g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	  g2d.drawImage(original, 0, 0, thumb.getWidth(), thumb.getHeight(), null);
	  return thumb;
  }
  
  /** Creates an image of the periodic table.
    * @return An image of the entire periodic table except the inner transition 
    * elements.
    */ 
  public URL getImageFile() {
    try {
      return imageFile.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /** Creates an image of the left half of the inner transition elements. 
    * @return The image of the left half of the inner transition elements. 
    */
  public URL getInnerImageFile1() {
    try {
      return innerImageFile1.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /** Creates an image of the right half of the inner transition elements. 
    * @return The image of the right half of the inner transition elements. 
    */
 public URL getInnerImageFile2() {
   try {
     return innerImageFile2.toURI().toURL();
   } catch (MalformedURLException e) {
     throw new IllegalArgumentException(e);
   }
 }
 
 public URL getThumbnailImageFile() {
   try {
     return thumbnailImageFile.toURI().toURL();
   } catch (MalformedURLException e) {
     throw new IllegalArgumentException(e);
   }
 }
 
 public URL getThumbnailInnerImageFile() {
   try {
     return thumbnailInnerImageFile.toURI().toURL();
   } catch (MalformedURLException e) {
     throw new IllegalArgumentException(e);
   }
 }
 
  /** Creates the image of the elements, except the inner transition elements.
    * @return a buffer containing the image.
    */
  protected BufferedImage createImage() {
    BufferedImage image = new BufferedImage(WIDTH * 18 + 1,
                                            7 * HEIGHT + 1,
                                            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    // Rare earth metals
    for (int y = 0; y < 7; ++y) {
      for (int x = 0; x < 1; ++x) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }
    // Alkaline earth metals
    for (int y = 1; y < 7; ++y) {
      for (int x = 1; x < 2; ++x) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }
    // Transition metals
    for (int y = 3; y < 7; ++y) {
      for (int x = 2; x < 12; ++x) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }
    // Nonmetals/Metalloids
    for (int y = 1; y < 6; ++y) {
      for (int x = 12; x < 17; ++x) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }
    // Noble gases
    for (int y = 0; y < 6; ++y) {
      for (int x = 17; x < 18; ++x) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(Color.BLUE);
    g2d.setFont(new Font("Sans-Serif", Font.PLAIN, 28));

    g2d.drawString("H", 2, 35);
    g2d.drawString("Li", 2, HEIGHT + 35);
    g2d.drawString("Na", 2, HEIGHT * 2 + 35);
    g2d.drawString("K", 2, HEIGHT * 3 + 35);
    g2d.drawString("Rb", 2, HEIGHT * 4 + 35);
    g2d.drawString("Cs", 2, HEIGHT * 5 + 35);
    g2d.drawString("Fr", 2, HEIGHT * 6 + 35);

    g2d.drawString("Be", WIDTH + 2, HEIGHT + 35);
    g2d.drawString("Mg", WIDTH + 2, HEIGHT * 2 + 35);
    g2d.drawString("Ca", WIDTH + 2, HEIGHT * 3 + 35);
    g2d.drawString("Sr", WIDTH + 2, HEIGHT * 4 + 35);
    g2d.drawString("Ba", WIDTH + 2, HEIGHT * 5 + 35);
    g2d.drawString("Ra", WIDTH + 2, HEIGHT * 6 + 35);

    g2d.drawString("Sc", WIDTH *  2 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Ti", WIDTH *  3 + 2, HEIGHT * 3 + 35);
    g2d.drawString("V",  WIDTH *  4 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Cr", WIDTH *  5 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Mn", WIDTH *  6 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Fe", WIDTH *  7 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Co", WIDTH *  8 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Ni", WIDTH *  9 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Cu", WIDTH * 10 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Zn", WIDTH * 11 + 2, HEIGHT * 3 + 35);

    g2d.drawString("Y",  WIDTH *  2 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Zr", WIDTH *  3 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Nb", WIDTH *  4 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Mo", WIDTH *  5 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Tc", WIDTH *  6 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Ru", WIDTH *  7 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Rh", WIDTH *  8 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Pd", WIDTH *  9 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Ag", WIDTH * 10 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Cd", WIDTH * 11 + 2, HEIGHT * 4 + 35);

    g2d.drawString("La", WIDTH *  2 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Hf", WIDTH *  3 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Ta", WIDTH *  4 + 2, HEIGHT * 5 + 35);
    g2d.drawString("W",  WIDTH *  5 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Re", WIDTH *  6 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Os", WIDTH *  7 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Ir", WIDTH *  8 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Pt", WIDTH *  9 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Au", WIDTH * 10 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Hg", WIDTH * 11 + 2, HEIGHT * 5 + 35);

    g2d.drawString("Ac",  WIDTH *  2 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Rf",  WIDTH *  3 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Db",  WIDTH *  4 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Sg",  WIDTH *  5 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Bh",  WIDTH *  6 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Hs",  WIDTH *  7 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Mt",  WIDTH *  8 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Ds",  WIDTH *  9 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Rg",  WIDTH * 10 + 2, HEIGHT * 6 + 35);
    g2d.drawString("Uub", WIDTH * 11 + 2, HEIGHT * 6 + 35);

    g2d.drawString("B", WIDTH * 12 + 2, HEIGHT + 35);
    g2d.drawString("C", WIDTH * 13 + 2, HEIGHT + 35);
    g2d.drawString("N", WIDTH * 14 + 2, HEIGHT + 35);
    g2d.drawString("O", WIDTH * 15 + 2, HEIGHT + 35);
    g2d.drawString("F", WIDTH * 16 + 2, HEIGHT + 35);

    g2d.drawString("Al", WIDTH * 12 + 2, HEIGHT * 2 + 35);
    g2d.drawString("Si", WIDTH * 13 + 2, HEIGHT * 2 + 35);
    g2d.drawString("P",  WIDTH * 14 + 2, HEIGHT * 2 + 35);
    g2d.drawString("S",  WIDTH * 15 + 2, HEIGHT * 2 + 35);
    g2d.drawString("Cl", WIDTH * 16 + 2, HEIGHT * 2 + 35);

    g2d.drawString("Ga", WIDTH * 12 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Ge", WIDTH * 13 + 2, HEIGHT * 3 + 35);
    g2d.drawString("As", WIDTH * 14 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Se", WIDTH * 15 + 2, HEIGHT * 3 + 35);
    g2d.drawString("Br", WIDTH * 16 + 2, HEIGHT * 3 + 35);

    g2d.drawString("In", WIDTH * 12 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Sn", WIDTH * 13 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Sb", WIDTH * 14 + 2, HEIGHT * 4 + 35);
    g2d.drawString("Te", WIDTH * 15 + 2, HEIGHT * 4 + 35);
    g2d.drawString("I",  WIDTH * 16 + 2, HEIGHT * 4 + 35);

    g2d.drawString("Tl", WIDTH * 12 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Pb", WIDTH * 13 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Bi", WIDTH * 14 + 2, HEIGHT * 5 + 35);
    g2d.drawString("Po", WIDTH * 15 + 2, HEIGHT * 5 + 35);
    g2d.drawString("At", WIDTH * 16 + 2, HEIGHT * 5 + 35);

    g2d.drawString("He", 2 + 17 * WIDTH, 35);
    g2d.drawString("Ne", 2 + 17 * WIDTH, HEIGHT + 35);
    g2d.drawString("Ar", 2 + 17 * WIDTH, 2 * HEIGHT + 35);
    g2d.drawString("Kr", 2 + 17 * WIDTH, 3 * HEIGHT + 35);
    g2d.drawString("Xe", 2 + 17 * WIDTH, 4 * HEIGHT + 35);
    g2d.drawString("Rn", 2 + 17 * WIDTH, 5 * HEIGHT + 35);

    return image;
  }

  /** Creates an image of the left half of the inner transition elements.
    * @return the buffer containing the image.
    */
  protected BufferedImage createInnerImage1() {
    BufferedImage image = new BufferedImage(WIDTH * 7 + 1,
                                            HEIGHT * 2 + 1,
                                            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();
    
    for (int x = 0; x < 7; ++x) {
      for (int y = 0; y < 2; ++y) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
      }
    }
		
    g2d.setColor(Color.BLUE);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
		
    g2d.drawString("Ce", 2, 35);
    g2d.drawString("Pr", WIDTH + 2, 35);
    g2d.drawString("Nd", WIDTH *  2 + 2, 35);
    g2d.drawString("Pm", WIDTH *  3 + 2, 35);
    g2d.drawString("Sm", WIDTH *  4 + 2, 35);
    g2d.drawString("Eu", WIDTH *  5 + 2, 35);
    g2d.drawString("Gd", WIDTH *  6 + 2, 35);
    	
    g2d.drawString("Th", 2, HEIGHT + 35);
    g2d.drawString("Pa", WIDTH + 2, HEIGHT + 35);
    g2d.drawString("U",  WIDTH *  2 + 2, HEIGHT + 35);
    g2d.drawString("Np", WIDTH *  3 + 2, HEIGHT + 35);
    g2d.drawString("Pu", WIDTH *  4 + 2, HEIGHT + 35);
    g2d.drawString("Am", WIDTH *  5 + 2, HEIGHT + 35);
    g2d.drawString("Cm", WIDTH *  6 + 2, HEIGHT + 35);
    
    
    return image;
  }
  
  /** Returns the image of the right half of the inner transition elements.
    */
  protected BufferedImage createInnerImage2() {
	BufferedImage image = new BufferedImage(
			WIDTH * 7 + 1,
            HEIGHT * 2 + 1,
            BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = image.createGraphics();
    
	for (int x = 0; x < 7; ++x) {
		for (int y = 0; y < 2; ++y) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
		}
	}
	
	g2d.setColor(Color.BLUE);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setFont(new Font("Sans-Serif", Font.PLAIN, 28));
	
	g2d.drawString("Tb", WIDTH *  0 + 2, 35);
    g2d.drawString("Dy", WIDTH *  1 + 2, 35);
    g2d.drawString("Ho", WIDTH *  2 + 2, 35);
    g2d.drawString("Er", WIDTH *  3 + 2, 35);
    g2d.drawString("Tm", WIDTH *  4 + 2, 35);
    g2d.drawString("Yb", WIDTH *  5 + 2, 35);
    g2d.drawString("Lu", WIDTH *  6 + 2, 35);
    
    g2d.drawString("Bk", WIDTH *  0 + 2, HEIGHT + 35);
    g2d.drawString("Cf", WIDTH *  1 + 2, HEIGHT + 35);
    g2d.drawString("Es", WIDTH *  2 + 2, HEIGHT + 35);
    g2d.drawString("Fm", WIDTH *  3 + 2, HEIGHT + 35);
    g2d.drawString("Md", WIDTH *  4 + 2, HEIGHT + 35);
    g2d.drawString("No", WIDTH *  5 + 2, HEIGHT + 35);
    g2d.drawString("Lr", WIDTH *  6 + 2, HEIGHT + 35);
    
    return image;
  }
}