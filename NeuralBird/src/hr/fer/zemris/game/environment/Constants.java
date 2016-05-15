package hr.fer.zemris.game.environment;

import hr.fer.zemris.game.model.GameModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class which represents constants which are used in {@link GameModel}.
 *
 * @author Damir Kopljar, Boris Generalic, Jure Cular and Martin Matak
 *
 */
public class Constants {

	public static Constants currentConstants = new Constants();
	public static final Constants DEFAULT_CONSTANTS = new Constants();

	public IntegerProperty NUMBER_OF_PIPES = new SimpleIntegerProperty(5);
	public IntegerProperty NUMBER_OF_GROUNDS = new SimpleIntegerProperty(20);
	public DoubleProperty PIPES_SPEED_X = new SimpleDoubleProperty(8);//settings screen
	public DoubleProperty REWARD_SPEED_X = new SimpleDoubleProperty(PIPES_SPEED_X.get());
	public DoubleProperty PIPES_SPEED_Y = new SimpleDoubleProperty(4);//settings screen
	public DoubleProperty JUMP_SPEED = new SimpleDoubleProperty(20);//settings screen
	public DoubleProperty PIPE_GAP_X = new SimpleDoubleProperty(350);//settings screen
	public DoubleProperty PIPE_GAP_Y = new SimpleDoubleProperty(200);//settings screen
	public DoubleProperty PIPE_WIDTH = new SimpleDoubleProperty(70);//settings screen
	public DoubleProperty INITIAL_PIPE_OFFSET = new SimpleDoubleProperty(100);
	public DoubleProperty REWARD_GAP_X = new SimpleDoubleProperty(PIPE_GAP_X.get() + PIPE_WIDTH.get());
	public IntegerProperty PIPE_PASSED_BONUS = new SimpleIntegerProperty(1);
	public IntegerProperty REWARD_COLLECTED_BONUS = new SimpleIntegerProperty(10);
	public DoubleProperty REWARD_PROBABILITY = new SimpleDoubleProperty(0.5);//settings screen
	public DoubleProperty GRAVITY = new SimpleDoubleProperty(4.81);//settings screen
	public BooleanProperty GOD_MODE = new SimpleBooleanProperty(false);//settings screen

	{
		REWARD_SPEED_X.bind(PIPES_SPEED_X);
		REWARD_GAP_X.bind(PIPE_GAP_X.add(PIPE_WIDTH));
	}

	/**
	 * Switch to default settings.
	 */
	public static void resetToDefaultSettings() {
		currentConstants.NUMBER_OF_PIPES.set(DEFAULT_CONSTANTS.NUMBER_OF_PIPES.get());
		currentConstants.NUMBER_OF_GROUNDS.set(DEFAULT_CONSTANTS.NUMBER_OF_GROUNDS.get());
		currentConstants.PIPES_SPEED_X.set(DEFAULT_CONSTANTS.PIPES_SPEED_X.get());
		currentConstants.PIPES_SPEED_Y.set(DEFAULT_CONSTANTS.PIPES_SPEED_Y.get());
		currentConstants.JUMP_SPEED.set(DEFAULT_CONSTANTS.JUMP_SPEED.get());
		currentConstants.PIPE_GAP_X.set(DEFAULT_CONSTANTS.PIPE_GAP_X.get());
		currentConstants.PIPE_GAP_Y.set(DEFAULT_CONSTANTS.PIPE_GAP_Y.get());
		currentConstants.PIPE_WIDTH.set(DEFAULT_CONSTANTS.PIPE_WIDTH.get());
		currentConstants.INITIAL_PIPE_OFFSET.set(DEFAULT_CONSTANTS.INITIAL_PIPE_OFFSET.get());
		currentConstants.PIPE_PASSED_BONUS.set(DEFAULT_CONSTANTS.PIPE_PASSED_BONUS.get());
		currentConstants.REWARD_COLLECTED_BONUS.set(DEFAULT_CONSTANTS.REWARD_COLLECTED_BONUS.get());
		currentConstants.REWARD_PROBABILITY.set(DEFAULT_CONSTANTS.REWARD_PROBABILITY.get());
		currentConstants.GRAVITY.set(DEFAULT_CONSTANTS.GRAVITY.get());
		currentConstants.GOD_MODE.set(DEFAULT_CONSTANTS.GOD_MODE.get());
	}

}
