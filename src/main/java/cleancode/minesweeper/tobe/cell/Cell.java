package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

    // [코드 스멜] LandMineCell 에서도 의미 없는 메서드(LandMineCell 인스턴스 자체만으로 지뢰 Cell이기 때문)
    public abstract void turnOnLandMine();

    // [코드 스멜] NumberCell 에서만 유효한 메서드
    public abstract void updateNearbyLandMineCount(int count);

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

    public void flag() {
        isFlagged = true;
    }

    public void open() {
        isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isOpened() {
        return isOpened;
    }

}
