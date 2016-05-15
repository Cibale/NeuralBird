package hr.fer.zemris.game.components.reward;

import hr.fer.zemris.game.components.IComponent;
import hr.fer.zemris.game.components.IIntersectionChecker;
import hr.fer.zemris.game.components.bird.Bird;
import hr.fer.zemris.game.model.GameModel;
import hr.fer.zemris.util.RandomProvider;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Class which represents an collectable reward (coin) in {@link GameModel}.
 *
 * @author Jure Cular and Boris Generalic
 *
 */
public class Reward extends Circle implements IComponent, IIntersectionChecker, Comparable<Reward> {

	private double height;

	private Random random = RandomProvider.get();

	private static final double REWARD_RADIUS = 20;
	private static final double MINIMUM_Y_OFFSET = 50;
	private static LinkedList<Image> rewardFrames = new LinkedList<>();

	static {
		loadRewardFrames();
	}

	public Reward(double rewardCenterX, double height) {
		this.height = height;

		setCenterX(rewardCenterX);
		setRadius(REWARD_RADIUS);
		setVisible(false);

		randomizeYPosition();
		updateFrame();
	}

	@Override
	public double getRightMostX() {
		return getCenterX() + getRadius();
	}

	@Override
	public double getLeftMostX() {
		return getCenterX() - getRadius();
	}

	@Override
	public boolean intersects(Bird bird) {
		if(!isVisible()) {
			return false;
		}
		return isIntersection(bird, this);
	}

	@Override
	public void translate(double dx) {
		setCenterX(getCenterX() - dx);
	}

	public void randomizeYPosition() {
		double centerY = random.nextDouble() *
				(height - 2 * MINIMUM_Y_OFFSET - 2 * REWARD_RADIUS) +
				MINIMUM_Y_OFFSET + REWARD_RADIUS;
		setCenterY(centerY);
	}

	/**
	 * Switches frames.
	 */
	public void updateFrame() {
		Image frame = rewardFrames.removeFirst();
		setFill(new ImagePattern(frame));
		rewardFrames.add(frame);
	}

	@Override
	public int compareTo(Reward o) {
		return Double.compare(this.getCenterX(), o.getCenterX());
	}

	/**
	 * Loads frames from resource.
	 */
	private static void loadRewardFrames() {
		IntStream.range(0, 5).forEach(i -> {
			rewardFrames.add(new Image(Reward.class.getResourceAsStream("coinFrame" + i + ".png")));
		});
	}

}
