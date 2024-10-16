package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @DisplayName("이용권 주문의 총 금액을 계산한다")
    @Test
    void getTotalPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2, 1000, 0.5);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 500);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int totalPrice = passOrder.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(1000);
    }

}
