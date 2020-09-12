package Model;

import textures.ModelTexture;

public class TextureModel {
	private RawModel model;
	private ModelTexture texture;
	public TextureModel(RawModel model,ModelTexture texture)
	{
		this.model = model;
		this.texture = texture;	
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
