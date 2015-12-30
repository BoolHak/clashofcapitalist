package com.yumait.clashofcapitalists.fragment;

import android.app.Fragment;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.funzio.pure2D.BaseScene;
import com.funzio.pure2D.BaseStage;
import com.funzio.pure2D.Camera;
import com.funzio.pure2D.Pure2D;
import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.activity.MainActivity;

/**
 * Created by Bilel Methnani on 12/30/2015.
 */
public class GameFragment extends Fragment implements View.OnTouchListener {

    protected BaseStage mStage;
    protected BaseScene mScene;
    protected Point mDisplaySize = new Point();
    protected Point mDisplaySizeDiv2 = new Point();
    private Camera mCamera;
    protected PointF mRegisteredVector;
    protected PointF mRegisteredCenter;
    protected float mRegisteredZoom = 1;

    private int mMapTileId;
    private int mStageRefrence;
    protected View mMainView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mDisplaySize.x = metrics.widthPixels;
        mDisplaySize.y = metrics.heightPixels;
        mDisplaySizeDiv2.x = mDisplaySize.x / 2;
        mDisplaySizeDiv2.y = mDisplaySize.y / 2;
    }

    protected void setMapTile(int pMapTileId){
        this.mMapTileId = pMapTileId;
    }

    protected void setStageId(int pStageRefrence){
        this.mStageRefrence = pStageRefrence;
    }

    protected void initView(View pMainView){
        this.mMainView = pMainView;
        mStage = (BaseStage) mMainView.findViewById(R.id.stage);
        mScene = createScene();
        mStage.setScene(mScene);
        mStage.setOnTouchListener(this);

        mScene.setRenderContinueously(true);
        Pure2D.setAutoUpdateBounds(true);
        mCamera = new Camera(new PointF(mDisplaySizeDiv2), new PointF(mDisplaySize));
        mCamera.setClipping(true);
        mScene.setCamera(mCamera);
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

        // find the vector
        PointF p1 = null;
        PointF p2 = null;
        PointF vector = null;
        if (event.getPointerCount() == 2) {
            p1 = new PointF(event.getX(0), mDisplaySize.y - event.getY(0));
            p2 = new PointF(event.getX(1), mDisplaySize.y - event.getY(1));
            vector = new PointF(p2.x - p1.x, p2.y - p1.y);
        } else if (event.getPointerCount() < 2) {
            //mRegisteredVector;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getPointerCount() == 1) {
                if (mRegisteredCenter == null) {
                    mRegisteredCenter = mCamera.getPosition();
                } else {
                    float deltaX = event.getX() - mRegisteredCenter.x;
                    float deltaY = mDisplaySize.y - event.getY() - mRegisteredCenter.y;
                    mCamera.moveTo(mRegisteredCenter.x + deltaX, mRegisteredCenter.y + deltaY);
                }
            } else if (event.getPointerCount() == 2) {

                if (mRegisteredVector == null) {
                    mRegisteredVector = new PointF(vector.x, vector.y);
                    mRegisteredZoom = mCamera.getZoom().x;
                    //mRegisteredRotation = mCamera.getRotation();
                }

                // focus on the center of the vector
                mCamera.setPosition(
                        ( p1.x + vector.x / 2 ),
                        ( p1.y + vector.y / 2)
                );
                // zoom it
                float scale = vector.length() / mRegisteredVector.length();
                if (scale > 0) {
                    mCamera.setZoom(mRegisteredZoom * scale);
                }
            }
        }


        return true;
    }
}
