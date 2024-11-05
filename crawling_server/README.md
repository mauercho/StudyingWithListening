# 설치 가이드

### 가상환경 생성
```
python -m venv shortgong_env
```

### 가상환경 활성화 명령어를 위한 설정
```
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

### 가상환경 활성화
```
.\shortgong_env\Scripts\Activate
```

### pip 버전 업그레이드
```
python.exe -m pip install --upgrade pip
```

### 패키지 다운 (fastapi uvicorn requests beautifulsoup4)
```
pip install -r requirements.txt
```

### 서버 실행
--host 0.0.0.0 : 외부에서 접근 가능 설정   
--port 5000 : 5000 포트에서 서버 실행 설정   
--reload : 코드가 변경될 때 서버 자동 재시작 설정   

```
uvicorn main:app --host 0.0.0.0 --port 5000 --reload
```