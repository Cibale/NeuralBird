package start.game_play_fxml.ai_fxml;

import hr.fer.zemris.game.model.GameModel;
import hr.fer.zemris.game.model.GameModelAI;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import start.game_play_fxml.AbstractFXMLController;

/**
 * Class which represents a controller for the {@link AIScreen}.
 *
 * @author Boris Generalic
 * Created by generalic on 4.1.2016..
 */
public class AIScreenController extends AbstractFXMLController {

	@FXML
	private Slider fpsSlider;

	@FXML
	private StackPane inputIndicator;

	@FXML
	private Circle inputOn;

	@FXML
	private VBox optionPanel;

	@Override
	public void initScreen(Scene scene, Pane root, Transition transition) {
		super.initScreen(scene, root, transition);
		setupBinding();
	}

	@Override
	protected GameModel createGameModel() {
		GameModelAI model = new GameModelAI();
		model.addEnvironmentListener(AIScreen.network);
		return model;
	}

	@Override
	protected void resetScreen(Scene scene, Transition transition) {
		new AIScreen(scene, transition);
	}

	private void setupBinding() {
		GameModel gameModel = engine.getGameModel();

		iOSToggleButton toggleButton = new iOSToggleButton();
		optionPanel.getChildren().add(1, toggleButton);
		gameModel.traceableProperty().bind(toggleButton.switchedOnProperty());

		inputIndicator.visibleProperty().bind(gameModel.traceableProperty());
		inputOn.visibleProperty().bind(gameModel.jumpProperty());

		fpsSlider.valueProperty().bindBidirectional(engine.getGameLoop().rateProperty());
	}

}