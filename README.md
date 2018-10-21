Today
==============
 하루를 취침 시간부터 기상 시간을 제외하고, 일정한 간격의 시간 주기(30분, 1시간 ..)마다 텍스트 입력 팝업창을 띄워주고, 팝업이 뜰 때마다 현재 내가 하고 있던 일을 입력하여, 하루가 끝나면 오늘 하루 종일 어떤 일을 했는지 객관적으로 볼 수 있는 안드로이드 어플리케이션

Requirement
------------------------
> **Note:**

> - 하루를 기록 할 수 있도록 구현
> - 과거의 하루를 찾아서 볼 수 있도록 구현
> - 정해진 시간(30분, 1시간 등등..)마다 알람이 울리도록 설정
> - 시간 간격을 조절 할 수 있도록 설정
> - 텍스트를 입력 할 수 있는 팝업창이 뜨고 텍스트를 입력함으로서 기록
> - 방해 금지 모드
> - 기상 시간, 취침 시간 설정

Development Progress
------------------------
> **Note:**

> - ButterKnife 적용
> - SQLite ProtoType 구현 완료
> 시간과 업무를 데이터베이스에 저장
> - AlarmManger 기본적인 동작 구현
> - SettingFragment 구현 및 작동 확인
> - Text 팝업 ProtoType 구현
> - MainActivity DB ListView 실시간 갱신 후 노출 기능 구현(18.06.15)
> - AlarmManger SystemClock에서 RealTime Clock으로 교체(18.06.26)
> - 1시간 단위로 알람이 올 수 있도록 구현(30분 확장성 고려하여 구현)(18.06.26)
> - View에 뿌려지는 DB의 데이터가 하루 단위로 뿌려지도록 구현(18.06.27)
> - 알람 7시부터 11시사이에 알람이 올 수 있도록 구현(18.06.27)
> - 가로형 달력 UI 적용(18.07.01)
> - 달력의 날짜 선택에 따라 해당하는 DB 불러오기 구현(구현 더 필요함)(18.07.01)


TODO:
------------------------
> **Note:**

> - 하고 있던 일을 등록한 시간과 해당하는 일의 정보를 관리하는 Model Class 생성
> > **!** 몇 월 며칠을 parameter로 받아서 처리를 할 수 있도록
> > **!** 하루를 DB에 저장 및 관리하는 역할
> 
> - TestCase 작성

> **6.16:**

> - 스크롤 할 시 이 전 날의 DB가 노출되도록 구현(Medium completion)
> - 리스트뷰는 아이폰 캘린더 기준으로 UI 구현 (Medium completion)
> - 처음엔 7시부터 11시에 알람이 올 수 있도록 구현
쿼리를 바꿔야함. 현재는 전체지만 그 날 당일꺼만 가져올 수 있도록  (Complete)
> - 한시간 단위로 알람이 올 수 있도록 구현 (Complete)
> - * 위의 경우로 구현하더라도 확장성있게 구현하도록 만들기 

> **7.01:**

> - UI 조금 깔끔하게 다듬기
> - 날짜에 따라 데이터 불러오는 함수 다듬기
> - 내용 나오는 리스트뷰 칸 늘리기
> - * 실제 사용이 가능한 같은 UI로 만들기 *
> - * 실제 사용이 가능하게 버그 잡기 *
> 
### Libray

> - Butterknife : 안드로이드의 View와 Activity를 보다 쉽게 연결해주고, 코드의 반복을 줄여주는 라이브러리
> http://jakewharton.github.io/butterknife/
> - Horizontal Calendar : 안드로이드 UI 라이브러리 중 하나로, 가로형 달력을 표현해주는 UI 라이브러리
> https://github.com/Mulham-Raee/Horizontal-Calendar 

### Development Environment

> - Language : JAVA
> - IDE : Android Studio
> - DB : SQLite

