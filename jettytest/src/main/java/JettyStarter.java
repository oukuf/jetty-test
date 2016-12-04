import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class JettyStarter {

	public static void main(String[] args) throws Exception {

		WebAppContext webAppContext = new WebAppContext();

		Class<?> clazz = JettyStarter.class;
		ProtectionDomain protectionDomain = clazz.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URL url = codeSource.getLocation();
		String externalForm = url.toExternalForm();
		webAppContext.setWar(externalForm);

		Configuration[] configurations = { //
				new AnnotationConfiguration(), //
				new EnvConfiguration(), //
				new FragmentConfiguration(), //
				new JettyWebXmlConfiguration(), //
				new MetaInfConfiguration(), //
				new PlusConfiguration(), //
				new WebInfConfiguration(), //
				new WebXmlConfiguration() //
		};
		webAppContext.setConfigurations(configurations);

		webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

		Server server = new Server(8080);
		server.setHandler(webAppContext);
		server.start();
		server.join();
	}

}
