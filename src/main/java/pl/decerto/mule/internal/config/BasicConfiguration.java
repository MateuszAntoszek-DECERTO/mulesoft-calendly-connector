package pl.decerto.mule.internal.config;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import pl.decerto.mule.internal.connection.CalendlyTokenConnectionProvider;
import pl.decerto.mule.internal.operation.CalendlyOperations;
import pl.decerto.mule.internal.source.CalendlyListener;

@Operations(CalendlyOperations.class)
@ConnectionProviders(CalendlyTokenConnectionProvider.class)
@Sources(CalendlyListener.class)
public class BasicConfiguration {

}
