package hr.fer.zemris.game.components;

import hr.fer.zemris.game.components.bird.Bird;
import javafx.geometry.Bounds;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 * Interfaces which implement components which can intersect with {@link Bird}.
 *
 * @author Jure Cular and Boris Generalic
 *
 */
public interface IIntersectionChecker {

    /**
     * Returns {@code true} if one of the components intersects with bird, {@code false} otherwise.
     *
     * @param bird	bird
     * @return	{@code true} if one of the components intersects with bird, {@code false} otherwise.
     */
    boolean intersects(Bird bird);

    /**
     * Method first checks if boundsInParent of {@linkplain bird} and {@linkplain component}
     * do intersect, if so then method {@linkplain Shape.intersects()} is called which is
     * performance costly.
     *
     * @param bird		a bird
     * @param component	part of pipe
     * @return {@code true} if one of pipes intersects with bird, {@code false} otherwise.
     */
    public default boolean isIntersection(Bird bird, Shape component) {
    	Bounds birdBounds = bird.getBoundsInParent();
    	Bounds pipePartBounds = component.getBoundsInParent();
    	if(birdBounds.intersects(pipePartBounds)) {
    		return !((Path) Shape.intersect(bird, component)).getElements().isEmpty();
    	}
    	return false;
	}

}
