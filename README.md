쉽게 말하면 WebFlux라는 용어는 Reactor의 타입인 Flux(N개의 데이터를 emit)가 Web에서 사용.  
WebFlux는 리액티브한 웹 애플리케이션을 구현하기 위한 기술 자체를 상징하고 있다고 보는게 더 적절.  
    
Spring MVC 프레임워크를 사용해서 웹 애플리케이션을 구현할 수 있듯이  
Spring WebFlux 프레임워크를 사용해서 리액티브한 웹 애플리케이션을 구현.  
    
이 전 유닛에서 리액티브 프로그래밍(Reactive Programming)에 대한 개념적인 내용만 대략적으로 살펴보았기 때문에  
리액티브(Reactive)라는 개념이 애플리케이션에 어떻게 적용되는지 어떤 특성을 가지는지 여기서 구현할 예정.  
  
mvc-sample, mvc-outbound-sample은 각각  
mvc방식으로 클라이언트 요청 받음 ->  
mvc sample 컨트롤러에서 getCoffee 매서드에서 RestTemplate를 통하여 mvc 아웃바운드 컨트롤러로 전송.(또다른 백엔드 서버)  
(mvc 샘플의 어플리케이션 실행 코드는 getCoffee 사용할때 로그 작성 로직 추가. (원래는 멤버서비스, 리파지토리역할) )  
  
결과는 mvc 아웃바운드 컨트롤러에 Thread.sleep(5000) 매서드때문에 25초걸림.(어플리케이션에서 다섯번 로그호출하거든)  
즉,  
Spring MVC 기반의 메인 애플리케이션이 외부 애플리케이션 서버와 통신할 때 요청 처리 쓰레드가 Blocking 된다는 것을 알 수 있음.  


메인 컨트롤러에서  
Spring MVC 기반 애플리케이션에서는 외부 애플리케이션과의 통신을 위해 RestTemplate을 사용한 반면  
Spring WebFlux 기반 애플리케이션에서는 위와 같이 WebClient라는 Rest Client를 사용  
RestTemplate이 Blocking 방식의 Rest Client인 반명에 WebClient는 Non-Blocking 방식의 Rest Client라는 사실.  
  
  
웹플럭스는 실행시키면 6초걸림. 똑같이 쓰레드.슬립이 존재하는데도.  
왜그럴까?(쓰레드 비동기와 논블로킹의 차이)  
메인 애플리케이션의 Controller가 요청을 1차로 수신한 시간을 보면 밀리초 단위는 조금 다르지만 초 단위는 동일한것을 확인.  
외부 애플리케이션의 Controller에서 Thread.sleep(5000)으로 지연 시간을 주었다고 해서  
메인 애플리케이션 Controller의 요청 처리 쓰레드가 Blocking 되지 않는 다는 것을 의미.  

한마디로 거의 동일한 시간에 동시 다발적으로 요청이 들어와도 요청을 일단 Blocking 하지 않고, 외부 애플리케이션에게 요청을 그대로 전달.  
1차로 요청을 수신한 애플리케이션(웹플럭스 메인 어플리케이션)에서 외부 애플리케이션에 요청을 추가적으로 전달할 때  
1차로 요청을 수신한 애플리케이션의 요청 처리 쓰레드가 Blocking 되지 않는다는 사실, 꼭 기억  


핵심 포인트  
Spring WebFlux는 Spring 5부터 지원하는 리액티브 웹 애플리케이션을 위한 웹 프레임워크이다.
Spring WebFlux는 Spring MVC 방식의  
@Controller, @RestController, @RequestMapping 등과 같은 애너테이션을 동일하게 지원한다.
Spring WebFlux는 1차로 요청을 수신한 애플리케이션에서  
외부 애플리케이션에 요청을 추가적으로 전달할 때 1차로 요청을 수신한 애플리케이션의 요청 처리 쓰레드가 Blocking 되지 않는다.  

