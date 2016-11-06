package statelessservice;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;

import microsoft.servicefabric.services.communication.runtime.CommunicationListener;
import microsoft.servicefabric.services.runtime.StatelessServiceContext;
import system.fabric.FabricRuntime;
import system.fabric.ServiceContext;
import system.fabric.description.EndPointProtocol;
import system.fabric.description.EndpointResourceDescription;

public class CustomHttpCommunicationListener implements CommunicationListener {

	private static Logger logger = Logger.getLogger("base64api");

	private ServiceContext serviceContext;
	private String endpointName;
	private String listeningAddress;
	private String appRoot;
	private String publishAddress;

	private Server server;

	public CustomHttpCommunicationListener(ServiceContext serviceContext, String endpointName, String appRoot) {
		this.serviceContext = serviceContext;
		this.endpointName = endpointName;
		this.appRoot = appRoot;
	}

	@Override
	public void abort() {
		try {
			this.server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<?> closeAsync() {
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();

		try {
			this.server.stop();
			future.complete(0);
		} catch (Exception e) {
			future.completeExceptionally(e);
		}
		return future;
	}

	@Override
	public CompletableFuture<String> openAsync() {
		EndpointResourceDescription serviceEndpoint = this.serviceContext.codePackageActivationContext()
				.getEndpoint(this.endpointName);
		EndPointProtocol protocol = serviceEndpoint.getProtocol();
		int port = serviceEndpoint.getPort();

		if (this.serviceContext instanceof StatelessServiceContext) {
			this.listeningAddress = String.format("%s://+:%s/%s", protocol, port,
					this.appRoot == null || this.appRoot.equals("") ? "" : this.appRoot.replaceAll("/$", "") + "/");
			logger.info("Listening address: " + listeningAddress);
		}

		this.publishAddress = this.listeningAddress.replace("+", FabricRuntime.getNodeContext().getIpAddressOrFQDN());
		logger.info("Publish address: " + publishAddress);

		logger.info("Starting web server on " + this.listeningAddress);
		InetSocketAddress addr = new InetSocketAddress(FabricRuntime.getNodeContext().getIpAddressOrFQDN(), port);
		this.server = new Server(addr);
		server.setHandler(new SimpleHttpHandler());

		CompletableFuture<String> future = CompletableFuture.completedFuture(this.publishAddress);
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return future;
	}

}
