package terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entity.RawModel;
import loader.Loader;
import texture.TerrainTexture;
import texture.TerrainTexturePack;

public class Terrain {
	private final float size = 800;
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturepack;
	private TerrainTexture blendmap;
	private final float Max_Height = 80;
	private final float Max_Pixel_Color = 256*256*256;
	private float [][]heights;
	
	public Terrain(int gridx,int gridz,Loader loader,TerrainTexturePack texturepack,TerrainTexture blendmap,String heightmap)
	{
		this.model = generateTerrain(loader,heightmap);
		x = gridx*size;
		z = gridz*size;
		this.texturepack = texturepack;
		this.blendmap = blendmap;
	}
	public float getX() {
		return x;
	}
	public float getZ() {
		return z;
	}
	public RawModel getRawModel()
	{
		return model;
	}
	public TerrainTexture getBlendMap() {
		return blendmap;
	}
	public TerrainTexturePack getTexturePack()
	{
		return texturepack;
	}
	public float getHeightOfTerrain(float worldX,float worldZ)
	{
		float terrainX = worldX-this.x;
		float terrainZ = worldZ-this.z;
		float squareSize = size/(float)(heights.length-1);
		
		int gridX = (int)Math.floor(terrainX/squareSize);
		int gridZ = (int)Math.floor(terrainZ/squareSize);
		
		if(gridX<0||gridX>=heights.length-1||gridZ<0||gridZ>=heights.length-1)
			return 0;
		
		float xCoords = terrainX%squareSize/squareSize;
		float zCoords = terrainZ%squareSize/squareSize;
		
		if(xCoords<=1-zCoords)
			return baryCenteric(new Vector3f(0,heights[gridZ][gridX],0),new Vector3f(0,heights[gridZ+1][gridX],1),new Vector3f(1,heights[gridZ][gridX+1],0),new Vector2f(xCoords,zCoords));
		else
			return baryCenteric(new Vector3f(0,heights[gridZ+1][gridX],1),new Vector3f(1,heights[gridZ+1][gridX+1],1),new Vector3f(1,heights[gridZ][gridX+1],0),new Vector2f(xCoords,zCoords));
	}
	public RawModel generateTerrain(Loader loader,String heightmap)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/"+heightmap+".png"));
		}catch(IOException e )
		{}
		int vertexcount = image.getHeight();
		
		int count = vertexcount*vertexcount;
		float vertices[] = new float[count*3];
		float normals[] = new float[count*3];
		float texturecoords[] = new float [count*2];
		int vertexnumber = 0;
		
		heights = new float[vertexcount][vertexcount];
		
		for(int z = 0;z<vertexcount;z++)
			for(int x = 0; x<vertexcount;x++)
			{
				float height = getHeight(x,z,image);
				heights[z][x] = height; 
				
				vertices[vertexnumber*3] = (float)x/(float)(vertexcount-1)*size;
				vertices[vertexnumber*3+1] = height;
				vertices[vertexnumber*3+2] = (float)z/(float)(vertexcount-1)*size;
				Vector3f normal = caculateNormal(x,z,image);
				normals[vertexnumber*3] = normal.x;
				normals[vertexnumber*3+1] = normal.y;
				normals[vertexnumber*3+2] = normal.z;
				
				texturecoords[vertexnumber*2] = (float)x/(float)(vertexcount-1);
				texturecoords[vertexnumber*2+1] = (float)z/(float)(vertexcount-1);
				vertexnumber++;
			}
		
		int indices[] = new int [6*(vertexcount-1)*(vertexcount-1)];
		
		int index = 0 ;
		int topLeft;
		int topRight;
		int bottomLeft;
		int bottomRight;
		
		for(int z = 0; z<vertexcount-1;z++)
		{
			for(int x = 0;x<vertexcount-1;x++)
			{
				topLeft = z*(vertexcount)+x;
				topRight = topLeft+1;
				bottomLeft = (z+1)*(vertexcount)+x;
				bottomRight = bottomLeft+1;
				
				indices[index++] = topLeft;
				indices[index++] = bottomLeft;
				indices[index++] = topRight;
				indices[index++] = bottomLeft;
				indices[index++] = bottomRight;
				indices[index++] = topRight;
			}
		}
		return loader.loadToVao(vertices, texturecoords, normals, indices);
	}
	public Vector3f caculateNormal(int x,int z,BufferedImage image)
	{
		float heightl = getHeight(x-1,z,image);
		float heightr = getHeight(x+1,z,image);
		float heightd = getHeight(x,z-1,image);
		float heightu = getHeight(x,z+1,image);
		Vector3f normal = new Vector3f(heightl-heightr,2f,heightd-heightu);
		normal.normalise();
		return normal;
		
	}
	public float baryCenteric(Vector3f p1,Vector3f p2,Vector3f p3,Vector2f position)
	{
		float det = (p1.x-p3.x)*(p2.z-p3.z)-(p2.x-p3.x)*(p1.z-p3.z);
		float f1 = ((p2.z-p3.z)*(position.x-p3.x)+(p3.x-p2.x)*(position.y-p3.z))/det;
		float f2 = ((p3.z-p1.z)*(position.x-p3.x)+(p1.x-p3.x)*(position.y-p3.z))/det;
		float f3 = 1-f2-f1;
		return f1*p1.y+f2*p2.y+f3*p3.y;
	}
	public float getHeight(int x,int z,BufferedImage image)
	{
		if(x<0||x>=image.getHeight()||z<0||z>=image.getHeight())
			return 0;
		float height = (float)image.getRGB(x,z);
		height += Max_Pixel_Color;
		height /= (Max_Pixel_Color);
		height *= Max_Height;
		return height;
	}
}
