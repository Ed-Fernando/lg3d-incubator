package org.jdesktop.lg3d.apps.presentoire.transition;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.jdesktop.lg3d.apps.presentoire.PresentoireDocument;

/**
 * Implements a factory for the transitions.
 * @author Pierre Ducroquet
 *
 */
public class TransitionFactory {
	/**
	 * Returns the transition from document "doc" with name "name".
	 * It also loads the .class file if it's a Java transition in the document.
	 * @param name the transition we're looking for
	 * @param doc the document we're displaying
	 * @return the transition
	 */
	public static Transition getTransition (String name, PresentoireDocument doc) {
		if (name.endsWith(".class")) {
			// Load the Transition from the specified class file...
			// Ok that's easier said than done.
			System.out.println("This is where the fun begins");
			System.out.println(name);
			String fileName = name.substring(name.lastIndexOf('/') + 1);
			String className = name.substring(0, name.lastIndexOf('.')).replace('/', '.');
			System.out.println(className);
			System.out.println(fileName);
			try {
				// First step : load the class in this byte array.
				byte[] classdata;
				DataInputStream dis = new DataInputStream(doc.getZIPInputStream(fileName));
				if (dis.available() <= 0) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int bc = 0;
					byte[] buf = new byte[1024];
					while ((bc = dis.read(buf)) > 0) {
						baos.write(buf);
						if (bc < 1024) break;
					}
					baos.close();
					classdata = baos.toByteArray();
				} else {
					classdata = new byte[dis.available()];
					dis.readFully(classdata);
				}
				dis.close();
				// Ok, now we can try to open this byte array as a Java class.
                try {
                	Class myClass = new TransitionClassLoader().getTransitionClass(className, classdata, 0, classdata.length);
					return (Transition) myClass.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch ( java.lang.LinkageError err) {
	                System.err.println("Linkage error loading: " + name);
	                err.printStackTrace();
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		// Ok, other cases are simpler.
		if (doc.getScriptTransition(name) != null)
			return doc.getScriptTransition(name);
		if ("rotation".equals(name))
			return new RotationTransition();
		if ("scale".equals(name))
			return new ScaleTransition();
		if ("transparency".equals(name))
			return new TransparencyTransition();
		return null;		
	}
	
	/**
	 * Implements a really really simple ClassLoader.
	 * In fact, it's here only to expose the defineClass call.
	 * @author Pierre Ducroquet
	 *
	 */
	private static class TransitionClassLoader extends ClassLoader {
		public TransitionClassLoader() {
			super(TransitionFactory.class.getClassLoader());
		}
		
		public Class getTransitionClass(String name, byte[] b, int off, int len) {
			return defineClass(name, b, off, len);
		}
	}
}
