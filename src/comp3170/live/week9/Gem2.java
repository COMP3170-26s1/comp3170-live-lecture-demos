package comp3170.live.week9;

import static comp3170.Math.TAU;
import static comp3170.Math.cross;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.InputManager;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
import comp3170.live.week8.Week8;

public class Gem2 extends SceneObject {
	
//	final private String VERTEX_SHADER = "normalVertex.glsl";
//	final private String FRAGMENT_SHADER = "normalFragment.glsl";
	final private String VERTEX_SHADER = "lightVertex.glsl";
	final private String FRAGMENT_SHADER = "lightFragment.glsl";
	final private String WIREFRAME_VERTEX_SHADER = "simpleVertex.glsl";
	final private String WIREFRAME_FRAGMENT_SHADER = "simpleFragment.glsl";
	
	private Vector4f[] vertices; 
	private int vertexBuffer;
	private Vector4f[] normals; 
	private int normalBuffer;

	private Vector4f wireColour = new Vector4f(0,0,0,1);
	
	private static final int NSIDES = 8;
	
	private Shader shader;
	private Shader wireframeShader;
	
	public Gem2() {
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);
		wireframeShader = ShaderLibrary.instance.compileShader(WIREFRAME_VERTEX_SHADER, WIREFRAME_FRAGMENT_SHADER);
		shader.setStrict(false);
		wireframeShader.setStrict(false);
		
		createBuffers();
	}

	private void createBuffers() {
		createAttributeBuffers();				
	}

	private void createAttributeBuffers() {
		int nVertices = 3 * 2 * NSIDES; // 2 facets per side, 3 verts per tri 
		vertices = new Vector4f[nVertices];	
		normals = new Vector4f[nVertices];	

		int k = 0;
		
		float[] rgb = new float[3];
		for (int i = 0; i < NSIDES; i++) {
			float angle0 = TAU * i / NSIDES;
			float angle1 = TAU * (i+1) / NSIDES;

			Vector4f p0 = new Vector4f(0,1,0,1);	// top
			Vector4f p1 = new Vector4f(1,0,0,1).rotateY(angle0);
			Vector4f p2 = new Vector4f(1,0,0,1).rotateY(angle1);
			
			Vector4f v10 = p1.sub(p0, new Vector4f());
			Vector4f v20 = p2.sub(p0, new Vector4f());
			Vector4f n = cross(v10, v20, new Vector4f());
			
			vertices[k] = p0;
			normals[k] = n;
			k++;
			
			vertices[k] = p1;
			normals[k] = n;
			k++;

			vertices[k] = p2;
			normals[k] = n;
			k++;
				
			p0 = new Vector4f(0,-1,0,1); // bottom
			p1 = new Vector4f(1,0,0,1).rotateY(angle1);
			p2 = new Vector4f(1,0,0,1).rotateY(angle0);
			
			v10 = p1.sub(p0, new Vector4f());
			v20 = p2.sub(p0, new Vector4f());
			n = cross(v10, v20, new Vector4f());
			
			vertices[k] = p0;
			normals[k] = n;
			k++;
			
			vertices[k] = p1;
			normals[k] = n;
			k++;

			vertices[k] = p2;
			normals[k] = n;
			k++;
		}
		
		vertexBuffer = GLBuffers.createBuffer(vertices);		
		normalBuffer = GLBuffers.createBuffer(normals);		
	}
	
	private Vector3f diffuseMaterial = new Vector3f(1,1,0);
	private Vector3f ambientIntensity = new Vector3f(0.1f,0.1f,0.1f);

	private Vector3f lightIntensity = new Vector3f(1,1,1);
	private Vector4f lightDirection = new Vector4f(1,0,0,0); 

	private Matrix4f modelMatrix = new Matrix4f();
	private Matrix4f normalMatrix = new Matrix4f();
	
	public void drawSelf(Matrix4f mvpMatrix, int pass) {
		
		switch (pass) {
		
		case Week8.OPAQUE_PASS:
			// Opaque pass: draw the faces
	
			getModelToWorldMatrix(modelMatrix);
			modelMatrix.normal(normalMatrix);		// convert model matrix to normal matrix
			
			shader.enable();		
			shader.setUniform("u_mvpMatrix", mvpMatrix);
			shader.setUniform("u_modelMatrix", modelMatrix);
			shader.setUniform("u_normalMatrix", normalMatrix);

			shader.setAttribute("a_position", vertexBuffer);
			shader.setAttribute("a_normal", normalBuffer);
			shader.setUniform("u_diffuseMaterial", diffuseMaterial);
			shader.setUniform("u_ambientIntensity", ambientIntensity);
			shader.setUniform("u_lightIntensity", lightIntensity);
			shader.setUniform("u_lightDirection", lightDirection);
			
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			glDrawArrays(GL_TRIANGLES, 0, vertices.length);
			break;
	
		case Week8.WIREFRAME_PASS:
			// draw the wireframe
			
			wireframeShader.enable();
			wireframeShader.setUniform("u_mvpMatrix", mvpMatrix);
			wireframeShader.setAttribute("a_position", vertexBuffer);
			wireframeShader.setUniform("u_colour", wireColour);
			
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glDrawArrays(GL_TRIANGLES, 0, vertices.length);
			break;
		}
	}

	private static final float ROTATION_SPEED = TAU / 10;
	
	public void update(float deltaTime, InputManager input) {
		float angle = deltaTime * ROTATION_SPEED;
		getMatrix().rotateY(angle);
	}
}
