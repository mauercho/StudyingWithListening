import Link from 'next/link'
import Logo from '/public/images/logo.svg'
import YoutubeLogo from '/public/icons/youtube-logo.svg'
import GithubLogo from '/public/icons/github-logo.svg'
import Image from 'next/image'

export default function Home() {
  const lines = [
    '한국어를 한국어답게,',
    '말하기 듣기 쓰기 균형잡힌',
    '한국어 통합 교육 서비스',
  ]
  return (
    <div className="flex flex-col min-h-screen">
      <main className="h-main-screen flex justify-center items-center">
        <div className="flex gap-32 justify-center mb-20 relative">
          <Image
            src="/images/dictation-mockup.png"
            width={700}
            height={700}
            alt="dictation-mockup"
            className="animate-fade-in"
          />

          <div className="flex flex-col justify-center">
            <Logo className="w-60" />
            <h1 className="text-4xl font-bold my-8 leading-relaxed text-primary-800">
              {lines.map((line, lineIndex) => {
                const previousLinesDelay = lines
                  .slice(0, lineIndex)
                  .reduce((acc, prevLine) => acc + prevLine.length * 0.1, 0)
                return (
                  <div key={lineIndex}>
                    {line.split('').map((char, charIndex) => (
                      <span
                        key={charIndex}
                        className="inline-block opacity-0 animate-fade-in"
                        style={{
                          animationDelay: `${previousLinesDelay + charIndex * 0.1}s`,
                        }}
                      >
                        {char === ' ' ? '\u00A0' : char}
                      </span>
                    ))}
                  </div>
                )
              })}
            </h1>
            <Link
              href="/sign-up"
              className="w-64 bg-primary-400 text-white text-center text-xl p-4 rounded-full hover:bg-primary-500 transition-colors"
            >
              무료로 등록하기
            </Link>
          </div>
        </div>
      </main>
      <footer className="w-full h-72 bg-gray-100 pt-10 pb-14 text-slate-500 text-sm">
        <div className="h-full max-w-screen-xl mx-auto flex flex-col">
          <div className="flex justify-between h-full">
            <address className="not-italic flex flex-col gap-1">
              <h1 className="text-2xl font-extrabold mb-1 hover:text-primary-500">
                <Link href="#">KoALA</Link>
              </h1>
              <p className="text-base mb-4">
                서비스이용약관 | 개인정보수집동의
              </p>
              <p>
                대외협력 |{' '}
                <Link
                  href="https://www.ssafy.com/"
                  className="hover:text-slate-800"
                >
                  삼성 청년 SW 아카데미 11th
                </Link>
              </p>
              <p className="mb-4">
                고동연김유경김성현박수진이주형윤서영 | 서울 강남구 테헤란로 212
                멀티캠퍼스 역삼 201호
              </p>
              <p className="mt-auto">
                Copyright © 2024 KoALA. All rights reserved.
              </p>
            </address>
            <div className="flex items-center text-2xl gap-4 mt-auto">
              <Link
                href="https://www.youtube.com/watch?v=UfunP6Nt6vo"
                className="text-slate-500 hover:text-slate-800"
              >
                <YoutubeLogo className="w-8 h-8" />
              </Link>
              <Link
                href="https://lab.ssafy.com/s11-webmobile1-sub2/S11P12A502"
                className="text-slate-500 hover:text-slate-800"
              >
                <GithubLogo className="w-8 h-8" />
              </Link>
              <Link href="mailto:onepst@hanyang.ac.kr">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 512 512"
                  fill="current"
                  width="current"
                  className="h-6 fill-slate-500 hover:fill-slate-800"
                >
                  <path d="M48 64C21.5 64 0 85.5 0 112c0 15.1 7.1 29.3 19.2 38.4L236.8 313.6c11.4 8.5 27 8.5 38.4 0L492.8 150.4c12.1-9.1 19.2-23.3 19.2-38.4c0-26.5-21.5-48-48-48H48zM0 176V384c0 35.3 28.7 64 64 64H448c35.3 0 64-28.7 64-64V176L294.4 339.2c-22.8 17.1-54 17.1-76.8 0L0 176z" />
                </svg>
              </Link>
            </div>
          </div>
        </div>
      </footer>
    </div>
  )
}
