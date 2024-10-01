package cleancode.minesweeper.tobe;

public class Cell {

    private final String sign;

    private Cell(String sign) {
        this.sign = sign;
    }

    public static Cell of(String sign) {
        return new Cell(sign);
    }

    public boolean doesNotEqualSign(String sign) {
        return !equalsSign(sign);
    }

    public boolean equalsSign(String sign) {
        return this.sign.equals(sign);
    }

    public String getSign() {
        return this.sign;
    }
}
