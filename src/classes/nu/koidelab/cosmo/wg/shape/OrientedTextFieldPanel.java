package nu.koidelab.cosmo.wg.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.vecmath.Color4f;

import nu.koidelab.cosmo.manager.CosmoConfig;

import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class OrientedTextFieldPanel extends OrientedTextPanel {
    private boolean isPerspective = false;
    private Color shadowColor;
      
    public OrientedTextFieldPanel(String str){
        this(str, CosmoConfig.STR_COLOR_4F);
    }
    
    public OrientedTextFieldPanel(String str, boolean isPerspective){
        this(str, false, isPerspective, CosmoConfig.DEFAULT_FONT, CosmoConfig.STR_COLOR, new Color(0, 0, 0, 0), 1.0f); 
    }
            
    public OrientedTextFieldPanel(String str, Color4f color){
        this(str, true, CosmoConfig.DEFAULT_FONT, color);
    }
    
    public OrientedTextFieldPanel(String str, boolean shiny, Font font, Color4f color) {
        this(str, shiny, false, font, new Color(color.x, color.y, color.z), new Color(0, 0, 0, 0), color.w); 
    }
    
    public OrientedTextFieldPanel(String str, boolean shiny, boolean isPerspective, Font font, Color4f color) {
        this(str, shiny, isPerspective, font, new Color(color.x, color.y, color.z), new Color(0, 0, 0, 0), color.w); 
    }
    
    public OrientedTextFieldPanel(String str, boolean shiny, boolean isPerspecitve, Font font, Color fgColor, Color bgColor, float alpha){
        this.isPerspective = isPerspecitve;
        this.shadowColor = CosmoConfig.STR_SHADOW_COLOR;
        spacer = font.getSize(); 
        setForeGround(fgColor);
        setBackGround(bgColor);
        setFont(font);
        
        SimpleAppearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, alpha,
                SimpleAppearance.ENABLE_TEXTURE
                        | ((shiny) ? (0) : (SimpleAppearance.NO_GLOSS))
                        | SimpleAppearance.DISABLE_CULLING);

        BufferedImage image = createImage(str);
        TextureLoader loader = new TextureLoader(image);
        app.setTexture(loader.getTexture());
        setAppearance(app);

        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        width = toolkit3d.widthNativeToPhysical(image.getWidth());
        height = toolkit3d.heightNativeToPhysical(image.getHeight());
        makePanel(width, height, shiny);
    }        
        
    private BufferedImage createImage(String str) {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(font);
        int strLength = g.getFontMetrics().stringWidth(str);
        int charHeight = g.getFontMetrics().getMaxAscent() 
            + g.getFontMetrics().getMaxDescent() + g.getFontMetrics().getLeading();
        int width = isPerspective ? strLength + 1 :strLength;
        int height =  charHeight;
        
        /* draw image */
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();        
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);        
        g.setBackground(bgColor);
        g.clearRect(0, 0, width, height);
        g.setFont(font);
        if(isPerspective){            
            g.setColor(shadowColor);
            g.drawString(str, 1, g.getFontMetrics().getMaxAscent()+1);
        }
        g.setColor(fgColor);
        g.drawString(str, 0, g.getFontMetrics().getMaxAscent());
        g.dispose();

        return image;
    }
    
    public void setPerspective(boolean isPerspective){
        this.isPerspective = isPerspective;
    }
}
