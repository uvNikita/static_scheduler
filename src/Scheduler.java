import java.util.*;

public class Scheduler {
    public static ArrayList<Task> getTasks(int [][] connTable, int[] weights) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        for (int i = 0; i < weights.length; i++) {
            Task task = new Task(i, weights[i]);
            tasks.add(i, task);
        }
        for (int i = 0; i < connTable.length; i++) {
            Task parent = tasks.get(i);
            for (int j = 0; j < connTable.length; j++) {
                int weight = connTable[i][j];
                if (weight > 0) {
                    Task child = tasks.get(j);
                    child.addParent(parent, connTable[i][j]);
                    parent.addChild(child, connTable[i][j]);
                }
            }
        }
        return tasks;
    }

    public static Levels getLevels(ArrayList<Task> tasks) {
        Levels levels = new Levels();
        int currLevel = 0;
        levels.add(currLevel, new Level());
        for (Task task : tasks) {
            if (task.getParents().size() == 0) {
                levels.get(0).add(task);
                task.level = 0;
            }
        }
        boolean was_changed = true;
        while (was_changed) {
            was_changed = false;
            currLevel++;
            levels.add(currLevel, new Level());
            for (Task prevTask : levels.get(currLevel - 1)) {
                Collection<Task> children = prevTask.getChildren().keySet();
                for (Task child : children) {
                    boolean all_has = true;
                    // Check every parent has it's tier
                    for (Task parent : child.getParents().keySet()) {
                        if (!parent.hasLevel())
                        {
                            all_has = false;
                            break;
                        }

                    }
                    if (all_has) {
                        levels.get(currLevel).add(child);
                        child.level = currLevel;
                        was_changed = true;
                    }
                }
            }
        }
        levels.remove(levels.size() - 1);
        return levels;
    }

    public static Schedule makeBasic(Levels levels) {
        Schedule schedule = new Schedule();
        for (Level level : levels) {
            for (Task task : level) {
                int startTime = 0;
                for (Map.Entry<Task, Integer> pair : task.getParents().entrySet()) {
                    Task parent = pair.getKey();
                    Integer weight = pair.getValue();
                    int endTime = schedule.getStartTime(parent) + parent.weight + weight;
                    if (startTime < endTime) {
                        startTime = endTime;
                    }
                }
                Processor pr = schedule.addProcessor();
                schedule.addTask(pr, task, startTime);
            }
        }
        return schedule;
    }

    public static Schedule makeBasicWithTopology(Levels levels) {
        Schedule schedule = new Schedule();
        for (Level level : levels) {
            for (Task task : level) {
                int startTime = 0;
                for (Map.Entry<Task, Integer> pair : task.getParents().entrySet()) {
                    Task parent = pair.getKey();
                    Integer weight = pair.getValue();
                    int parentEnd = schedule.getStartTime(parent) + parent.weight;
                    int transStart = schedule.addTransmission(parentEnd, weight);
                    int endTime = transStart + weight;
                    if (startTime < endTime) {
                        startTime = endTime;
                    }
                }
                Processor pr = schedule.addProcessor();
                schedule.addTask(pr, task, startTime);
            }
        }
        return schedule;
    }
}
