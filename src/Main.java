import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int [][] connTable = {
            { 0, 5, 0, 4 },
            { 0, 0, 2, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        int [] weights = { 1, 2, 3, 4 };
        ArrayList<Task> tasks = Scheduler.getTasks(connTable, weights);
        System.out.println(tasks);
        Task task = tasks.iterator().next();
        System.out.println(task);
        System.out.println(task.getChildren());
        int level_id = 0;
        for (Level level : Scheduler.getLevels(tasks)) {
            System.out.print(level_id + ": ");
            System.out.println(level);
            level_id++;
        }
        Levels levels = Scheduler.getLevels(tasks);
        new DrawFrame("Original", levels);
        Schedule basic = Scheduler.makeBasic(levels);
        new DrawFrame("Basic", basic);

        Schedule basicWithTopology = Scheduler.makeBasicWithTopology(levels);
        new DrawFrame("With topology", basicWithTopology);
    }
}
