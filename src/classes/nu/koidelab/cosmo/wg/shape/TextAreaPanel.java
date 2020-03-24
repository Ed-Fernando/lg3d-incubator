package nu.koidelab.cosmo.wg.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import nu.koidelab.cosmo.manager.CosmoConfig;

import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * @author fumi TextAreaPanel nu.koide-lab.cosmo.wg.shape
 */
public class TextAreaPanel extends StringPanel {
    private int row;
    private int column;
    private StringBuffer sb;
    private SimpleAppearance app;

    public TextAreaPanel(int row, int column, String str) {
        this(row, column, str, new Font("MS Gothic", Font.BOLD, 25), new Color(
                0.4f, 0.8f, 0.8f), Color.WHITE);
    }

    public TextAreaPanel(int row, int column, String str, Font font,
            Color fgColor, Color bgColor){
    	this(row, column, false, str, font, fgColor, bgColor);
    }
    
    public TextAreaPanel(int row, int column, boolean shiny, String str, Font font,
            Color fgColor, Color bgColor) {
        super();
        this.row = row;
        this.column = column;
        setForeGround(fgColor);
        setBackGround(bgColor);
        setFont(font);
        spacer = font.getSize() / 4;

        app = new SimpleAppearance(1.0f, 1.0f, 1.0f, fgColor.getTransparency(),
                SimpleAppearance.ENABLE_TEXTURE
                        | ((shiny) ? (0) : (SimpleAppearance.NO_GLOSS))
                        | SimpleAppearance.DISABLE_CULLING);
        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

        setText(str);

        setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        setCapability(Shape3D.ALLOW_GEOMETRY_READ);

        setAppearance(app);
    }

    private String[] getStringArray() {
        ArrayList<String> strings = new ArrayList<String>();        
        while (sb.length() > 1) {
            if (sb.length() > column) {
                int index = sb.indexOf("\n");
                if ( (index > column) || (index < 0) ) {
                    strings.add(sb.substring(0, column));
                    sb = sb.delete(0, column);
                } else if (index > 0) {
                    strings.add(sb.substring(0, index));
                    sb = sb.delete(0, index+1);
                } else if (index == 0){
                    strings.add("");
                    sb = sb.delete(0, 2);
                }
            } else {
                strings.add(sb.substring(0, sb.length()));
                sb = sb.delete(0, sb.length());
            }
        }
        if(strings.size() < row)
            for(int i = strings.size(); i < row; i++)
                strings.add("");
        else if(strings.size() > row)
            for(int i = row; i < strings.size(); i++)
                strings.remove(i);        
        String[] ret = new String[strings.size()];
        return strings.toArray(ret);
    }
    
    public void setText(String str) {
        sb = new StringBuffer(str);

        BufferedImage image = createImage(getStringArray());
        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();
        texture.setCapability(Texture.ALLOW_SIZE_READ);
        app.setTexture(texture);
        
//        width = texture.getWidth() * CosmoConfig.UNIT_TRANS_FACTOR;
//        height = texture.getHeight() * CosmoConfig.UNIT_TRANS_FACTOR;
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        width = toolkit3d.widthNativeToPhysical(image.getWidth());
        height = toolkit3d.heightNativeToPhysical(image.getHeight());


        makePanel(width, height, shiny);
    }

    private BufferedImage createImage(String[] str) {
        BufferedImage image = new BufferedImage(2, 2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(font);

        int strLength = 0;
        for(int i = 0; i < str.length; i++){
            int a = g.getFontMetrics().stringWidth(str[i]);
            if(a > strLength)
                strLength = a;
        }
        
        int charHeight = g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent();
        int width = strLength + 4;
        int height = g.getFontMetrics().getAscent() * str.length + 4;

        // create image
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setBackground(bgColor);
        g.clearRect(0, 0, width, height);
        g.setColor(fgColor);
        g.setFont(font);
        for (int i = 0; i < str.length; i++) {
            if(isCentering){
                int offset = (width - g.getFontMetrics().stringWidth(str[i]))/2;
                g.drawString(str[i], offset, g.getFontMetrics().getAscent()*(i+1) + 2);
            }else
                g.drawString(str[i], 2, g.getFontMetrics().getAscent()*(i+1) + 2);
        }
        g.dispose();

        return image;
    }
}
