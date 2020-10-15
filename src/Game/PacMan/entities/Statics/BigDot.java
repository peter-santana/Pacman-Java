package Game.PacMan.entities.Statics;
import java.awt.image.BufferedImage;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class BigDot extends BaseStatic{
	public Animation Blink;
	//Animation for the blink of the bigDot



	public BigDot(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.pacmanDots[0]);
        BufferedImage[] blinkanilist = new BufferedImage[2];
        blinkanilist[0] = Images.pacmanDots[0];
        blinkanilist[1] = Images.pacmanDots[2];
        Blink = new Animation(100,blinkanilist);
        
        
        
}
	@Override
	public void tick() {
		Blink.tick();
	}
	
}