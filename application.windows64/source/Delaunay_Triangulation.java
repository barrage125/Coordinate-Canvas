import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Delaunay_Triangulation extends PApplet {


/**
 *Program that creates triangles between user created vertices using the Delaunay Triangulation Algorithm.
 *Vertices have a set rgb color and randomized height.
 *Triangle fill determined by average color of its vertices.
 *Height used to create a visually stimulating experience from finished artwork.
 *
 *CONTROLS
 *  Colors: 1 - Black
 *          2 - White
 *          3 - Red
 *          4 - Green
 *          5 - Blue
 *
 *  Toggles: T - Triangles
 *           P - Vertices
 *           O - Circle Boundaries
 *           C - Click Color-Selector
 *           M - 3D
 * Up/Down - Increase/Decrease Background Darkness
 * Left/Right - Change 3D direction of rotation
 * Space to reset project
 * Enter to save image
 * Escape to exit
 */

boolean points = true;
boolean triangles = true;
boolean circles = false;
boolean threeD = false;
boolean colors = false;
ArrayList<triPoint> pointsList = new ArrayList<triPoint>(); //List of Vertices
ArrayList<triang> triangleList = new ArrayList<triang>();   //List of Triangles
int rGlobal = 255;
int gGlobal = 255;
int bGlobal = 255;
int rot = 0;
int dir = 1;
float bg = 50;
float heightRange = 250;
float xScreen, yScreen, zScreen;
String fileType = ".png";
  
public void setup() {
  background(bg);
  
  //size(480, 120);
  xScreen = width/2;
  yScreen = height/2;
  zScreen = 0;
}

public void draw() {
  translate(xScreen, yScreen, zScreen);
  if (colors) {
    rot = 0;
    background(bg);
    if (triangles) {
      for (int i = 0; i < triangleList.size(); i++) {
        if (circles) {
        noFill();
        circleBound circ = new circleBound(triangleList.get(i).getP1(), triangleList.get(i).getP2(), triangleList.get(i).getP3());
        ellipse(circ.getX(), circ.getY(), 2 * circ.getRadius(), 2 * circ.getRadius());
        }
        triang t = triangleList.get(i);
        fill(t.getR(), t.getG(), t.getB());
        triangle(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
      }
    }
    if (points) {
      for (int i = 0; i < pointsList.size(); i++) {
        triPoint p = pointsList.get(i);
        fill(p.getR(), p.getG(), p.getB());
        rect(p.getX() - 5, p.getY() - 5, 10, 10);
      }
    }
    
    fill(rGlobal, gGlobal, bGlobal);
    rect(mouseX - ((width/2) + 50), mouseY - ((height/2) + 50), 10, 10);
  } else {
    stroke(0);
    if (threeD) {
      rotateX(11 * PI/32);
      rotateZ(rot * PI/1024);
      rot += dir;
      background(bg);
      if (triangles) {
        for (int i = 0; i < triangleList.size(); i++) {
              if (circles) {
              noFill();
              circleBound circ = new circleBound(triangleList.get(i).getP1(), triangleList.get(i).getP2(), triangleList.get(i).getP3());
              ellipse(circ.getX(), circ.getY(), 2 * circ.getRadius(), 2 * circ.getRadius());
              }
              triang t = triangleList.get(i);
              fill(t.getR(), t.getG(), t.getB());
              
              beginShape();
              vertex(t.getX1(), t.getY1(), t.getZ1());
              vertex(t.getX2(), t.getY2(), t.getZ2());
              vertex(t.getX3(), t.getY3(), t.getZ3());
              endShape();
        }
      }
      if (points) {
        for (int i = 0; i < pointsList.size(); i++) {
            triPoint p = pointsList.get(i);
            fill(p.getR(), p.getG(), p.getB());
            rect(p.getX() - 5, p.getY() - 5, 10, 10);
        }
      }
    }
    else {
      rot = 0;
      background(bg);
      if (triangles) {
        for (int i = 0; i < triangleList.size(); i++) {
              if (circles) {
              noFill();
              circleBound circ = new circleBound(triangleList.get(i).getP1(), triangleList.get(i).getP2(), triangleList.get(i).getP3());
              ellipse(circ.getX(), circ.getY(), 2 * circ.getRadius(), 2 * circ.getRadius());
              }
              triang t = triangleList.get(i);
              fill(t.getR(), t.getG(), t.getB());
              triangle(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
        }
      }
      if (points) {
        for (int i = 0; i < pointsList.size(); i++) {
            triPoint p = pointsList.get(i);
            fill(p.getR(), p.getG(), p.getB());
            rect(p.getX() - 5, p.getY() - 5, 10, 10);
        }
      }
      
      fill(rGlobal, gGlobal, bGlobal);
      rect(mouseX - ((width/2) + 5), mouseY - ((height/2) + 5), 10, 10);
    }
  }
}

public void keyPressed() {
  if (key == ' ') {
    pointsList = new ArrayList<triPoint>();
    triangleList = new ArrayList<triang>();
    background(bg);
  }
  
  if (key == '1') {
    rGlobal = 0;
    gGlobal = 0;
    bGlobal = 0;
  }
  
  if (key == '2') {
    rGlobal = 255;
    gGlobal = 255;
    bGlobal = 255;
  }
  
  if (key == '3') {
    rGlobal = 255;
    gGlobal = 0;
    bGlobal = 0;
  }
  
  if (key == '4') {
    rGlobal = 0;
    gGlobal = 255;
    bGlobal = 0;
  }
  
  if (key == '5') {
    rGlobal = 0;
    gGlobal = 0;
    bGlobal = 255;
  }
  
  if (key == CODED) {
    if (keyCode == DOWN) {
      bg+=5;
      if (bg > 255) bg = 255;
    } else if (keyCode == UP) {
      bg-=5;
      if (bg < 0) bg = 0;
    } else if (keyCode == LEFT) {
      dir = 1;
    } else if (keyCode == RIGHT) {
      dir = -1;
    } 
  }
  
  if (key == 'w') rGlobal+=20;
  if (rGlobal > 255) rGlobal = 255;
  
  if (key == 'q') rGlobal-=20;
  if (rGlobal < 0) rGlobal = 0;
  
  if (key == 's') gGlobal+=20;
  if (gGlobal > 255) gGlobal = 255;
  
  if (key == 'a') gGlobal-=20;
  if (gGlobal < 0) gGlobal = 0;
  
  if (key == 'x') bGlobal+=20;
  if (bGlobal > 255) bGlobal = 255;
  
  if (key == 'z') bGlobal-=20;
  if (bGlobal < 0) bGlobal = 0;
  
  if (key == 'c') colors = !colors;
  
  if (key == 't') triangles = !triangles;
  
  if (key == 'p') points = !points;
  
  if (key == 'o') circles = !circles;
  
  if (key == 'm') threeD = !threeD;
  
  if (keyCode == ENTER) {
    String fileName = "Delaunay ";
    int m = month();
    int d = day();
    int y = year();
    int h = hour();
    int min = minute();
    int s = second();
    fileName += m + "_" + d + "_" + y; //adda date
    fileName += " " + h + "-" + min + "-" + s; //add time
    fileName += fileType; //add extension
    save(fileName);
  }
}

public void mousePressed() {
  
  if (colors) {
    rot = 0;
    background(bg);
    if (triangles) {
      for (int i = 0; i < triangleList.size(); i++) {
            if (circles) {
            noFill();
            circleBound circ = new circleBound(triangleList.get(i).getP1(), triangleList.get(i).getP2(), triangleList.get(i).getP3());
            ellipse(circ.getX(), circ.getY(), 2 * circ.getRadius(), 2 * circ.getRadius());
            }
            triang t = triangleList.get(i);
            fill(t.getR(), t.getG(), t.getB());
            triangle(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
      }
    }
    if (points) {
      for (int i = 0; i < pointsList.size(); i++) {
          triPoint p = pointsList.get(i);
          fill(p.getR(), p.getG(), p.getB());
          rect(p.getX() - 5, p.getY() - 5, 10, 10);
      }
    }
    
    fill(rGlobal, gGlobal, bGlobal);
    rect(mouseX - ((width/2) + 50), mouseY - ((height/2) + 50), 10, 10);
    
    int c = get(mouseX - (0), mouseY - (0));
    rGlobal = c >> 16 & 0xFF;
    gGlobal = c >> 8 & 0xFF;
    bGlobal = c & 0xFF;
    fill(rGlobal, gGlobal, bGlobal);
  } else {                                                                                                                                      //http://introcs.cs.princeton.edu/java/36inheritance/Delaunay.java.html
    triangleList = new ArrayList<triang>();
    pointsList.add(new triPoint(mouseX - width/2, mouseY - height/2, rGlobal, gGlobal, bGlobal));
    for (int i = 0; i < pointsList.size(); i++) {
      for (int j = i+1; j < pointsList.size(); j++) {
        for (int k = j+1; k < pointsList.size(); k++) {
          boolean isTriangle = true;
          for (int a = 0; a < pointsList.size(); a++) {
            if (a == i || a == j || a == k);
            else {
              circleBound circ = new circleBound(pointsList.get(i), pointsList.get(j), pointsList.get(k));
              if (sq(pointsList.get(a).getX() - circ.getX()) + sq(pointsList.get(a).getY() - circ.getY()) < sq(circ.getRadius()) || circ.getRadius() == -1) {
                 isTriangle = false;
                 a = pointsList.size();
              }
            }
          }
          if (isTriangle) {
              triangleList.add(new triang(pointsList.get(i), pointsList.get(j), pointsList.get(k)));
          }
        }
      }
    }
  }
}

private class triPoint {
  float x;
  float y;
  float z;
  float r;
  float g;
  float b;
  private triPoint(float x, float y, int r, int g, int b) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.g = g;
    this.b = b;
    z = random(heightRange);
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }
  
  public float getZ() {
    return z;
  }
  
  public float getR() {
    return r;
  }
  
  public float getG() {
    return g;
  }
  
  public float getB() {
    return b;
  }
}

private class triang {
  float x1;
  float y1;
  float z1;
  float x2;
  float y2;
  float z2;
  float x3;
  float y3;
  float z3;
  float r;
  float g;
  float b;
  triPoint p1;
  triPoint p2;
  triPoint p3;
  private triang(triPoint p1, triPoint p2, triPoint p3) {
    x1 = p1.getX();
    y1 = p1.getY();
    z1 = p1.getZ();
    x2 = p2.getX();
    y2 = p2.getY();
    z2 = p2.getZ();
    x3 = p3.getX();
    y3 = p3.getY();
    z3 = p3.getZ();
    r = (p1.getR() + p2.getR() + p3.getR()) / 3;
    g = (p1.getG() + p2.getG() + p3.getG()) / 3;
    b = (p1.getB() + p2.getB() + p3.getB()) / 3;
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
  }
  
  public float getX1() {
    return x1;
  }
  
  public float getY1() {
    return y1;
  }
  
  public float getZ1() {
    return z1;
  }
  
  public float getX2() {
    return x2;
  }
  
  public float getY2() {
    return y2;
  }
  
  public float getZ2() {
    return z2;
  }
  
  public float getX3() {
    return x3;
  }
  
  public float getY3() {
    return y3;
  }
  
  public float getZ3() {
    return z3;
  }
  
  public float getR() {
    return r;
  }
  
  public float getG() {
    return g;
  }
  
  public float getB() {
    return b;
  }
  
  public triPoint getP1() {
    return p1;
  }
  
  public triPoint getP2() {
    return p2;
  }
  
  public triPoint getP3() {
    return p3;
  }
}

public float quadrat(float a, float b, float c) {
  float x1 = 0;
  float x2 = 0;
  if ((sq(b) - 4 * (a * c)) < 0) {
    x1 = -1;
    x2 = -1;
    print("error");
  } else {
    x1 = ((-1 * b) + sqrt(sq(b) - 4 * (a * c))) / (2 * a);
    x2 = ((-1 * b) - sqrt(sq(b) - 4 * (a * c))) / (2 * a);
  }
  return x1 > x2 ? x1 : x2;
}

private class circleBound {
  float radius;
  float x;
  float y;
  private circleBound(triPoint p1, triPoint p2, triPoint p3){           //"Equation of a Circle from 3 Points (2 dimensions)", found on http://paulbourke.net/geometry/circlesphere/
    float x1 = p1.getX();
    float y1 = p1.getY();
    float x2 = p2.getX();
    float y2 = p2.getY();
    float x3 = p3.getX();
    float y3 = p3.getY();
    float m1 = 0;
    float m2 = 0;
    float m3 = 0;
    boolean flag = false;
    if ((y1 == y2 && y2 == y3) || (x1 == x2 && x2 == x3)) {
      x = 0;
      y = 0;
      radius = -1;
    } if (((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3)) || x3 == x1 && y3 == y1) {
      x = 0;
      y = 0;
      radius = -1;
    }
    else {
      if (x2-x1 == 0) {
        m2 = (x2 - x3)/(y3 - y2);
        m3 = (x3 - x1)/(y1 - y3);
        y = (y2 + y1) / 2;
        //x = quadrat(1, (2 * x1), (sq(x1) + sq(y1) - (2 * y1 * y) + sq(y)));
        //x = (((y3 - y2) * (y - ((y2 + y3) / 2))) / (x2 - x3));
        x = (((y3 + y1) / 2) - ((y3 + y2) / 2) + (m2 * ((x2 + x3) / 2)) - (m3 * ((x3 + x1) / 2))) / (m2 - m3); //BC
        //x = (((y3 - y2) * (y - ((y2 + y3) / 2))) / (x2 - x3)) + ((x2 + x3) / 2);
        flag = true;
      } else if (x3-x2 == 0) {
        m1 = (x1 - x2)/(y2 - y1);
        m3 = (x3 - x1)/(y1 - y3);
        y = (y3 + y2) / 2;
        //x = quadrat(1, (2 * x1), (sq(x1) + sq(y1) - (2 * y1 * y) + sq(y)));
        //x = (((y1 - y3) * (y - ((y3 + y1) / 2))) / (x3 - x1));
        x = (((y1 + y2) / 2) - ((y1 + y3) / 2) + (m3 * ((x3 + x1) / 2)) - (m1 * ((x1 + x2) / 2))) / (m3 - m1); //CA
        //x = (((y2 - y1) * (y - ((y1 + y2) / 2))) / (x1 - x2)) + ((x1 + x2) / 2);
        flag = true;
      } else if (x1-x3 == 0) {
        m1 = (x1 - x2)/(y2 - y1);
        m2 = (x2 - x3)/(y3 - y2);
        y = (y1 + y3) / 2;
        //x = quadrat(1, (2 * x1), (sq(x1) + sq(y1) - (2 * y1 * y) + sq(y)));
        //x = (((y2 - y1) * (y - ((y1 + y2) / 2))) / (x1 - x2));
        x = (((y2 + y3) / 2) - ((y2 + y1) / 2) + (m1 * ((x1 + x2) / 2)) - (m2 * ((x2 + x3) / 2))) / (m1 - m2); //AB
        //x = (((y2 - y1) * (y - ((y1 + y2) / 2))) / (x1 - x2)) + ((x1 + x2) / 2);
        flag = true;
      }
      if (y2-y1 == 0) {
        x = (x2 + x1) / 2;
        m2 = (y3 - y2)/(x3 - x2);
        y = (-1 * ((x - ((x2 + x3) / 2)) / m2)) + ((y2 + y3) / 2);
        flag = true;
      } else if (y3-y2 == 0) {
        x = (x3 + x2) / 2;
        m1 = (y2 - y1)/(x2 - x1);
        y = (-1 * ((x - ((x1 + x2) / 2)) / m1)) + ((y1 + y2) / 2);
        flag = true;
      } else if (y1-y3 == 0) {
        x = (x1 + x3) / 2;
        m1 = (y2 - y1)/(x2 - x1);
        y = (-1 * ((x - ((x1 + x2) / 2)) / m1)) + ((y1 + y2) / 2);
        flag = true;
      }
      if (!flag) {
        m1 = (y2 - y1)/(x2 - x1);
        m2 = (y3 - y2)/(x3 - x2);
        x = (m1 * m2 * (y1 - y3) + m2 * (x1 + x2) - m1 * (x2 + x3)) / (2 * (m2 - m1));
        y = (-1 * ((x - ((x1 + x2) / 2)) / m1)) + ((y1 + y2) / 2);
      }
      radius = sqrt(sq(x - x1) + sq(y - y1));
    }
  }
  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }
  
  public float getRadius() {
    return radius;
  }
}
  public void settings() {  fullScreen(P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Delaunay_Triangulation" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
