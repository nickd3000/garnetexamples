import com.physmo.garnet.Garnet;
//import old.TestGameContainer;

public class TestGarnet {
    public static void main(String[] args) {
        Garnet garnet = new Garnet(new ContainerSpriteSample(), 640, 480);
        //Garnet garnet = new Garnet(new ContainerRegularFontSample(),640,480);
        //Garnet garnet = new Garnet(new Boids(),640,480);
        garnet.init();
        garnet.run();
    }
}
