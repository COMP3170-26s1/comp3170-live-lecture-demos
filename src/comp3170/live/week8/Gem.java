package comp3170.live.week8;

import static comp3170.Math.TAU;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Gem extends SceneObject {
	
	final private String VERTEX_SHADER = "week8Vertex.glsl";
	final private String FRAGMENT_SHADER = "week8Fragment.glsl";
	final private String WIREFRAME_VERTEX_SHADER = "simpleVertex.glsl";
	final private String WIREFRAME_FRAGMENT_SHADER = "simpleFragment.glsl";
	
	private Vector4f[] vertices; 
	private int vertexBuffer;
	
	private Vector4f[] colours; 
	private int colourBuffer;

	private Vector4f wireColour = new Vector4f(0,0,0,1);
	
	private int[] indices;
	private int indexBuffer;

	private static final int NSIDES = 8;
	
	private Shader shader;
	private Shader wireframeShader;
	
	public Gem() {
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);
		wireframeShader = ShaderLibrary.instance.compileShader(WIREFRAME_VERTEX_SHADER, WIREFRAME_FRAGMENT_SHADER);
		shader.setStrict(false);
		wireframeShader.setStrict(false);
		
		createBuffers();
	}


	private void createBuffers() {
		vertices = new Vector4f[2 + NSIDES];	// top + bottom + sides
		colours = new Vector4f[2 + NSIDES];

		vertices[0] = new Vector4f(0,1,0,1);	// top
		colours[0] = new Vector4f(1,1,1,1);
		
		vertices[1] = new Vector4f(0,-1,0,1);	// bottoms
		colours[1] = new Vector4f(1,1,1,1);
			
		int k = 2;
		
		float[] rgb = new float[3];
		for (int i = 0; i < NSIDES; i++) {
			float angle = TAU * i / NSIDES;
			vertices[k] = new Vector4f(1,0,0,1).rotateY(angle);
			
			float hue = (float)i / NSIDES;
			Color c = Color.getHSBColor(hue, 1, 1);
			c.getRGBColorComponents(rgb);
			colours[k] = new Vector4f(rgb[0],rgb[1],rgb[2],1);
			
			k++;
		}
		
		vertexBuffer = GLBuffers.createBuffer(vertices);		
		colourBuffer = GLBuffers.createBuffer(colours);		
		
		indices = new int[2 * NSIDES * 3];	// 2 triangles per side, 3 vertices per triangle
		
		k = 0;		
		for (int i = 0; i < NSIDES; i++) {
			indices[k++] = 0; 			// TOP
			indices[k++] = 2 + i;		// SIDE
			indices[k++] = 2 + (i + 1) % NSIDES;

			indices[k++] = 1; 			// BOTTOM
			indices[k++] = 2 + (i + 1) % NSIDES;
			indices[k++] = 2 + i;		// SIDE
		}		
		
		indexBuffer = GLBuffers.createIndexBuffer(indices);
	}
	
	
	public void drawSelf(Matrix4f mvpMatrix) {
		shader.enable();
		
		shader.setUniform("u_mvpMatrix", mvpMatrix);

		shader.setAttribute("a_position", vertexBuffer);
		shader.setAttribute("a_colour", colourBuffer);
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

		// draw the wireframe
		
		wireframeShader.enable();
		wireframeShader.setUniform("u_mvpMatrix", mvpMatrix);
		wireframeShader.setAttribute("a_position", vertexBuffer);
		wireframeShader.setUniform("u_colour", wireColour);
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
