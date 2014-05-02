import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        int [][] connTable = {
//            { 0, 5, 0, 4 },
//            { 0, 0, 2, 0 },
//            { 0, 0, 0, 0 },
//            { 0, 0, 0, 0 }
//        };
//        int [] weights = { 1, 2, 3, 4 };
//        int[][] connTable = {
//                {0,12,12,12,12,12,12,0,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 8, 8,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,8,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,8,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,8,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 12,12,12,12,12,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,8,8,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,8,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,8,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,12,12,12,12,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,8,8,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,8},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,12,12},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0},
//                {0,0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0}};
//
//
//        int[] weights = {8, 4, 4, 4, 4, 4, 6, 3, 3, 3, 3, 4, 2, 2, 2, 2, 1, 1};
        int [][] connTable = {
                {0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        int [] weights = { 3, 2, 2, 1, 2, 2, 3, 6, 3, 4, 1, 3, 5, 8 };
        ArrayList<Task> tasks = Scheduler.getTasks(connTable, weights);
        System.out.println(tasks);
        Task task = tasks.iterator().next();
        System.out.println(tasks.get(1));
        System.out.println(tasks.get(1).getParents());
        int level_id = 0;
        Levels levels = Scheduler.getLevels(tasks);
        for (Level level : levels) {
            System.out.print(level_id + ": ");
            System.out.println(level);
            level_id++;
        }
//        new DrawFrame("Original", levels);
//        Schedule basic = Scheduler.makeBasic(levels);
//        new DrawFrame("Basic", basic);
//
//        Schedule basicWithTopology = Scheduler.makeBasicWithTopology(levels);
//        new DrawFrame("With topology", basicWithTopology);

        Schedule advancedMethod = Scheduler.makeAdvanced(levels);
        new DrawFrame("Advanced", advancedMethod);
    }
}
