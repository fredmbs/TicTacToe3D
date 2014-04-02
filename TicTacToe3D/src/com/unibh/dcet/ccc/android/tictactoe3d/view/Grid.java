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

public class Grid {
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

   private int mW;
   private int mH;
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
   
   public Grid(int w, int h) {
       if (w < 0 || w >= 65536) {
           throw new IllegalArgumentException("w");
       }
       if (h < 0 || h >= 65536) {
           throw new IllegalArgumentException("h");
       }
       if (w * h >= 65536) {
           throw new IllegalArgumentException("w * h >= 65536");
       }

       mW = w;
       mH = h;
       int size = w * h;

       mVertexByteBuffer = ByteBuffer.allocateDirect(VERTEX_SIZE * size)
       .order(ByteOrder.nativeOrder());
       mVertexBuffer = mVertexByteBuffer.asFloatBuffer();

       int quadW = mW - 1;
       int quadH = mH - 1;
       int quadCount = quadW * quadH;
       int indexCount = quadCount * 6;
       mIndexCount = indexCount;
       mIndexBuffer = ByteBuffer.allocateDirect(CHAR_SIZE * indexCount)
       .order(ByteOrder.nativeOrder()).asCharBuffer();

       /*
        * Initialize triangle list mesh.
        *
        *     [0]-----[  1] ...
        *      |    /   |
        *      |   /    |
        *      |  /     |
        *     [w]-----[w+1] ...
        *      |       |
        *
        */

       {
           int i = 0;
           for (int y = 0; y < quadH; y++) {
               for (int x = 0; x < quadW; x++) {
                   char a = (char) (y * mW + x);
                   char b = (char) (y * mW + x + 1);
                   char c = (char) ((y + 1) * mW + x);
                   char d = (char) ((y + 1) * mW + x + 1);

                   mIndexBuffer.put(i++, a);
                   mIndexBuffer.put(i++, c);
                   mIndexBuffer.put(i++, b);

                   mIndexBuffer.put(i++, b);
                   mIndexBuffer.put(i++, c);
                   mIndexBuffer.put(i++, d);
               }
           }
       }
   }

   public void set(int i, int j, float x, float y, float z, float nx, float ny, float nz) {
       if (i < 0 || i >= mW) {
           throw new IllegalArgumentException("i");
       }
       if (j < 0 || j >= mH) {
           throw new IllegalArgumentException("j");
       }

       int index = mW * j + i;

       mVertexBuffer.position(index * VERTEX_SIZE / FLOAT_SIZE);
       mVertexBuffer.put(x);
       mVertexBuffer.put(y);
       mVertexBuffer.put(z);
       mVertexBuffer.put(nx);
       mVertexBuffer.put(ny);
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
       gl11.glNormalPointer(GL10.GL_FLOAT, VERTEX_SIZE, VERTEX_NORMAL_BUFFER_INDEX_OFFSET * FLOAT_SIZE);

       // ativa os buffers
       gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
       gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

       // desenha usando indices de vértices, os vertices e seus vetores normais 
       gl11.glDrawElements(GL10.GL_TRIANGLES, mIndexCount, GL10.GL_UNSIGNED_SHORT, 0);
       
       // desativa os buffers
       gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
       gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

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

