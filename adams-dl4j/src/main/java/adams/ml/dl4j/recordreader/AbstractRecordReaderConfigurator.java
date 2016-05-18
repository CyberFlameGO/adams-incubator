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
 * AbstractRecordReaderConfigurator.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package adams.ml.dl4j.recordreader;

import adams.core.option.AbstractOptionHandler;
import org.canova.api.records.reader.RecordReader;

/**
 * Ancestor for record reader configurators.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractRecordReaderConfigurator
  extends AbstractOptionHandler
  implements RecordReaderConfigurator {

  private static final long serialVersionUID = -5049221729823530346L;

  /**
   * Hook method before configuring the record reader.
   * <br>
   * Default implementation does nothing.
   *
   * @return		null if successful, otherwise error message
   */
  protected String check() {
    return null;
  }

  /**
   * Configures the actual {@link RecordReader} and returns it.
   *
   * @return		the reader
   */
  protected abstract RecordReader doConfigureRecordReader();

  /**
   * Configures the {@link RecordReader} and returns it.
   *
   * @return		the reader
   */
  public RecordReader configureRecordReader() {
    check();
    return doConfigureRecordReader();
  }
}
