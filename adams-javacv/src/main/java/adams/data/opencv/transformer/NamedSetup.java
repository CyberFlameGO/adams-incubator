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
 * NamedSetup.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */

package adams.data.opencv.transformer;

import adams.data.opencv.OpenCVImageContainer;

/**
 <!-- globalinfo-start -->
 * Applies a OpenCV transformer that is referenced via its global setup name (see 'NamedSetups').
 * <p/>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p/>
 * 
 * <pre>-logging-level &lt;OFF|SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST&gt; (property: loggingLevel)
 * &nbsp;&nbsp;&nbsp;The logging level for outputting errors and debugging output.
 * &nbsp;&nbsp;&nbsp;default: WARNING
 * </pre>
 * 
 * <pre>-setup &lt;adams.core.NamedSetup&gt; (property: setup)
 * &nbsp;&nbsp;&nbsp;The name of the setup to use.
 * &nbsp;&nbsp;&nbsp;default: name_of_setup
 * </pre>
 * 
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 9237 $
 */
public class NamedSetup
  extends AbstractOpenCVTransformer {

  /** for serialization. */
  private static final long serialVersionUID = 7793661256600883689L;

  /** the name of the setup to load. */
  protected adams.core.NamedSetup m_Setup;

  /** the actual scheme. */
  protected AbstractOpenCVTransformer m_ActualScheme;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return "Applies a OpenCV transformer that is referenced via its global setup name (see 'NamedSetups').";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "setup", "setup",
	    new adams.core.NamedSetup());
  }

  /**
   * Resets the filter.
   */
  @Override
  public void reset() {
    super.reset();

    m_ActualScheme = null;
  }

  /**
   * Sets the setup name.
   *
   * @param value	the name
   */
  public void setSetup(adams.core.NamedSetup value) {
    m_Setup = value;
    if (!m_Setup.isDummy() && !m_Setup.exists())
      getLogger().severe("Warning: named setup '" + m_Setup + "' unknown!");
    reset();
  }

  /**
   * Returns the setup name.
   *
   * @return		the name
   */
  public adams.core.NamedSetup getSetup() {
    return m_Setup;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the gui
   */
  public String setupTipText() {
    return "The name of the setup to use.";
  }

  /**
   * Returns the named setup.
   *
   * @return		the actual scheme to use
   */
  protected AbstractOpenCVTransformer getActualScheme() {
    if (m_ActualScheme == null) {
      m_ActualScheme = (AbstractOpenCVTransformer) m_Setup.getSetup();
      if (m_ActualScheme == null)
	throw new IllegalStateException(
	    "Failed to instantiate named setup '" + m_Setup + "'!");
    }

    return m_ActualScheme;
  }

  /**
   * Performs the transformation.
   *
   * @param img		the image to process (can be modified, since it is a copy)
   * @return		the copy of the image
   */
  @Override
  protected OpenCVImageContainer[] doTransform(OpenCVImageContainer img) {
    return getActualScheme().transform(img);
  }
}
