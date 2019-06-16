package ru.cft.focusstart.turlyun;

class Functions {
    static long function(int i) {
        long localSum = 0;
        for (int j = 0; j < i; j++) {
            localSum++;
        }

        return (long) Math.pow(localSum, 0.5);
    }
}