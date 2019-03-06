package pl.decerto.mule.internal.connection;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendlyTokenConnectionProvider implements PoolingConnectionProvider<CalendlyTokenConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(CalendlyTokenConnectionProvider.class);

	@Parameter
	@DisplayName("Token")
	private String token;

	@Override
	public CalendlyTokenConnection connect() {
		return new CalendlyTokenConnection(token);
	}

	@Override
	public void disconnect(CalendlyTokenConnection connection) {

	}

	@Override
	public ConnectionValidationResult validate(CalendlyTokenConnection connection) {
		return ConnectionValidationResult.success();
	}
}
