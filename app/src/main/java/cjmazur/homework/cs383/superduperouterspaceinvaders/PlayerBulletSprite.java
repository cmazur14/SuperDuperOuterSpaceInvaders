package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class PlayerBulletSprite extends Sprite
{
    private static final int upSpeed = -1500;
    private boolean dead=false;
    public PlayerBulletSprite(Vec2d v)
    {
        super(v);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(getResizedBitmap(r.getImage(R.drawable.yellow_bullet), 20, 40), 1.0);
        setBitmaps(s);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
    {
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
        //bm.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean isActive()
    {
        return true;
    }

    @Override
    public void tick(double dt, World world)
    {
        super.tick(dt, world);
        Vec2d pos = getPosition();
        if (pos.getY() < world.getPlayer().getPosition().getY() - world.SCREEN_HEIGHT) {
            world.toBeRemoved.add(this);
            return;
        } else
            setPosition(pos.add(new Vec2d((float) 0, (float) dt * upSpeed)));
    }

    @Override
    public void resolve(Collision collision, Sprite other, World world)
    {
        if (other.isDying())
            return;
        else {
            other.startDeath();
            world.playExplosion();
            world.sprites.remove(this);
            world.playerBullets.remove(this);
        }

    }


}
