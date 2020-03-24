package nu.koidelab.cosmo.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;

/**
 * @author fumi_ss
 * 14:35:32
 */
public class TextureUtil {
	private static final TextureUtil instance = new TextureUtil();
    private HashMap<TextureKey, Texture> texMap = new HashMap<TextureKey, Texture>();
//    private WeakHashMap<TextureKey, Texture> texMap = new WeakHashMap<TextureKey, Texture>();
    
    public static TextureUtil getInstance(){
        return instance;
    }
    
    public Texture getTexture(String filename) {
    	return getTexture(filename, 0L, null);
    }
    
    public Texture getTexture(String filename, long offset, String fileExtention){
//    	System.err.println("debug: getTexture offset="+offset);
    	Texture tex = texMap.get(new TextureKey(filename, offset));
    	if(tex == null){
    		//System.err.println("debug: filename="+filename+", offset="+offset);
    		tex = getTextureImpl(filename, offset, fileExtention);
    		texMap.put(new TextureKey(filename, offset), tex);
    	} else {
    		//System.err.println("RECYCLE : " + filename);
    	}
    	
    	return tex;
    }
    
    /**
     * Retrieves a texture object using the specified imagefile.
     * @param filename - filename of the image
     * @param width - width of image in pixel
     * @param height - height of image
     * @return The texture object 
     */
    public Texture getTexture(String filename, int width, int height){
        return getTexture(filename, 0L, null, width, height);
    }
    
    public Texture getTexture(String filename, long offset, String fileExtention, int width, int height){
    	Texture tex = texMap.get(new TextureKey(filename, offset, width, height));
    	if(tex == null){
    		tex = getTextureImpl(filename, offset, fileExtention, width, height);
    		texMap.put(new TextureKey(filename, offset, width, height), tex);
    	} else {
//    		System.err.println("RECYCLE : " + filename + " / " + width + " / " + height);
    	}
    	
    	return tex;
    }
    
    private Texture2D getTextureImpl(String filename, String fileExtention) {
    	return getTextureImpl(filename, 0L, fileExtention);
    }
    
    private Texture2D getTextureImpl(String filename, long offset, String fileExtention){    	
        //System.err.println("debug: filename="+filename);
        BufferedImage image = getImage(filename, offset, fileExtention);
        int newW = getClosestPowerOf2(image.getWidth());
        int newH = getClosestPowerOf2(image.getHeight());
        image = normalize(image, newW, newH);
        
    	return createTexture(image);
    }
    
    private Texture2D getTextureImpl(String filename, String fileExtention, int width, int height) {
    	return getTextureImpl(filename, 0L, fileExtention, width, height);
    }
    
    private Texture2D getTextureImpl(String filename, long offset, String fileExtention, int width, int height){
        BufferedImage image = getImage(filename, offset, fileExtention);        
        image = normalize(image, width, height);
        
        return createTexture(image);    
    }
    
    
    private int getClosestPowerOf2(int value) {
    	if (value < 1)
    	    return value;
    	
    	int powerValue = 1;
    	for (;;) {
    	    powerValue *= 2;
    	    if (value < powerValue) {
    		// Found max bound of power, determine which is closest
    		int minBound = powerValue/2;
    		if ((powerValue - value) >
    		    (value - minBound))
    		    return minBound;
    		else
    		    return powerValue;
    	    }
    	}
    }

    private BufferedImage getImage(String filename) {
    	return getImage(filename, 0L, null);    	
    }
    
	private BufferedImage getImage(String filename, long offset, String fileExtention) {
		Iterator readers;
		if (fileExtention==null) {
			readers = ImageIO.getImageReadersBySuffix(getFileExtension(filename));
		} else {
			readers = ImageIO.getImageReadersBySuffix(fileExtention);
		}
		
        ImageReader reader = (ImageReader)readers.next();
        BufferedImage image = null;
        try {
            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(filename);
            if (inStream == null) {
                inStream = new FileInputStream(filename);
            }
            for (long x=0; x!=offset; ) {
            	long skipped = inStream.skip(offset-x);
            	if (skipped <= 0) {
            		System.err.println("debug: cannot skip.");
            	}
            	x+=skipped;
            }
            ImageInputStream stream = ImageIO.createImageInputStream(inStream);
            reader.setInput(stream);
            image = reader.read(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return image;
	}
        
    /* Normalize image size with arg "w" and "h". */
    private static BufferedImage normalize(BufferedImage source, int w, int h){
        int srcW = source.getWidth();
        int srcH = source.getHeight();
        float xScale = (float)w/(float)srcW;
        float yScale = (float)h/(float)srcH;

        if (xScale == 1.0f && yScale == 1.0f)
            return source;
        else {
            int scaleW = (int)(source.getWidth() * xScale + 0.5);
            int scaleH = (int)(source.getHeight() * yScale + 0.5);

            BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics g = destination.getGraphics();
            g.drawImage(source, 0, 0, w, h, null);
            g.dispose();

            return destination;
        }
    }

    private static String getFileExtension(String filename){
        if(filename == null)
            throw new IllegalArgumentException("filename cannot be null.");
        String[] filenameParts = filename.split("\\.");
        
        if (filenameParts.length > 1) {
            return filenameParts[filenameParts.length - 1].toLowerCase();               
        } else {
            return null;
        }
    }

    /* Create Texture2D from image*/
    private static Texture2D createTexture(BufferedImage image) {
        ImageComponent2D imageComp = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA, 
                                                          image, true, true);
        imageComp.set(image);

        /* Mipmaps are not created. */
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                            imageComp.getWidth(), imageComp.getHeight());
        
        texture.setImage(0, imageComp);
        texture.setCapability(Texture2D.ALLOW_IMAGE_READ);
        texture.setMinFilter(Texture2D.BASE_LEVEL_LINEAR);
        texture.setMagFilter(Texture2D.BASE_LEVEL_LINEAR);

        return texture;
    }
    
    private class TextureKey {
    	private static final int NONE_SPECIFIED = -1;
    	private String filename;
    	private long offset;
    	private int width;
    	private int height;
    	private boolean scaled;
    	private int hash = 0;
    	
    	private TextureKey(String filename, long offset) {
    		this.filename = filename;
    		this.width = NONE_SPECIFIED;
    		this.height = NONE_SPECIFIED;
    		this.offset = offset;
    		scaled = false;
		}
    	
    	private TextureKey(String filename, long offset, int width, int height){
    		this.filename = filename;
    		this.width = width;
    		this.height = height;
    		this.offset = offset;
    		scaled = true;
    	}
    	
    	@Override
    	public int hashCode() {
    		hash = filename.hashCode();
    		hash = hash*31 + Integer.valueOf(width).hashCode();
    		hash = hash*31 + Integer.valueOf(height).hashCode();
    		hash = hash*31 + Boolean.valueOf(scaled).hashCode();
    		hash = hash*31 + Long.valueOf(offset).hashCode();
    		return hash;
    	}
    	
    	@Override
    	public boolean equals(Object obj) {
    		if(this == obj) return true;
    		if(!(obj instanceof TextureKey)) return false;
    		TextureKey target = (TextureKey)obj;
    		
    		return ((this.filename.equals(target.filename))
					&& (this.scaled == target.scaled)
					&& (this.width == target.width) 
					&& (this.height == target.height)
					&& (this.offset == target.offset));
		}
    }
}
