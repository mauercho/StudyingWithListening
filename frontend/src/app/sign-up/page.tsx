'use client'

import { SubmitHandler, useForm } from 'react-hook-form'
import Logo from '/public/images/logo.svg'
import NextBtn from '/public/icons/next-btn.svg'
import BackBtn from '/public/icons/arrow-left.svg'
import Link from 'next/link'
import { useEffect, useState } from 'react'
import AuthLayout from '@/app/_components/AuthLayout'
import { postSignUp } from '../apis/auth'
import { useRouter } from 'next/navigation'

interface SignUpFormValues {
  login_id: string
  password: string
  passwordConfirm: string
  name: string
  nickname: string
}

export default function SignUp() {
  const [page, setPage] = useState(1)
  const {
    register,
    handleSubmit,
    watch,
    setFocus,
    formState: { errors, isValid },
  } = useForm<SignUpFormValues>({ mode: 'onChange' })
  const router = useRouter()

  const onSubmit: SubmitHandler<SignUpFormValues> = async (data) => {
    const payload = {
      login_id: data.login_id,
      password: data.password,
      name: data.name,
      nickname: data.nickname,
    }
    const res = await postSignUp('/users', payload)
    if (res?.status === 201) {
      alert('회원가입이 완료되었습니다!')
      router.push('/login')
    } else if (res?.status === 400) {
      if (res?.data.message === '아이디 중복') {
        alert('중복된 아이디입니다!')
        setPage(1)
      }
      if (res.data.message === '닉네임 중복') {
        alert('중복된 닉네임입니다!')
        setFocus('nickname')
      }
    }
  }

  useEffect(() => {
    setFocus('login_id')
  }, [page, setFocus])

  return (
    <AuthLayout>
      <div className="relative bg-white max-w-[600px] w-full mx-auto py-10 px-24 flex flex-col items-center rounded-[60px]">
        <h1 className="text-center py-10">
          <Logo width="210" height="56" />
          <p className="text-primary-400 text-2xl font-medium">회원가입</p>
        </h1>

        <form
          onSubmit={handleSubmit(onSubmit)}
          className="w-full flex flex-col gap-4 mt-4 mb-14"
        >
          {page === 1 && (
            <>
              <div className="relative">
                <input
                  id="login_id"
                  type="text"
                  placeholder=" "
                  {...register('login_id', {
                    required: '아이디는 필수입력 필드입니다.',
                  })}
                  className="input peer"
                />
                <label htmlFor="login_id" className="input-label">
                  아이디
                </label>
                <p className="error-message">
                  {errors.login_id && String(errors.login_id.message)}
                </p>
              </div>

              <div className="relative">
                <input
                  id="password"
                  type="password"
                  placeholder=" "
                  {...register('password', {
                    required: '비밀번호는 필수입력 필드입니다.',
                  })}
                  className="input peer"
                />
                <label htmlFor="password" className="input-label">
                  비밀번호
                </label>
                <p className="error-message">
                  {errors.password && String(errors.password.message)}
                </p>
              </div>

              <div className="relative">
                <input
                  id="passwordConfirm"
                  type="password"
                  placeholder=" "
                  {...register('passwordConfirm', {
                    required: '비밀번호 확인은 필수입력 필드입니다.',
                    validate: (value) =>
                      value === watch('password') ||
                      '비밀번호 확인이 일치하지 않습니다.',
                  })}
                  className="input peer"
                />
                <label htmlFor="passwordConfirm" className="input-label">
                  비밀번호 확인
                </label>
                <p className="error-message">
                  {errors.passwordConfirm &&
                    String(errors.passwordConfirm.message)}
                </p>
              </div>
              <button
                onClick={() => setPage(() => page + 1)}
                disabled={!isValid}
                className={`${isValid ? 'text-primary-400' : 'text-gray-400'} transition-colors duration-300 self-end`}
              >
                <NextBtn className="w-10" />
              </button>
            </>
          )}

          {page === 2 && (
            <>
              <button
                onClick={() => setPage(() => page - 1)}
                className="absolute top-10 left-10 text-gray-400 hover:text-gray-500"
              >
                <BackBtn width={32} height={32} />
              </button>
              <div className="relative">
                <input
                  id="name"
                  type="name"
                  placeholder=" "
                  {...register('name', {
                    required: '이름은 필수입력 필드입니다.',
                  })}
                  className="input peer"
                />
                <label htmlFor="name" className="input-label">
                  이름
                </label>
                <p className="error-message">
                  {errors.name && String(errors.name.message)}
                </p>
              </div>
              <div className="relative">
                <input
                  id="nickname"
                  type="nickname"
                  placeholder=" "
                  {...register('nickname', {
                    required: '닉네임은 필수입력 필드입니다.',
                  })}
                  className="input peer"
                />
                <label htmlFor="nickname" className="input-label">
                  닉네임
                </label>
                <p className="error-message">
                  {errors.nickname && String(errors.nickname.message)}
                </p>
              </div>
              <button
                type="submit"
                className={`submit-btn mt-6 ${isValid ? 'bg-primary-400' : 'bg-gray-300'}`}
                disabled={!isValid}
              >
                회원가입
              </button>
            </>
          )}
        </form>
        <p className="text-sm text-gray-400">
          이미 회원이신가요?
          <Link
            href="/login"
            className="ml-1 font-medium text-gray-500 hover:text-gray-700"
          >
            로그인
          </Link>
        </p>
      </div>
    </AuthLayout>
  )
}
