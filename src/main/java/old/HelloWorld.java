package old;

import com.physmo.garnet.Texture;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class HelloWorld {

    //static String fileName = "/Users/nick/Dev/java/lwjgltest/src/main/resources/FG_Blocks.PNG";
    static String fileName = "/Users/nick/dev/java/garnettest/src/main/resources/FG_Blocks.png";

    Texture texture;
    // The window handle
    private long window;

    public static void main(String[] args) {
        new HelloWorld().run();
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");


        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);


        GL.createCapabilities();

        // Note this crashes if createCapabilities is not called first.
        texture = Texture.loadTexture(fileName);

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        float v = 0;

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            v += 0.01;

            // Set the clear color
            glClearColor(v, 0.0f, 0.0f, 0.0f);


            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            drawQuad();

            glfwSwapBuffers(window); // swap the color buffers


            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void drawQuad() {
//        if(!swapcolor)
//        {
//            glColor3f(0.0f, 1.0f, 0.0f);
//        }
//        else
//        {
//            glColor3f(0.0f, 0.0f, 1.0f);
//        }

        //texture.bind();

        float sp = (float) (Math.random() * 0.01f) + 0.9f;
        //      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glEnable(GL_TEXTURE_2D);
        //glClear(GL_COLOR_BUFFER_BIT);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glBegin(GL_QUADS);
        {
//            glVertex3f(-sp, -sp, 0.0f);
//            glVertex3f(sp, -sp, 0.0f);
//            glVertex3f(sp, sp, 0.0f);
//            glVertex3f(-sp, sp, 0.0f);
//            glVertex2i(100, 100);
//            glTexCoord2i(0, 0);
//            glTexCoord2i(0, 1);
//            glTexCoord2i(1, 1);
//            glTexCoord2i(1, 0);

            glTexCoord2f(0, 0);
            glVertex2f(-sp, -sp);
            glTexCoord2f(1, 0);
            glVertex2f(sp, -sp);
            glTexCoord2f(1, 1);
            glVertex2f(sp, sp);
            glTexCoord2f(0, 1);
            glVertex2f(-sp, sp);

        }
        glEnd();
    }

    private void textureExperimant() {

        glBindTexture(GL_TEXTURE_2D, 1);
    }

}