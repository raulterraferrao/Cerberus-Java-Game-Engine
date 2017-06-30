package gerenciadorDeFontes;

import java.io.File;

public class FontType {

	private int textureAtlas;
	private Malha loader;


	public FontType(int textureAtlas, File fontFile) {
		this.textureAtlas = textureAtlas;
		this.loader = new Malha(fontFile);
	}

	public int getTextureAtlas() {
		return textureAtlas;
	}


	public TextMeshData loadText(GUIText text) {
		return loader.criarMalha(text);
	}

}
