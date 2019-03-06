package pl.decerto.mule.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import java.util.concurrent.ExecutionException;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.internal.connection.CalendlyTokenConnection;

public class CalendlyOperations {

	private final Logger LOGGER = LoggerFactory.getLogger(CalendlyOperations.class);

	@MediaType(ANY)
	public void getIssue(String issueName, @Connection CalendlyTokenConnection connection) throws ExecutionException, InterruptedException {

	}

}
