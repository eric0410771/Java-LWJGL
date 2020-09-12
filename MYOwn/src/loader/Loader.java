package loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entity.RawModel;

public class Loader {
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVao(float []vertices,float []texturecoords,float []normals,int []indices)
	{
		int vaoid = createVao();
		vaos.add(vaoid);
		bindIndicesBuffer(indices);
		storeDataInAttribList(0,3,vertices);
		storeDataInAttribList(1,2,texturecoords);
		storeDataInAttribList(2,3,normals);
		unBindVao();
		return new RawModel(vaoid,indices.length);
	}
	public RawModel loadToVao(float []position)
	{
		int vaoid = createVao();
		vaos.add(vaoid);
		this.storeDataInAttribList(0, 2, position);
		unBindVao();
		return new RawModel(vaoid,position.length/2);
	}
	public int createVao() {
		int vaoid = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoid);
		return vaoid;
	}
	public void bindIndicesBuffer(int []indices)
	{
		int vboid = GL15.glGenBuffers();
		vbos.add(vboid);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,vboid);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
		
	}
	
	public IntBuffer storeDataInIntBuffer(int []data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void storeDataInAttribList(int index,int datanumber,float [] data)
	{
		int vboid = GL15.glGenBuffers();
		vbos.add(vboid);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboid);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index,datanumber,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	public FloatBuffer storeDataInFloatBuffer(float []data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void unBindVao() {
		GL30.glBindVertexArray(0);
	}
	public void cleanUP() {
		for(int vao:vaos)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo:vbos)
			GL15.glDeleteBuffers(vbo);
	}
	public int loadTexture(String filename)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG",new FileInputStream(new File("res/"+filename+".png")));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D,GL14.GL_TEXTURE_LOD_BIAS,-0.4f);
		}catch(IOException e)
		{
		}
		
		int textureId = texture.getTextureID();
		
		textures.add(textureId);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		return textureId;
	}
}
