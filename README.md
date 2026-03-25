## 📦 Le-ECommerce

</br>
----------------------
🧾 Overview
</br>
이 프로젝트는 MSA(MicroService Architecture) 기반의 전자상거래 시스템으로,  </br>
Kafka를 활용한 이벤트 기반 데이터 처리, 분산 추적, 모니터링, 그리고 컨테이너 기반 배포를 적용 및 실험한 프로젝트입니다.
</br>
-----------------

⚙️ Key Features
**MSA** 기반 서비스 분리 및 독립 배포
**Kafka 기반 이벤트 드리븐 아키텍처** 구현
Kafka **Connector를 통한 DB 동기화**
**FeignClient** 활용 **서비스 간 통신**
**Zipkin**을 활용한 **분산 트레이싱**
**Prometheus + Actuator** 기반 모니터링
Docker 기반 **동일 네트워크 그룹 컨테이너 환경** 구성

-----------

🎯 Purpose
MSA 구조 설계 및 서비스 간 통신
Kafka를 활용한 비동기 이벤트 처리
FeignClient 활용
분산 시스템에서의 추적(Tracing)과 모니터링(Monitoring) 구현
실제 운영 환경을 고려한 컨테이너 기반 배포 경험
-------------------------


🏗️ Architecture
🔹 Microservices 핵심기능
API Gateway
로드밸런스드 라우팅 및 인증필터
User Service
회원 관리 / 토큰 인증 (Connector Sink로 이벤트 동기화)
Order Service
주문 생성 및 이벤트 발행 (Producer)
Catalog Service
상품 및 재고 관리 (kafkalistener를 활용한 Consumer)
Config Service
중앙 설정 관리 서버 (Spring Cloud Config)
Eureka
서비스 디스커버리

🔄 Event-Driven Architecture (Kafka)
✔️ Order → Catalog 흐름
Order Service
주문 생성 시 OrderProducer를 통해 Kafka 토픽으로 메시지 발행
Kafka
주문 데이터를 이벤트로 전달
Kafka Connector (Sink)
토픽 데이터를 DB에 반영
Catalog DB
재고 수량 자동 업데이트

🔍 Observability

📊 Monitoring (Prometheus + Actuator)

🐳 Deployment (Docker)

