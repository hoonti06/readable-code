package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.sign.EmptyCellSignProvider;
import cleancode.minesweeper.tobe.sign.FlagCellSignProvider;
import cleancode.minesweeper.tobe.sign.LandMineCellSignProvider;
import cleancode.minesweeper.tobe.sign.NumberCellSIgnProvider;
import cleancode.minesweeper.tobe.sign.UncheckedCellSignProvider;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {
        final String alphabets = generateColAlphabets(board);

        System.out.println("    " + alphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                CellPosition cellPosition = CellPosition.of(row, col);

                CellSnapshot cellSnapshot = board.getSnapshot(cellPosition);
                String cellSign = decideCellSignFrom(cellSnapshot);

                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * [코드 스멜]
     * 만약 enum에 타입이 하나 더 추가되면 그에 따른 대응을 개발자가 해줘야 한다
     */
    private String decideCellSignFrom(CellSnapshot snapshot) {
        CellSnapshotStatus status = snapshot.getStatus();
        if (status == CellSnapshotStatus.EMPTY) {
            EmptyCellSignProvider cellSignProvider = new EmptyCellSignProvider();
            return cellSignProvider.provide(snapshot);
        }
        if (status == CellSnapshotStatus.LAND_MINE) {
            LandMineCellSignProvider cellSignProvider = new LandMineCellSignProvider();
            return cellSignProvider.provide(snapshot);
        }
        if (status == CellSnapshotStatus.FLAG) {
            FlagCellSignProvider cellSignProvider = new FlagCellSignProvider();
            return cellSignProvider.provide(snapshot);
        }
        if (status == CellSnapshotStatus.NUMBER) {
            NumberCellSIgnProvider cellSignProvider = new NumberCellSIgnProvider();
            return cellSignProvider.provide(snapshot);
        }
        if (status == CellSnapshotStatus.UNCHECKED) {
            UncheckedCellSignProvider cellSignProvider = new UncheckedCellSignProvider();
            return cellSignProvider.provide(snapshot);
        }
        throw new IllegalArgumentException("확인할 수 없는 셀입니다.");
    }

    private String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(Objects::toString)
                .toList();
        return String.join(" ", alphabets);
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showCommentForUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

}
