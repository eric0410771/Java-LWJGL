package EngineTest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Model.RawModel;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class OBJLoader {

	public static RawModel loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/" + fileName + ".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find them obj's!");
			e.printStackTrace();
		}

		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] texturesArray = null;
		int[] indicesArray = null;
		line = "";
		try {
				while(line!=null)
				{
					String current[] = line.split(" ");
					if(line.startsWith("v "))
					{
						vertices.add(new Vector3f(Float.parseFloat(current[1]),Float.parseFloat(current[2]),Float.parseFloat(current[3])));
					}
					else if(line.startsWith("vn"))
					{
						normals.add(new Vector3f(Float.parseFloat(current[1]),Float.parseFloat(current[2]),Float.parseFloat(current[3])));
					}
					else if(line.startsWith("vt"))
					{
						textures.add(new Vector2f(Float.parseFloat(current[1]),Float.parseFloat(current[2])));
					}
					line = reader.readLine();
				}
				fr = new FileReader(new File("res/" + fileName + ".obj"));
				reader = new BufferedReader(fr);
				verticesArray = new float[vertices.size()*3];
				normalsArray = new float[vertices.size()*3];
				texturesArray = new float[vertices.size()*2];
				line = "";
				while(line!= null)
				{
					
					if(line.startsWith("f "))
					{
						String []current = line.split(" ");

						String[] vertex1 = current[1].split("/");
						String[] vertex2 = current[2].split("/");
						String[] vertex3 = current[3].split("/");
						
						processVertex(vertex1,vertices,indices,textures,normals,texturesArray,normalsArray,verticesArray);
						processVertex(vertex2,vertices,indices,textures,normals,texturesArray,normalsArray,verticesArray);
						processVertex(vertex3,vertices,indices,textures,normals,texturesArray,normalsArray,verticesArray);

					
					}
					line = reader.readLine();
				}
			
			reader.close();

		} catch (Exception e) {
			
		}
		indicesArray = new int[indices.size()];
		for(int i = 0; i < indices.size(); i++){
			indicesArray[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesArray, texturesArray,normalsArray, indicesArray);
	}
	
	private static void processVertex(String[] vertexData,List<Vector3f> vertices, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals,
			float[] texturesArray, float[] normalsArray,float []verticesArray){
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		
		Vector3f currentVector = vertices.get(currentVertexPointer);
		verticesArray[currentVertexPointer *3] = currentVector.x;
		verticesArray[currentVertexPointer *3 +1] = currentVector.y;
		verticesArray[currentVertexPointer *3 +2] = currentVector.z;
		if(!vertexData[1].equals(""))
		{
			Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
			texturesArray[currentVertexPointer * 2] = currentTex.x;
			texturesArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		}
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}

}