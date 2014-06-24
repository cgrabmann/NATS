package at.stefan.nats;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class Slider extends Entity {

	private Sprite mSlider, mThumb;
	private float mValue, mHeight, mWidth, pX, pY;
	private OnSliderValueChangeListener mListener;
	SceneManager sceneManager;

	public Slider(final float pX, final float pY, Scene s, SceneManager sc,
			ITextureRegion sliderTextureRegion,
			ITextureRegion thumbTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		if (sliderTextureRegion == null || thumbTextureRegion == null)
			throw new NullPointerException(
					"Slider or thumb texture region cannot be null");
		this.sceneManager = sc;
		this.pX = pX;
		this.pY = pY;
		mValue = 0.0f;
		mSlider = new Sprite(pX, pY, sliderTextureRegion,
				vertexBufferObjectManager) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				float newX = pSceneTouchEvent.getX();
				if (newX < pX - mSlider.getWidth() / 2 + 10) {
					newX = pX - mSlider.getWidth() / 2 + 10;
				} else if (newX > pX + mSlider.getWidth() / 2 - 10) {
					newX = pX + mSlider.getWidth() / 2 - 10;
				}
				mThumb.setPosition(newX, pY);

				float volume = (newX - (pX - mSlider.getWidth() / 2)) / 480f;

				if (pSceneTouchEvent.isActionMove()) {

					// Log.i("NATS", "Volume: " + volume);
					sceneManager.getPlayer().setVolume(volume);
					if (volume > 0.75) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(3);
					} else if (volume > 0.40) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(2);
					} else if (volume > 0.05) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(1);
					} else {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(0);
						mThumb.setPosition(pX - mSlider.getWidth() / 2 + 10, pY);
						sceneManager.getPlayer().setVolume(0f);
					}
				}

				if (pSceneTouchEvent.isActionUp()) {
					sceneManager.getPlayer().setVolume(volume);
					if (volume > 0.75) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(3);
					} else if (volume > 0.40) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(2);
					} else if (volume > 0.05) {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(1);
					} else {
						sceneManager.getSettings().getSoundSprite()
								.setCurrentTileIndex(0);
						sceneManager.getPlayer().setVolume(0f);
					}
				}

				return false;
			}
		};
		mThumb = new Sprite(10 + pX - mSlider.getWidth() / 2, pY,
				thumbTextureRegion, vertexBufferObjectManager) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				return false;
			}

		};
		mWidth = (mThumb.getWidth() >= mSlider.getWidth()) ? mThumb.getWidth()
				: mSlider.getWidth();
		mHeight = (mThumb.getHeight() >= mSlider.getHeight()) ? mThumb
				.getHeight() : mSlider.getHeight();
		s.attachChild(mSlider);
		s.attachChild(mThumb);
		s.registerTouchArea(mSlider);
	}

	public Sprite getmThumb() {
		return mThumb;
	}

	public float getWidth() {
		return mWidth;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setVolume(float f) {
		if (f >= 0f && f <= 1f) {
			mThumb.setPosition(pX - mSlider.getWidth() / 2 + f * 480, pY);
		}
	}

	public void setOnSliderValueChangeListener(
			OnSliderValueChangeListener sliderValueChangeListener) {
		if (sliderValueChangeListener == null)
			throw new NullPointerException(
					"OnSliderValueChangeListener cannot be null");
		this.mListener = sliderValueChangeListener;
	}

	public interface OnSliderValueChangeListener {
		public void onSliderValueChanged(float value);
	}
}