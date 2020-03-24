// $ANTLR 2.7.1: "Zeta.g" -> "ZParser.java"$
package org.jdesktop.lg3d.apps.archviz3d.zparser; 
import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;
    
import java.io.*;   
import antlr.DumpASTVisitor;

import java.util.*;
import java.lang.reflect.Array;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.*;


public class ZParser extends antlr.LLkParser
       implements ZTokenTypes
 {

        protected SymbolTable symbols;

        PrototypeFactory factory;



        public PrototypeFactory factory() {
          return factory;
        }


        private Prototype getCurrent() {
          return symbols.getCurrent();
	}

        private void push(String name) {
            //System.out.println("DOING Push" + name);
          symbols.push(symbols.get(name));
	}

	private void pop() {
          symbols.pop();
	}

        private void addSchema(String schemaName) {
	    //System.out.println("addSchema:adding schema "+schemaName);
          symbols.add_schema(schemaName);
	}

  //      private void addEmptySchema() {
//	    symbols.add_Emptyschema();
//	}

	private void addDelta(String delta, String mode) {
	  //System.out.println("Deltas Before:" + ((Vector)getCurrent().getSlot("deltas")).toString());
	  ((Vector)getCurrent().getSlot("deltas")).add(new SchemaRef(delta,mode));
	  //System.out.println("Deltas After:" + ((Vector)getCurrent().getSlot("deltas")).toString());
	}

	private void addSymbol(String type, String name) {
	  	//  System.out.println("ADD Symbol: " + name + " type: " + type);
	  symbols.add_symbol(type,name);

        }

	private void addType(String name, ZType type) {
	//  System.out.println("ADD Type: " + name + " type: " + type.toString());
	  symbols.add_type(name,type);

        }

	private  ZType getType(String name) {
	    try {
	//  System.out.println("GetType=" + name);
	  return getCurrent().getSlotPrototype(name).getSlotZType("type"); } 
	    catch (Exception e) { return null; }
	}

	private  ZType getTypeOfVar(String name) {
	//  System.out.println("GetTyamepeof Variable=" + name);
            if (getCurrent().getSlotPrototype(name) != null)
                return getType(getCurrent().getSlotPrototype(name).getSlotString("type"));
                else return null;
	}

	private boolean  isType(String name) {
	  if (name.equals("")) return false;
	  if (getCurrent().get(name) != null) return (getCurrent().getSlotPrototype(name).getName().equals("type"));
	    else return false;
	}
	
	private boolean  isFunction(String name) {
	   // System.out.println("IsFunction");
	    //if (getCurrent().get(name) != null ) return getCurrent().get(name).equals("\\pfun"); 
	    //else return false;
	    return  name.equals("bal");
	}


        public void initPrototypes() {
                factory=new PrototypeFactory();

                Prototype prot=new Prototype("symbol"); //Symbol
                prot.addSlot("name");
                prot.addSlot("type");
		prot.addSlot("isVar");
		prot.addSlot("prolog");
                factory.put(prot);

		prot=new Prototype("type"); //Type
		prot.addSlot("type");
                prot.addSlot("name");
	
                factory.put(prot);

                prot=new Prototype("schema");   //Schema
                prot.addSlot("name");
                prot.addSlot("declarations");
                prot.setSlot("declarations",new Vector());
                prot.addSlot("includes");
                prot.setSlot("includes",new Vector());
                prot.addSlot("deltas");
                prot.setSlot("deltas",new Vector());
		prot.addSlot("changes");
                prot.setSlot("changes",new Vector());
                factory.put(prot);

                prot=new Prototype("declarations");     //Declaration
                prot.addSlot("name");
                factory.put(prot);

                prot=new Prototype("inop");     //inop
                prot.addSlot("name");
                prot.addSlot("priority");
                prot.addSlot("prolog");
                prot.addSlot("operator");
                factory.put(prot);

                prot=new Prototype("postop");   //postop
                prot.addSlot("name");
                prot.addSlot("prolog");
                factory.put(prot);

                prot=new Prototype("inrel");    //inrel
                prot.addSlot("name");
                prot.addSlot("prolog");
                prot.addSlot("operator");
                factory.put(prot);

                prot=new Prototype("prerel");   //prerel
                prot.addSlot("name");
                prot.addSlot("prolog");
                factory.put(prot);

                prot=new Prototype("ingen");    //ingen
                prot.addSlot("name");
                prot.addSlot("prolog");
                factory.put(prot);

                prot=new Prototype("pregen");   //pregen
                prot.addSlot("name");
                prot.addSlot("prolog");
                factory.put(prot);

                prot=new Prototype("ignores");  //ignores
                prot.addSlot("name");
                prot.addSlot("prolog");
                factory.put(prot);

                
        }


        public void symbolTable(SymbolTable st) {
                symbols=st;
        }


        public static void  parse(String file, boolean debug) {
                try {
						System.out.println("------------------------------------------" );				
						System.out.println("-----------------Parsing: " + file );
                        InputStream input = new FileInputStream(file);
                        ZLexer lexer = new ZLexer(input);
                        lexer.setTokenObjectClass("reqviz.zparser.tools.ZToken");

                        ZParser parser = new ZParser(lexer);
                        parser.initPrototypes();

                        SymbolTable st= new SymbolTable(parser.factory());
                        lexer.symbolTable(st);
                        parser.symbolTable(st);

                        //parser.addEmptySchema();
                        parser.specification();

                        if (debug) {
                        System.out.println(parser.getAST().toStringList());
                        System.out.println("-----------------------------");
                        
                        System.out.println("___________VISITOR_____________");
                        DumpASTVisitor visitor= new DumpASTVisitor();
                        visitor.visit(parser.getAST()); }

                        //System.out.println("hash -------" + st.toString());

                        //System.out.println("------------------------Codigo Prolog---------------------");
                        ZTreeWalker walker = new ZTreeWalker();
                        walker.symbolTable(st);
                        walker.out(new PrintStream(new FileOutputStream(file+".pl")));
                        //walker.out(System.out);
                        walker.specification(parser.getAST());  // walk tree

                        //visitor.visit(walker.getAST());

	                //System.out.println( st.dump() );
			//System.out.println( ((Prototype)st.get("Class")).dump() );
			//System.out.println( ((Prototype)st.get("Enrolok")).dump() );
			//System.out.println( ((Prototype)st.get("Enrol")).dump() );
			//System.out.println( ((Prototype)((Prototype)st.get("Class")).getSlot("enrolled")).dump() );
			//System.out.println( ((Prototype)((Prototype)st.get("Enrolok")).getSlot("s")).dump() );

                }
                catch (Exception e) {
                        System.err.println("parser exception: "+e);
                        e.printStackTrace();   // so we can get stack trace
                }
        }



        public static void main(String[] args) {
            boolean debug=false;
                try {
                        if (Array.getLength(args) ==0 ) {
                            System.out.println("Usage: Options ZParser files");
                            System.out.println("Options: -debug");
                            System.exit(0);
                        }
                        int iargs=0;
                        if (args[0].equals("-debug")) {debug=true; iargs++; }
                        for (; iargs < args.length; iargs++) {
                            parse(args[iargs],debug);
                        }
                        
                }
                catch (Exception e) {
                        System.err.println("parser exception: "+e);
                        e.printStackTrace();   // so we can get stack trace
                }
        }

protected ZParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public ZParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected ZParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public ZParser(TokenStream lexer) {
  this(lexer,3);
}

public ZParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
}

	public final void specification() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST specification_AST = null;
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					paragraph();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			AST tmp84_AST = null;
			tmp84_AST = (AST)astFactory.create(LT(1));
			match(Token.EOF_TYPE);
			specification_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = specification_AST;
	}
	
	public final void paragraph() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paragraph_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 25:
			{
				AST tmp85_AST = null;
				tmp85_AST = (AST)astFactory.create(LT(1));
				match(25);
				zedParagraphs();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp86_AST = null;
				tmp86_AST = (AST)astFactory.create(LT(1));
				match(26);
				paragraph_AST = (AST)currentAST.root;
				break;
			}
			case 27:
			{
				AST tmp87_AST = null;
				if (inputState.guessing==0) {
					tmp87_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp87_AST);
				}
				match(27);
				{
				switch ( LA(1)) {
				case OPENBRACKET:
				{
					formals();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case WORD:
				case PRE_GEN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case PRE_REL:
				case HYPHEN:
				case UNDERSCORE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				schemaDisplayText();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp88_AST = null;
				if (inputState.guessing==0) {
					tmp88_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp88_AST);
				}
				match(28);
				paragraph_AST = (AST)currentAST.root;
				break;
			}
			case 29:
			{
				AST tmp89_AST = null;
				if (inputState.guessing==0) {
					tmp89_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp89_AST);
				}
				match(29);
				{
				switch ( LA(1)) {
				case OPENBRACKET:
				{
					formals();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case WORD:
				case PRE_GEN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case PRE_REL:
				case HYPHEN:
				case UNDERSCORE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				schemaDisplayText();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp90_AST = null;
				tmp90_AST = (AST)astFactory.create(LT(1));
				match(30);
				paragraph_AST = (AST)currentAST.root;
				break;
			}
			case 31:
			{
				AST tmp91_AST = null;
				tmp91_AST = (AST)astFactory.create(LT(1));
				match(31);
				schemaParagraph();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp92_AST = null;
				tmp92_AST = (AST)astFactory.create(LT(1));
				match(32);
				paragraph_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = paragraph_AST;
	}
	
	public final void zedParagraphs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST zedParagraphs_AST = null;
		
		try {      // for error handling
			zedParagraph();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop9:
			do {
				if (((LA(1) >= SEMICOLON && LA(1) <= BACKSLASH_BACKSLASH))) {
					sep();
					zedParagraph();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop9;
				}
				
			} while (true);
			}
			zedParagraphs_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = zedParagraphs_AST;
	}
	
	public final void formals() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST formals_AST = null;
		
		try {      // for error handling
			AST tmp93_AST = null;
			tmp93_AST = (AST)astFactory.create(LT(1));
			match(OPENBRACKET);
			ident();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop30:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp94_AST = null;
					tmp94_AST = (AST)astFactory.create(LT(1));
					match(COMMA);
					ident();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop30;
				}
				
			} while (true);
			}
			AST tmp95_AST = null;
			tmp95_AST = (AST)astFactory.create(LT(1));
			match(CLOSEBRACKET);
			formals_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = formals_AST;
	}
	
	public final void schemaDisplayText() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaDisplayText_AST = null;
		
		try {      // for error handling
			decl_Part();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case 121:
			{
				AST tmp96_AST = null;
				tmp96_AST = (AST)astFactory.create(LT(1));
				match(121);
				axiom_Part();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case 28:
			case 30:
			case 32:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			schemaDisplayText_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaDisplayText_AST;
	}
	
	public final void schemaParagraph() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaParagraph_AST = null;
		Token  schemaName = null;
		AST schemaName_AST = null;
		
		try {      // for error handling
			AST tmp97_AST = null;
			tmp97_AST = (AST)astFactory.create(LT(1));
			match(OPENBRACE);
			schemaName = LT(1);
			if (inputState.guessing==0) {
				schemaName_AST = (AST)astFactory.create(schemaName);
				astFactory.addASTChild(currentAST, schemaName_AST);
			}
			match(WORD);
			if ( inputState.guessing==0 ) {
				addSchema(schemaName_AST.getText());
			}
			{
			switch ( LA(1)) {
			case OPENBRACKET:
			{
				formals();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case CLOSEBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp98_AST = null;
			tmp98_AST = (AST)astFactory.create(LT(1));
			match(CLOSEBRACE);
			if ( inputState.guessing==0 ) {
				push(schemaName_AST.getText());
			}
			schemaDisplayText();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				pop();
			}
			if ( inputState.guessing==0 ) {
				schemaParagraph_AST = (AST)currentAST.root;
				schemaParagraph_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(SCHEMA,"SCHEMA")).add(schemaParagraph_AST));
				currentAST.root = schemaParagraph_AST;
				currentAST.child = schemaParagraph_AST!=null &&schemaParagraph_AST.getFirstChild()!=null ?
					schemaParagraph_AST.getFirstChild() : schemaParagraph_AST;
				currentAST.advanceChildToEnd();
			}
			schemaParagraph_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaParagraph_AST;
	}
	
	public final void zedParagraph() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST zedParagraph_AST = null;
		AST set_AST = null;
		AST set2_AST = null;
		AST def1_AST = null;
		AST exp1_AST = null;
		Token  interfaceName = null;
		AST interfaceName_AST = null;
		AST enumT_AST = null;
		AST b1_AST = null;
		AST b2_AST = null;
		ZTypeComposed type=null;
		
		try {      // for error handling
			if ((LA(1)==OPENBRACKET)) {
				AST tmp99_AST = null;
				if (inputState.guessing==0) {
					tmp99_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp99_AST);
				}
				match(OPENBRACKET);
				ident();
				if (inputState.guessing==0) {
					set_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					type=new ZTypeComposed(set_AST.getText(),"set"); addType(set_AST.getText(),type);
				}
				{
				_loop12:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp100_AST = null;
						tmp100_AST = (AST)astFactory.create(LT(1));
						match(COMMA);
						ident();
						if (inputState.guessing==0) {
							set2_AST = (AST)returnAST;
							astFactory.addASTChild(currentAST, returnAST);
						}
						if ( inputState.guessing==0 ) {
							type=new ZTypeComposed(set2_AST.getText(),"set"); addType(set2_AST.getText(),type);
						}
					}
					else {
						break _loop12;
					}
					
				} while (true);
				}
				AST tmp101_AST = null;
				tmp101_AST = (AST)astFactory.create(LT(1));
				match(CLOSEBRACKET);
				zedParagraph_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched14 = false;
				if (((LA(1)==WORD||LA(1)==PRE_GEN) && (_tokenSet_7.member(LA(2))) && (_tokenSet_8.member(LA(3))))) {
					int _m14 = mark();
					synPredMatched14 = true;
					inputState.guessing++;
					try {
						{
						def_Lhs();
						match(36);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched14 = false;
					}
					rewind(_m14);
					inputState.guessing--;
				}
				if ( synPredMatched14 ) {
					def_Lhs();
					if (inputState.guessing==0) {
						def1_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp102_AST = null;
					if (inputState.guessing==0) {
						tmp102_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp102_AST);
					}
					match(36);
					expression();
					if (inputState.guessing==0) {
						exp1_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					if ( inputState.guessing==0 ) {
						//System.out.println("defs: " + #def1.getText() + " " + #exp1.getText());
										 
										  if (isType(def1_AST.getText())) {
										    //verificar que es conjunto y lo que declare es igual a este nuevo conjunto 
										  }
										      else {
											symbols.add_symbol(def1_AST.getText());
											getCurrent().getSlotPrototype(def1_AST.getText()).setSlot("type","\\defs");
										      }
											
					}
					zedParagraph_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched17 = false;
					if (((LA(1)==WORD) && (LA(2)==OPENBRACKET||LA(2)==38) && (_tokenSet_9.member(LA(3))))) {
						int _m17 = mark();
						synPredMatched17 = true;
						inputState.guessing++;
						try {
							{
							match(WORD);
							{
							switch ( LA(1)) {
							case OPENBRACKET:
							{
								formals();
								break;
							}
							case 38:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							match(38);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched17 = false;
						}
						rewind(_m17);
						inputState.guessing--;
					}
					if ( synPredMatched17 ) {
						interfaceName = LT(1);
						if (inputState.guessing==0) {
							interfaceName_AST = (AST)astFactory.create(interfaceName);
							astFactory.addASTChild(currentAST, interfaceName_AST);
						}
						match(WORD);
						if ( inputState.guessing==0 ) {
							addSchema(interfaceName_AST.getText());
						}
						{
						switch ( LA(1)) {
						case OPENBRACKET:
						{
							formals();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							break;
						}
						case 38:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						AST tmp103_AST = null;
						if (inputState.guessing==0) {
							tmp103_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp103_AST);
						}
						match(38);
						if ( inputState.guessing==0 ) {
							push(interfaceName_AST.getText());
						}
						schema_Exp();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						if ( inputState.guessing==0 ) {
							pop();
						}
						zedParagraph_AST = (AST)currentAST.root;
					}
					else {
						boolean synPredMatched20 = false;
						if (((LA(1)==WORD) && (LA(2)==39||LA(2)==STROKE) && (LA(3)==WORD||LA(3)==39||LA(3)==STROKE))) {
							int _m20 = mark();
							synPredMatched20 = true;
							inputState.guessing++;
							try {
								{
								ident();
								match(39);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched20 = false;
							}
							rewind(_m20);
							inputState.guessing--;
						}
						if ( synPredMatched20 ) {
							ident();
							if (inputState.guessing==0) {
								enumT_AST = (AST)returnAST;
								astFactory.addASTChild(currentAST, returnAST);
							}
							if ( inputState.guessing==0 ) {
								type=new ZTypeComposed(enumT_AST.getText(),"enum"); addType(enumT_AST.getText(),type);
							}
							AST tmp104_AST = null;
							if (inputState.guessing==0) {
								tmp104_AST = (AST)astFactory.create(LT(1));
								astFactory.makeASTRoot(currentAST, tmp104_AST);
							}
							match(39);
							branch();
							if (inputState.guessing==0) {
								b1_AST = (AST)returnAST;
								astFactory.addASTChild(currentAST, returnAST);
							}
							if ( inputState.guessing==0 ) {
								addSymbol(enumT_AST.getText(),b1_AST.getText()); type.add(b1_AST.getText());
							}
							{
							_loop23:
							do {
								if ((_tokenSet_10.member(LA(1))) && (LA(2)==WORD||LA(2)==40) && (_tokenSet_11.member(LA(3)))) {
									{
									switch ( LA(1)) {
									case SEMICOLON:
									case 48:
									case BACKSLASH_BACKSLASH:
									{
										sep();
										if (inputState.guessing==0) {
											astFactory.addASTChild(currentAST, returnAST);
										}
										break;
									}
									case 40:
									{
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									}
									}
									AST tmp105_AST = null;
									tmp105_AST = (AST)astFactory.create(LT(1));
									match(40);
									branch();
									if (inputState.guessing==0) {
										b2_AST = (AST)returnAST;
										astFactory.addASTChild(currentAST, returnAST);
									}
									if ( inputState.guessing==0 ) {
										addSymbol(enumT_AST.getText(),b2_AST.getText()); type.add(b2_AST.getText());
									}
								}
								else {
									break _loop23;
								}
								
							} while (true);
							}
							zedParagraph_AST = (AST)currentAST.root;
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}}}
					}
					catch (RecognitionException ex) {
						if (inputState.guessing==0) {
							reportError(ex);
							consume();
							consumeUntil(_tokenSet_12);
						} else {
						  throw ex;
						}
					}
					returnAST = zedParagraph_AST;
				}
				
	public final void sep() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sep_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case SEMICOLON:
			{
				AST tmp106_AST = null;
				if (inputState.guessing==0) {
					tmp106_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp106_AST);
				}
				match(SEMICOLON);
				sep_AST = (AST)currentAST.root;
				break;
			}
			case 48:
			{
				AST tmp107_AST = null;
				if (inputState.guessing==0) {
					tmp107_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp107_AST);
				}
				match(48);
				sep_AST = (AST)currentAST.root;
				break;
			}
			case BACKSLASH_BACKSLASH:
			{
				AST tmp108_AST = null;
				if (inputState.guessing==0) {
					tmp108_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp108_AST);
				}
				match(BACKSLASH_BACKSLASH);
				sep_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		returnAST = sep_AST;
	}
	
	public final void ident() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ident_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST dec_AST = null;
		
		try {      // for error handling
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = (AST)astFactory.create(id);
				astFactory.makeASTRoot(currentAST, id_AST);
			}
			match(WORD);
			decoration();
			if (inputState.guessing==0) {
				dec_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				
				
				if (dec_AST != null)
				if (dec_AST.getText().equals("\'")) { symbols.add_changed(id_AST.getText()); //System.out.println("Decorated: " + #id.getText()); 
				}
				
			}
			ident_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		returnAST = ident_AST;
	}
	
	public final void def_Lhs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST def_Lhs_AST = null;
		AST var_AST = null;
		
		try {      // for error handling
			boolean synPredMatched34 = false;
			if (((LA(1)==WORD) && (LA(2)==OPENBRACKET||LA(2)==36||LA(2)==STROKE) && (_tokenSet_15.member(LA(3))))) {
				int _m34 = mark();
				synPredMatched34 = true;
				inputState.guessing++;
				try {
					{
					var_Name_Dec();
					{
					if ((LA(1)==OPENBRACKET)) {
						formals();
					}
					else {
					}
					
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched34 = false;
				}
				rewind(_m34);
				inputState.guessing--;
			}
			if ( synPredMatched34 ) {
				var_Name_Dec();
				if (inputState.guessing==0) {
					var_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				switch ( LA(1)) {
				case OPENBRACKET:
				{
					formals();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case 36:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				def_Lhs_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==PRE_GEN)) {
				AST tmp109_AST = null;
				if (inputState.guessing==0) {
					tmp109_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp109_AST);
				}
				match(PRE_GEN);
				ident();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				def_Lhs_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched37 = false;
				if (((LA(1)==WORD) && (LA(2)==IN_GEN||LA(2)==STROKE) && (LA(3)==WORD||LA(3)==IN_GEN||LA(3)==STROKE))) {
					int _m37 = mark();
					synPredMatched37 = true;
					inputState.guessing++;
					try {
						{
						ident();
						match(IN_GEN);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched37 = false;
					}
					rewind(_m37);
					inputState.guessing--;
				}
				if ( synPredMatched37 ) {
					ident();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp110_AST = null;
					if (inputState.guessing==0) {
						tmp110_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp110_AST);
					}
					match(IN_GEN);
					ident();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					def_Lhs_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					consume();
					consumeUntil(_tokenSet_16);
				} else {
				  throw ex;
				}
			}
			returnAST = def_Lhs_AST;
		}
		
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 83:
			{
				AST tmp111_AST = null;
				if (inputState.guessing==0) {
					tmp111_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp111_AST);
				}
				match(83);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp112_AST = null;
				tmp112_AST = (AST)astFactory.create(LT(1));
				match(84);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp113_AST = null;
				tmp113_AST = (AST)astFactory.create(LT(1));
				match(85);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_AST = (AST)currentAST.root;
				break;
			}
			case WORD:
			case PRE_GEN:
			case OPENPAREN:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case 94:
			case 95:
			case 96:
			case HYPHEN:
			case NUMBER:
			case 106:
			case 107:
			case 108:
			case 110:
			case 112:
			case 114:
			case 116:
			case 117:
			case OPENSET:
			{
				expression_1();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_AST;
	}
	
	public final void schema_Exp() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Exp_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 50:
			{
				AST tmp114_AST = null;
				if (inputState.guessing==0) {
					tmp114_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp114_AST);
				}
				match(50);
				schema_Text("forall");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp115_AST = null;
				tmp115_AST = (AST)astFactory.create(LT(1));
				match(AT);
				schema_Exp();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_AST = (AST)currentAST.root;
				break;
			}
			case 52:
			{
				AST tmp116_AST = null;
				if (inputState.guessing==0) {
					tmp116_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp116_AST);
				}
				match(52);
				schema_Text("exists");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp117_AST = null;
				tmp117_AST = (AST)astFactory.create(LT(1));
				match(AT);
				schema_Exp();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_AST = (AST)currentAST.root;
				break;
			}
			case 53:
			{
				AST tmp118_AST = null;
				if (inputState.guessing==0) {
					tmp118_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp118_AST);
				}
				match(53);
				schema_Text("exists_1");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp119_AST = null;
				tmp119_AST = (AST)astFactory.create(LT(1));
				match(AT);
				schema_Exp();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_AST = (AST)currentAST.root;
				break;
			}
			case OPENPAREN:
			case 67:
			case 69:
			case 70:
			case 71:
			case 72:
			case SCHEMA_NAME:
			{
				schema_Exp_1();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Exp_AST;
	}
	
	public final void branch() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST branch_AST = null;
		AST i_AST = null;
		
		try {      // for error handling
			ident();
			if (inputState.guessing==0) {
				i_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case 43:
			{
				AST tmp120_AST = null;
				if (inputState.guessing==0) {
					tmp120_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp120_AST);
				}
				match(43);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp121_AST = null;
				if (inputState.guessing==0) {
					tmp121_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp121_AST);
				}
				match(44);
				break;
			}
			case 26:
			case 40:
			case SEMICOLON:
			case 48:
			case BACKSLASH_BACKSLASH:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			branch_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		returnAST = branch_AST;
	}
	
	public final void var_Name_Dec() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST var_Name_Dec_AST = null;
		AST i_AST = null;
		
		try {      // for error handling
			ident();
			if (inputState.guessing==0) {
				i_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			var_Name_Dec_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_20);
			} else {
			  throw ex;
			}
		}
		returnAST = var_Name_Dec_AST;
	}
	
	public final void schema_Text(
		String context
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Text_AST = null;
		
		try {      // for error handling
			declaration(context);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case 40:
			{
				AST tmp122_AST = null;
				tmp122_AST = (AST)astFactory.create(LT(1));
				match(40);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case AT:
			case CLOSEPAREN:
			case 68:
			case 99:
			case CLOSESET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				schema_Text_AST = (AST)currentAST.root;
				schema_Text_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(SCHEMA_TEXT,"SCHEMA_TEXT")).add(schema_Text_AST));
				currentAST.root = schema_Text_AST;
				currentAST.child = schema_Text_AST!=null &&schema_Text_AST.getFirstChild()!=null ?
					schema_Text_AST.getFirstChild() : schema_Text_AST;
				currentAST.advanceChildToEnd();
			}
			schema_Text_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Text_AST;
	}
	
	public final void schema_Exp_1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Exp_1_AST = null;
		
		try {      // for error handling
			schema_Exp_2();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case 54:
			{
				AST tmp123_AST = null;
				if (inputState.guessing==0) {
					tmp123_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp123_AST);
				}
				match(54);
				schema_Exp_1();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case 26:
			case SEMICOLON:
			case 48:
			case BACKSLASH_BACKSLASH:
			case CLOSEPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			schema_Exp_1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Exp_1_AST;
	}
	
	public final void schema_Exp_2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Exp_2_AST = null;
		
		try {      // for error handling
			schema_Exp_3();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop45:
			do {
				if (((LA(1) >= 55 && LA(1) <= 63))) {
					{
					switch ( LA(1)) {
					case 55:
					{
						AST tmp124_AST = null;
						if (inputState.guessing==0) {
							tmp124_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp124_AST);
						}
						match(55);
						break;
					}
					case 56:
					{
						AST tmp125_AST = null;
						if (inputState.guessing==0) {
							tmp125_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp125_AST);
						}
						match(56);
						break;
					}
					case 57:
					{
						AST tmp126_AST = null;
						if (inputState.guessing==0) {
							tmp126_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp126_AST);
						}
						match(57);
						break;
					}
					case 58:
					{
						AST tmp127_AST = null;
						if (inputState.guessing==0) {
							tmp127_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp127_AST);
						}
						match(58);
						break;
					}
					case 59:
					{
						AST tmp128_AST = null;
						if (inputState.guessing==0) {
							tmp128_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp128_AST);
						}
						match(59);
						break;
					}
					case 60:
					{
						AST tmp129_AST = null;
						if (inputState.guessing==0) {
							tmp129_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp129_AST);
						}
						match(60);
						break;
					}
					case 61:
					{
						AST tmp130_AST = null;
						if (inputState.guessing==0) {
							tmp130_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp130_AST);
						}
						match(61);
						break;
					}
					case 62:
					{
						AST tmp131_AST = null;
						if (inputState.guessing==0) {
							tmp131_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp131_AST);
						}
						match(62);
						break;
					}
					case 63:
					{
						AST tmp132_AST = null;
						if (inputState.guessing==0) {
							tmp132_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp132_AST);
						}
						match(63);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					schema_Exp_3();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop45;
				}
				
			} while (true);
			}
			schema_Exp_2_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Exp_2_AST;
	}
	
	public final void schema_Exp_3() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Exp_3_AST = null;
		
		try {      // for error handling
			schema_Exp_U();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop50:
			do {
				if ((LA(1)==64)) {
					AST tmp133_AST = null;
					if (inputState.guessing==0) {
						tmp133_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp133_AST);
					}
					match(64);
					AST tmp134_AST = null;
					tmp134_AST = (AST)astFactory.create(LT(1));
					match(OPENPAREN);
					decl_Name();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					_loop49:
					do {
						if ((LA(1)==COMMA)) {
							AST tmp135_AST = null;
							tmp135_AST = (AST)astFactory.create(LT(1));
							match(COMMA);
							decl_Name();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
						}
						else {
							break _loop49;
						}
						
					} while (true);
					}
					AST tmp136_AST = null;
					tmp136_AST = (AST)astFactory.create(LT(1));
					match(CLOSEPAREN);
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
			schema_Exp_3_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_23);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Exp_3_AST;
	}
	
	public final void schema_Exp_U() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Exp_U_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 67:
			{
				AST tmp137_AST = null;
				if (inputState.guessing==0) {
					tmp137_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp137_AST);
				}
				match(67);
				schema_Text("lsch");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp138_AST = null;
				tmp138_AST = (AST)astFactory.create(LT(1));
				match(68);
				schema_Exp_U_AST = (AST)currentAST.root;
				break;
			}
			case 69:
			{
				AST tmp139_AST = null;
				if (inputState.guessing==0) {
					tmp139_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp139_AST);
				}
				match(69);
				schema_Exp_U();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_U_AST = (AST)currentAST.root;
				break;
			}
			case 70:
			{
				AST tmp140_AST = null;
				if (inputState.guessing==0) {
					tmp140_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp140_AST);
				}
				match(70);
				schema_Exp_U();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_U_AST = (AST)currentAST.root;
				break;
			}
			case OPENPAREN:
			{
				AST tmp141_AST = null;
				tmp141_AST = (AST)astFactory.create(LT(1));
				match(OPENPAREN);
				schema_Exp();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp142_AST = null;
				tmp142_AST = (AST)astFactory.create(LT(1));
				match(CLOSEPAREN);
				schema_Exp_U_AST = (AST)currentAST.root;
				break;
			}
			case 71:
			case 72:
			case SCHEMA_NAME:
			{
				schema_Ref();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				schema_Exp_U_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Exp_U_AST;
	}
	
	public final void decl_Name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decl_Name_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PRE_GEN:
			case PRE_REL:
			case HYPHEN:
			case UNDERSCORE:
			{
				op_Name();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				decl_Name_AST = (AST)currentAST.root;
				break;
			}
			case WORD:
			{
				ident();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				decl_Name_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = decl_Name_AST;
	}
	
	public final void schema_Ref() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schema_Ref_AST = null;
		Token  delta = null;
		AST delta_AST = null;
		Token  xi = null;
		AST xi_AST = null;
		Token  name = null;
		AST name_AST = null;
		AST decor_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case 71:
			{
				delta = LT(1);
				if (inputState.guessing==0) {
					delta_AST = (AST)astFactory.create(delta);
					astFactory.addASTChild(currentAST, delta_AST);
				}
				match(71);
				break;
			}
			case 72:
			{
				xi = LT(1);
				if (inputState.guessing==0) {
					xi_AST = (AST)astFactory.create(xi);
					astFactory.addASTChild(currentAST, xi_AST);
				}
				match(72);
				break;
			}
			case SCHEMA_NAME:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name = LT(1);
			if (inputState.guessing==0) {
				name_AST = (AST)astFactory.create(name);
				astFactory.makeASTRoot(currentAST, name_AST);
			}
			match(SCHEMA_NAME);
			decoration();
			if (inputState.guessing==0) {
				decor_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case OPENBRACKET:
			{
				genActuals_or_renaming();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case 26:
			case 28:
			case 30:
			case 32:
			case COMMA:
			case CLOSEBRACKET:
			case WORD:
			case 40:
			case 44:
			case IN_GEN:
			case SEMICOLON:
			case 48:
			case BACKSLASH_BACKSLASH:
			case AT:
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
			case OPENPAREN:
			case CLOSEPAREN:
			case 68:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case IN_REL:
			case 77:
			case 84:
			case 85:
			case 86:
			case 87:
			case IN_FUN1:
			case IN_FUN2:
			case IN_FUN3:
			case IN_FUN4:
			case IN_FUN5:
			case IN_FUN6:
			case 98:
			case 99:
			case POINT:
			case POST_FUN:
			case 103:
			case 104:
			case NUMBER:
			case 106:
			case 107:
			case 108:
			case 109:
			case 110:
			case 111:
			case 112:
			case 113:
			case 114:
			case 116:
			case 117:
			case OPENSET:
			case CLOSESET:
			case 121:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				schema_Ref_AST = (AST)currentAST.root;
				symbols.includeRef(name_AST.getText(),schema_Ref_AST);
				if (delta_AST != null) this.addDelta(name_AST.getText(),delta_AST.getText());
							    else if (xi_AST != null) this.addDelta(name_AST.getText(),xi_AST.getText());
							    else if (decor_AST != null ) this.addDelta(name_AST.getText(),decor_AST.getText());
							    else  this.addDelta(name_AST.getText(),"");
				
				
			}
			schema_Ref_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		returnAST = schema_Ref_AST;
	}
	
	public final void decoration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decoration_AST = null;
		
		try {      // for error handling
			{
			_loop173:
			do {
				if ((LA(1)==STROKE)) {
					AST tmp143_AST = null;
					if (inputState.guessing==0) {
						tmp143_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp143_AST);
					}
					match(STROKE);
				}
				else {
					break _loop173;
				}
				
			} while (true);
			}
			decoration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_27);
			} else {
			  throw ex;
			}
		}
		returnAST = decoration_AST;
	}
	
	public final void genActuals_or_renaming() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST genActuals_or_renaming_AST = null;
		
		try {      // for error handling
			boolean synPredMatched57 = false;
			if (((LA(1)==OPENBRACKET) && (_tokenSet_28.member(LA(2))) && (_tokenSet_29.member(LA(3))))) {
				int _m57 = mark();
				synPredMatched57 = true;
				inputState.guessing++;
				try {
					{
					match(OPENBRACKET);
					decl_Name();
					match(SLASH);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched57 = false;
				}
				rewind(_m57);
				inputState.guessing--;
			}
			if ( synPredMatched57 ) {
				renaming();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				genActuals_or_renaming_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==OPENBRACKET) && (_tokenSet_30.member(LA(2))) && (_tokenSet_31.member(LA(3)))) {
				gen_Actuals();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				switch ( LA(1)) {
				case OPENBRACKET:
				{
					renaming();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case 26:
				case 28:
				case 30:
				case 32:
				case COMMA:
				case CLOSEBRACKET:
				case WORD:
				case 40:
				case 44:
				case IN_GEN:
				case SEMICOLON:
				case 48:
				case BACKSLASH_BACKSLASH:
				case AT:
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
				case OPENPAREN:
				case CLOSEPAREN:
				case 68:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case IN_REL:
				case 77:
				case 84:
				case 85:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 98:
				case 99:
				case POINT:
				case POST_FUN:
				case 103:
				case 104:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 109:
				case 110:
				case 111:
				case 112:
				case 113:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				case CLOSESET:
				case 121:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				genActuals_or_renaming_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		returnAST = genActuals_or_renaming_AST;
	}
	
	public final void renaming() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST renaming_AST = null;
		
		try {      // for error handling
			AST tmp144_AST = null;
			if (inputState.guessing==0) {
				tmp144_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp144_AST);
			}
			match(OPENBRACKET);
			decl_Name();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp145_AST = null;
			if (inputState.guessing==0) {
				tmp145_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp145_AST);
			}
			match(SLASH);
			decl_Name();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop157:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp146_AST = null;
					if (inputState.guessing==0) {
						tmp146_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp146_AST);
					}
					match(COMMA);
					decl_Name();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp147_AST = null;
					if (inputState.guessing==0) {
						tmp147_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp147_AST);
					}
					match(SLASH);
					decl_Name();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop157;
				}
				
			} while (true);
			}
			AST tmp148_AST = null;
			if (inputState.guessing==0) {
				tmp148_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp148_AST);
			}
			match(CLOSEBRACKET);
			renaming_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_26);
			} else {
			  throw ex;
			}
		}
		returnAST = renaming_AST;
	}
	
	public final void gen_Actuals() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST gen_Actuals_AST = null;
		
		try {      // for error handling
			AST tmp149_AST = null;
			if (inputState.guessing==0) {
				tmp149_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp149_AST);
			}
			match(OPENBRACKET);
			expression();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop61:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp150_AST = null;
					tmp150_AST = (AST)astFactory.create(LT(1));
					match(COMMA);
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop61;
				}
				
			} while (true);
			}
			AST tmp151_AST = null;
			if (inputState.guessing==0) {
				tmp151_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp151_AST);
			}
			match(CLOSEBRACKET);
			gen_Actuals_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = gen_Actuals_AST;
	}
	
	public final void declaration(
		String context
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declaration_AST = null;
		
		try {      // for error handling
			basic_Decl(context);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop66:
			do {
				if ((LA(1)==SEMICOLON)) {
					AST tmp152_AST = null;
					tmp152_AST = (AST)astFactory.create(LT(1));
					match(SEMICOLON);
					basic_Decl(context);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop66;
				}
				
			} while (true);
			}
			declaration_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = declaration_AST;
	}
	
	public final void predicate() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_AST = null;
		Token  exi = null;
		AST exi_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 50:
			{
				AST tmp153_AST = null;
				if (inputState.guessing==0) {
					tmp153_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp153_AST);
				}
				match(50);
				schema_Text("forall");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp154_AST = null;
				tmp154_AST = (AST)astFactory.create(LT(1));
				match(AT);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_AST = (AST)currentAST.root;
				break;
			}
			case 52:
			{
				exi = LT(1);
				if (inputState.guessing==0) {
					exi_AST = (AST)astFactory.create(exi);
				}
				match(52);
				if ( inputState.guessing==0 ) {
					
									  exi.setText(exi.getText()+ "L" +  exi.getLine()+"R"+ exi.getColumn());
									  //System.out.println("Token: " + exi.getText());
									  //addSchema(exi.getText());
									  //push(exi.getText()); 
								
				}
				schema_Text("exists");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp155_AST = null;
				tmp155_AST = (AST)astFactory.create(LT(1));
				match(AT);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					predicate_AST = (AST)currentAST.root;
					//pop(); getCurrent().removeSlot(exi.getText());
									 predicate_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(EXISTS_NUM,exi.getText())).add(predicate_AST));
								    
							     
								
					currentAST.root = predicate_AST;
					currentAST.child = predicate_AST!=null &&predicate_AST.getFirstChild()!=null ?
						predicate_AST.getFirstChild() : predicate_AST;
					currentAST.advanceChildToEnd();
				}
				predicate_AST = (AST)currentAST.root;
				break;
			}
			case 53:
			{
				AST tmp156_AST = null;
				if (inputState.guessing==0) {
					tmp156_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp156_AST);
				}
				match(53);
				schema_Text("exists_1");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp157_AST = null;
				tmp157_AST = (AST)astFactory.create(LT(1));
				match(AT);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_AST = (AST)currentAST.root;
				break;
			}
			case 75:
			{
				AST tmp158_AST = null;
				if (inputState.guessing==0) {
					tmp158_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp158_AST);
				}
				match(75);
				equalDecl();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				_loop72:
				do {
					if ((LA(1)==SEMICOLON)) {
						AST tmp159_AST = null;
						if (inputState.guessing==0) {
							tmp159_AST = (AST)astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp159_AST);
						}
						match(SEMICOLON);
						equalDecl();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
					}
					else {
						break _loop72;
					}
					
				} while (true);
				}
				AST tmp160_AST = null;
				tmp160_AST = (AST)astFactory.create(LT(1));
				match(AT);
				predicate();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_AST = (AST)currentAST.root;
				break;
			}
			case WORD:
			case PRE_GEN:
			case OPENPAREN:
			case 69:
			case 70:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case PRE_REL:
			case 79:
			case 80:
			case 83:
			case 94:
			case 95:
			case 96:
			case HYPHEN:
			case NUMBER:
			case 106:
			case 107:
			case 108:
			case 110:
			case 112:
			case 114:
			case 116:
			case 117:
			case OPENSET:
			{
				predicate_1();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = predicate_AST;
	}
	
	public final void basic_Decl(
		String context
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST basic_Decl_AST = null;
		AST exp_AST = null;
		AST sr_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case WORD:
			case PRE_GEN:
			case PRE_REL:
			case HYPHEN:
			case UNDERSCORE:
			{
				decl_Name();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				_loop163:
				do {
					if ((LA(1)==COMMA)) {
						AST tmp161_AST = null;
						tmp161_AST = (AST)astFactory.create(LT(1));
						match(COMMA);
						decl_Name();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
					}
					else {
						break _loop163;
					}
					
				} while (true);
				}
				AST tmp162_AST = null;
				if (inputState.guessing==0) {
					tmp162_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp162_AST);
				}
				match(COLON);
				expression();
				if (inputState.guessing==0) {
					exp_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					basic_Decl_AST = (AST)currentAST.root;
					//System.out.println("Declaration: " + #basic_Decl.getText() + "type: " + #exp.getText());
								      symbols.declaration(basic_Decl_AST,exp_AST,context);
				}
				basic_Decl_AST = (AST)currentAST.root;
				break;
			}
			case 71:
			case 72:
			case SCHEMA_NAME:
			{
				schema_Ref();
				if (inputState.guessing==0) {
					sr_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				basic_Decl_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		returnAST = basic_Decl_AST;
	}
	
	public final void axiom_Part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST axiom_Part_AST = null;
		
		try {      // for error handling
			predicate();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop69:
			do {
				if (((LA(1) >= SEMICOLON && LA(1) <= BACKSLASH_BACKSLASH))) {
					sep();
					predicate();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop69;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				axiom_Part_AST = (AST)currentAST.root;
				axiom_Part_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(AXIOM_PART,"AXIOM_PART")).add(axiom_Part_AST));
				currentAST.root = axiom_Part_AST;
				currentAST.child = axiom_Part_AST!=null &&axiom_Part_AST.getFirstChild()!=null ?
					axiom_Part_AST.getFirstChild() : axiom_Part_AST;
				currentAST.advanceChildToEnd();
			}
			axiom_Part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = axiom_Part_AST;
	}
	
	public final void equalDecl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST equalDecl_AST = null;
		
		try {      // for error handling
			var_Name();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp163_AST = null;
			if (inputState.guessing==0) {
				tmp163_AST = (AST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp163_AST);
			}
			match(36);
			expression();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			equalDecl_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		returnAST = equalDecl_AST;
	}
	
	public final void predicate_1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_1_AST = null;
		
		try {      // for error handling
			predicate_2();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case 54:
			{
				AST tmp164_AST = null;
				if (inputState.guessing==0) {
					tmp164_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp164_AST);
				}
				match(54);
				predicate_1();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case 28:
			case 30:
			case 32:
			case SEMICOLON:
			case 48:
			case BACKSLASH_BACKSLASH:
			case AT:
			case CLOSEPAREN:
			case 68:
			case 84:
			case 99:
			case CLOSESET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			predicate_1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = predicate_1_AST;
	}
	
	public final void predicate_2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_2_AST = null;
		
		try {      // for error handling
			predicate_U();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop78:
			do {
				if ((LA(1)==55||LA(1)==57||LA(1)==59)) {
					{
					switch ( LA(1)) {
					case 55:
					{
						AST tmp165_AST = null;
						if (inputState.guessing==0) {
							tmp165_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp165_AST);
						}
						match(55);
						break;
					}
					case 57:
					{
						AST tmp166_AST = null;
						if (inputState.guessing==0) {
							tmp166_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp166_AST);
						}
						match(57);
						break;
					}
					case 59:
					{
						AST tmp167_AST = null;
						if (inputState.guessing==0) {
							tmp167_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp167_AST);
						}
						match(59);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					predicate_U();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop78;
				}
				
			} while (true);
			}
			predicate_2_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		returnAST = predicate_2_AST;
	}
	
	public final void predicate_U() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST predicate_U_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PRE_REL:
			{
				AST tmp168_AST = null;
				if (inputState.guessing==0) {
					tmp168_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp168_AST);
				}
				match(PRE_REL);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_U_AST = (AST)currentAST.root;
				break;
			}
			case 70:
			{
				AST tmp169_AST = null;
				if (inputState.guessing==0) {
					tmp169_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp169_AST);
				}
				match(70);
				schema_Ref();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_U_AST = (AST)currentAST.root;
				break;
			}
			case 79:
			{
				AST tmp170_AST = null;
				if (inputState.guessing==0) {
					tmp170_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp170_AST);
				}
				match(79);
				predicate_U_AST = (AST)currentAST.root;
				break;
			}
			case 80:
			{
				AST tmp171_AST = null;
				if (inputState.guessing==0) {
					tmp171_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp171_AST);
				}
				match(80);
				predicate_U_AST = (AST)currentAST.root;
				break;
			}
			case 69:
			{
				AST tmp172_AST = null;
				if (inputState.guessing==0) {
					tmp172_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp172_AST);
				}
				match(69);
				predicate_U();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				predicate_U_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched81 = false;
				if (((_tokenSet_30.member(LA(1))) && (_tokenSet_38.member(LA(2))) && (_tokenSet_39.member(LA(3))))) {
					int _m81 = mark();
					synPredMatched81 = true;
					inputState.guessing++;
					try {
						{
						expression();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched81 = false;
					}
					rewind(_m81);
					inputState.guessing--;
				}
				if ( synPredMatched81 ) {
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					_loop84:
					do {
						if ((LA(1)==IN_REL||LA(1)==77)) {
							{
							switch ( LA(1)) {
							case IN_REL:
							{
								AST tmp173_AST = null;
								if (inputState.guessing==0) {
									tmp173_AST = (AST)astFactory.create(LT(1));
									astFactory.makeASTRoot(currentAST, tmp173_AST);
								}
								match(IN_REL);
								break;
							}
							case 77:
							{
								AST tmp174_AST = null;
								if (inputState.guessing==0) {
									tmp174_AST = (AST)astFactory.create(LT(1));
									astFactory.makeASTRoot(currentAST, tmp174_AST);
								}
								match(77);
								AST tmp175_AST = null;
								tmp175_AST = (AST)astFactory.create(LT(1));
								match(OPENBRACE);
								ident();
								if (inputState.guessing==0) {
									astFactory.addASTChild(currentAST, returnAST);
								}
								AST tmp176_AST = null;
								tmp176_AST = (AST)astFactory.create(LT(1));
								match(CLOSEBRACE);
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							expression();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
						}
						else {
							break _loop84;
						}
						
					} while (true);
					}
					predicate_U_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched86 = false;
					if (((LA(1)==OPENPAREN) && (_tokenSet_40.member(LA(2))) && (_tokenSet_41.member(LA(3))))) {
						int _m86 = mark();
						synPredMatched86 = true;
						inputState.guessing++;
						try {
							{
							match(OPENPAREN);
							predicate();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched86 = false;
						}
						rewind(_m86);
						inputState.guessing--;
					}
					if ( synPredMatched86 ) {
						AST tmp177_AST = null;
						tmp177_AST = (AST)astFactory.create(LT(1));
						match(OPENPAREN);
						predicate();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						AST tmp178_AST = null;
						tmp178_AST = (AST)astFactory.create(LT(1));
						match(CLOSEPAREN);
						predicate_U_AST = (AST)currentAST.root;
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					consume();
					consumeUntil(_tokenSet_42);
				} else {
				  throw ex;
				}
			}
			returnAST = predicate_U_AST;
		}
		
	public final void expression_0() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_0_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 81:
			{
				AST tmp179_AST = null;
				if (inputState.guessing==0) {
					tmp179_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp179_AST);
				}
				match(81);
				schema_Text("lambda");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp180_AST = null;
				tmp180_AST = (AST)astFactory.create(LT(1));
				match(AT);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_0_AST = (AST)currentAST.root;
				break;
			}
			case 82:
			{
				AST tmp181_AST = null;
				if (inputState.guessing==0) {
					tmp181_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp181_AST);
				}
				match(82);
				schema_Text("mu");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				switch ( LA(1)) {
				case AT:
				{
					AST tmp182_AST = null;
					tmp182_AST = (AST)astFactory.create(LT(1));
					match(AT);
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case CLOSEPAREN:
				case 99:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expression_0_AST = (AST)currentAST.root;
				break;
			}
			case 75:
			{
				AST tmp183_AST = null;
				if (inputState.guessing==0) {
					tmp183_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp183_AST);
				}
				match(75);
				equalDecl();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				_loop90:
				do {
					if ((LA(1)==SEMICOLON)) {
						AST tmp184_AST = null;
						if (inputState.guessing==0) {
							tmp184_AST = (AST)astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp184_AST);
						}
						match(SEMICOLON);
						equalDecl();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
					}
					else {
						break _loop90;
					}
					
				} while (true);
				}
				AST tmp185_AST = null;
				tmp185_AST = (AST)astFactory.create(LT(1));
				match(AT);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_0_AST = (AST)currentAST.root;
				break;
			}
			case WORD:
			case PRE_GEN:
			case OPENPAREN:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case 83:
			case 94:
			case 95:
			case 96:
			case HYPHEN:
			case NUMBER:
			case 106:
			case 107:
			case 108:
			case 110:
			case 112:
			case 114:
			case 116:
			case 117:
			case OPENSET:
			{
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_0_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_0_AST;
	}
	
	public final void expression_1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_1_AST = null;
		AST e1_AST = null;
		Token  i = null;
		AST i_AST = null;
		AST e2_AST = null;
		boolean ingen=false;
		
		try {      // for error handling
			expression_1A();
			if (inputState.guessing==0) {
				e1_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop94:
			do {
				if ((LA(1)==IN_GEN)) {
					i = LT(1);
					if (inputState.guessing==0) {
						i_AST = (AST)astFactory.create(i);
						astFactory.makeASTRoot(currentAST, i_AST);
					}
					match(IN_GEN);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_1A();
					if (inputState.guessing==0) {
						e2_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					if ( inputState.guessing==0 ) {
						if ( getCurrent().get(e1_AST.getText()) == null)
									       	       System.out.println("ERROR tipo de dominio de INGEN  desconocido:" + e1_AST.getText());
									       	   if ( getCurrent().get(e2_AST.getText()) == null)
									       	       System.out.println("ERROR tipo de dominio de INGEN   desconocido:" + e2_AST.getText());
											   ingen=true;
									
					}
				}
				else {
					break _loop94;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				expression_1_AST = (AST)currentAST.root;
				if (ingen) addType(expression_1_AST.toStringList().substring(1),new ZType(expression_1_AST.toStringList().substring(1),i_AST.getText()));
			}
			expression_1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_17);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_1_AST;
	}
	
	public final void expression_1A() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_1A_AST = null;
		AST e1_AST = null;
		AST e2_AST = null;
		boolean relation=false;
		
		try {      // for error handling
			expression_2();
			if (inputState.guessing==0) {
				e1_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop98:
			do {
				if ((LA(1)==86||LA(1)==87)) {
					{
					switch ( LA(1)) {
					case 86:
					{
						AST tmp186_AST = null;
						if (inputState.guessing==0) {
							tmp186_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp186_AST);
						}
						match(86);
						break;
					}
					case 87:
					{
						AST tmp187_AST = null;
						if (inputState.guessing==0) {
							tmp187_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp187_AST);
						}
						match(87);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					expression_2();
					if (inputState.guessing==0) {
						e2_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					if ( inputState.guessing==0 ) {
						if ( getCurrent().get(e1_AST.getText()) == null)
										System.out.println("ERROR tipo de dominio del producto cartesiano desconocido:" + e1_AST.getText());
									 if ( getCurrent().get(e2_AST.getText()) == null)
										System.out.println("ERROR tipo de dominio del producto cartesiano desconocido:" + e2_AST.getText());
									 relation=true;
									
					}
				}
				else {
					break _loop98;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				expression_1A_AST = (AST)currentAST.root;
				if (relation) addType(expression_1A_AST.toStringList().substring(1),new ZType(expression_1A_AST.toStringList().substring(1),"prod"));
			}
			expression_1A_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_1A_AST;
	}
	
	public final void expression_2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_AST = null;
		
		try {      // for error handling
			expression_2_prio1();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			expression_2_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_AST;
	}
	
	public final void expression_2_prio1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio1_AST = null;
		
		try {      // for error handling
			expression_2_prio2();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop102:
			do {
				if ((LA(1)==IN_FUN1)) {
					AST tmp188_AST = null;
					if (inputState.guessing==0) {
						tmp188_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp188_AST);
					}
					match(IN_FUN1);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2_prio2();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop102;
				}
				
			} while (true);
			}
			expression_2_prio1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio1_AST;
	}
	
	public final void expression_2_prio2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio2_AST = null;
		
		try {      // for error handling
			expression_2_prio3();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop105:
			do {
				if ((LA(1)==IN_FUN2)) {
					AST tmp189_AST = null;
					if (inputState.guessing==0) {
						tmp189_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp189_AST);
					}
					match(IN_FUN2);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2_prio3();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop105;
				}
				
			} while (true);
			}
			expression_2_prio2_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio2_AST;
	}
	
	public final void expression_2_prio3() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio3_AST = null;
		
		try {      // for error handling
			expression_2_prio4();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop108:
			do {
				if ((LA(1)==IN_FUN3)) {
					AST tmp190_AST = null;
					if (inputState.guessing==0) {
						tmp190_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp190_AST);
					}
					match(IN_FUN3);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2_prio4();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop108;
				}
				
			} while (true);
			}
			expression_2_prio3_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio3_AST;
	}
	
	public final void expression_2_prio4() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio4_AST = null;
		
		try {      // for error handling
			expression_2_prio5();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop111:
			do {
				if ((LA(1)==IN_FUN4)) {
					AST tmp191_AST = null;
					if (inputState.guessing==0) {
						tmp191_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp191_AST);
					}
					match(IN_FUN4);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2_prio5();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop111;
				}
				
			} while (true);
			}
			expression_2_prio4_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio4_AST;
	}
	
	public final void expression_2_prio5() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio5_AST = null;
		
		try {      // for error handling
			expression_2_prio6();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop114:
			do {
				if ((LA(1)==IN_FUN5)) {
					AST tmp192_AST = null;
					if (inputState.guessing==0) {
						tmp192_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp192_AST);
					}
					match(IN_FUN5);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2_prio6();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop114;
				}
				
			} while (true);
			}
			expression_2_prio5_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio5_AST;
	}
	
	public final void expression_2_prio6() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2_prio6_AST = null;
		
		try {      // for error handling
			expression_2A();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop117:
			do {
				if ((LA(1)==IN_FUN6)) {
					AST tmp193_AST = null;
					if (inputState.guessing==0) {
						tmp193_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp193_AST);
					}
					match(IN_FUN6);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2A();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop117;
				}
				
			} while (true);
			}
			expression_2_prio6_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_50);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2_prio6_AST;
	}
	
	public final void expression_2A() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_2A_AST = null;
		AST e1_AST = null;
		AST e1seq_AST = null;
		Token  pregen = null;
		AST pregen_AST = null;
		AST egen_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 94:
			case 95:
			{
				{
				switch ( LA(1)) {
				case 94:
				{
					AST tmp194_AST = null;
					if (inputState.guessing==0) {
						tmp194_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp194_AST);
					}
					match(94);
					break;
				}
				case 95:
				{
					AST tmp195_AST = null;
					if (inputState.guessing==0) {
						tmp195_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp195_AST);
					}
					match(95);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expression_4();
				if (inputState.guessing==0) {
					e1_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					expression_2A_AST = (AST)currentAST.root;
					String tName= e1_AST.toStringList().substring(1); //System.out.println("]"+tName+"[");
									if (isType(tName)) {addType(expression_2A_AST.toStringList().substring(1),new ZTypeComposedOf(expression_2A_AST.toStringList().substring(1),"pset",getType(tName))); }
									else System.out.println("ERROR en pset de:" + e1_AST.getText());
									
				}
				expression_2A_AST = (AST)currentAST.root;
				break;
			}
			case 96:
			{
				{
				AST tmp196_AST = null;
				if (inputState.guessing==0) {
					tmp196_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp196_AST);
				}
				match(96);
				}
				expression_4();
				if (inputState.guessing==0) {
					e1seq_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					expression_2A_AST = (AST)currentAST.root;
					String tName= e1seq_AST.toStringList().substring(1); //System.out.println("]"+tName+"[");
									if (isType(tName)) {addType(expression_2A_AST.toStringList().substring(1),new ZTypeComposedOf(expression_2A_AST.toStringList().substring(1),"seq",getType(tName))); }
									else System.out.println("ERROR en seq de:" + e1seq_AST.getText());
									
				}
				expression_2A_AST = (AST)currentAST.root;
				break;
			}
			case PRE_GEN:
			{
				pregen = LT(1);
				if (inputState.guessing==0) {
					pregen_AST = (AST)astFactory.create(pregen);
					astFactory.makeASTRoot(currentAST, pregen_AST);
				}
				match(PRE_GEN);
				decoration();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4();
				if (inputState.guessing==0) {
					egen_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					expression_2A_AST = (AST)currentAST.root;
					
									    String varName= egen_AST.toStringList().substring(1); //System.out.println("PREGEN: IN: " +#pregen.getText());
									    ZType type=getTypeOfVar(varName);
									    if (type != null) {
								        addType(expression_2A_AST.toStringList().substring(1),new ZTypeComposedOf(expression_2A_AST.toStringList().substring(1),pregen_AST.getText(),type)); }
									else System.out.println("ERROR de PREGEN:");
				}
				expression_2A_AST = (AST)currentAST.root;
				break;
			}
			case HYPHEN:
			{
				AST tmp197_AST = null;
				if (inputState.guessing==0) {
					tmp197_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp197_AST);
				}
				match(HYPHEN);
				decoration();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_2A_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched122 = false;
				if (((_tokenSet_51.member(LA(1))) && (_tokenSet_52.member(LA(2))) && (_tokenSet_53.member(LA(3))))) {
					int _m122 = mark();
					synPredMatched122 = true;
					inputState.guessing++;
					try {
						{
						expression_4();
						match(98);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched122 = false;
					}
					rewind(_m122);
					inputState.guessing--;
				}
				if ( synPredMatched122 ) {
					expression_4();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp198_AST = null;
					if (inputState.guessing==0) {
						tmp198_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp198_AST);
					}
					match(98);
					expression_0();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp199_AST = null;
					tmp199_AST = (AST)astFactory.create(LT(1));
					match(99);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2A_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_51.member(LA(1))) && (_tokenSet_54.member(LA(2))) && (_tokenSet_39.member(LA(3)))) {
					expression_3();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					expression_2A_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_2A_AST;
	}
	
	public final void expression_4() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_4_AST = null;
		AST exp_AST = null;
		Token  p = null;
		AST p_AST = null;
		
		try {      // for error handling
			expression_4A();
			if (inputState.guessing==0) {
				exp_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case POINT:
			{
				{
				int _cnt135=0;
				_loop135:
				do {
					if ((LA(1)==POINT)) {
						AST tmp200_AST = null;
						if (inputState.guessing==0) {
							tmp200_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp200_AST);
						}
						match(POINT);
						{
						boolean synPredMatched132 = false;
						if (((LA(1)==WORD||LA(1)==SCHEMA_NAME||LA(1)==101) && (_tokenSet_56.member(LA(2))) && (_tokenSet_57.member(LA(3))))) {
							int _m132 = mark();
							synPredMatched132 = true;
							inputState.guessing++;
							try {
								{
								{
								switch ( LA(1)) {
								case 101:
								{
									match(101);
									break;
								}
								case WORD:
								case SCHEMA_NAME:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
								}
								var_Name_4();
								match(OPENPAREN);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched132 = false;
							}
							rewind(_m132);
							inputState.guessing--;
						}
						if ( synPredMatched132 ) {
							{
							switch ( LA(1)) {
							case 101:
							{
								p = LT(1);
								if (inputState.guessing==0) {
									p_AST = (AST)astFactory.create(p);
								}
								match(101);
								break;
							}
							case WORD:
							case SCHEMA_NAME:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							var_Name_4();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							AST tmp201_AST = null;
							tmp201_AST = (AST)astFactory.create(LT(1));
							match(OPENPAREN);
							{
							switch ( LA(1)) {
							case WORD:
							case PRE_GEN:
							case OPENPAREN:
							case 71:
							case 72:
							case SCHEMA_NAME:
							case 83:
							case 94:
							case 95:
							case 96:
							case HYPHEN:
							case NUMBER:
							case 106:
							case 107:
							case 108:
							case 110:
							case 112:
							case 114:
							case 116:
							case 117:
							case OPENSET:
							{
								expressions();
								if (inputState.guessing==0) {
									astFactory.addASTChild(currentAST, returnAST);
								}
								break;
							}
							case CLOSEPAREN:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
							}
							AST tmp202_AST = null;
							tmp202_AST = (AST)astFactory.create(LT(1));
							match(CLOSEPAREN);
							if ( inputState.guessing==0 ) {
								expression_4_AST = (AST)currentAST.root;
								
								//System.out.println("MM[]: " +  #expression_4.toStringTree() );
								if (p ==null) expression_4_AST   = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(METHODCALL,"METHODCALL")).add(expression_4_AST));
								else expression_4_AST   = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(METHODCALL,"METHODCALLASYNC")).add(expression_4_AST));  
								//System.out.println("--------POINTRAYA: " + #p);
								//System.out.println("MM[]: " +  #expression_4.toStringTree() );
								
								currentAST.root = expression_4_AST;
								currentAST.child = expression_4_AST!=null &&expression_4_AST.getFirstChild()!=null ?
									expression_4_AST.getFirstChild() : expression_4_AST;
								currentAST.advanceChildToEnd();
							}
						}
						else if ((LA(1)==WORD||LA(1)==SCHEMA_NAME) && (_tokenSet_58.member(LA(2))) && (_tokenSet_59.member(LA(3)))) {
							var_Name_4();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							if ( inputState.guessing==0 ) {
								
								//System.out.println("MM[]: " +  #expression_4.toStringTree() ); 
								//#expression_4   = #([POINT,"POINT"],#expression_4 ); System.out.println("MM[]: " +  #expression_4.toStringTree() );
								
							}
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
					}
					else {
						if ( _cnt135>=1 ) { break _loop135; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt135++;
				} while (true);
				}
				break;
			}
			case POST_FUN:
			{
				AST tmp203_AST = null;
				if (inputState.guessing==0) {
					tmp203_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp203_AST);
				}
				match(POST_FUN);
				break;
			}
			case 103:
			{
				AST tmp204_AST = null;
				if (inputState.guessing==0) {
					tmp204_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp204_AST);
				}
				match(103);
				expression();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp205_AST = null;
				tmp205_AST = (AST)astFactory.create(LT(1));
				match(104);
				break;
			}
			case 26:
			case 28:
			case 30:
			case 32:
			case COMMA:
			case CLOSEBRACKET:
			case WORD:
			case 40:
			case 44:
			case IN_GEN:
			case SEMICOLON:
			case 48:
			case BACKSLASH_BACKSLASH:
			case AT:
			case 54:
			case 55:
			case 57:
			case 59:
			case OPENPAREN:
			case CLOSEPAREN:
			case 68:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case IN_REL:
			case 77:
			case 84:
			case 85:
			case 86:
			case 87:
			case IN_FUN1:
			case IN_FUN2:
			case IN_FUN3:
			case IN_FUN4:
			case IN_FUN5:
			case IN_FUN6:
			case 98:
			case 99:
			case 104:
			case NUMBER:
			case 106:
			case 107:
			case 108:
			case 109:
			case 110:
			case 111:
			case 112:
			case 113:
			case 114:
			case 116:
			case 117:
			case OPENSET:
			case CLOSESET:
			case 121:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			expression_4_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_4_AST;
	}
	
	public final void expression_3() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_3_AST = null;
		
		try {      // for error handling
			{
			int _cnt125=0;
			_loop125:
			do {
				if ((_tokenSet_51.member(LA(1)))) {
					expression_4();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					if ( _cnt125>=1 ) { break _loop125; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt125++;
			} while (true);
			}
			expression_3_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_3_AST;
	}
	
	public final void expression_4A() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_4A_AST = null;
		AST v_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMBER:
			{
				AST tmp206_AST = null;
				if (inputState.guessing==0) {
					tmp206_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp206_AST);
				}
				match(NUMBER);
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case 106:
			{
				AST tmp207_AST = null;
				if (inputState.guessing==0) {
					tmp207_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp207_AST);
				}
				match(106);
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case 107:
			{
				AST tmp208_AST = null;
				if (inputState.guessing==0) {
					tmp208_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp208_AST);
				}
				match(107);
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case OPENSET:
			{
				set_Exp();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case 108:
			{
				AST tmp209_AST = null;
				if (inputState.guessing==0) {
					tmp209_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp209_AST);
				}
				match(108);
				{
				switch ( LA(1)) {
				case WORD:
				case PRE_GEN:
				case OPENPAREN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case 83:
				case 94:
				case 95:
				case 96:
				case HYPHEN:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				{
					expressions();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case 109:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp210_AST = null;
				tmp210_AST = (AST)astFactory.create(LT(1));
				match(109);
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case 110:
			{
				AST tmp211_AST = null;
				if (inputState.guessing==0) {
					tmp211_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp211_AST);
				}
				match(110);
				{
				switch ( LA(1)) {
				case WORD:
				case PRE_GEN:
				case OPENPAREN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case 83:
				case 94:
				case 95:
				case 96:
				case HYPHEN:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				{
					expressions();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case 111:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp212_AST = null;
				tmp212_AST = (AST)astFactory.create(LT(1));
				match(111);
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			case OPENPAREN:
			case 71:
			case 72:
			case SCHEMA_NAME:
			case 112:
			case 114:
			case 116:
			case 117:
			{
				expression_4B();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4A_AST = (AST)currentAST.root;
				break;
			}
			default:
				if ((LA(1)==WORD) && (LA(2)==OPENPAREN||LA(2)==STROKE) && (_tokenSet_61.member(LA(3)))) {
					var_Name();
					if (inputState.guessing==0) {
						v_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp213_AST = null;
					tmp213_AST = (AST)astFactory.create(LT(1));
					match(OPENPAREN);
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp214_AST = null;
					tmp214_AST = (AST)astFactory.create(LT(1));
					match(CLOSEPAREN);
					if ( inputState.guessing==0 ) {
						expression_4A_AST = (AST)currentAST.root;
						expression_4A_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(APPLICATION,"APPLICATION")).add(expression_4A_AST));
						currentAST.root = expression_4A_AST;
						currentAST.child = expression_4A_AST!=null &&expression_4A_AST.getFirstChild()!=null ?
							expression_4A_AST.getFirstChild() : expression_4A_AST;
						currentAST.advanceChildToEnd();
					}
					expression_4A_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==WORD) && (_tokenSet_62.member(LA(2))) && (_tokenSet_59.member(LA(3)))) {
					var_Name();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					switch ( LA(1)) {
					case OPENBRACKET:
					{
						gen_Actuals();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						break;
					}
					case 26:
					case 28:
					case 30:
					case 32:
					case COMMA:
					case CLOSEBRACKET:
					case WORD:
					case 40:
					case 44:
					case IN_GEN:
					case SEMICOLON:
					case 48:
					case BACKSLASH_BACKSLASH:
					case AT:
					case 54:
					case 55:
					case 57:
					case 59:
					case OPENPAREN:
					case CLOSEPAREN:
					case 68:
					case 71:
					case 72:
					case SCHEMA_NAME:
					case IN_REL:
					case 77:
					case 84:
					case 85:
					case 86:
					case 87:
					case IN_FUN1:
					case IN_FUN2:
					case IN_FUN3:
					case IN_FUN4:
					case IN_FUN5:
					case IN_FUN6:
					case 98:
					case 99:
					case POINT:
					case POST_FUN:
					case 103:
					case 104:
					case NUMBER:
					case 106:
					case 107:
					case 108:
					case 109:
					case 110:
					case 111:
					case 112:
					case 113:
					case 114:
					case 116:
					case 117:
					case OPENSET:
					case CLOSESET:
					case 121:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					expression_4A_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_4A_AST;
	}
	
	public final void var_Name_4() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST var_Name_4_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case WORD:
			{
				var_Name();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				var_Name_4_AST = (AST)currentAST.root;
				break;
			}
			case SCHEMA_NAME:
			{
				AST tmp215_AST = null;
				if (inputState.guessing==0) {
					tmp215_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp215_AST);
				}
				match(SCHEMA_NAME);
				var_Name_4_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_64);
			} else {
			  throw ex;
			}
		}
		returnAST = var_Name_4_AST;
	}
	
	public final void expressions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expressions_AST = null;
		
		try {      // for error handling
			expression();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop154:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp216_AST = null;
					tmp216_AST = (AST)astFactory.create(LT(1));
					match(COMMA);
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop154;
				}
				
			} while (true);
			}
			expressions_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_65);
			} else {
			  throw ex;
			}
		}
		returnAST = expressions_AST;
	}
	
	public final void var_Name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST var_Name_AST = null;
		AST i_AST = null;
		
		try {      // for error handling
			ident();
			if (inputState.guessing==0) {
				i_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				if (symbols.get(i_AST.getText()) == null) System.out.println("ERROR: Id \"" + i_AST.getText() +"\" no declarado");
							
			}
			var_Name_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_66);
			} else {
			  throw ex;
			}
		}
		returnAST = var_Name_AST;
	}
	
	public final void set_Exp() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST set_Exp_AST = null;
		
		try {      // for error handling
			boolean synPredMatched149 = false;
			if (((LA(1)==OPENSET) && (_tokenSet_67.member(LA(2))) && (_tokenSet_68.member(LA(3))))) {
				int _m149 = mark();
				synPredMatched149 = true;
				inputState.guessing++;
				try {
					{
					match(OPENSET);
					schema_Text("set");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched149 = false;
				}
				rewind(_m149);
				inputState.guessing--;
			}
			if ( synPredMatched149 ) {
				if ( inputState.guessing==0 ) {
					addSchema("set"); push("set");
				}
				AST tmp217_AST = null;
				tmp217_AST = (AST)astFactory.create(LT(1));
				match(OPENSET);
				schema_Text("set");
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				switch ( LA(1)) {
				case AT:
				{
					AST tmp218_AST = null;
					tmp218_AST = (AST)astFactory.create(LT(1));
					match(AT);
					expression();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case CLOSESET:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp219_AST = null;
				tmp219_AST = (AST)astFactory.create(LT(1));
				match(CLOSESET);
				if ( inputState.guessing==0 ) {
					set_Exp_AST = (AST)currentAST.root;
					set_Exp_AST= (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(SETSSCH,"SETSSCH")).add(set_Exp_AST));
					currentAST.root = set_Exp_AST;
					currentAST.child = set_Exp_AST!=null &&set_Exp_AST.getFirstChild()!=null ?
						set_Exp_AST.getFirstChild() : set_Exp_AST;
					currentAST.advanceChildToEnd();
				}
				if ( inputState.guessing==0 ) {
					pop();
				}
				set_Exp_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==OPENSET) && (_tokenSet_69.member(LA(2))) && (_tokenSet_70.member(LA(3)))) {
				AST tmp220_AST = null;
				tmp220_AST = (AST)astFactory.create(LT(1));
				match(OPENSET);
				{
				switch ( LA(1)) {
				case WORD:
				case PRE_GEN:
				case OPENPAREN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case 83:
				case 94:
				case 95:
				case 96:
				case HYPHEN:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				{
					expressions();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case CLOSESET:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp221_AST = null;
				tmp221_AST = (AST)astFactory.create(LT(1));
				match(CLOSESET);
				if ( inputState.guessing==0 ) {
					set_Exp_AST = (AST)currentAST.root;
					set_Exp_AST= (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(SETSEXP,"SETSEXP")).add(set_Exp_AST));
					currentAST.root = set_Exp_AST;
					currentAST.child = set_Exp_AST!=null &&set_Exp_AST.getFirstChild()!=null ?
						set_Exp_AST.getFirstChild() : set_Exp_AST;
					currentAST.advanceChildToEnd();
				}
				set_Exp_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		returnAST = set_Exp_AST;
	}
	
	public final void expression_4B() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_4B_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 112:
			{
				AST tmp222_AST = null;
				if (inputState.guessing==0) {
					tmp222_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp222_AST);
				}
				match(112);
				{
				switch ( LA(1)) {
				case WORD:
				case PRE_GEN:
				case OPENPAREN:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case 83:
				case 94:
				case 95:
				case 96:
				case HYPHEN:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 110:
				case 112:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				{
					expressions();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case 113:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp223_AST = null;
				tmp223_AST = (AST)astFactory.create(LT(1));
				match(113);
				expression_4B_AST = (AST)currentAST.root;
				break;
			}
			case 114:
			{
				AST tmp224_AST = null;
				if (inputState.guessing==0) {
					tmp224_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp224_AST);
				}
				match(114);
				AST tmp225_AST = null;
				if (inputState.guessing==0) {
					tmp225_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp225_AST);
				}
				match(SCHEMA_NAME);
				decoration();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				{
				switch ( LA(1)) {
				case Renaming:
				{
					AST tmp226_AST = null;
					if (inputState.guessing==0) {
						tmp226_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp226_AST);
					}
					match(Renaming);
					break;
				}
				case 26:
				case 28:
				case 30:
				case 32:
				case COMMA:
				case CLOSEBRACKET:
				case WORD:
				case 40:
				case 44:
				case IN_GEN:
				case SEMICOLON:
				case 48:
				case BACKSLASH_BACKSLASH:
				case AT:
				case 54:
				case 55:
				case 57:
				case 59:
				case OPENPAREN:
				case CLOSEPAREN:
				case 68:
				case 71:
				case 72:
				case SCHEMA_NAME:
				case IN_REL:
				case 77:
				case 84:
				case 85:
				case 86:
				case 87:
				case IN_FUN1:
				case IN_FUN2:
				case IN_FUN3:
				case IN_FUN4:
				case IN_FUN5:
				case IN_FUN6:
				case 98:
				case 99:
				case POINT:
				case POST_FUN:
				case 103:
				case 104:
				case NUMBER:
				case 106:
				case 107:
				case 108:
				case 109:
				case 110:
				case 111:
				case 112:
				case 113:
				case 114:
				case 116:
				case 117:
				case OPENSET:
				case CLOSESET:
				case 121:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expression_4B_AST = (AST)currentAST.root;
				break;
			}
			case 71:
			case 72:
			case SCHEMA_NAME:
			{
				schema_Ref();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4B_AST = (AST)currentAST.root;
				break;
			}
			case 116:
			case 117:
			{
				builtIn();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				expression_4B_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched143 = false;
				if (((LA(1)==OPENPAREN) && (_tokenSet_30.member(LA(2))) && (_tokenSet_71.member(LA(3))))) {
					int _m143 = mark();
					synPredMatched143 = true;
					inputState.guessing++;
					try {
						{
						match(OPENPAREN);
						expression();
						match(COMMA);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched143 = false;
					}
					rewind(_m143);
					inputState.guessing--;
				}
				if ( synPredMatched143 ) {
					AST tmp227_AST = null;
					tmp227_AST = (AST)astFactory.create(LT(1));
					match(OPENPAREN);
					expressions();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp228_AST = null;
					tmp228_AST = (AST)astFactory.create(LT(1));
					match(CLOSEPAREN);
					if ( inputState.guessing==0 ) {
						expression_4B_AST = (AST)currentAST.root;
						expression_4B_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(TUPLE,"TUPLE")).add(expression_4B_AST));
						currentAST.root = expression_4B_AST;
						currentAST.child = expression_4B_AST!=null &&expression_4B_AST.getFirstChild()!=null ?
							expression_4B_AST.getFirstChild() : expression_4B_AST;
						currentAST.advanceChildToEnd();
					}
					expression_4B_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==OPENPAREN) && (_tokenSet_72.member(LA(2))) && (_tokenSet_73.member(LA(3)))) {
					AST tmp229_AST = null;
					tmp229_AST = (AST)astFactory.create(LT(1));
					match(OPENPAREN);
					expression_0();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					AST tmp230_AST = null;
					tmp230_AST = (AST)astFactory.create(LT(1));
					match(CLOSEPAREN);
					expression_4B_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_4B_AST;
	}
	
	public final void builtIn() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST builtIn_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 116:
			{
				AST tmp231_AST = null;
				if (inputState.guessing==0) {
					tmp231_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp231_AST);
				}
				match(116);
				builtIn_AST = (AST)currentAST.root;
				break;
			}
			case 117:
			{
				AST tmp232_AST = null;
				if (inputState.guessing==0) {
					tmp232_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp232_AST);
				}
				match(117);
				builtIn_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		returnAST = builtIn_AST;
	}
	
	public final void decl_Part() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decl_Part_AST = null;
		
		try {      // for error handling
			basic_Decl("");
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			_loop160:
			do {
				if (((LA(1) >= SEMICOLON && LA(1) <= BACKSLASH_BACKSLASH))) {
					sep();
					basic_Decl("");
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop160;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				decl_Part_AST = (AST)currentAST.root;
				decl_Part_AST= (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(DECL_PART,"DECL_PART")).add(decl_Part_AST));
				currentAST.root = decl_Part_AST;
				currentAST.child = decl_Part_AST!=null &&decl_Part_AST.getFirstChild()!=null ?
					decl_Part_AST.getFirstChild() : decl_Part_AST;
				currentAST.advanceChildToEnd();
			}
			decl_Part_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_74);
			} else {
			  throw ex;
			}
		}
		returnAST = decl_Part_AST;
	}
	
	public final void op_Name() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST op_Name_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PRE_GEN:
			case PRE_REL:
			{
				{
				switch ( LA(1)) {
				case PRE_GEN:
				{
					AST tmp233_AST = null;
					if (inputState.guessing==0) {
						tmp233_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp233_AST);
					}
					match(PRE_GEN);
					break;
				}
				case PRE_REL:
				{
					AST tmp234_AST = null;
					if (inputState.guessing==0) {
						tmp234_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp234_AST);
					}
					match(PRE_REL);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp235_AST = null;
				if (inputState.guessing==0) {
					tmp235_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp235_AST);
				}
				match(UNDERSCORE);
				op_Name_AST = (AST)currentAST.root;
				break;
			}
			case HYPHEN:
			{
				AST tmp236_AST = null;
				if (inputState.guessing==0) {
					tmp236_AST = (AST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp236_AST);
				}
				match(HYPHEN);
				decoration();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				op_Name_AST = (AST)currentAST.root;
				break;
			}
			default:
				if ((LA(1)==UNDERSCORE) && (LA(2)==IN_GEN||LA(2)==IN_REL||LA(2)==IN_FUN)) {
					AST tmp237_AST = null;
					if (inputState.guessing==0) {
						tmp237_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp237_AST);
					}
					match(UNDERSCORE);
					{
					switch ( LA(1)) {
					case IN_FUN:
					{
						AST tmp238_AST = null;
						if (inputState.guessing==0) {
							tmp238_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp238_AST);
						}
						match(IN_FUN);
						break;
					}
					case IN_GEN:
					{
						AST tmp239_AST = null;
						if (inputState.guessing==0) {
							tmp239_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp239_AST);
						}
						match(IN_GEN);
						break;
					}
					case IN_REL:
					{
						AST tmp240_AST = null;
						if (inputState.guessing==0) {
							tmp240_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp240_AST);
						}
						match(IN_REL);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp241_AST = null;
					if (inputState.guessing==0) {
						tmp241_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp241_AST);
					}
					match(UNDERSCORE);
					op_Name_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==UNDERSCORE) && (LA(2)==POST_FUN)) {
					AST tmp242_AST = null;
					if (inputState.guessing==0) {
						tmp242_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp242_AST);
					}
					match(UNDERSCORE);
					AST tmp243_AST = null;
					if (inputState.guessing==0) {
						tmp243_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp243_AST);
					}
					match(POST_FUN);
					op_Name_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==UNDERSCORE) && (LA(2)==98)) {
					AST tmp244_AST = null;
					if (inputState.guessing==0) {
						tmp244_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp244_AST);
					}
					match(UNDERSCORE);
					AST tmp245_AST = null;
					if (inputState.guessing==0) {
						tmp245_AST = (AST)astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp245_AST);
					}
					match(98);
					AST tmp246_AST = null;
					if (inputState.guessing==0) {
						tmp246_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp246_AST);
					}
					match(UNDERSCORE);
					AST tmp247_AST = null;
					if (inputState.guessing==0) {
						tmp247_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp247_AST);
					}
					match(99);
					decoration();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					op_Name_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		returnAST = op_Name_AST;
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
	
	private static final long _tokenSet_0_data_[] = { 2852126720L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 2L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { 2852126722L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { 67108864L, 0L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	private static final long _tokenSet_4_data_[] = { 40063454937088L, 576460760893375360L, 0L, 0L };
	public static final BitSet _tokenSet_4 = new BitSet(_tokenSet_4_data_);
	private static final long _tokenSet_5_data_[] = { 5637144576L, 0L, 0L };
	public static final BitSet _tokenSet_5 = new BitSet(_tokenSet_5_data_);
	private static final long _tokenSet_6_data_[] = { 4294967296L, 0L, 0L };
	public static final BitSet _tokenSet_6 = new BitSet(_tokenSet_6_data_);
	private static final long _tokenSet_7_data_[] = { 70583492542464L, 288230376151711744L, 0L, 0L };
	public static final BitSet _tokenSet_7 = new BitSet(_tokenSet_7_data_);
	private static final long _tokenSet_8_data_[] = { 105767864631296L, 321266318626521986L, 0L, 0L };
	public static final BitSet _tokenSet_8 = new BitSet(_tokenSet_8_data_);
	private static final long _tokenSet_9_data_[] = { 14636836227907584L, 1002L, 0L, 0L };
	public static final BitSet _tokenSet_9 = new BitSet(_tokenSet_9_data_);
	private static final long _tokenSet_10_data_[] = { 986261930115072L, 0L, 0L };
	public static final BitSet _tokenSet_10 = new BitSet(_tokenSet_10_data_);
	private static final long _tokenSet_11_data_[] = { 995195529199616L, 288230376151711744L, 0L, 0L };
	public static final BitSet _tokenSet_11 = new BitSet(_tokenSet_11_data_);
	private static final long _tokenSet_12_data_[] = { 985162485596160L, 0L, 0L };
	public static final BitSet _tokenSet_12 = new BitSet(_tokenSet_12_data_);
	private static final long _tokenSet_13_data_[] = { 14673128701558784L, 609496694778350562L, 0L, 0L };
	public static final BitSet _tokenSet_13 = new BitSet(_tokenSet_13_data_);
	private static final long _tokenSet_14_data_[] = { 777959174469320704L, 285978422791911318L, 0L, 0L };
	public static final BitSet _tokenSet_14 = new BitSet(_tokenSet_14_data_);
	private static final long _tokenSet_15_data_[] = { 35399120453632L, 321266318626521986L, 0L, 0L };
	public static final BitSet _tokenSet_15 = new BitSet(_tokenSet_15_data_);
	private static final long _tokenSet_16_data_[] = { 68719476736L, 0L, 0L };
	public static final BitSet _tokenSet_16 = new BitSet(_tokenSet_16_data_);
	private static final long _tokenSet_17_data_[] = { 777874847081431040L, 180883990783209492L, 0L, 0L };
	public static final BitSet _tokenSet_17 = new BitSet(_tokenSet_17_data_);
	private static final long _tokenSet_18_data_[] = { 985162485596160L, 4L, 0L, 0L };
	public static final BitSet _tokenSet_18 = new BitSet(_tokenSet_18_data_);
	private static final long _tokenSet_19_data_[] = { 986261997223936L, 0L, 0L };
	public static final BitSet _tokenSet_19 = new BitSet(_tokenSet_19_data_);
	private static final long _tokenSet_20_data_[] = { 77309411328L, 0L, 0L };
	public static final BitSet _tokenSet_20 = new BitSet(_tokenSet_20_data_);
	private static final long _tokenSet_21_data_[] = { 2251799813685248L, 36028831378702356L, 0L, 0L };
	public static final BitSet _tokenSet_21 = new BitSet(_tokenSet_21_data_);
	private static final long _tokenSet_22_data_[] = { 18999560995078144L, 4L, 0L, 0L };
	public static final BitSet _tokenSet_22 = new BitSet(_tokenSet_22_data_);
	private static final long _tokenSet_23_data_[] = { -17029236023885824L, 4L, 0L, 0L };
	public static final BitSet _tokenSet_23 = new BitSet(_tokenSet_23_data_);
	private static final long _tokenSet_24_data_[] = { -17029236023885824L, 5L, 0L, 0L };
	public static final BitSet _tokenSet_24 = new BitSet(_tokenSet_24_data_);
	private static final long _tokenSet_25_data_[] = { 51539607552L, 72057594037928964L, 0L, 0L };
	public static final BitSet _tokenSet_25 = new BitSet(_tokenSet_25_data_);
	private static final long _tokenSet_26_data_[] = { -14688181152645120L, 213920828753982359L, 0L, 0L };
	public static final BitSet _tokenSet_26 = new BitSet(_tokenSet_26_data_);
	private static final long _tokenSet_27_data_[] = { -14639175575797760L, 288230238711723927L, 0L, 0L };
	public static final BitSet _tokenSet_27 = new BitSet(_tokenSet_27_data_);
	private static final long _tokenSet_28_data_[] = { 35321811042304L, 576460760893374464L, 0L, 0L };
	public static final BitSet _tokenSet_28 = new BitSet(_tokenSet_28_data_);
	private static final long _tokenSet_29_data_[] = { 70368744177664L, 2017612925119763456L, 0L, 0L };
	public static final BitSet _tokenSet_29 = new BitSet(_tokenSet_29_data_);
	private static final long _tokenSet_30_data_[] = { 35321811042304L, 33035942474810242L, 0L, 0L };
	public static final BitSet _tokenSet_30 = new BitSet(_tokenSet_30_data_);
	private static final long _tokenSet_31_data_[] = { 14742449473716224L, 934495651365899234L, 0L, 0L };
	public static final BitSet _tokenSet_31 = new BitSet(_tokenSet_31_data_);
	private static final long _tokenSet_32_data_[] = { -14688172562710528L, 213920828753982359L, 0L, 0L };
	public static final BitSet _tokenSet_32 = new BitSet(_tokenSet_32_data_);
	private static final long _tokenSet_33_data_[] = { 2252899325313024L, 36028831378702356L, 0L, 0L };
	public static final BitSet _tokenSet_33 = new BitSet(_tokenSet_33_data_);
	private static final long _tokenSet_34_data_[] = { 3236967869317120L, 36028831379750932L, 0L, 0L };
	public static final BitSet _tokenSet_34 = new BitSet(_tokenSet_34_data_);
	private static final long _tokenSet_35_data_[] = { 3238067380944896L, 180144019454558228L, 0L, 0L };
	public static final BitSet _tokenSet_35 = new BitSet(_tokenSet_35_data_);
	private static final long _tokenSet_36_data_[] = { 2392537302040576L, 0L, 0L };
	public static final BitSet _tokenSet_36 = new BitSet(_tokenSet_36_data_);
	private static final long _tokenSet_37_data_[] = { 21251366378799104L, 36028831379750932L, 0L, 0L };
	public static final BitSet _tokenSet_37 = new BitSet(_tokenSet_37_data_);
	private static final long _tokenSet_38_data_[] = { 792598501711151104L, 934495685726698486L, 0L, 0L };
	public static final BitSet _tokenSet_38 = new BitSet(_tokenSet_38_data_);
	private static final long _tokenSet_39_data_[] = { -14087526285310L, 2305843009213692927L, 0L, 0L };
	public static final BitSet _tokenSet_39 = new BitSet(_tokenSet_39_data_);
	private static final long _tokenSet_40_data_[] = { 14672020599996416L, 33035942474927074L, 0L, 0L };
	public static final BitSet _tokenSet_40 = new BitSet(_tokenSet_40_data_);
	private static final long _tokenSet_41_data_[] = { 789361533841833984L, 934495651365911526L, 0L, 0L };
	public static final BitSet _tokenSet_41 = new BitSet(_tokenSet_41_data_);
	private static final long _tokenSet_42_data_[] = { 777856103777042432L, 36028831379750932L, 0L, 0L };
	public static final BitSet _tokenSet_42 = new BitSet(_tokenSet_42_data_);
	private static final long _tokenSet_43_data_[] = { 0L, 34359738372L, 0L, 0L };
	public static final BitSet _tokenSet_43 = new BitSet(_tokenSet_43_data_);
	private static final long _tokenSet_44_data_[] = { 777945215825608704L, 180883990783209492L, 0L, 0L };
	public static final BitSet _tokenSet_44 = new BitSet(_tokenSet_44_data_);
	private static final long _tokenSet_45_data_[] = { 777945215825608704L, 180883990795792404L, 0L, 0L };
	public static final BitSet _tokenSet_45 = new BitSet(_tokenSet_45_data_);
	private static final long _tokenSet_46_data_[] = { 777945215825608704L, 180883990812569620L, 0L, 0L };
	public static final BitSet _tokenSet_46 = new BitSet(_tokenSet_46_data_);
	private static final long _tokenSet_47_data_[] = { 777945215825608704L, 180883990846124052L, 0L, 0L };
	public static final BitSet _tokenSet_47 = new BitSet(_tokenSet_47_data_);
	private static final long _tokenSet_48_data_[] = { 777945215825608704L, 180883990913232916L, 0L, 0L };
	public static final BitSet _tokenSet_48 = new BitSet(_tokenSet_48_data_);
	private static final long _tokenSet_49_data_[] = { 777945215825608704L, 180883991047450644L, 0L, 0L };
	public static final BitSet _tokenSet_49 = new BitSet(_tokenSet_49_data_);
	private static final long _tokenSet_50_data_[] = { 777945215825608704L, 180883991315886100L, 0L, 0L };
	public static final BitSet _tokenSet_50 = new BitSet(_tokenSet_50_data_);
	private static final long _tokenSet_51_data_[] = { 137438953472L, 33035926368158594L, 0L, 0L };
	public static final BitSet _tokenSet_51 = new BitSet(_tokenSet_51_data_);
	private static final long _tokenSet_52_data_[] = { 35330400976896L, 934495650296253314L, 0L, 0L };
	public static final BitSet _tokenSet_52 = new BitSet(_tokenSet_52_data_);
	private static final long _tokenSet_53_data_[] = { 17136051927646208L, 2161726687263316966L, 0L, 0L };
	public static final BitSet _tokenSet_53 = new BitSet(_tokenSet_53_data_);
	private static final long _tokenSet_54_data_[] = { 777980546226585600L, 1078611956136311702L, 0L, 0L };
	public static final BitSet _tokenSet_54 = new BitSet(_tokenSet_54_data_);
	private static final long _tokenSet_55_data_[] = { 777945215825608704L, 180883991852757012L, 0L, 0L };
	public static final BitSet _tokenSet_55 = new BitSet(_tokenSet_55_data_);
	private static final long _tokenSet_56_data_[] = { 137438953472L, 288230376151712258L, 0L, 0L };
	public static final BitSet _tokenSet_56 = new BitSet(_tokenSet_56_data_);
	private static final long _tokenSet_57_data_[] = { 35321811042304L, 321266318626521990L, 0L, 0L };
	public static final BitSet _tokenSet_57 = new BitSet(_tokenSet_57_data_);
	private static final long _tokenSet_58_data_[] = { 777945353264562176L, 502150380271973270L, 0L, 0L };
	public static final BitSet _tokenSet_58 = new BitSet(_tokenSet_58_data_);
	private static final long _tokenSet_59_data_[] = { -14087526285310L, 1078612110755232767L, 0L, 0L };
	public static final BitSet _tokenSet_59 = new BitSet(_tokenSet_59_data_);
	private static final long _tokenSet_60_data_[] = { 777945353264562176L, 213919935400784790L, 0L, 0L };
	public static final BitSet _tokenSet_60 = new BitSet(_tokenSet_60_data_);
	private static final long _tokenSet_61_data_[] = { 35321811042304L, 321266318626521986L, 0L, 0L };
	public static final BitSet _tokenSet_61 = new BitSet(_tokenSet_61_data_);
	private static final long _tokenSet_62_data_[] = { 777945361854496768L, 502151204905694102L, 0L, 0L };
	public static final BitSet _tokenSet_62 = new BitSet(_tokenSet_62_data_);
	private static final long _tokenSet_63_data_[] = { 777945353264562176L, 213920828753982358L, 0L, 0L };
	public static final BitSet _tokenSet_63 = new BitSet(_tokenSet_63_data_);
	private static final long _tokenSet_64_data_[] = { 777945353264562176L, 213920004120261526L, 0L, 0L };
	public static final BitSet _tokenSet_64 = new BitSet(_tokenSet_64_data_);
	private static final long _tokenSet_65_data_[] = { 0L, 36767668832829444L, 0L, 0L };
	public static final BitSet _tokenSet_65 = new BitSet(_tokenSet_65_data_);
	private static final long _tokenSet_66_data_[] = { 777945430573973504L, 213920828753982358L, 0L, 0L };
	public static final BitSet _tokenSet_66 = new BitSet(_tokenSet_66_data_);
	private static final long _tokenSet_67_data_[] = { 35321811042304L, 576460760893375360L, 0L, 0L };
	public static final BitSet _tokenSet_67 = new BitSet(_tokenSet_67_data_);
	private static final long _tokenSet_68_data_[] = { 2464031327649792L, 2125699316176654848L, 0L, 0L };
	public static final BitSet _tokenSet_68 = new BitSet(_tokenSet_68_data_);
	private static final long _tokenSet_69_data_[] = { 35321811042304L, 69064739493774210L, 0L, 0L };
	public static final BitSet _tokenSet_69 = new BitSet(_tokenSet_69_data_);
	private static final long _tokenSet_70_data_[] = { 792617245015539712L, 1078611973316279286L, 0L, 0L };
	public static final BitSet _tokenSet_70 = new BitSet(_tokenSet_70_data_);
	private static final long _tokenSet_71_data_[] = { 14742415113977856L, 934495651365899238L, 0L, 0L };
	public static final BitSet _tokenSet_71 = new BitSet(_tokenSet_71_data_);
	private static final long _tokenSet_72_data_[] = { 35321811042304L, 33035942475205506L, 0L, 0L };
	public static final BitSet _tokenSet_72 = new BitSet(_tokenSet_72_data_);
	private static final long _tokenSet_73_data_[] = { 14742397934108672L, 934495651365899238L, 0L, 0L };
	public static final BitSet _tokenSet_73 = new BitSet(_tokenSet_73_data_);
	private static final long _tokenSet_74_data_[] = { 5637144576L, 144115188075855872L, 0L, 0L };
	public static final BitSet _tokenSet_74 = new BitSet(_tokenSet_74_data_);
	
	}
