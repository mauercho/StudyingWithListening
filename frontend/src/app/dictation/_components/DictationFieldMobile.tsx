'use client'

import { useEffect, useRef, useState } from 'react'
import Brush from '/public/icons/brush.svg'

const MobileDictation = ({
  userAnswer: { userAnswer, setUserAnswer },
}: any) => {
  const editorRef = useRef(null)
  const [editor, setEditor] = useState<any>(null)
  const [clearFlag, setClearFlag] = useState(false)

  useEffect(() => {
    if (userAnswer.length === 0) {
      setClearFlag(!clearFlag)
    }
  }, [userAnswer])

  useEffect(() => {
    if (editorRef.current) {
      const script = document.createElement('script')
      script.src = 'https://cdn.jsdelivr.net/npm/iink-ts/dist/iink.min.js'
      script.onload = async () => {
        const editorElement = editorRef.current

        const options = {
          configuration: {
            server: {
              protocol: 'WEBSOCKET',
              scheme: 'https',
              host: 'cloud.myscript.com',
              applicationKey: process.env.NEXT_PUBLIC_APPLICATION_KEY,
              hmacKey: process.env.NEXT_PUBLIC_HMAC_KEY,
            },
            recognition: {
              type: 'TEXT',
              language: 'ko_KR',
              text: {
                guides: { enable: false },
                lineSpacing: 100,
              },
            },
            rendering: {
              guides: {
                enable: false,
                gap: 500,
              },
              smartGuide: {
                enable: false,
              },
            },
          },
        }

        try {
          const iinkEditor: any = new (window as any).iink.Editor(
            editorElement,
            options
          )

          setEditor(iinkEditor)

          await iinkEditor.initialize()

          window.addEventListener('resize', () => {
            // Call resize method if applicable
            if (iinkEditor.resize) {
              iinkEditor.resize()
            }
          })

          iinkEditor.events.addEventListener('exported', (event: any) => {
            let str = event.detail['application/vnd.myscript.jiix'].label

            const splitString = str.split(/V|✓|v|\n/)

            const filteredSplitString = splitString.filter(
              (part: any) => part.trim() !== ''
            )
            const noSpaces = filteredSplitString.map((part: any) =>
              part.replace(/\s+/g, '')
            )
            const result = noSpaces.join(' ')

            setUserAnswer(result)
          })

          const availableLanguageResponse = await new (
            window as any
          ).iink.getAvailableLanguageList(iinkEditor.configuration)

          const configuration = JSON.parse(
            JSON.stringify(iinkEditor.configuration)
          )
          configuration.recognition.lang = 'ko_KR'
          iinkEditor.configuration = configuration
        } catch (error) {
          console.error('Error initializing IINK Editor:', error)
        }
      }

      document.body.appendChild(script)

      return () => {
        // WebSocket 종료 (editor가 제공하는 종료 메서드가 있을 경우)
        if (editor && typeof editor.close === 'function') {
          editor.close()
        }

        document.body.removeChild(script)
      }
    }
  }, [clearFlag]) // clearFlag가 변경될 때마다 useEffect가 호출됩니다.

  const handleClear = async () => {
    if (editor) {
      try {
        await editor.clear()
        setClearFlag((prev) => !prev) // 상태를 변경하여 재렌더링 유도
      } catch (error) {
        console.error('Clear failed:', error)
      }
    }
  }

  const triggerExport = async () => {
    if (editor) {
      try {
        await editor.export()
      } catch (error) {
        console.error('Export failed:', error)
      }
    }
  }

  return (
    <div>
      <div className="flex justify-between mb-1">
        <button
          onClick={handleClear}
          className="hover:bg-gray-50 items-center shadow-md flex gap-1 bg-white py-1 px-3 rounded-full text-primary-400 text-sm"
        >
          <Brush width={20} height={20} />
          전체 지우기
        </button>
        {/* <button onClick={triggerExport}>Export</button> */}
        <p className="text-primary-400 font-semibold">
          주의사항: 띄어쓰기를 할 때는 ✓표시를 해주세요
        </p>
      </div>

      <div
        id="editor"
        ref={editorRef}
        // touchAction="none"
        className="bg-red-100 w-[1040px] h-[300px] bg-dictation-field bg-center"
      >
        {/* <img src="/images/dictationField.png" className="w-full" /> */}
      </div>
    </div>
  )
}

export default MobileDictation
