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
                    if (child.hasLevel())
                        continue;
                    boolean all_has = true;
                    // Check every parent has it's tier
                    for (Task parent : child.getParents().keySet()) {
                        if (!parent.hasLevel() || parent.level == currLevel)
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
                int startTime = schedule.getMinStartTime(task);
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
                Processor pr = schedule.addProcessor();
                schedule.assignTask(pr, task);
            }
        }
        return schedule;
    }

    public static Schedule makeAdvanced(Levels levels) {
        Schedule schedule = new Schedule();
        // assign first level
        Level firstLevel = levels.get(0);
        for (Task task : firstLevel) {
            Processor pr = schedule.addProcessor();
            schedule.addTask(pr, task, 0);
        }
        for (Level level : levels) {
            // assign not assigned task on prev iteration
            for (Task task : level) {
                System.out.println("Task: " + task.id);
                if (!schedule.assignes.containsKey(task)) {
//                    Processor pr = schedule.addProcessor();
//                    schedule.assignTask(pr, task);
                    Task parent = task.getParents().keySet().iterator().next();
                    Processor pr = schedule.assignes.get(parent);
                    schedule.assignTask(pr, task);
                    System.out.println("task:" + task.id);
                    System.out.println("proc: " + pr.id);
                    if (task.id == 5) {
                        System.out.println(schedule.transmissions);
                    }
                }
            }
            for (Task task : level) {
                int maxStartTime = 0;
                Task selectedChild = null;
                for (Task child : task.getChildren().keySet()) {
                    // only next level
                    if (child.level != task.level + 1)
                        continue;
                    if (schedule.assignes.containsKey(child))
                        continue;
                    int minStartTime = schedule.getMinStartTime(child);
                    if (maxStartTime < minStartTime) {
                        maxStartTime = minStartTime;
                        selectedChild = child;
                    }
                }
                if (selectedChild == null)
                    continue;
                Processor pr = schedule.assignes.get(task);
                schedule.assignTask(pr, selectedChild);
            }
        }
        System.out.println("Trans: " + schedule.transmissions);
        return schedule;
    }
}
