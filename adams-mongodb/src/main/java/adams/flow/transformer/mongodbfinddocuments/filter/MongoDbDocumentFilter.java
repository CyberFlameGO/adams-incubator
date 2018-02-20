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
 * MongoDbDocumentFilter.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package adams.flow.transformer.mongodbfinddocuments.filter;

import adams.core.QuickInfoSupporter;
import adams.core.option.OptionHandler;
import org.bson.conversions.Bson;

/**
 * Interface for filters.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface MongoDbDocumentFilter
  extends OptionHandler, QuickInfoSupporter {

  /**
   * Configures the filter.
   *
   * @return		the filter
   */
  public Bson configure();
}
