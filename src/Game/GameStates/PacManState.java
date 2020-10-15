package Game.GameStates;

import Display.UI.UIManager;
import Game.PacMan.World.GhostSpawner;
import Game.PacMan.World.MapBuilder;
import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BigDot;
import Game.PacMan.entities.Statics.Cherry;
import Game.PacMan.entities.Statics.Dot;
import Game.PacMan.entities.Statics.Orange;
import Game.PacMan.entities.Dynamics.Ghost;
import Game.PacMan.entities.Dynamics.PacMan;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PacManState extends State {

	private UIManager uiManager;
	public String Mode = "Intro";
	public int startCooldown = 60 * 4;// seven seconds for the music to finish
	public int counter = 60 * 4;
	public int gameover, initialspawn = 0;
	public int health = 3;
	public int initialcounter = 0,ghostspawneat;
	public int cases = 0;
	public int counterspawn = 60 * 8,countereat = 60*30;
	public boolean eat = false,ghostdead = false;

	public PacManState(Handler handler) {
		super(handler);
		handler.setMap(MapBuilder.createMap(Images.mapTwo, handler));

	}

	@Override
	public void tick() {

		if (Mode.equals("Stage")) {
			if (startCooldown <= 0) {
				ArrayList<BaseDynamic> toREmovedynamic = new ArrayList<>();
				for (BaseDynamic entity : handler.getMap().getEnemiesOnMap()) {
					if (entity instanceof Ghost && eat == true) {
						if (entity.getBounds().intersects(handler.getPacman().getBounds())) {
							Random rand = new Random();
							ghostspawneat = rand.nextInt(60*4)+60;
							handler.getMusicHandler().playEffect("pacman_chomp.wav");
							toREmovedynamic.add(entity);
							handler.getScoreManager().addPacmanCurrentScore(500);
							ghostdead = true;
							handler.getMusicHandler().playEffect("pacman_eatghost.wav");
						}else if(ghostdead == true && ghostspawneat>1) {
							ghostspawneat --;					
						}
					}
					entity.tick();
				}
				ArrayList<BaseStatic> toREmove = new ArrayList<>();
				for (BaseStatic blocks : handler.getMap().getBlocksOnMap()) {
					if (blocks instanceof Dot) {
						if (blocks.getBounds().intersects(handler.getPacman().getBounds())) {
							handler.getMusicHandler().playEffect("pacman_chomp.wav");
							toREmove.add(blocks);
							handler.getScoreManager().addPacmanCurrentScore(10);
						}
					} else if (blocks instanceof BigDot) {
						((BigDot) blocks).Blink.tick();
						if (blocks.getBounds().intersects(handler.getPacman().getBounds())) {
							handler.getMusicHandler().playEffect("pacman_chomp.wav");
							toREmove.add(blocks);
							handler.getScoreManager().addPacmanCurrentScore(100);
							eat = true;
						}else if(eat == true && countereat>1) {
							countereat--;
						}
					} else if (blocks instanceof Cherry || blocks instanceof Orange) {
						if (blocks.getBounds().intersects(handler.getPacman().getBounds())) {
							handler.getMusicHandler().playEffect("pacman_eatfruit.wav");
							toREmove.add(blocks);
							handler.getScoreManager().addPacmanCurrentScore(120);
						}
					}
				}
				for (BaseStatic removing : toREmove) {
					handler.getMap().getBlocksOnMap().remove(removing);
				}
				for (BaseDynamic removing : toREmovedynamic) {
					handler.getMap().getEnemiesOnMap().remove(removing);
				}
			} else {
				startCooldown--;
			}
		} else if (Mode.equals("Menu")) {
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				Mode = "Stage";
				handler.getMusicHandler().playEffect("pacman_beginning.wav");
			}
		} else {
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				Mode = "Menu";
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_P) && health > 0) {
			handler.getMusicHandler().playEffect("pacman_death.wav");
			handler.getMap().reset();

		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N) && health < 3) {
			health += 1;
			handler.getMusicHandler().playEffect("pacman_extrapac.wav");
			// Added the N button for health.

		}if(countereat == 1) {
			eat = false;
			countereat = 60*30;
		}

		// added ghost spawn
		switch (cases) {
		case 0:
			counterspawn++;
			if (counterspawn >= 240) {
				GhostSpawner ghost = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost);
				ArrayList<BaseDynamic> ghostList = ghost.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList);
				cases = 1;
			}
			break;

		case 1:
			counterspawn++;
			if (counterspawn >= 840) {
				GhostSpawner ghost1 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost1);
				ArrayList<BaseDynamic> ghostList1 = ghost1.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList1);
				cases++;
			}
			break;

		case 2:
			counterspawn++;
			if (counterspawn >= 900) {
				GhostSpawner ghost2 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost2);
				ArrayList<BaseDynamic> ghostList2 = ghost2.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList2);
				cases++;
			}
			break;

		case 3:
			counterspawn++;
			if (counterspawn >= 960) {
				GhostSpawner ghost3 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost3);
				ArrayList<BaseDynamic> ghostList3 = ghost3.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList3);
				cases++;
			}
			break;
		}

		// added C implementation
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) || (ghostdead == true && ghostspawneat==1)) {
			ghostdead = false;
			Random g = new Random();
			int cases = g.nextInt(4);

			switch (cases) {

			case 0:
				GhostSpawner ghost4 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost);
				ArrayList<BaseDynamic> ghostList4 = ghost4.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList4);
				break;
			case 1:
				GhostSpawner ghost5 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost1);
				ArrayList<BaseDynamic> ghostList5 = ghost5.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList5);
				break;

			case 2:
				GhostSpawner ghost6 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost2);
				ArrayList<BaseDynamic> ghostList6 = ghost6.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList6);
				break;
			case 3:
				GhostSpawner ghost7 = new GhostSpawner(200, 200, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier,
						handler, Images.ghost3);
				ArrayList<BaseDynamic> ghostList7 = ghost7.spawnGhost();
				handler.getMap().getEnemiesOnMap().addAll(ghostList7);
				break;

			}
		}if(countereat<1) {
			eat = false;
		}

	}

	@Override
	public void render(Graphics g) {

		if (Mode.equals("Stage")) {
			Graphics2D g2 = (Graphics2D) g.create();
			handler.getMap().drawMap(g2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("Score: " + handler.getScoreManager().getPacmanCurrentScore(),
					(handler.getWidth() / 2) + handler.getWidth() / 6, 25);
			g.drawString("High-Score: " + handler.getScoreManager().getPacmanHighScore(),
					(handler.getWidth() / 2) + handler.getWidth() / 6, 75);
			// added ready
			if (startCooldown >= 7) {
				g.drawImage(Images.ready, 185, 260, null);
			}
			// added pacmanLife image
			g.drawString("Pacman-life: ", handler.getWidth() - handler.getWidth() / 3, handler.getHeight() / 8);
			for (int health1 = 0; health1 < health; health1++) {
				g.drawImage(Images.pacmanLife1, (handler.getWidth() / 2 + 80) + handler.getWidth() / 6 + 80,
						handler.getHeight() / 10, null);
			}
			for (int health1 = 0; health1 < health - 1; health1++) {
				g.drawImage(Images.pacmanLife2, (handler.getWidth() / 2 + 100) + handler.getWidth() / 6 + 100,
						handler.getHeight() / 10, null);
			}
			for (int health1 = 0; health1 < health - 2; health1++) {
				g.drawImage(Images.pacmanLife3, (handler.getWidth() / 2 + 120) + handler.getWidth() / 6 + 120,
						handler.getHeight() / 10, null);
			}
			g.drawImage(Images.wall, 200, 165, null);
			// added game over
			int health2 = 0;
			if (health2 == health) {
				switch (gameover) {
				case 0:
					g.drawImage(Images.gameover, 170, 260, null);
					this.startCooldown = 120;
					if (Mode.equals("Stage")) {
						counter++;
						;
						if (counter >= 480) {
							Mode = "Menu";
							health = 3;
							handler.getScoreManager().setPacmanCurrentScore(0);
							handler.setMap(MapBuilder.createMap(Images.mapTwo, handler));
							startCooldown = 60 * 4;
							counter = 60 * 4;
							counterspawn = 60 * 8;
							cases = 0;
						}

					}
					break;
				}
			}
			// added pacmanLife image
		} else if (Mode.equals("Menu")) {
			g.drawImage(Images.start, 0, 0, handler.getWidth() / 2, handler.getHeight(), null);
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("" + handler.getScoreManager().getPacmanHighScore(), handler.getWidth() / 6, 60);
			g.drawImage(Images.logo, handler.getWidth() / 7, 0, handler.getWidth() / 4, handler.getHeight() / 2, null);
			// Added the PacMan logo to the menu
		} else {
			g.drawImage(Images.intro, 0, 0, handler.getWidth() / 2, handler.getHeight(), null);

		}
	}

	@Override
	public void refresh() {

	}

}
