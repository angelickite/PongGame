package ua;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public abstract class GameAdapter implements MyApplicationListener, InputProcessor {

	private boolean resetAlreadyAttempted;

	public abstract void hardReset();

	public abstract void update();

	@Override
	public void create() {
		hardReset();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public final void render() {
		update();
		resetAlreadyAttempted = false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void onRenderException(Exception e) {
		if (resetAlreadyAttempted) {
			Gdx.app.exit();
			System.err.println("GameAdapter: a call to reset() did not fix the problem...");
			e.printStackTrace();
		} else {
			resetAlreadyAttempted = true;
			hardReset();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
