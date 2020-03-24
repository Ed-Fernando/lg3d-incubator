 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

package edu.cmu.sun.view;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

import edu.cmu.sun.components.BoxComponent;
import edu.cmu.sun.components.ImageComponent;
import edu.cmu.sun.components.TextComponent;
import edu.cmu.sun.controller.WindowController;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;
import edu.cmu.sun.model.WindowModel;

public class WindowBarView extends Component3D implements LgEventListener, ModelListener{

	WindowModel model;
	WindowController controller;
	
	// sub-components:
	BoxComponent topBar;
	BoxComponent bottomBar;
	Component3D images;
	ImageComponent closeButton;
	ImageComponent unpinnedButton;
	ImageComponent pinnedButton;
	ImageComponent knurling;
	ImageComponent searchButton;
	ImageComponent menuButton;
	TextComponent titleText;
	
        static final String RESOURCE_DIR = "edu/cmu/sun/resources/menubar/";
        
	// drawing attributes
        static final Appearance APPEARANCE = new SimpleAppearance(0.6f, 0.6f, 0.6f);
	static final float WIDTH = WindowView.WIDTH;
	static final float TOP_HEIGHT = 20f * SceneModel.P2M; // total is 55
	static final float BOTTOM_HEIGHT = 35f * SceneModel.P2M;
	static final float DEPTH = WindowView.DEPTH;
	static final float IMAGE_Z = WindowView.Z_LAYERING ;
	static final float WIDGET_HEIGHT = 23 * SceneModel.P2M; // was 21
	static final float SMALL_WIDGET_WIDTH = 23 * SceneModel.P2M; // was 21
	static final float LARGE_WIDGET_WIDTH = 33 * SceneModel.P2M; // was 31
	static final float SPACING = 5 * SceneModel.P2M; //was 7
	static final float WIDGET_Y = SPACING + WIDGET_HEIGHT - TOP_HEIGHT - BOTTOM_HEIGHT + 2*SceneModel.P2M;
	static final float TITLE_Y = - 21f * SceneModel.P2M; ;
	static final float TITLE_X = SPACING;
	static final float CLOSE_WIDGET_X = SPACING;
	static final float PIN_WIDGET_X = SPACING * 2f + SMALL_WIDGET_WIDTH;
	static final float KNURLING_X = PIN_WIDGET_X + LARGE_WIDGET_WIDTH + SPACING;
	static final float KNURLING_NOCLOSE_X =  SPACING;
	static final float KNURLING_WIDTH = 133 * SceneModel.P2M;
	static final float KNURLING_NOCLOSE_WIDTH = (133 * SceneModel.P2M) + PIN_WIDGET_X + LARGE_WIDGET_WIDTH;
	static final float SEARCH_WIDGET_X = WIDTH - SMALL_WIDGET_WIDTH * 2f - SPACING * 2f;
	static final float MENU_WIDGET_X = WIDTH - SMALL_WIDGET_WIDTH - SPACING;
	
	
	
	
	public WindowBarView(WindowModel model, WindowController controller)
	{
		this.model = model;
		model.addModelListener(this);
		this.controller = controller;
		this.addListener(this);
		buildSubComponents();
	}
	
	private void buildSubComponents()
	{
		// all images float on the image plane:
		images = new Component3D();
		images.setTranslation(0f, 0f, IMAGE_Z); //raized up a little
		this.addChild(images);
		

		// close button:
		closeButton = new ImageComponent(RESOURCE_DIR + "close.png", SMALL_WIDGET_WIDTH, WIDGET_HEIGHT);
		closeButton.setTranslation(CLOSE_WIDGET_X, WIDGET_Y, 0f);
		closeButton.addListener(this);
		images.addChild(closeButton);
		
		// pin button:
		unpinnedButton = new ImageComponent(RESOURCE_DIR + "unpinned.png", LARGE_WIDGET_WIDTH, WIDGET_HEIGHT);
		unpinnedButton.setTranslation(PIN_WIDGET_X, WIDGET_Y, 0f);
		unpinnedButton.addListener(this);
		images.addChild(unpinnedButton);
		pinnedButton = new ImageComponent(RESOURCE_DIR + "pinned.png", LARGE_WIDGET_WIDTH, WIDGET_HEIGHT);
		pinnedButton.setTranslation(PIN_WIDGET_X, WIDGET_Y, 0f);
		pinnedButton.addListener(this);
		images.addChild(pinnedButton);
		
		
		// knurling:
		float knurlingX = (model.isCloseable()) ? KNURLING_X : KNURLING_NOCLOSE_X;
		float knurlingWidth = (model.isCloseable()) ? KNURLING_WIDTH : KNURLING_NOCLOSE_WIDTH;
		knurling = new ImageComponent(RESOURCE_DIR + "knurling.png", knurlingWidth, WIDGET_HEIGHT);
		knurling.setTranslation(knurlingX, WIDGET_Y, 0f);
		images.addChild(knurling);
		
                
		// search button:
		searchButton = new ImageComponent(RESOURCE_DIR + "search.png", SMALL_WIDGET_WIDTH, WIDGET_HEIGHT);
		searchButton.setTranslation(SEARCH_WIDGET_X, WIDGET_Y, 0f);
		searchButton.addListener(this);
		images.addChild(searchButton);
		
		// menu button:
		menuButton = new ImageComponent(RESOURCE_DIR + "menu.png", SMALL_WIDGET_WIDTH, WIDGET_HEIGHT);
		menuButton.setTranslation(MENU_WIDGET_X, WIDGET_Y, 0f);
		menuButton.addListener(this);
		images.addChild(menuButton);
		
		// Title Text:
		String title = model.getTitle();
		titleText = new TextComponent(title, true);
		titleText.setTranslation(TITLE_X, TITLE_Y, 0f);
		images.addChild(titleText);
		
		// draw the boxy part
		float topWidth = titleText.getWidth() + SPACING * 2f;
		topBar = new BoxComponent(topWidth, TOP_HEIGHT, DEPTH, APPEARANCE);
		bottomBar = new BoxComponent(WIDTH, BOTTOM_HEIGHT, DEPTH, APPEARANCE);
		bottomBar.setTranslation(0f, -TOP_HEIGHT, 0f);
		
		this.addChild(topBar);
		this.addChild(bottomBar);
		
		
		
		
		update();
	}
	
	// -------------- update state from model
	
	public void modelChanged(Model m) {
		if (m == model) update();
	}
	
	public void update()
	{
		if (!model.isCloseable())
		{
			closeButton.setVisible(false);
			pinnedButton.setVisible(false);
			unpinnedButton.setVisible(false);
		}
		else if (model.isPinned())
		{
			pinnedButton.setVisible(true);
			unpinnedButton.setVisible(false);
		}
		else
		{
			unpinnedButton.setVisible(true);
			pinnedButton.setVisible(false);
		}
	}

	// ------------------- event handling
	
	public void processEvent(LgEvent e) {
		if (e.getClass() != MouseButtonEvent3D.class) return;
		MouseButtonEvent3D m = (MouseButtonEvent3D) e;
		if (m.isClicked())
		{
			if (m.getSource() == closeButton)
			{
				controller.close();
			}
			else if (m.getSource() == unpinnedButton)
			{
				controller.pin();
			}
			else if (m.getSource() == pinnedButton)
			{
				controller.unpin();
			}
			else if (m.getSource() == searchButton)
			{
				controller.search();
			}
			else if (m.getSource() == menuButton)
			{
				// TODO: handle menu bar event.
			}
			else
			{
				controller.makeActive();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public Class<LgEvent>[] getTargetEventClasses() {
		Class[] types = new Class[] {MouseButtonEvent3D.class};
		return types;
	}

	public static float getHeight() {
		return TOP_HEIGHT + BOTTOM_HEIGHT;
	}


	
}
