package com.hand13;

import com.hand13.exception.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class JSONArray {
    public List<Object> objects;

    public JSONArray(Lexer lexer) throws JSONException {
        this.objects = new ArrayList<>();
        char c = lexer.nextClean();
        if (c != '[') {
            throw new JSONException("error");
        }
        for (; ; ) {
            c = lexer.nextClean();
            switch (c) {
                case '"': {
                    lexer.goBack();
                    String value = lexer.getStringValue();
                    objects.add(value);
                    break;
                }
                case '{': {
                    lexer.goBack();
                    JSONObject jsonObject = new JSONObject(lexer);
                    objects.add(jsonObject);
                    break;
                }
                case '[': {
                    lexer.goBack();
                    JSONArray array = new JSONArray(lexer);
                    objects.add(array);
                }
                default: {
                    lexer.goBack();
                    if (c >= '0' && c <= '9') {
                        BigDecimal number = lexer.getNumber();
                        objects.add(number);
                    } else {
                        throw new JSONException("error while parse");
                    }
                }
            }
            c = lexer.nextClean();
            if (c != ',' && c != ']') {
                throw new JSONException("failed");
            } else if (c == ']') {
                return;
            }
        }
    }

    public List<Object> getObjects() {
        return objects;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Object object : objects) {
            stringBuilder.append(object.toString());
            stringBuilder.append(",");
        }
        stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
