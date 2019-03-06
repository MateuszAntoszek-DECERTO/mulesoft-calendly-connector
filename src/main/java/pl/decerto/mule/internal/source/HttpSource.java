package pl.decerto.mule.internal.source;

import static org.mule.runtime.http.api.HttpHeaders.Names.CONTENT_TYPE;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.inject.Inject;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.server.HttpServer;
import org.mule.runtime.http.api.server.RequestHandler;
import org.mule.runtime.http.api.server.ServerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class HttpSource extends Source<InputStream, HttpRequestAttributes> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpSource.class);

	public final String NOTIFICATION_LISTENER_PATH = "/notificationListenerPath";

	@Parameter
	private String configurationName;

	@Inject
	private HttpService httpService;

	private HttpServer server;

	@Override
	public void onStart(SourceCallback<InputStream, HttpRequestAttributes> sourceCallback) throws MuleException {
		try {
			getServer().addRequestHandler(NOTIFICATION_LISTENER_PATH, getRequestHandler(sourceCallback));
			getServer().start();
		} catch (IOException e) {
			throw new RuntimeException("Error while creating listener on path ", e);
		}
		doStart(sourceCallback);
	}

	@Override
	public void onStop() {
		getServer().stop();
		doStop();
	}

	private RequestHandler getRequestHandler(SourceCallback<InputStream, HttpRequestAttributes> sourceCallback) {
		return (requestContext, responseCallback) -> {
			HttpRequest request = requestContext.getRequest();
			MediaType mediaType = getMediaType(request.getHeaderValue(CONTENT_TYPE), Charset.defaultCharset());

			Result.Builder<InputStream, HttpRequestAttributes> resultBuilder = Result.builder();
			Result<InputStream, HttpRequestAttributes> build = resultBuilder
					.output(request.getEntity().getContent())
					.mediaType(mediaType)
					.build();
			sourceCallback.handle(build, sourceCallback.createContext());
		};
	}

	private HttpServer getServer() {
		if (server == null) {
			createHttpServer();
		}
		return server;
	}

	private void createHttpServer() {
		try {
			server = httpService.getServerFactory().lookup(configurationName);
		} catch (ServerNotFoundException e) {
			throw new RuntimeException("Http configuration not found", e);
		}
	}

	private static MediaType getMediaType(final String contentTypeValue, Charset defaultCharset) {
		MediaType mediaType = MediaType.ANY;

		if (contentTypeValue != null) {
			mediaType = MediaType.parse(contentTypeValue);
		}
		if (!mediaType.getCharset().isPresent()) {
			return mediaType.withCharset(defaultCharset);
		} else {
			return mediaType;
		}
	}

	protected abstract void doStart(SourceCallback<InputStream, HttpRequestAttributes> sourceCallback) throws MuleException;

	protected abstract void doStop();
}
