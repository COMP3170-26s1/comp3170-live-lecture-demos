package comp3170.live.week6;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import comp3170.SceneObject;
import comp3170.InputManager;

import static comp3170.Math.TAU;

public class Camera extends SceneObject {

	private float zoom = 2.0f;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();

	// perspective camera params
	private static final float ASPECT = 1.0f;
	private static final float FOVY = TAU/4.0f;

	// ortho camera params
	private float width = 10.0f;
	private float height = 10.0f;

	// common camera params
	private static final float NEAR = 0.1f;
	private static final float FAR = 20.0f;
	private float distance = 5.0f;

	private boolean perspectiveMode = false;

	public Camera() {
	}
	
	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
		viewMatrix = getMatrix();
		return viewMatrix.invert(dest);
	}
	
	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		if(perspectiveMode) {
			return dest.setPerspective(FOVY, ASPECT, NEAR, FAR);
			
		} else {
			return dest.setOrtho(-width /2.0f, width/2.0f, -height/2.0f, height/2.0f, NEAR, FAR);
		}
		//		return projectionMatrix.invert(dest);
	}

	private final float ZOOM_SPEED = 1.0f;
	private float yAngle = 0f;
	private float xAngle = 0f;

	
	private final float ROTATE_RATE = TAU/3;

	public void update(InputManager input, float deltaTime) {
		if (input.isKeyDown(GLFW_KEY_UP)) {
			distance -= ZOOM_SPEED * deltaTime;
		}
		
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			distance += ZOOM_SPEED * deltaTime;
		}
		if (input.isKeyDown(GLFW_KEY_W)) {
			xAngle += ROTATE_RATE * deltaTime;
		}
		if (input.isKeyDown(GLFW_KEY_S)) {
			xAngle -= ROTATE_RATE * deltaTime;
		}
		if (input.isKeyDown(GLFW_KEY_A)) {
			yAngle -= ROTATE_RATE * deltaTime;
		}
		if (input.isKeyDown(GLFW_KEY_D)) {
			yAngle += ROTATE_RATE * deltaTime;
		}
		
		if (input.isKeyDown(GLFW_KEY_I)) {
			perspectiveMode = true;
		}
		if (input.isKeyDown(GLFW_KEY_O)) {
			perspectiveMode = false;
		}
		if (input.wasKeyPressed(GLFW_KEY_P)) {
			perspectiveMode = !perspectiveMode;
		}
		getMatrix().identity().rotateY(yAngle).rotateX(xAngle).translate(0.0f, 0.0f, distance);
		projectionMatrix.scaling(zoom,zoom,1.0f); // Doesn't work for non-uniform screens - why? Check Week 4 slides!
	}
}