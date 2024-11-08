import React, { Suspense, lazy, useEffect } from 'react'

import { Routes, Route, useLocation } from 'react-router-dom'

import routes from './routes'
import TitleLayout from './layouts/TitleLayout'
import SimpleLayout from './layouts/SimpleLayout'
import Home from './pages/home'
import Layout from './layouts/Layout'
import Loading from './components/Loading'
const NotFound = lazy(() => import('./pages/notFound'))

function App() {
  const { pathname } = useLocation()

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [pathname])

  return (
    <Suspense
      fallback={
        <SimpleLayout>
          <Loading text="" size={60} />
        </SimpleLayout>
      }
    >
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
        </Route>
        <Route element={<TitleLayout />}>
          {routes.map(({ path, element, title }) => (
            <Route
              key={path}
              path={path}
              element={React.cloneElement(element, { title })}
            />
          ))}
        </Route>
        <Route path="*" element={<NotFound error={404} />} />
      </Routes>
    </Suspense>
  )
}

export default App
