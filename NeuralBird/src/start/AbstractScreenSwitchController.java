package start;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import start.game_play_fxml.IScreenController;

/**
 * Abstract class represents controller and offers implementation of
 * intro and outro animation when screen is switched.
 *
 * @author Boris Generalic
 * Created by generalic on 7.1.2016..
 *
 */
public abstract class AbstractScreenSwitchController implements IScreenController {

	@FXML
	public Button backButton;

	protected void switchScreen(Scene scene, Pane root, Transition transition) {
		Group group = (Group) scene.getRoot();
		Node menuPane = group.getChildren().get(0);

		transition.setOnFinished(e -> {
			group.getChildren().forEach(c -> c.setVisible(false));
			group.getChildren().add(root);
		});

		PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));

		ScaleTransition zoomOutTransition = new ScaleTransition(Duration.seconds(0.5), root);
		zoomOutTransition.setFromX(5);
		zoomOutTransition.setFromY(5);
		zoomOutTransition.setToX(1);
		zoomOutTransition.setToY(1);
		zoomOutTransition.setInterpolator(Interpolator.LINEAR);

		SequentialTransition transitions = new SequentialTransition(transition, pauseTransition, zoomOutTransition);
		transitions.play();

		backButton.setOnAction(e -> {
			Transition clearScreenTransition = createClearScreenTransition();

			ScaleTransition zoomInTransition = new ScaleTransition(Duration.seconds(0.5), root);
			zoomInTransition.setFromX(1);
			zoomInTransition.setFromY(1);
			zoomInTransition.setToX(5);
			zoomInTransition.setToY(5);
			zoomInTransition.setInterpolator(Interpolator.LINEAR);
			zoomInTransition.setOnFinished(event -> {
				group.getChildren().remove(root);
				group.getChildren().forEach(c -> c.setVisible(true));
			});

			zoomOutTransition.setNode(menuPane);
			zoomOutTransition.setDuration(Duration.seconds(0.1));

			new SequentialTransition(
					clearScreenTransition,
					zoomInTransition,
					pauseTransition,
					zoomOutTransition
			).play();
		});
	}

	protected abstract Transition createClearScreenTransition();

}
