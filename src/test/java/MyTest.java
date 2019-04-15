import com.hand13.JSONArray;
import com.hand13.JSONObject;
import com.hand13.Lexer;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @version $Revision$ $Date$
 * @author $Author$
*/
public class MyTest {
    @Test
    public void ts()throws Exception {
        InputStream in = this.getClass().getResourceAsStream("test.json");
        Lexer lexer = new Lexer(new InputStreamReader(in));
        JSONObject object = new JSONObject(lexer);
        Assert.assertEquals(object.getInt("id"),12);
        Assert.assertTrue(object.getBoolean("human"));
        System.out.println(object.toJSON());
        JSONArray array = object.getArray("data");
        for(Object o :array) {
            System.out.println(o);
            if(o instanceof JSONArray) {
                for(Object o1:(JSONArray)o) {
                    System.out.println(o1);
                }
            }
        }
        System.out.println(object);
    }
}
