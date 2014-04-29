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
 * TumblrPostConverter.java
 * Copyright (C) 2013 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.transformer;

import adams.core.DateFormat;
import adams.core.QuickInfoHelper;
import adams.core.TechnicalInformation;
import adams.core.TechnicalInformationHandler;
import adams.data.tumblr.AbstractTumblrPostConverter;
import adams.data.tumblr.TextConverter;
import adams.flow.core.Token;
import adams.flow.core.Unknown;

import com.tumblr.jumblr.types.Post;

/**
 <!-- globalinfo-start -->
 * Turns a Tumblr post into different representation.<br/>
 * For more information on the date format, see:<br/>
 * Javadoc. java.text.SimpleDateFormat.
 * <p/>
 <!-- globalinfo-end -->
 *
 <!-- technical-bibtex-start -->
 * <pre>
 * &#64;misc{missing_id,
 *    author = {Javadoc},
 *    title = {java.text.SimpleDateFormat},
 *    HTTP = {http:&#47;&#47;download.oracle.com&#47;javase&#47;1,5.0&#47;docs&#47;api&#47;java&#47;text&#47;SimpleDateFormat.html}
 * }
 * </pre>
 * <p/>
 <!-- technical-bibtex-end -->
 *
 <!-- flow-summary-start -->
 * Input&#47;output:<br/>
 * - accepts:<br/>
 * &nbsp;&nbsp;&nbsp;com.tumblr.jumblr.types.Post<br/>
 * - generates:<br/>
 * &nbsp;&nbsp;&nbsp;java.lang.String<br/>
 * <p/>
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
 * &nbsp;&nbsp;&nbsp;default: TumblrConverter
 * </pre>
 * 
 * <pre>-annotation &lt;adams.core.base.BaseText&gt; (property: annotations)
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
 * <pre>-converter &lt;adams.data.tumblr.AbstractTumblrPostConverter&gt; (property: converter)
 * &nbsp;&nbsp;&nbsp;The converter to use.
 * &nbsp;&nbsp;&nbsp;default: adams.data.tumblr.TextConverter -separator \\t
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class TumblrPostConverter
  extends AbstractTransformer
  implements TechnicalInformationHandler {

  /** for serialization. */
  private static final long serialVersionUID = -4249772734326614365L;

  /** the converter to use. */
  protected AbstractTumblrPostConverter m_Converter;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return
        "Turns a Tumblr post into different representation.\n"
      + "For more information on the date format, see:\n"
      + getTechnicalInformation().toString();
  }

  /**
   * Returns an instance of a TechnicalInformation object, containing
   * detailed information about the technical background of this class,
   * e.g., paper reference or book this class is based on.
   *
   * @return the technical information about this class
   */
  public TechnicalInformation getTechnicalInformation() {
    return new DateFormat().getTechnicalInformation();
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "converter", "converter",
	    new TextConverter());
  }

  /**
   * Sets the converter to use.
   *
   * @param value	the converter
   */
  public void setConverter(AbstractTumblrPostConverter value) {
    m_Converter = value;
    reset();
  }

  /**
   * Returns the converter to use.
   *
   * @return		the converter
   */
  public AbstractTumblrPostConverter getConverter() {
    return m_Converter;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String converterTipText() {
    return "The converter to use.";
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    return QuickInfoHelper.toString(this, "converter", m_Converter);
  }

  /**
   * Returns the class that the consumer accepts.
   *
   * @return		<!-- flow-accepts-start -->com.tumblr.jumblr.types.Post.class<!-- flow-accepts-end -->
   */
  public Class[] accepts() {
    return new Class[]{Post.class};
  }

  /**
   * Returns the class of objects that it generates.
   *
   * @return		<!-- flow-generates-start -->java.lang.String.class<!-- flow-generates-end -->
   */
  public Class[] generates() {
    if (m_Converter != null)
      return new Class[]{m_Converter.generates()};
    else
      return new Class[]{Unknown.class};
  }

  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    String	result;

    result = null;

    try {
      m_OutputToken = new Token(m_Converter.convert((Post) m_InputToken.getPayload()));
    }
    catch (Exception e) {
      result = handleException("Failed to process post update!", e);
    }

    return result;
  }
}
