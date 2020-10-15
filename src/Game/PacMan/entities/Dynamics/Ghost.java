package Game.PacMan.entities.Dynamics;

import Game.PacMan.World.Map;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BigDot;
import Game.PacMan.entities.Statics.BoundBlock;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Ghost extends BaseDynamic {

	protected double velX, velY, speed = 1;
	public String facing = "Up";
	public boolean moving = false;
	public Animation eatghost;

	public Ghost(int x, int y, int width, int height, Handler handler, BufferedImage sprite) {
		super(x, y, width, height, handler, sprite);
		BufferedImage[] eatanilist = new BufferedImage[2];
		eatanilist[0] = Images.ghost4;
		eatanilist[1] = Images.ghost5;
		eatghost = new Animation(100, eatanilist);

	}

	@Override
	public void tick() {
		eatghost.tick();
		// Initial path for the ghost is randomly chosen
		Random g = new Random();
		int initialpath = g.nextInt(6);

		if (facing == "Up") {
			y -= speed ;
			moving = true;
		}
		if (facing == "Left") {
			x -= speed ;
			moving = true;
		}
		if (facing == "Down") {
			y += speed ;
			moving = true;
		}
		if (facing == "Right") {
			x += speed ;
			moving = true;
		}
		// Added portal wrap points. the cake is a lie
		if (x <= 0) {
			x = 399;
		}
		if (x >= 400) {
			x = 1;
		}

		if (facing.equals("Right") || facing.equals("Left")) {
			checkHorizontalCollision();
		} else {
			checkVerticalCollisions();
		}
		// debug for some corners and randomly chosen paths
		if (x >= 306 && (y >= 361 && y <= 363)) {
			facing = "Up";
		}
		if (x >= 379 && y >= 397) {
			facing = "Up";
		}
		if (initialpath ==0 && (x > 193 && x < 200) && (y > 142 && y < 146)) {
			facing = "Right";
			speed = 2;
		}
			

	}

	public String checkVerticalCollisions() {
		Ghost ghost = this;
		ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamic> pacman = handler.getMap().getEnemiesOnMap();
		boolean toUp = moving && facing.contentEquals("Up");
		Rectangle ghostBounds = toUp ? ghost.getTopBounds() : ghost.getBottomBounds();
		for (BaseStatic brick : bricks) {
			if (brick instanceof BoundBlock) {
				Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
				if (ghostBounds.intersects(brickBounds)) {
					if (toUp) {
						if (checkPreHorizontalCollision("Right")) {
							facing = "Left";
						}
						facing = "Right";
					}
					if (!toUp && checkPreHorizontalCollision("Left")) {
						facing = "Right";
					} else {
						facing = "Left";
					}
				}
			}

		}
		for (BaseDynamic enemy : pacman) {
			Rectangle boundspacman = handler.getPacman().getBounds();
			if (enemy instanceof PacMan) {
				if (ghostBounds.intersects(boundspacman) && handler.getPacManState().eat == false) {
					handler.getMap().reset();
				} else if (ghostBounds.intersects(boundspacman) && handler.getPacManState().eat == true) {

				}
			}
		}
		return facing;
	}

	public boolean checkPreVerticalCollisions(String facing) {
		Ghost ghost = this;
		ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();

		boolean toUp = moving && facing.equals("Up");

		Rectangle ghostBounds = toUp ? ghost.getTopBounds() : ghost.getBottomBounds();
		for (BaseStatic brick : bricks) {
			if (brick instanceof BoundBlock) {
				Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
				if (ghostBounds.intersects(brickBounds)) {
					return true;
				}
			}
		}
		return false;

	}

	public String checkHorizontalCollision() {
		Ghost ghost = this;
		ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamic> pacman = handler.getMap().getEnemiesOnMap();
		boolean toRight = moving && facing.equals("Right");

		Rectangle ghostBounds = toRight ? ghost.getRightBounds() : ghost.getLeftBounds();
		for (BaseStatic brick : bricks) {
			if (brick instanceof BoundBlock) {
				Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
				if (ghostBounds.intersects(brickBounds)) {
					if (toRight) {
						if (checkPreVerticalCollisions("Up"))
							facing = "Down";
						else
							facing = "Up";
					} else if (!toRight)
						if (checkPreVerticalCollisions("Down")) {
							facing = "Up";
						}
					facing = "Down";
				}
			}
		}
		for (BaseDynamic enemy : pacman) {
			Rectangle boundspacman = handler.getPacman().getBounds();
			if (enemy instanceof PacMan) {
				if (ghostBounds.intersects(boundspacman) && handler.getPacManState().eat == false) {
					handler.getMap().reset();
				}
			}
		}
		return facing;
	}

	public boolean checkPreHorizontalCollision(String facing) {
		Ghost ghost = this;
		ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
		velX = speed;
		boolean toRight = moving && facing.equals("Right");

		Rectangle ghostBounds = toRight ? ghost.getRightBounds() : ghost.getLeftBounds();

		for (BaseStatic brick : bricks) {
			if (brick instanceof BoundBlock) {
				Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
				if (ghostBounds.intersects(brickBounds)) {
					return true;
				}
			}
		}
		return false;
	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}

}
