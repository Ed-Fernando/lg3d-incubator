package nu.koidelab.cosmo.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import nu.koidelab.cosmo.util.TextureUtil;

import org.jdesktop.lg3d.sg.Texture;

public class FileManager {
	private static final int ICON_HEIGHT = 64;
	private static final int ICON_WIDTH = 64;
    private static final String fileIconDir = "resources/cosmo/images/default/" + "icons/mimetypes/";
	private String defaultIconPath = "resources/cosmo/images/default/" + "icons/" + "moon.png";
	Texture defaultIcon;
	private Properties defaultFileAssociation;
	private Properties userFileAssociation = null;
	private Properties defaultIconAssociation;
	private Properties userIconAssociation = null;
	private Map<String, Texture> icons;

	public FileManager() {
		defaultFileAssociation = loadProps("resources/cosmo/FileAssociation.conf");
		defaultIconAssociation = loadProps("resources/cosmo/IconAssociation.conf");
		icons = new HashMap<String, Texture>();
        defaultIcon = TextureUtil.getInstance().getTexture(defaultIconPath, ICON_WIDTH, ICON_HEIGHT);
	}
	
	public FileManager(String filename) {
		this();
		if(filename != null)
			userFileAssociation = loadProps(filename);
	}
	
	/* Return an application's name associated with argument. 
	 * If the extention of the file is not registered, return null.
	 * */
	public String getFileAssociation(String filename){
		String extension = getFileExtension(filename);
		if(extension == null) return null;
		
		String appName = null;
		if(userFileAssociation != null)
			appName = userFileAssociation.getProperty(extension);
		if(appName == null)
			appName = defaultFileAssociation.getProperty(extension);

		return appName;
	}
	

	public Texture getFileIcon(String filename){
		String ext = getFileExtension(filename);
		Texture tex = icons.get(ext);
		if(tex == null){
			String iconFile = defaultIconAssociation.getProperty(ext);
			if(iconFile == null && userIconAssociation != null)
				iconFile = userIconAssociation.getProperty(ext);
			if(iconFile != null){
                String filePath = fileIconDir + iconFile;
                /*  If system resource is null */
                InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);                
                if(in != null)
                    tex = TextureUtil.getInstance().getTexture(filePath, ICON_WIDTH, ICON_HEIGHT);
                else
                    tex = defaultIcon; 
				icons.put(ext, tex);
                
			} else {
                tex = defaultIcon; 
			}
		}
		
		return tex;
	}

	/* arg ext : file extesion */
	public String getIconFilename(String ext){
		String iconFile = defaultIconAssociation.getProperty(ext);
		if(iconFile == null && userIconAssociation != null)
			iconFile = userIconAssociation.getProperty(ext);
		if(iconFile != null){
			return fileIconDir + iconFile;
		} else {
			return null;
		}
	}
	
	public String getFileExtension(String filename){
		if(filename == null)
			throw new IllegalArgumentException("filename cannot be null.");
		String[] filenameParts = filename.split("\\.");
		
		if (filenameParts.length > 1) {
			return filenameParts[filenameParts.length - 1].toLowerCase();				
		} else {
			return null;
		}
	}
	
	private Properties loadProps(String filename){
		if(filename == null) return null;
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
		if(in == null){
			try {
				in = new FileInputStream(new File(filename));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
			
//	private Texture2D getImage(String filename){
//        Iterator readers = ImageIO.getImageReadersBySuffix(getFileExtension(filename));
//        ImageReader reader = (ImageReader)readers.next();
//        BufferedImage image = null;
//        try {
//            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(filename);
//            if (inStream == null) {
//                inStream = new FileInputStream(filename);
//            }
//            
//            ImageInputStream stream = ImageIO.createImageInputStream(inStream);
//            reader.setInput(stream);
//            image = reader.read(0);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        
//        image = normalize(image, ICON_WIDTH, ICON_HEIGHT);
//        
//        return createTexture(image);	
//	}
//		
//	/* Normalize image size with arg "w" and "h". */
//    private BufferedImage normalize(BufferedImage source, int w, int h){
//        int srcW = source.getWidth();
//        int srcH = source.getHeight();
//        float xScale = (float)w/(float)srcW;
//        float yScale = (float)h/(float)srcH;
//
//        if (xScale == 1.0f && yScale == 1.0f)
//            return source;
//        else {
//            int scaleW = (int)(source.getWidth() * xScale + 0.5);
//            int scaleH = (int)(source.getHeight() * yScale + 0.5);
//
//            BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//            Graphics g = destination.getGraphics();
//            g.drawImage(source, 0, 0, w, h, null);
//            g.dispose();
//
//            return destination;
//        }
//    }
//
//    /* Create Texture2D from image*/
//    private Texture2D createTexture(BufferedImage image) {
//        ImageComponent2D imageComp = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA, 
//                                                          image, true, true);
//        imageComp.set(image);
//
//        /* Mipmap is not created. */
//        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
//                                            imageComp.getWidth(), imageComp.getHeight());
//        
//        texture.setImage(0, imageComp);
//        texture.setCapability(Texture2D.ALLOW_IMAGE_READ);
//        texture.setMinFilter(Texture2D.BASE_LEVEL_LINEAR);
//        texture.setMagFilter(Texture2D.BASE_LEVEL_LINEAR);
//
//        return texture;
//    }	
}
