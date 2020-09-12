package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entity.RawModel;

public class ObjLoader {
	public static RawModel loadObjFile(String filename,Loader loader) {
		FileReader fr = null;
		
		try {
			fr = new FileReader(new File("res/"+filename+".obj"));
		}catch(IOException e)
		{}
		BufferedReader br = new BufferedReader(fr);
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> texturecoords = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		float [] vertexarray = null;
		float [] texturearray = null;
		float [] normalarray = null;
		
		int []indexarray = null;
		
		String line;
		String []current;
		try {
			while((line = br.readLine()) != null) {
				if(line.startsWith("v ")) {
					current = line.split(" ");
					vertices.add(new Vector3f(Float.parseFloat(current[1]),Float.parseFloat(current[2]),Float.parseFloat(current[3])));
				}
				if(line.startsWith("vt")) {
					current = line.split(" ");
					texturecoords.add(new Vector2f(Float.parseFloat(current[1]),Float.parseFloat(current[2])));
				}
				if(line.startsWith("vn"))
				{
					current = line.split(" ");
					normals.add(new Vector3f(Float.parseFloat(current[1]),Float.parseFloat(current[2]),Float.parseFloat(current[3])));
				}
			}
			vertexarray = new float[vertices.size()*3];
			texturearray = new float[vertices.size()*2];
			normalarray = new float[vertices.size()*3];
			fr = new FileReader(new File("res/"+filename+".obj"));
			br = new BufferedReader(fr);
			
			line = "";
			while((line = br.readLine()) != null) {
				if(line.startsWith("f ")) {
					current = line.split(" ");
					String []group1 = current[1].split("/");
					String []group2 = current[2].split("/");
					String []group3 = current[3].split("/");
					
					processGroup(group1,vertices,texturecoords,normals,indices,vertexarray,texturearray,normalarray);
					processGroup(group2,vertices,texturecoords,normals,indices,vertexarray,texturearray,normalarray);
					processGroup(group3,vertices,texturecoords,normals,indices,vertexarray,texturearray,normalarray);
					
				}
			}
			indexarray = new int[indices.size()];
			
			for(int i  = 0 ;i<indices.size();i++)
				indexarray[i] = indices.get(i);
			
			
		}catch(IOException e)
		{
			
		}
		
		
		return loader.loadToVao(vertexarray,texturearray,normalarray,indexarray);
	}
	
	public static void processGroup(String []group,List<Vector3f>vertices,List<Vector2f> texturecoords,List<Vector3f>normals,List<Integer>indices,float[] vertexarray,float [] texturearray,float[] normalarray) {
		int currentVertexindex = Integer.parseInt(group[0])-1;
		
		indices.add(currentVertexindex);
		Vector3f currentv = vertices.get(currentVertexindex);
		vertexarray[currentVertexindex*3] = currentv.x;
		vertexarray[currentVertexindex*3+1] = currentv.y;
		vertexarray[currentVertexindex*3+2] = currentv.z;
		
		if(!group[1].equals("")) {
			Vector2f currentvt = texturecoords.get(Integer.parseInt(group[1])-1);
			texturearray[currentVertexindex*2] = currentvt.x;
			texturearray[currentVertexindex*2+1] =1-currentvt.y;
		}
		Vector3f currentvn = normals.get(Integer.parseInt(group[2])-1);
		
		normalarray[currentVertexindex*3] = currentvn.x;
		normalarray[currentVertexindex*3+1] = currentvn.y;
		normalarray[currentVertexindex*3+2] = currentvn.z;
		
	}
}
