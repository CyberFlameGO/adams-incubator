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

/**
 * OpenCVFeatureGenerator.java
 * Copyright (C) 2014-2015 University of Waikato, Hamilton, New Zealand
 */
package adams.gui.visualization.image.plugins;

import adams.core.option.OptionUtils;
import adams.data.featureconverter.SpreadSheet;
import adams.data.opencv.OpenCVHelper;
import adams.data.opencv.OpenCVImageContainer;
import adams.data.opencv.features.AbstractOpenCVFeatureGenerator;
import adams.data.spreadsheet.Row;

import java.awt.image.BufferedImage;

/**
 * Allows the user to apply a OpenCV feature generator to an image in the ImageViewer.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 7713 $
 */
public class OpenCVFeatureGenerator
  extends AbstractSelectedImagesFeatureGenerator {

  /** for serialization. */
  private static final long serialVersionUID = -3146372359577147914L;

  /**
   * Returns the text for the menu to place the plugin beneath.
   *
   * @return		the menu
   */
  @Override
  public String getMenu() {
    return "Features";
  }

  /**
   * Returns the text for the menu item to create.
   *
   * @return		the text
   */
  @Override
  public String getCaption() {
    return "OpenCV feature generator...";
  }

  /**
   * Returns the icon name.
   *
   * @return		the name, null if none available
   */
  @Override
  public String getIconName() {
    return "opencv.png";
  }

  /**
   * Returns the class to use as type (= superclass) in the GOE.
   * 
   * @return		the class
   */
  @Override
  protected Class getEditorType() {
    return AbstractOpenCVFeatureGenerator.class;
  }
  
  /**
   * Returns the default object to use in the GOE if no last setup is yet
   * available.
   * 
   * @return		the object
   */
  @Override
  protected Object getDefaultValue() {
    return new adams.data.opencv.features.Pixels();
  }

  /**
   * Creates the log message.
   * 
   * @return		the message, null if none available
   */
  @Override
  protected String createLogEntry() {
    return getClass().getSimpleName() + ": " + OptionUtils.getCommandLine(m_Editor.getValue());
  }

  /**
   * Filters the image.
   *
   * @param image	the image to filter
   * @return		the generated instances
   */
  @Override
  protected Row[] generateFeatures(BufferedImage image) {
    Row[]				result;
    AbstractOpenCVFeatureGenerator	generator;
    OpenCVImageContainer		input;
    Row[]				features;

    result = null;

    setLastSetup(m_Editor.getValue());
    generator = (AbstractOpenCVFeatureGenerator) m_Editor.getValue();
    generator.setConverter(new SpreadSheet());
    input     = OpenCVHelper.toOpenCVImageContainer(image);
    features  = (Row[]) generator.generate(input);
    if (features.length == 0)
      m_FilterError = "No features generated!";
    if (features.length > 0)
      result = features;

    return result;
  }
}
