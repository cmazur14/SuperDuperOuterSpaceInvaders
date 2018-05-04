package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by CJ on 5/1/2018.
 */

public class AlienSprite extends Sprite {

    private static final int upSpeed = -280;
    private BitmapSequence explosion;
    private boolean dying;
    private boolean dead;
    private boolean dirRight;
    private static final float translateSpeed = 200;
    private int amtMoved;
    private int movedThisTick;

    private static final float SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final float SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public AlienSprite(Vec2d v) {
        super(v);
        dying = false;
        dead = false;
        dirRight = true;
        amtMoved = 0;
        movedThisTick = 0;
        loadBitmaps();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.alien_ship), 100);
        setBitmaps(s);

        explosion = new BitmapSequence();
        explosion.addImage(r.getImage(R.drawable.explosion0), 0.1);
        explosion.addImage(r.getImage(R.drawable.explosion1), 0.1);
        explosion.addImage(r.getImage(R.drawable.explosion2), 0.1);
        explosion.addImage(r.getImage(R.drawable.explosion3), 0.1);
        explosion.addImage(r.getImage(R.drawable.explosion4), 0.1);
        explosion.addImage(r.getImage(R.drawable.explosion5), 0.1);


    }

    @Override
    public void tick(double dt, World world) {
        super.tick(dt, world);
        if (!dying) {
            if (dirRight) {
                movedThisTick = (int) (translateSpeed * dt);
                amtMoved += movedThisTick;
                setPosition(getPosition().add(new Vec2d(movedThisTick, dt * upSpeed)));
                if (amtMoved > 100)
                    dirRight = false;
            } else {
                movedThisTick = (int) (-translateSpeed * dt);
                amtMoved += movedThisTick;
                setPosition(getPosition().add(new Vec2d(movedThisTick, dt * upSpeed)));
                if (amtMoved < -100)
                    dirRight = true;
            }
        } else {
            if (explosion.getCurrIndex() == explosion.getLength() - 1)
                world.toBeRemoved.add(this);
        }
    }

    @Override
    public boolean isDying() {
        return dying;
    }

    @Override
    public void startDeath() {
        dying = true;
        setBitmaps(explosion);
    }


}
