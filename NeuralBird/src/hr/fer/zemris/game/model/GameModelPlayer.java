package hr.fer.zemris.game.model;

import hr.fer.zemris.game.components.bird.Bird;
import hr.fer.zemris.game.components.reward.Reward;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Model which is used when human being is playing the game.<br>
 * Model offers lazy-start, in other words, game starts when user
 * performs action for first jump of the {@link Bird}.
 *
 * Based on Proxy desing pattern.
 *
 * @author Boris Generalic and Jure Cular
 *
 */
public class GameModelPlayer extends GameModel {

    public BooleanProperty started;

    public GameModelPlayer() {
		super();
        this.started = new SimpleBooleanProperty(false);
        started.bind(jump);
    }

    @Override
	public boolean update(int time) {
    	if(!started.getValue()) {
			bird.updateFrame();
			moveGround(time);
			rewards.forEach(Reward::updateFrame);
			return true;
    	}
    	started.unbind();
    	return super.update(time);
    }

	@Override
	protected void scanEnvironment() {}

	@Override
    public void reset() {
        super.reset();
        started.bind(jump);
    }

}
