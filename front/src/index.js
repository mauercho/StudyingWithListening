import React from 'react'
import ReactDOM from 'react-dom/client'

import { BrowserRouter } from 'react-router-dom'
import { ThemeProvider } from '@emotion/react'

import App from './App'
import * as serviceWorkerRegistration from './serviceWorkerRegistration'

import './assets/styles/reset.css'
import './assets/styles/styles.css'
import theme from './assets/styles/theme'
import GlobalStyles from './components/GlobalStyles'

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <App />
      </ThemeProvider>
    </BrowserRouter>
  </React.StrictMode>
)

serviceWorkerRegistration.register()
