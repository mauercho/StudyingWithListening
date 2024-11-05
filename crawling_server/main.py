from fastapi import FastAPI, Query
import requests
from bs4 import BeautifulSoup
from service.html_parser_service import HtmlParserService

app = FastAPI(root_path="/crawling")

@app.get("/")
def crawl_page(url: str = Query(..., description="크롤링할 웹 페이지의 URL")):
    # URL로 요청 보내기
    response = requests.get(url)

    # 요청 실패 시 에러 발생
    response.raise_for_status()

    # HTML 파싱
    soup = BeautifulSoup(response.text, "html.parser")

    # 옵션 1. 태그 다 벗겨서 가져오기
    body_content = soup.body.get_text(strip = True) if soup.body else "본문이 없습니다."

    # 옵션 2. 태그 계층 살려서 json으로 오기
    # body_content = HtmlParserService.parse_html(soup.body) if soup.body else "본문이 없습니다."

    return { "body" : body_content }
        