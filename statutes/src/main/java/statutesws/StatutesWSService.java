package statutesws;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7 2016-08-27T08:51:52.848-07:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "StatutesWS", targetNamespace = "http://statutesws/")
public class StatutesWSService extends Service {

	public final static QName SERVICE = new QName("http://statutesws/", "StatutesWS");
	public final static QName StatutesWSPort = new QName("http://statutesws/", "StatutesWSPort");

	public StatutesWSService(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public StatutesWSService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public StatutesWSService(URL wsdlLocation, WebServiceFeature... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public StatutesWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	/**
	 *
	 * @return returns StatutesWS
	 */
	@WebEndpoint(name = "StatutesWSPort")
	public StatutesWS getStatutesWSPort() {
		return super.getPort(StatutesWSPort, StatutesWS.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns StatutesWS
	 */
	@WebEndpoint(name = "StatutesWSPort")
	public StatutesWS getStatutesWSPort(WebServiceFeature... features) {
		return super.getPort(StatutesWSPort, StatutesWS.class, features);
	}

}
