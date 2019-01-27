import java.util.Random;

public class Main {

    private static final int QUEUE_COUNT = 3;
    private static final int QUEUE_CAPACITY = 7;

    /**
     * Default pseudo-random number generator of Java
     */
    private static Random randomGenerator;

    public static void main(String[] args) {
        // create a queue to store our game queues
        Queue<Queue<Integer>> queues = new Queue<>(QUEUE_COUNT);
        // create and insert game queues
        for (int i = 0; i < QUEUE_COUNT; i++) {
            // our capacity is 7
            Queue<Integer> q = new Queue<>(QUEUE_CAPACITY);
            queues.enqueue(q);
        }

        boolean turn = true; // true -> player 1, false -> player 2
        boolean allFull;
        while (true) {
            allFull = true; // reset

            // user plays
            int randomQueue = randQueue();
            int randomNumber = randNumber();

            // if random selected queue is full, select another
            while (queues.elementAt(randomQueue).isFull())
                randomQueue = randQueue();

            // enqueue number to selected queue
            queues.elementAt(randomQueue).enqueue(randomNumber);

            // print current state to screen
            System.out.println("\nUser" + (turn ? 1 : 2) + ":");
            for (int qi = 0; qi < QUEUE_COUNT; qi++) {
                Queue<Integer> q = queues.elementAt(qi);
                System.out.println("Q" + (qi + 1) + " " + q.toString());
            }

            // SEQUENCES
            // =========

            // flag for indicating if any sequence is encountered during search
            boolean hasSequence = false;

            // HORIZONTAL SEQUENCES
            for (int i = 0; i < QUEUE_COUNT; i++) {
                Queue<Integer> q = queues.elementAt(i);
                if (q.size() < 3)
                    continue;

                for (int j = 1; j < q.size() - 1; j++) {
                    // skip first and last element and check through windows across queue, sized 3
                    if (isSequence(q.elementAt(j - 1), q.elementAt(j), q.elementAt(j + 1))) {
                        hasSequence = true;
                        break;
                    }
                }

                // already found a sequence? stop.
                if (hasSequence)
                    break;
            }

            // no need to play further or check the terminal conditions
            if (hasSequence) {
                allFull = false;
                break;
            }

            // VERTICAL SEQUENCES

            // find smallest queue
            int minLength = 999;
            for (int i = 0; i < QUEUE_COUNT; i++)
                if (queues.elementAt(i).size() < minLength)
                    minLength = queues.elementAt(i).size();

            for (int i = 0; i < minLength; i++) {
                if (isSequence(queues.elementAt(0).elementAt(i), queues.elementAt(1).elementAt(i), queues.elementAt(2).elementAt(i))) {
                    hasSequence = true;
                    break;
                }
            }

            if (hasSequence) {
                allFull = false;
                break;
            }

            // are queues full?
            for (int i = 0; i < QUEUE_COUNT; i++) {
                allFull = allFull && queues.elementAt(i).isFull();
            }

            if (allFull) {
                break;
            }

            turn = !turn;
        }

        System.out.println();
        if (allFull) {
            System.out.println("Tie!");
        } else {
            System.out.println("winner: User" + (turn ? 1 : 2));
        }
    }

    private static boolean isSequence(int n1, int n2, int n3) {
        return (Math.abs((n1 - n2) + (n2 - n3)) == 2) && ((n1 > n2 && n2 > n3) || (n1 < n2 && n2 < n3));
    }

    private static int randQueue() {
        return randInt(0, 3);
    }

    private static int randNumber() {
        return randInt(1, 5);
    }

    private static int randInt(int min, int max) {
        if (randomGenerator == null)
            randomGenerator = new Random();

        return randomGenerator.nextInt(max) + min;
    }
}
