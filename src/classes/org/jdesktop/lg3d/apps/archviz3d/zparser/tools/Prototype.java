package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

import java.util.*;
import java.io.*;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.Prototype;
import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.ZType;


public class Prototype implements Cloneable {
    /**
     * A container for a slots, the key is the slot name.
     */
    protected Hashtable slots;

    /**
     * Vector of names of slots where there are Prototypes in a
     * upper level. This slot have her own slot plus slots from
     * all Prototypes in up.
     */
    protected Vector up;


    /**
     * private Constructor
     */
    //private Prototype() {}


    /**
     * Constructor
     *
     * @param  name  name of this Prototype.
     */
    public Prototype(String name) {
      //System.out.println("ProName: " + name);
        slots = new Hashtable();
        up = new Vector();
        slot("__name_prot");
        put("__name_prot",name);
     }


    public String getName() {
	return (String) get("__name_prot");
    }

    /**
     * Clone this Prototype, slots and up.
     *
     * @see    java.lang.Cloneable
     */
    public Object clone() {
        try {
            Prototype p=(Prototype) super.clone();
            p.slots=(Hashtable)p.slots.clone();
            p.up=(Vector)p.up.clone();
            return p;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }


    /**
     * @deprecated by jvlio, replaced by {@link #addSlot(String)}
     */
    public void slot(String slot) {
        slot(slot,false);
    }
    /**
     * Same that {@link #addSlot(String,boolean)}, but the
     * boolean olways set to false.
     *
     * @param  slotname  the name of the new slot
     * @see    #addSlot(String,boolean)
     */
    public void addSlot(String slotname) {
        addSlot(slotname,false);
    }


    /**
     * @deprecated by jvlio, replaced by {@link #addSlot(String,boolean)}
     * @see  #addSlot(String,boolean)
     */
    public void slot(String slot, boolean parent) {
        //System.out.println("Prototype:slot");
        if (slots.containsKey(slot))
            System.out.println("Error slot ya declarado !!!!!!!!!!!!: " + slot);
        else  { //System.out.println("Slot: " + slot);
            slots.put(slot,"");
            if (parent) up.add(slot);
        }
    }
    /**
     * Add a new slot to <code>Prototype</code> with a empty String in. If
     * there is a slot with that name then print a error mesg.
     * If <code>parent</code> is true then most be put this slot name in up,
     * because the contents of this slot is a Prototype father??.
     * MOST BE CALL addSlot(String,boolean)
     *
     * @param  slotname  the name of the new slot
     * @param  parent    a boolean flag, if true then put this
     *                   name in up
     */
    public void addSlot(String slotname, boolean parent) {
        //System.out.println("Prototype:addSlot");
        if (slots.containsKey(slotname))
            System.out.println("Error slot ya declarado !!!!!!!!!!!!: " + slotname);
        else  {
            slots.put(slotname,"");
            if (parent) up.add(slotname);
        }
    }
 public void removeSlot(String slotname) {
        //System.out.println("Prototype:addSlot");
        slots.remove(slotname);
    }

public boolean containsSlot(String slotName) {
    return (slots.containsKey(slotName));
}


    /**
     * @deprecated by jvlio, replaced by {@link #getSlot(String)}
     * @see  #getSlot(String)
     */
    public Object get(String slotname) {
        return getSlot(slotname);
    }
    /**
     * Return the contents of slot "slotname", if in this Prototype
     * havn't a slot named slotname, then search in the Prototypes
     * in the slots named by the vector up, if nathing in there then
     * returns null.
     *
     * @param  slotname  name of slot to search.
     * @return if slotname isn't found then null, otherwise the contents
     *         of slotname.
     */
    public Object getSlot(String slotname) {
        Object result=null; Prototype prot;
        if (slots.containsKey(slotname))
            return slots.get(slotname);
        else {
            //busca en los prototipos que estan en up (en up estan los nombre de los slots!)
            for (Enumeration e = up.elements(); e.hasMoreElements();) {
                prot=(Prototype) slots.get((String)e.nextElement());
                result=prot.getSlot(slotname);
                if (result!=null)
                    return result;
            }
        }
        return null;
    }

    public String getSlotString(String slotname) {
        return (String) this.getSlot(slotname);
    }

    public Vector getSlotVector(String slotname) {
        return (Vector) this.getSlot(slotname);
    }

     public Prototype getSlotPrototype(String slotname) {
        return (Prototype) this.getSlot(slotname);
    }

    public ZType getSlotZType(String slotname) {
        return (ZType) this.getSlot(slotname);
    }

    /**
     * @deprecated by jvlio, replaced by {@link #setSlot(String,Object)}
     * @see  #setSlot(String,Object)
     */
    public void put(String slotname, Object obj ) {
        setSlot(slotname, obj);
    }
    /**
     * Add a new slot and put obj in them
     *
     * @param  slotname  name of new slot.
     * @param  obj       think to put in slot slotname
     */
    public void setSlot(String slotname, Object obj ) {
        if (slots.containsKey(slotname))
            slots.put(slotname,obj);
    }


    //public Object get0(String s) {
    //  if (slots.containsKey(s)) return slots.get(s);
    //  else return null;
    //
    //}

    /**
     * Returns the Prototype that contains a slot with
     * name=slotname.
     *
     * @param  slotname  name of slot to search
     * @return the Prototype container of a slot named slotname
     */
    public /*Object*/Prototype container(String slotname) {
        //Object result=null; Prototype prot;
        if (slots.containsKey(slotname))
            return this;
        else {
            //if (up.size()>0)
                for (Enumeration e = up.elements(); e.hasMoreElements();) {
                    Prototype prot=(Prototype) slots.get((String)e.nextElement());
                    /*Object*/Prototype result=prot.container(slotname);
                    if (result!=null) return result;
                }
        }
        return null;
    }


    /**
     * Returns the name of Prototype container of slotname.
     *
     * @param  slotname  name of slot to search.
     * @return the name of container.
     */
    public String containerName(String slotname) {
        Prototype p = /*(Prototype)*/container(slotname);
        if ( (p != null) &&  (p.get("name") != null) )
            return (String)p.get("name");
        else
            return "";
    }



    public String toString() {
        //System.out.println("Prototype:toString");
        return getClass().getName()+ "named "+getSlot("name")+ "@"+ (slots.keys()+up.toString()) ;
    }


    public String dump() {
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        dump(new PrintStream(ostream,true));
        return ostream.toString();
    }

    public void dump(PrintStream ostream) {
        ostream.println("Name Prototype (__name_prot): "+get("__name_prot"));
        ostream.println("slot name: "+get("name"));

        for (Enumeration slots_keys=slots.keys(); slots_keys.hasMoreElements() ;) {
            String slotkey = (String)slots_keys.nextElement();
            Object slotval = slots.get(slotkey);
            if ( slotkey!="__name_prot" && slotkey!="name" )
                if (slotval instanceof Prototype)
                    ostream.println("slot "+slotkey+": "+"Prototype! name:"+((Prototype)slotval).get("name"));
                else
                    ostream.println("slot "+slotkey+": "+slotval);
        }

        ostream.println("up: "+up);
        ostream.println();
    }


}