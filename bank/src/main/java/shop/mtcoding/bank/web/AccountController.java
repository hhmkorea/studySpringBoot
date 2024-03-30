package shop.mtcoding.bank.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountListRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.service.AccountService;
import shop.mtcoding.bank.service.AccountService.AccountDepositReqDto;
import shop.mtcoding.bank.service.AccountService.AccountDepositRespDto;

/**
 * packageName    : shop.mtcoding.bank.web
 * fileName       : AccountController
 * author         : dotdot
 * date           : 2024-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-28        dotdot       최초 생성
 */


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountReqDto accountReqDto, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        // BindingResult : AOP가 알아서 잡아서 처리
        // AuthenticationPrincipal : 세션에 있는 값 처리
        AccountSaveRespDto accountRespDto = accountService.saveAccount(accountReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountRespDto), HttpStatus.CREATED);
    }

    // 인증이 필요하고, account 테이블 데이타 다 주세요!!
    // 인증이 필요하고, account 테이블에 login한 유저의 계좌만 주세요.
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser) {

        AccountListRespDto accountListRespDto = accountService.findListByUser(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "유저별 계좌 목록보기 성공", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/s/account/{number}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long number, @AuthenticationPrincipal LoginUser loginUser) {
        accountService.deleteAccount(number, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 완료", null), HttpStatus.OK);
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountDepositReqDto accountDepositReqDto, BindingResult bindingResults) {
        AccountDepositRespDto accountDepositResqDto = accountService.depositAccount(accountDepositReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 입금 완료", accountDepositResqDto), HttpStatus.CREATED);
    }
}
