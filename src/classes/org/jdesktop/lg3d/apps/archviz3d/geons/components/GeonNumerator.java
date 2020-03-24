package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Hashtable;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonNumerator;

/**
 * Esta clase se utiliza para numerar las partes de un conector en la vista 
 * de Componentes y Conectores. Dichas partes necesitan ser numeradas para 
 * setearles su nombre y asi poder identificarlas univocamente.
 * 
 * @author Juan Feldman
 *
 */
public class GeonNumerator {
	/** Unica instancia de esta clase.*/
	private static GeonNumerator instance;
	/** Tabla de numeradores.*/
	private Hashtable<String, Integer> numeratorTable = null;
	
	/**
	 * Crea un GeonNumerator.
	 */
	private GeonNumerator() {
		numeratorTable  = new Hashtable<String, Integer>(); 
	}
	
	/**
	 * Retorna la unica instancia de esta clase.
	 * @return GeonNumerator Unica instancia de esta clase.
	 */
	public synchronized static GeonNumerator instance() {
		if (instance == null)
			instance = new GeonNumerator();
		return instance;
	}
	
	/**
	 * Se inicializa la tabla de numeradores. 
	 */
	public void initialize() {
		numeratorTable.clear();
	}
	
	/**
	 * Retorna el proximo valor del numerador para la clave asociada
	 * con dicho numerador.
	 * @param key Clave asociada con el numerador.
	 * @return int Valor del numerador.
	 */
	public int getNextValue(String key) {
		int value = 1;
		if (numeratorTable.containsKey(key)) {
			value = numeratorTable.get(key).intValue() + 1;
			numeratorTable.put(key, new Integer(value));
		}
		else 
			numeratorTable.put(key, new Integer(value));
		return value;
	}
}
