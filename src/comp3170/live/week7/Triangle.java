package comp3170.live.week7;

import static comp3170.Math.TAU;
import static comp3170.Math.random;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

import java.awt.Color;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Triangle extends SceneObject {

	static final private String VERTEX_SHADER = "week7Vertex.glsl";
	static final private String FRAGMENT_SHADER = "week7Fragment.glsl";
	private Shader shader;
	private Vector4f[] vertices;
	private Vector4f[] colours;
	private int vertexBuffer;
	private int colourBuffer;

	public Triangle() {
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);

		float[] angles = new float[3];
		for (int i = 0; i < angles.length; i++) {
			angles[i] = random(0, TAU);
		}
		Arrays.sort(angles);

		vertices = new Vector4f[3];
		
		for (int i = 0; i < angles.length; i++) {
			vertices[i] = new Vector4f(1,0,0,1).rotateZ(angles[i]);
		}
		
		colours = new Vector4f[] {
			new Vector4f(1,0,0,1),
			new Vector4f(0,1,0,1),
			new Vector4f(0,0,1,1),
		};
		
		vertexBuffer = GLBuffers.createBuffer(vertices);
		colourBuffer = GLBuffers.createBuffer(colours);
	}

	
	@Override
	protected void drawSelf(Matrix4f mvpMatrix) {
		shader.enable();

		shader.setUniform("u_mvpMatrix", mvpMatrix);
		shader.setAttribute("a_position", vertexBuffer);
		shader.setAttribute("a_colour", colourBuffer);

		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
	}
	
	
}
