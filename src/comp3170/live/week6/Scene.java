package comp3170.live.week6;

import org.joml.Vector3f;

import comp3170.InputManager;
import comp3170.SceneObject;
import static comp3170.Math.TAU;

public class Scene extends SceneObject {
	
	public static Scene theScene;

	private Camera camera;
	private Gem blueGem;
	private Gem redGem;

	private Vector3f blueGemColour = new Vector3f(0.0f,0.8f,0.8f);
	private float blueGemSize = 1.0f;
	private int blueGemSides = 8;

	private Vector3f redGemColour = new Vector3f(0.8f,0.0f,0.0f);
	private float redGemSize = 1.0f;
	private int redGemSides = 360;

	public Scene () {		
		theScene = this;

		blueGem = new Gem(blueGemColour, blueGemSize, blueGemSides);
		blueGem.setParent(this);
		blueGem.getMatrix().translate(2.0f, 0.0f, 0.0f);

		redGem = new Gem(redGemColour, redGemSize, redGemSides);
		redGem.setParent(this);
		redGem.getMatrix().translate(-2.0f, 0.0f, 0.0f);

		camera = new Camera();
		camera.setParent(this);
	}
	
	public Camera GetCamera() {
		return camera;
	}

	public void update(InputManager input, float deltaTime) {
		camera.update(input, deltaTime);
//		blueGem.update(input, deltaTime);
//		redGem.update(input, deltaTime);
	}
}
