package gerenciadorDeFontes;


import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import renderizadorDeFontes.TextMaster;

public class GUIText {

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;
	private Vetor3f colour = new Vetor3f(0f, 0f, 0f);

	private Vetor2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

	private boolean centerText = false;

	public GUIText(String text, float fontSize, FontType font, Vetor2f position, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextMaster.loadText(this);;
	}

	public void remove() {
		TextMaster.removeText(this);
	}

	public FontType getFont() {
		return font;
	}

	public void setColour(float r, float g, float b) {
		colour.x = r;
		colour.y = g;
		colour.z = b;
	}


	public Vetor3f getColour() {
		return colour;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vetor2f getPosition() {
		return position;
	}

	public int getMesh() {
		return textMeshVao;
	}

	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	protected float getFontSize() {
		return fontSize;
	}

	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	protected boolean isCentered() {
		return centerText;
	}

	protected float getMaxLineSize() {
		return lineMaxSize;
	}


	protected String getTextString() {
		return textString;
	}

}
