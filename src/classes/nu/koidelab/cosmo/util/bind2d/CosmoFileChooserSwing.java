package nu.koidelab.cosmo.util.bind2d;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileView;

import nu.koidelab.cosmo.manager.CSDGeneralManager;

public class CosmoFileChooserSwing extends JFrame {

	public MyFileChooser fileChooser;

	private LgExplorerSwing lgExp;

	public CosmoFileChooserSwing(LgExplorerSwing lgExp) {
		setTitle("[Cosmo FileChooser]");
		setSize(320, 240);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.lgExp = lgExp;
	}

	public JPanel getFileChooserPanel(String path) {
		JPanel panel = new JPanel();

		fileChooser = new MyFileChooser(path);
		fileChooser.setFileView(new IconFileView());
		panel.add(fileChooser);

		return panel;
	}

	private class MyFileChooser extends JFileChooser {
		public MyFileChooser(String string) {
			super(string);
		}

		@Override
		public void approveSelection() {
			super.approveSelection();
			lgExp.selection(getSelectedFile());
		}
		@Override
		public void cancelSelection() {
			super.cancelSelection();
			lgExp.cancel();
		}
	}

	private class IconFileView extends FileView {
		HashMap<String, ImageIcon> map = new HashMap<String, ImageIcon>();

		public IconFileView() {}

		@Override
		public Icon getIcon(File f) {
			String ext = CSDGeneralManager.getInstance().getFileManager().getFileExtension(f.getName());
			if(ext == null)
				return super.getIcon(f);		
			if (map.containsKey(ext)) {
				return map.get(ext);
			} else {
				String fname = CSDGeneralManager.getInstance().getFileManager().getIconFilename(ext);
				if(fname == null){
					return super.getIcon(f);		
				} else {
					ImageIcon icon = new ImageIcon( getImage(fname).getScaledInstance(32, 32, Image.SCALE_FAST) );
					map.put(ext, icon);
					return icon;					
				}
			}
		}
		
		private BufferedImage getImage(String filename){
	        Iterator readers = ImageIO.getImageReadersBySuffix(CSDGeneralManager.getInstance().getFileManager().getFileExtension(filename));
	        ImageReader reader = (ImageReader)readers.next();
	        BufferedImage image = null;
	        try {
	            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(filename);
	            if (inStream == null) {
	                inStream = new FileInputStream(filename);
	            }
	            
	            ImageInputStream stream = ImageIO.createImageInputStream(inStream);
	            reader.setInput(stream);
	            image = reader.read(0);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        
	        return image;
		}		
	}
}
