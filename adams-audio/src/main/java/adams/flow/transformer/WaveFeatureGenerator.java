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
 * WaveFeatureGenerator.java
 * Copyright (C) 2011-2014 University of Waikato, Hamilton, New Zealand
 */

package adams.flow.transformer;

import adams.core.QuickInfoHelper;
import adams.data.audio.WaveContainer;
import adams.data.audiofeaturegenerator.wave.AbstractWaveFeatureGenerator;
import adams.data.audiofeaturegenerator.wave.Fingerprint;
import adams.flow.core.Token;
import adams.flow.provenance.ActorType;
import adams.flow.provenance.Provenance;
import adams.flow.provenance.ProvenanceContainer;
import adams.flow.provenance.ProvenanceInformation;
import adams.flow.provenance.ProvenanceSupporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 <!-- globalinfo-start -->
 <!-- globalinfo-end -->
 *
 <!-- flow-summary-start -->
 <!-- flow-summary-end -->
 *
 <!-- options-start -->
 <!-- options-end -->
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class WaveFeatureGenerator
  extends AbstractTransformer
  implements ProvenanceSupporter {

  /** for serialization. */
  private static final long serialVersionUID = -1998955116780561587L;

  /** the key for storing the current objects in the backup. */
  public final static String BACKUP_QUEUE = "queue";

  /** the algorithm to apply to the image. */
  protected AbstractWaveFeatureGenerator m_Algorithm;

  /** the generated objects. */
  protected ArrayList m_Queue;

  /**
   * Returns a string describing the object.
   *
   * @return 			a description suitable for displaying in the gui
   */
  @Override
  public String globalInfo() {
    return
        "Applies an Wave feature generator to the incoming image and outputs "
      + "the generated features.";
  }

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "algorithm", "algorithm",
	    new Fingerprint());
  }

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();
    
    m_Queue = new ArrayList();
  }
  
  /**
   * Resets the scheme.
   */
  @Override
  protected void reset() {
    super.reset();
    
    m_Queue.clear();
  }

  /**
   * Sets the algorithm to use.
   *
   * @param value	the algorithm
   */
  public void setAlgorithm(AbstractWaveFeatureGenerator value) {
    m_Algorithm = value;
    reset();
  }

  /**
   * Returns the algorithm in use.
   *
   * @return		the algorithm
   */
  public AbstractWaveFeatureGenerator getAlgorithm() {
    return m_Algorithm;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String algorithmTipText() {
    return "The feature generation algorithm to use.";
  }

  /**
   * Returns a quick info about the actor, which will be displayed in the GUI.
   *
   * @return		null if no info available, otherwise short string
   */
  @Override
  public String getQuickInfo() {
    return QuickInfoHelper.toString(this, "algorithm", m_Algorithm);
  }

  /**
   * Removes entries from the backup.
   */
  @Override
  protected void pruneBackup() {
    super.pruneBackup();

    pruneBackup(BACKUP_QUEUE);
  }

  /**
   * Backs up the current state of the actor before update the variables.
   *
   * @return		the backup
   */
  @Override
  protected Hashtable<String,Object> backupState() {
    Hashtable<String,Object>	result;

    result = super.backupState();

    result.put(BACKUP_QUEUE, m_Queue);

    return result;
  }

  /**
   * Restores the state of the actor before the variables got updated.
   *
   * @param state	the backup of the state to restore from
   */
  @Override
  protected void restoreState(Hashtable<String,Object> state) {
    if (state.containsKey(BACKUP_QUEUE)) {
      m_Queue = (ArrayList) state.get(BACKUP_QUEUE);
      state.remove(BACKUP_QUEUE);
    }

    super.restoreState(state);
  }

  /**
   * Returns the class that the consumer accepts.
   *
   * @return		the Class of objects that can be processed
   */
  public Class[] accepts() {
    return new Class[]{WaveContainer.class};
  }

  /**
   * Returns the class of objects that it generates.
   *
   * @return		the Class of objects that get generated
   */
  public Class[] generates() {
    if (m_Algorithm == null)
      return new Class[]{Object.class};
    else
      return new Class[]{m_Algorithm.getRowFormat()};
  }

  /**
   * Executes the flow item.
   *
   * @return		null if everything is fine, otherwise error message
   */
  @Override
  protected String doExecute() {
    String		result;
    WaveContainer	cont;

    result = null;

    m_Queue.clear();
    try {
      cont = m_InputToken.getPayload(WaveContainer.class);
      m_Queue.addAll(Arrays.asList(m_Algorithm.generate(cont)));
    }
    catch (Exception e) {
      result = handleException("Failed to generate features: ", e);
    }

    return result;
  }

  /**
   * Updates the provenance information in the provided container.
   *
   * @param cont	the provenance container to update
   */
  public void updateProvenance(ProvenanceContainer cont) {
    if (Provenance.getSingleton().isEnabled()) {
      if (m_InputToken.hasProvenance())
	cont.setProvenance(m_InputToken.getProvenance().getClone());
      cont.addProvenance(new ProvenanceInformation(ActorType.PREPROCESSOR, m_InputToken.getPayload().getClass(), this, ((Token) cont).getPayload().getClass()));
    }
  }

  /**
   * Checks whether there is pending output to be collected after
   * executing the flow item.
   *
   * @return		true if there is pending output
   */
  @Override
  public boolean hasPendingOutput() {
    return (m_Queue.size() > 0);
  }

  /**
   * Returns the generated token.
   *
   * @return		the generated token
   */
  @Override
  public Token output() {
    Token	result;

    result = new Token(m_Queue.get(0));
    m_Queue.remove(0);

    updateProvenance(result);

    return result;
  }

  /**
   * Cleans up after the execution has finished.
   */
  @Override
  public void wrapUp() {
    m_Queue.clear();

    super.wrapUp();
  }
}
