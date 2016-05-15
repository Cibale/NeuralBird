package hr.fer.zemris.game.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import hr.fer.zemris.game.components.IComponent;
import hr.fer.zemris.game.components.bird.Bird;
import hr.fer.zemris.game.components.ground.Ground;
import hr.fer.zemris.game.components.pipes.PipePair;
import hr.fer.zemris.game.components.reward.Reward;
import hr.fer.zemris.game.environment.Constants;
import hr.fer.zemris.game.physics.Physics;
import hr.fer.zemris.util.RandomProvider;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * Model which represents world of this game.<br>
 * Model contains implementation of the collaboration between {@link Bird}, {@link PipePair}s,
 * {@link Ground} and {@link Reward}.<br>
 * Model's runtime is powered by {@linkplain update} method.
 *
 * @author Jure Cular and Boris Generalic
 *
 */
public abstract class GameModel {

    public Dimension2D dimension;

	protected Dimension2D gameDimension;

	{
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		dimension = new Dimension2D(bounds.getWidth(), bounds.getHeight());
		gameDimension = new Dimension2D(dimension.getWidth(), dimension.getHeight() - dimension.getHeight() / 8);
	}

    protected Random random = RandomProvider.get();

    protected Bird bird;

	protected BooleanProperty jump = new SimpleBooleanProperty(false);

	protected Constants constants;

    protected LinkedList<PipePair> pipesPairs = new LinkedList<>();

	protected PipePair nearestPipePair;

	protected PipePair lastPassed;

    protected LinkedList<Reward> rewards = new LinkedList<>();

    protected LinkedList<Ground> grounds = new LinkedList<>();

	protected Group group = new Group();

	protected IntegerProperty score = new SimpleIntegerProperty(0);

	private IntegerProperty numberOfPassedPipes = new SimpleIntegerProperty(0);

	protected BooleanProperty traceable = new SimpleBooleanProperty(false);

	public GameModel() {
        initModel();
    }

	/**
	 * Initializes model.
	 */
    private void initModel() {
    	constants = Constants.currentConstants;
    	initaliseBird();
        initialiseEnvironment();
        lastPassed = getNearestPairAheadOfBird().get();
    }

    /**
     * Returns {@link Pane} which contains all the components of the model.
     *
     * @return	{@link Pane} which contains all the components of the model
     */
	public Pane getGamePane() {
        group.getChildren().add(bird);
        group.getChildren().addAll(pipesPairs);
        group.getChildren().addAll(rewards);
        group.getChildren().addAll(grounds);

        Pane gamePane = new Pane(group);
        gamePane.setPrefWidth(dimension.getWidth());
        gamePane.setPrefHeight(dimension.getHeight());

        return gamePane;
    }

	/**
	 * Resets the model.
	 */
    public void reset() {
        group.getChildren().clear();
        pipesPairs.clear();
        rewards.clear();

        initModel();
    }

    /**
     * Creates {@link Bird} used in model.
     */
	protected void initaliseBird() {
		this.bird = new Bird(gameDimension.getWidth() / 3, gameDimension.getHeight() / 2);
	}

	/**
	 * Creates {@link PipePair}s, {@link Reward}s, and {@link Ground}.
	 */
    protected void initialiseEnvironment() {
		setupPipesAndRewards();
		setupGround();
    }

    /**
     * Creates {@link PipePair}s and {@link Reward}s.
     */
	private void setupPipesAndRewards() {
		double nextPipeX = gameDimension.getWidth() + constants.INITIAL_PIPE_OFFSET.get();
		double nextRewardCenterX = nextPipeX + constants.PIPE_WIDTH.get() + constants.PIPE_GAP_X.get() / 2;
		for (int i = 0; i < constants.NUMBER_OF_PIPES.get(); i++) {
			nextPipeX = initialisePipePair(nextPipeX);
			nextRewardCenterX = initialiseReward(nextRewardCenterX);
		}
	}

	/**
	 * Creates {@link Ground}.
	 */
	protected void setupGround() {
		double nextGroundX = 0;
		for (int i = 0; i < constants.NUMBER_OF_GROUNDS.get(); i++) {
			nextGroundX = initialiseGround(nextGroundX);
		}
	}

	/**
	 * Abstract class which is a template for intializing classes which implement
	 * {@link IComponent} interface.
	 * Based on Template Method design pattern.
	 *
	 * @author Boris Generalic
	 *
	 * @param <T> type of the component
	 */
	private abstract class AbstractInitialiser<T extends IComponent> {

        private List<T> components;

        public AbstractInitialiser(List<T> components) {
            super();
            this.components = components;
        }

        /**
         * Method which creates next component and sets it on {@linkplain nextComponentX}
         * x-coordinate increments it for fixed size.
         *
         * @param nextComponentX	x-coordinate where component will be created
         * @return	x-coordinate for next component which will be created
         */
        public final double initialiseComponent(double nextComponentX) {
            T c = createComponent(nextComponentX);
            nextComponentX = calculateOffset(c);
            components.add(c);
            return nextComponentX;
        }

        /**
         * Creates component.
         * @param nextComponentX	given x-coordinate
         * @return	return created component
         */
        protected abstract T createComponent(double nextComponentX);

        /**
         * Calculates x-coordinate for next component.
         * @param component current component
         * @return	x-coordinate for next component
         */
        protected abstract double calculateOffset(T component);

    }

	/**
	 * Creates new {@link PipePair}.
	 *
	 * @param nextPipeX	x-coordinate for created {@link PipePair}.
	 *
	 * @return	x-coordinate for next {@link PipePair}
	 */
    private double initialisePipePair(double nextPipeX) {
        return new AbstractInitialiser<PipePair>(pipesPairs) {

            @Override
            protected PipePair createComponent(double nextComponentX) {
                return new PipePair(nextPipeX, constants.PIPE_GAP_Y.get(), constants.PIPE_WIDTH.get(), gameDimension.getHeight());
            }

            @Override
            protected double calculateOffset(PipePair component) {
                return component.getRightMostX() + constants.PIPE_GAP_X.get();
            }

        }.initialiseComponent(nextPipeX);
    }

    /**
	 * Creates new {@link Reward}.
	 *
	 * @param nextRewardCenterX	x-coordinate for created {@link Reward}.
	 *
	 * @return	x-coordinate for next {@link Reward}
	 */
    private double initialiseReward(double nextRewardCenterX) {
        return new AbstractInitialiser<Reward>(rewards) {

            @Override
            protected Reward createComponent(double nextComponentX) {
                return new Reward(nextRewardCenterX, gameDimension.getHeight());
            }

            @Override
            protected double calculateOffset(Reward component) {
                return component.getCenterX() + constants.REWARD_GAP_X.get();
            }

        }.initialiseComponent(nextRewardCenterX);
    }

    /**
	 * Creates new {@link Ground}.
	 *
	 * @param nextGroundX	x-coordinate for created {@link Ground}.
	 *
	 * @return	x-coordinate for next {@link Ground}
	 */
	protected double initialiseGround(double nextGroundX) {
        return new AbstractInitialiser<Ground>(grounds) {

            @Override
            protected Ground createComponent(double nextComponentX) {
                return new Ground(nextGroundX, gameDimension.getHeight());
            }

            @Override
            protected double calculateOffset(Ground component) {
                return component.getRightMostX();
            }

        }.initialiseComponent(nextGroundX);
    }

	/**
	 * Abstract class which is a template for moving classes on x-axis which implement
	 * {@link IComponent} interface.
	 * Based on Template Method design pattern.
	 *
	 * @author Boris Generalic
	 *
	 * @param <T> type of the component
	 */
    private abstract class AbstractMover<T extends IComponent> {

		private LinkedList<T> components;

        public AbstractMover(LinkedList<T> components) {
            super();
            this.components = components;
        }

        /**
         * Method which calculates for given time interval how much a component
         * will be moved, and it moves it.
         *
         * @param time	time interval
         */
        public final void move(int time) {
            components.forEach(this::translate);

            T first = components.peekFirst();
			if(Objects.isNull(first)) {
				return;
			}
            if (first.getRightMostX() < 0) {
                T last = components.peekLast();
                putFirstBehindLast(first, last);
                components.addLast(components.removeFirst());
            }
        }

        /**
         * Translates given component.
         *
         * @param component	component which will be translated.
         */
        protected abstract void translate(T component);

        /**
         * Puts first component behind last one in queue when first component gets
         * behing screen bounds.
         *
         * @param first
         * @param last
         */
        protected abstract void putFirstBehindLast(T first, T last);

    }

    /**
     * Moves {@link PipePair}s.
     *
     * @param time given time interval
     */
    private void movePipes(int time) {
        new AbstractMover<PipePair>(pipesPairs) {

            @Override
            protected void translate(PipePair component) {
                double shiftX = Physics.calculateShiftX(constants.PIPES_SPEED_X.get(), time);
                component.translate(shiftX);
                double shiftY = Physics.calculateShiftX(constants.PIPES_SPEED_Y.get(), time);
                component.setPairYPosition(shiftY);
            }

            @Override
            protected void putFirstBehindLast(PipePair first, PipePair last) {
                first.setPairXPosition(last.getRightMostX() + constants.PIPE_GAP_X.get());
                first.randomizeYPositions();
            }

        }.move(time);
    }

    /**
     * Moves {@link Reward}s.
     *
     * @param time given time interval
     */
    private void moveRewards(int time) {
        new AbstractMover<Reward>(rewards) {

            @Override
            protected void translate(Reward component) {
                double shiftX = Physics.calculateShiftX(constants.REWARD_SPEED_X.get(), time);
                component.translate(shiftX);
                component.updateFrame();
            }

            @Override
            protected void putFirstBehindLast(Reward first, Reward last) {
                first.setCenterX(last.getCenterX() + constants.REWARD_GAP_X.get());
                first.randomizeYPosition();
                first.setVisible(random.nextDouble() < constants.REWARD_PROBABILITY.get());
            }

        }.move(time);
    }

    /**
     * Moves {@link Ground}.
     *
     * @param time given time interval
     */
    protected void moveGround(int time) {
        new AbstractMover<Ground>(grounds) {

            @Override
            protected void translate(Ground component) {
                double shiftX = Physics.calculateShiftX(constants.PIPES_SPEED_X.get(), time);
                component.translate(shiftX);
            }

            @Override
            protected void putFirstBehindLast(Ground first, Ground last) {
                first.setX(last.getRightMostX());
            }

        }.move(time);
    }

    /**
     * Moves {@link Bird}.
     *
     * @param time given time interval
     */
    private void moveBird(int time) {
        if (jump.get()) {
            double shiftY = Physics.calculateShiftY(constants.JUMP_SPEED.negate().get(), time);
            bird.setCurrentVelocity(constants.JUMP_SPEED.negate().get());
            bird.setCenterY(bird.getCenterY() + shiftY);
            jump.set(false);
        } else {
            double shiftY = Physics.calculateShiftY(bird.getCurrentVelocity(), time);
            bird.setCurrentVelocity(Physics.calculateVelocity(bird.getCurrentVelocity(), time));
            bird.setCenterY(bird.getCenterY() + shiftY);
        }
        bird.updateFrame();
    }

    /**
     * Checks for collisions.<br>
     * Moves all the components contained in the model.<br>
     * Updates score.<br>
     * Performs scan of the environment.
     *
     * @param time	given time interval
     *
     * @return	{@code false} if intersection has occured, {@code true} otherwise.
     */
    public boolean update(int time) {
        if (!constants.GOD_MODE.get() && checkCollisions()) {
            return false;
        }

		refreshScore();

        movePipes(time);
        moveRewards(time);
        moveBird(time);
		moveGround(time);

		scanEnvironment();

        return true;
    }

    /**
     * Refreshes score.
     */
	private void refreshScore() {
		if (isRewardCollected()) {
			score.set(score.get() + constants.REWARD_COLLECTED_BONUS.get());
		}

		nearestPipePair = getNearestPairAheadOfBird().get();
		if (!nearestPipePair.equals(lastPassed)) {
			score.set(score.get() + constants.PIPE_PASSED_BONUS.get());
			numberOfPassedPipes.set(numberOfPassedPipes.get() + 1);
			lastPassed = nearestPipePair;
		}
	}

	/**
	 * Method is used to get information about the current environment status.
	 */
	protected abstract void scanEnvironment();

	/**
	 * Returns {@code true} if reward is collected, {@code false} otherwise.
	 *
	 * @return	{@code true} if reward is collected, {@code false} otherwise
	 */
	private boolean isRewardCollected() {
        return rewards.stream()
        		.filter(r -> r.intersects(bird))
        		.peek(r -> r.setVisible(false))
        		.findAny()
        		.isPresent();
    }

	/**
	 * Returns {@code true} if reward there are collisions, {@code false} otherwise.
	 *
	 * @return	{@code true} if reward there are collisions, {@code false} otherwise
	 */
	private boolean checkCollisions() {
        boolean intersection = pipesPairs.stream()
        		.filter(p -> p.intersects(bird))
        		.findAny()
        		.isPresent();
        return intersection || isBirdOutOfBounds();
    }

	/**
	 * Returns {@code true} if {@link Bird} is out of the screen bounds, {@code false} otherwise.
	 *
	 * @return	{@code true} if {@link Bird} is out of the screen bounds, {@code false} otherwise
	 */
    private boolean isBirdOutOfBounds() {
		Bounds birdBounds = bird.getBoundsInParent();
        return birdBounds.getMaxY() > gameDimension.getHeight() || birdBounds.getMinY() < 0;
    }

    /**
     * Vraca dvije cijevi(par).<br>
     * Prva(index = 0) je ona gore, druga(index = 1) je ona dolje.
     *
     * @param
     * @return
     */
    // TO BUDEMO KORISTILI ZA GLEDANJE DI JE KOJA CIJEV KOD UČENJA MREZE
//    private Optional<PipePair> getNearestPairAheadOfBird() {
//        return getNearestComponentAheadOfBird(pipesPairs)
//        		.findFirst();
//    }
    protected Optional<PipePair> getNearestPairAheadOfBird() {
    	return pipesPairs.stream()
    			.filter(p -> p.getRightMostX() > bird.getLeftMostX())
        		.sorted()
        		.findFirst();
    }

	public void jumpBird() {
		jump.set(true);
	}

	public BooleanProperty jumpProperty() {
		return jump;
	}

	public int getScore() {
		return score.get();
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public int getNumberOfPassedPipes() {
		return numberOfPassedPipes.get();
	}

	public BooleanProperty traceableProperty() {
		return traceable;
	}

}
