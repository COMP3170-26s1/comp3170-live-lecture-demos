package comp3170.live.week2;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glLineWidth;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.Shader;
import comp3170.ShaderLibrary;

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


	public Hexagon() { //int width, int height) {

//		screenWidth = width;
//		screenHeight = height;
		
		// compile shader
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);

		// @formatter:off
		vertices = new Vector4f[] {
				new Vector4f(0.0f, 0.0f, 0.0f, 1.0f),   // centre
				new Vector4f(-0.8f, 0.0f, 0.0f, 1.0f),   // left middle
				new Vector4f(-0.5f, -0.8f, 0.0f, 1.0f),  // bottom left
				new Vector4f(0.5f, -0.8f, 0.0f, 1.0f),   // bottom right
				new Vector4f(0.8f, 0.0f, 0.0f, 1.0f),    // right middle
				new Vector4f(0.5f, 0.8f, 0.0f, 1.0f),    // top right
				new Vector4f(-0.5f, 0.8f, 0.0f, 1.0f),   // top left
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

	}

	public void draw() {
		// activate the shader
		shader.enable();

		// connect the vertex buffer to the a_position attribute
		shader.setAttribute("a_position", vertexBuffer);

		// write the colour value into the u_colour uniform
		Vector3f colour = new Vector3f(1.0f, 0.0f, 0.0f);
		shader.setUniform("u_colour", colour);

//		Vector2f screenSize = new Vector2f(screenWidth, screenHeight);
//		shader.setUniform("u_screenSize", screenSize);

		// mode = GL_TRIANGLES
		// starting offset = 0
		// number of elements = 6
//		glDrawArrays(GL_TRIANGLES, 0, vertices.length);
//		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		glLineWidth(10.0f);
		glDrawElements(GL_LINES, indices.length, GL_UNSIGNED_INT, 0);
//		glDrawArrays(GL_LINES, 0, vertices.length);
		
	}

}
