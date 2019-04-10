package com.hand13;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $Revision$ $Date$
 * @author $Author$
*/
public class JSONArray {
    public List<Object> objects;
    public JSONArray(Lexer lexer){
        this.objects = new ArrayList<Object>();
    }
}
