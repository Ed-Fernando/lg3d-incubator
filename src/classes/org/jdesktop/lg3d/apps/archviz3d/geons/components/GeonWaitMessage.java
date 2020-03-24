package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Vector;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.Sparkle;
import org.jdesktop.lg3d.wg.Component3D;



/**
 * Esta clase representa un mensaje de espera.
 * 
 * @author Juan Feldman
 *
 */
public class GeonWaitMessage extends Component3D implements Runnable {
	/** Imagen para el mensaje.*/
	private ImagePanel image = null;
	/** Thread para hacer la animacion.*/
	private Thread thread = null;
	/** Imagenes del borde del mensaje.*/
	private Vector<Primitive> border = null;
	/** Indice de la imagen del border a animar.*/
	private int index = 0;
	/** Ancho del mensaje.*/
	private float width;
	/** Largo del mensaje.*/
	private float height;

	/** Tiempo que se duerme el thread que anima una imagen del borde.*/
	private final int SLEEP_TIME = 100;
	/** Numero de loops que se hacen para animar una imagen del borde.*/
	private final int NUMBER_LOOPS = 10;
	/** Valor que se utiliza para animar una imagen del borde.*/
	private final float VALUE = 0.3f;  
	
	/**
	 * Instancia el GeonWaitMessage.
	 * @param imageName Nombre de la imagen que contiene el
	 * mensaje de espera.
	 * @param width Ancho del mensaje.
	 * @param height Largo del mensaje.
	 */
	public GeonWaitMessage(String imageName, float width, float height) {
		border = new Vector<Primitive>();
		this.width = width;
		this.height = height;
		
		// Creo la imagen
		try {
			image = new ImagePanel(getClass().getResource("/resources/images/" + imageName), width, height);
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.addChild(image);
		
		// Creo la mancha que va detras de la imagen 
		Sparkle sparkle = new Sparkle(0.4f, new Color4f(1.0f, 1.0f, 1.0f, 1));
		this.addChild(sparkle);

		// Creo el borde
		//createBorder();
	}

	/**
	 * Crea el borde del mensaje 
	 */
	@SuppressWarnings("unused")
	private void createBorder() {
		// Creo la linea de abajo
		createLine(-width/2 + 0.006f, -height/2 - 0.005f, 1, 0, 8);
	}
	
	/**
	 * Crea una linea del borde
	 * @param xFrom Punto de inicio en X.
	 * @param yFrom Punto de inicio en Y.
	 * @param dirX Direccion en X.
	 * @param dirY Direccion en Y.
	 * @param dirY Direccion en Y.
	 * @param numberImage Numero de images a agregar.
	 */
	private void createLine(float xFrom, float yFrom, int directionX, int directionY, int numberImage) {
		for (int i = 0; i < numberImage; i++) {
			Appearance appearance = Factory3D.instance().createAppearance(new Color3f(0.0f, 0.0f, 0.6f));
			Box image = new Box(0.005f, 0.003f, 0.001f, appearance);
			Component3D container = new Component3D();
			container.addChild(image);
			container.setTranslation(xFrom + i * directionX * 0.012f, yFrom + i * directionY * 0.012f, 0.0f);
			
			this.addChild(container);
			border.add(image);
		}
	}
	
	/**
	 * Comienza la animacion.
	 */
	public void startAnimation() {
		index = 0;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Ejecuta la animacion del borde.
	 */
	public void run() {
		Thread currentThread = Thread.currentThread();
		int direction = 1;
		this.setAnimation(new NaturalMotionAnimation(SLEEP_TIME * NUMBER_LOOPS));
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		while (currentThread == thread) {
			Thread appearanceThread = new Thread(new MyRunnable());
			appearanceThread.start();
			
			try {
				Thread.sleep(SLEEP_TIME * NUMBER_LOOPS + 100);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if (index == border.size() - 1)
				direction = -1;
			else if (index == 0)
				direction = 1;
				
			index += direction;
		}
	}
	
	/**
	 * Se elimina el mensaje y se frena la animacion
	 * del borde.
	 */
	public void detach() {
		super.detach();
		thread = null;
	}
	
	/**
	 * Esta clase se utiliza para animar una imagen del borde.
	 * 
	 * @author Juan Feldman 
	 *
	 */
	public class MyRunnable implements Runnable {
		
		/**
		 * Ejecuta la animacion de una imagen del borde.
		 */
		public void run() {
			// Obtengo la apariencia de la imagen a animar 
			Appearance appearance = border.elementAt(index).getAppearance();

			// Obtengo el color de la imagen a animar 
			Color3f originalColor = new Color3f();
			Color3f color = new Color3f();
			appearance.getMaterial().getDiffuseColor(originalColor);
			color = (Color3f)originalColor.clone();

			// Realizo la animacion 
			for (int loopNumber = 0; loopNumber < NUMBER_LOOPS; loopNumber++) {
				// Calculo el nuevo color
				if (loopNumber < NUMBER_LOOPS/2) {
					color.x += VALUE;
					color.y += VALUE;
					color.z += VALUE;
				}
				else {
					color.x -= VALUE;
					color.y -= VALUE;
					color.z -= VALUE;
				}
				
				// Seteo el nuevo color
				appearance.getMaterial().setDiffuseColor(color);
			
				try {
					Thread.sleep(SLEEP_TIME);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			// Vuelvo el color de la imagen a su estado original
			appearance.getMaterial().setDiffuseColor(originalColor);
		}
	}
	
}
