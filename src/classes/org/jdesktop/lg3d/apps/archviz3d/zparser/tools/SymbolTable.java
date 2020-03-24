/**
 * SymbolTable.java
 *
 *
 * Created: Tue Jun 29 14:35:38 1999
 *
 * @author Alfredo Teyseyre
 * @version
 */

package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

import antlr.collections.AST;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.Prototype;
import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.PrototypeFactory;
import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.ZType;

import java.util.*;
import java.lang.reflect.Array;
import java.io.*;

public class SymbolTable  {


    protected Stack symbols;
    protected int lastVar;

    String[][] inops =
        {{},
        {"\\map"},
        {"\\upto"},
        {"+","-","\\uni","\\union","\\setminus","\\cat","\\uplus","\\uminus"},
        {"*","\\div","/","\\mod","\\int","\\inter","\\ires","\\sres","\\comp","\\fcmp","\\filter","\\cmp","\\otimes",},
        {"\\oplus","\\bcount"},
        {"\\dres","\\rres","\\ndres","\\dsub","\\nrres","\\rsub"},
        {}
       };


    String[] inrels =
        { "=","<",">","\\leq","\\geq","\\neq",                                            //comparacion
         "\\in","\\mem","\\notin","\\nem","\\nmem","\\subset","\\psubs",                 //sets
         "\\partition","\\subs","\\subseteq","\\psups","\\supset","\\sups","\\supseteq"//sets
         
        };

    String[] ingens =
        {"\\rel","\\fun","\\tfun","\\inj","\\tinj","\\surj","\\tsurj","\\ffun","\\pfun", //relation and functions
         "\\pinj","\\psur","\\psurj","\\bij","\\finj"                                    //relation and functions
        };

    String[] prerels = {};

    String[] pregens =
        {"\\#", "\\dom","\\ran",   //alf:no va aca son word set relation
         "\\fset","\\finset",       //set
         "\\rev" , "\\head","\\tail" ,"\\front","\\last",        //sequence - no se si corresponde 
         "\\nth",               //tupla nth elemento - no se si va?
         "\\sort",               //sort tuplas por nth elemento - no se si va?
         "\\new"                    //object behaviour
        };

    String[] postops =
        { "\\plus"
        };

    protected PrototypeFactory factory;

    /**
     * Constructor
     */
    // modificar operaciones y ver manejo de pila
    public SymbolTable(PrototypeFactory f) {
        setFactory(f);
        lastVar=0;
        symbols = new Stack();

        //crea el Prototype Root
        Prototype p=getFactory().copy("declarations");
        p.setSlot("name","Root");
        symbols.push(p);

	//add_symbol("set","\\emptyset","[]");

        for (int i=0; i < Array.getLength(inops); i++)
            for (int j=0; j < Array.getLength(inops[i]); j++)
                add_inops(inops[i][j],i);

        for (int i=0; i < Array.getLength(inrels); i++)
            add_inrels(inrels[i]);

        for (int i=0; i < Array.getLength(prerels); i++)
            add_prerels(prerels[i]);

        for (int i=0; i < Array.getLength(pregens); i++)
            add_pregens(pregens[i]);

        for (int i=0; i < Array.getLength(postops); i++)
            add_postops(postops[i]);

        for (int i=0; i < Array.getLength(ingens); i++)
            add_ingens(ingens[i]);

        //Prototype pro= ( (Prototype)getCurrent().getSlot("\\#") );
        //System.out.println("Prototipo # prolog slot: " + ((String)pro.getSlot("prolog")) );
        //pro.setSlot("prolog","length");
        //System.out.println("Prototipo # prolog slot: " + ((String)pro.getSlot("prolog")) );
        //.setSlot("prolog","length");
        //System.out.println("# toProlog: " + this.toProlog("\\#"));

        //------to Prolog .........

        ( (Prototype)getCurrent().getSlot("\\#") ).setSlot("prolog","length");
        ( (Prototype)getCurrent().getSlot("\\leq") ).setSlot("prolog","<=");
        ( (Prototype)getCurrent().getSlot("\\geq") ).setSlot("prolog",">=");
        ( (Prototype)getCurrent().getSlot("\\neq") ).setSlot("prolog","\\=");
        ( (Prototype)getCurrent().getSlot("\\cat") ).setSlot("prolog","append");


        //------to Prolog  is isInfix .........

        String[] infixprolog ={ "\\leq", "\\geq", "\\neq",  "=", "<",">", "+", "-" , "/" , "*"
        };

        for (int i=0; i < Array.getLength(infixprolog); i++)
            ( (Prototype)getCurrent().getSlot(infixprolog[i]) ).setSlot("operator","infix");


        //tipos predefinidos


        add_type("\\nat",new ZType("\\nat","\\nat"));
        add_type("\\real",new ZType("\\real","\\real"));
        add_type("\\defs",new ZType("\\defs","\\defs"));



    }


    /**
     * Returns the PrototypeFactory. Replace method
     * factory()
     *
     * @return  factory
     */
    public PrototypeFactory getFactory() {
            return factory;
    }

    /**
     * Set the PrototypeFactory. Replace method
     * factory(PrototypeFactory).
     *
     * @param  f  a PrototypeFactory to replace.
     */
    public void setFactory(PrototypeFactory f) {
        factory=f;
    }

    /**
     * Returns the Stack symbols. Replace method
     * stack()
     *
     * @return  symbols
     */
    public Stack getSymbols(){
        return symbols;
    }


    public void initVarGen() {
        lastVar=0;
    }

    /**
     * retorna algo del estilo "Z_TEMPxxx", donde xxx=al valor de lastVar
     */
    public String varTemp() {
        lastVar++;
        return "Z_TEMP" + Integer.toString(lastVar);
    }

    public String getLastVarTemp() {
        return "Z_TEMP" + Integer.toString(lastVar);
    }

    /**
     * Put "value" in slot "slot" from prototype "prototype".
     *
     * @param  prototype  prototype to set.
     * @param  slot       slot to set.
     * @param  slotvalue  slot's value
     * @see    #getCurrent
     */
    public void put(String prototype, String slot, Object slotvalue) {
       ((Prototype)getCurrent().getSlot(prototype)).setSlot(slot,slotvalue);
    }

    public Object get(String slot) {
        //System.out.println("GET SLOT:" + slot);
       return getCurrent().getSlot(slot);
    }

    public Object get(String prototype, String slot) {
       return ((Prototype) getCurrent().getSlot(prototype)).getSlot(slot);
    }

    public boolean contains(String prototype, String slot, Object value) {
       return  value.equals( ((Prototype) getCurrent().getSlot(prototype)).getSlot(slot));
    }

    /**
     * Return the Prototype in top of stack
     *
     * @return top of stack
     */
    public Prototype getCurrent() {
       Object item = symbols.peek();
       //System.out.println("SymbolTable:getCurrent: current="+item);
       return (Prototype) item;
    }



    public Object push(Object item) {
        //System.out.println("SymbolTable:push: pushing "+item);
        return symbols.push(item);
    }

    public Object push(String item) {
       // System.out.println("SymbolTable:push: pushing "+item);
        return symbols.push( this.getCurrent().getSlot(item) );
    }



    public Object pop() {
        Object item = symbols.pop();
        //System.out.println("SymbolTable:pop: poping "+item);
        return item;
    }



    /**
     * Agrega un nuevo prototipo, por default este tendra
     * un slot "name" y otro "prolog", tambien se agrega
     * al Prototye "current" (posible Root??) como un slot
     * de nombre name.
     *
     * @param   prototype   type of prototype
     * @param   symbol      prototype name of type prototype
     * @return  new Prototype where the values of slots are:
     *          "__name_prot"=prototype
     *          "name"=name
     *          "prolog"=(name.charAt(0) != '\\')? name : "z_"+name
     * @see     tools.Prototype
     */
    private Prototype putProt(String name, String prototype) {
        //System.out.println("PutProt in: " + (String)peek().get("name") + " :" + prot + "   symbol:" + symbol);

        //le pide a factory que haga un clon del prototipo "prototype"
        Prototype p=getFactory().copy(prototype);
        //le agrega el slot "name"->name
        p.setSlot("name",name);

        //egrega el slot prolog
        if (name.charAt(0) != '\\')
           //si name no enpieza con '\' => en el slot "prolog"->name
           p.setSlot("prolog",name);
        else
           //si empieza con '\\' => es una operacion => egrega "z_" al principio en el slot prolog
           p.setSlot("prolog", name.substring(1));
 //          p.setSlot("prolog","z_" + name.substring(1));

        // LO AGREGA A Root??? -> al root de esta especificacion!!
        //en el current agrega el slot name
        getCurrent().addSlot(name);
        //en el curent, slot symbol pone el Prototype recien creado
        getCurrent().setSlot(name,p);

        return p;
    }

    public String toProlog(String symbol) {
        String s= (String) getProt(symbol,"prolog");
        if (s == null) return null;
        else return new String(s);
    }

    public String prologOperator(String symbol) {
        String s= (String) getProt(symbol,"operator");
        if (s == null) return "prefix";
        else return s;

    }

    /**
     * Returns the contents of slot "slot" in Prototype "symbol".
     *
     * @param  prototype  name of prototype.
     * @param  slot       name of slot
     * @return the Object stored in slot "slot" in
     *         Prototype named "symbol".
     */
    private Object getProt(String prototype, String slot){
        //System.out.println("SymbolTable:getProt: symbol="+prototype+"  slot="+slot); //by jvlio

        //Prototype p=(Prototype) (getCurrent().get(symbol));

        if (getCurrent() != null)  //by jvlio - por error en WIN - no deberia ya q' siempre esta Root!
          if ( (Prototype) getCurrent().getSlot(prototype) != null )
        return ((Prototype) getCurrent().getSlot(prototype)).getSlot(slot);
          else
        return null;
        else
      return null;  //by jvlio
    }


    private AST lastSibling(AST tree) {
        AST aux=tree;
        while (aux != null ) {
            if (aux.getNextSibling() == null) return aux;
            aux=aux.getNextSibling();
               }
        return aux;
    }



public void declaration(AST tree, AST exp,String Context){
    try {
        //System.out.println("SymbolTable:declaration: tree="+tree.toStringList());
        //System.out.println("SymbolTable:declaration: exp="+exp.toStringList());

        if (Context.equals("exists")) {
          //  System.out.println("exists");
            AST aux=tree.getFirstChild();
            while (aux != null ) {
                add_symbol(aux.getText()); //System.out.println("Simbolo add:" + aux.getText() + " type" + exp);
                Prototype p= (Prototype) get(aux.getText());
                p.setSlot("type","");
                p.setSlot("isVar","exists");

                aux=aux.getNextSibling();
    	    }
        }
        else {

            Vector v = (Vector)getCurrent().getSlot("declarations");
            //System.out.println("SymbolTable:declaration: v="+v);

            AST aux=tree.getFirstChild();

            while (aux != null ) {
                if (v != null) v.add(aux.getText());
                add_symbol(aux.getText()); //System.out.println("Simbolo add:" + aux.getText() + " type" + exp);
                Prototype p= (Prototype) get(aux.getText());
                p.setSlot("type",exp.toStringList().substring(1));
                p.setSlot("isVar","true");

                ZType type=null;
                //Object o= get(exp.getText());
                Object o= get(exp.toStringList().substring(1));

                if (o != null) type= ((Prototype) o).getSlotZType("type");

                if (type == null) { System.out.println("ERROR tipo desconocido: ID: "+ aux.getText() + " Type: " + exp.toStringList());  }
                aux=aux.getNextSibling();
	    }
	} //end final else
    } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SymbolTable ERROR :declaration: tree="+tree.toStringList());
            System.out.println("SymbolTable ERROR :declaration: exp="+exp.toStringList());
    }
}


    /**
     * Add symbol to a "includes" slot from current top
     * of stak and add a new slot named "symbol".
     *
     * @param  symbol  new include.
     * @param  tree    I don't know!? (ask alfredo!)
     * @see  #getCurrent
     */
    public void includeRef(String symbol, AST tree){
        //System.out.println("SymbolTable:includeRef: TREE: " + tree.toStringList());
        Prototype current=getCurrent();
        // symbol no esta en este prototype pero es buscado hasta Root!
        Prototype p=(Prototype)current.getSlot(symbol);
        // se agrega un nuevo slot llamado "symbol" y se le agrega p
        current.addSlot(symbol,true);
        current.setSlot(symbol,p);

        ((Vector)current.getSlot("includes")).add(symbol);
    }


    /**
     * Add a new schema of name "name", add a new slot "__parent"
     * that value is current top of Stack(symbols) (eq peek() or
     * getCurrent()).
     *
     * @param  name  name of new schema.
     * @see    #putProt(String,String)
     */
    public void add_schema(String name) {
        Prototype p=putProt(name,"schema");

        p.addSlot("__parent",true);
        p.setSlot("__parent",getCurrent());

        // do this because clone can't clone the contents of slots
        // and if don't do this, then all schemas have same Vector!
        p.setSlot("includes",new Vector());
        p.setSlot("declarations",new Vector());
        p.setSlot("deltas",new Vector());
        p.setSlot("changes",new Vector());
    }

    public void add_Emptyschema() {
        Prototype p=putProt("empty_schema","schema");

        //p.addSlot("__parent",false);
        //p.setSlot("__parent",getCurrent());

        // do this because clone can't clone the contents of slots
        // and if don't do this, then all schemas have same Vector!
        p.setSlot("includes",new Vector());
        p.setSlot("declarations",new Vector());
        p.setSlot("deltas",new Vector());
        p.setSlot("changes",new Vector());
    }
    
    public void add_type(String name,ZType type) {
        Prototype p=putProt(name,"type");
	    p.setSlot("type",type);


    }

    //una variable modificada por este esquema
    public void add_changed(String name) {
      //System.out.println("Changed: " + name);
        Vector v = (Vector)getCurrent().getSlot("changes");
        v.add(name);
    }

     public boolean is_changed(String name) {
        Vector v = (Vector)getCurrent().getSlot("changes");
        //System.out.println("IS Changed: " + v.contains(name) + "Vector" + v);
        return v.contains(name);
    }

    public void add_symbol(String symbol) {
        putProt(symbol,"symbol");
        //Prototype p=(Prototype) peek().get(symbol);
        //System.out.println("Prot--add : " + p.toString());

    }

    public void add_symbol(String type,String name) {
        putProt(name,"symbol");
        Prototype p=(Prototype)get(name);
        p.setSlot("type",type);
    }

    public void add_symbol(String type,String name,String prolog) {
        putProt(name,"symbol");
        Prototype p=(Prototype)get(name);
        p.setSlot("type",type);
	p.setSlot("prolog",prolog);

    }

    public void add_inops(String symbol, int prioridad) {
        Prototype p=putProt(symbol,"inop");
        p.setSlot("priority",new Integer(prioridad));
    }

    public void add_postops(String symbol) {
        putProt(symbol,"postop");
    }

    public void add_inrels(String symbol) {
        putProt(symbol,"inrel");
    }

    public void  add_prerels(String symbol) {
        putProt(symbol,"prerel");
    }

    public void add_ingens(String symbol) {
        putProt(symbol,"ingen");
    }

    public void add_pregens(String symbol) {
        putProt(symbol,"pregen");
    }

    public void add_ignores(String symbol) {
        putProt(symbol,"ignores");
    }


    public boolean is_inop(String symbol) {
        return "inop".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_ingen(String symbol) {
        return "ingen".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_inrel(String symbol) {
        return "inrel".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_pregen(String symbol) {
        //System.out.println("---------------- is Pregen:  " + symbol);
        //System.out.println(getProt(symbol,"__name_prot"));
        //if ((peek().get(symbol)) != null) System.out.println(  ((Prototype)(peek().get(symbol))).get("__name_prot"));
        //if ((peek().get(symbol)) != null) System.out.println(  ((Prototype)(peek().get(symbol))));
        return "pregen".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_prerel(String symbol) {
        return "prerel".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_postop(String symbol) {
        return "postop".equals((String)getProt(symbol,"__name_prot"));
    }

    public boolean is_schema(String symbol) {
        //System.out.println("---------------- is Schema:  " + symbol);
        //      System.out.println(peek().get(symbol));
        //      System.out.println(getProt(symbol,"__name_prot"));
        //if ((peek().get(symbol)) != null) System.out.println(  ((Prototype)(peek().get(symbol))).get("__name_prot"));
        //if ((peek().get(symbol)) != null) System.out.println(  ((Prototype)(peek().get(symbol))));

        return "schema".equals((String)getProt(symbol,"__name_prot"));
    }

    /* public String toString(){

         return getClass().getName() + "@"+ (symbols.peek().toString());
         }*/



    public String dump() {
  ByteArrayOutputStream ostream = new ByteArrayOutputStream();
  dump(new PrintStream(ostream,true));
  return ostream.toString();
    }

    public void dump(PrintStream ostream) {
  for (Enumeration e = symbols.elements() ; e.hasMoreElements() ;)
      ostream.print( ((Prototype)e.nextElement()).dump() );
    }
} // SymbolTable


