package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

/**
 * Esta interface declara los metodos que deben implementar las clases que
 * deseen poder ejecutar un caso de uso.
 * Los componentes que implementen esta interface actuaran como un monitor
 * para el thread GeonUseCaseThreadManager, encargado de la animacion del 
 * caso de uso. Esto significa que el thread GeonUseCaseThreadManager se 
 * dormira al comenzar la animacion en el componente utilizando el metodo
 * useCaseThreadManagerWait, y se despertara al finalizar la misma con el
 * metodo useCaseThreadManagerWakeup.
 *   
 * @author Juan Feldman
 *
 */
public interface GeonUseCaseInterface {
	/**
	 * Se salva el valor a modificar durante la animacion del caso de uso,
	 * para que una vez finalizado se restaure el valor original.
	 */
	public abstract void saveOriginalValue();

	/**
     * Retorna el valor a modificar.
     * @return Object Valor a modificar.
     */
	public abstract Object getValueToChange();
	
	/**
	 * Se ejecutan las acciones que correspondan para la animacion
	 * del caso de uso.
	 * @param value Valor a cambiar.
	 * @param loopNumber numero de iteracion.
	 * @return Object Nuevo valor.
	 */
	public abstract Object loopAction(Object value, int loopNumber);
	
	/**
	 * Se aplican los cambios realizados en el metodo
	 * <b>loopAction</b>.
	 * @param value Nueva transparencia.
	 */
	public abstract void applyChange(Object value);
	
	/**
	 * Se deshacen los cambios realizados en el metodo
	 * <b>applyChange</b>.
	 */
	public abstract void restore();
}
