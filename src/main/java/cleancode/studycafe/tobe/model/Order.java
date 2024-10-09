package cleancode.studycafe.tobe.model;

public class Order {
    private final StudyCafePass studyCafePass;
    private final StudyCafeLockerPass lockerPass;

    private Order(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
        this.studyCafePass = studyCafePass;
        this.lockerPass = lockerPass;
    }

    public static Order of(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
        return new Order(studyCafePass, lockerPass);
    }

    public int getTotalPrice() {
        return studyCafePass.getPrice() - getDiscountPrice() + (lockerPass != null ? lockerPass.getPrice() : 0);
    }

    public int getDiscountPrice() {
        double discountRate = studyCafePass.getDiscountRate();
        return (int) (studyCafePass.getPrice() * discountRate);
    }

}
