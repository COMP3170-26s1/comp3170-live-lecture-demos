package comp3170.live.week8;

import comp3170.live.common.cameras.PerspectiveOrbittingCamera;
import static comp3170.Math.TAU;

public class Camera extends PerspectiveOrbittingCamera {

	private static final float DISTANCE = 5;
	private static final float WIDTH = 8;
	private static final float HEIGHT = 8;
	private static final float NEAR = 0.1f;
	private static final float FAR = 10;
	private static final float FOVY = TAU / 4; 
	private static final float ASPECT = 1;
	
	public Camera() {
		super(DISTANCE, FOVY, ASPECT, NEAR, FAR);
	}

}
