package Game.PacMan.entities.Statics;

import Main.Handler;
import Resources.Images;

public class Cherry extends BaseStatic{
    public Cherry(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.fruit[0]);      
        
    }
}