package start.game_play_fxml.player_fxml;

import javafx.animation.Transition;
import javafx.scene.Scene;
import start.game_play_fxml.AbstractFXMLScreen;

/**
 * Class which represents the screen where human being
 * is playing the game.
 *
 * @author Boris Generalic
 * Created by generalic on 4.1.2016..
 */
public class PlayerScreen extends AbstractFXMLScreen {

	private static final String FXML_FILE_NAME = "player_screen.fxml";

	public PlayerScreen(Scene scene, Transition transition) {
		super(scene, transition);
	}

	@Override
	protected String getFXMLFileName() {
		return FXML_FILE_NAME;
	}

}
