package start.settings_fxml;

import javafx.animation.Transition;
import javafx.scene.Scene;
import start.game_play_fxml.AbstractFXMLScreen;

/**
 * Class which represents setting screen.
 *
 * @author Boris Generalic
 * Created by generalic on 7.1.2016..
 */
public class SettingsScreen extends AbstractFXMLScreen {

	private static final String FXML_FILE_NAME = "settings_screen.fxml";

    public SettingsScreen(Scene scene, Transition transition) {
		super(scene, transition);
    }

	@Override
	protected String getFXMLFileName() {
		return  FXML_FILE_NAME;
	}

}
