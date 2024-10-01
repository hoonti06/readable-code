package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel) {
        gameBoard = new GameBoard(gameLevel);
    }

    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        /**
         * [코드 스멜]
         * while문도 추상화 레벨이 맞지 않다
         */
        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                final String cellInput = getCellInputFromUser();
                final String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        final int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
        final int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex);
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    /**
     * [코드 스멜]
     * cell이 모두 opened 상태인지 확인 후 맞으면 gameStatus를 1로 변경하게 되어 있다
     * method 명을, 조회 개념의 check 보다는 갱신 개념의 update 쪽에 가깝다
     * 엘레강트 오브젝트에서 나오는 개념인, 조정자(manipulator)에 가깝다
     * - 빌더(buider)
     *   - 뭔가를 만들고 새로운 객체를 반환하는 메서드
     * - 조정자(manipulator)
     *   - 객체로 추상화한 엔티티를 수정하는 메서드
     *   - 변환 타입은 void
     */
    private void checkIfGameIsOver() {
        final boolean isAllOpened = gameBoard.isAllCellChecked();
        if (isAllOpened) {
            gameStatus = 1;
        }
    }

}
