package game.sqysqyi.ADDAMMOLOL;

import static game.sqysqyi.ADDAMMOLOL.AddAmmoMain.RoundStats.NONE;

import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Game;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.Relationship;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Comparator.ToDoRegister;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Executor.Executor;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Enemy;
import game.sqysqyi.ADDAMMOLOL.AddAmmoMain.Players.Player;

public class Test {
    public static void main(String[] args) {
        System.out.println(ToDoRegister.size);
    }
    
    
    /*public static void main(String[] args) {
        Player p = new Player();
        Enemy e = new Enemy();
        ToDoRegister<Relationship> toDoes = new ToDoRegister<>();
        toDoes.add(new Relationship(p, e, NONE));
        toDoes.add(new Relationship(p, e, NONE));
        toDoes.add(new Relationship(p, e, NONE));
        new Executor(toDoes);
        toDoes.reset();
    }*/

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
