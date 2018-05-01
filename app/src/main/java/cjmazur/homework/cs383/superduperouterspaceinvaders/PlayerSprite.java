package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by CJ on 4/30/2018.
 */

public class PlayerSprite extends Sprite {

    private static final int upSpeed = -300;
    private float translateSpeed = 0;
    private boolean dead;
    private BitmapSequence deadSequence;

    public PlayerSprite(Vec2d v) {
        super(v);
        dead = false;
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(getResizedBitmap(r.getImage(R.drawable.player_ship0), 120, 180), 0.1);
        s.addImage(getResizedBitmap(r.getImage(R.drawable.player_ship1), 120, 180), 0.1);
        s.addImage(getResizedBitmap(r.getImage(R.drawable.player_ship2), 120, 180), 0.1);
        s.addImage(getResizedBitmap(r.getImage(R.drawable.player_ship3), 120, 180), 0.1);
        setBitmaps(s);

        deadSequence = new BitmapSequence();
        deadSequence.addImage(r.getImage(R.drawable.explosion0), 0.1);
        deadSequence.addImage(r.getImage(R.drawable.explosion1), 0.1);
        deadSequence.addImage(r.getImage(R.drawable.explosion2), 0.1);
        deadSequence.addImage(r.getImage(R.drawable.explosion3), 0.1);
        deadSequence.addImage(r.getImage(R.drawable.explosion4), 0.1);
        deadSequence.addImage(r.getImage(R.drawable.explosion5), 0.1);

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d((float) dt * translateSpeed, (float) dt * upSpeed)));
    }

    public void setTranslateSpeed(float dt, float value) {
        translateSpeed = dt * value;
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        if (!dead) makeDead();
    }

    private void makeDead() {
        dead = true;
        setBitmaps(deadSequence);
    }

}
