package start.engine;

import hr.fer.zemris.game.model.GameModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class which represents the runtime manager for this game.
 *
 * @author Boris Generalic
 * Created by generalic on 6.1.2016..
 */
public class GameEngine {

	private GameModel model;

	private Node gameNode;

	private Timeline gameLoop;

	private BooleanProperty gameOver;

	public GameEngine(GameModel model) {
		this.model = model;
		gameNode = getDecoratedGameNode(model.getGamePane());
		this.gameOver = new SimpleBooleanProperty();
		initGameLoop();
		gameOver.addListener((observable, oldValue, newValue) -> {
			if(!newValue) {
				gameLoop.stop();
			}
		});
	}

	private void initGameLoop() {
		gameLoop = new Timeline(new KeyFrame(Duration.seconds(1), e -> gameOver.set(model.update(1))));
		gameLoop.setRate(30);
		gameLoop.setCycleCount(Animation.INDEFINITE);
	}

	private boolean paused = false;

	public void runGame() {
		gameLoop.play();
	}

	public EventHandler<KeyEvent> getEventHandler() {
		return e -> {
			if (e.getCode().equals(KeyCode.B)) {
				if (!paused) {
					gameLoop.pause();
				} else {
					gameLoop.play();
				}
				paused ^= true;
			} else {
				model.jumpBird();
			}
		};
	}

	public GameModel getGameModel() {
		return model;
	}

	public Node getGameNode() {
		return gameNode;
	}

	public Timeline getGameLoop() {
		return gameLoop;
	}

	public BooleanProperty gameOverProperty() {
		return gameOver;
	}

	private static Pane getDecoratedGameNode(Pane gamePane) {
		Pane gameWorld = new Pane(gamePane);
		String image = GameEngine.class.getResource("night_sky.jpg").toExternalForm();

		gameWorld.setStyle(
				"-fx-background-image: url('" + image + "'); " +
				"-fx-background-size: stretch; " +
				"-fx-background-repeat: stretch; "
		);

		return gameWorld;
	}

}
