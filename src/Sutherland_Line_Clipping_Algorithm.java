import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Sutherland_Line_Clipping_Algorithm implements GLEventListener {

	static GLProfile profile = GLProfile.get(GLProfile.GL2);
	static GLCapabilities capabilities = new GLCapabilities(profile);
	// The canvas
	static GLCanvas glcanvas = new GLCanvas(capabilities);

	static int lineNumber = 1000;
	static double[] x1 = new double[lineNumber];
	static double[] y1 = new double[lineNumber];
	static double[] x2 = new double[lineNumber];
	static double[] y2 = new double[lineNumber];

	static double div = 1000;
	static double Xmax = 600;
	static double Xmin = -600;
	static double Ymax = 400;
	static double Ymin = -400;

	static int top = 8;
	static int bot = 4;
	static int right = 2;
	static int left = 1;

	static int outcode, outcode_0, outcode_1;

	public static void main(String[] args) {
		// getting the capabilities object of GL2 profile

		for (int i = 0; i < lineNumber; i++) {
			x1[i] = randomNumber(-1000, 1000);
			y1[i] = randomNumber(-1000, 1000);
			x2[i] = randomNumber(-1000, 1000);
			y2[i] = randomNumber(-1000, 1000);
		}

		Sutherland_Line_Clipping_Algorithm l = new Sutherland_Line_Clipping_Algorithm();
		// creating frame
		glcanvas.addGLEventListener(l);
		glcanvas.setSize(800, 600);

		final JFrame frame = new JFrame("straight Line");
		// adding canvas to frame
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

	}

	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glColor3f(0f, 0f, 1f);
		gl.glBegin(GL2.GL_LINES);// static field
		gl.glVertex2d(Xmax / div, Ymax / div);
		gl.glVertex2d(Xmin / div, Ymax / div);
		gl.glEnd();

		gl.glBegin(GL2.GL_LINES);// static field
		gl.glVertex2d(Xmin / div, Ymax / div);
		gl.glVertex2d(Xmin / div, Ymin / div);
		gl.glEnd();
		//
		gl.glBegin(GL2.GL_LINES);// static field
		gl.glVertex2d(Xmin / div, Ymin / div);
		gl.glVertex2d(Xmax / div, Ymin / div);
		gl.glEnd();
		////
		gl.glBegin(GL2.GL_LINES);// static field
		gl.glVertex2d(Xmax / div, Ymin / div);
		gl.glVertex2d(Xmax / div, Ymax / div);
		gl.glEnd();

		// Cohen_sutherland Algo Starting
		// System.out.println( "LineNum"+ lineNumber);
		double x = 0, y = 0;
		for (int i = 0; i < lineNumber; i++) {
			System.out.println("NewLine " + i);
			// int x0 = 20;
			// int y0 = 30;
			// int x1 = 0;
			// int y1 = 95;
			int x0 = (int) x1[i];
			int y0 = (int) y1[i];
			int x1 = (int) x2[i];
			int y1 = (int) y2[i];

			outcode_0 = make_code(x0, y0);
			outcode_1 = make_code(x1, y1);
			// System.out.println("or" + (outcode_0 | outcode_1));
			// System.out.println("&" + (outcode_0 | outcode_1));
			boolean flag = false;
			while (1 < 2) {
				if ((outcode_0 | outcode_1) == 0) { // accepted
					// System.out.println( "Line "+i+" Fully Accepted");
					if (flag == true) { // prints only partially
						gl.glBegin(GL2.GL_LINES);// static field
						gl.glColor3f(0f, 1f, 0f);
						gl.glVertex2d(x0 / div, y0 / div);
						gl.glVertex2d(x1 / div, y1 / div);
						gl.glEnd();
					}
					break;
				} else if ((outcode_0 & outcode_1) > 0) { // rejected
//					 System.out.println( "Line "+i+" Fully Rejected");
//					 gl.glBegin (GL2.GL_LINES);//static field
//					 gl.glColor3f(1f,0f,0f);
//					 gl.glVertex2d(x0/div,y0/div);
//					 gl.glVertex2d(x1/div,y1/div);
//					 gl.glEnd();
					break;
				} else {
					// partially accepted
					System.out.println("Line " + i + " Partially Accepted");
					flag = true;
					gl.glColor3f(0f, 0f, 1f); // white
					gl.glBegin(GL2.GL_LINES);// static field
					gl.glVertex2d(x0 / div, y0 / div);
					gl.glVertex2d(x1 / div, y1 / div);
					gl.glEnd();
					// System.out.println("// partially accepted"+i);
					///////////
					if (outcode_0 > 0) {
						outcode = outcode_0;
					} else {
						outcode = outcode_1;
					}
					// System.out.println("outcode "+outcode);

					if ((outcode & top) != 0) { // point is above Ymax
						y = (double) Ymax;
						x = x0 + ((y - y0) / (y1 - y0)) * (x1 - x0);
					} else if ((outcode & bot) != 0) { // point is below Ymin
						y = (double) Ymin;
						x = x0 + ((y - y0) / (y1 - y0)) * (x1 - x0);
					} else if ((outcode & right) != 0) { // point is below Xmax
						x = (double) Xmax;
						y = y0 + ((x - x0) / (x1 - x0)) * (y1 - y0);
					} else if ((outcode & left) != 0) { // point is below Xmin
						x = (double) Xmin;
						y = y0 + ((x - x0) / (x1 - x0)) * (y1 - y0);
					}

					// Now x & y is clipped value
					System.out.println("X,Y = (" + x + "," + y+")");

					if (outcode == outcode_0) {
						x0 = (int) x;
						y0 = (int) y;
						outcode_0 = make_code(x0, y0);
					} else {
						x1 = (int) x;
						y1 = (int) y;
						outcode_1 = make_code(x1, y1);
					}
					////////////
				} // partially accepted end

			} // End of while

			///////////////
		} // For loop End
	}

	static int make_code(int x, int y) {
		int outcode = 0;
		if (y > Ymax) {
			outcode += top;
		} else if (y < Ymin) {
			outcode += bot;
		}
		if (x > Xmax) {
			outcode += right;
		} else if (x < Xmin) {
			outcode += left;
		}
		return outcode;
	}

	static double randomNumber(double min, double max) {
		double range = (max - min);
		return (Math.random() * range) + min;
	}

	public void dispose(GLAutoDrawable arg0) {
		// method body
	}

	public void init(GLAutoDrawable drawable) {
		// method body
		// 4. drive the display() in a loop
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// method body
	}
	// end of main
}// end of classimport javax.media.opengl.GL2;
