package start.settings_fxml;

import hr.fer.zemris.game.environment.Constants;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import start.AbstractScreenSwitchController;

/**
 * Class which represents a controller for the {@link SettingsScreen}.
 *
 * @author Boris Generalic
 * Created by generalic on 7.1.2016..
 */
public class SettingsScreenController extends AbstractScreenSwitchController {

	@FXML
	public Label gravityLabel;

	@FXML
	public Slider gravitySlider;

	@FXML
	public Label pipeXSpeedLabel;

	@FXML
	public Slider pipeXSpeedSlider;

	@FXML
	public Label pipeYSpeedLabel;

	@FXML
	public Slider pipeYSpeedSlider;

	@FXML
	public Label pipeGapXLabel;

	@FXML
	public Slider pipeGapXSlider;

	@FXML
	public Label pipeGapYLabel;

	@FXML
	public Slider pipeGapYSlider;

	@FXML
	public Label pipeWidthLabel;

	@FXML
	public Slider pipeWidthSlider;

	@FXML
	public Label jumpSpeedLabel;

	@FXML
	public Slider jumpSpeedSlider;

	@FXML
	public Label rewardProbabilityLabel;

	@FXML
	public Slider rewardProbabilitySlider;

	@FXML
	public Label godModeLabel;

	@FXML
	public Slider godModeSlider;

	@FXML
	public Button defaultSettingsButton;

	@Override
	public void initScreen(Scene scene, Pane root, Transition transition) {
		setupBinding();
		switchScreen(scene, root, transition);
	}

	private void setupBinding() {
		gravityLabel.textProperty().bind(gravitySlider.valueProperty().asString("%.2f"));
		gravitySlider.valueProperty().bindBidirectional(Constants.currentConstants.GRAVITY);

		pipeXSpeedLabel.textProperty().bind(pipeXSpeedSlider.valueProperty().asString("%.0f"));
		pipeXSpeedSlider.valueProperty().bindBidirectional(Constants.currentConstants.PIPES_SPEED_X);

		pipeYSpeedLabel.textProperty().bind(pipeYSpeedSlider.valueProperty().asString("%.0f"));
		pipeYSpeedSlider.valueProperty().bindBidirectional(Constants.currentConstants.PIPES_SPEED_Y);

		pipeGapXLabel.textProperty().bind(pipeGapXSlider.valueProperty().asString("%.0f"));
		pipeGapXSlider.valueProperty().bindBidirectional(Constants.currentConstants.PIPE_GAP_X);

		pipeGapYLabel.textProperty().bind(pipeGapYSlider.valueProperty().asString("%.0f"));
		pipeGapYSlider.valueProperty().bindBidirectional(Constants.currentConstants.PIPE_GAP_Y);

		pipeWidthLabel.textProperty().bind(pipeWidthSlider.valueProperty().asString("%.0f"));
		pipeWidthSlider.valueProperty().bindBidirectional(Constants.currentConstants.PIPE_WIDTH);

		jumpSpeedLabel.textProperty().bind(jumpSpeedSlider.valueProperty().asString("%.0f"));
		jumpSpeedSlider.valueProperty().bindBidirectional(Constants.currentConstants.JUMP_SPEED);

		rewardProbabilityLabel.textProperty().bind(rewardProbabilitySlider.valueProperty().multiply(100).asString("%.0f").concat("%"));
		rewardProbabilitySlider.valueProperty().bindBidirectional(Constants.currentConstants.REWARD_PROBABILITY);

		godModeLabel.setText(Constants.currentConstants.GOD_MODE.get() ? "ON" : "OFF");
		godModeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			godModeLabel.setText(newValue.intValue() == 0 ? "OFF" : "ON");
			Constants.currentConstants.GOD_MODE.set(newValue.intValue() != 0);
		});
		Constants.currentConstants.GOD_MODE.addListener((observable, oldValue, newValue) -> {
			godModeSlider.valueProperty().set(newValue ? 1 : 0);
		});
		godModeSlider.valueProperty().set(Constants.currentConstants.GOD_MODE.get() ? 1 : 0);
	}

	@FXML
	public void resetToDefaultSettings() {
		Constants.resetToDefaultSettings();
	}

	@Override
	protected Transition createClearScreenTransition() {
		return new PauseTransition(Duration.ZERO);
	}
}
