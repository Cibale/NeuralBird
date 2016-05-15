package hr.fer.zemris.game.components.pipes;

import hr.fer.zemris.game.components.IComponent;
import hr.fer.zemris.game.components.IIntersectionChecker;
import hr.fer.zemris.game.components.bird.Bird;
import hr.fer.zemris.game.model.GameModel;
import hr.fer.zemris.util.RandomProvider;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Class which represents single pipe pair in {@link GameModel}.
 *
 * @author Jure Cular and Boris Generalic
 *
 */
public class PipePair extends Group implements IComponent, IIntersectionChecker, Comparable<PipePair> {

    public Rectangle upperBody;
    public Rectangle lowerBody;
    public Rectangle upperHead;
    public Rectangle lowerHead;

    private double headHight;
    private double height;
    private double gap;
    private double direction;

    private Random random = RandomProvider.get();

    private static final double HEAD_X_OFFSET = 5;
    private static final double MINIMUM_Y_OFFSET = 100;

    private static Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.DARKGREEN) };
    private static LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    private static Color strokeColor = Color.BLACK;

    public PipePair(double lastPipeX, double gap, double pipeWidth, double height) {
        this.headHight = pipeWidth * 0.75;
    	this.height = height;
        this.gap = gap;
        this.direction = 1;

        this.upperBody = new Rectangle(lastPipeX, 0, pipeWidth, height);

        upperHead = new Rectangle(upperBody.getWidth() + 2 * HEAD_X_OFFSET, headHight);
        upperHead.xProperty().bind(upperBody.xProperty().subtract(HEAD_X_OFFSET));
        upperHead.yProperty().bind(upperBody.yProperty().add(upperBody.getHeight()).subtract(headHight));

        double lowerY = height + gap;

        this.lowerBody = new Rectangle(lastPipeX, lowerY, pipeWidth, height);

        lowerHead = new Rectangle(lowerBody.getWidth() + 2 * HEAD_X_OFFSET, headHight);
        lowerHead.xProperty().bind(lowerBody.xProperty().subtract(HEAD_X_OFFSET));
        lowerHead.yProperty().bind(lowerBody.yProperty());

        lowerHead.setFill(lg);
        lowerBody.setFill(lg);
        upperHead.setFill(lg);
        upperBody.setFill(lg);
        lowerHead.setStroke(strokeColor);
        lowerHead.setStrokeWidth(2);
        lowerBody.setStroke(strokeColor);
        lowerBody.setStrokeWidth(2);
        upperHead.setStroke(strokeColor);
        upperHead.setStrokeWidth(2);
        upperBody.setStroke(strokeColor);
        upperBody.setStrokeWidth(2);

        //////////////////////////////////////////////////////////////////
        getChildren().addAll(upperBody, lowerBody, upperHead, lowerHead);
        //////////////////////////////////////////////////////////////////

        randomizeYPositions();
    }

    @Override
    public double getRightMostX() {
        return upperBody.getX() + upperBody.getWidth();
    }

    @Override
    public double getLeftMostX() {
        return upperBody.getX();
    }

    /**
     * Returns {@code true} if one of pipes intersects with bird, {@code false} otherwise.
     *
     * @param bird	bird
     * @return	{@code true} if one of pipes intersects with bird, {@code false} otherwise.
     */
    @Override
	public boolean intersects(Bird bird) {
        return 	isIntersection(bird, upperBody) ||
        		isIntersection(bird, lowerBody) ||
        		isIntersection(bird, upperHead) ||
        		isIntersection(bird, lowerHead);
    }

    @Override
    public int compareTo(PipePair o) {
    	return Double.compare(this.getLeftMostX(), o.getLeftMostX());
    }

    /**
     * Sets pipe pair x coordinate to the given one.
     *
     * @param x	new x coordinate of the pipe pair
     */
    public void setPairXPosition(double x) {
        upperBody.setX(x);
        lowerBody.setX(x);
    }

    /**
     * Randomizes pipe pair position on vertical axis.
     */
    public void randomizeYPositions() {
        double yUpper = random.nextDouble() * (height - gap - MINIMUM_Y_OFFSET - MINIMUM_Y_OFFSET) + MINIMUM_Y_OFFSET - height;
        double yLower = yUpper + height + gap;

        upperBody.setY(yUpper);
        lowerBody.setY(yLower);
    }

    /**
     * Sets pipe pair y coordinate to the given one.
     *
     * @param y	new y coordinate of the pipe pair
     */
    public void setPairYPosition(double y) {
        if (upperBody.getY() + height + direction * y < MINIMUM_Y_OFFSET
                || lowerBody.getY() + direction * y > height - MINIMUM_Y_OFFSET) {
            direction *= -1;
        }

        upperBody.setY(upperBody.getY() + direction * y);
        lowerBody.setY(lowerBody.getY() + direction * y);
    }

    /**
     * Gets the vertical direction where pipe pair is heading at the moment.
     *
     * @return	-1 if it is going down, 1 otherwise
     */
    public double getDirection() {
        return direction;
    }

    @Override
    public void translate(double dx) {
    	setPairXPosition(upperBody.getX() - dx);
    }

}
