package start.game_play_fxml.player_fxml;

import hr.fer.zemris.game.model.GameModel;
import hr.fer.zemris.game.model.GameModelPlayer;
import javafx.animation.Transition;
import javafx.scene.Scene;
import start.game_play_fxml.AbstractFXMLController;

/**
 * Class which represents a controller for the {@link PlayerScreen}.
 *
 * @author Boris Generalic
 * Created by generalic on 4.1.2016..
 */
public class PlayerScreenController extends AbstractFXMLController {

	@Override
	protected GameModel createGameModel() {
		return new GameModelPlayer();
	}

	@Override
	protected void resetScreen(Scene scene, Transition resetTransition) {
		new PlayerScreen(scene, resetTransition);
	}

}