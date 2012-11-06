import processing.core.PApplet;
import processing.core.PImage;


public class World{
    PImage worldPNG;
    World(PApplet c, float x, float y, float z) {
        worldPNG = c.loadImage("../map.png");
    }

    public void draw(PApplet c, float a){
        c.background( 0 );
        c.lights();
        c.noStroke();
        c.fill(128);
        c.translate( c.width/2, c.height/2);

        c.pushMatrix();
        c.rotateX( (float) Math.toRadians(-30));
        c.rotateY(a);
        makeSphere(c, 150, 10, worldPNG );

        c.popMatrix();
    }

    public void makeSphere(PApplet c, int R, int step, PImage tex ) {
        c.beginShape(c.TRIANGLE_STRIP);
        c.texture( tex );
        for( int i = 0; i < 90; i+=step ) {
          float sini = (float) Math.sin( Math.toRadians( i ));
          float cosi = (float) Math.cos( Math.toRadians( i ));
          float sinip = (float) Math.sin( Math.toRadians( i + step ));
          float cosip = (float) Math.cos( Math.toRadians( i + step ));

          for( int j = 0; j < 360; j+=step ) {
            float sinj = (float) Math.sin( Math.toRadians( j ));
            float cosj = (float) Math.cos( Math.toRadians( j ));
            float sinjp = (float) Math.sin( Math.toRadians( j + step ));
            float cosjp = (float) Math.cos( Math.toRadians( j + step ));

            // Upper hemisphere
            c.vertex( R * cosj * sini, R * -cosi, R * sinj * sini, // x, y, z
                    tex.width-j * tex.width / 360, i * tex.height / 180 // u, v
                    );

            c.vertex( R * cosjp * sini, R * -cosi, R * sinjp * sini,
                    tex.width-(j + step ) * tex.width / 360, i * tex.height / 180
            );

            c.vertex( R * cosj * sinip, R * -cosip, R * sinj * sinip,
                    tex.width-j * tex.width / 360, (i + step ) * tex.height / 180
                    );

            c.vertex( R * cosjp * sinip, R * -cosip, R * sinjp * sinip,
                    tex.width-(j + step ) * tex.width / 360, (i + step ) * tex.height / 180
                    );

            // Lower hemisphere
            c.vertex( R * cosj * sini, R * cosi, R * sinj * sini,
                    tex.width-j * tex.width / 360, tex.height - i * tex.height / 180
                    );

            c.vertex( R * cosjp * sini, R * cosi, R * sinjp * sini,
                    tex.width-(j + step ) * tex.width / 360, tex.height - i * tex.height / 180
                    );

            c.vertex( R * cosj * sinip, R * cosip, R * sinj * sinip,
                    tex.width-j * tex.width / 360, tex.height - (i+step) * tex.height / 180
                    );

            c.vertex( R * cosjp * sinip, R * cosip, R * sinjp * sinip,
                    tex.width-(j+step) * tex.width / 360, tex.height - (i+step) * tex.height / 180
                    );
          }
        }
        c.endShape();
      }


}
