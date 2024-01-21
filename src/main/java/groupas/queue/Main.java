package groupas.queue;


public class Main {
    public static void main(String[] args) {
        var queue = new DoubleEndedQueue<Integer>();

        queue.pushFirst(1);
        queue.pushFirst(2);
        queue.pushFirst(3);
        queue.pushFirst(4);
        queue.pushFirst(5);
        queue.pushFirst(6);
        queue.pushFirst(7);
        queue.pushFirst(8);
        System.out.println(queue);
        queue.pushFirst(9);
        System.out.println(queue);

//        var stringQ = new DoubleEndedQueue<String>();
//        stringQ.pushFirst("first");
//        stringQ.pushFirst("second");
//        stringQ.pushLast("third");
//        stringQ.pushLast("fourth");
//        System.out.println(stringQ);

    }
}