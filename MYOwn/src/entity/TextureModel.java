package entity;

import texture.ModelTexture;

public class TextureModel {
	private RawModel model;
	private ModelTexture texture;
	public TextureModel(RawModel model,ModelTexture texture) {
		this.texture = texture;
		this.model = model;
	}
	
	public RawModel getRawModel() {
		return model;
	}
	public ModelTexture getModelTexture() {
		return texture;
	}
}
