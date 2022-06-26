# springboot_lotto

쇼핑몰의 기능 api를 제공합니다.

* 주요기능 *
- 스프링 시큐리티와 Jwt를 이용한 토큰 인증 로그인방식
- 스프링 aop를 사용하여 관리자 전용 api구성
- iamport 결제 모듈을 이용한 테스트결제
- 예외메서드를 커스텀하여 주문 예외 발생 시 자동 결제취소 방식
- 그 외 장바구니, 쿠폰, 상품crud기능


DB 관계도
![스크린샷 2022-06-23 오후 3 23 19](https://user-images.githubusercontent.com/91492469/175825724-c384fc72-7a82-4db2-ad9b-63505332735d.png)


Response Json

로그인 
![스크린샷 2022-06-23 오후 1 21 00](https://user-images.githubusercontent.com/91492469/175825630-ca376153-f68a-4fb5-9882-01a74f052284.png)

상품 추가
![스크린샷 2022-06-23 오후 1 30 38](https://user-images.githubusercontent.com/91492469/175825664-26acd62f-dcdd-409a-87a2-dd5624a19c47.png)

쿠폰 등록
![스크린샷 2022-06-23 오후 1 38 21](https://user-images.githubusercontent.com/91492469/175825680-b5c003a8-d0c6-4101-b2ec-fdfe28877fb5.png)

쿠폰 발행
![스크린샷 2022-06-23 오후 1 39 12](https://user-images.githubusercontent.com/91492469/175825709-c81b4d0f-e53f-4492-92fa-6387e9f22957.png)

등등
