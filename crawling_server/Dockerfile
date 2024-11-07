# Python 이미지를 베이스로 사용 (Python 3.9 버전 사용 예시)
FROM python:3.9-slim

# 작업 디렉터리 설정
WORKDIR /app

# requirements.txt 파일을 컨테이너로 복사
COPY requirements.txt .

# 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# 애플리케이션 코드 복사
COPY . .

# uvicorn을 사용하여 FastAPI 서버 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000", "--reload"]
