package hr.fer.zemris.game.components.ground;

import hr.fer.zemris.game.components.IComponent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jcular on 1/8/16.
 *
 * @author Jure Cular
 */
public class Ground extends ImageView implements IComponent {

    private static final Image groundImage;

    static {
        groundImage = new Image(Ground.class.getResource("ground.png").toExternalForm());
    }

    public Ground(double xPosition, double yPosition){
        setImage(groundImage);
        setX(xPosition);
        setY(yPosition);
    }

    @Override
    public double getRightMostX() {
		return getX() + getImage().getWidth();
    }

    @Override
    public double getLeftMostX() {
        return getX();
    }

    @Override
    public void translate(double dx) {
        setX(getX() - dx);
    }

}
