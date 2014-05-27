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
 * MekaEditorsRegistration.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.gui.goe;

/**
 * Registers the MEKA GenericObjectEditor editors.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 4584 $
 */
public class MekaEditorsRegistration
  extends AbstractEditorRegistration {

  /** for serialization. */
  private static final long serialVersionUID = -2908979337117222215L;

  /** whether registration already occurred. */
  protected static boolean m_Registered;

  /**
   * Returns whether registration already occurred.
   *
   * @return		true if registration already occurred
   */
  @Override
  protected boolean hasRegistered() {
    return m_Registered;
  }

  /**
   * Performs the registration of the editors.
   *
   * @return		true if registration successful
   */
  @Override
  protected boolean doRegister() {
    meka.gui.goe.GenericObjectEditor.determineAllClasses();
    meka.gui.goe.GenericObjectEditor.registerAllEditors();
    m_Registered = true;
    return true;
  }
}
