class HtmlParserService:

    @staticmethod
    def parse_html(element):
        # 태그가 있는 경우
        if element.name:
            return {
                "tag": element.name,
                "attributes": element.attrs,
                "children": [parse_html(child) for child in element.children if child.name or child.strip()]
            }
        # 태그가 아닌 텍스트 노드인 경우
        else:
            return element.strip()