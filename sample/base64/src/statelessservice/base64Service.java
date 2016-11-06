package statelessservice;

import java.util.ArrayList;
import java.util.List;

import microsoft.servicefabric.services.communication.runtime.ServiceInstanceListener;
import microsoft.servicefabric.services.runtime.StatelessService;

public class base64Service extends StatelessService {

	@Override
	protected List<ServiceInstanceListener> createServiceInstanceListeners() {
		List<ServiceInstanceListener> listeners = new ArrayList<>();
		listeners.add(new ServiceInstanceListener(
				context -> new CustomHttpCommunicationListener(context, "endpoint", ""), "endpoint"));
		return listeners;
	}
}
