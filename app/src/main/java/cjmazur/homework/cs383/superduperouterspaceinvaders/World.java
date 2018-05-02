package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by CJ on 4/30/2018.
 */

class World {
    private List<Sprite> sprites;
    private List<AlienSprite> aliens;
    private PlayerSprite player;
    private static final int PLAYER_SPRITE_WIDTH = 100;
    private static final int PLAYER_TRANSLATE_SPEED = 15000;
    private static final float SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final float SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static final int INVERSE_SHOTS_PER_TICK = 20;
    private Random rng;

    public World() {
        sprites = new ArrayList<>();
        aliens = new ArrayList<>();
        sprites.add(player = new PlayerSprite(new Vec2d((SCREEN_WIDTH / 2) - (PLAYER_SPRITE_WIDTH / 2), 1000)));
        generateAliens();
        rng = new Random(System.currentTimeMillis());
    }

    private void generateAliens() {
        for (int i = 200; i < SCREEN_WIDTH - 200; i += 100) {
            for (int j = -200; j < 201; j += 100 ) {
                AlienSprite alien = new AlienSprite(new Vec2d(i, j));
                sprites.add(alien);
                aliens.add(alien);
            }
        }
    }

    public void tick(float dt) {
        MotionEvent e = TouchEventQueue.getInstance().dequeue();
        if (e != null) {
            handleMotionEvent(e, dt);
        }
        for (Sprite s : sprites) {
            s.tick(dt);
        }
        checkIfFireLaser();
        //resolveCollisions();
    }

    private void checkIfFireLaser() {
        if (rng.nextInt(INVERSE_SHOTS_PER_TICK) == 5)
            fireRandomLaser();
    }

    private void fireRandomLaser() {
        Vec2d positionOfAlien = aliens.get(rng.nextInt(aliens.size())).getPosition();
        sprites.add(new AlienLaserSprite(positionOfAlien));
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
    private void handleMotionEvent(MotionEvent e, float dt) {
        if (e.getX() > SCREEN_WIDTH / 2) {
            //player touched the right, so move right
            player.setTranslateSpeed(dt, PLAYER_TRANSLATE_SPEED);
        } else {
            //player touched left, so move left
            player.setTranslateSpeed(dt, -PLAYER_TRANSLATE_SPEED);
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            player.setTranslateSpeed(dt, 0);
        }
    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
        float y = player.getPosition().getY();
        c.translate(0,  -y+ (3*SCREEN_HEIGHT/4));
        int backgroundNumber = (int)(y / bg.getHeight());
        c.drawBitmap(bg, 0, bg.getHeight()* (backgroundNumber - 2), null);
        c.drawBitmap(bg, 0, bg.getHeight()* (backgroundNumber - 1), null);
        c.drawBitmap(bg, 0, bg.getHeight()* backgroundNumber, null);
        c.drawBitmap(bg, 0, bg.getHeight()* (backgroundNumber + 1), null);
        for(Sprite s: sprites)
            s.draw(c);
    }
}
