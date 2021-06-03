package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    private float x;
    private GameBitmap bitmap;
    private float y;
    private int speed;
    private int power;
    private float dx,dy;

    private Bullet(float x, float y, int speed) {

        Log.d(TAG, "loading bitmap for bullet");
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y, int speed, int power, int type) {
        MainGame game = MainGame.get();
        Bullet bullet = (Bullet) game.get(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet(x, y, speed);
        }
        bullet.init(x, y, speed,power,0,1,type);
        return bullet;
    }

    public static Bullet get(float x, float y, int speed, int power,float dx,float dy) {
        MainGame game = MainGame.get();
        Bullet bullet = (Bullet) game.get(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet(x, y, speed);
        }
        bullet.init(x, y, speed,power,dx,dy, 0);
        return bullet;
    }

    private void init(float x, float y, int speed, int power, float dx, float dy, int type) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
        this.power = power;
        this.dx =dx;
        this.dy= dy;

        if(speed>0)
            bitmap = new GameBitmap(R.mipmap.laser_1);
        else {
            bitmap = new GameBitmap(R.mipmap.e_missile);
            bitmap.setSize(20,20);
        }
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += dy*speed * game.frameTime;
        x += dx*speed * game.frameTime;

        if (y < 0|| y > GameView.view.getHeight()||
                x < 0 || x > GameView.view.getWidth()) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void hitBullet(int damage) {

    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x, y, rect);
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
    }

    public void attack(BoxCollidable object) {
        object.hitBullet(power);
    }
}