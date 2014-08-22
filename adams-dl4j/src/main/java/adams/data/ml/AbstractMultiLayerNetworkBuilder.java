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
 * AbstractMultiLayerNetworkBuilder.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package adams.data.ml;

import java.util.HashMap;
import java.util.Map;

import org.deeplearning4j.datasets.DataSet;
import org.deeplearning4j.nn.BaseMultiLayerNetwork;
import org.deeplearning4j.nn.BaseMultiLayerNetwork.Builder;
import org.deeplearning4j.nn.WeightInit;
import org.deeplearning4j.nn.activation.ActivationFunction;
import org.deeplearning4j.nn.activation.Null;

import adams.core.Utils;
import adams.core.base.BaseInteger;

/**
 * Ancestor for multi-layer neural network builder setups.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @param <T> the type of builder to configure
 */
public abstract class AbstractMultiLayerNetworkBuilder<T extends BaseMultiLayerNetwork>
  extends AbstractNetworkBuilder<Builder<T>, T> {

  /** for serialization. */
  private static final long serialVersionUID = 3279279215595856579L;

  /** the sizes of the hidden layers. */
  protected BaseInteger[] m_HiddenLayerSizes;

  /** how to initialize the hidden weights. */
  protected WeightInitialization[] m_HiddenWeightInit;

  /** the activation function. */
  protected ActivationFunction m_ActivationFunction;

  /** the output activation function. */
  protected ActivationFunction m_OutputActivationFunction;

  /** how to initialize the output weights. */
  protected WeightInitialization m_OutputWeightInit;

  /**
   * Adds options to the internal list of options.
   */
  @Override
  public void defineOptions() {
    super.defineOptions();

    m_OptionManager.add(
	    "activation-function", "activationFunction",
	    new Null());

    m_OptionManager.add(
	    "hidden-weight-init", "hiddenWeightInit",
	    new WeightInitialization[]{WeightInitialization.NONE});

    m_OptionManager.add(
	    "hidden-layer-sizes", "hiddenLayerSizes",
	    new BaseInteger[]{new BaseInteger(3)});

    m_OptionManager.add(
	    "output-weight-init", "outputWeightInit",
	    WeightInitialization.NONE);

    m_OptionManager.add(
	    "output-activation-function", "outputActivationFunction",
	    new Null());
  }

  /**
   * Returns the default momentum.
   *
   * @return		the default
   */
  @Override
  protected double getDefaultMomentum() {
    return 0.1;
  }

  /**
   * Sets the activation function.
   *
   * @param value	the activation function
   */
  public void setActivationFunction(ActivationFunction value) {
    m_ActivationFunction = value;
    reset();
  }

  /**
   * Returns the activation function.
   *
   * @return		the activation function
   */
  public ActivationFunction getActivationFunction() {
    return m_ActivationFunction;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String activationFunctionTipText() {
    return "The activation function to use.";
  }

  /**
   * Sets the hidden layer sizes.
   *
   * @param value	the layers
   */
  public void setHiddenLayerSizes(BaseInteger[] value) {
    m_HiddenLayerSizes = value;
    m_HiddenWeightInit = (WeightInitialization[]) Utils.adjustArray(m_HiddenWeightInit, m_HiddenLayerSizes.length, WeightInitialization.NONE);
    reset();
  }

  /**
   * Returns the hidden layer sizes.
   *
   * @return		the layers
   */
  public BaseInteger[] getHiddenLayerSizes() {
    return m_HiddenLayerSizes;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String hiddenLayerSizesTipText() {
    return "The hidden layer sizes; at least one layer and each layer must have at least 1 node.";
  }

  /**
   * Sets the weight initialization for the hidden layers.
   *
   * @param value	the weight initialization
   */
  public void setHiddenWeightInit(WeightInitialization[] value) {
    m_HiddenWeightInit = value;
    reset();
  }

  /**
   * Returns the weight initialization for the hidden layers.
   *
   * @return		the weight initialization
   */
  public WeightInitialization[] getHiddenWeightInit() {
    return m_HiddenWeightInit;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String hiddenWeightInitTipText() {
    return "The weight initialization for the hidden layers.";
  }

  /**
   * Sets the output activation function.
   *
   * @param value	the output activation function
   */
  public void setOutputActivationFunction(ActivationFunction value) {
    m_OutputActivationFunction = value;
    reset();
  }

  /**
   * Returns the output activation function.
   *
   * @return		the output activation function
   */
  public ActivationFunction getOutputActivationFunction() {
    return m_OutputActivationFunction;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String outputActivationFunctionTipText() {
    return "The output activation function to use.";
  }

  /**
   * Sets the weight initialization for the output.
   *
   * @param value	the weight initialization
   */
  public void setOutputWeightInit(WeightInitialization value) {
    m_OutputWeightInit = value;
    reset();
  }

  /**
   * Returns the weight initialization for the output.
   *
   * @return		the weight initialization
   */
  public WeightInitialization getOutputWeightInit() {
    return m_OutputWeightInit;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the GUI or for listing the options.
   */
  public String outputWeightInitTipText() {
    return "The weight initialization for the output.";
  }

  /**
   * Convencience method for turning the hidden layer sizes into an integer array.
   *
   * @return		the hidden layer sizes as int array
   */
  protected int[] hiddenLayerSizes() {
    int[]	result;
    int		i;

    result = new int[m_HiddenLayerSizes.length];
    for (i = 0; i < result.length; i++)
      result[i] = m_HiddenLayerSizes[i].intValue();

    return result;
  }

  /**
   * Convencience method for turning the hidden layer sizes into an integer array.
   *
   * @return		the hidden layer sizes as int array
   */
  protected Map<Integer,WeightInit> hiddenLayerWeightInits() {
    Map<Integer,WeightInit>	result;
    int				i;

    result = new HashMap<Integer,WeightInit>();
    for (i = 0; i < m_HiddenWeightInit.length; i++) {
      if (m_HiddenWeightInit[i] != WeightInitialization.NONE)
	result.put(i, m_HiddenWeightInit[i].getWeightInit());
    }

    return result;
  }

  /**
   * Performs checks before configuring the builder.
   * <p/>
   * Default implementation ensures that at least one hidden layer is defined
   * and that the hidden layers have at least 1 node each.
   *
   * @param data	the data to train with
   */
  @Override
  protected void check(DataSet data) {
    int		i;

    if (m_HiddenLayerSizes.length == 0)
      throw new IllegalStateException("No hidden layers defined");

    for (i = 0; i < m_HiddenLayerSizes.length; i++) {
      if (m_HiddenLayerSizes[i].intValue() < 1)
	throw new IllegalStateException("Hidden layer #" + (i+1) + " has less than 1 node!");
    }
  }

  /**
   * Performs the actual configuration.
   *
   * @param data	the data to train with
   * @return		the configured builder
   */
  @Override
  protected Builder<T> doConfigureNetwork(DataSet data) {
    Builder<T>	result;

    result = newBuilder();

    // general
    if (!(m_RandomNumberGenerator instanceof adams.data.random.Null))
      result.withRng(m_RandomNumberGenerator.getRandomGenerator());
    result.withMomentum(m_Momentum);
    result.withDist(m_Distribution.getRealDistribution());

    // input
    result.numberOfInputs(data.getFirst().getColumns());
    if (m_WeightInit != WeightInitialization.NONE)
      result.weightInit(m_WeightInit.getWeightInit());
    if (!(m_ActivationFunction instanceof org.deeplearning4j.nn.activation.Null))
      result.withActivation(m_ActivationFunction);

    // hidden
    result.hiddenLayerSizes(hiddenLayerSizes());
    result.weightInitByLayer(hiddenLayerWeightInits());

    // output
    result.numberOfOutPuts(data.getSecond().getColumns());
    if (!(m_OutputActivationFunction instanceof org.deeplearning4j.nn.activation.Null))
      result.withOutputActivationFunction(m_OutputActivationFunction);

    // TODO parameters

    return result;
  }

  /**
   * Configures the builder.
   *
   * @param data	the data to train with
   * @return		the builder
   */
  @Override
  public Builder<T> configureNetwork(DataSet data) {
    check(data);
    return doConfigureNetwork(data);
  }

  /**
   * Performs the actual generation of the network.
   *
   * @param builder	the builder to use
   * @param data	the data to train with
   * @return		the generated network
   */
  @Override
  protected T doGenerateNetwork(Builder<T> builder, DataSet data) {
    return builder.build();
  }
}
