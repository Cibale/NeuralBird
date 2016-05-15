package start.game_play_fxml;

import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Interface implemented by controller classes which implement the offered
 * method to initialize the screen which they control.
 *
 * @author Boris Generalic
 * Created by generalic on 7.1.2016..
 */
@FunctionalInterface
public interface IScreenController {

	/**
	 * Controller will initialize the screen for which it is in charge,
	 * before the screen is put on the {@link Scene}.
	 *
	 * @param scene 		scene
	 * @param root			screen pane
	 * @param transition	intro transition
	 */
	void initScreen(Scene scene, Pane root, Transition transition);

}
