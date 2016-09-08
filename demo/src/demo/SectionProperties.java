package demo;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * 用于处理带section的配置文件，如
 * [section]
 * key=value
 * 。。。。。
 * 此时key=section.key
 * 如果无section，key=key
 * 
 * @author: liangxiaolin
 */
public class SectionProperties extends Properties {
	private static final long serialVersionUID = 4112578634029874840L;
	private final String keydelimit = ".";
	
	private boolean isIgnoreChar(char c){
		return c == '[' || Character.isWhitespace(c);
	}
	
	@Override
	public void load(InputStream inStream ) throws IOException{
		// The spec says that the file must be encoded using ISO-8859-1.
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "ISO-8859-1"));
	    String line;
	    String currSection = null;
	 
	    while ((line = reader.readLine()) != null)
	    {
	    	char c = 0;
	        int pos = 0;
	        // Leading whitespaces must be deleted first.
	        while (pos < line.length() && isIgnoreChar(c = line.charAt(pos)))
	        	pos++;
	 
	        // If empty line or begins with a comment character, skip this line.
	        if ((line.length() - pos) == 0 || line.charAt(pos) == '#' || line.charAt(pos) == '!')
	        	continue;
	 
	        // The characters up to the next Whitespace, ':', or '='
	        // describe the key.  But look for escape sequences.
	        // Try to short-circuit when there is no escape char.
	        int start = pos;
	        
	        //deal with [section]
	        boolean isSection = line.indexOf('[',pos) != -1 && line.indexOf(']',pos) != -1;
	        
	        //deal with \ line feed
	        boolean needsEscape = line.indexOf('\\', pos) != -1;
	        StringBuilder key = needsEscape ? new StringBuilder() : null;
	        
	        while (pos < line.length() && ! Character.isWhitespace(c = line.charAt(pos++))
	               && c != '=' && c != ':' && c != ']')
	        {
	        	if (needsEscape && c == '\\'){
	        		if (pos == line.length()){
	                    // The line continues on the next line.  If there
	                    // is no next line, just treat it as a key with an
	                    // empty value.
	                    line = reader.readLine();
	                    
	                    if (line == null)  line = "";
	                    
	                    pos = 0;
	                    while (pos < line.length() && isIgnoreChar(c = line.charAt(pos)))
	                    	pos++;
	                }else
	                {
	                	c = line.charAt(pos++);
	                    switch (c){
	                    case 'n':
	                    	key.append('\n');
	                        break;
	                    case 't':
	                        key.append('\t');
	                        break;
	                    case 'r':
	                        key.append('\r');
	                        break;
	                    case 'u':
	                        if (pos + 4 <= line.length()){
	                        	char uni = (char) Integer.parseInt(line.substring(pos, pos + 4), 16);
	                            key.append(uni);
	                            pos += 4;
	                        }        // else throw exception?
	                        break;
	                    default:
	                        key.append(c);
	                        break;
	                      }
	                  }
	              }
	            else if (needsEscape)
	            	key.append(c);
	          }
	 
	        boolean isDelim = (c == ':' || c == '=' || c == ']');
	        String keyString;
	        if (needsEscape)
	        	keyString = key.toString();
	        else if (isDelim || Character.isWhitespace(c))
	        	keyString = line.substring(start, pos - 1);
	        else
	        	keyString = line.substring(start, pos);
	        
	        if(isSection){
	        	currSection = keyString;
	        	keyString = "";
	        }
	 
	        while (pos < line.length() && isIgnoreChar(c = line.charAt(pos)))
	        	pos++;
	 
	        if (! isDelim && (c == ':' || c == '='))
	          {
	            pos++;
	            while (pos < line.length() && isIgnoreChar(c = line.charAt(pos)))
	              pos++;
	          }
	 
	    // Short-circuit if no escape chars found.
	    if (!needsEscape)
	      {
	    	if(currSection == null){
	    		put(keyString, line.substring(pos));
	    	}
	    	else{
	    		put(currSection+keydelimit+keyString, line.substring(pos));
	    	}
	        
	        continue;
	      }
	 
	    // Escape char found so iterate through the rest of the line.
	        StringBuilder element = new StringBuilder(line.length() - pos);
	        while (pos < line.length())
	          {
	            c = line.charAt(pos++);
	            if (c == '\\')
	              {
	                if (pos == line.length())
	                  {
	                    // The line continues on the next line.
	                    line = reader.readLine();
	 
	            // We might have seen a backslash at the end of
	            // the file.  The JDK ignores the backslash in
	            // this case, so we follow for compatibility.
	            if (line == null)
	              break;
	 
	                    pos = 0;
	                    while (pos < line.length()
	                           && Character.isWhitespace(c = line.charAt(pos)))
	                      pos++;
	                    element.ensureCapacity(line.length() - pos +
	                                           element.length());
	                  }
	                else
	                  {
	                    c = line.charAt(pos++);
	                    switch (c)
	                      {
	                      case 'n':
	                        element.append('\n');
	                        break;
	                      case 't':
	                        element.append('\t');
	                        break;
	                      case 'r':
	                        element.append('\r');
	                        break;
	                      case 'u':
	                        if (pos + 4 <= line.length())
	                          {
	                            char uni = (char) Integer.parseInt
	                              (line.substring(pos, pos + 4), 16);
	                            element.append(uni);
	                            pos += 4;
	                          }        // else throw exception?
	                        break;
	                      default:
	                        element.append(c);
	                        break;
	                      }
	                  }
	              }
	            else
	              element.append(c);
	          }
	        
	        if(currSection == null){
	        	put(keyString, element.toString());
	        }
	        else{
	        	put(currSection+keydelimit+keyString, line.substring(pos));
	        }
	      }

	}
}
