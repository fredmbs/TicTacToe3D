package com.unibh.dcet.ccc.android.tictactoe3d.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

/** A grid is a topologically rectangular array of vertices.
*
* This grid class is customized for the vertex data required for this
* example.
*
* The vertex and index data are held in VBO objects because on most
* GPUs VBO objects are the fastest way of rendering static vertex
* and index data.
*
*/

public class Cylinder {
   // Size of vertex data elements in bytes:
   final static int FLOAT_SIZE = 4;
   final static int CHAR_SIZE = 2;

   // Vertex structure:
   // float x, y, z;
   // float nx, ny, nx;

   final static int VERTEX_SIZE = 6 * FLOAT_SIZE;
   final static int VERTEX_NORMAL_BUFFER_INDEX_OFFSET = 3;

   private int mVertexBufferObjectId;
   private int mElementBufferObjectId;

   // These buffers are used to hold the vertex and index data while
   // constructing the grid. Once createBufferObjects() is called
   // the buffers are nulled out to save memory.

   private ByteBuffer mVertexByteBuffer;
   private FloatBuffer mVertexBuffer;
   private CharBuffer mIndexBuffer;

   private int mP;
   private float mH, mDy;
   private int mIndexCount;

   /**
    * This is not the fastest way to check for an extension, but fine if
    * we are only checking for a few extensions each time a context is created.
    * @param gl
    * @param extension
    * @return true if the extension is present in the current context.
    */
   static private boolean checkIfContextSupportsExtension(GL10 gl, String extension) {
       String extensions = " " + gl.glGetString(GL10.GL_EXTENSIONS) + " ";
       // The extensions string is padded with spaces between extensions, but not
       // necessarily at the beginning or end. For simplicity, add spaces at the
       // beginning and end of the extensions string and the extension string.
       // This means we can avoid special-case checks for the first or last
       // extension, as well as avoid special-case checks when an extension name
       // is the same as the first part of another extension name.
       return extensions.indexOf(" " + extension + " ") >= 0;
   }

   static public boolean checkIfContextSupportsCubeMap(GL10 gl) {
       return checkIfContextSupportsExtension(gl, "GL_OES_texture_cube_map");
   }
   
   public Cylinder(float r, float h, int p) {

	   if (p < 0 || p >= 65536) {
           throw new IllegalArgumentException("p");
       }

	   int size = (p*2)+2;

       if (size < 0 || size >= 65536) {
           throw new IllegalArgumentException("(p*4)+2");
       }

       mH = h;
       mP = p;
       mDy = ((float)mH)/2;

       
       mVertexByteBuffer = ByteBuffer.allocateDirect(VERTEX_SIZE * size)
       .order(ByteOrder.nativeOrder());
       mVertexBuffer = mVertexByteBuffer.asFloatBuffer();

       int indexCount = 12*(p + 1);
       mIndexCount = indexCount;
       mIndexBuffer = ByteBuffer.allocateDirect(CHAR_SIZE * indexCount)
       .order(ByteOrder.nativeOrder()).asCharBuffer();

       /*
        * Initialize triangle list mesh. (0 < n < p)
        *
        *          [c0]
        *           /\   
        *          /  \  
        *         /    \ 
        *     [a n]----[b n] ...
        *       |    /   |
        *       |   /    |
        *       |  /     |
        *    [a n+1]---[b n+1] ...
        *         \    /
        *          \  /
        *           \/
        *          [c1]
        *
        */

       {
           int i = 0;
           char c0 = (char)(0);
           char c1 = (char)(p + 1);
    	   char a0, b0, a1, b1;
           for (char n = 0; n < p; n++) {
        	   a0 = (char)(n + 1);
        	   if (a0 == p) {
        		   b0 = 1;
            	   a1 = (char)(p + n + 2);
            	   b1 = (char)(p + 2);
        	   } else {	   
        		   b0 = (char)(a0 + 1);
            	   a1 = (char)(p + n + 2);
            	   b1 = (char)(a1 + 1);
        	   }

        	   mIndexBuffer.put(i++, c0);
        	   mIndexBuffer.put(i++, a0);
        	   mIndexBuffer.put(i++, b0);

        	   mIndexBuffer.put(i++, a0);
        	   mIndexBuffer.put(i++, a1);
        	   mIndexBuffer.put(i++, b0);

        	   mIndexBuffer.put(i++, b0);
        	   mIndexBuffer.put(i++, a1);
        	   mIndexBuffer.put(i++, b1);
        	   
        	   mIndexBuffer.put(i++, a1);
        	   mIndexBuffer.put(i++, c1);
        	   mIndexBuffer.put(i++, b1);
           }
       }
       
	   float theta = (float) ((2 * Math.PI) / p); 
       float c = (float) Math.cos(theta);  //precalculate the sine and cosine
       float s = (float) Math.sin(theta);
       float t;

       float x = r;  //we start at angle = 0 
       float z = 0; 

       for(int i = 0; i < p; i++) 
       { 
    	   set(i, x, mDy, z, x/r, 0, z/r);

    	   //apply the rotation matrix
    	   t = x;
    	   x = c * x - s * z;
    	   z = s * t + c * z;
       } 

       setCenter();
   }


   public void setCenter() {

	   mVertexBuffer.position(0);
       mVertexBuffer.put(0);
       mVertexBuffer.put(-mDy);
       mVertexBuffer.put(0);
       mVertexBuffer.put(0);
       mVertexBuffer.put(-1);
       mVertexBuffer.put(0);
       
       int index = 1 + mP;

       mVertexBuffer.position(index * VERTEX_SIZE / FLOAT_SIZE);
       mVertexBuffer.put(0);
       mVertexBuffer.put(mDy);
       mVertexBuffer.put(0);
       mVertexBuffer.put(0);
       mVertexBuffer.put(-1);
       mVertexBuffer.put(0);
	   
   }
   
   public void set(int p, float x, float y, float z, float nx, float ny, float nz) {

	   if (p < 0 || p >= mP) {
           throw new IllegalArgumentException("p");
       }

       int index = 1 + p;

       mVertexBuffer.position(index * VERTEX_SIZE / FLOAT_SIZE);
       mVertexBuffer.put(x);
       mVertexBuffer.put(-y);
       mVertexBuffer.put(z);
       mVertexBuffer.put(nx);
       mVertexBuffer.put(0);
       mVertexBuffer.put(nz);
       
       index = 2 + p + mP;

       mVertexBuffer.position(index * VERTEX_SIZE / FLOAT_SIZE);
       mVertexBuffer.put(x);
       mVertexBuffer.put(+y);
       mVertexBuffer.put(z);
       mVertexBuffer.put(nx);
       mVertexBuffer.put(0);
       mVertexBuffer.put(nz);
       
   }

   public void createBufferObjects(GL gl) {
       checkGLError(gl);
       // Generate a the vertex and element buffer IDs
       int[] vboIds = new int[2];
       GL11 gl11 = (GL11) gl;
       gl11.glGenBuffers(2, vboIds, 0);
       mVertexBufferObjectId = vboIds[0];
       mElementBufferObjectId = vboIds[1];

       // Upload the vertex data
       gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertexBufferObjectId);
       mVertexByteBuffer.position(0);
       gl11.glBufferData(GL11.GL_ARRAY_BUFFER, mVertexByteBuffer.capacity(), mVertexByteBuffer, GL11.GL_STATIC_DRAW);
       gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

       gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mElementBufferObjectId);
       mIndexBuffer.position(0);
       gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndexBuffer.capacity() * CHAR_SIZE, mIndexBuffer, GL11.GL_STATIC_DRAW);
       gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

       // We don't need the in-memory data any more
       mVertexBuffer = null;
       mVertexByteBuffer = null;
       mIndexBuffer = null;
       checkGLError(gl);
   }

   public void draw(GL10 gl) {
       checkGLError(gl);
       GL11 gl11 = (GL11) gl;

       // define os buffers ativos
       gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertexBufferObjectId);
       gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mElementBufferObjectId);

       // configura os ponteiros de vértices e normais (em relação aos buffers)
       gl11.glVertexPointer(3, GL10.GL_FLOAT, VERTEX_SIZE, 0);
       //gl11.glNormalPointer(GL10.GL_FLOAT, VERTEX_SIZE, VERTEX_NORMAL_BUFFER_INDEX_OFFSET * FLOAT_SIZE);

       // ativa os buffers
       gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
       //gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

       // desenha usando indices de vértices, os vertices e seus vetores normais 
       gl11.glDrawElements(GL10.GL_TRIANGLES, mIndexCount, GL10.GL_UNSIGNED_SHORT, 0);
       
       // desativa os buffers
       gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
       //gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

       // anula a definição de buffers (para não afetar outros desenhos)
       gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
       gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
       
       // verifica erros
       checkGLError(gl);
   }

  static void checkGLError(GL gl) {
      int error = ((GL10) gl).glGetError();
      if (error != GL10.GL_NO_ERROR) {
    	  Log.d("Erro", "Grid: GLError 0x" + Integer.toHexString(error));
          //throw new RuntimeException("GRID: GLError 0x" + Integer.toHexString(error));
      }
  }
   
}

