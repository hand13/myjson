package com.hand13;

import com.hand13.exception.JSONException;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @version $Revision$ $Date$
 * @author $Author$
*/
public class JSONObject implements JSONEntity{
    private Map<String,Object> objects;
    public JSONObject(Lexer lexer) throws JSONException {
        objects = new LinkedHashMap<>();
        char c = lexer.nextClean();
        if(c != '{'){
            throw new JSONException("object should be started with {");
        }
        for(;;) {
            String name = lexer.getKey();
            c = lexer.nextClean();
            if(c != ':') {
                throw new JSONException("die");
            }
            c = lexer.nextClean();
            switch (c){
                case '"':{
                    lexer.goBack();
                    String value = lexer.getStringValue();
                    objects.put(name,value);
                    break;
                }
                case '{':{
                    lexer.goBack();
                    JSONObject jsonObject = new JSONObject(lexer);
                    objects.put(name,jsonObject);
                    break;
                }
                case'[':{
                    lexer.goBack();
                    JSONArray array = new JSONArray(lexer);
                    objects.put(name,array);
                    break;
                }
                default:{
                    lexer.goBack();
                    if(c>='0' && c<= '9'){
                        BigDecimal number = lexer.getNumber();
                        objects.put(name,number);
                    }else {
                        throw new JSONException("error while parse");
                    }
                }
            }
            c = lexer.nextClean();
            if(c !=',' && c!= '}'){
                throw new JSONException("failed");
            }else if (c == '}'){
                return;
            }
        }
    }

    public String getString(String key) throws JSONException {
        Object o = objects.get(key);
        if(o instanceof  String){
            return (String)o;
        }else
            throw new JSONException("not a string");
    }
    public JSONArray getArray(String key) throws JSONException{
        Object o = objects.get(key);
        if(o instanceof JSONArray) {
            return (JSONArray)o;
        }else {
            throw new JSONException("not a array");
        }
    }

    public JSONObject getObject(String key)throws JSONException{
        Object o = objects.get(key);
        if(o instanceof JSONObject) {
            return (JSONObject)o;
        }else {
            throw new JSONException("not a object");
        }
    }

    public int getInt(String key)throws JSONException{
        Object o = objects.get(key);
        if(o instanceof BigDecimal) {
            return ((BigDecimal) o).intValue();
        }else {
            throw new JSONException("not a number");
        }
    }

    public float getFloat(String key)throws JSONException{
        Object o = objects.get(key);
        if(o instanceof BigDecimal) {
            return ((BigDecimal) o).floatValue();
        }else {
            throw new JSONException("not a number");
        }
    }

    @Override
    public String toString() {
        Set<String> keys = objects.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        int i = 0;
        for(String key :keys) {
            i++;
            stringBuilder.append(key).append(":").append(objects.get(key));
            if(i < keys.size()){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String toJSON() {
        Set<String> keys = objects.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        int i = 0;
        for(String key :keys) {
            i++;
            stringBuilder.append("\"").append(key).append("\"").append(":");
            Object o = objects.get(key);
            if(o instanceof  String) {
                stringBuilder.append("\"").append(o).append("\"");
            }else if(o instanceof JSONEntity) {
                stringBuilder.append(((JSONEntity) o).toJSON());
            }else {
                stringBuilder.append(o.toString());
            }
            if(i < keys.size()){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
