package game.addammo.InternalTests;

public class Test {
    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("AddAmmo test demo");
        System.out.println("version: " + TestStart.version_t);
        System.out.println("==========================");

        new Thread(new TestStart(), "preloading").start();
    }
    /*private long window;

    public void run(){

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(){

        GLFWErrorCallback.createPrint(System.err).set();

        if( ! glfwInit() ){
            throw new IllegalStateException("GLFW initialize error");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(300, 300, "test", NULL, NULL);

        if (window == NULL) throw new RuntimeException("Failed to create window");

        glfwSetKeyCallback(window, (window , key , scancode , action , mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
                glfwSetWindowShouldClose(window, true);
            }
        });

        try (MemoryStack stack = stackPush() ){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize (window , pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) >> 1,
                (vidmode.height() - pHeight.get(0)) >> 1
            );
        }

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    private void loop(){
        GL.createCapabilities();

        glClearColor(1.0f, 1.0f, 1.0f, 0.5f);

        while( ! glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }
    public static void main(String[] args) {
        new Test().run();
    }
     */

}
