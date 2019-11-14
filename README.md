# ExFourSquare

FourSquare OpenApi 를 이용 한 샘플코드 앱 입니다. 이 앱에서 하는 일은 아래와 같습니다. 

1. 실행 후 액세스 토큰을 얻기 위한 code 를 설치된 four square 앱 로그인 및 권한 인증 후 activity result 로 얻습니다.

2. 받은 code 를 이용 하여 액세스 토큰 을 아래처럼 rest api 로 발급 받습니다. 
```
GET
https://foursquare.com/oauth2/access_token
    ?client_id=YOUR_CLIENT_ID
    &client_secret=YOUR_CLIENT_SECRET
    &grant_type=authorization_code
    &code=CODE
```

3. 발급받은 액세스 코드를 이용 하여 로그인한 사용자의 정보를 보여줍니다. 
```
GET
https://api.foursquare.com/v2/users/self
    ?oauth_token=ACCESS_TOKEN
    &v=VERSION
```

<details><summary>[JsonResponse 예제 보기]</summary>
<p>
  
```
{
    "meta": {
        "code": 200,
        "requestId": "5dcdbef7660a9f0028f5e8d2"
    },
    "notifications": [
        {
            "type": "notificationTray",
            "item": {
                "unreadCount": 0
            }
        }
    ],
    "response": {
        "user": {
            "id": "565712862",
            "firstName": "성우",
            "lastName": "강",
            "gender": "none",
            "relationship": "self",
            "canonicalUrl": "https://foursquare.com/user/565712862",
            "photo": {
                "prefix": "https://fastly.4sqi.net/img/user/",
                "suffix": "/565712862_LTwgmIww_VBmD_KJivEZIApjR7Ni-j_jR5OkA50aWSL65G5lKZZ4-QfW0odVwJiyGuB2Vp8Vg.jpg"
            },
            "friends": {
                "count": 0,
                "groups": [
                    {
                        "type": "friends",
                        "name": "Mutual friends",
                        "count": 0,
                        "items": []
                    },
                    {
                        "type": "others",
                        "name": "Other friends",
                        "count": 0,
                        "items": []
                    }
                ]
            },
            "birthday": 486864000,
            "tips": {
                "count": 0
            },
            "homeCity": "Seoul, Seoul",
            "bio": "",
            "contact": {
                "verifiedPhone": "false",
                "email": "burkdog@naver.com"
            },
            "photos": {
                "count": 0,
                "items": []
            },
            "checkinPings": "off",
            "pings": false,
            "type": "user",
            "mayorships": {
                "count": 0,
                "items": []
            },
            "checkins": {
                "count": 4,
                "items": [
                    {
                        "id": "5dcd8af59ee69b00076614ac",
                        "createdAt": 1573751541,
                        "type": "checkin",
                        "private": true,
                        "visibility": "private",
                        "timeZoneOffset": 540,
                        "editableUntil": 1573837941000,
                        "venue": {
                            "id": "5b80a13d78782c002ce62cf0",
                            "name": "Starbucks Reserve (스타벅스 리저브)",
                            "location": {
                                "address": "송파구 송파대로 201",
                                "crossStreet": "문정법원로R점",
                                "lat": 37.487411568126376,
                                "lng": 127.11880628085092,
                                "labeledLatLngs": [
                                    {
                                        "label": "display",
                                        "lat": 37.487411568126376,
                                        "lng": 127.11880628085092
                                    }
                                ],
                                "postalCode": "05854",
                                "cc": "KR",
                                "city": "Seoul",
                                "state": "Seoul",
                                "country": "South Korea",
                                "formattedAddress": [
                                    "송파구 송파대로 201 (문정법원로R점)",
                                    "문정2동",
                                    "송파구",
                                    "서울특별시",
                                    "05854"
                                ]
                            },
                            "categories": [
                                {
                                    "id": "4bf58dd8d48988d1e0931735",
                                    "name": "Coffee Shop",
                                    "pluralName": "Coffee Shops",
                                    "shortName": "Coffee Shop",
                                    "icon": {
                                        "prefix": "https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_",
                                        "suffix": ".png"
                                    },
                                    "primary": true
                                }
                            ],
                            "like": false
                        },
                        "likes": {
                            "count": 0,
                            "groups": []
                        },
                        "like": false,
                        "isMayor": false,
                        "photos": {
                            "count": 0,
                            "items": []
                        },
                        "posts": {
                            "count": 0,
                            "textCount": 0
                        },
                        "comments": {
                            "count": 0
                        },
                        "source": {
                            "name": "Foursquare for Android",
                            "url": "https://foursquare.com/download/#/android"
                        }
                    }
                ]
            },
            "requests": {
                "count": 0
            },
            "lists": {
                "count": 4,
                "groups": [
                    {
                        "type": "created",
                        "count": 1,
                        "items": [
                            {
                                "id": "5dcd8bdeabe9110006c5ec59",
                                "name": "임시 목록",
                                "description": "ㅇㅇㅇㅇㅇㅇㅇㅇ",
                                "editable": true,
                                "public": true,
                                "collaborative": false,
                                "url": "/user/565712862/list/%EC%9E%84%EC%8B%9C-%EB%AA%A9%EB%A1%9D",
                                "canonicalUrl": "https://foursquare.com/user/565712862/list/%EC%9E%84%EC%8B%9C-%EB%AA%A9%EB%A1%9D",
                                "createdAt": 1573751774,
                                "updatedAt": 1573751784,
                                "photo": {
                                    "id": "55dff7e3498edc6aff1b3339",
                                    "createdAt": 1440741347,
                                    "prefix": "https://fastly.4sqi.net/img/general/",
                                    "suffix": "/18752869_LYEnmRfLN-3R5CymQdBMi35XuY4G-JOH6irNqoJEDcI.jpg",
                                    "width": 1920,
                                    "height": 1440,
                                    "user": {
                                        "id": "18752869",
                                        "firstName": "yk",
                                        "gender": "none",
                                        "photo": {
                                            "prefix": "https://fastly.4sqi.net/img/user/",
                                            "suffix": "/18752869-5FJLTMDYEMKMQDVS.jpg"
                                        }
                                    },
                                    "visibility": "public"
                                },
                                "followers": {
                                    "count": 0
                                },
                                "listItems": {
                                    "count": 2
                                }
                            }
                        ]
                    },
                    {
                        "type": "followed",
                        "count": 1,
                        "items": []
                    },
                    {
                        "type": "yours",
                        "count": 2,
                        "items": [
                            {
                                "id": "565712862/todos",
                                "name": "My Saved Places",
                                "description": "",
                                "type": "todos",
                                "editable": true,
                                "public": true,
                                "collaborative": false,
                                "url": "/user/565712862/list/todos",
                                "canonicalUrl": "https://foursquare.com/user/565712862/list/todos",
                                "listItems": {
                                    "count": 7
                                }
                            },
                            {
                                "id": "565712862/venuelikes",
                                "name": "My Liked Places",
                                "description": "",
                                "type": "likes",
                                "editable": true,
                                "public": true,
                                "collaborative": false,
                                "url": "/user/565712862/list/venuelikes",
                                "canonicalUrl": "https://foursquare.com/user/565712862/list/venuelikes",
                                "listItems": {
                                    "count": 0
                                }
                            }
                        ]
                    }
                ]
            },
            "blockedStatus": "none",
            "createdAt": 1573573116,
            "lenses": [],
            "referralId": "u-565712862"
        }
    }
}
```

</p>
</details>


## Modules 
빠른 유닛 테스트, 비즈니스 코드 와 뷰 코드 의 분리를 위하여 목적에 따라 각 모듈별로 분리 하였습니다. 

### app (module)
기존 app 모듈로서 view 와 관련된 코드, 리소스들 이 있습니다. 도메인 당 1 Activity : n Fragment 형태로 구현 되며 각 Fragment 에는 n 개의 ViewModel 이 사용 될 수 있습니다. 

#### base package
Base Activity, Fragment 부모 클래스들이 존재 하며, DI(Koin) 에 사용될 모듈 들이 선언 되어 있는 파일이 존재 합니다. 그리고 ViewModel 에서 주입받아 사용 할 Helper 인터페이스의 구현체 Impl 클래스 들이 존재 합니다. 이 Helper 들은 View 에 대한 디펜던시를 제거한 model 모듈 에서 각 Helper 인터페이스 를 통해 view 외 각 리소스를 사용 할 수 있게 해줍니다. 
그리고 databinding adapter 등이 존재 하며 뷰에 대한 직접적인 처리 들을 합니다. 

#### repositories package
도메인 별 Api 인터페이스, Repository의 구현체 Impl 클래스와 MockImpl 클래스가 구현되어 있습니다. 

#### view package
Activity, Fragment 이 존재 합니다. 

### common (module)
app, model 두 모듈에서 필요한 일반적인 Constants나 kotlin extension function 등 이 존재 합니다. String 을 common 모듈 에서 관리 합니다. 그렇기 때문에 app, model 두 모듈은 common 모듈에 대해 디펜던시를 갖고 있습니다. 

### model (module)
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
- Koin, Koin-test
- retrofit + okHttp + moshi
- rxJava2
- junit + mockito
- 그 외 필요한 것 은 kotlin extension function 으로 만듦. 
