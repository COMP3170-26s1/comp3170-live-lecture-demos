package comp3170.live.week8;

import comp3170.live.common.cameras.OrthographicOrbittingCamera;

public class Camera extends OrthographicOrbittingCamera {

	private static final float DISTANCE = 5;
	private static final float WIDTH = 2;
	private static final float HEIGHT = 2;
	private static final float NEAR = 0.1f;
	private static final float FAR = 10;
	
	
	public Camera() {
		super(DISTANCE, WIDTH, HEIGHT, NEAR, FAR);
	}

}
