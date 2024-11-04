import React, { Suspense, lazy, useEffect } from 'react'

import { Routes, Route, useLocation } from 'react-router-dom'

import Home from './pages/home'
const NotFound = lazy(() => import('./pages/notFound'))

function App() {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return (
    <Suspense fallback={<div>loading...</div>}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Suspense>
  )
}

export default App
