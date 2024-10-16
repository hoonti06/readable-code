package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StudyCafeSeatPassesTest {

    @DisplayName("시간제 이용권만 찾는다")
    @Test
    void findPassBy() {
        // given
        StudyCafeSeatPass pass1 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 1200, 0.2);
        StudyCafeSeatPass pass2 = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 1400, 0.7);
        StudyCafeSeatPass pass3 = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 5, 1700, 0.3);
        StudyCafeSeatPass pass4 = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 6, 2000, 0.4);

        StudyCafeSeatPasses passes = StudyCafeSeatPasses.of(List.of(pass1, pass2, pass3, pass4));

        // when
        List<StudyCafeSeatPass> foundPassList = passes.findPassBy(StudyCafePassType.HOURLY);


        // then
        assertThat(foundPassList).hasSize(2)
            .extracting("passType", "duration", "price", "discountRate")
            .containsExactlyInAnyOrder(
                tuple(StudyCafePassType.HOURLY, 2, 1200, 0.2),
                tuple(StudyCafePassType.HOURLY, 6, 2000, 0.4)
            );
    }

}
