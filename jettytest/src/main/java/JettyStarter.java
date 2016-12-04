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

	// private static final Logger LOG =
	// LoggerFactory.getLogger(JettyStarter.class);

	public static void main(String[] args) throws Exception {

		WebAppContext webAppContext = new WebAppContext();

		Class<?> clazz = JettyStarter.class;
		ProtectionDomain protectionDomain = clazz.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URL url = codeSource.getLocation();
		String externalForm = url.toExternalForm();

		// ex) file:/C:/pleiades/workspace/jettytest/target/jettytest.war
		// ex) file:/C:/pleiades/workspace/jettytest/target/classes/
		// LOG.trace("externalForm:" + externalForm);

		String contextPath = null;

		if (externalForm.endsWith(".war")) {

			webAppContext.setWar(externalForm);

			String[] externalForms = externalForm.split("/");
			String warName = externalForms[externalForms.length - 1];
			contextPath = warName.replaceAll("\\.war$", "");

		} else {

			webAppContext.setResourceBase("src/main/webapp");
			webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");

			String[] externalForms = externalForm.split("/");
			for (String s : externalForms) {
				if (s.equals("target") || s.equals("classes")) {
					break;
				}
				contextPath = s;
			}
		}

		webAppContext.setContextPath("/" + contextPath);

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
