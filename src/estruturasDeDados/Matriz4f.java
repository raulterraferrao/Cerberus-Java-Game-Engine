package estruturasDeDados;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;

public class Matriz4f {

	public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;
	
	public Matriz4f(){
		
		setIdentidade();
	}

	public Matriz4f setIdentidade(){
		
		return setIdentidade(this);
	}
	
	public Matriz4f setIdentidade(Matriz4f m){
		
		m.m00 = 1.0f;		m.m01 = 0.0f;		m.m02 = 0.0f;		m.m03 = 0.0f;
		m.m10 = 0.0f;		m.m11 = 1.0f;		m.m12 = 0.0f;		m.m13 = 0.0f;
		m.m20 = 0.0f;		m.m21 = 0.0f;		m.m22 = 1.0f;		m.m23 = 0.0f;
		m.m30 = 0.0f;		m.m31 = 0.0f;		m.m32 = 0.0f;		m.m33 = 1.0f;
		
		return m;
	}
	
	public static Matriz4f somar(Matriz4f esquerda, Matriz4f direita, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();

		dest.m00 = esquerda.m00 + direita.m00;
		dest.m01 = esquerda.m01 + direita.m01;
		dest.m02 = esquerda.m02 + direita.m02;
		dest.m03 = esquerda.m03 + direita.m03;
		dest.m10 = esquerda.m10 + direita.m10;
		dest.m11 = esquerda.m11 + direita.m11;
		dest.m12 = esquerda.m12 + direita.m12;
		dest.m13 = esquerda.m13 + direita.m13;
		dest.m20 = esquerda.m20 + direita.m20;
		dest.m21 = esquerda.m21 + direita.m21;
		dest.m22 = esquerda.m22 + direita.m22;
		dest.m23 = esquerda.m23 + direita.m23;
		dest.m30 = esquerda.m30 + direita.m30;
		dest.m31 = esquerda.m31 + direita.m31;
		dest.m32 = esquerda.m32 + direita.m32;
		dest.m33 = esquerda.m33 + direita.m33;

		return dest;
	}
	
	public static Matriz4f subtrair(Matriz4f esquerda, Matriz4f direita, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();

		dest.m00 = esquerda.m00 - direita.m00;
		dest.m01 = esquerda.m01 - direita.m01;
		dest.m02 = esquerda.m02 - direita.m02;
		dest.m03 = esquerda.m03 - direita.m03;
		dest.m10 = esquerda.m10 - direita.m10;
		dest.m11 = esquerda.m11 - direita.m11;
		dest.m12 = esquerda.m12 - direita.m12;
		dest.m13 = esquerda.m13 - direita.m13;
		dest.m20 = esquerda.m20 - direita.m20;
		dest.m21 = esquerda.m21 - direita.m21;
		dest.m22 = esquerda.m22 - direita.m22;
		dest.m23 = esquerda.m23 - direita.m23;
		dest.m30 = esquerda.m30 - direita.m30;
		dest.m31 = esquerda.m31 - direita.m31;
		dest.m32 = esquerda.m32 - direita.m32;
		dest.m33 = esquerda.m33 - direita.m33;

		return dest;
	}
	
	public static Matriz4f mul(Matriz4f esquerda, Matriz4f direita, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();

		float m00 = esquerda.m00 * direita.m00 + esquerda.m10 * direita.m01 + esquerda.m20 * direita.m02 + esquerda.m30 * direita.m03;
		float m01 = esquerda.m01 * direita.m00 + esquerda.m11 * direita.m01 + esquerda.m21 * direita.m02 + esquerda.m31 * direita.m03;
		float m02 = esquerda.m02 * direita.m00 + esquerda.m12 * direita.m01 + esquerda.m22 * direita.m02 + esquerda.m32 * direita.m03;
		float m03 = esquerda.m03 * direita.m00 + esquerda.m13 * direita.m01 + esquerda.m23 * direita.m02 + esquerda.m33 * direita.m03;
		float m10 = esquerda.m00 * direita.m10 + esquerda.m10 * direita.m11 + esquerda.m20 * direita.m12 + esquerda.m30 * direita.m13;
		float m11 = esquerda.m01 * direita.m10 + esquerda.m11 * direita.m11 + esquerda.m21 * direita.m12 + esquerda.m31 * direita.m13;
		float m12 = esquerda.m02 * direita.m10 + esquerda.m12 * direita.m11 + esquerda.m22 * direita.m12 + esquerda.m32 * direita.m13;
		float m13 = esquerda.m03 * direita.m10 + esquerda.m13 * direita.m11 + esquerda.m23 * direita.m12 + esquerda.m33 * direita.m13;
		float m20 = esquerda.m00 * direita.m20 + esquerda.m10 * direita.m21 + esquerda.m20 * direita.m22 + esquerda.m30 * direita.m23;
		float m21 = esquerda.m01 * direita.m20 + esquerda.m11 * direita.m21 + esquerda.m21 * direita.m22 + esquerda.m31 * direita.m23;
		float m22 = esquerda.m02 * direita.m20 + esquerda.m12 * direita.m21 + esquerda.m22 * direita.m22 + esquerda.m32 * direita.m23;
		float m23 = esquerda.m03 * direita.m20 + esquerda.m13 * direita.m21 + esquerda.m23 * direita.m22 + esquerda.m33 * direita.m23;
		float m30 = esquerda.m00 * direita.m30 + esquerda.m10 * direita.m31 + esquerda.m20 * direita.m32 + esquerda.m30 * direita.m33;
		float m31 = esquerda.m01 * direita.m30 + esquerda.m11 * direita.m31 + esquerda.m21 * direita.m32 + esquerda.m31 * direita.m33;
		float m32 = esquerda.m02 * direita.m30 + esquerda.m12 * direita.m31 + esquerda.m22 * direita.m32 + esquerda.m32 * direita.m33;
		float m33 = esquerda.m03 * direita.m30 + esquerda.m13 * direita.m31 + esquerda.m23 * direita.m32 + esquerda.m33 * direita.m33;

		dest.m00 = m00;
		dest.m01 = m01;
		dest.m02 = m02;
		dest.m03 = m03;
		dest.m10 = m10;
		dest.m11 = m11;
		dest.m12 = m12;
		dest.m13 = m13;
		dest.m20 = m20;
		dest.m21 = m21;
		dest.m22 = m22;
		dest.m23 = m23;
		dest.m30 = m30;
		dest.m31 = m31;
		dest.m32 = m32;
		dest.m33 = m33;

		return dest;
	}
	
	public static Matriz4f transladar(Vetor3f vec, Matriz4f src, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();

		dest.m30 += src.m00 * vec.x + src.m10 * vec.y + src.m20 * vec.z;
		dest.m31 += src.m01 * vec.x + src.m11 * vec.y + src.m21 * vec.z;
		dest.m32 += src.m02 * vec.x + src.m12 * vec.y + src.m22 * vec.z;
		dest.m33 += src.m03 * vec.x + src.m13 * vec.y + src.m23 * vec.z;

		return dest;
	}
	
	public static Matriz4f transladar(Vetor2f vec, Matriz4f src, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();

		dest.m30 += src.m00 * vec.x + src.m10 * vec.y;
		dest.m31 += src.m01 * vec.x + src.m11 * vec.y;
		dest.m32 += src.m02 * vec.x + src.m12 * vec.y;
		dest.m33 += src.m03 * vec.x + src.m13 * vec.y;

		return dest;
}
	
	public static Matriz4f rotacionar(float angulo, Vetor3f axis, Matriz4f src, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();
		float c = (float) Math.cos(angulo);
		float s = (float) Math.sin(angulo);
		float oneminusc = 1.0f - c;
		float xy = axis.x*axis.y;
		float yz = axis.y*axis.z;
		float xz = axis.x*axis.z;
		float xs = axis.x*s;
		float ys = axis.y*s;
		float zs = axis.z*s;

		float f00 = axis.x*axis.x*oneminusc+c;
		float f01 = xy*oneminusc+zs;
		float f02 = xz*oneminusc-ys;
		// n[3] not used
		float f10 = xy*oneminusc-zs;
		float f11 = axis.y*axis.y*oneminusc+c;
		float f12 = yz*oneminusc+xs;
		// n[7] not used
		float f20 = xz*oneminusc+ys;
		float f21 = yz*oneminusc-xs;
		float f22 = axis.z*axis.z*oneminusc+c;

		float t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
		float t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
		float t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
		float t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
		float t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
		float t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
		float t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
		float t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
		dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
		dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
		dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
		dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
		dest.m00 = t00;
		dest.m01 = t01;
		dest.m02 = t02;
		dest.m03 = t03;
		dest.m10 = t10;
		dest.m11 = t11;
		dest.m12 = t12;
		dest.m13 = t13;
		return dest;
	}
	public Matriz4f bufferizar(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m01);
		buf.put(m02);
		buf.put(m03);
		buf.put(m10);
		buf.put(m11);
		buf.put(m12);
		buf.put(m13);
		buf.put(m20);
		buf.put(m21);
		buf.put(m22);
		buf.put(m23);
		buf.put(m30);
		buf.put(m31);
		buf.put(m32);
		buf.put(m33);
		return this;
	}
	public static Matriz4f escalar(Vetor3f vec, Matriz4f src, Matriz4f dest) {
		if (dest == null)
			dest = new Matriz4f();
		dest.m00 = src.m00 * vec.x;
		dest.m01 = src.m01 * vec.x;
		dest.m02 = src.m02 * vec.x;
		dest.m03 = src.m03 * vec.x;
		dest.m10 = src.m10 * vec.y;
		dest.m11 = src.m11 * vec.y;
		dest.m12 = src.m12 * vec.y;
		dest.m13 = src.m13 * vec.y;
		dest.m20 = src.m20 * vec.z;
		dest.m21 = src.m21 * vec.z;
		dest.m22 = src.m22 * vec.z;
		dest.m23 = src.m23 * vec.z;
		return dest;
	}
	
	public float determinante() {
		float f =
			m00
				* ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32)
					- m13 * m22 * m31
					- m11 * m23 * m32
					- m12 * m21 * m33);
		f -= m01
			* ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32)
				- m13 * m22 * m30
				- m10 * m23 * m32
				- m12 * m20 * m33);
		f += m02
			* ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31)
				- m13 * m21 * m30
				- m10 * m23 * m31
				- m11 * m20 * m33);
		f -= m03
			* ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31)
				- m12 * m21 * m30
				- m10 * m22 * m31
				- m11 * m20 * m32);
		return f;
	}
	
	private static float determinante3x3(float t00, float t01, float t02,
		     float t10, float t11, float t12,
		     float t20, float t21, float t22){
		return   t00 * (t11 * t22 - t12 * t21)
      + t01 * (t12 * t20 - t10 * t22)
      + t02 * (t10 * t21 - t11 * t20);
	}
	
	public static Matriz4f inversa(Matriz4f src, Matriz4f dest) {
		float determinante = src.determinante();
		if (determinante != 0) {
			/*
			 * m00 m01 m02 m03
			 * m10 m11 m12 m13
			 * m20 m21 m22 m23
			 * m30 m31 m32 m33
			 */
			if (dest == null)
				dest = new Matriz4f();
			float determinante_inv = 1f/determinante;

			// first row
			float t00 =  determinante3x3(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
			float t01 = -determinante3x3(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
			float t02 =  determinante3x3(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
			float t03 = -determinante3x3(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
			// second row
			float t10 = -determinante3x3(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
			float t11 =  determinante3x3(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
			float t12 = -determinante3x3(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
			float t13 =  determinante3x3(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
			// third row
			float t20 =  determinante3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
			float t21 = -determinante3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
			float t22 =  determinante3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
			float t23 = -determinante3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
			// fourth row
			float t30 = -determinante3x3(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
			float t31 =  determinante3x3(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
			float t32 = -determinante3x3(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
			float t33 =  determinante3x3(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);

			// transpose and divide by the determinant
			dest.m00 = t00*determinante_inv;
			dest.m11 = t11*determinante_inv;
			dest.m22 = t22*determinante_inv;
			dest.m33 = t33*determinante_inv;
			dest.m01 = t10*determinante_inv;
			dest.m10 = t01*determinante_inv;
			dest.m20 = t02*determinante_inv;
			dest.m02 = t20*determinante_inv;
			dest.m12 = t21*determinante_inv;
			dest.m21 = t12*determinante_inv;
			dest.m03 = t30*determinante_inv;
			dest.m30 = t03*determinante_inv;
			dest.m13 = t31*determinante_inv;
			dest.m31 = t13*determinante_inv;
			dest.m32 = t23*determinante_inv;
			dest.m23 = t32*determinante_inv;
			return dest;
		} else
			return null;
	}
	public static Vetor4f transformar(Matriz4f esquerda, Vetor4f direita, Vetor4f dest) {
		if (dest == null)
			dest = new Vetor4f(1,1,1,1);

		float x = esquerda.m00 * direita.x + esquerda.m10 * direita.y + esquerda.m20 * direita.z + esquerda.m30 * direita.w;
		float y = esquerda.m01 * direita.x + esquerda.m11 * direita.y + esquerda.m21 * direita.z + esquerda.m31 * direita.w;
		float z = esquerda.m02 * direita.x + esquerda.m12 * direita.y + esquerda.m22 * direita.z + esquerda.m32 * direita.w;
		float w = esquerda.m03 * direita.x + esquerda.m13 * direita.y + esquerda.m23 * direita.z + esquerda.m33 * direita.w;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;

		return dest;
	}
	
}
