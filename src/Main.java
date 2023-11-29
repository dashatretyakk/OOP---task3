public class Main {
    private static final Object lock = new Object(); // Object to synchronize

    public static void main(String[] args) {

        ThreadGroup mainGroup = new ThreadGroup("Main Group");
        ThreadGroup subGroup1 = new ThreadGroup(mainGroup, "Subgroup 1");
        ThreadGroup subGroup2 = new ThreadGroup(mainGroup, "Subgroup 2");

        // Creating threads that start other threads in subgroups
        new Thread(mainGroup, () -> continuouslyStartThreads(subGroup1), "Thread Starter in Subgroup 1").start();
        new Thread(mainGroup, () -> continuouslyStartThreads(subGroup2), "Thread Starter in Subgroup 2").start();

        // Stream to display information about streams
        ThreadGroupInfoPrinter.printThreadGroupInfo(mainGroup);
    }

    private static void continuouslyStartThreads(ThreadGroup group) {
        while (true) {
            try {
                Thread.sleep((long) (Math.random() * 2000));
                String threadName = "Worker " + (int)(Math.random() * 1000);
                new Thread(group, Main::doRandomTask, threadName).start();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void doRandomTask() {
        int taskType = (int) (Math.random() * 3);
        try {
            switch (taskType) {
                case 0:
                    // Delay
                    Thread.sleep((long) (Math.random() * 1000));
                    break;
                case 1:
                    // Computational problem
                    double value = 0;
                    for (int i = 0; i < 100000000; i++) {
                        value += Math.sin(i) * Math.tan(i);
                    }
                    break;
                case 2:
                    // Synchronized block
                    synchronized (lock) {
                        // We artificially create conditions for blocking
                        Thread.sleep((long) (Math.random() * 1000) + 1000);
                    }
                    break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
