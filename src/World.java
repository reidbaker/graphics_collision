import processing.core.PApplet;
import processing.core.PImage;


public class World extends Particle{
    PImage worldPNG;

    World(float x, float y, float z) {
        super(null, x, y, z, 20);
        worldPNG = GraphicsCollision.getInstance().loadImage("../map.png");
    }

    public void draw(PApplet c){
        c.lights();
        c.noStroke();
        c.fill(128);
        c.translate( c.width/2, c.height/2);
        makeSphere(c, 150, 10, worldPNG );
        c.translate( -c.width/2, -c.height/2);
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
            c.vertex( (R * cosj * sini)+pos.x,
                    (R * -cosi)+pos.y,
                    (R * sinj * sini)+pos.z, // x, y, z
                    tex.width-j * tex.width / 360, i * tex.height / 180 // u, v
                    );

            c.vertex( (R * cosjp * sini)+pos.x,
                    (R * -cosi)+pos.y,
                    (R * sinjp * sini)+pos.z,
                    tex.width-(j + step ) * tex.width / 360, i * tex.height / 180
            );

            c.vertex( (R * cosj * sinip)+pos.x,
                    (R * -cosip)+pos.y,
                    (R * sinj * sinip)+pos.z,
                    tex.width-j * tex.width / 360, (i + step ) * tex.height / 180
                    );

            c.vertex((R * cosjp * sinip)+pos.x,
                    (R * -cosip)+pos.y,
                    (R * sinjp * sinip)+pos.z,
                    tex.width-(j + step ) * tex.width / 360, (i + step ) * tex.height / 180
                    );

            // Lower hemisphere
            c.vertex( (R * cosj * sini)+pos.x,
                    (R * cosi)+pos.y,
                    (R * sinj * sini)+pos.z,
                    tex.width-j * tex.width / 360, tex.height - i * tex.height / 180
                    );

            c.vertex( (R * cosjp * sini)+pos.x,
                    (R * cosi)+pos.y,
                    (R * sinjp * sini)+pos.z,
                    tex.width-(j + step ) * tex.width / 360, tex.height - i * tex.height / 180
                    );

            c.vertex( (R * cosj * sinip)+pos.x,
                    (R * cosip)+pos.y,
                    (R * sinj * sinip)+pos.z,
                    tex.width-j * tex.width / 360, tex.height - (i+step) * tex.height / 180
                    );

            c.vertex( (R * cosjp * sinip)+pos.x,
                    (R * cosip)+pos.y,
                    (R * sinjp * sinip)+pos.z,
                    tex.width-(j+step) * tex.width / 360, tex.height - (i+step) * tex.height / 180
                    );
          }
        }
        c.endShape();
      }


}
