package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.jdesktop.lg3d.apps.archviz3d.PlEnginePool;
import org.jdesktop.lg3d.apps.archviz3d.SharedBrain;
import org.jdesktop.lg3d.apps.archviz3d.SharedPlEngine;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqFactory;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.CommandZAnimat;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Dispatcher;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZAnimat;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEventArch;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEventArg;


import JavaLog.PlList;
import JavaLog.PlObject;
import JavaLog.PlVar;


public class ZAnimat extends SharedBrain {

	SharedPlEngine localBrain;

	PlObject oldState, state;

	PlObject schema, oldSchema;
	/** Propiedades del ComponentAndConnectorMappings.xml.*/
	private HashMap<String, String> properties;
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

//	static {
//		URL url = Loader.class.getResource("/JavaLog/init");
//		PlEnginePool.instance().consult(url.getPath() + "/unit.pl", true);
//	}

	public ZAnimat(String FileName) {
		properties = new HashMap<String, String>();
		localBrain = PlEnginePool.instance().getNotPooled();

		script(FileName);

		initState();
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public boolean isProperty(String name) {
		return properties.containsKey(name);
	}

	public Set getNameProperty() {
		return properties.keySet();
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void command(String command, PlObject args) {
		new CommandZAnimat(this, command, args).run();
	}

	public String toString() {
		return super.toString() + " schema: " + this.fileNameOnly();
	}

	public void initState() {
		try {
			synchronized (localBrain) {

				if (localBrain.answerQuery("init_" + fileNameOnly()
						+ "_stateIni(State),z_init_" + fileNameOnly()
						+ "(globalState($0,state(State,[]),S1),Schema).",
						new Object[] { this })) {
					state((PlObject) (localBrain.answer().get("S1")));
					schema((PlObject) (localBrain.answer().get("Schema")));
				} 
				else
					logger.log(Level.WARNING, "Error de inicializacion de ZAnimat en :" + fileName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void schema(PlObject s) {
		if (s != null) {
			oldSchema = schema;
			schema = s;
		}
	}

	public PlObject schema() { // era String
		return schema;
	}

	public PlObject oldSchema() { // era String
		return oldSchema;
	}

	protected void state(PlObject s) {
		oldState = state;
		state = s;
	}

	public PlObject state() {
		return state;
	}

	public PlObject oldState() {
		return oldState;
	}

	public synchronized PlObject iVar(String ivName) {
		return value(ivName);
	}

	public synchronized boolean iVarExists(String ivName) {
		synchronized (localBrain) {
			boolean res = false;
			try {
				res = localBrain.answerQuery(
						"value($0 ,'" + ivName + "',Val).",
						new Object[] { schema() });
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Animat ivar fail: " + ivName + " "
						+ this.toString());
				e.printStackTrace();
			}
			return res;
		}
	}

	public synchronized PlObject value(String variable) {
		PlObject res = null;
		try {
			synchronized (localBrain) {
				localBrain.answerQuery("value($0 ,'" + variable + "',Val).",
						new Object[] { schema() });
				res = (PlObject) localBrain.answer().get("Val");
			} // end sync
		} catch (Exception e) {
			logger.log(Level.SEVERE, "val-fail:" + variable);
			logger.log(Level.SEVERE, "val-fail-schema:" + schema().toString());
			e.printStackTrace();
		}
		return res;
	}

	public synchronized void setiVar(String ivName, Object value) {

		synchronized (localBrain) {
			try {
				if (value instanceof String)
					localBrain.answerQuery("setValue( $0, NewSchema , '"
							+ ivName + "' ," + value + ").",
							new Object[] { schema() });
				else
					localBrain.answerQuery("setValue( $0, NewSchema , '"
							+ ivName + "' , $1).", new Object[] { schema(),
									value });

				schema((PlObject) (localBrain.answer().get("NewSchema")));

			} catch (Exception e) {
				logger.log(Level.SEVERE, "ERROR setiVar: " + ivName);
				e.printStackTrace();
			}
		}
	}

	public synchronized boolean execute(String command) {
		return execute(command, (HashMap) null);
	}

	public synchronized boolean execute(String command, HashMap map) {
		boolean result = false;

		try {
			PlObject auxState;
			String events;

			Dispatcher.instance().pause();
			setChanged();
			notifyObservers(new ZEventArg(this, ZEventArg.START_OPERATION,
					command));

			int par1 = command.indexOf('(');
			int par2 = command.indexOf(')');

			String s;

			if (par1 == -1)
				s = command + "(globalState($2,$1,NewState), $0 ,NewSchema).";
			else
				s = command.substring(0, par1 + 1)
				+ "globalState($2,$1,NewState), $0, NewSchema"
				+ (par1 == par2 - 1 ? "" : ",")
				+ command.substring(par1 + 1, command.length()) + ".";

			synchronized (localBrain) {
				if (localBrain.answerQuery(s, new Object[] { schema(), state(),
						this })) {
					// sacar Hastable de retorno utilizar directamente la que
					// devuelve PlEngine y devolverla

					schema((PlObject) (localBrain.answer().get("NewSchema")));
					auxState = (PlObject) (localBrain.answer().get("NewState"));
					if (map != null) {
						for (Iterator i = map.keySet().iterator(); i.hasNext();) {
							Object key = i.next();
							map.remove(key);
							map.put(key, localBrain.answer().get(key));
						}
					}

					localBrain.answerQuery("events($0,Events).",
							new Object[] { auxState });

					events = localBrain.answer().get("Events").toString();

					// clear events and set new state
					localBrain.answerQuery("clearEvents($0,NewState).",
							new Object[] { auxState });
					state((PlObject) (localBrain.answer().get("NewState")));

					setChanged();
					notifyObservers(new ZEventArg(this,
							ZEventArg.END_OPERATION, command));
					setChanged();
					notifyObservers(new ZEventArg(this,
							ZEventArg.STATE_CHANGED, events));
					result = true; // ejecucion correcta
				} else {
					setChanged();
					notifyObservers(new ZEventArg(this,
							ZEventArg.FAIL_OPERATION, command));
				}
			} // sync
		} catch (Exception e) {
			logger.log(Level.SEVERE, "FAIL OPRATION This: " + this + "\n\tCommand: " + command);
			setChanged();
			notifyObservers(new ZEventArg(this, ZEventArg.FAIL_OPERATION, command));
		}
		return result;
	}

	// public synchronized void execute(String command, PlList args) {
	// Requerimietno de ejecucion de otro PlEngine!!!!!
	public synchronized boolean execute(String command, PlObject args) {
		Dispatcher.instance().pause();
		boolean result = false;

		try {
			PlObject auxState;
			String events;

			String s;
			s = command + "(globalState($2,$1,NewState), $0 ,NewSchema";
			PlObject[] a = ((PlList) args).toArray();

			Object[] o;
			if (a == null)
				o = new Object[3];
			else
				o = new Object[a.length + 3];
			o[0] = schema();
			o[1] = state();
			o[2] = this;

			int io = 3;
			for (int i = 3; i < o.length; i++) {
				// a[i-2].getClass());

				/*
				 * correcto lo cambie por el de abajo resumido que anda ok if
				 * (a[i-3] instanceof PlVar) { if (!((PlVar)a[i-3]).bound()) { s += "
				 * ,ZZ_" + ((PlVar)a[i-3]).id(); } else { s += " ,$" + (io) ;
				 * o[io]= ((PlVar)a[i-3]).dereference(); io++; } } else { s += "
				 * ,$" + (io) ; o[io]=a[i-3]; ((PlObject)o[io]).dereference();
				 * io++; }
				 */

				if (a[i - 3] instanceof PlVar && !((PlVar) a[i - 3]).bound()) {
					s += " ,ZZ_" + ((PlVar) a[i - 3]).id();
				} else {
					s += " ,$" + (io);
					o[io] = ((PlObject) a[i - 3]).dereference();
					io++;
				}

			}
			s += ").";

			setChanged();
			notifyObservers(new ZEventArch(this, ZEventArg.START_OPERATION,
					command, o));

			synchronized (localBrain) {

				if (localBrain.answerQuery(s, o)) {
					// sacar Hastable de retorno utilizar directamente la que
					// devuelve PlEngine y devolverla

					if (a != null)
						for (int i = 0; i < a.length; i++) {
							if (a[i] instanceof PlVar) {
								if (!((PlVar) a[i]).bound()) {
									a[i].unify(((PlObject) localBrain.answer()
											.get("ZZ_" + ((PlVar) a[i]).id()))
											.dereference());
								}
							}
						}
					schema((PlObject) (localBrain.answer().get("NewSchema")));
					auxState = (PlObject) (localBrain.answer().get("NewState"));

					localBrain.answerQuery("events($0,Events).",
							new Object[] { auxState });

					events = localBrain.answer().get("Events").toString();

					// clear events and set new state
					localBrain.answerQuery("clearEvents($0,NewState).",
							new Object[] { auxState });
					state((PlObject) (localBrain.answer().get("NewState")));

					setChanged();
					notifyObservers(new ZEventArch(this,
							ZEventArg.END_OPERATION, command, o));
					setChanged();
					notifyObservers(new ZEventArch(this,
							ZEventArg.STATE_CHANGED, events, o));

					result = true;
				} else {
					setChanged();

					notifyObservers(new ZEventArch(this,
							ZEventArg.FAIL_OPERATION, command, o));
				}
			} // sync
		} catch (Exception e) {
			logger.log(Level.SEVERE, "FAIL OPRATION2 This: " + this + "\n\tCommand: "
					+ command + " " + args);
			e.printStackTrace();
			setChanged();
			notifyObservers(new ZEventArch(this, ZEventArg.FAIL_OPERATION,
					command, null));

		}
		return result;
	}

	public synchronized ZAnimat createObject(String name) {
		return ArqFactory.instance().create(this, name);
	}

	public String nameForCreation(String name) {
		return fileNameBase() + "/" + name + ".tex.pl";
	}

}