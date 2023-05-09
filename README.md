# Team-AOS-1st
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

---
### 📚 Git Convention
```
feat : 새로운 기능에 대한 커밋
fix : 버그 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor :  코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

### 🏛 Package
```
cvs-api : 비즈니스 로직을 다루는 api 서버
cvs-batch : 배치 서버
cvs-domain : 엔티티를 다루는 core 패키지
```

### ✨ Local DB 실행
```shell
# maria db, redis 실행 
$ docker-compose up -d
```
```shell
# maria db, redis 종료 
$ docker-compose down
```

### 🔨 환경 변수 설정
`Edit Configuration -> Active profiles : dev` <br>
`Edit Configuration -> Enable EnvFile : true -> .env 지정`