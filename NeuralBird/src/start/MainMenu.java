package start;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Class which represents entry point for this game.
 * Load main menu.
 *
 * @author Boris Generalic
 * Created by generalic on 4.1.2016..
 */
public class MainMenu extends Application {

	private static final String FXML_FILE_NAME = "main_menu.fxml";

	private Stage stage;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_FILE_NAME));
		Pane root = fxmlLoader.load();

		Rectangle2D bounds = Screen.getPrimary().getBounds();
		root.setPrefWidth(bounds.getWidth());
		root.setPrefHeight(bounds.getHeight());

		this.scene = new Scene(new Group(root));

		MainMenuController controller = fxmlLoader.getController();
		controller.setScene(scene);

		setupStage();
		setScreenStartAnimation();
	}

	private void setupStage() {
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreen(true);
		stage.show();
	}

	private void setScreenStartAnimation() {
		final double duration = 1;

		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), scene.getRoot());
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), scene.getRoot());
		scaleTransition.setFromX(0);
		scaleTransition.setFromY(0);
		scaleTransition.setToX(1);
		scaleTransition.setToY(1);

		ParallelTransition transitions = new ParallelTransition(fadeTransition, scaleTransition);
		transitions.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
