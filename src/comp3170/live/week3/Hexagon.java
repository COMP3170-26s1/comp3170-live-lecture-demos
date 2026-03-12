package comp3170.live.week3;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Matrix4f;

import comp3170.GLBuffers;
import comp3170.Shader;
import comp3170.ShaderLibrary;

import static comp3170.Math.TAU;

public class Hexagon {

	final private String VERTEX_SHADER = "vertex.glsl";
	final private String FRAGMENT_SHADER = "fragment.glsl";

	private Vector4f[] vertices;
	private int vertexBuffer;

	private int[] indices;
	private int indexBuffer;
	
	private Shader shader;
	private int screenWidth;
	private int screenHeight;
    private long duration = 5000; // 5 seconds in millis

    private Matrix4f modelMatrix = new Matrix4f();
    private Matrix4f transMatrix = new Matrix4f();
    private Matrix4f rotMatrix = new Matrix4f();
    private Matrix4f scalMatrix = new Matrix4f();

	public Hexagon() {
		
		// compile shader
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);

        // calculate vertices based upon a radius of 'r'
        float r = 0.8f;
        float r3d2 = (float) (r * Math.sqrt(3.0f))/2.0f;
        
		// @formatter:off
            vertices = new Vector4f[] {
                new Vector4f(0.0f, 0.0f, 0.0f, 1.0f),   // centre
                new Vector4f(-r, 0.0f, 0.0f, 1.0f),   // left middle
                new Vector4f(-r/2.0f, -r3d2, 0.0f, 1.0f),  // bottom left
                new Vector4f(r/2.0f, -r3d2, 0.0f, 1.0f),   // bottom right
                new Vector4f(r, 0.0f, 0.0f, 1.0f),    // right middle
                new Vector4f(r/2.0f, r3d2, 0.0f, 1.0f),    // top right
                new Vector4f(-r/2.0f, r3d2, 0.0f, 1.0f),   // top left
        };

		indices = new int[] {
				0, 1, 2,   // Triangle 1
				0, 2, 3,   // Triangle 2
				0, 3, 4,   // Triangle 3
				0, 4, 5,   // Triangle 4
				0, 5, 6,   // Triangle 5
				0, 6, 1,   // Triangle 6
		};

		// @formatter:on
		vertexBuffer = GLBuffers.createBuffer(vertices);
		indexBuffer = GLBuffers.createIndexBuffer(indices);

//		Vector2f offset = new Vector2f(0.25f, 0.0f);
//		Vector2f scaler = new Vector2f(0.5f, 1.0f);
//		float rotation = TAU/12;
//
//     	translationMatrix(offset, transMatrix);
//		rotationMatrix(rotation, rotMatrix);
//     	scaleMatrix(scaler, scalMatrix);
//     	modelMatrix.mul(transMatrix).mul(rotMatrix).mul(scalMatrix); // T R S ordering

		Vector3f offset = new Vector3f(0.25f, 0.0f, 0.0f);
		Vector3f scalar = new Vector3f(0.5f, 1.0f, 0.0f);
		float rotation = TAU/12;

		modelMatrix.translate(offset).rotateZ(rotation).scale(scalar);
	}

	public void draw() {
		// activate the shader
		shader.enable();

		// connect the vertex buffer to the a_position attribute
		shader.setAttribute("a_position", vertexBuffer);
		shader.setUniform("u_modelMatrix", modelMatrix);

		// write the colour value into the u_colour uniform
		Vector3f colour = new Vector3f(1.0f, 0.0f, 0.0f);
		shader.setUniform("u_colour", colour);

		// query clock
		long currTime = System.currentTimeMillis();
		// phase ranges between 0.0 and 1.0
		float phase = ((float) (currTime % duration)) / ((float) duration);
		shader.setUniform("u_phase", phase);
		
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);		
	}


    private Matrix4f translationMatrix(Vector2f vec, Matrix4f dest) {

            //         = [ 1 0 0 x ]
            // T(x, y) = [ 0 1 0 y ]
            //         = [ 0 0 0 0 ]
            //         = [ 0 0 0 1 ]

            dest.m30(vec.x);
            dest.m31(vec.y);

            return dest;
    }

    private Matrix4f rotationMatrix(float angle, Matrix4f dest) {

            //      = [ cos(a) -sin(a) 0 0 ]
            // R(a) = [ sin(a)  cos(a) 0 0 ]
            //      = [      0       0 0 0 ]
            //      = [      0       0 0 1 ]

            dest.m00((float) Math.cos(angle));
            dest.m01((float) Math.sin(angle));
            dest.m10((float) Math.sin(-angle));
            dest.m11((float) Math.cos(angle));

            return dest;
    }

    private Matrix4f scaleMatrix(Vector2f s, Matrix4f dest) {

            //           = [ sx 0  0  0 ]
            // S(sx, sy) = [ 0  sy 0  0 ]
            //           = [ 0  0  0  0 ]
            //           = [ 0  0  0  1 ]

            dest.m00(s.x);
            dest.m11(s.y);

            return dest;
    }

}
