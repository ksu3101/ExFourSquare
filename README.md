# ExFourSquare

FourSquare OpenApi 를 이용 한 샘플코드 앱 입니다. "WIP..."

## Modules 
빠른 유닛 테스트, 비즈니스 코드 와 뷰 코드 의 분리를 위하여 목적에 따라 각 모듈별로 분리 하였습니다. 

### app package
기존 app 모듈로서 view 와 관련된 코드, 리소스들 이 있습니다. 도메인 당 1 Activity : n Fragment 형태로 구현 되며 각 Fragment 에는 n 개의 ViewModel 이 사용 될 수 있습니다. 

#### base package
Base Activity, Fragment 부모 클래스들이 존재 하며, DI(Koin) 에 사용될 모듈 들이 선언 되어 있는 파일이 존재 합니다. 그리고 ViewModel 에서 주입받아 사용 할 Helper 인터페이스의 구현체 Impl 클래스 들이 존재 합니다. 이 Helper 들은 View 에 대한 디펜던시를 제거한 model 모듈 에서 각 Helper 인터페이스 를 통해 view 외 각 리소스를 사용 할 수 있게 해줍니다. 
그리고 databinding adapter 등이 존재 하며 뷰에 대한 직접적인 처리 들을 합니다. 

#### repositories package
도메인 별 Api 인터페이스, Repository의 구현체 Impl 클래스와 MockImpl 클래스가 구현되어 있습니다. 

#### view package
Activity, Fragment 이 존재 합니다. 

### common
app, model 두 모듈에서 필요한 일반적인 Constants나 kotlin extension function 등 이 존재 합니다. String 을 common 모듈 에서 관리 합니다. 그렇기 때문에 app, model 두 모듈은 common 모듈에 대해 디펜던시를 갖고 있습니다. 

### model
view 에 대한 직접적인 처리를 제외한 비즈니스 로직 들을 도메인별로 모아놓은 모듈 입니다. 

#### base package
Helper 인터페이스, BaseViewModel 과 같은 ViewModel 에 대한 인터페이스, Redux 아키텍쳐의 구현체 및 인터페이스 들 이 있습니다. 

#### domain package
각 도메인 별 페이지의 ViewModel 과 Redux 아키텍쳐 에서 사용 되는 Action, State 그리고 Action Processor 와 Reducer 가 구현 되어 있습니다. 

## MVVM, Redux architecture
실제 비즈니스 로직은 다음과 같은 redux 구조를 사용 하면서 스트림 흐름은 다음과 같이 정리 할 수 있습니다. 

> Action -> Middlewares[ActionProcessor...] -> Reducer -> State

### Action
어떠한 State 를 만들기 위한 Trigger Action 혹은 Result, Error Action 의 형태를 갖는 immutable data class 입니다. 이 Action 은 AppStore 를 통해서 dispatch 됩니다. 

### Middleware 
dispatch 된 Action 이 미들웨어들을 이터레이셔닝 하면서 핸들링 한 뒤 새로운 Trigger Action 혹은 Result, Error Action 을 생성 합니다. 

### Reducer
Middleware 를 통해 최종적으로 나온 Action 을 핸들링 하여 화면을 갱신하기 위한 State 로 만들어 주는 클래스 입니다. 

### State 
최종적인 상태를 AppStore를 통해 저장 하게 되는 immutable data class 입니다. 이 state 는 각 ViewModel 당 1개 씩 대응 되며 해당 state 를 핸들링 하여 화면을 최종 업데이트 합니다. 

## 사용된 라이브러리 
- Kotlin
- AndroidX
- Databinding
- Koin - (Dependency injection tool)
- retrofit + okHttp + moshi
- rxJava2
- junit + mockito
- 그 외 필요한 것 은 직접 kotlin extension function 으로 만듦. 
