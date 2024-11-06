// src/routes.js
import React, { lazy } from 'react'

const Upload = lazy(() => import('./pages/upload'))
const Detail = lazy(() => import('./pages/detail'))

const routes = [
  { path: '/upload', element: <Upload />, title: '자료 업로드' },
  { path: '/detail', element: <Detail />, title: '상세 페이지' },
]

export default routes
