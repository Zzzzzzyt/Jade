package com.zzzyt.jade.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.U;

public class FPSDisplay extends Actor {

	private BitmapFont font;

	public FPSDisplay() {
		this.font = A.get("font/debug.fnt");
		setBounds(U.getConfig().windowWidth - 72, 0, 72, 15);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (U.game.fpsCounter.hasEnoughData()) {
			font.draw(batch, String.format("%.2f fps", 1 / U.game.fpsCounter.getMean()), getX(),
					getY() + getHeight());
		} else {
			font.draw(batch, "----- fps", getX(), getY());
		}
	}
}
