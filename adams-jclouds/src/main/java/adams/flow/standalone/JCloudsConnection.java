/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * JCloudsConnection.java
 * Copyright (C) 2016 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.standalone;

import adams.core.QuickInfoHelper;
import adams.core.base.BaseClassname;
import adams.core.base.BasePassword;
import adams.core.base.BaseURL;
import adams.core.net.JClouds;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

/**
 <!-- globalinfo-start -->
 * Contains parameters for a JClouds connection.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
 <!-- flow-summary-end -->
 *
 <!-- options-start -->
 * <pre>-logging-level &lt;OFF|SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST&gt; (property: loggingLevel)
 * &nbsp;&nbsp;&nbsp;The logging level for outputting errors and debugging output.
 * &nbsp;&nbsp;&nbsp;default: WARNING
 * </pre>
 * 
 * <pre>-name &lt;java.lang.String&gt; (property: name)
 * &nbsp;&nbsp;&nbsp;The name of the actor.
 * &nbsp;&nbsp;&nbsp;default: JCloudsConnection
 * </pre>
 * 
 * <pre>-annotation &lt;adams.core.base.BaseAnnotation&gt; (property: annotations)
 * &nbsp;&nbsp;&nbsp;The annotations to attach to this actor.
 * &nbsp;&nbsp;&nbsp;default: 
 * </pre>
 * 
 * <pre>-skip &lt;boolean&gt; (property: skip)
 * &nbsp;&nbsp;&nbsp;If set to true, transformation is skipped and the input token is just forwarded 
 * &nbsp;&nbsp;&nbsp;as it is.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-stop-flow-on-error &lt;boolean&gt; (property: stopFlowOnError)
 * &nbsp;&nbsp;&nbsp;If set to true, the flow gets stopped in case this actor encounters an error;
 * &nbsp;&nbsp;&nbsp; useful for critical actors.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-silent &lt;boolean&gt; (property: silent)
 * &nbsp;&nbsp;&nbsp;If enabled, then no errors are output in the console; Note: the enclosing 
 * &nbsp;&nbsp;&nbsp;actor handler must have this enabled as well.
 * &nbsp;&nbsp;&nbsp;default: false
 * </pre>
 * 
 * <pre>-provider &lt;java.lang.String&gt; (property: provider)
 * &nbsp;&nbsp;&nbsp;The cloud provider to use.
 * &nbsp;&nbsp;&nbsp;default: openstack-nova
 * </pre>
 * 
 * <pre>-identity &lt;java.lang.String&gt; (property: identity)
 * &nbsp;&nbsp;&nbsp;The identity to use (tenantName:userName).
 * &nbsp;&nbsp;&nbsp;default: tenantName:userName
 * </pre>
 * 
 * <pre>-credential &lt;adams.core.base.BasePassword&gt; (property: credential)
 * &nbsp;&nbsp;&nbsp;The password to use for the connection.
 * &nbsp;&nbsp;&nbsp;default: {c2VjcmV0}
 * </pre>
 * 
 * <pre>-endpoint &lt;adams.core.base.BaseURL&gt; (property: endpoint)
 * &nbsp;&nbsp;&nbsp;The URL for the API endpoint.
 * &nbsp;&nbsp;&nbsp;default: https:&#47;&#47;somehost:5000&#47;v2.0
 * </pre>
 * 
 * <pre>-api-class &lt;adams.core.base.BaseClassname&gt; (property: APIClass)
 * &nbsp;&nbsp;&nbsp;The API class for the provider.
 * &nbsp;&nbsp;&nbsp;default: java.lang.Object
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class JCloudsConnection
  extends AbstractStandalone {

  /** for serialization. */
  private static final long serialVersionUID = -1959430342987913960L;

  /** the provider. */
  protected String m_Provider;

  /** the identity. */
  protected String m_Identity;

  /** the credential. */
  protected BasePassword m_Credential;

  /** the endpoint. */
  protected BaseURL m_Endpoint;

  /** the API class. */
  protected BaseClassname m_APIClass;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Contains parameters for a JClouds connection.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
      "provider", "provider",
      JClouds.getProvider());

    m_OptionManager.add(
      "identity", "identity",
      JClouds.getIdentity());

    m_OptionManager.add(
      "credential", "credential",
      JClouds.getCredential());

    m_OptionManager.add(
      "endpoint", "endpoint",
      JClouds.getEndpoint());

    m_OptionManager.add(
      "api-class", "APIClass",
      new BaseClassname(Object.class));
  }

  /**
   * Sets the cloud provider to use.
   *
   * @param value	the provider
   */
  public void setProvider(String value) {
    m_Provider = value;
    reset();
  }

  /**
   * Returns the cloud provider to use.
   *
   * @return		the provider
   */
  public String getProvider() {
    return m_Provider;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String providerTipText() {
    return "The cloud provider to use.";
  }

  /**
   * Sets the identity to use.
   *
   * @param value	the secret
   */
  public void setIdentity(String value) {
    m_Identity = value;
    reset();
  }

  /**
   * Returns the identity to use.
   *
   * @return		the identity
   */
  public String getIdentity() {
    return m_Identity;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String identityTipText() {
    return "The identity to use (tenantName:userName).";
  }

  /**
   * Sets the credential to use.
   *
   * @param value	the password
   */
  public void setCredential(BasePassword value) {
    m_Credential = value;
    reset();
  }

  /**
   * Returns the credential to use.
   *
   * @return		the password
   */
  public BasePassword getCredential() {
    return m_Credential;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String credentialTipText() {
    return "The password to use for the connection.";
  }

  /**
   * Sets the API endpoint to use.
   *
   * @param value	the URL
   */
  public void setEndpoint(BaseURL value) {
    m_Endpoint = value;
    reset();
  }

  /**
   * Returns the API endpoint to use.
   *
   * @return		the URL
   */
  public BaseURL getEndpoint() {
    return m_Endpoint;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String endpointTipText() {
    return "The URL for the API endpoint.";
  }

  /**
   * Sets the API class to use.
   *
   * @param value	the class
   */
  public void setAPIClass(BaseClassname value) {
    m_APIClass = value;
    reset();
  }

  /**
   * Returns the API class to use.
   *
   * @return		the class
   */
  public BaseClassname getAPIClass() {
    return m_APIClass;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String APIClassTipText() {
    return "The API class for the provider.";
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    String	result;

    result  = QuickInfoHelper.toString(this, "provider", m_Provider, "provider: ");
    result += QuickInfoHelper.toString(this, "identity", m_Identity, ", identity: ");
    result += QuickInfoHelper.toString(this, "endpoint", m_Endpoint, ", endpoint: ");
    result += QuickInfoHelper.toString(this, "apiClass", m_APIClass, ", API: ");

    return result;
  }

  /**
   * Assembles a context builder.
   *
   * @return		the builder
   */
  public ContextBuilder getContextBuilder() {
    ContextBuilder 	result;
    Iterable<Module> 	modules;
    
    result = ContextBuilder.newBuilder(m_Provider);
    result.endpoint(m_Endpoint.getValue());
    result.credentials(m_Identity, m_Credential.getValue());
    if (isLoggingEnabled()) {
      modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
      result.modules(modules);
    }

    return result;
  }

  /**
   * Builds the API.
   *
   * @return		the API
   */
  public Object buildAPI() {
    return getContextBuilder().buildApi(m_APIClass.classValue());
  }

  /**
   * Executes the flow item.
   *
   * @return		always null
   */
  @Override
  protected String doExecute() {
    return null;
  }
}
