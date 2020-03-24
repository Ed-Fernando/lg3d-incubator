package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventSplash;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.smoother.AcceleratingVector3fSmoother;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;



/**
 * Esta clase representa la pantalla de Splash que se visualiza
 * al comenzar la aplicacion.
 * 
 * @author Juan Feldman
 *
 */
public class GeonSplashScreen extends Component3D {
	/** Contenedor del splah.*/
	private BranchGroup geonSplashBG = null;
	/** Contenedor de la animacion.*/
	private AnimationGroup geonSplashAG = null;

	/**
	 * Inicializa el Splash. 
	 */
	public GeonSplashScreen() {
		super();
		geonSplashBG = new BranchGroup();
		geonSplashAG = createLabel();
		geonSplashBG.addChild(geonSplashAG);
		this.addChild(geonSplashBG);
	}

	/**
	 * Ejecuta una animacion que muestra el splash en la pantalla. 
	 * 
	 * @param millisecs Tiempo que dura la animacion.
	 * @param nextStep Paso que se ejecutara cuando termine la animacion.
	 */
	public void zoomIntoView(int millisecs, int nextStep) {
		TranslationAnimationBoolean kwebSplashTA =
			new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.8f),
					new Vector3f(0f, 0f, 0.2f),
					millisecs);
		geonSplashAG.setAnimation(kwebSplashTA);
		LgEventConnector.getLgEventConnector().addListener(kwebSplashTA,
				new GenericEventAdapter(LgEvent.class,
						new GeonEventSplash(this, nextStep))
		);
		kwebSplashTA.setAnimationFinishedEvent(LgEvent.class);
		kwebSplashTA.performAction(null, true);
	}

	/**
	 * Ejecuta una animacion que saca el splash de la pantalla y la setea a invisible. 
	 * 
	 * @param millisecs Tiempo que dura la animacion.
	 * @param nextStep Paso que se ejecutara cuando termine la animacion.
	 */
	public void zoomOutOfView(int millisecs, int nextStep) {
		TranslationAnimationBoolean kwebSplashTA =
			new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.2f),
					new Vector3f(0f, 0f, 0.8f),
					millisecs,
					new AcceleratingVector3fSmoother());
		geonSplashAG.setAnimation(kwebSplashTA);
		LgEventConnector.getLgEventConnector().addListener(kwebSplashTA,
				new GenericEventAdapter(LgEvent.class,
						new GeonEventSplash(this, nextStep))
		);
		kwebSplashTA.setAnimationFinishedEvent(LgEvent.class);
		kwebSplashTA.performAction(null, true);
	}

	/**
	 * Crea la imagen para el splash.
	 * 
	 * @return AnimationGroup Contenedor de la animacion. 
	 */
	private AnimationGroup createLabel() {
		AnimationGroup ag = new AnimationGroup();
		try {
			ImagePanel splash = new ImagePanel(getClass().getResource("/resources/images/splash.png"), 0.08f, 0.05f);
			ag.addChild(splash);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ag;
	}

	/**
	 * Ejecuta el siguiente paso de la animacion.
	 * @param stepNumber Paso a ejecutar.
	 */
	public void nextStep(int stepNumber) {
		switch (stepNumber) {
		case 1:
			this.zoomIntoView(4000, 2);
			this.setVisible(true);
			break;
		case 2:
			this.zoomOutOfView(2000, 3);
			break;
		case 3:
			this.setVisible(false);
			detach();
			break;
		default:
		}
	}

}
