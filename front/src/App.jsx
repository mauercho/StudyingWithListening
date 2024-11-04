import React, { Suspense, lazy, useEffect } from 'react'

import { Routes, Route, useLocation } from 'react-router-dom'

import TitleLayout from './layouts/TitleLayout'
import Home from './pages/home'
import Layout from './layouts/Layout'
const NotFound = lazy(() => import('./pages/notFound'))

function App() {
  const { pathname } = useLocation()

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [pathname])

  return (
    <Suspense fallback={<div>loading...</div>}>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
        </Route>
        <Route element={<TitleLayout />}>
          {/* 타이틀이 필요한 page */}
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Suspense>
  )
}

export default App
