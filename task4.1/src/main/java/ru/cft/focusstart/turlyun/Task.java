package ru.cft.focusstart.turlyun;
public class Task implements Runnable {

    private int minArgument;
    private int maxArgument;
    private Function function;
    private long result;

    Task(int minArgument, int maxArgument, Function function) {
        this.minArgument = minArgument;
        this.maxArgument = maxArgument;
        this.function = function;
    }

    long getResult() {
        return result;
    }

    @Override
    public void run() {
        for (int i = minArgument; i <= maxArgument; i++) {
            this.result += function.getResult(i);
            if (i == (maxArgument - minArgument) / 3 + minArgument) {
                System.out.println("33 % counted in " + Thread.currentThread().getName());
            }
            if (i == (maxArgument - minArgument) * 2 / 3 + minArgument) {
                System.out.println("66 % counted in " + Thread.currentThread().getName());
            }
        }
        System.out.printf("Result from %s is %d%n", Thread.currentThread().getName(), getResult());
    }
}