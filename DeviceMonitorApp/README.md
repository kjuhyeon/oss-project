# Device Status Monitoring and Inspection Support System

Java Swing 기반의 장비 상태 모니터링 및 점검 지원 시스템입니다.
Conceptualization, Analysis, Design 문서의 주요 Use Case와 Class 구조에 맞춰 장비 등록, 상태 입력, 가상 상태 수신, 이상 알림, 점검 기록, 장비 목록 조회, 이력 조회, 통계 조회 기능을 구현했습니다.

## 실행 환경

* JDK 17 이상
* Windows 10/11 또는 Java 실행이 가능한 OS

## 실행 방법

### Compile

```powershell
javac -encoding UTF-8 DeviceMonitorApp.java
```

### Run

```powershell
java DeviceMonitorApp
```

## 구현 범위

* 외부 라이브러리, 서버, 데이터베이스, 실제 하드웨어 연동은 사용하지 않습니다.
* 실제 장비 상태 수신은 테스트/가상 상태 데이터 생성 방식으로 대체합니다.
* 장비 정보와 이력 데이터는 프로그램 실행 중 메모리에만 저장됩니다.
* Java Swing 기반의 단일 실행 프로그램으로 구현했습니다.

## 주요 기능

* 장비 ID, 장비명, 위치를 입력하여 장비 등록
* 선택된 장비의 온도, 진동, 전원 값을 저장하고 상태 평가
* 가상 상태 데이터를 생성하여 등록된 장비 상태 갱신
* WARNING 또는 ABNORMAL 장비를 알림 영역에 표시
* 점검 수행 시 장비 상태는 변경하지 않고 INSPECTION 이력만 생성
* 장비 목록, 상태/점검 이력, 등록 장비 수, 정상 장비 수, 주의/이상 장비 수, 누적 기록 수 조회
