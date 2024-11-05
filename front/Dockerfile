# 공식 Node.js 이미지를 베이스 이미지로 사용
FROM node:18-alpine

# 컨테이너 내 작업 디렉터리 설정
WORKDIR /app

# package.json과 package-lock.json (존재하는 경우) 복사
COPY package*.json ./

# 의존성 설치
RUN npm ci

# 프로젝트 전체를 컨테이너로 복사
COPY . .

EXPOSE 3000

# React를 위한 환경 변수 설정
ENV NODE_ENV=production

# 애플리케이션 실행
CMD ["npm", "start"]
