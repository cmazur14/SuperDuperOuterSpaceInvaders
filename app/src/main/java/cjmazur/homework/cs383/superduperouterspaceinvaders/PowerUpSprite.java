package cjmazur.homework.cs383.superduperouterspaceinvaders;

/**
 * Created by CJ on 5/9/2018.
 */

public class PowerUpSprite extends Sprite {

    public PowerUpSprite(Vec2d v) {
        super(v);
        loadBitmaps();
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.powerup), 100);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void resolve(Collision collision, Sprite other, World world) {
        super.resolve(collision, other, world);
        world.setFireSpeed(5);
    }
}
