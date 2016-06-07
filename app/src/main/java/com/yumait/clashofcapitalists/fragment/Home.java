package com.yumait.clashofcapitalists.fragment;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.funzio.pure2D.BaseScene;
import com.funzio.pure2D.BaseStage;
import com.funzio.pure2D.Camera;
import com.funzio.pure2D.DisplayObject;
import com.funzio.pure2D.Pure2D;
import com.funzio.pure2D.Scene;
import com.funzio.pure2D.animators.Animator;
import com.funzio.pure2D.animators.TrajectoryAnimator;
import com.funzio.pure2D.astar.Astar;
import com.funzio.pure2D.astar.AstarAdapter;
import com.funzio.pure2D.astar.AstarNode;
import com.funzio.pure2D.astar.AstarNodeSet;
import com.funzio.pure2D.atlas.JsonAtlas;
import com.funzio.pure2D.containers.DisplayGroup;
import com.funzio.pure2D.containers.GridGroup;
import com.funzio.pure2D.effects.trails.MotionTrailShape;
import com.funzio.pure2D.gl.GLColor;
import com.funzio.pure2D.gl.gl10.BlendModes;
import com.funzio.pure2D.gl.gl10.GLState;
import com.funzio.pure2D.gl.gl10.textures.Texture;
import com.funzio.pure2D.grid.HexGrid;
import com.funzio.pure2D.grid.VerticalHexGrid;
import com.funzio.pure2D.shapes.Sprite;
import com.funzio.pure2D.uni.UniClip;
import com.funzio.pure2D.uni.UniGroup;
import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.activity.MainActivity;
import com.yumait.clashofcapitalists.objects.UniBouncer;

import android.view.View.OnTouchListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Home extends Fragment implements OnTouchListener {

    final protected static int OBJ_INIT_NUM = 1000;
    final protected static int OBJ_STEP_NUM = 100;
    final protected static Random RANDOM = new Random();
    final protected static GLColor COLOR_BLACK = GLColor.BLACK;
    final protected static GLColor COLOR_WHITE = GLColor.WHITE;
    final protected static GLColor COLOR_GRAY = new GLColor(0.5f, 0.5f, 0.5f, 1);
    final protected static GLColor COLOR_RED = new GLColor(0.7f, 0, 0, 1);
    final protected static GLColor COLOR_GREEN = new GLColor(0, 0.7f, 0, 1);
    final protected static GLColor COLOR_BLUE = new GLColor(0, 0, 0.7f, 1);
    final protected static GLColor COLOR_YELLOW = new GLColor(1f, 1f, 0, 1);
    final protected static GLColor COLOR_TRANSPARENT = new GLColor(0, 0, 0, 0);
    private final int MAP_SIZE = 20;
    protected BaseStage mStage;
    protected BaseScene mScene;
    protected Point mDisplaySize = new Point();
    protected Point mDisplaySizeDiv2 = new Point();
    protected PointF oldVector;
    protected PointF mRegisteredCenter;
    protected float mRegisteredZoom = 1;
    protected float mRegisteredRotation = 0;
    float oldX = 0, oldY = 0;
    PointF vector = new PointF(0, 0);
    PointF p1 = null;
    PointF p2 = null;
    private MainActivity mainActivity;
    private Camera mCamera;
    private DisplayObject mSelectedObject;

    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mDisplaySize.x = metrics.widthPixels;
        mDisplaySize.y = metrics.heightPixels;
        mDisplaySizeDiv2.x = mDisplaySize.x / 2;
        mDisplaySizeDiv2.y = mDisplaySize.y / 2;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return initView(inflater, container);
    }

    private Texture loadTexture() {
        return mScene.getTextureManager().createDrawableTexture(R.drawable.tile, null);
    }

    private void addObject(final Texture texture, final float x, final float y) {
        // convert from screen to scene's coordinates
        DisplayGroup container = new DisplayGroup();
        container.setPosition(0, 0);
        PointF mTempPoint = new PointF();
        mScene.screenToGlobal(x, y, mTempPoint);

        // create object
        Sprite obj = new Sprite();
        // center origin
        //obj.setOriginAtCenter();
        obj.setTexture(texture);
        // set positions
        obj.setPosition(mTempPoint.x, mTempPoint.y);
        float xFactor = obj.getWidth() / 2 + 1;
        float yFactor = -1 * (obj.getHeight() / 2) + 15.5f;
        container.addChild(obj);
        PointF[][] positionTabls = new PointF[MAP_SIZE][MAP_SIZE];
        positionTabls[0][0] = new PointF(x, y);
        for (int i = 1; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                Sprite obj2 = new Sprite();
                obj2.setTexture(texture);
                float x2 = x + i * xFactor - j * xFactor;
                float y2 = y + i * yFactor + j * yFactor;
                positionTabls[i][j] = new PointF(x2, y2);
                obj2.setPosition(x2, y2);
                container.addChild(obj2);
            }
        }

        mScene.addChild(container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);

        mStage = (BaseStage) mainView.findViewById(R.id.stage);
        mScene = createScene();
        mStage.setScene(mScene);
        mStage.setOnTouchListener(this);


        mScene.setColor(COLOR_TRANSPARENT);
        mScene.setRenderContinueously(true);
        Pure2D.setAutoUpdateBounds(true); // for camera clipping
        mCamera = new Camera(new PointF(mDisplaySizeDiv2), new PointF(mDisplaySize));
        mCamera.setClipping(true);
        mScene.setCamera(mCamera);

        mScene.setListener(new Scene.Listener() {

            @Override
            public void onSurfaceCreated(final GLState glState, final boolean firstTime) {
                if (firstTime) {
                    // load the textures
                    Texture texture = loadTexture();

                    // create first obj
                    final float x = mDisplaySize.x / 2;
                    final float y = 3 * (mDisplaySize.y / 2);

                    addObject(texture, x, y);
                }
            }
        });


        return mainView;
    }

    protected BaseScene createScene() {
        return new BaseScene();
    }


    @Override
    public void onPause() {
        super.onPause();

        // pause the stage
        if (mStage != null && mScene != null) {
            mStage.onPause();
            mScene.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // resume the stage
        if (mStage != null && mScene != null) {
            mStage.onResume();
            mScene.resume();

        }
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (event.getPointerCount() == 2) {
                p1 = new PointF(event.getX(0), mDisplaySize.y - event.getY(0));
                p2 = new PointF(event.getX(1), mDisplaySize.y - event.getY(1));
                vector = new PointF(p2.x - p1.x, p2.y - p1.y);
                oldVector = new PointF(vector.x, vector.y);
                mRegisteredZoom = mCamera.getZoom().x;
                return true;
            }

            if (event.getPointerCount() == 1) {
                oldX = event.getX();
                oldY = event.getY();
                return true;
            }

        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            oldVector = null;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if (event.getPointerCount() == 2) {
                oldX = event.getX();
                oldY = event.getY();
                p1 = new PointF(event.getX(0), mDisplaySize.y - event.getY(0));
                p2 = new PointF(event.getX(1), mDisplaySize.y - event.getY(1));
                vector = new PointF(p2.x - p1.x, p2.y - p1.y);

                if(oldVector == null) oldVector = new PointF(vector.x, vector.y);
                mRegisteredZoom = mCamera.getZoom().x;

                // zoom it
                float scale = vector.length() / oldVector.length();
                if (scale > 0) {
                    mCamera.setZoom(mRegisteredZoom * scale);
                }

                oldVector = new PointF(vector.x, vector.y);
                return true;
            }

            if (event.getPointerCount() == 1) {

                float deltaX = (event.getX() - oldX);
                float deltaY = (event.getY() - oldY);
                mCamera.moveTo(mCamera.getPosition().x - deltaX, mCamera.getPosition().y + deltaY);
                oldX = event.getX();
                oldY = event.getY();
                return true;
            }


        }


        return true;
    }
}
