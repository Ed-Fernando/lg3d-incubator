// $ANTLR 2.7.1: "Zeta.g" -> "ZLexer.java"$
package org.jdesktop.lg3d.apps.archviz3d.zparser; 
import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import java.io.Reader;
import java.util.Hashtable;


import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.*;


public class ZLexer extends antlr.CharScanner implements ZTokenTypes, TokenStream
 {

        protected SymbolTable symbols;

        public  void symbolTable(SymbolTable st) {
                symbols=st;
        }

        protected int tokColumn = 1;
        protected int column = 1;
        protected int line = 1;

        public void consume() {
          try {
          if ( inputState.guessing==0 ) {
            if (text.length()==0) { // remember token start column
              tokColumn = column;
            }
            if (LA(1)=='\n') { column = 1; line++; }
            else { column++; }
            } //end if
          super.consume();
          } catch (antlr.CharStreamException e) {}
        }//end consume

        protected Token makeToken(int t) {
          Token tok = super.makeToken(t);
          tok.setColumn(tokColumn);
          tok.setLine(line);
          return tok;
        } //end makeToken



public ZLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public ZLexer(Reader in) {
	this(new CharBuffer(in));
}
public ZLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public ZLexer(LexerSharedInputState state) {
	super(state);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("\\theta", this), new Integer(114));
	literals.put(new ANTLRHashString("\\begin{gendef}", this), new Integer(29));
	literals.put(new ANTLRHashString("\\lbag", this), new Integer(112));
	literals.put(new ANTLRHashString("\\forall", this), new Integer(50));
	literals.put(new ANTLRHashString("\\land", this), new Integer(55));
	literals.put(new ANTLRHashString("\\semi", this), new Integer(62));
	literals.put(new ANTLRHashString("\\inrel", this), new Integer(77));
	literals.put(new ANTLRHashString("\\rsch", this), new Integer(68));
	literals.put(new ANTLRHashString("\\end{zed}", this), new Integer(26));
	literals.put(new ANTLRHashString("\\bbar", this), new Integer(40));
	literals.put(new ANTLRHashString("\\seq", this), new Integer(96));
	literals.put(new ANTLRHashString("\\Delta", this), new Integer(71));
	literals.put(new ANTLRHashString("\\begin{schema}", this), new Integer(31));
	literals.put(new ANTLRHashString("\\power", this), new Integer(94));
	literals.put(new ANTLRHashString("\\vee", this), new Integer(58));
	literals.put(new ANTLRHashString("\\hide", this), new Integer(64));
	literals.put(new ANTLRHashString("\\lsch", this), new Integer(67));
	literals.put(new ANTLRHashString("\\bsup", this), new Integer(103));
	literals.put(new ANTLRHashString("\\rdata", this), new Integer(44));
	literals.put(new ANTLRHashString("\\pipe", this), new Integer(63));
	literals.put(new ANTLRHashString("\\Xi", this), new Integer(72));
	literals.put(new ANTLRHashString("\\ldata", this), new Integer(43));
	literals.put(new ANTLRHashString("\\begin{axdef}", this), new Integer(27));
	literals.put(new ANTLRHashString("\\exists", this), new Integer(52));
	literals.put(new ANTLRHashString("\\emptyset", this), new Integer(106));
	literals.put(new ANTLRHashString("\\pset", this), new Integer(95));
	literals.put(new ANTLRHashString("\\IF", this), new Integer(83));
	literals.put(new ANTLRHashString("\\lor", this), new Integer(57));
	literals.put(new ANTLRHashString("\\also", this), new Integer(48));
	literals.put(new ANTLRHashString("\\implies", this), new Integer(54));
	literals.put(new ANTLRHashString("\\defs", this), new Integer(36));
	literals.put(new ANTLRHashString("\\natone", this), new Integer(117));
	literals.put(new ANTLRHashString("\\end{gendef}", this), new Integer(30));
	literals.put(new ANTLRHashString("|", this), new Integer(101));
	literals.put(new ANTLRHashString("\\zcmp", this), new Integer(61));
	literals.put(new ANTLRHashString("\\rseq", this), new Integer(111));
	literals.put(new ANTLRHashString("\\pre", this), new Integer(70));
	literals.put(new ANTLRHashString("\\ddef", this), new Integer(39));
	literals.put(new ANTLRHashString("\\false", this), new Integer(80));
	literals.put(new ANTLRHashString("\\true", this), new Integer(79));
	literals.put(new ANTLRHashString("\\exists_1", this), new Integer(53));
	literals.put(new ANTLRHashString("\\iff", this), new Integer(59));
	literals.put(new ANTLRHashString("\\nat", this), new Integer(116));
	literals.put(new ANTLRHashString("\\esup", this), new Integer(104));
	literals.put(new ANTLRHashString("\\rimg", this), new Integer(99));
	literals.put(new ANTLRHashString("\\end{schema}", this), new Integer(32));
	literals.put(new ANTLRHashString("\\lambda", this), new Integer(81));
	literals.put(new ANTLRHashString("\\wedge", this), new Integer(56));
	literals.put(new ANTLRHashString("\\lseq", this), new Integer(110));
	literals.put(new ANTLRHashString("\\mu", this), new Integer(82));
	literals.put(new ANTLRHashString("\\prod", this), new Integer(87));
	literals.put(new ANTLRHashString("\\rangle", this), new Integer(109));
	literals.put(new ANTLRHashString("\\langle", this), new Integer(108));
	literals.put(new ANTLRHashString("\\ELSE", this), new Integer(85));
	literals.put(new ANTLRHashString("\\cross", this), new Integer(86));
	literals.put(new ANTLRHashString("\\lnot", this), new Integer(69));
	literals.put(new ANTLRHashString("\\sdef", this), new Integer(38));
	literals.put(new ANTLRHashString("\\THEN", this), new Integer(84));
	literals.put(new ANTLRHashString("\\end{axdef}", this), new Integer(28));
	literals.put(new ANTLRHashString("\\where", this), new Integer(121));
	literals.put(new ANTLRHashString("\\limg", this), new Integer(98));
	literals.put(new ANTLRHashString("\\LET", this), new Integer(75));
	literals.put(new ANTLRHashString("\\project", this), new Integer(60));
	literals.put(new ANTLRHashString("\\rbag", this), new Integer(113));
	literals.put(new ANTLRHashString("\\emptyseq", this), new Integer(107));
	literals.put(new ANTLRHashString("\\begin{zed}", this), new Integer(25));
caseSensitiveLiterals = true;
setCaseSensitive(true);
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '[':
				{
					mOPENBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mCLOSEBRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case '{':
				{
					mOPENBRACE(true);
					theRetToken=_returnToken;
					break;
				}
				case '}':
				{
					mCLOSEBRACE(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mOPENPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mCLOSEPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMICOLON(true);
					theRetToken=_returnToken;
					break;
				}
				case ':':
				{
					mCOLON(true);
					theRetToken=_returnToken;
					break;
				}
				case '@':
				{
					mAT(true);
					theRetToken=_returnToken;
					break;
				}
				case '/':
				{
					mSLASH(true);
					theRetToken=_returnToken;
					break;
				}
				case '!':  case '\'':  case '?':  case '_':
				{
					mSTROKE(true);
					theRetToken=_returnToken;
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mNUMBER(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='\\') && (LA(2)=='{')) {
						mOPENSET(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\\') && (LA(2)=='}')) {
						mCLOSESET(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\\') && (LA(2)=='_')) {
						mUNDERSCORE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\\') && (LA(2)=='\\') && (true)) {
						mBACKSLASH_BACKSLASH(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='~') && (true) && (true)) {
						mHYPHEN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (true) && (true)) {
						mPOINT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==',') && (true) && (true)) {
						mCOMMA(true);
						theRetToken=_returnToken;
					}
					else if ((_tokenSet_0.member(LA(1))) && (true) && (true)) {
						mWS(true);
						theRetToken=_returnToken;
					}
					else if ((_tokenSet_1.member(LA(1))) && (true) && (true)) {
						mWORD(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mHYPHEN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HYPHEN;
		int _saveIndex;
		
		match('~');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPOINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = POINT;
		int _saveIndex;
		
		match('.');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPENBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPENBRACKET;
		int _saveIndex;
		
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCLOSEBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSEBRACKET;
		int _saveIndex;
		
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPENBRACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPENBRACE;
		int _saveIndex;
		
		match('{');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCLOSEBRACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSEBRACE;
		int _saveIndex;
		
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPENPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPENPAREN;
		int _saveIndex;
		
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCLOSEPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSEPAREN;
		int _saveIndex;
		
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPENSET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPENSET;
		int _saveIndex;
		
		match("\\{");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCLOSESET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CLOSESET;
		int _saveIndex;
		
		match("\\}");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mUNDERSCORE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UNDERSCORE;
		int _saveIndex;
		
		match("\\_");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;
		
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSEMICOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMICOLON;
		int _saveIndex;
		
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;
		
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBACKSLASH_BACKSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BACKSLASH_BACKSLASH;
		int _saveIndex;
		
		match("\\\\");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AT;
		int _saveIndex;
		
		match('@');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SLASH;
		int _saveIndex;
		
		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mNL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NL;
		int _saveIndex;
		
		{
		if ((LA(1)=='\r') && (LA(2)=='\n')) {
			match("\r\n");
		}
		else if ((LA(1)=='\r') && (true)) {
			match('\r');
		}
		else if ((LA(1)=='\n')) {
			match('\n');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		
		}
		_ttype = Token.SKIP; newline();
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		case '~':
		{
			match('~');
			break;
		}
		case '&':
		{
			match('&');
			break;
		}
		case '\n':  case '\r':
		{
			mNL(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		_ttype = Token.SKIP;
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT;
		int _saveIndex;
		
		matchRange('0','9');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LETTER;
		int _saveIndex;
		
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mIDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = IDENT;
		int _saveIndex;
		
		switch ( LA(1)) {
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':  case 'a':  case 'b':
		case 'c':  case 'd':  case 'e':  case 'f':
		case 'g':  case 'h':  case 'i':  case 'j':
		case 'k':  case 'l':  case 'm':  case 'n':
		case 'o':  case 'p':  case 'q':  case 'r':
		case 's':  case 't':  case 'u':  case 'v':
		case 'w':  case 'x':  case 'y':  case 'z':
		{
			mLETTER(false);
			{
			_loop203:
			do {
				switch ( LA(1)) {
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':  case 'a':  case 'b':
				case 'c':  case 'd':  case 'e':  case 'f':
				case 'g':  case 'h':  case 'i':  case 'j':
				case 'k':  case 'l':  case 'm':  case 'n':
				case 'o':  case 'p':  case 'q':  case 'r':
				case 's':  case 't':  case 'u':  case 'v':
				case 'w':  case 'x':  case 'y':  case 'z':
				{
					mLETTER(false);
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mDIGIT(false);
					break;
				}
				case '\\':
				{
					{
					_saveIndex=text.length();
					match("\\");
					text.setLength(_saveIndex);
					match("_");
					}
					break;
				}
				default:
				{
					break _loop203;
				}
				}
			} while (true);
			}
			break;
		}
		case '*':  case '+':  case ',':  case '-':
		case '.':  case '<':  case '=':  case '>':
		case '|':
		{
			mINFIX(false);
			break;
		}
		default:
			if ((LA(1)=='\\') && (LA(2)=='#')) {
				match("\\#");
			}
			else if ((LA(1)=='\\') && (_tokenSet_2.member(LA(2)))) {
				match('\\');
				{
				int _cnt205=0;
				_loop205:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						mLETTER(false);
					}
					else {
						if ( _cnt205>=1 ) { break _loop205; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
					}
					
					_cnt205++;
				} while (true);
				}
				{
				if ((LA(1)=='{')) {
					mOPENBRACE(false);
					{
					int _cnt208=0;
					_loop208:
					do {
						if ((_tokenSet_2.member(LA(1)))) {
							mLETTER(false);
						}
						else {
							if ( _cnt208>=1 ) { break _loop208; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
						}
						
						_cnt208++;
					} while (true);
					}
					mCLOSEBRACE(false);
				}
				else {
				}
				
				}
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mINFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INFIX;
		int _saveIndex;
		
		{
		if ((LA(1)=='=') && (_tokenSet_3.member(LA(2))) && (true)) {
			match('=');
			{
			if ((LA(1)=='=') && (_tokenSet_3.member(LA(2))) && (true)) {
				match('=');
			}
			else if ((_tokenSet_3.member(LA(1))) && (true) && (true)) {
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			
			}
		}
		else if ((LA(1)==',') && (_tokenSet_3.member(LA(2))) && (true)) {
			match(',');
		}
		else if ((_tokenSet_3.member(LA(1))) && (true) && (true)) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		
		}
		{
		int _cnt214=0;
		_loop214:
		do {
			switch ( LA(1)) {
			case '+':
			{
				match('+');
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '*':
			{
				match('*');
				break;
			}
			case '.':
			{
				match('.');
				break;
			}
			case '=':
			{
				match('=');
				break;
			}
			case '<':
			{
				match('<');
				break;
			}
			case '>':
			{
				match('>');
				break;
			}
			case ',':
			{
				match(',');
				break;
			}
			case '|':
			{
				match('|');
				break;
			}
			default:
			{
				if ( _cnt214>=1 ) { break _loop214; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			}
			_cnt214++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTROKE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STROKE;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '\'':
		{
			match('\'');
			break;
		}
		case '?':
		{
			match('?');
			break;
		}
		case '!':
		{
			match('!');
			break;
		}
		case '_':
		{
			match('_');
			mDIGIT(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NUMBER;
		int _saveIndex;
		
		{
		int _cnt217=0;
		_loop217:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				mDIGIT(false);
			}
			else {
				if ( _cnt217>=1 ) { break _loop217; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt217++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWORD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WORD;
		int _saveIndex;
		Token x=null;
		
		mIDENT(true);
		x=_returnToken;
		
		_ttype = WORD;
		
		if (symbols.is_inop(x.getText())) {
		
		switch ( ((Integer)symbols.get(x.getText(),"priority")).intValue()) {
		case 1: _ttype = IN_FUN1;break;
		case 2: _ttype = IN_FUN2;break;
		case 3: _ttype = IN_FUN3;break;
		case 4: _ttype = IN_FUN4;break;
		case 5: _ttype = IN_FUN5;break;
		case 6: _ttype = IN_FUN6;break;
		}
		}
		if (symbols.is_ingen(x.getText()))  _ttype = IN_GEN;
		if (symbols.is_inrel(x.getText()))  _ttype = IN_REL;
		if (symbols.is_pregen(x.getText())) _ttype = PRE_GEN;
		if (symbols.is_prerel(x.getText())) _ttype = PRE_REL;
		if (symbols.is_postop(x.getText())) _ttype = POST_FUN;
		if (symbols.is_schema(x.getText())) _ttype = SCHEMA_NAME;
		
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long _tokenSet_0_data_[] = { 279172883968L, 4611686018427387904L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 8070586871689773056L, 1729382248722989054L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { 0L, 576460743847706622L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { 8070586871689773056L, 1152921504606846976L, 0L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	
	}
