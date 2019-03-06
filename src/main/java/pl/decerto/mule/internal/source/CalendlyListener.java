package pl.decerto.mule.internal.source;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import java.io.InputStream;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.internal.config.BasicConfiguration;
import pl.decerto.mule.internal.connection.CalendlyTokenConnection;
import pl.decerto.mule.internal.service.CalendlyService;
import pl.decerto.mule.internal.service.model.EventType;

@MediaType(value = ANY, strict = false)
public class CalendlyListener extends HttpSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendlyListener.class);

	@Connection
	private ConnectionProvider<CalendlyTokenConnection> connection;

	@Config
	private BasicConfiguration config;

	@Parameter
	@DisplayName(value = "Server url")
	private String url;

	@Parameter
	private EventType type;

	private CalendlyService calendlyService;

	@Override
	protected void doStart(SourceCallback<InputStream, HttpRequestAttributes> sourceCallback) throws MuleException {
		calendlyService = new CalendlyService(connection.connect().getTemplate());
		String hookId = calendlyService.getHookId(getNotificationUrl());
		if (hookId == null) {
			calendlyService.createHook(getNotificationUrl(), type);
			LOGGER.info("START LISTENER ON URL: " + getNotificationUrl());
		}
	}

	@Override
	protected void doStop() {
		String hookId = calendlyService.getHookId(getNotificationUrl());
		if (hookId != null) {
			calendlyService.deleteHook(hookId);
			LOGGER.info("STOP LISTENER ON URL: " + getNotificationUrl());
		}
	}

	private String getNotificationUrl() {
		return url + NOTIFICATION_LISTENER_PATH;
	}
}