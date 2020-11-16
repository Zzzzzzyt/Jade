package com.hhs.jade.demo.stage1;

import com.badlogic.gdx.utils.Array;
import com.hhs.jade.game.BossScene;
import com.hhs.jade.util.A;
import com.hhs.jade.util.J;
import com.hhs.jade.game.entity.Enemy;
import com.hhs.jade.game.task.Spellcard;

public class Stage1BossScene extends BossScene {

	public Stage1BossScene() {
		super();

		Array<Spellcard> phase1 = new Array<>();

		phase1.add(new Spellcard(1000, 15 * 60, false, 1000000000, new Stage1Mid1()));
		phase1.add(new Spellcard(1000, 60 * 60, false, 1000000000, new Stage1Mid2()));

		Array<Spellcard> phase2 = new Array<>();

		phase2.add(new Spellcard(1000, 60 * 60, false, 1000000000, new Stage1Mid1()));
		phase2.add(new Spellcard(1000, 60 * 60, true, 1000000000, new Stage1Mid2()));

		phases = Array.with(phase1, phase2);
	}

	@Override
	public void init() {
		Enemy e = new Enemy(A.get("th10_player.atlas"), "th10_marisa", 5, 2, 0, -100, 1000, 32, 16, true);
		J.add(e);

		super.init();
	}
}