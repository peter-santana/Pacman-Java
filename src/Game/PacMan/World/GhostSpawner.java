package Game.PacMan.World;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Ghost;
import Main.Handler;
import Resources.Images;

public class GhostSpawner extends Ghost {

	public GhostSpawner(int x, int y, int width, int height, Handler handler, BufferedImage sprite) {
		super(x, y, width, height, handler, sprite);
	}

	public boolean moving = true, turnFlag = false;
	int turnCooldown = 30;

	public ArrayList<BaseDynamic> spawnGhost() {
		ArrayList<BaseDynamic> ghost = new ArrayList<>();
		ghost.add(new Ghost(x, y, width, height, handler, sprite));
		return ghost;

	}

	@Override
	public void tick() {

	}

}
