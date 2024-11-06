// src/routes.js
import React, { lazy } from 'react'

const Upload = lazy(() => import('./pages/upload'))

const routes = [{ path: '/upload', element: <Upload />, title: '자료 업로드' }]

export default routes
