package texture;

public class TerrainTexturePack {
	private TerrainTexture backgroundtexture;
	private TerrainTexture rtexture;
	private TerrainTexture gtexture;
	private TerrainTexture btexture;
	
	public TerrainTexturePack(TerrainTexture backgroundtexture,TerrainTexture rtexture,TerrainTexture gtexture,TerrainTexture btexture)
	{
		this.backgroundtexture = backgroundtexture;
		this.rtexture = rtexture;
		this.gtexture = gtexture;
		this.btexture = btexture;
	}
	public TerrainTexture getBackgroundTexture()
	{
		return backgroundtexture;
	}
	public TerrainTexture getRTexture()
	{
		return rtexture;
	}
	public TerrainTexture getGTexture()
	{
		return gtexture;
	}
	public TerrainTexture getBTexture()
	{
		return btexture;
	}
}
