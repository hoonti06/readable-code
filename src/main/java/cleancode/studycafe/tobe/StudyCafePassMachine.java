package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            runMachine();
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void runMachine() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();

        StudyCafePassType studyCafePassType = selectStudyCafePassType();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> passCandidates = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.hasSamePassType(studyCafePassType))
                .toList();

        StudyCafePass selectedPass = selectStudyCafePass(passCandidates);

        if (studyCafePassType == StudyCafePassType.HOURLY) {
            outputHandler.showPassOrderSummary(selectedPass, null);
        } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
            outputHandler.showPassOrderSummary(selectedPass, null);
        } else if (studyCafePassType == StudyCafePassType.FIXED) {
            StudyCafeLockerPass lockerPass = selectLockerPass(selectedPass);

            boolean lockerSelection = false;
            if (lockerPass != null) {
                outputHandler.askLockerPass(lockerPass);
                lockerSelection = inputHandler.getLockerSelection();
            }

            if (lockerSelection) {
                outputHandler.showPassOrderSummary(selectedPass, lockerPass);
            } else {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }
        }
    }

    private StudyCafePass selectStudyCafePass(List<StudyCafePass> passCandidates) {
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private StudyCafePassType selectStudyCafePassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafeLockerPass selectLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        return lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
    }

}
