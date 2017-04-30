package rabbitmethods;

import java.util.List;
import javafx.scene.text.Text;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Harry
 */
public class ValidationTest {
    
    public ValidationTest() {
    }
    
    @Test
    public void emailTest()
    {
        Session session = new Session();
        Validation validation = new Validation();
        Text text = new Text();
        assertTrue(validation.emailFieldIsValid("test@email.com", text, session)); //Valid email
        assertFalse(validation.emailFieldIsValid("test", text, session)); //No domain
        assertFalse(validation.emailFieldIsValid("@email.com", text, session)); //No email before domain
        assertFalse(validation.emailFieldIsValid("", text, session)); //Blank
        
    }
    
    @Test
    public void passwordTest()
    {
        Session session = new Session();
        Validation validation = new Validation();
        Text text = new Text();
        
        
    }
    
}
