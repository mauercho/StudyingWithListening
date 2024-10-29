'use client'

import { SWRConfig } from 'swr'

const fetcher = async (url: string) => {
  const res = await fetch(url)
  if (!res.ok) {
    throw new Error('Network response was not ok')
  }
  return res.json()
}

export const SWRProvider = ({ children }: { children: React.ReactNode }) => {
  return (
    <SWRConfig
      value={{
        fetcher,
        dedupingInterval: 3000,
        shouldRetryOnError: false,
      }}
    >
      {children}
    </SWRConfig>
  )
}
