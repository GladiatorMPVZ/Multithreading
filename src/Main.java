public class Main {
    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.a();
        main.b();
    }

    public void a() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() -
                startTime) + " ms.");

    }

    public void b() throws InterruptedException {
        int size = 10_000_000;
        int half = size / 2;
        float[] arr1 = new float[size];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = 1.0f;
        }
        long startTime1 = System.currentTimeMillis();
        float[] arrOne = new float[half];
        float[] arrTwo = new float[half];
        System.arraycopy(arr1, 0, arrOne, 0, half);
        System.arraycopy(arr1, half, arrTwo, 0, half);
        Thread thread1 = new Thread( () -> {
            for (int i = 0; i < arrOne.length; i++) {
                arrOne[i] = (float)(arrOne[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread( () -> {
            for (int i = 0; i < arrTwo.length; i++) {
                arrTwo[i] = (float)(arrTwo[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        float[] mergedArr = new float[size];
        System.arraycopy(arrOne, 0, mergedArr, 0, half);
        System.arraycopy(arrTwo, 0, mergedArr, half, half);
        System.out.println("Two thread time: " + (System.currentTimeMillis() -
                startTime1) + " ms.");

    }
}