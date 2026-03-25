## 📦 Le-ECommerce

</br>

----------------------

🧾 Overview

</br>

이 프로젝트는 **MSA(MicroService Architecture) 기반의 전자상거래 시스템**으로,  

</br>

**Kafka**를 활용한 이벤트 기반 데이터 처리, **분산 추적, 모니터링, 그리고 컨테이너 기반 배포**를 적용 및 실험한 프로젝트입니다.

</br>

-----------------

</br>

⚙️ Key Features

</br>

* **MSA** 기반 서비스 분리 및 독립 배포 </br>
* **RabbitMQ & SpringCloudBus** 활용 config refresh </br>
* **Kafka 기반 이벤트 드리븐 아키텍처** 구현 </br>
* Kafka **Connector를 통한 DB 동기화** </br>
* **FeignClient** 활용 **서비스 간 통신** </br>
* **Zipkin**을 활용한 **분산 트레이싱** </br>
* **Prometheus + Actuator** 기반 모니터링 </br>
* Docker 기반 **동일 네트워크 그룹 컨테이너 환경** 구성 </br>

</br>

-----------

</br>

🎯 Purpose

</br>

1. MSA 구조 설계 및 서비스 간 통신 </br>
2. Kafka를 활용한 비동기 이벤트 처리 </br>
3. FeignClient 활용 </br>
4. 분산 시스템에서의 추적(Tracing)과 모니터링(Monitoring) 구현 </br>
5. 네트워크 그룹 기반 이미지 배포 </br>

</br>

-------------------------

</br>

<details>
  <summary>🏗️ 유스케이스</summary>
  <img width="814" height="446" alt="image" src="https://github.com/user-attachments/assets/bcaee170-8a24-4036-ad52-02ff84c9059d" /> </br>
</details>

-------

🏗️ Architecture </br>

</br>

🔹 Microservices 핵심기능 </br>

* API Gateway </br>
로드밸런스드 라우팅 및 인증필터 </br>
* User Service </br>
회원 관리 / 토큰 인증 (Connector Sink로 이벤트 동기화) </br>
* Order Service </br>
주문 생성 및 이벤트 발행 (Producer) </br>
* Catalog Service </br>
상품 및 재고 관리 (kafkaListener를 활용한 Consumer) </br>
* Config Service </br>
중앙 설정 관리 서버 (native repo/ git repo) (Spring Cloud Config) (토큰 암호화) </br>
* Eureka </br>
서비스 디스커버리 </br>

</br>
<details>
  <summary> 🔹 API </summary>
  
| 기능                     | URL(API Gateway)              | URI (!API Gateway)     | HTTP Method |
|--------------------------|------------------------------|------------------------|-------------|
| 사용자 정보 등록         | /user-service/users          | /users                 | POST        |
| 전체 사용자 조회         | /user-service/users          | /users                 | GET         |
| 사용자 정보,주문 내역 조회 | /user-service/{user-id}      | /users/{user_id}       | GET         |
| 작동 상태 확인           | /user-service/users/health_check | /users/health_check    | GET         |
| 환영 메시지              | /user-service/users/welcome  | /users/welcome         | GET         |

</br>

| 기능 | 마이크로 서비스 | URI(API Gateway) | HTTP Method |
| --- | --- | --- | --- |
| 상품 목록 조회 | Catalogs MicroService | /catalog-service/catalogs | GET |
| 사용자 별 상품 주문 | Orders MicroService | /order-service/{user_id}/orders | POST |
| 사용자 별 주문 내역 조회 | Orders MicroService | /order-service/{user_id}/orders | GET |

</br>

</details>


-----------------

</br>

🔄 Event-Driven Architecture (Kafka) </br>

Order Service </br>
주문 생성 시 OrderProducer를 통해 Kafka 토픽으로 메시지 발행 </br>
Kafka </br>
주문 데이터를 토픽으로 전달 </br>
Kafka Connector (Sink) </br>
토픽 데이터를 DB(User와 같은 DB)에 반영 </br>
Catalog DB </br>
재고 수량 리스너를 통해 업데이트 </br>

</br>

-------------

🔍 Observability 

</br>

🧵 Distributed Tracing & Monitoring (Zipkin & Prometheus & Actuator) </br>

1. 각 서비스 요청 흐름을 traceId 기반으로 다음 정보 추적 및 병목현상 관리 </br>
* 서비스 간 호출 흐름 </br>
* 처리 시간 </br>
* method/path </br>
* 연결된 서비스 </br>
2. 트래픽 및 요청 횟수 등 확인 </br>

</br>

---------------

</br>

🏗️ Tests </br>

1. JWT 인증 </br>

<img width="1089" height="757" alt="image (2)" src="https://github.com/user-attachments/assets/5d7bc734-382c-4900-a8f3-bd4a57a2291e" /> </br>

<img width="1102" height="671" alt="image (3)" src="https://github.com/user-attachments/assets/ba68a9be-fcc4-4769-9fcd-e8dba149b432" /> </br>

2. Config refresh by Spring Cloud Bus & RabbitMQ </br>

<img width="1329" height="120" alt="image (4)" src="https://github.com/user-attachments/assets/6451d8f5-59a1-47ca-90f3-4dad351707f9" /> </br>

3. FeignClient 활용 서비스 예외처리 </br>

<img width="1034" height="453" alt="image (5)" src="https://github.com/user-attachments/assets/2d219944-d55f-429c-9853-e1e6a68ff009" /> </br>

4. kafka message queuing test </br>

<img width="1111" height="436" alt="image (6)" src="https://github.com/user-attachments/assets/10443152-23e9-4d56-a726-5d2ff935f5c5" /> </br>

5. kafka Connector Source/Sink 활용 DB 동기화 </br>

<img width="940" height="435" alt="image (7)" src="https://github.com/user-attachments/assets/78e43cc4-b4e4-467f-b6cf-6a66649a5790" /> </br>

6. Topic Producing & Consuming </br>

<img width="1054" height="711" alt="image (8)" src="https://github.com/user-attachments/assets/2e0fce6b-691d-4d7e-a2f2-b610d402ea3e" /> </br>

<img width="1079" height="608" alt="image (9)" src="https://github.com/user-attachments/assets/dd71ba2c-6aed-45a7-b95b-293eafe83ded" /> </br>

<img width="1042" height="700" alt="image (10)" src="https://github.com/user-attachments/assets/ec45affc-2881-462e-95fb-00f183bb8040" /> </br>

7. 분산된 동일 서비스에서 Sink 활용한 DB 동기화 </br>

<img width="1105" height="613" alt="image (11)" src="https://github.com/user-attachments/assets/eca97e6d-b04c-4189-b273-a0ecabdd1e25" /> </br>

<img width="1056" height="780" alt="image (12)" src="https://github.com/user-attachments/assets/ac44019c-3c72-40b5-b444-5b4402c05d2c" /> </br>

<img width="1852" height="644" alt="image (13)" src="https://github.com/user-attachments/assets/eb60a072-eef8-4d11-b002-9ecdeb2541d4" /> </br>

<img width="1871" height="653" alt="image (14)" src="https://github.com/user-attachments/assets/b099dbf6-7775-44fa-a381-05d2762d2896" /> </br>

8. Zipkin & Prometheus </br>

<img width="1919" height="856" alt="image (15)" src="https://github.com/user-attachments/assets/ad5f70fd-2315-4a09-a0f5-c5509daaf102" /> </br>

<img width="1280" height="441" alt="image (16)" src="https://github.com/user-attachments/assets/3155f0e2-792b-46fe-a685-757df39ce8f3" /> </br>

<img width="1596" height="949" alt="image (17)" src="https://github.com/user-attachments/assets/162516a9-075b-4242-94c7-6fb505b20ac5" /> </br>

</br>

-----------------------
