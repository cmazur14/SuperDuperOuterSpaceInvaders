package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 4/30/2018.
 */

class World {
    private List<Sprite> sprites;
    private PlayerSprite player;

    public World() {
        sprites = new ArrayList<>();
        sprites.add(player = new PlayerSprite(new Vec2d(Resources.getSystem().getDisplayMetrics().widthPixels / 2, Resources.getSystem().getDisplayMetrics().heightPixels - 200)));
    }

    public void tick(double dt) {
        MotionEvent e = TouchEventQueue.getInstance().dequeue();
        if (e != null) {
            handleMotionEvent(e);
        }
        for (Sprite s : sprites) {
            s.tick(dt);
        }
        resolveCollisions();
    }

    private void resolveCollisions() {
        ArrayList<Collision> collisions = new ArrayList<>();
        for(int i=0; i < sprites.size()-1; i++)
            for(int j=i+1; j < sprites.size(); j++) {
                Sprite s1 = sprites.get(i);
                Sprite s2 = sprites.get(j);

                if (s1.collidesWith(s2))
                    collisions.add(new Collision(s1, s2));
            }

        for(Collision c: collisions) c.resolve();
    }

    /**
     * When the user touches the screen, this message is sent.  Probably you
     * want to tell the player to do something?
     *
     * @param e the MotionEvent corresponding to the touch
     */
    private void handleMotionEvent(MotionEvent e) {

    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
        float y = player.getPosition().getY();
        c.translate(0, -y+100);
        int backgroundNumber = (int)(y / bg.getHeight());
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber - 1, null);
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber, null);
        c.drawBitmap(bg, 0, bg.getHeight()*backgroundNumber + 1, null);
        /*c.drawBitmap(bg, bg.getWidth()*(backgroundNumber-1), 0,  null);
        c.drawBitmap(bg, bg.getWidth()*backgroundNumber, 0,  null);
        c.drawBitmap(bg, bg.getWidth()*(backgroundNumber+1), 0,  null);
        */
        for(Sprite s: sprites)
            s.draw(c);
    }
}
