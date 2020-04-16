import java.sql.SQLException;

/**
 * Exception class. there is a constructor that is capable of getting the message that describes the malfunction
 * as well as a constructor that is capable of getting both the message,
 * that describes the malfunction, and the root cause
 */
public class CalorieException extends SQLException {
    CalorieException(String msg){
        super(msg);
    }
    CalorieException(String msg,Throwable rootCause){
        super(msg,rootCause);
    }
}
