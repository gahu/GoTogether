# GoTogether
# 서울둘레길 앱을 위한 git 정책
- 프로젝트에 contribute 하기위해서 project owner 가 contributor 로 추가해줘야한다.
- 추가하려는 email 은 개인 profile > setting 에서 email 을 공개해줘야한다.
- contributor 가 되기 위해서 Angel-Study/webProject repository 를 fork 해야한다.


## git config
1. 로컬에서 프로젝트를 다운받으려는 폴더로 이동
```
$ cd {path}
```
2. git init 설정
```
$ git init
```
3. git global 설정
```
$ git config --global user.name {username}
$ git config --global user.email {email}
```
4. 프로젝트 clone
```
$ git clone git@github.com:Angel-Study/webProject.git
```
5. 프로젝트의 push url 설정 
- 프로젝트 push 는 fork 된 프로젝트로 (ex) eunjeongsong/webProject
- merge request 는 본 프로젝트로 (ex) Angel-study/webProject
```
$ git remote set-url --push origin git@github.com:eunjeongsong/webProject.git
```
5. remote 경로가 다음과 같으면 정상적으로 remote 가 설정된 것
- push url 은 fork 된 프로젝트
- fetch(pull) url 은 본 프로젝트
```
$ git remote -v
origin	git@github.com:Angel-Study/webProject.git (fetch)
origin	git@github.com:eunjeongsong/webProject.git (push)
```
6. 다운받은 프로젝트 폴더로 이동
```
$ cd webProject
```

## project push 정책
1. 모든 contributor 는 로컬에서 개인 branch 를 따서 작업한다.
- Merge request 는 fork 한 repository 에 새로운 브랜치를 따서 등록한다.
```
$ git branch {작업브랜치명}
```
2. push 하기 전에는 항상 로컬 master branch 로 이동해 최신분을 pull 받고, push 하려는 branch 를 최신 상태로 만들어준다.
```
$ git checkout master
$ git pull
$ git checkout {작업브랜치명}
$ git rebase master
```
3. remote 프로젝트로 push 한다. (remote 프로젝트의 master 브랜치가 아닌 다른 브랜치로 push)
```
$ git add .
$ git commit -am "{작업내용}"
$ git push origin {작업브랜치명}
```
4. [Angel-Study/webProject](https://github.com/Angel-Study/webProject/pulls)에서 "pull Request" 를 생성한다.
- pull Request 의 commit message 를 통해 어떤 이슈에대한 내용을 해결했는지에 대한 태깅을 해준다. ex) resolve #{이슈번호} {커밋내용} 
5. 프로젝트 owner 가 request 를 확인해 push 한 내용을 머지해준다.
6. Merge request 이후 머지된 브랜치는 가능한 삭제하고, 새로운 브랜치를 따서 새로운 작업을 진행할 수 있도록 한다.
```
$ git checkout master
$ git pull
$ git branch -D {작업브랜치명}
$ git checkout -b {새로운브랜치명}
```
- 브랜치를 제거한 경우 로컬에서 트래킹 중인 remote 브랜치 정보를 제거해 준다.
```
# 트래킹 중인 remote branch 가 있는 경우 서버에서 삭제되면 로컬에서도 리모트 브랜치 정보가 삭제됨.
$ git fetch -p
```

## coding convention
- 개발 tool 은 brackets2 를 사용한다.
- beautify 플러그인을 설치해 코드 포맷을 맞춘다.(메뉴 > 편집 > beautify 선택) 

## local server config (예 bitnami)
- bitnami 를 설치하고 설치된 경로(installdir)에 존재하는 bitnami.conf 파일을 연다.
```
$ vi installdir/apache2/conf/bitnami/bitnami.conf
```
- bitnami.conf 파일에서 아래의 내용을 수정한다.
  - port 변경 (8080 -> 9999) (Host 설정 관련 내용을 모두 9999 로 변경)
  - DocumentRoot 로 시작하는 부분에 있는 경로를 개발중인 디렉토리의 경로로 변경
  - Directory 로 시작하는 부분에 있는 경로를 개발중인 디렉토리의 경로로 변경
```
  변경전 : NameVirtualHost *:8080
  변경후 : NameVirtualHost *:9999
 
  변경전 : DocumentRoot "/Applications/mampstack-7.0.23-0/apache2/htdocs"
  변경후 : DocumentRoot "/Users/song/Desktop/song/workspace/webProject"

  변경전 : <Directory "/Applications/mampstack-7.0.23-0/apache2/htdocs">
  변경후 : <Directory "/Users/song/Desktop/song/workspace/webProject"> 
```
- http://localhost:9999 로 접속했을때 개발중인 index.html 페이지가 나오면 정상
 


