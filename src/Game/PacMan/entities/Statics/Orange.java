package Game.PacMan.entities.Statics;

import Main.Handler;
import Resources.Images;

public class Orange extends BaseStatic{
    public Orange(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.fruit[2]);      
        
    }
}