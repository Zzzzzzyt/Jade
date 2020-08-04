package com.zzzyt.jade.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.Entity;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.U;

/**
 * Base class for bullets
 *
 */
public class Item extends Entity {

	public int tag;
	public int t;
	public float x, y;
	public Sprite sprite;
	public float boundingWidth, boundingHeight;
	public float radius;
	
	public Texture texture;
	
	public float angle,speed;
	
	public boolean canAutoCollect;
	public boolean follow;
	
	public Item() {

	}

	public Item(Texture t, int tag,float radius,float x,float y) {
		this.tag = tag;
		this.boundingWidth = t.getWidth();
		this.boundingHeight = t.getHeight();
		this.sprite = new Sprite(t);
		this.texture=t;
		this.radius=radius;
		
		this.x=x;
		this.y=y;
		this.angle=90;
		this.speed=3;
	}
	

	public float getBoundingWidth() {
		return boundingWidth;
	}

	public float getBoundingHeight() {
		return boundingHeight;
	}

	@Override
	public Item setX(float nx) {
		x = nx;
		updateSpritePosition();
		return this;
	}

	@Override
	public Item setY(float ny) {
		y = ny;
		updateSpritePosition();
		return this;
	}

	@Override
	public Item setXY(float nx, float ny) {
		x = nx;
		y = ny;
		updateSpritePosition();
		return this;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public Item updateSpritePosition() {
		sprite.setPosition(x - sprite.getWidth() * sprite.getScaleX() / 2,
				y - sprite.getHeight() * sprite.getScaleY() / 2);
		return this;
	}

	public Item setRotaion(float degrees) {
		sprite.setRotation(degrees);
		return this;
	}

	public Item setScale(float scaleXY) {
		sprite.setScale(scaleXY);
		boundingWidth = texture.getWidth() * sprite.getScaleX();
		boundingHeight = texture.getHeight() * sprite.getScaleY();
		updateSpritePosition();
		return this;
	}

	public Item setColor(Color tint) {
		sprite.setColor(tint);
		return this;
	}

	public Item setAlpha(float a) {
		sprite.setAlpha(a);
		return this;
	}

	public float dist2(float x2, float y2) {
		return M.sqr(x - x2) + M.sqr(y - y2);
	}

	public void draw(Batch batch) {
		if (!U.outOfFrame(x, y, boundingWidth, boundingHeight)) {
			sprite.draw(batch);
		}
	}

	public boolean collide(Player player) {
		return Collision.defaultCollision(player.getX(), player.getY(), player.getRadius(), x, y, radius);
	}
	
	public boolean collide2(Player player) {
		return Collision.defaultCollision(player.getX(), player.getY(), player.getItemRadius(), x, y, radius);
	}
	
	public void update(int frame) {
		if(collide(J.getPlayer())) { //collide with player
			onGet();
			J.remove(this);
		}
		if(collide2(J.getPlayer())) { //collide with item sucking circle
			follow=true;
		}
		if(J.getPlayer().getY()>=J.getPlayer().getItemCollectionLineHeight()
				&& canAutoCollect) {
			follow=true;
		}

		t++;
		
		if(follow) {
			speed=5;
			angle=M.atan2(x, y, J.getPlayer().getX(), J.getPlayer().getY());
		}else {
			speed-=0.05f;
		}
		
		x += speed * MathUtils.cosDeg(angle);
		y += speed * MathUtils.sinDeg(angle);
		
		if (U.outOfWorld(x, y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY())) {
			J.remove(this);
			return;
		}
		
		updateSpritePosition();
	}

	/**
	 * Implement this to make the item has super cool effects!!!
	 */
	public void onGet() {
		
	}
	
	@Override
	public int getZIndex() {
		return 0;
	}

}