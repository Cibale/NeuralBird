package start.game_play_fxml;

import java.io.IOException;

import javafx.animation.Transition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * Abstract class which is extended by classes which are loaded from
 * {@link FXMLLoader} and set on {@link Scene}.
 *
 * Created by generalic on 7.1.2016..
 */
public abstract class AbstractFXMLScreen {

	public AbstractFXMLScreen(Scene scene, Transition transition) {
		FXMLLoader fxmlLoader = createFXMLLoader();

		Pane root = getRoot(fxmlLoader);

		Rectangle2D bounds = Screen.getPrimary().getBounds();
		root.setPrefWidth(bounds.getWidth());
		root.setPrefHeight(bounds.getHeight());

		IScreenController controller = fxmlLoader.getController();
		controller.initScreen(scene, root, transition);
	}

	private FXMLLoader createFXMLLoader() {
		return new FXMLLoader(getClass().getResource(getFXMLFileName()));
	}

	protected static Pane getRoot(FXMLLoader fxmlLoader) {
		try {
			Pane root = fxmlLoader.load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected abstract String getFXMLFileName();

}
