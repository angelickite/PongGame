package ua.gamedemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.GameAdapter;
import ua.MyLwjglApplication;
import ua.MyLwjglApplicationConfiguration;

public class PongGame extends GameAdapter {

	ShapeRenderer shaper;
	Viewport viewport;
	OrthographicCamera ortho;

	Rectangle ballBody;
	Vector2 ballVelocity;
	Rectangle paddleBody;
	Rectangle computerPaddleBody;
	int playerScore, computerScore;

	@Override
	public void hardReset() {
		shaper = new ShapeRenderer();
		ortho = new OrthographicCamera();
		viewport = new FitViewport(640, 360, ortho);

		ballBody = new Rectangle(640 / 2f, 360 / 2f, 10, 10);
		ballVelocity = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).nor();
		paddleBody = new Rectangle(0, 10, 60, 8);
		computerPaddleBody = new Rectangle(0, 360 - 18, 60, 8);

		playerScore = 0;
		computerScore = 0;
	}

	@Override
	public void update() {
		float computerTarget = ballBody.x - computerPaddleBody.width / 2f + ballBody.width / 2f;
		float computerSpeed = .1f;
		computerPaddleBody.x += computerSpeed * (computerTarget - computerPaddleBody.x);

		float ballSpeed = 5f;
		ballBody.x += ballVelocity.x * ballSpeed;
		ballBody.y += ballVelocity.y * ballSpeed;

		if (ballBody.x - ballBody.width < 0) {
			ballBody.x = ballBody.width;
			ballVelocity.x *= -1;
		}
		if (ballBody.y - ballBody.height < 0) {
			computerScore++;
			if (computerScore == 5) {
				System.out.println("YOU LOOSE!");
				Gdx.app.exit();
			}
			ballBody = new Rectangle(640 / 2f, 360 / 2f, 10, 10);
			ballVelocity = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).nor();
		}
		if (ballBody.x + ballBody.width > 640) {
			ballBody.x = 640 - ballBody.width;
			ballVelocity.x *= -1;
		}
		if (ballBody.y + ballBody.height > 360) {
			playerScore++;
			if (playerScore == 5) {
				System.out.println("YOU WIN!");
				Gdx.app.exit();
			}
			ballBody = new Rectangle(640 / 2f, 360 / 2f, 10, 10);
			ballVelocity = new Vector2(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f)).nor();
		}

		Vector2 mouse = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		paddleBody.x = mouse.x - paddleBody.width / 2f;

		if (ballBody.overlaps(paddleBody)) {
			if (ballVelocity.y < 0) {
				ballVelocity.y *= -1;
				ballBody.y = paddleBody.y + paddleBody.height + .0001f;

				float relative = ((paddleBody.x + paddleBody.width / 2f) - (ballBody.x + ballBody.width / 2f))
						/ paddleBody.width * 2;

				Vector2 bounce = new Vector2(0, 1);
				float bounceAngleMax = 60;
				bounce.rotate(bounceAngleMax * relative);

				ballVelocity.set(bounce).nor();
			}
		}

		if (ballBody.overlaps(computerPaddleBody)) {
			if (ballVelocity.y > 0) {
				ballVelocity.y *= -1;
				ballBody.y = computerPaddleBody.y - ballBody.height - .0001f;

				float relative = -((computerPaddleBody.x + computerPaddleBody.width / 2f)
						- (ballBody.x + ballBody.width / 2f)) / computerPaddleBody.width * 2;

				Vector2 bounce = new Vector2(0, -1);
				float bounceAngleMax = 60;
				bounce.rotate(bounceAngleMax * relative);

				ballVelocity.set(bounce).nor();
			}
		}

		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		shaper.setProjectionMatrix(ortho.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shaper.begin(ShapeType.Filled);
		shaper.setColor(Color.DARK_GRAY);
		shaper.rect(0, 0, 640, 360);
		shaper.setColor(Color.ORANGE);
		shaper.rect(ballBody.x, ballBody.y, ballBody.width, ballBody.height);
		shaper.setColor(Color.GREEN);
		shaper.rect(paddleBody.x, paddleBody.y, paddleBody.width, paddleBody.height);
		shaper.setColor(Color.RED);
		shaper.rect(computerPaddleBody.x, computerPaddleBody.y, computerPaddleBody.width, computerPaddleBody.height);
		shaper.setColor(Color.WHITE);
		for (int i = 0; i < playerScore; i++) {
			shaper.circle(10, 10 + i * 10, 5);
		}
		for (int i = 0; i < computerScore; i++) {
			shaper.circle(10, 360 - 10 - i * 10, 5);
		}
		shaper.end();

		if (Gdx.input.isKeyJustPressed(Keys.R))
			hardReset();
	}

	public static void main(String[] args) {
		MyLwjglApplicationConfiguration configuration = new MyLwjglApplicationConfiguration();
		configuration.width = 640;
		configuration.height = 360;
		configuration.resizable = true;
		configuration.vSyncEnabled = false;
		configuration.foregroundFPS = 60;
		configuration.backgroundFPS = 60;
		configuration.title = "PongGame";

		new MyLwjglApplication(new PongGame(), configuration);
	}

}
