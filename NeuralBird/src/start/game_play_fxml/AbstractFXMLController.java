package start.game_play_fxml;

import hr.fer.zemris.game.model.GameModel;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import start.AbstractScreenSwitchController;
import start.engine.GameEngine;

/**
 * Abstract class which represents a controller for screens where
 * game is played.
 *
 * Created by generalic on 7.1.2016..
 */
public abstract class AbstractFXMLController extends AbstractScreenSwitchController {

	@FXML
	private AnchorPane root;

	@FXML
	private Pane gamePane;

	@FXML
	private VBox optionPanel;

	@FXML
	private VBox gameOverVBox;

	@FXML
	private Label scoreLabel;

	@FXML
	private Label endScoreLabel;

	@FXML
	private Button restartButton;

	protected GameEngine engine;

	@FXML
	public void initialize() {
		optionPanel.setTranslateX(-optionPanel.getPrefWidth());
		optionPanel.setDisable(true);

		gameOverVBox.setTranslateY(-2 * root.getPrefHeight());
		gameOverVBox.setDisable(true);
	}

	@FXML
	public void mouseEntered() {
	}

	@FXML
	public void mouseExited() {
		slideOutToolBar();
	}

	@FXML
	public void keyPressed(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ESCAPE)) {
			slideInToolBar();
		}
	}

	@FXML
	public void buttonMouseEntered() {
		ScaleTransition transition = new ScaleTransition(Duration.millis(200), backButton);
		transition.setToX(1.5);
		transition.setToY(1.5);
		transition.play();
	}

	@FXML
	public void buttonMouseExited() {
		ScaleTransition transition = new ScaleTransition(Duration.millis(200), backButton);
		transition.setToX(1);
		transition.setToY(1);
		transition.play();
	}

	@FXML
	public void showToolBar(MouseEvent me) {
		if(me.getSceneX() < 50) {
			slideInToolBar();
		}
	}

	private void slideInToolBar() {
		optionPanel.setDisable(false);
		TranslateTransition transTransition = new TranslateTransition(Duration.millis(350), optionPanel);
		transTransition.setToX(0);
		transTransition.play();
	}

	private void slideOutToolBar() {
		TranslateTransition transTransition = new TranslateTransition(Duration.millis(350), optionPanel);
		transTransition.setToX(-optionPanel.getPrefWidth());
		transTransition.play();
		transTransition.setOnFinished(e -> optionPanel.setDisable(true));
	}

	private void bindOnGameOverAction(BooleanProperty gameOverProperty) {
		gameOverProperty.addListener((observable, oldValue, newValue) -> {
			if(!newValue) {
				TranslateTransition hideScoreLabelTransition = new TranslateTransition(Duration.millis(300), scoreLabel);
				hideScoreLabelTransition.setToY(-root.getPrefHeight());
				hideScoreLabelTransition.setInterpolator(Interpolator.LINEAR);
				hideScoreLabelTransition.setOnFinished(e -> gameOverVBox.setDisable(false));

				TranslateTransition gameOverTranslate = new TranslateTransition(Duration.millis(500), gameOverVBox);
				gameOverTranslate.setToY(0);
				gameOverTranslate.setInterpolator(Interpolator.LINEAR);

				ScaleTransition gameOverTransition = new ScaleTransition(Duration.seconds(0.5), gameOverVBox);
				gameOverTransition.setFromX(1);
				gameOverTransition.setFromY(1);
				gameOverTransition.setToX(2);
				gameOverTransition.setToY(2);
				gameOverTransition.setAutoReverse(true);
				gameOverTransition.setCycleCount(2);
				gameOverTransition.setInterpolator(Interpolator.LINEAR);

				new ParallelTransition(hideScoreLabelTransition, gameOverTranslate, gameOverTransition).play();
			}
		});
	}

	private void bindScoreLabels(IntegerProperty scoreProperty) {
		scoreLabel.textProperty().bind(new SimpleStringProperty("SCORE: ").concat(scoreProperty.asString()));
		endScoreLabel.textProperty().bind(scoreLabel.textProperty());

		scoreLabel.textProperty().addListener(observable -> {
			ScaleTransition transition = new ScaleTransition(Duration.millis(300), scoreLabel);
			transition.setFromX(1);
			transition.setFromY(1);
			transition.setToX(1.5);
			transition.setToY(1.5);
			transition.setInterpolator(Interpolator.LINEAR);
			transition.setAutoReverse(true);
			transition.setCycleCount(2);
			transition.play();
		});
	}

	public void addGameScreen(Node gameNode) {
		gamePane.getChildren().add(gameNode);
	}

	@Override
	public void initScreen(Scene scene, Pane root, Transition transition) {
		this.engine = new GameEngine(createGameModel());

		addGameScreen(engine.getGameNode());
		bindOnGameOverAction(engine.gameOverProperty());
		bindScoreLabels(engine.getGameModel().scoreProperty());

		switchScreen(scene, root, transition);

		Group group = (Group) scene.getRoot();
		restartButton.setOnAction(e -> {
			ScaleTransition zoomInTransition = new ScaleTransition(Duration.seconds(0.5), root);
			zoomInTransition.setFromX(1);
			zoomInTransition.setFromY(1);
			zoomInTransition.setToX(5);
			zoomInTransition.setToY(5);
			zoomInTransition.setInterpolator(Interpolator.LINEAR);
			zoomInTransition.setOnFinished(event -> {
				group.getChildren().remove(root);
				group.getChildren().forEach(c -> c.setVisible(false));
			});

			Transition clearScreenTransition = createClearScreenTransition();

			resetScreen(scene, new SequentialTransition(clearScreenTransition, zoomInTransition));
		});

		scene.setOnKeyPressed(engine.getEventHandler());
		engine.runGame();
	}

	protected abstract GameModel createGameModel();

	protected abstract void resetScreen(Scene scene, Transition transition);

	@Override
	protected Transition createClearScreenTransition() {
		TranslateTransition transition = new TranslateTransition(Duration.millis(300), gameOverVBox);
		transition.setToY(-root.getPrefWidth());
		transition.setInterpolator(Interpolator.LINEAR);
		return transition;
	}
}