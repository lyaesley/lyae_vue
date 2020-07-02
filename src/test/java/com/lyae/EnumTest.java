package com.lyae;

enum EnumTest {
    MONDAY, TUESDAY, SATURDAY(PayType.WEEKEND);

    private final PayType payType;

    EnumTest() { this(PayType.WEEKDAY); }
    EnumTest(PayType payType) { this.payType = payType; }

    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }

    enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate /2;
            }

        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;

            }

        };

        abstract int overtimePay(int minsWorked, int payRate);
        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay(minsWorked, payRate);

        }
    }

    public static void main(String[] args) {
        System.out.println(EnumTest.MONDAY.pay(10 * 60, 9000/60));
        System.out.println(EnumTest.TUESDAY.pay(12 * 60, 9000/60));
        System.out.println(EnumTest.SATURDAY.pay(8 * 60, 9000/60));

    }
}


