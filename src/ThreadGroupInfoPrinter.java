public class ThreadGroupInfoPrinter {

    public static void printThreadGroupInfo(final ThreadGroup group) {
        new Thread(group, () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    printGroupInfo(group, "");
                    Thread.sleep(3000); // Periodicity of information output (3 seconds)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private static void printGroupInfo(ThreadGroup group, String indent) {
        if (group == null) {
            return;
        }

        // Output of information about a group of threads
        System.out.println(indent + "Thread Group: " + group.getName() + " [Active threads: " + group.activeCount() + ", Active subgroups: " + group.activeGroupCount() + "]");

        // Output of information about streams in the group
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads, false);
        for (Thread thread : threads) {
            if (thread != null) {
                System.out.println(indent + "  Thread: " + thread.getName() + " (State: " + thread.getState() + ")");
            }
        }

        // Recursive output of information about subgroups
        ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];
        group.enumerate(groups, false);
        for (ThreadGroup subGroup : groups) {
            if (subGroup != null) {
                printGroupInfo(subGroup, indent + "  ");
            }
        }
    }
}
