package game.addammo.InternalTests;

public class TaskQueue<T> {
    
    public TaskQueue() {
    }

    public class Task{
        private Task lastTask, nextTask;
        private TaskEvent<T> event;

        public Task(){

        }

        public void submit(Task task){

        }
        public Task getTask(){
            return this;
        }
    }
    public interface TaskEvent<T>{
        void execute(T data);
    }
}
