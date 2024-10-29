import type { Metadata } from 'next'
import { Noto_Sans_KR } from 'next/font/google'
import './globals.css'
import { SWRProvider } from './swr-provider'
import Header from './_components/Header'
import { SentenceProvider } from './context/SentenceContext'
import { DictationResultProvider } from './context/toggleContext'

const notoSansKR = Noto_Sans_KR({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'KoALA | 1등 한국어 교육 서비스 ',
  description: '이주배경 가정 학부모 특화 한국어 교육 서비스',
}

export default function RootLayout({
  children,
  modal,
}: Readonly<{
  children: React.ReactNode
  modal: React.ReactNode
}>) {
  return (
    <html lang="en">
      <body className={notoSansKR.className}>
        <SWRProvider>
          <DictationResultProvider>
            <SentenceProvider>
              {modal}
              <Header />
              <main className="pt-20 min-h-screen">{children}</main>
            </SentenceProvider>
          </DictationResultProvider>
        </SWRProvider>
      </body>
    </html>
  )
}
