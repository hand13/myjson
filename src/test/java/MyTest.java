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
    }
}
