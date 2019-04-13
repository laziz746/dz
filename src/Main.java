import java.util.Arrays;

class HW5 {

    final int SIZE = 10000000;
    final int HALF = SIZE / 2;
    float[] arr = new float[SIZE];



    public static void main(String[] args) {
        HW5 hw = new HW5();
        hw.doWithoutThreads();
        hw.doWithThreads();
    }



    void doWithoutThreads() {



        for (int i = 0; i <arr.length ; i++) {
            arr[i]=1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++)
            arr[i] = (float)(arr[i] *
                    Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        System.out.println("Первый способ " + (System.currentTimeMillis() - a));

    }

    void doWithThreads() {
        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];


        Arrays.fill(arr, 1);//TODO либо так заполнить массив "еденицами". Не понимаю, как более правильнее
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread one = new Thread(new CalcArray(a1, 0));
        Thread two = new Thread(new CalcArray(a2, HALF));
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Второй способ " + (System.currentTimeMillis() - a));

    }

    class CalcArray implements Runnable {
        private float[] array;
        private int shift;

        CalcArray(float[] array, int shift) {
            this.array = array;
            this.shift = shift;
        }

        @Override
        public void run() {
            for (int i = 0; i < array.length; i++)
                array[i] = (float)(array[i] *
                        Math.sin(0.2f + (i + shift) / 5) *
                        Math.cos(0.2f + (i + shift) / 5) *
                        Math.cos(0.4f + (i + shift) / 2));
        }
    }
}