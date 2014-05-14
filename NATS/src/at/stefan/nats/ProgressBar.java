package at.stefan.nats;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

public class ProgressBar extends Entity {

	Rectangle bgRectangle;
	Rectangle fgRectangle;
	Line border[] = new Line[4];
	Line steps[];
	Scene target;

	enum AndEngine {
		GLES2, GLES2_AnchorCenter
	}

	private float x;
	private float y;
	private float width;
	private float height;
	private int progress = 0;
	private int intervall = 1;
	private boolean attached = false;

	AndEngine version;

	public ProgressBar(float x, float y, float width, float height,
			AndEngine version) {
		super();

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.version = version;

		bgRectangle = new Rectangle(this.x, this.y, this.width, this.height,
				(VertexBufferObjectManager) null);
		if (version == AndEngine.GLES2) {
			fgRectangle = new Rectangle(this.x, this.y, 0, this.height,
					(IRectangleVertexBufferObject) null);
			border[0] = new Line(this.x, this.y, (this.x + this.width), this.y,
					3, (VertexBufferObjectManager) null);
			border[1] = new Line(this.x, (this.y + this.height - 2),
					(this.x + this.width), (this.y + this.height - 2), 3,
					(VertexBufferObjectManager) null);
			border[2] = new Line(this.x, this.y, this.x,
					(this.y + this.height), 3, (VertexBufferObjectManager) null);
			border[3] = new Line((this.x + this.width - 2), this.y, (this.x
					+ this.width - 2), (this.y + this.height), 3,
					(VertexBufferObjectManager) null);
		} else if (version == AndEngine.GLES2_AnchorCenter) {
			fgRectangle = new Rectangle((this.x - this.width / 2), this.y, 0,
					this.height, (VertexBufferObjectManager) null);
			border[0] = new Line((this.x - this.width / 2), (this.y
					+ this.height / 2 - 1), (this.x + this.width / 2), (this.y
					+ this.height / 2 - 1), 3, (VertexBufferObjectManager) null);
			border[1] = new Line((this.x - this.width / 2), (this.y
					- this.height / 2 + 1), (this.x + this.width / 2), (this.y
					- this.height / 2 + 1), 3, (VertexBufferObjectManager) null);
			border[2] = new Line((this.x - this.width / 2 + 1),
					(this.y + this.height / 2), (this.x - this.width / 2 + 1),
					(this.y - this.height / 2), 3,
					(VertexBufferObjectManager) null);
			border[3] = new Line((this.x + this.width / 2 - 1),
					(this.y + this.height / 2), (this.x + this.width / 2 - 1),
					(this.y - this.height / 2), 3,
					(VertexBufferObjectManager) null);
		}
		
		bgRectangle.setColor(new Color(255, 0, 0));
		fgRectangle.setColor(new Color(0, 255, 0));

	}

	public void setForeGroundColor(Color c) {
		fgRectangle.setColor(c);
	}

	public void setBackGroundColor(Color c) {
		bgRectangle.setColor(c);
	}

	public void setIntervall(int intervall) {
		if (this.intervall > 1) {
			for (int i = 0; i < this.intervall; i++) {
				this.target.detachChild(steps[i]);
			}
		}

		this.intervall = intervall;

		if (intervall > 1) {
			steps = new Line[intervall];
			for (int i = 1; i < intervall; i++) {
				if (this.version == AndEngine.GLES2) {
					steps[i - 1] = new Line((this.x + i * this.width
							/ intervall - 1), this.y, (this.x + i * this.width
							/ intervall - 1), (this.y + this.height), 3,
							(VertexBufferObjectManager) null);
				} else if (this.version == AndEngine.GLES2_AnchorCenter) {
					steps[i - 1] = new Line(
							(this.x - this.width / 2 + this.width / intervall
									* i), (this.y - this.height / 2), (this.x
									- this.width / 2 + this.width / intervall
									* i), (this.y + this.height / 2), 3,
							(VertexBufferObjectManager) null);
				}
				steps[i - 1].setColor(new Color(0, 0, 0));
				if (this.attached == true) {
					this.target.attachChild(steps[i - 1]);
				}
			}
		}
	}

	public void setProgress(int progress) {
		if(progress < this.intervall) {
			this.progress = progress;
		}
		fgRectangle.setWidth(this.width / this.intervall * this.progress);
		if (this.version == AndEngine.GLES2_AnchorCenter) {
			fgRectangle.setPosition((this.x - this.width / 2 + (this.width
					/ this.intervall / 2 * this.progress)), this.y);
		}

	}

	public void increaseProgress() {
		if(this.progress < this.intervall) {
			this.progress++;
		}
		fgRectangle.setWidth(this.width / this.intervall * this.progress);
		if (this.version == AndEngine.GLES2_AnchorCenter) {
			fgRectangle.setPosition((this.x - this.width / 2 + (this.width
					/ this.intervall / 2 * this.progress)), this.y);
		}
	}

	public void attach(HUD s) {
		this.attached = true;
		this.target = s;
		s.attachChild(bgRectangle);
		s.attachChild(fgRectangle);
		for (int i = 0; i < 4; i++) {
			border[i].setColor(new Color(0, 0, 0));
			s.attachChild(border[i]);
		}
		if (this.intervall > 1) {
			for (int i = 1; i < this.intervall; i++) {
				s.attachChild(steps[i - 1]);
			}
		}
	}

	public void detach(Scene s) {
		this.attached = false;
		this.target = null;
		s.detachChild(bgRectangle);
		s.detachChild(fgRectangle);
		for (int i = 0; i < 4; i++) {
			s.detachChild(border[i]);
		}
		if (this.intervall > 1) {
			for (int i = 1; i < this.intervall; i++) {
				s.detachChild(steps[i - 1]);
			}
		}
	}

	public void reset() {
		this.progress = 0;
		fgRectangle.setWidth(0);
		if (this.version == AndEngine.GLES2_AnchorCenter) {
			fgRectangle.setPosition(this.x, this.y);
		}
	}

}
