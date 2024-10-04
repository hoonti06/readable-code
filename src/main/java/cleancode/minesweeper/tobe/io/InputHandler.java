package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.position.CellPosition;

public interface InputHandler {

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}
