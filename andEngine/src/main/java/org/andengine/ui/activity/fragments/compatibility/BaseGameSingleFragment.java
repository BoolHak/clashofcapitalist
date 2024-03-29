package org.andengine.ui.activity.fragments.compatibility;

import org.andengine.BuildConfig;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationSensorOptions;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.sensor.location.ILocationListener;
import org.andengine.input.sensor.location.LocationSensorOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationSensorOptions;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.shader.ShaderProgramManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.view.IRendererListener;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.ActivityUtils;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;
import org.andengine.util.system.SystemUtils;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

/**
 * 
 * *
 * <p>
 * Fragment implementation of {@link BaseGameActivity}
 * </p>
 * 
 * 
 * 
 * <p>
 * This implementation uses the {@link android.support.v4.app.Fragment} from the
 * <a href="http://developer.android.com/sdk/compatibility-library.html">
 * Compatibility Package</a>.
 * 
 * To use this class, you must include the Android Compatibility Package in your
 * project.
 * 
 * If you want to use the native Android 3.0 (Honeycomb) support for Fragments,
 * use {@link org.andengine.ui.activity.fragments.BaseGameSingleFragment}
 * </p>
 * 
 * 
 * 
 * <p>
 * (c) 2011 Nicolas Gramlich<br>
 * (c) 2011 Zynga Inc.
 * </p>
 * 
 * 
 * 
 * @author Nicolas Gramlich
 * 
 * @author Scott Kennedy
 * 
 * @since 14:10:00 - 04.08.2010
 * 
 * @author Paul Robinson
 * @author gelakinetic
 * 
 */
public abstract class BaseGameSingleFragment extends Fragment implements IGameInterface, IRendererListener {

	/**
	 * 
	 * Paul Robinson implemented this again from GLES1, I think most of this
	 * 
	 * is taken from the standard {@link BaseGameActivity}
	 * 
	 * I've credited the Nicholas and Scott since they did the original GLES1
	 * 
	 * implementation. I' pretty much only did a few tweaks here and there.
	 */

	// ===========================================================

	// Constants

	// ===========================================================

	// ===========================================================

	// Fields

	// ===========================================================

	protected Engine mEngine;

	private WakeLock mWakeLock;

	protected RenderSurfaceView mRenderSurfaceView;

	private boolean mGamePaused;

	private boolean mGameCreated;

	private boolean mCreateGameCalled;

	private boolean mOnReloadResourcesScheduled;

	// ===========================================================

	// Constructors

	// ===========================================================

	@Override
	public void onCreate(final Bundle pSavedInstanceState) {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onCreate" + " @(Thread: '" + Thread.currentThread().getName()
					+ "')");

		}

		super.onCreate(pSavedInstanceState);

		this.mGamePaused = true;

		this.mEngine = this.onCreateEngine(this.onCreateEngineOptions());

		this.mEngine.startUpdateThread();

		this.applyEngineOptions();

		// this.onSetContentView();

	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {

		return new Engine(pEngineOptions);

	}

	@Override
	public synchronized void onSurfaceCreated(final GLState pGLState) {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onSurfaceCreated" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		if (this.mGameCreated) {

			this.onReloadResources();

			if (this.mGamePaused && this.mGameCreated) {

				this.onResumeGame();

			}

		} else {

			if (this.mCreateGameCalled) {

				this.mOnReloadResourcesScheduled = true;

			} else {

				this.mCreateGameCalled = true;

				this.onCreateGame();

			}

		}

	}

	@Override
	public synchronized void onSurfaceChanged(final GLState pGLState, final int pWidth, final int pHeight) {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onSurfaceChanged(Width=" + pWidth + ",  Height=" + pHeight
					+ ")" + " @(Thread: '" + Thread.currentThread().getName() + "')");

		}

	}

	protected synchronized void onCreateGame() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onCreateGame" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		final OnPopulateSceneCallback onPopulateSceneCallback = new OnPopulateSceneCallback() {

			@Override
			public void onPopulateSceneFinished() {

				try {

					if (BuildConfig.DEBUG) {

						Debug.d(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onGameCreated"
								+ " @(Thread: '" + Thread.currentThread().getName() + "')");

					}

					BaseGameSingleFragment.this.onGameCreated();

				} catch (final Throwable pThrowable) {

					Debug.e(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onGameCreated failed."
							+ " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);

				}

				BaseGameSingleFragment.this.callGameResumedOnUIThread();

			}

		};

		final OnCreateSceneCallback onCreateSceneCallback = new OnCreateSceneCallback() {

			@Override
			public void onCreateSceneFinished(final Scene pScene) {

				BaseGameSingleFragment.this.mEngine.setScene(pScene);

				try {

					if (BuildConfig.DEBUG) {

						Debug.d(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onPopulateScene"
								+ " @(Thread: '" + Thread.currentThread().getName() + "')");

					}

					BaseGameSingleFragment.this.onPopulateScene(pScene, onPopulateSceneCallback);

				} catch (final Throwable pThrowable) {

					Debug.e(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onPopulateScene failed."
							+ " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);

				}

			}

		};

		final OnCreateResourcesCallback onCreateResourcesCallback = new OnCreateResourcesCallback() {

			@Override
			public void onCreateResourcesFinished() {

				try {

					if (BuildConfig.DEBUG) {

						Debug.d(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onCreateScene"
								+ " @(Thread: '" + Thread.currentThread().getName() + "')");

					}

					BaseGameSingleFragment.this.onCreateScene(onCreateSceneCallback);

				} catch (final Throwable pThrowable) {

					Debug.e(BaseGameSingleFragment.this.getClass().getSimpleName() + ".onCreateScene failed."
							+ " @(Thread: '" + Thread.currentThread().getName() + "')", pThrowable);

				}

			}

		};

		try {

			if (BuildConfig.DEBUG) {

				Debug.d(this.getClass().getSimpleName() + ".onCreateResources" + " @(Thread: '"
						+ Thread.currentThread().getName() + "')");

			}

			this.onCreateResources(onCreateResourcesCallback);

		} catch (final Throwable pThrowable) {

			Debug.e(this.getClass().getSimpleName() + ".onCreateGame failed." + " @(Thread: '"
					+ Thread.currentThread().getName() + "')", pThrowable);

		}

	}

	@Override
	public synchronized void onGameCreated() {

		this.mGameCreated = true;

		/* Since the potential asynchronous resource creation,

		 * the surface might already be invalid

		 * and a resource reloading might be necessary. */

		if (this.mOnReloadResourcesScheduled) {

			this.mOnReloadResourcesScheduled = false;

			try {

				this.onReloadResources();

			} catch (final Throwable pThrowable) {

				Debug.e(this.getClass().getSimpleName() + ".onReloadResources failed." + " @(Thread: '"
						+ Thread.currentThread().getName() + "')", pThrowable);

			}

		}

	}

	@Override
	public synchronized void onResume() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onResume" + " @(Thread: '" + Thread.currentThread().getName()
					+ "')");

		}

		super.onResume();

		this.acquireWakeLock();

		this.mRenderSurfaceView.onResume();

	}

	@Override
	public synchronized void onResumeGame() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onResumeGame" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		this.mEngine.start();

		this.mGamePaused = false;

	}

	/*

	@Override

	public synchronized void onWindowFocusChanged(final boolean pHasWindowFocus) {

	        super.onWindowFocusChanged(pHasWindowFocus);



	        if(pHasWindowFocus && this.mGamePaused && this.mGameCreated) {

	                this.onResumeGame();

	        }

	}

	 */

	@Override
	public void onReloadResources() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onReloadResources" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		this.mEngine.onReloadResources();

	}

	@Override
	public void onPause() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onPause" + " @(Thread: '" + Thread.currentThread().getName()
					+ "')");

		}

		super.onPause();

		this.mRenderSurfaceView.onPause();

		this.releaseWakeLock();

		if (!this.mGamePaused) {

			this.onPauseGame();

		}

	}

	@Override
	public synchronized void onPauseGame() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onPauseGame" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		this.mGamePaused = true;

		this.mEngine.stop();

	}

	@Override
	public void onDestroy() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onDestroy" + " @(Thread: '" + Thread.currentThread().getName()
					+ "')");

		}

		super.onDestroy();

		this.mEngine.onDestroy();

		try {

			this.onDestroyResources();

		} catch (final Throwable pThrowable) {

			Debug.e(this.getClass().getSimpleName() + ".onDestroyResources failed." + " @(Thread: '"
					+ Thread.currentThread().getName() + "')", pThrowable);

		}

		this.onGameDestroyed();

		this.mEngine = null;

	}

	@Override
	public void onDestroyResources() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onDestroyResources" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		if (this.mEngine.getEngineOptions().getAudioOptions().needsMusic()) {

			this.getMusicManager().releaseAll();

		}

		if (this.mEngine.getEngineOptions().getAudioOptions().needsSound()) {

			this.getSoundManager().releaseAll();

		}

	}

	@Override
	public synchronized void onGameDestroyed() {

		if (BuildConfig.DEBUG) {

			Debug.d(this.getClass().getSimpleName() + ".onGameDestroyed" + " @(Thread: '"
					+ Thread.currentThread().getName() + "')");

		}

		this.mGameCreated = false;

	}

	// ===========================================================

	// Getter & Setter

	// ===========================================================

	public Engine getEngine() {

		return this.mEngine;

	}

	public boolean isGamePaused() {

		return this.mGamePaused;

	}

	public boolean isGameRunning() {

		return !this.mGamePaused;

	}

	public boolean isGameLoaded() {

		return this.mGameCreated;

	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {

		return this.mEngine.getVertexBufferObjectManager();

	}

	public TextureManager getTextureManager() {

		return this.mEngine.getTextureManager();

	}

	public FontManager getFontManager() {

		return this.mEngine.getFontManager();

	}

	public ShaderProgramManager getShaderProgramManager() {

		return this.mEngine.getShaderProgramManager();

	}

	public SoundManager getSoundManager() {

		return this.mEngine.getSoundManager();

	}

	public MusicManager getMusicManager() {

		return this.mEngine.getMusicManager();

	}

	// ===========================================================

	// Methods for/from SuperClass/Interfaces

	// ===========================================================

	// ===========================================================

	// Methods

	// ===========================================================

	private void callGameResumedOnUIThread() {

		BaseGameSingleFragment.this.getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				BaseGameSingleFragment.this.onResumeGame();

			}

		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,

	Bundle savedInstanceState) {

		this.mRenderSurfaceView = new RenderSurfaceView(this.getActivity());

		this.mRenderSurfaceView.setRenderer(this.mEngine, this);

		return this.mRenderSurfaceView;// ,
										// BaseGameSingleFragment.createSurfaceViewLayoutParams());

	}

	// protected void onSetContentView() {

	// this.mRenderSurfaceView = new RenderSurfaceView(this.getActivity());

	// this.mRenderSurfaceView.setRenderer(this.mEngine, this);

	//

	// this.setContentView(this.mRenderSurfaceView,
	// BaseGameSingleFragment.createSurfaceViewLayoutParams());

	// }

	/**
	 * 
	 * @see Engine#runOnUpdateThread(Runnable)
	 */

	public void runOnUpdateThread(final Runnable pRunnable) {

		this.mEngine.runOnUpdateThread(pRunnable);

	}

	/**
	 * 
	 * @see Engine#runOnUpdateThread(Runnable, boolean)
	 */

	public void runOnUpdateThread(final Runnable pRunnable, final boolean pOnlyWhenEngineRunning) {

		this.mEngine.runOnUpdateThread(pRunnable, pOnlyWhenEngineRunning);

	}

	private void acquireWakeLock() {

		this.acquireWakeLock(this.mEngine.getEngineOptions().getWakeLockOptions());

	}

	private void acquireWakeLock(final WakeLockOptions pWakeLockOptions) {

		if (pWakeLockOptions == WakeLockOptions.SCREEN_ON) {

			ActivityUtils.keepScreenOn(this.getActivity());

		} else {

			final PowerManager pm = (PowerManager) this.getActivity().getSystemService(Context.POWER_SERVICE);

			this.mWakeLock = pm.newWakeLock(pWakeLockOptions.getFlag() | PowerManager.ON_AFTER_RELEASE,
					Constants.DEBUGTAG);

			try {

				this.mWakeLock.acquire();

			} catch (final SecurityException pSecurityException) {

				Debug.e("You have to add\n\t<uses-permission android:name=\"android.permission.WAKE_LOCK\"/>\nto your AndroidManifest.xml !",
						pSecurityException);

			}

		}

	}

	private void releaseWakeLock() {

		if (this.mWakeLock != null && this.mWakeLock.isHeld()) {

			this.mWakeLock.release();

		}

	}

	private void applyEngineOptions() {

		final EngineOptions engineOptions = this.mEngine.getEngineOptions();

		// if(engineOptions.isFullscreen()) {

		// ActivityUtils.requestFullscreen(this.getActivity());

		// }

		if (engineOptions.getAudioOptions().needsMusic() || engineOptions.getAudioOptions().needsSound()) {

			this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

		}

		switch (engineOptions.getScreenOrientation()) {

		case LANDSCAPE_FIXED:

			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			break;

		case LANDSCAPE_SENSOR:

			if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {

				this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

			} else {

				Debug.w(ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.LANDSCAPE_SENSOR
						+ " is not supported on this device. Falling back to "
						+ ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.LANDSCAPE_FIXED);

				this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			}

			break;

		case PORTRAIT_FIXED:

			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			break;

		case PORTRAIT_SENSOR:

			if (SystemUtils.SDK_VERSION_GINGERBREAD_OR_LATER) {

				this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

			} else {

				Debug.w(ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.PORTRAIT_SENSOR
						+ " is not supported on this device. Falling back to "
						+ ScreenOrientation.class.getSimpleName() + "." + ScreenOrientation.PORTRAIT_FIXED);

				this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			}

			break;

		}

	}

	protected static LayoutParams createSurfaceViewLayoutParams() {

		final LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		layoutParams.gravity = Gravity.CENTER;

		return layoutParams;

	}

	protected void enableVibrator() {

		this.mEngine.enableVibrator(this.getActivity());

	}

	/**
	 * 
	 * @see {@link Engine#enableLocationSensor(Context, ILocationListener, LocationSensorOptions)}
	 */

	protected void enableLocationSensor(final ILocationListener pLocationListener,
			final LocationSensorOptions pLocationSensorOptions) {

		this.mEngine.enableLocationSensor(this.getActivity(), pLocationListener, pLocationSensorOptions);

	}

	/**
	 * 
	 * @see {@link Engine#disableLocationSensor(Context)}
	 */

	protected void disableLocationSensor() {

		this.mEngine.disableLocationSensor(this.getActivity());

	}

	/**
	 * 
	 * @see {@link Engine#enableAccelerationSensor(Context, IAccelerationListener)}
	 */

	protected boolean enableAccelerationSensor(final IAccelerationListener pAccelerationListener) {

		return this.mEngine.enableAccelerationSensor(this.getActivity(), pAccelerationListener);

	}

	/**
	 * 
	 * @see {@link Engine#enableAccelerationSensor(Context, IAccelerationListener, AccelerationSensorOptions)}
	 */

	protected boolean enableAccelerationSensor(final IAccelerationListener pAccelerationListener,
			final AccelerationSensorOptions pAccelerationSensorOptions) {

		return this.mEngine.enableAccelerationSensor(this.getActivity(), pAccelerationListener,
				pAccelerationSensorOptions);

	}

	/**
	 * 
	 * @see {@link Engine#disableAccelerationSensor(Context)}
	 */

	protected boolean disableAccelerationSensor() {

		return this.mEngine.disableAccelerationSensor(this.getActivity());

	}

	/**
	 * 
	 * @see {@link Engine#enableOrientationSensor(Context, IOrientationListener)}
	 */

	protected boolean enableOrientationSensor(final IOrientationListener pOrientationListener) {

		return this.mEngine.enableOrientationSensor(this.getActivity(), pOrientationListener);

	}

	/**
	 * 
	 * @see {@link Engine#enableOrientationSensor(Context, IOrientationListener, OrientationSensorOptions)}
	 */

	protected boolean enableOrientationSensor(final IOrientationListener pOrientationListener,
			final OrientationSensorOptions pLocationSensorOptions) {

		return this.mEngine.enableOrientationSensor(this.getActivity(), pOrientationListener, pLocationSensorOptions);

	}

	/**
	 * 
	 * @see {@link Engine#disableOrientationSensor(Context)}
	 */

	protected boolean disableOrientationSensor() {

		return this.mEngine.disableOrientationSensor(this.getActivity());

	}

	// ===========================================================

	// Inner and Anonymous Classes

	// ===========================================================

}
