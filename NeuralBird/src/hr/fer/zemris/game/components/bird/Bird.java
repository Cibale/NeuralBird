package hr.fer.zemris.game.components.bird;

import hr.fer.zemris.game.components.IComponent;
import hr.fer.zemris.game.model.GameModel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * Class which represents bird in {@link GameModel}.
 *
 * @author Jure Cular and Boris Generalic
 *
 */
public class Bird extends Circle implements IComponent {

    private static final double BIRD_RADIUS = 20;
	private static LinkedList<Image> birdFrames = new LinkedList<>();

	/** Bird's current velocity. */
    private double currentVelocity;

	static {
		loadBirdFrames();
	}

    public Bird(double centerX, double centerY) {
        super(centerX, centerY, BIRD_RADIUS);
        this.currentVelocity = 0;
        loadBirdFrames();
        updateFrame();
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(double velocity) {
        this.currentVelocity = velocity;
    }

    public void updateFrame() {
        Image frame = birdFrames.removeFirst();
        setFill(new ImagePattern(frame));
        birdFrames.add(frame);
    }

	@Override
	public void translate(double dx) {
		//bird moves only in vertical direction
	}

	@Override
	public double getRightMostX() {
		return getCenterX() + getRadius();
	}

	@Override
	public double getLeftMostX() {
		return getCenterX() - getRadius();
	}

	/**
	 * Loads frames from resource.
	 */
	private static void loadBirdFrames() {
		IntStream.range(0, 3).forEach(i -> {
			birdFrames.add(new Image(Bird.class.getResourceAsStream("birdSprite" + i + ".png")));
		});
	}

}
