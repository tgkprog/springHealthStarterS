package sel2in.academic;



import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

public class Test1 {

	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(Test1.class.getName());
    
    public static void main(String[] args) {
		logger.log(Sel2inLogger.DEBUG, "msg");
	}

}
