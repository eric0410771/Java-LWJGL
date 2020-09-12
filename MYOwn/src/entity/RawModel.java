package entity;

public class RawModel {
	private int vaoid;
	private int vertexcounter;
	
	public RawModel(int vaoid,int vertexcounter)
	{
		this.vaoid = vaoid;
		this.vertexcounter = vertexcounter;
	}
	
	public int getVaoId()
	{
		return this.vaoid;
	}
	public int getVertexCounter() {
		return this.vertexcounter;
	}
}
