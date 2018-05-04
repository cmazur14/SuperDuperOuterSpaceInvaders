package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by CJ on 4/30/2018.
 */

class World {
    public List<Sprite> sprites;
    public List<Sprite> toBeRemoved;
    private List<AlienSprite> aliens;
    private PlayerSprite player;
    private PlayerBulletSprite newPlayerBullet;
    public List<PlayerBulletSprite> playerBullets;
    private AlienLaserSprite newAlienBullet;
    public List<AlienLaserSprite> alienBullets;
    private List<Collision> collisions;
    public static final int PLAYER_SPRITE_WIDTH = 100;
    public static final int PLAYER_TRANSLATE_SPEED = 15000;
    public static final float SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final float SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int tickCounter;
    private int mPlayer_ship0;
    private Long initialTime;
    private int inverseShotsPerTick = 20;
    private Random rng;
    private int score;
    private Vec2d playerPosition;

    public World() {
        initialTime = System.nanoTime();
        score = 0;
        tickCounter = 0;
        playerBullets = new ArrayList<>();
        alienBullets = new ArrayList<>();
        toBeRemoved = new ArrayList<>();
        collisions = new ArrayList<>();
        sprites = new ArrayList<>();
        aliens = new ArrayList<>();
        sprites.add(player = new PlayerSprite(new Vec2d((SCREEN_WIDTH / 2) - (PLAYER_SPRITE_WIDTH / 2), 1000)));
        generateAliens();
        rng = new Random(System.currentTimeMillis());
    }

    public void incrementScore() {
        score ++;
    }

    private void generateAliens() {
        int screenTop = (int) (player.getPosition().getY() - SCREEN_HEIGHT);
        for (int i = 100; i < SCREEN_WIDTH - 100; i += 200) {
            for (int j = screenTop + 500; j < screenTop + 801; j += 100 ) {
                AlienSprite alien = new AlienSprite(new Vec2d(i, j));
                sprites.add(alien);
                aliens.add(alien);
            }
        }
    }

    public void tick(float dt) {
        if (player != null) {
            MotionEvent e = TouchEventQueue.getInstance().dequeue();
            if (e != null) {
                handleMotionEvent(e, dt);
            }
            for (Sprite s : sprites) {
                s.tick(dt, this);
            }
            checkIfAliensFireLaser();
            spawnPlayerBullet();
            tickCounter++;
            resolveCollisions();
            for (Sprite s : toBeRemoved) {
                sprites.remove(s);
                if (s.getClass().equals(PlayerBulletSprite.class))
                    playerBullets.remove(s);
                else if (s.getClass().equals(AlienLaserSprite.class))
                    alienBullets.remove(s);
                else if (s.getClass().equals(AlienSprite.class)) {
                    aliens.remove(s);
                    score++;
                }
            }
            toBeRemoved.clear();
            checkLevelCompletion();
        }
    }

    private void checkLevelCompletion() {
        if (aliens.size() == 0) {
            generateAliens();
            Log.d("ShotFrequency", "" + inverseShotsPerTick);
            if (inverseShotsPerTick > 1)
                inverseShotsPerTick--;
            else
                declareWin();
        }
    }

    private void declareWin() {
        //TODO
    }

    public PlayerSprite getPlayer() {
        return player;
    }

    public void removePlayer() {
        sprites.remove(player);
        playerPosition = player.getPosition();
        player = null;
    }

    private void spawnPlayerBullet() {
        if (tickCounter == 10 && player != null) {
            newPlayerBullet = new PlayerBulletSprite((new Vec2d(player.getPosition().getX()+50, player.getPosition().getY()
                    - 5)));
            sprites.add(newPlayerBullet);
            playerBullets.add(newPlayerBullet);
            tickCounter = 0;
        }
    }

    private void checkIfAliensFireLaser() {
        if (rng.nextInt(inverseShotsPerTick) == 1)
            fireRandomAlienLaser();
    }

    private void fireRandomAlienLaser() {
        Vec2d positionOfAlien = aliens.get(rng.nextInt(aliens.size())).getPosition();
        newAlienBullet = new AlienLaserSprite(positionOfAlien);
        sprites.add(newAlienBullet);
        alienBullets.add(newAlienBullet);
    }

    private void drawTimer(Canvas c)
    {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(85);
        String t = findTime();
        p.setTextAlign(Paint.Align.CENTER);


        c.drawText("Time: " + t ,SCREEN_WIDTH/2,player.getPosition().getY()+400, p);

    }

    private void drawScore(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(65);
        p.setTextAlign(Paint.Align.CENTER);

        c.drawText("Score: " + score, 3 * SCREEN_WIDTH / 4 + 100, player.getPosition().getY() + 400, p);
    }

    private void drawLevel(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(65);
        p.setTextAlign(Paint.Align.CENTER);

        c.drawText("Level: " + (21 - inverseShotsPerTick), SCREEN_WIDTH / 4 - 100, player.getPosition().getY() + 400, p);
    }

    private String findTime()
    {
        Long newTime = System.nanoTime()-initialTime;
        newTime = newTime/1000000000;
        return Long.toString(newTime);

    }

    private void resolveCollisions() {
        for(int i=0; i < playerBullets.size()-1; i++)
            for(int j = 0; j < aliens.size(); j++) {
                Sprite s1 = playerBullets.get(i);
                Sprite s2 = aliens.get(j);

                if (s1.collidesWith(s2))
                    collisions.add(new Collision(s1, s2));
            }

        for(Collision c: collisions) c.resolve(this);
        collisions.clear();
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
        if (player != null) {
            Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
            float y = player.getPosition().getY();
            c.translate(0, -y + (3 * SCREEN_HEIGHT / 4));
            int backgroundNumber = (int) (y / bg.getHeight());
            c.drawBitmap(bg, 0, bg.getHeight() * (backgroundNumber - 2), null);
            c.drawBitmap(bg, 0, bg.getHeight() * (backgroundNumber - 1), null);
            c.drawBitmap(bg, 0, bg.getHeight() * backgroundNumber, null);
            c.drawBitmap(bg, 0, bg.getHeight() * (backgroundNumber + 1), null);
            for (Sprite s : sprites)
                s.draw(c);
            drawTimer(c);
            drawScore(c);
            drawLevel(c);
        }
        drawDeathSplash(c);
    }

    private void drawDeathSplash(Canvas c)  {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(200);
        String t = "YOU\nDIED!";
        p.setTextAlign(Paint.Align.CENTER);


        c.drawText("Time: " + t ,SCREEN_WIDTH/2,playerPosition.getY()+400, p);
    }

}
