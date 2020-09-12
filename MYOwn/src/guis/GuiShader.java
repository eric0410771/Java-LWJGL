package guis;

import org.lwjgl.util.vector.Matrix4f;

import shader.ShaderProgram;

public class GuiShader extends ShaderProgram {
	private static final String vertexfile = "src/guis/guiVertexShader.txt";
	private static final String fragmentfile = "src/guis/guiFragmentShader.txt";
	
	private static int location_transformmatrix;
	public GuiShader() {
		super(vertexfile, fragmentfile);
	}
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0,"position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformmatrix = super.getUniformLocation("transformmatrix");
	}
	
	public void loadTransformMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformmatrix, matrix);
	}
}
