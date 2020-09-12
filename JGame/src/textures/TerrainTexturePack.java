package textures;

public class TerrainTexturePack {
	private TerrainTexture backgroundtexture;
	private TerrainTexture rtexture;
	private TerrainTexture btexture;
	private TerrainTexture gtexture;
	public TerrainTexturePack(TerrainTexture backgroundtexture, TerrainTexture rtexture, TerrainTexture btexture,
			TerrainTexture gtexture) {
		super();
		this.backgroundtexture = backgroundtexture;
		this.rtexture = rtexture;
		this.btexture = btexture;
		this.gtexture = gtexture;
	}
	public TerrainTexture getBackgroundtexture() {
		return backgroundtexture;
	}
	public TerrainTexture getRtexture() {
		return rtexture;
	}
	public TerrainTexture getBtexture() {
		return btexture;
	}
	public TerrainTexture getGtexture() {
		return gtexture;
	}
	
}
