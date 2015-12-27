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
import com.funzio.pure2D.Scene;
import com.funzio.pure2D.animators.Animator;
import com.funzio.pure2D.animators.TrajectoryAnimator;
import com.funzio.pure2D.atlas.JsonAtlas;
import com.funzio.pure2D.gl.GLColor;
import com.funzio.pure2D.gl.gl10.GLState;
import com.funzio.pure2D.gl.gl10.textures.Texture;
import com.funzio.pure2D.uni.UniClip;
import com.funzio.pure2D.uni.UniGroup;
import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.activity.MainActivity;

import java.util.Random;


public class Home extends Fragment implements View.OnTouchListener, Animator.AnimatorListener {

    private MainActivity mainActivity;
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

    protected BaseStage mStage;
    protected BaseScene mScene;
    protected Point mDisplaySize = new Point();
    protected Point mDisplaySizeDiv2 = new Point();
    protected Random mRandom = new Random();
    protected PointF mTempPoint = new PointF();

    private Texture mTexture;
    private JsonAtlas mAtlas;
    private UniGroup mUniGroup;

    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mDisplaySize.x = metrics.widthPixels;
        mDisplaySize.y = metrics.heightPixels;
        mDisplaySizeDiv2.x = mDisplaySize.x / 2;
        mDisplaySizeDiv2.y = mDisplaySize.y / 2;





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return initView(inflater,container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container){
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        // set up the stage and scene
        mStage = (BaseStage) mainView.findViewById(R.id.stage);
        mScene = createScene();
        // mScene.setAutoClear(false);
        mStage.setScene(mScene);
        mStage.setOnTouchListener(this);

        setCoinExplotion();

        return mainView;
    }

    private void setCoinExplotion() {

        try {
            mAtlas = new JsonAtlas(mScene.getAxisSystem());
            mAtlas.load(mainActivity.getAssets(), "atlas/coin_01_60.json", 1);
        } catch (Exception e) {
            Log.e("JsonAtlasActivity", Log.getStackTraceString(e));
        }

        mScene.setListener(new Scene.Listener() {

            @Override
            public void onSurfaceCreated(final GLState glState, final boolean firstTime) {
                if (firstTime) {

                    // load the textures
                    loadTexture();

                    mUniGroup = new UniGroup();
                    mUniGroup.setTexture(mTexture);
                    mScene.addChild(mUniGroup);

                    // generate a lot of squares
                    addSome(mDisplaySizeDiv2.x, mDisplaySizeDiv2.y);
                }
            }
        });

    }

    private void loadTexture() {
        // create texture
        mTexture = mScene.getTextureManager().createAssetTexture("atlas/coin_01_60.png", null);
    }

    private void addObject(final float x, final float y) {
        // create object
        UniClip obj = new UniClip(mAtlas.getMasterFrameSet());
        obj.playAt(mRandom.nextInt(obj.getNumFrames()));
        // obj.setRotation(mRandom.nextInt(360));
        // obj.setFps(30);

        // center origin
        obj.setOriginAtCenter();

        // position
        obj.setPosition(x, y);

        // add to scene
        mUniGroup.addChild(obj);

        // animation
        final TrajectoryAnimator animator = new TrajectoryAnimator(0);
        // animator.setTargetAngleFixed(false);
        // animator.setTargetAngleOffset(-90);
        obj.addManipulator(animator);
        animator.start(mRandom.nextInt(100), (float) (mRandom.nextInt(360) * Math.PI / 180));
        animator.setListener(this);
    }

    private void addSome(final float screenX, final float screenY) {
        mScene.screenToGlobal(screenX, screenY, mTempPoint);
        for (int i = 0; i < 10; i++) {
            addObject(mTempPoint.x, mTempPoint.y);
        }
    }

    protected BaseScene createScene() {
        return new BaseScene();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            final int num = event.getPointerCount();
            for (int i = 0; i < num; i++) {
                final float x = event.getX(i);
                final float y = event.getY(i);
                mStage.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        addSome(x, y);
                    }
                });
            }
        }

        return true;
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
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationUpdate(Animator animator, float value) {

    }
}
