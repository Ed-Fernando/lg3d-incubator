/*
 * ��.��: 2005/08/03
 */
package nu.koidelab.cosmo.wg.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

public class NumberPanel extends Shape3D{
    protected int num = 0;
    protected float shiftX = 0;
    protected float shiftY = 0;
    protected float shiftZ = 0;
    protected static final Texture DEFAULT_TEXTURE;
    private int column = -1;
    static{
        BufferedImage image = createImage("0123456789", new Color(1f, 1f, 1f));
        TextureLoader loader = new TextureLoader(image);     
        DEFAULT_TEXTURE = loader.getTexture();
    }

    public NumberPanel(){
        setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        SimpleAppearance app = new SimpleAppearance(1f, 1f, 1f, 1.0f,
        		SimpleAppearance.ENABLE_TEXTURE
        		| SimpleAppearance.DISABLE_CULLING);
        app.setTexture(DEFAULT_TEXTURE);
        setAppearance(app);
    }
    
    public NumberPanel(int num){
    	this();
        setNumber(num);
    }
    
    public NumberPanel(int num, float shiftX, float shiftY, float shiftZ){
        setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        SimpleAppearance app = new SimpleAppearance(1f, 1f, 1f, 1.0f,
        		SimpleAppearance.ENABLE_TEXTURE
        		| SimpleAppearance.DISABLE_CULLING);
        setAppearance(app);
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.shiftZ = shiftZ;
        setNumber(num);
    }
        
    public void setNumber(int num){
        if(this.num == num) return;
        this.num = num;
        makeGeometries(num);
    }
    
    public void setNumber(String num){
        this.num = Integer.valueOf(num);
        int[] nums = new int[num.length()];
        for(int i = 0, n = nums.length; i < n; i++){
            String str = num.substring(i, i+1);
            nums[i] = Integer.valueOf(str);
        }
        set(nums);
    }
    
    public int getNumber(){
        return num;
    }
    
    public void setOffset(float x, float y, float z){
        shiftX = x;
        shiftY = y;
        shiftZ = z;
        makeGeometries(num);
    }
    
    public void setColumn(int num){
        column = num;
    }
    
    private void makeGeometries(int num){
        String number = String.valueOf(num);
        int length = number.length();

        if(column < 0) {
            int[] nums = new int[length]; 
            for(int i = 0, n = nums.length; i < n; i++){
                String str = number.substring(i, i+1);
                nums[i] = Integer.valueOf(str);
            }
            set(nums);        
            return;
        }
        
        int[] nums = new int[column]; 
        if(length < column){
            /* TODO : optimize later */
            for(int i = 0, n = column; i < n; i++){
                if(column - length <= i){
                    String str = number.substring((i-column+length), (i-column+length+1));
                    nums[i] = Integer.valueOf(str);
                } else {
                    nums[i] = 0;
                }
            }
            
        } else {
            for(int i = 0, n = nums.length; i < n; i++){
                String str = number.substring(i, i+1);
                nums[i] = Integer.valueOf(str);
            }
        }
        set(nums);        
    }
    
    private void set(int[] nums){
        if(nums.length == 0)
            this.removeAllGeometries();        
        
        float width = 0.0045861113f;
        float height = 0.005291667f;
        
        int keta = nums.length;
        float w = width;//width
        float h = height/2;//height/2
        float offset = w / 2;//width/2
        float offsetAll = offset * keta;
        float[] coordinates = new float[12*keta];
        float[] texCoords = new float[8*keta];
        float[] normals = new float[12*keta];
        
        for (int i = 0, j = 0, k = 0; i < keta; i++, j += 12, k += 8) {
            int num = nums[i];
            coordinates[j] = w * i - offsetAll + shiftX;
            coordinates[j + 1] = h + shiftY;
            coordinates[j + 2] = shiftZ;
            normals[j] = 0;
            normals[j + 1] = 0;
            normals[j + 2] = 1;
            texCoords[k] =  num * 0.1f;
            texCoords[k + 1] = 0;

            coordinates[j + 3] = w * i - offsetAll + shiftX;
            coordinates[j + 4] = -h + shiftY;
            coordinates[j + 5] = shiftZ;
            normals[j + 3] = 0;
            normals[j + 4] = 0;
            normals[j + 5] = 1;
            texCoords[k + 2] = num * 0.1f;
            texCoords[k + 3] = 1;

            coordinates[j + 6] = w + w * i - offsetAll + shiftX;
            coordinates[j + 7] = -h + shiftY;
            coordinates[j + 8] = shiftZ;
            normals[j + 6] = 0;
            normals[j + 7] = 0;
            normals[j + 8] = 1;
            texCoords[k + 4] = num * 0.1f + 0.1f;
            texCoords[k + 5] = 1;

            coordinates[j + 9] = w + w * i - offsetAll + shiftX;
            coordinates[j + 10] = h + shiftY;
            coordinates[j + 11] = 0 + shiftZ;
            normals[j + 9] = 0;
            normals[j + 10] = 0;
            normals[j + 11] = 1;
            texCoords[k + 6] = num * 0.1f + 0.1f;            
            texCoords[k + 7] = 0;
        }
                
        QuadArray geom = new QuadArray(keta * 4, 
                GeometryArray.COORDINATES |
                GeometryArray.TEXTURE_COORDINATE_2 |
                GeometryArray.NORMALS);
        geom.setCoordinates(0, coordinates);
        geom.setTextureCoordinates(0, 0, texCoords);
        geom.setNormals(0, normals);
        
        geom.setCapability(Geometry.ALLOW_INTERSECT);
        geom.setCapability(GeometryArray.ALLOW_COUNT_READ);
        geom.setCapability(GeometryArray.ALLOW_FORMAT_READ);
        geom.setCapability(GeometryArray.ALLOW_COORDINATE_READ);

        this.removeAllGeometries();
        this.addGeometry(geom);
    }

    public static Texture getNumberTexture(Color color){
        BufferedImage image = createImage("0123456789", color);
        TextureLoader loader = new TextureLoader(image);                 
        return loader.getTexture();
    }
    
    static BufferedImage createImage(String str, Color fgColor) {
//        Font font = CosmoConfig.DATE_FONT;
        Font font = new Font("MS UI Gothic", Font.PLAIN, 32);
        BufferedImage image = new BufferedImage(2, 2,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(font);
        int strLength = g.getFontMetrics().stringWidth(str);
        int charHeight = g.getFontMetrics().getMaxAscent()
                + g.getFontMetrics().getMaxDescent();
        int width = strLength + 2;
        int height = charHeight;

//        Color bgColor = new Color(1f,1f,1f,1f);
        Color bgColor = new Color(0, 0, 0, 0);
        // ���᡼���κ�.
        image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setBackground(bgColor);
        g.clearRect(0, 0, width, height);
        g.setFont(font);
        
        //draw font shadow with offset(1 dot). 
//        g.setColor(Color.BLACK);
//        g.drawString(str, 1, g.getFontMetrics().getMaxAscent()+1);
        
        g.setColor(fgColor);
        g.drawString(str, 0, g.getFontMetrics().getMaxAscent());
        
        g.dispose();
        return image;
    }   
}
