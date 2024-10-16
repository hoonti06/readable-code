package cleancode.studycafe.tobe.model.pass;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafePassTypeTest {

    @DisplayName("고정석 이용권은 사물함 타입이다")
    @Test
    void isLockerType() {
        // given
        StudyCafePassType studyCafePassType = StudyCafePassType.FIXED;

        // when
        boolean isLockerType = studyCafePassType.isLockerType();

        // then
        Assertions.assertThat(isLockerType).isEqualTo(true);
    }

}
