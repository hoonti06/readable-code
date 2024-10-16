package cleancode.studycafe.tobe.model.pass;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassTest {

    @DisplayName("할인금액을 계산한다")
    @Test
    void getDiscountPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 1000, 0.6);

        // when
        int discountPrice = seatPass.getDiscountPrice();

        // then
        assertThat(discountPrice).isEqualTo(600);
    }

    @DisplayName("타입과 기간이 같은 이용권이다")
    @Test
    void isSameDurationType() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 1000, 0.6);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 2, 1000);

        // when
        boolean actualResult = seatPass.isSameDurationType(lockerPass);

        // then
        assertThat(actualResult).isEqualTo(true);
    }

    @DisplayName("타입이 시간제인 경우, 사물함을 사용할 수 없다.")
    @Test
    void cannotUseLockerWhenPassTypeIsHourly() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 1000, 0.6);

        // when
        boolean b = seatPass.cannotUseLocker();

        // then
        Assertions.assertThat(b).isEqualTo(true);
    }

    @DisplayName("타입이 주간제인 경우, 사물함을 사용할 수 없다.")
    @Test
    void cannotUseLockerWhenPassTypeIsWeekly() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 1000, 0.6);

        // when
        boolean b = seatPass.cannotUseLocker();

        // then
        Assertions.assertThat(b).isEqualTo(true);
    }

    @DisplayName("타입이 고정석인 경우, 사물함을 사용할 수 있다.")
    @Test
    void cannotUseLockerWhenPassTypeIsFixed() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 2, 1000, 0.6);

        // when
        boolean b = seatPass.cannotUseLocker();

        // then
        Assertions.assertThat(b).isEqualTo(false);
    }
}
