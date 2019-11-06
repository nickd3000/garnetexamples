package old;

public class Starfield {

    private int numStars;
    private int width;
    private int height;

    int elementSize = 5;
    float [] element;
    int ox=0;
    int oy=1;
    int oz=2;

    public Starfield(int numStars, int width, int height) {
        this.numStars = numStars;
        this.width = width;
        this.height = height;
        element = new float[numStars*elementSize];
        for (int i=0;i<numStars;i++) {
            int ii = i*elementSize;
            element[ii+ox] = (float) (Math.random()*width);
            element[ii+oy] = (float) (Math.random()*height);
            element[ii+oz] = (float) (Math.random()+1);
        }
    }


}
