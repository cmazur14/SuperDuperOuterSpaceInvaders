package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by CJ on 5/1/2018.
 */

class AlienLaserSprite extends Sprite {

    private static final int FLY_SPEED = 300;

    public AlienLaserSprite(Vec2d positionOfAlien) {
        super(positionOfAlien);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap origImage = BitmapFactory.decodeResource(r.getContext().getResources(), R.drawable.red_bullet);
        Bitmap wantedImg = Bitmap.createBitmap(origImage, 0, 0, origImage.getWidth(), origImage.getHeight(), matrix, true);
        s.addImage(wantedImg, 100);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        setPosition(getPosition().add(new Vec2d(0, dt * FLY_SPEED)));
    }
}
