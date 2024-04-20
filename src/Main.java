import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[][] inputTable = {
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0},
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1}
        };

        int[] resultArray = {0,0,0,1,1,1,1,1};
        double[] wArray = createCoefficientArray(); //випадкове заповнення коефіцієнтів

        double theta = 0.5; //порогове значення
        double n = 0.4; //коеф. швидкості навчання
        boolean flag;
        int counter = 1;

        do {
            flag = false;
            System.out.println("\n[ Ітерація #" + counter++ +" | n = " + n + " ]--------------------------------------------------------------------------------------------------------------");
            System.out.printf("%6s%6s%6s%6s%7s%6s%6s%6s%6s%7s%5s%6s%11s%7s%8s%8s%9s%15s%n",
                    "w1", "w2", "w3", "w0", "theta", "x0", "x1", "x2", "x3", "a", "Y", "T", "n(T - Y)", "dw0", "dw1", "dw2", "dw3", "delta*theta");

            for (int i = 0; i < 8; ++i) {
                double delta;
                int Y = 0;

                double a = wArray[1]*inputTable[i][0] + wArray[2]*inputTable[i][1] + wArray[3]*inputTable[i][2] + wArray[0]; //активація

                if (a > theta)
                    Y = 1;

                if (Y == resultArray[i]) //похибка
                    delta = 0;
                else {
                    delta = n*(resultArray[i] - Y);
                    flag = true;
                }

                System.out.printf("%6.3f%6.3f%6.3f%6.3f%7.3f%6d%6d%6d%6d%7.3f%5d%6d%9.3f%9.3f%9.3f%9.3f%9.3f%9.3f%n",
                        wArray[1], wArray[2], wArray[3], wArray[0], theta, 1, inputTable[i][0], inputTable[i][1], inputTable[i][2], a, Y, resultArray[i], n*(resultArray[i]-Y),
                        delta*wArray[0], delta * wArray[1], delta * wArray[2], delta*wArray[3], delta * theta);

                updateWeights(wArray, inputTable[i], delta);
                theta += delta * theta;
            }
        } while (flag);

    }

    public static double[] createCoefficientArray(){
        double[] array = new double[4];
        Random random = new Random();
        for (int i = 0; i < array.length; ++i)
            array[i] = Math.round((random.nextDouble() * 0.98 + 0.01) * 10.0) / 10.0; //[0.01; 0.99]
        return array;
    }

    private static void updateWeights(double[] wArray, int[] input, double delta) {
        wArray[0] += delta;
        for (int j = 1; j < wArray.length; ++j) {
            wArray[j] += delta * input[j - 1];
        }
    }
}