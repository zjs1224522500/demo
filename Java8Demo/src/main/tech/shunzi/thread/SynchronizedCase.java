package tech.shunzi.thread;

public class SynchronizedCase {

    private int count = 5;

    public int getCount()
    {
        return count;
    }

    public void subCount()
    {
        count--;
    }

    class TestThread extends Thread {


        @Override
        public void run() {
            count++;
            System.out.println(currentThread().getName() + " count:" + count);
        }
    }


    public static void main(String[] args) {
        SynchronizedCase testCase = new SynchronizedCase();

        TestThread testThread = testCase.new TestThread();
        for(int i = 0; i < 5; i++)
        {
            Thread childThread = new Thread(testThread, "Thread-" + i);
            childThread.start();
        }
    }
}
