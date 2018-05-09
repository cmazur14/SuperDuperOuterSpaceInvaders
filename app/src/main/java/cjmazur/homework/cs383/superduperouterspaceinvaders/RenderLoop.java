package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.TextureView;

/**
 * Created by CJ on 4/30/2018.
 */

class RenderLoop implements Runnable {
    private static final int FPS = 30;
    private final World world;
    private final TextureView textureView;
    private Activity context;

    public RenderLoop(TextureView textureView) {
        this.textureView = textureView;
        world = World.getInstance();
        context = (Activity) textureView.getContext();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            if (!world.frozen()) {
                world.tick(1.0f / FPS);
                drawWorld();
                try {
                    delay();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    delay(FPS);
                    startNextActivity();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private void startNextActivity() {
        context.startActivity(new Intent(textureView.getContext(), MainMenuActivity.class));
    }

    private void delay() throws InterruptedException {
        Thread.sleep((long)(1.0/FPS * 1000));
    }

    private void delay(long frames) throws InterruptedException {
        Thread.sleep((long) (frames / FPS * 1000));
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
