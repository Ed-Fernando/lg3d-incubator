// $ANTLR 2.7.1: "Zeta.g" -> "ZTreeWalker.java"$
package org.jdesktop.lg3d.apps.archviz3d.zparser; 
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.collections.impl.BitSet;

import java.util.*;
import java.io.*;
import java.lang.ArrayIndexOutOfBoundsException;

import org.jdesktop.lg3d.apps.archviz3d.zparser.responses.*;
import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.*;



public class ZTreeWalker extends antlr.TreeParser
       implements ZTokenTypes
 {

        protected PrintStream out;


        public void out(PrintStream stream) {
                out=stream;
        }


        protected SymbolTable symbols;
	protected String varPrefix = "Z_";
	protected String rulePrefix = "z_";
	String stateIni= "";

public  void symbolTable(SymbolTable st) {
                symbols=st;
        }

private  ZType getType(String name) {
	  return getCurrent().getSlotPrototype(name).getSlotZType("type");
	}

private boolean  isType(String name) {
	  return (getCurrent().get(name)) != null  ;
	}
private Prototype getCurrent() {
          return symbols.getCurrent();
	}

private void push(String name) {
          symbols.push(symbols.get(name));
	}

private void pop() {
          symbols.pop();
	}


protected ZTree_Exp_Res createExpRes(String z_op, ZTree_Exp_Res exp1, ZTree_Exp_Res exp2) {

  //System.out.println("Creating Exp: op:" + z_op + "\t\n" + exp1.toString() + "\t\n" +exp2.toString() );

	  String op=symbols.toProlog(z_op);
	  if (op.equals("null")) op=z_op;
	  ZTree_Exp_Res res= new ZTree_Exp_Res();

	  boolean isInfix= symbols.prologOperator(z_op).equals("infix");

    	  String param = "";
	  res.var = symbols.varTemp();

	  if (isInfix) {
	      if (exp1!=null) {
		  if (!"".equals(exp1.prolog))
		      res.prolog = exp1.prolog+",\n";
	      }

	      if (exp2!=null) {
		  if (!"".equals(exp2.prolog))
		      res.prolog += exp2.prolog+",\n";
	      }

	      res.prolog += "\t"+ res.var + " is " + exp1.var +op+ exp2.var ;
	  } //end if isinfix
	  else {

	      if (exp1!=null) {
		  if (!"".equals(exp1.prolog))
		      res.prolog = exp1.prolog+",\n";
		  if (!"".equals(exp1.var))
		      param += exp1.var + ',';
	      }

	      if (exp2!=null) {
		  if (!"".equals(exp2.prolog))
		      res.prolog += exp2.prolog+",\n";
		  if (!"".equals(exp2.var))
		      param += exp2.var + ',';
	      }
	      param += res.var;
	      res.prolog += "\t"+op+ "(" + param + ")";
	  } //end else


	  //System.out.println("Exp Res: " + res.toString() );
	  return res;
	}



protected ZTree_Exp_Res createExpVirtualRes(String op, ZTree_Exp_Res exp1, ZTree_Exp_Res exp2) {

 
	 
	  ZTree_Exp_Res res= new ZTree_Exp_Res();

    	  String param = "";
	  res.var = symbols.varTemp();

	  

	      if (exp1!=null) {
		  if (!"".equals(exp1.prolog))
		      res.prolog = exp1.prolog+",\n";
		  if (!"".equals(exp1.var))
		      param += exp1.var + ',';
	      }

	      if (exp2!=null) {
		  if (!"".equals(exp2.prolog))
		      res.prolog += exp2.prolog+",\n";
		  if (!"".equals(exp2.var))
		      param += exp2.var + ',';
	      }
	      param += res.var;
	      res.prolog += "\t"+op+ "(" + param + ")";
	 


	  //System.out.println("Exp Res: " + res.toString() );
	  return res;
	}


 protected String[] getUpdates(String state, String operation) { //sacar ????
	   String updates = new String();


        Vector vdel= (Vector) symbols.getCurrent().getSlot("deltas");
	   for (Enumeration edel=vdel.elements(); edel.hasMoreElements();) {
	       SchemaRef scRef= (SchemaRef) edel.nextElement();

	       // System.out.println("Updates DELTA: " + scRef.mode);
	       if (scRef.mode.equals("\\Delta") || scRef.mode.equals("'") ) {

		   Prototype sch = (Prototype)(symbols.getCurrent().getSlot( scRef.schema ));
		   Vector vdecl = (Vector)sch.getSlot("declarations");
		   for (Enumeration ed=vdecl.elements(); ed.hasMoreElements();) {

		       String container = (String)sch.getSlot("name");
		       String varName = (String)ed.nextElement();
		       if (symbols.is_changed(varName)) {
		       String varChangeName = varPrefix+varName+"_DECORV";
		       String newState = symbols.varTemp();
		       String thisUpdate = "\t" + operation + "("+state+",\'"+
						   container+"\',\'"+
						   varPrefix + varName+"\',"+
						   varChangeName+","+
						   newState+")";
		       updates += ("".equals(updates))? thisUpdate : ",\n"+thisUpdate;
		       state = newState;
		     } //end if
		   }
	       }
	   }
	   String[] retup = new String[2];
	   retup[0] = state;
	   retup[1] = updates;
	   //System.out.println("Updates: " + state + " /// " + updates);
	   return retup;
	}

protected boolean isSchemaOperation() {

  Vector vdel= (Vector) symbols.getCurrent().getSlot("deltas");
  for (Enumeration edel=vdel.elements(); edel.hasMoreElements();) {
    SchemaRef scRef= (SchemaRef) edel.nextElement();  

    // System.out.println("Updates DELTA: " + scRef.mode);
    if (scRef.mode.equals("\\Delta") || scRef.mode.equals("'") || scRef.mode.equals("\\Xi")) return true;
  }
  return false;
}


protected String ChangedVariables(Prototype prot) {
   String sig="[";

 
   Vector vdel= (Vector) prot.getSlot("deltas");
   for (Enumeration e=vdel.elements(); e.hasMoreElements();) {
     SchemaRef scRef=(SchemaRef)e.nextElement();
     String schema =  scRef.schema;
     
     Prototype schemaProt= (Prototype)(prot.getSlot(schema));
     Vector vdec= (Vector) schemaProt.getSlot("declarations");
     
     for (Enumeration e2=vdec.elements(); e2.hasMoreElements();) {
       //System.out.println("schema: " + schema +" mode: " + scRef.mode);
       Object o= e2.nextElement();
       if (scRef.mode.equals("\\Delta") || scRef.mode.equals("'")  ) {
	  
	
	 sig+= (symbols.is_changed((String)o)  ? (sig.endsWith("[") ? "" : "," ) + "va('Z_" + ((String)o) + "')" : "");

        }
        
     }//end for e2
     
     sig+="]";
   } //end for e
   return sig;   

}


protected String invariantSignature(boolean decor,boolean global) {
    return invariantSignature(decor,global,symbols.getCurrent(),false);
}




protected String invariantSignature(boolean decor,boolean global,Prototype prot,boolean allDecor) {
   String sig="";

 
   Vector vdel= (Vector) prot.getSlot("deltas");
   for (Enumeration e=vdel.elements(); e.hasMoreElements();) {
     SchemaRef scRef=(SchemaRef)e.nextElement();
     String schema =  scRef.schema;
     sig+= rulePrefix + schema + "(" + (global ? "globalState(This,Z_NewState,Z_NewState)" : "");
     
     Prototype schemaProt= (Prototype)(prot.getSlot(schema));
     Vector vdec= (Vector) schemaProt.getSlot("declarations");
     
     for (Enumeration e2=vdec.elements(); e2.hasMoreElements();) {
	 //System.out.println("schema: " + schema +" mode: " + scRef.mode);
       Object o= e2.nextElement();
       if (scRef.mode.equals("\\Delta") || scRef.mode.equals("'")  ) {
	  
	
	 sig+=(sig.endsWith("(") ? "" : ",") + "Z_" + o + (((symbols.is_changed((String)o)&&decor) || allDecor ) ? "_DECORV": "") ;
	 //if ( symbols.is_changed( (String)o )) System.out.println("IS decorated: " + (String)o);
	 //else System.out.println("NOT decorated: " + (String)o);
       }
       else sig+=(sig.endsWith("(") ? "" : ",") + "Z_" + o;
     }//end for e2
     
     sig+=")";
   } //end for e
   return sig;
   
 }

protected String signatureCommon(String schemaName) {
  Prototype sch = (Prototype)symbols.get(schemaName);
  Vector vdecl = (Vector)sch.getSlot("declarations");
  String sdecl="";
  for (Enumeration e=vdecl.elements(); e.hasMoreElements();)
    sdecl += (sdecl==""? "" : ",") + varPrefix+(String)e.nextElement();

  return sdecl;

}

protected String signatureCommonChanged(String schemaName,String Name, String NewName) {
  Prototype sch = (Prototype)symbols.get(schemaName);
  Vector vdecl = (Vector)sch.getSlot("declarations");
  String sdecl="";
  for (Enumeration e=vdecl.elements(); e.hasMoreElements();) {
   String name=(String)e.nextElement();
   if (name.equals(Name)) name=NewName;
    sdecl += (sdecl==""? "" : ",") + varPrefix+name;
}

  return sdecl;

}
		
protected String createSchemaStateSignature(String schemaName, String state) {
  String sdecl=signatureCommon(schemaName);
  
  //for info and access to fields
  Prototype sch = (Prototype)symbols.get(schemaName);
  Vector vdecl = (Vector)sch.getSlot("declarations");
  
  stateIni+= (stateIni.endsWith("[") ? "" : "," ) + "tree('" + schemaName + "',["; 
  for (Enumeration e=vdecl.elements(); e.hasMoreElements();) {
    String es=(String)e.nextElement();
    stateIni += (stateIni.endsWith("[") ? "" : "," ) + "'" + es + "'";
    out.println("value(z_" + schemaName + "(" + sdecl + ")," + "'Z_" + es + "'" + ",Z_" + es + ").");
    String new_sdecl=signatureCommonChanged(schemaName,es,"NEW_" + es);
        out.println("setValue(z_" + schemaName + "(" + sdecl + ")," 
            + "z_" + schemaName + "(" + new_sdecl + "),"
            + "'Z_" + es + "'" + ",Z_NEW_" + es + ").");
  }
  stateIni += "])";
  out.println("");

  //end ---
  
 
  return  rulePrefix+schemaName+"(globalState(This,"+state+","+ state +")"+ (sdecl=="" ? "" : ",") + sdecl+ ")";

}

protected String createSchemaOperationSignature(String schemaName, String state, String newState) {
 
  String sdecl=signatureCommon(schemaName);
  
return  rulePrefix+schemaName+"(globalState(This,"+state+","+ newState + "),"+ 
    invariantSignature(false,false) +"," + invariantSignature(true,false) +(sdecl=="" ? "" : ",") + sdecl + ")";
}

protected String createSchemaOperationCallSignature(String schemaName, String state, String newState) {
  String sdecl=signatureCommon(schemaName);
  return  rulePrefix+schemaName+"(globalState(This,"+state+","+ newState + "),"+ 
    invariantSignature(false,false,(Prototype)symbols.get(schemaName),false) +"," + invariantSignature(false,false,(Prototype)symbols.get(schemaName),true) +(sdecl=="" ? "" : ",") + sdecl + ")";
}


protected String createSchemaInitSignature(String schemaName, String state, String newState) {
  String sdecl=signatureCommon(schemaName);
  
 
  
  return  rulePrefix+schemaName+"(globalState(This,"+state+","+ newState + "),"+ 
    invariantSignature(true,false) +(sdecl=="" ? "" : ",") + sdecl + ")";

    	}


protected String createSchemaSignatureInterface(String schemaName, SchemaExpressionResponse schexp,String state, String newState) {

    Vector set = new Vector();
    Prototype sch=null;


    for (int i=0; i<schexp.getNumberOfOrExpressions(); i++) {

	String sexp = null;
	try { sexp = schexp.getOrExpressionAt(i);
	} catch (ArrayIndexOutOfBoundsException e) { System.err.println("ZTreeParser:schemaParagraph:  ArrayIndexOutOfBoundsException!");}

	StringTokenizer t = new StringTokenizer(sexp);
	if (t.hasMoreTokens()) { String schema=t.nextToken();
		if (schema != null) {
		sch = (Prototype)symbols.get(schema);
		Vector vdecl = (Vector)sch.getSlot("declarations");

		    for (Enumeration e=vdecl.elements(); e.hasMoreElements();)
			{ Object elem=e.nextElement();
			if (! set.contains(elem)) set.addElement(elem);
			}

		}
	} 
    } //end for

    String sdecl="";


    Enumeration e = set.elements();
    while ( e.hasMoreElements() )
	sdecl += (sdecl==""? "" : ",") + varPrefix+(String)e.nextElement();
    //System.out.println("");
    //System.out.println("");
    //System.out.println(""); //Hay un error cuando se crea la interface tiene en cuenta un unico esquema de los esquemas unidos por una operacion logica!!!! cuand se declara una interface poner ultimo el que incorpora modificaciones!!!!
    return  rulePrefix+schemaName+"(globalState(This,"+state+","+ newState + ")," +  invariantSignature(false,false,sch,false) + "," + invariantSignature(true,false,sch,true) + (sdecl=="" ? "" : ",") + sdecl+  ")";
} //end function


public ZTreeWalker() {
	tokenNames = _tokenNames;
}

	public final void specification(AST _t) throws RecognitionException {
		
		AST specification_AST_in = (AST)_t;
		
		try {      // for error handling
			stateIni= "stateIni(tree('Root',[";
			{
			_loop221:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					paragraph(_t);
					_t = _retTree;
				}
				else {
					break _loop221;
				}
				
			} while (true);
			}
			stateIni += "])).";
					       out.println(stateIni);
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void paragraph(AST _t) throws RecognitionException {
		
		AST paragraph_AST_in = (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case OPENBRACKET:
			case 36:
			case 38:
			case 39:
			{
				zedParagraph(_t);
				_t = _retTree;
				break;
			}
			case SCHEMA:
			{
				schemaParagraph(_t);
				_t = _retTree;
				break;
			}
			case 27:
			{
				axdefParagraph(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void zedParagraph(AST _t) throws RecognitionException {
		
		AST zedParagraph_AST_in = (AST)_t;
		AST key = null;
		AST val = null;
		AST name = null;
		ZTree_Exp_Res id=null; ZTree_Exp_Res exp=null; Vector vform=null;
		SchemaExpressionResponse schexp=null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case OPENBRACKET:
			{
				AST tmp1_AST_in = (AST)_t;
				match(_t,OPENBRACKET);
				_t = _t.getNextSibling();
				{
				int _cnt227=0;
				_loop227:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==WORD)) {
						id=ident(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt227>=1 ) { break _loop227; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt227++;
				} while (true);
				}
				break;
			}
			case 36:
			{
				AST __t228 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,36);
				_t = _t.getFirstChild();
				key = _t==ASTNULL ? null : (AST)_t;
				defLhs(_t);
				_t = _retTree;
				val = _t==ASTNULL ? null : (AST)_t;
				exp=expression(_t);
				_t = _retTree;
				_t = __t228;
				_t = _t.getNextSibling();
				//System.out.println("Expresion !!!!!!: " + exp.toString());
												stateIni+= (stateIni.endsWith("[") ? "" : "," ) + "value('Z_"+ key.getText() + "'," + val.getText() + ")";
				
										       	
				break;
			}
			case 38:
			{
				AST __t229 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,38);
				_t = _t.getFirstChild();
				name = (AST)_t;
				match(_t,WORD);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case WORD:
				{
					vform=formals(_t);
					_t = _retTree;
					break;
				}
				case 50:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
				case 59:
				case 60:
				case 61:
				case 62:
				case 63:
				case 64:
				case 67:
				case 69:
				case 70:
				case SCHEMA_NAME:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				symbols.push(name.getText());
												symbols.initVarGen(); String firstVar=symbols.varTemp();
				schexp=schema_Exp(_t);
				_t = _retTree;
				_t = __t229;
				_t = _t.getNextSibling();
				
				
				
				Vector vdel = (Vector) symbols.getCurrent().getSlot("deltas");
				
				//Prototype psdef = (Prototype)symbols.getCurrent().getSlot(name.getText());
				//Vector vdel = (Vector) psdef.getSlot("deltas");
				
				
				boolean isInit=true;
				for (Enumeration e=vdel.elements(); e.hasMoreElements() && isInit;) {
				SchemaRef scRef=(SchemaRef)e.nextElement();
				if (! "'".equals(scRef.mode)) isInit=false;
				}
				
				if ( ! isInit) { //begin schema interface
				
				// create prolog signature
				//System.out.println("SCHEXP: " + schexp.toString());
				
				String NameInterface=name.getText();
				String signature; // = createSchemaSignatureInterface(NameInterface,schexp,"Z_State","Z_NewState");
				
				//System.out.println("Signature Inteface: " + signature);
				
				//printing prolog_or predicates
				
				for (int i=0; i<schexp.getNumberOfOrExpressions(); i++) {
				// signature
				
				// or expresions
				
				String sexp = null;
				try {
					sexp = schexp.getOrExpressionAt(i);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("ZTreeParser:schemaParagraph:  ArrayIndexOutOfBoundsException!");
				}
				symbols.initVarGen();
				String s="";
				String state ="Z_State";
				String newstate ="";
				StringTokenizer t = new StringTokenizer(sexp);
				while (t.hasMoreTokens()) {
					String schema=t.nextToken();
					//System.out.println("Schema---: " + schema);
					newstate = symbols.varTemp();
				
				
					signature= createSchemaOperationCallSignature(schema,"Z_State","Z_NewState");
					signature= "z_"+ NameInterface + signature.substring(schema.length()+2);
					out.println(signature + ":-");
				
				
				
					Prototype sch=  (Prototype)symbols.get(schema);
					String sign= "\t" + createSchemaOperationCallSignature(schema,state,newstate);
					
					s += ("".equals(s))? sign : ",\n"+sign;
					state=newstate;
							       }
				out.println(s+",");
				// update state and end point
				//out.println("\tZ_NewState="+state+".\n");
				out.println("\taddChangeOp(" + state + ",z_" + name.getText() + ",Z_NewState).\n");
				}
				}//end schema interface
				else { //begin init schema
				
				out.print(createSchemaInitSignature(name.getText(),"Z_State","Z_NewState")+ ":-\n" + schexp.getOrExpressionAt(0));
				out.println("\t,addChangeOp(Z_State"  + ",z_"  + name.getText() + "," + ChangedVariables(symbols.getCurrent())  + ",Z_NewState).\n");
				
				//out.println("z_init_schema(GS,Schema):-z_" + name.getText() + "(GS,Schema).\n");
				stateIni= name.getText() + "_"+ stateIni ;
				
				
				} //end init schema
				symbols.pop();
						
				break;
			}
			case 39:
			{
				AST __t231 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,39);
				_t = _t.getFirstChild();
				id=ident(_t);
				_t = _retTree;
				{
				int _cnt233=0;
				_loop233:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==WORD)) {
						branch(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt233>=1 ) { break _loop233; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt233++;
				} while (true);
				}
				_t = __t231;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void schemaParagraph(AST _t) throws RecognitionException {
		
		AST schemaParagraph_AST_in = (AST)_t;
		AST schemaName = null;
		SchemaDisplayTextResponse pres=null; PredicateResponse pre=null; Vector vform=null; String signature=null;
		
		try {      // for error handling
			AST __t235 = _t;
			AST tmp5_AST_in = (AST)_t;
			match(_t,SCHEMA);
			_t = _t.getFirstChild();
			schemaName = (AST)_t;
			match(_t,WORD);
			_t = _t.getNextSibling();
			//symbols.push( symbols.getCurrent().getSlot(schemaName.getText()) );
									symbols.push(schemaName.getText());
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case WORD:
			{
				vform=formals(_t);
				_t = _retTree;
				break;
			}
			case DECL_PART:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			//initialize variable generator
							symbols.initVarGen();
			
			pres=schemaDisplayText(_t);
			_t = _retTree;
			_t = __t235;
			_t = _t.getNextSibling();
			
						   boolean scheOp= isSchemaOperation();
						   if (scheOp) { // Operation Schema
						     signature=createSchemaOperationSignature(schemaName.getText(),"Z_State","Z_NewState");
						   } //end Operation Schema
						   else { // State Schema
						     signature=createSchemaStateSignature(schemaName.getText(),"Z_State");
						     			  			     			    			     
						   } //end State Schema
						 
			if (pres.predicate != null) {
						   //printing prolog_or predicates
						   for (int i=0; i<pres.predicate.getNumberOfOrPredicates(); i++) {
						     // signature
						     out.println(signature+":-");
			
						       // getContains global variables not state variables which are passed are arguments
						       
			//saque
			//if (!"".equals(pres.decl)) out.println(pres.decl+",");
			
			
						       // or predicate
						       String spred = null;
						       try {
							   spred = pres.predicate.getOrPredicateAt(i);
					               } catch (ArrayIndexOutOfBoundsException e) {
						           System.err.println("ZTreeParser:schemaParagraph:  ArrayIndexOutOfBoundsException!");
					               }
						      
						     
						    
						       if(scheOp) { 
							 out.println(spred + ",");
							 out.println("\taddChangeOp(Z_State,z_" + schemaName.getText() +"," + ChangedVariables(symbols.getCurrent())   + ",Z_NewState),");
							 out.println( "\t" + invariantSignature(true,true)+".\n");
						       }
						       else {out.println(spred + ".\n");}
			} //end for
			} //end if
			else out.println(signature+".");
			symbols.pop();
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void axdefParagraph(AST _t) throws RecognitionException {
		
		AST axdefParagraph_AST_in = (AST)_t;
		SchemaDisplayTextResponse pres=null;
		
		try {      // for error handling
			AST tmp6_AST_in = (AST)_t;
			match(_t,27);
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case WORD:
			{
				formals(_t);
				_t = _retTree;
				break;
			}
			case DECL_PART:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			pres=schemaDisplayText(_t);
			_t = _retTree;
			AST tmp7_AST_in = (AST)_t;
			match(_t,28);
			_t = _t.getNextSibling();
			//System.out.println("Axdef: " + pres.toString());
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final Vector  formals(AST _t) throws RecognitionException {
		Vector vform;
		
		AST formals_AST_in = (AST)_t;
		vform=new Vector(); ZTree_Exp_Res id=null;
		
		try {      // for error handling
			{
			int _cnt241=0;
			_loop241:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==WORD)) {
					id=ident(_t);
					_t = _retTree;
					vform.add(id.var);
				}
				else {
					if ( _cnt241>=1 ) { break _loop241; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt241++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return vform;
	}
	
	public final SchemaDisplayTextResponse  schemaDisplayText(AST _t) throws RecognitionException {
		SchemaDisplayTextResponse res;
		
		AST schemaDisplayText_AST_in = (AST)_t;
		res=new SchemaDisplayTextResponse(); String decl=null;  PredicateResponse pres = null;
		
		try {      // for error handling
			decl=declPart(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AXIOM_PART:
			{
				pres=axiomPart(_t);
				_t = _retTree;
				break;
			}
			case 3:
			case 28:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			res.decl=decl;
						   res.predicate=pres;
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final ZTree_Exp_Res  ident(AST _t) throws RecognitionException {
		ZTree_Exp_Res res;
		
		AST ident_AST_in = (AST)_t;
		AST id = null;
		AST dec = null;
		res=new ZTree_Exp_Res();
		
		try {      // for error handling
			AST __t348 = _t;
			id = _t==ASTNULL ? null :(AST)_t;
			match(_t,WORD);
			_t = _t.getFirstChild();
			dec = _t==ASTNULL ? null : (AST)_t;
			decoration(_t);
			_t = _retTree;
			_t = __t348;
			_t = _t.getNextSibling();
			//System.out.println("ZTreeParser:ident: id="+#id.getText()+" dec="+ (#dec != null ? #dec.getText() : "No Decoration") );
			
				     Prototype pc= ((Prototype)symbols.getCurrent().get(id.getText()));
				     String pc_type="",pc_metaType="",isVar="";
				     ZType pc_ZType=null;
			if (pc != null) if ( !pc.getName().equals("type")) {
				       try {
					 if (pc != null)
					   if ( pc.get("type") != null) {
					     pc_type= (String)pc.get("type");
			pc_ZType= getType(pc_type);
					     pc_metaType=pc_ZType.metaType();
					   }
					 isVar=pc.get("isVar") == null ? "" : (String) pc.get("isVar");
				       } catch(Exception e) {
					 //System.out.println("*(ERROR)IDENT PROT:=" + id.getText() + " Type=" + pc_type + " ZType=" + (pc_ZType != null ? pc_ZType.toString() : ""));
				       }
			
				     } //end if pc.getName()
			
				     //System.out.println("IDENT PROT:=" + id.getText());
				     
				    
				       if (!isVar.equals("true") && pc_metaType.equals("enum")) {
					 res.var= "'" + id.getText() + "'";
					 res.prolog="";						
					 //System.out.println("\tIDENT(ENUM):=" + id.getText() + " Type=" + pc_type + " ZType=" + (pc_ZType != null ? pc_ZType.toString() : ""));
				       }	 
				       else if (dec != null && dec.getText().charAt(0)=='\'') {
					 //  System.out.println("Decoration");
					 res.var=varPrefix+id.getText()+"_DECORV";
					 res.prolog="";
					 //System.out.println("\tIDENT(DECOR):=" + id.getText() + " Type=" + pc_type + " ZType=" + (pc_ZType != null ? pc_ZType.toString() : ""));
				       }
				       else {                              
					 Prototype pcurrent = symbols.getCurrent();
					 String container = symbols.getCurrent().containerName(id.getText());
					 Prototype pcontainer = (Prototype)symbols.get(container);
					 
					 res.var=("".equals(container))? id.getText() : varPrefix+id.getText();			   
					 if (container.equals("Root") && pc_type.equals("\\defs")) {    				   
					   res.prolog="\tgetContain(Z_State," + "'" + container +"'" + ",'" + res.var + "'," + res.var +")";
					 }
					 //System.out.println("\tIDENT(ELSE):=" + id.getText() + " Type=" + pc_type + " ZType=" + (pc_ZType != null ? pc_ZType.toString() : ""));
				       }
				       //System.out.println("\t\tRes: " + res.toString());
				       
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final void defLhs(AST _t) throws RecognitionException {
		
		AST defLhs_AST_in = (AST)_t;
		ZTree_Exp_Res id1=null; ZTree_Exp_Res id2=null;  Vector vform=null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case WORD:
			{
				id1=varName(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==WORD)) {
					vform=formals(_t);
					_t = _retTree;
				}
				else if ((_tokenSet_1.member(_t.getType()))) {
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
				break;
			}
			case PRE_GEN:
			{
				AST tmp8_AST_in = (AST)_t;
				match(_t,PRE_GEN);
				_t = _t.getNextSibling();
				id1=ident(_t);
				_t = _retTree;
				break;
			}
			case IN_GEN:
			{
				AST tmp9_AST_in = (AST)_t;
				match(_t,IN_GEN);
				_t = _t.getNextSibling();
				id1=ident(_t);
				_t = _retTree;
				id2=ident(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final ZTree_Exp_Res  expression(AST _t) throws RecognitionException {
		ZTree_Exp_Res res;
		
		AST expression_AST_in = (AST)_t;
		AST number = null;
		res= new ZTree_Exp_Res(); ZTree_Exp_Res exp1=null; ZTree_Exp_Res exp2=null;
		ZTree_Exp_Res var=null; PredicateResponse pre=null; String aux1=new String();
		SchemaDisplayTextResponse schema=null;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case 83:
			{
				AST __t281 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,83);
				_t = _t.getFirstChild();
				pre=predicate(_t);
				_t = _retTree;
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t281;
				_t = _t.getNextSibling();
				break;
			}
			case IN_GEN:
			{
				AST __t282 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,IN_GEN);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t282;
				_t = _t.getNextSibling();
				break;
			}
			case 81:
			{
				AST __t283 = _t;
				AST tmp12_AST_in = (AST)_t;
				match(_t,81);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				exp1=expression(_t);
				_t = _retTree;
				_t = __t283;
				_t = _t.getNextSibling();
				break;
			}
			case 82:
			{
				AST __t284 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,82);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp1=expression(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t284;
				_t = _t.getNextSibling();
				break;
			}
			case 86:
			{
				AST __t286 = _t;
				AST tmp14_AST_in = (AST)_t;
				match(_t,86);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t286;
				_t = _t.getNextSibling();
				break;
			}
			case 87:
			{
				AST __t287 = _t;
				AST tmp15_AST_in = (AST)_t;
				match(_t,87);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t287;
				_t = _t.getNextSibling();
				break;
			}
			case IN_FUN1:
			{
				AST __t288 = _t;
				AST tmp16_AST_in = (AST)_t;
				match(_t,IN_FUN1);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t288;
				_t = _t.getNextSibling();
				res = createExpRes(tmp16_AST_in.getText(),exp1,exp2);
				break;
			}
			case IN_FUN2:
			{
				AST __t289 = _t;
				AST tmp17_AST_in = (AST)_t;
				match(_t,IN_FUN2);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t289;
				_t = _t.getNextSibling();
				res = createExpRes(tmp17_AST_in.getText(),exp1,exp2);
				break;
			}
			case IN_FUN3:
			{
				AST __t290 = _t;
				AST tmp18_AST_in = (AST)_t;
				match(_t,IN_FUN3);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t290;
				_t = _t.getNextSibling();
				res = createExpRes(tmp18_AST_in.getText(),exp1,exp2);
				break;
			}
			case IN_FUN4:
			{
				AST __t291 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,IN_FUN4);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t291;
				_t = _t.getNextSibling();
				res = createExpRes(tmp19_AST_in.getText(),exp1,exp2);
				break;
			}
			case IN_FUN5:
			{
				AST __t292 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,IN_FUN5);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t292;
				_t = _t.getNextSibling();
				res = createExpRes(tmp20_AST_in.getText(),exp1,exp2);
				break;
			}
			case IN_FUN6:
			{
				AST __t293 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,IN_FUN6);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t293;
				_t = _t.getNextSibling();
				res = createExpRes(tmp21_AST_in.getText(),exp1,exp2);
				break;
			}
			case 94:
			{
				AST __t294 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,94);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				_t = __t294;
				_t = _t.getNextSibling();
				break;
			}
			case 95:
			{
				AST __t295 = _t;
				AST tmp23_AST_in = (AST)_t;
				match(_t,95);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				_t = __t295;
				_t = _t.getNextSibling();
				break;
			}
			case PRE_GEN:
			{
				AST __t296 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,PRE_GEN);
				_t = _t.getFirstChild();
				decoration(_t);
				_t = _retTree;
				exp1=expression(_t);
				_t = _retTree;
				_t = __t296;
				_t = _t.getNextSibling();
				if (tmp24_AST_in.getText().equals("\\new")) exp1.var="This,'"+exp1.var+"'";
				res = createExpRes(tmp24_AST_in.getText(),exp1,null);
				break;
			}
			case HYPHEN:
			{
				AST __t297 = _t;
				AST tmp25_AST_in = (AST)_t;
				match(_t,HYPHEN);
				_t = _t.getFirstChild();
				decoration(_t);
				_t = _retTree;
				exp1=expression(_t);
				_t = _retTree;
				_t = __t297;
				_t = _t.getNextSibling();
				break;
			}
			case 98:
			{
				AST __t298 = _t;
				AST tmp26_AST_in = (AST)_t;
				match(_t,98);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				decoration(_t);
				_t = _retTree;
				_t = __t298;
				_t = _t.getNextSibling();
				break;
			}
			case POINT:
			{
				AST __t299 = _t;
				AST tmp27_AST_in = (AST)_t;
				match(_t,POINT);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				var=varName(_t);
				_t = _retTree;
				_t = __t299;
				_t = _t.getNextSibling();
				//System.out.println("POINT: " + #POINT.getText() + "\nExp1:  " +  exp1.toString() + "\nVAR  " + var.toString() );
				var.var= "'"+ (var.var.startsWith("Z_") ? "" : "Z_" ) + var.var+"'";
				res = createExpVirtualRes("ivar",exp1,var);
				
				break;
			}
			case POST_FUN:
			{
				AST __t300 = _t;
				AST tmp28_AST_in = (AST)_t;
				match(_t,POST_FUN);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				_t = __t300;
				_t = _t.getNextSibling();
				break;
			}
			case 103:
			{
				AST __t301 = _t;
				AST tmp29_AST_in = (AST)_t;
				match(_t,103);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t301;
				_t = _t.getNextSibling();
				break;
			}
			case WORD:
			{
				res=varName(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case GEN_ACTUALS:
				{
					gen_Actuals(_t);
					_t = _retTree;
					break;
				}
				case 3:
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				case STROKE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				break;
			}
			case NUMBER:
			{
				number = (AST)_t;
				match(_t,NUMBER);
				_t = _t.getNextSibling();
				res.var=number.getText();
				break;
			}
			case SETSSCH:
			{
				AST __t303 = _t;
				AST tmp30_AST_in = (AST)_t;
				match(_t,SETSSCH);
				_t = _t.getFirstChild();
				schema=schema_Text(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp1=expression(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t303;
				_t = _t.getNextSibling();
				System.out.println("NERW_SET exp: " + exp1);
				System.out.println("NERW_SET schema: " + schema);
				
				res.var = symbols.varTemp();
				res.prolog="fAll([" + schema.predicate.toString() + "]," + schema.decl +
				"," + res.var + ")";
				
				break;
			}
			case 106:
			{
				AST tmp31_AST_in = (AST)_t;
				match(_t,106);
				_t = _t.getNextSibling();
				res.var="[]";
				break;
			}
			case 107:
			{
				AST tmp32_AST_in = (AST)_t;
				match(_t,107);
				_t = _t.getNextSibling();
				res.var="[]";
				break;
			}
			case SETSEXP:
			{
				AST __t305 = _t;
				AST tmp33_AST_in = (AST)_t;
				match(_t,SETSEXP);
				_t = _t.getFirstChild();
				{
				_loop307:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_tokenSet_1.member(_t.getType()))) {
						exp1=expression(_t);
						_t = _retTree;
						//System.out.print("expresion:SETSEXP: in* aux1="+aux1+"|");
										                      //System.out.println(" exp1.prolog="+exp1.prolog+"|");
						aux1 = aux1 + ((aux1.equals("")) ? "" : ",") + exp1.var  ;
						
						res.prolog += ((res.prolog.equals("") || exp1.prolog.equals(""))? "" : ",") + exp1.prolog  ;
						
						//System.out.print("expresion:SETSEXP: out* aux1="+aux1+"|");
						//System.out.print(" exp1.prolog="+exp1.prolog+"|");
						//System.out.println(" res.prolog="+res.prolog+"|");
						
					}
					else {
						break _loop307;
					}
					
				} while (true);
				}
				_t = __t305;
				_t = _t.getNextSibling();
				//System.out.println("expresion:SETSEXP: aux1="+aux1);
				res.var = "["+aux1+"]";
				
				break;
			}
			case SETENUM:
			{
				AST __t308 = _t;
				AST tmp34_AST_in = (AST)_t;
				match(_t,SETENUM);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp1=expressions(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t308;
				_t = _t.getNextSibling();
				break;
			}
			case TUPLE:
			{
				AST __t310 = _t;
				AST tmp35_AST_in = (AST)_t;
				match(_t,TUPLE);
				_t = _t.getFirstChild();
				exp1=expressions(_t);
				_t = _retTree;
				_t = __t310;
				_t = _t.getNextSibling();
				res.prolog=exp1.prolog; res.var="tuple(" + exp1.var + ")" ;
				break;
			}
			case APPLICATION:
			{
				AST __t311 = _t;
				AST tmp36_AST_in = (AST)_t;
				match(_t,APPLICATION);
				_t = _t.getFirstChild();
				var=varName(_t);
				_t = _retTree;
				exp1=expression(_t);
				_t = _retTree;
				_t = __t311;
				_t = _t.getNextSibling();
				res = createExpVirtualRes("application",var,exp1);
				break;
			}
			case 108:
			{
				AST __t312 = _t;
				AST tmp37_AST_in = (AST)_t;
				match(_t,108);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp1=expressions(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t312;
				_t = _t.getNextSibling();
				break;
			}
			case 110:
			{
				AST __t314 = _t;
				AST tmp38_AST_in = (AST)_t;
				match(_t,110);
				_t = _t.getFirstChild();
				{
				_loop316:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_tokenSet_1.member(_t.getType()))) {
						exp1=expression(_t);
						_t = _retTree;
						//System.out.print("expresion:SETSEXP: in* aux1="+aux1+"|");
										                      //System.out.println(" exp1.prolog="+exp1.prolog+"|");
						aux1 = aux1 + ((aux1.equals("")) ? "" : ",") + exp1.var  ;
						
						res.prolog += ((res.prolog.equals("") || exp1.prolog.equals(""))? "" : ",") + exp1.prolog  ;
						
						//System.out.print("expresion:SETSEXP: out* aux1="+aux1+"|");
						//System.out.print(" exp1.prolog="+exp1.prolog+"|");
						//System.out.println(" res.prolog="+res.prolog+"|");
						
					}
					else {
						break _loop316;
					}
					
				} while (true);
				}
				_t = __t314;
				_t = _t.getNextSibling();
				//System.out.println("expresion:SETSEXP: aux1="+aux1);
				res.var = "["+aux1+"]";
				
				break;
			}
			case 112:
			{
				AST __t317 = _t;
				AST tmp39_AST_in = (AST)_t;
				match(_t,112);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp1=expressions(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t317;
				_t = _t.getNextSibling();
				break;
			}
			case 114:
			{
				AST __t319 = _t;
				AST tmp40_AST_in = (AST)_t;
				match(_t,114);
				_t = _t.getFirstChild();
				AST tmp41_AST_in = (AST)_t;
				match(_t,SCHEMA_NAME);
				_t = _t.getNextSibling();
				decoration(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case Renaming:
				{
					AST tmp42_AST_in = (AST)_t;
					match(_t,Renaming);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t319;
				_t = _t.getNextSibling();
				break;
			}
			case SCHEMA_NAME:
			{
				schema_Ref(_t);
				_t = _retTree;
				break;
			}
			case 116:
			{
				AST tmp43_AST_in = (AST)_t;
				match(_t,116);
				_t = _t.getNextSibling();
				break;
			}
			case 117:
			{
				AST tmp44_AST_in = (AST)_t;
				match(_t,117);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final SchemaExpressionResponse  schema_Exp(AST _t) throws RecognitionException {
		SchemaExpressionResponse res;
		
		AST schema_Exp_AST_in = (AST)_t;
		res = new SchemaExpressionResponse();
		SchemaExpressionResponse exp1=null;
		SchemaExpressionResponse exp2=null;
		SchemaReferenceResponse sref = new SchemaReferenceResponse();
		SchemaDisplayTextResponse sdtr=null;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case 50:
			{
				AST __t261 = _t;
				AST tmp45_AST_in = (AST)_t;
				match(_t,50);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t261;
				_t = _t.getNextSibling();
				break;
			}
			case 52:
			{
				AST __t262 = _t;
				AST tmp46_AST_in = (AST)_t;
				match(_t,52);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t262;
				_t = _t.getNextSibling();
				res=exp1;
				break;
			}
			case 53:
			{
				AST __t263 = _t;
				AST tmp47_AST_in = (AST)_t;
				match(_t,53);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t263;
				_t = _t.getNextSibling();
				break;
			}
			case 54:
			{
				AST __t264 = _t;
				AST tmp48_AST_in = (AST)_t;
				match(_t,54);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t264;
				_t = _t.getNextSibling();
				break;
			}
			case 55:
			{
				AST __t265 = _t;
				AST tmp49_AST_in = (AST)_t;
				match(_t,55);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t265;
				_t = _t.getNextSibling();
				
				exp1.addAndExpression(exp2);
				res=exp1;
						
				break;
			}
			case 56:
			{
				AST __t266 = _t;
				AST tmp50_AST_in = (AST)_t;
				match(_t,56);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t266;
				_t = _t.getNextSibling();
				break;
			}
			case 57:
			{
				AST __t267 = _t;
				AST tmp51_AST_in = (AST)_t;
				match(_t,57);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t267;
				_t = _t.getNextSibling();
				
				exp1.addOrExpression(exp2);
				res=exp1;
						
				break;
			}
			case 58:
			{
				AST __t268 = _t;
				AST tmp52_AST_in = (AST)_t;
				match(_t,58);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t268;
				_t = _t.getNextSibling();
				break;
			}
			case 59:
			{
				AST __t269 = _t;
				AST tmp53_AST_in = (AST)_t;
				match(_t,59);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t269;
				_t = _t.getNextSibling();
				break;
			}
			case 60:
			{
				AST __t270 = _t;
				AST tmp54_AST_in = (AST)_t;
				match(_t,60);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t270;
				_t = _t.getNextSibling();
				break;
			}
			case 61:
			{
				AST __t271 = _t;
				AST tmp55_AST_in = (AST)_t;
				match(_t,61);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t271;
				_t = _t.getNextSibling();
				break;
			}
			case 62:
			{
				AST __t272 = _t;
				AST tmp56_AST_in = (AST)_t;
				match(_t,62);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				exp2=schema_Exp(_t);
				_t = _retTree;
				_t = __t272;
				_t = _t.getNextSibling();
				break;
			}
			case 63:
			{
				AST __t273 = _t;
				AST tmp57_AST_in = (AST)_t;
				match(_t,63);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t273;
				_t = _t.getNextSibling();
				break;
			}
			case 64:
			{
				AST __t274 = _t;
				AST tmp58_AST_in = (AST)_t;
				match(_t,64);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				{
				int _cnt276=0;
				_loop276:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==WORD)) {
						ident(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt276>=1 ) { break _loop276; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt276++;
				} while (true);
				}
				_t = __t274;
				_t = _t.getNextSibling();
				break;
			}
			case 67:
			{
				AST __t277 = _t;
				AST tmp59_AST_in = (AST)_t;
				match(_t,67);
				_t = _t.getFirstChild();
				sdtr=schema_Text(_t);
				_t = _retTree;
				_t = __t277;
				_t = _t.getNextSibling();
				
								//System.out.println("lsch: " + sdtr.decl  + "   /  " + sdtr.predicate.toString());
								res.addAndExpression( ( sdtr.decl != null ? sdtr.decl : "") +sdtr.predicate.toString());
								
				break;
			}
			case 69:
			{
				AST __t278 = _t;
				AST tmp60_AST_in = (AST)_t;
				match(_t,69);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t278;
				_t = _t.getNextSibling();
				break;
			}
			case 70:
			{
				AST __t279 = _t;
				AST tmp61_AST_in = (AST)_t;
				match(_t,70);
				_t = _t.getFirstChild();
				exp1=schema_Exp(_t);
				_t = _retTree;
				_t = __t279;
				_t = _t.getNextSibling();
				break;
			}
			case SCHEMA_NAME:
			{
				sref=schema_Ref(_t);
				_t = _retTree;
				
				res.addAndExpression(" "+sref.getName());
						
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final void branch(AST _t) throws RecognitionException {
		
		AST branch_AST_in = (AST)_t;
		ZTree_Exp_Res id=null; ZTree_Exp_Res exp=null;
		
		try {      // for error handling
			id=ident(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_1.member(_t.getType()))) {
				exp=expression(_t);
				_t = _retTree;
			}
			else if ((_t.getType()==3||_t.getType()==WORD)) {
			}
			else {
				throw new NoViableAltException(_t);
			}
			
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final ZTree_Exp_Res  varName(AST _t) throws RecognitionException {
		ZTree_Exp_Res res;
		
		AST varName_AST_in = (AST)_t;
		res= null;
		
		try {      // for error handling
			res=ident(_t);
			_t = _retTree;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final PredicateResponse  predicate(AST _t) throws RecognitionException {
		PredicateResponse res;
		
		AST predicate_AST_in = (AST)_t;
		AST exi = null;
		res=new PredicateResponse();
		ZTree_Exp_Res exp1=null; ZTree_Exp_Res exp2=null;
		ZTree_Exp_Res id=null; ZTree_Exp_Res var=null;
		PredicateResponse pre1=null; PredicateResponse pre2=null; SchemaDisplayTextResponse st;
		String saux=null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case METHODCALL:
			{
				AST __t245 = _t;
				AST tmp62_AST_in = (AST)_t;
				match(_t,METHODCALL);
				_t = _t.getFirstChild();
				AST __t246 = _t;
				AST tmp63_AST_in = (AST)_t;
				match(_t,POINT);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				var=varName4(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case SETENUM:
				case SETSEXP:
				case SETSSCH:
				case TUPLE:
				case APPLICATION:
				case WORD:
				case PRE_GEN:
				case IN_GEN:
				case SCHEMA_NAME:
				case 81:
				case 82:
				case 83:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 94:
				case 95:
				case HYPHEN:
				case 98:
				case POINT:
				case POST_FUN:
				case 103:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				{
					exp2=expressions(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t246;
				_t = _t.getNextSibling();
				_t = __t245;
				_t = _t.getNextSibling();
				//System.out.println("METHODCALL: " + #METHODCALL.getText() + "\nExp1:  " +  exp1.toString() + "\nVAR  " + var.toString() );
				//if (exp2 != null) System.out.println("METHODCALL Exp2:  (" +  exp2.toString() + ")");
				
				String s;
				//System.out.println("VAR NAME:  " +  var.toString());
				//setIvar(ZAnim,Var,Val)                            
				if (var.var.equals("setiVar")) {
				s= "\tsetIvar(" +   exp1.var + ",'Z_"  + exp2.var.substring(0,exp2.var.indexOf(",")) + "'" + exp2.var.substring(exp2.var.indexOf(",")) +")";
				}
				else {
				
				if (exp2 != null) { if (tmp62_AST_in.getText().equals("METHODCALLASYNC")) s= "\tsendV(" +   exp1.var + ",command,['z_" +  var.var + "',[" + exp2.var +"]],_)"; 
				else s= "\tsendV(" +   exp1.var + ",execute,['z_" +  var.var + "',[" + exp2.var +"]],_)"; }
				else s= "\tsendV(" +   exp1.var + ",execute,['z_" +  var.var + "',[]],_)";
				
				//if (#METHODCALL.getText().equals("METHODCALLASYNC"))
				//s="thread(" + s +")";
				}
				
				if (exp2 != null)
				if (exp2.prolog != null)
				if ( !exp2.prolog.equals(""))
				s= exp2.prolog + "," + s;
				
				
				if (exp1.prolog != null)
				if (! exp1.prolog.equals(""))
				s= exp1.prolog + "," + s;
				
				
				res.addAndPredicate(s);
				break;
			}
			case 50:
			{
				AST __t248 = _t;
				AST tmp64_AST_in = (AST)_t;
				match(_t,50);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				pre1=predicate(_t);
				_t = _retTree;
				_t = __t248;
				_t = _t.getNextSibling();
				break;
			}
			case EXISTS_NUM:
			{
				AST __t249 = _t;
				exi = _t==ASTNULL ? null :(AST)_t;
				match(_t,EXISTS_NUM);
				_t = _t.getFirstChild();
				//System.out.println("Exist Tree: " + exi.getText()); 
				/*symbols.push( exi.getText());*/
				schema_Text(_t);
				_t = _retTree;
				pre1=predicate(_t);
				_t = _retTree;
				_t = __t249;
				_t = _t.getNextSibling();
				res.addAndPredicate("\texists([" + pre1.toString() + "\t])" );  /*symbols.pop();*/
				break;
			}
			case 53:
			{
				AST __t250 = _t;
				AST tmp65_AST_in = (AST)_t;
				match(_t,53);
				_t = _t.getFirstChild();
				schema_Text(_t);
				_t = _retTree;
				pre1=predicate(_t);
				_t = _retTree;
				_t = __t250;
				_t = _t.getNextSibling();
				break;
			}
			case 54:
			{
				AST __t251 = _t;
				AST tmp66_AST_in = (AST)_t;
				match(_t,54);
				_t = _t.getFirstChild();
				pre1=predicate(_t);
				_t = _retTree;
				pre2=predicate(_t);
				_t = _retTree;
				_t = __t251;
				_t = _t.getNextSibling();
				break;
			}
			case 55:
			{
				AST __t252 = _t;
				AST tmp67_AST_in = (AST)_t;
				match(_t,55);
				_t = _t.getFirstChild();
				pre1=predicate(_t);
				_t = _retTree;
				pre2=predicate(_t);
				_t = _retTree;
				_t = __t252;
				_t = _t.getNextSibling();
				
				pre1.addAndPredicate(pre2);
				res=pre1; 
						
				break;
			}
			case 57:
			{
				AST __t253 = _t;
				AST tmp68_AST_in = (AST)_t;
				match(_t,57);
				_t = _t.getFirstChild();
				pre1=predicate(_t);
				_t = _retTree;
				pre2=predicate(_t);
				_t = _retTree;
				_t = __t253;
				_t = _t.getNextSibling();
				
				pre1.addOrPredicate(pre2);
				res=pre1;
						
				break;
			}
			case 59:
			{
				AST __t254 = _t;
				AST tmp69_AST_in = (AST)_t;
				match(_t,59);
				_t = _t.getFirstChild();
				pre1=predicate(_t);
				_t = _retTree;
				pre2=predicate(_t);
				_t = _retTree;
				_t = __t254;
				_t = _t.getNextSibling();
				break;
			}
			case IN_REL:
			{
				AST __t255 = _t;
				AST tmp70_AST_in = (AST)_t;
				match(_t,IN_REL);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t255;
				_t = _t.getNextSibling();
				
						  //System.out.println("Inrel: " +#IN_REL.getText() + "\n\texp1: " + exp1.toString() + "\n\texp2: " + exp2.toString() + "\n\n");
				
				//System.out.println("Inrel AUX-1: exp1.prolog'"+ exp1.prolog + "'");
				//System.out.println("Inrel AUX-1: exp2.prolog'"+ exp2.prolog + "'");
				saux = exp1.prolog + (exp1.prolog.equals("") ? "" :",\n")+
				exp2.prolog + (exp2.prolog.equals("") ? "" :",\n") + "\t";
				//System.out.println("Inrel AUX-1: " + saux);
				if (symbols.prologOperator(tmp70_AST_in.getText()).equals("infix") ) {
				saux =saux + exp1.var + symbols.toProlog(tmp70_AST_in.getText()) + exp2.var ;
				}
				else {  //prefix
				saux = saux + symbols.toProlog(tmp70_AST_in.getText()) + '(' +
				exp1.var + ',' + exp2.var + ')';
				}
				
							                res.addAndPredicate(saux);
				
				break;
			}
			case 77:
			{
				AST __t256 = _t;
				AST tmp71_AST_in = (AST)_t;
				match(_t,77);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				id=ident(_t);
				_t = _retTree;
				exp2=expression(_t);
				_t = _retTree;
				_t = __t256;
				_t = _t.getNextSibling();
				break;
			}
			case PRE_REL:
			{
				AST __t257 = _t;
				AST tmp72_AST_in = (AST)_t;
				match(_t,PRE_REL);
				_t = _t.getFirstChild();
				exp1=expression(_t);
				_t = _retTree;
				_t = __t257;
				_t = _t.getNextSibling();
				break;
			}
			case 70:
			{
				AST __t258 = _t;
				AST tmp73_AST_in = (AST)_t;
				match(_t,70);
				_t = _t.getFirstChild();
				schema_Ref(_t);
				_t = _retTree;
				_t = __t258;
				_t = _t.getNextSibling();
				break;
			}
			case 79:
			{
				AST tmp74_AST_in = (AST)_t;
				match(_t,79);
				_t = _t.getNextSibling();
				break;
			}
			case 80:
			{
				AST tmp75_AST_in = (AST)_t;
				match(_t,80);
				_t = _t.getNextSibling();
				break;
			}
			case 69:
			{
				AST __t259 = _t;
				AST tmp76_AST_in = (AST)_t;
				match(_t,69);
				_t = _t.getFirstChild();
				pre1=predicate(_t);
				_t = _retTree;
				_t = __t259;
				_t = _t.getNextSibling();
				res.addAndPredicate("\tnot(" + pre1.toString() + "\t)" );
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final ZTree_Exp_Res  varName4(AST _t) throws RecognitionException {
		ZTree_Exp_Res res;
		
		AST varName4_AST_in = (AST)_t;
		AST s = null;
		res= null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case WORD:
			{
				res=varName(_t);
				_t = _retTree;
				//System.out.println("VAR NAME1:  " +  res.toString());
				if (res.var.startsWith("Z_")) res.var=res.var.substring(2);
				
				break;
			}
			case SCHEMA_NAME:
			{
				s = (AST)_t;
				match(_t,SCHEMA_NAME);
				_t = _t.getNextSibling();
				res = new ZTree_Exp_Res();
				res.var= s.getText() ;
				res.prolog="";
				//System.out.println("VAR NAME2:  " +  res.toString());
				
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final ZTree_Exp_Res  expressions(AST _t) throws RecognitionException {
		ZTree_Exp_Res res;
		
		AST expressions_AST_in = (AST)_t;
		res= new ZTree_Exp_Res(); ZTree_Exp_Res exp=new ZTree_Exp_Res();
		
		try {      // for error handling
			{
			int _cnt323=0;
			_loop323:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_1.member(_t.getType()))) {
					exp=expression(_t);
					_t = _retTree;
					res.var+=  ((res.var=="")? "" : ",") + exp.var;
					if (!exp.prolog.equals("")) res.prolog +=  ((res.prolog=="")? "" : ",") + exp.prolog;
				}
				else {
					if ( _cnt323>=1 ) { break _loop323; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt323++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final SchemaDisplayTextResponse  schema_Text(AST _t) throws RecognitionException {
		SchemaDisplayTextResponse res;
		
		AST schema_Text_AST_in = (AST)_t;
		res=new SchemaDisplayTextResponse(); String decl=null;  PredicateResponse pres = null;
		
		try {      // for error handling
			AST __t327 = _t;
			AST tmp77_AST_in = (AST)_t;
			match(_t,SCHEMA_TEXT);
			_t = _t.getFirstChild();
			decl=declaration(_t);
			_t = _retTree;
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EXISTS_NUM:
			case METHODCALL:
			case 50:
			case 53:
			case 54:
			case 55:
			case 57:
			case 59:
			case 69:
			case 70:
			case IN_REL:
			case 77:
			case PRE_REL:
			case 79:
			case 80:
			{
				pres=predicate(_t);
				_t = _retTree;
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t327;
			_t = _t.getNextSibling();
			res.decl=decl;
						res.predicate=pres;
						//if (pres != null) System.out.println("schema_Text: " + decl + " // " +  pres.toString());
						//else System.out.println("schemaText, predicate null");
						
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final SchemaReferenceResponse  schema_Ref(AST _t) throws RecognitionException {
		SchemaReferenceResponse res;
		
		AST schema_Ref_AST_in = (AST)_t;
		AST schemaName = null;
		AST delta = null;
		AST decor = null;
		res = new SchemaReferenceResponse();
		
		try {      // for error handling
			AST __t330 = _t;
			schemaName = _t==ASTNULL ? null :(AST)_t;
			match(_t,SCHEMA_NAME);
			_t = _t.getFirstChild();
			res.setName(schemaName.getText());
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case 71:
			{
				delta = (AST)_t;
				match(_t,71);
				_t = _t.getNextSibling();
				break;
			}
			case 72:
			{
				AST tmp78_AST_in = (AST)_t;
				match(_t,72);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			case STROKE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			decor = _t==ASTNULL ? null : (AST)_t;
			decoration(_t);
			_t = _retTree;
			_t = __t330;
			_t = _t.getNextSibling();
			
						     if (delta!=null /*&& "\\Delta".equals(delta.getText())*/) {
			
							 res.setDelta(true);
						     }
			
						       if (decor!=null && "'".equals(decor.getText())) {
							   //System.out.println("Decor!!!: " + decor.getText());
			
							   res.setDecor(true);
						     }
						       else {
						     Prototype sch = (Prototype)symbols.get(schemaName.getText());
						     String getContains = new String();
						     /* Vector vdecl = (Vector)sch.getSlot("declarations");
						     for (Enumeration ed=vdecl.elements(); ed.hasMoreElements();) {
							 String container = (String)sch.getSlot("name");
							 String varname = (String)ed.nextElement();
							 String var = varPrefix+varname;
							 String getContain = "\t"+"getContain(Z_State,'"+container+"','"+var+"',"+var+")";
							 getContains += ("".equals(getContains))? getContain : ",\n"+getContain;
							 }*/
						     res.setContains(getContains);
						     // System.out.println("Contains schema: "  + schemaName.getText() + " declarations " + vdecl.toString() + " ::: " + getContains);
						     //out.print(getContains);
						       } //else
						
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final void decoration(AST _t) throws RecognitionException {
		
		AST decoration_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			_loop357:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==STROKE)) {
					AST tmp79_AST_in = (AST)_t;
					match(_t,STROKE);
					_t = _t.getNextSibling();
				}
				else {
					break _loop357;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void gen_Actuals(AST _t) throws RecognitionException {
		
		AST gen_Actuals_AST_in = (AST)_t;
		ZTree_Exp_Res exp=null;
		
		try {      // for error handling
			AST __t350 = _t;
			AST tmp80_AST_in = (AST)_t;
			match(_t,GEN_ACTUALS);
			_t = _t.getFirstChild();
			{
			int _cnt352=0;
			_loop352:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_1.member(_t.getType()))) {
					exp=expression(_t);
					_t = _retTree;
				}
				else {
					if ( _cnt352>=1 ) { break _loop352; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt352++;
			} while (true);
			}
			_t = __t350;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final String  declPart(AST _t) throws RecognitionException {
		String res;
		
		AST declPart_AST_in = (AST)_t;
		res=""; String saux;
		
		try {      // for error handling
			AST __t336 = _t;
			AST tmp81_AST_in = (AST)_t;
			match(_t,DECL_PART);
			_t = _t.getFirstChild();
			{
			int _cnt338=0;
			_loop338:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==SCHEMA_NAME||_t.getType()==COLON)) {
					saux=basicDecl(_t);
					_t = _retTree;
					//System.out.println("declPart: basicDecl="+saux);
					if ( saux!=null && !"".equals(saux) )
					res += ("".equals(res))? saux : ",\n"+saux;
				}
				else {
					if ( _cnt338>=1 ) { break _loop338; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt338++;
			} while (true);
			}
			_t = __t336;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final PredicateResponse  axiomPart(AST _t) throws RecognitionException {
		PredicateResponse res;
		
		AST axiomPart_AST_in = (AST)_t;
		res=new PredicateResponse(); PredicateResponse paux=null;
		
		try {      // for error handling
			AST __t340 = _t;
			AST tmp82_AST_in = (AST)_t;
			match(_t,AXIOM_PART);
			_t = _t.getFirstChild();
			{
			int _cnt342=0;
			_loop342:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_2.member(_t.getType()))) {
					paux=predicate(_t);
					_t = _retTree;
					//System.out.println("Predicate Rec: " + paux.toString());
										res.addAndPredicate(paux);
				}
				else {
					if ( _cnt342>=1 ) { break _loop342; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt342++;
			} while (true);
			}
			_t = __t340;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final String  declaration(AST _t) throws RecognitionException {
		String res;
		
		AST declaration_AST_in = (AST)_t;
		res=""; String saux;
		
		try {      // for error handling
			{
			int _cnt334=0;
			_loop334:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==SCHEMA_NAME||_t.getType()==COLON)) {
					saux=basicDecl(_t);
					_t = _retTree;
					res += ("".equals(res))? saux : ",\n"+saux;
				}
				else {
					if ( _cnt334>=1 ) { break _loop334; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt334++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	public final String  basicDecl(AST _t) throws RecognitionException {
		String res;
		
		AST basicDecl_AST_in = (AST)_t;
		res = ""; ZTree_Exp_Res id=null; SchemaReferenceResponse ref=null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case COLON:
			{
				AST __t344 = _t;
				AST tmp83_AST_in = (AST)_t;
				match(_t,COLON);
				_t = _t.getFirstChild();
				{
				int _cnt346=0;
				_loop346:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==WORD)) {
						id=ident(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt346>=1 ) { break _loop346; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt346++;
				} while (true);
				}
				_t = __t344;
				_t = _t.getNextSibling();
				res=id.var;
				break;
			}
			case SCHEMA_NAME:
			{
				ref=schema_Ref(_t);
				_t = _retTree;
				res=ref.getContains();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return res;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"SCHEMA",
		"GEN_FORMALS",
		"SINTAXIS_PARA",
		"ZED_PARA",
		"AXIOM_DECL_PART",
		"DECL_PART",
		"AXIOM_PART",
		"SCHEMA_TEXT",
		"DECLARATION",
		"GEN_ACTUALS",
		"RENAMING",
		"EXPRESSIONS",
		"SETENUM",
		"SETSEXP",
		"SETSSCH",
		"OR",
		"TUPLE",
		"EXISTS_NUM",
		"APPLICATION",
		"METHODCALL",
		"SETVAR",
		"\"\\\\begin{zed}\"",
		"\"\\\\end{zed}\"",
		"\"\\\\begin{axdef}\"",
		"\"\\\\end{axdef}\"",
		"\"\\\\begin{gendef}\"",
		"\"\\\\end{gendef}\"",
		"\"\\\\begin{schema}\"",
		"\"\\\\end{schema}\"",
		"OPENBRACKET",
		"COMMA",
		"CLOSEBRACKET",
		"\"\\\\defs\"",
		"WORD",
		"\"\\\\sdef\"",
		"\"\\\\ddef\"",
		"\"\\\\bbar\"",
		"OPENBRACE",
		"CLOSEBRACE",
		"\"\\\\ldata\"",
		"\"\\\\rdata\"",
		"PRE_GEN",
		"IN_GEN",
		"SEMICOLON",
		"\"\\\\also\"",
		"BACKSLASH_BACKSLASH",
		"\"\\\\forall\"",
		"AT",
		"\"\\\\exists\"",
		"\"\\\\exists_1\"",
		"\"\\\\implies\"",
		"\"\\\\land\"",
		"\"\\\\wedge\"",
		"\"\\\\lor\"",
		"\"\\\\vee\"",
		"\"\\\\iff\"",
		"\"\\\\project\"",
		"\"\\\\zcmp\"",
		"\"\\\\semi\"",
		"\"\\\\pipe\"",
		"\"\\\\hide\"",
		"OPENPAREN",
		"CLOSEPAREN",
		"\"\\\\lsch\"",
		"\"\\\\rsch\"",
		"\"\\\\lnot\"",
		"\"\\\\pre\"",
		"\"\\\\Delta\"",
		"\"\\\\Xi\"",
		"SCHEMA_NAME",
		"SLASH",
		"\"\\\\LET\"",
		"IN_REL",
		"\"\\\\inrel\"",
		"PRE_REL",
		"\"\\\\true\"",
		"\"\\\\false\"",
		"\"\\\\lambda\"",
		"\"\\\\mu\"",
		"\"\\\\IF\"",
		"\"\\\\THEN\"",
		"\"\\\\ELSE\"",
		"\"\\\\cross\"",
		"\"\\\\prod\"",
		"IN_FUN1",
		"IN_FUN2",
		"IN_FUN3",
		"IN_FUN4",
		"IN_FUN5",
		"IN_FUN6",
		"\"\\\\power\"",
		"\"\\\\pset\"",
		"\"\\\\seq\"",
		"HYPHEN",
		"\"\\\\limg\"",
		"\"\\\\rimg\"",
		"POINT",
		"\"|\"",
		"POST_FUN",
		"\"\\\\bsup\"",
		"\"\\\\esup\"",
		"NUMBER",
		"\"\\\\emptyset\"",
		"\"\\\\emptyseq\"",
		"\"\\\\langle\"",
		"\"\\\\rangle\"",
		"\"\\\\lseq\"",
		"\"\\\\rseq\"",
		"\"\\\\lbag\"",
		"\"\\\\rbag\"",
		"\"\\\\theta\"",
		"Renaming",
		"\"\\\\nat\"",
		"\"\\\\natone\"",
		"OPENSET",
		"CLOSESET",
		"COLON",
		"\"\\\\where\"",
		"STROKE",
		"UNDERSCORE",
		"IN_FUN",
		"NL",
		"WS",
		"DIGIT",
		"LETTER",
		"IDENT",
		"INFIX"
	};
	
	private static final long _tokenSet_0_data_[] = { 902077349904L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 105690560921600L, 15022451273368064L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { 784752235079794688L, 127072L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	}
	
