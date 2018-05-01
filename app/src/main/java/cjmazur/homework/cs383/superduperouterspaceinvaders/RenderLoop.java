package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.graphics.Canvas;
import android.view.TextureView;

/**
 * Created by CJ on 4/30/2018.
 */

class RenderLoop implements Runnable {
    private static final int FPS = 30;
    private final World world;
    private final TextureView textureView;

    public RenderLoop(TextureView textureView) {
        this.textureView = textureView;
        world = new World();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            world.tick(1.0f/FPS);
            drawWorld();
            try {
                delay();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void delay() throws InterruptedException {
        Thread.sleep((long)(1.0/FPS * 1000));
    }

    private void drawWorld() {
        Canvas c = textureView.lockCanvas();
        try {
            world.draw(c);
        } finally {
            textureView.unlockCanvasAndPost(c);
        }
    }
}
