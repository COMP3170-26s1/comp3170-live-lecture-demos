package comp3170.live.week9;

import comp3170.InputManager;
import comp3170.SceneObject;
import comp3170.live.common.cameras.ICamera;
import comp3170.live.common.sceneobjects.Axes3D;
import comp3170.live.common.sceneobjects.Grid;

/**
 * The Scene class is the root of the scene graph, and handles the construction of objects in the scene.
 * 
 * This extended the SceneObject parent class, which implements the scene graph tree.
 */

public class Scene extends SceneObject {

	public static Scene theScene = null;
	private ICamera camera; // the 'common' package contains code for some standard cameras

	public Scene() {
		theScene = this;

		Grid grid = new Grid(21);
		grid.setParent(this);
		grid.getMatrix().scale(2);
		
		Cylinder cylinder = new Cylinder();
		cylinder.setParent(this);

		Axes3D axes = new Axes3D();
		axes.setParent(this);
		axes.getMatrix().translate(0,1.1f,0);
		
		camera = new Camera();
	}

	public void update(float deltaTime, InputManager input) {
		camera.update(deltaTime, input);
	}

	public ICamera getCamera() {
		return camera;
	}


}
