package groupas.queue;


public class Main {
    public static void main(String[] args) {
//        var queue = new DoubleEndedQueue<Integer>();
//
//        queue.pushFirst(1);
//        queue.pushFirst(2);
//        queue.pushFirst(3);
//        queue.pushFirst(4);
//        queue.pushFirst(5);
//        queue.pushFirst(6);
//        queue.popFirst();
//        queue.pushLast(-2);
//
//        System.out.println(queue);
//        queue.popLast();
//        System.out.println(queue);

        var stringQ = new DoubleEndedQueue<String>();

        stringQ.pushFirst("first");
        stringQ.pushFirst("second");
        stringQ.pushLast("third");
        stringQ.pushLast("fourth");
        System.out.println(stringQ);
    }
}