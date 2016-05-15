package start.game_play_fxml.ai_fxml;
import hr.fer.zemris.network.NeuralNetwork;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Class which represents toggle switch button which shows trace lines, and
 * {@link NeuralNetwork} output LED light on {@link AIScreen}.
 *
 * @author Boris Generalic
 * Created by generalic on 10.1.2016..
 */
public class iOSToggleButton extends Region {

	private Rectangle background;
	private Circle trigger;

    private BooleanProperty switchedOn = new SimpleBooleanProperty(false);

    private TranslateTransition translateAnimation;
    private FillTransition fillAnimation;

    private ParallelTransition animation;

    public BooleanProperty switchedOnProperty() {
        return switchedOn;
    }

    public iOSToggleButton() {
		setupBackground();
       	setupTrigger();
		setupShadow();
        setupAnimations();

        getChildren().addAll(background, trigger);

        setupBinding();

		this.setScaleX(0.5);
		this.setScaleY(0.5);
    }

	private void setupBackground() {
		this.background = new Rectangle(100, 50);
		background.setArcWidth(50);
		background.setArcHeight(50);
		background.setFill(Color.WHITE);
		background.setStroke(Color.LIGHTGRAY);
	}

	private void setupTrigger() {
		this.trigger = new Circle(25);
		trigger.setCenterX(25);
		trigger.setCenterY(25);
		trigger.setFill(Color.WHITE);
		trigger.setStroke(Color.LIGHTGRAY);
	}

	private void setupShadow() {
		DropShadow shadow = new DropShadow();
		shadow.setRadius(2);
		trigger.setEffect(shadow);
	}

	private void setupAnimations() {
		this.translateAnimation = new TranslateTransition(Duration.seconds(0.25), trigger);
		this.fillAnimation = new FillTransition(Duration.seconds(0.25), background);
		this.animation = new ParallelTransition(translateAnimation, fillAnimation);
	}

	private void setupBinding() {
		switchedOn.addListener((obs, oldState, newState) -> {
			boolean isOn = newState.booleanValue();
			translateAnimation.setToX(isOn ? 100 - 50 : 0);
			fillAnimation.setFromValue(isOn ? Color.WHITE : Color.LIGHTGREEN);
			fillAnimation.setToValue(isOn ? Color.LIGHTGREEN : Color.WHITE);
			animation.play();
		});
		setOnMouseClicked(event -> switchedOn.set(!switchedOn.get()));
	}

}