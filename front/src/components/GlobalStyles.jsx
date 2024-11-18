// GlobalStyles.js
import React from 'react'
import { Global, css } from '@emotion/react'

const globalStyles = (theme) => css`
  body {
    background-color: ${theme.color.primary};
    @font-face {
      font-family: 'TheJamsil';
      font-weight: 100;
      font-style: normal;
      src: url('/fonts/TheJamsil1Thin.ttf') format('truetype');
    }
    @font-face {
      font-family: 'TheJamsil';
      font-style: normal;
      font-weight: 300;
      src: url('/fonts/TheJamsil2Light.ttf') format('truetype');
    }
    @font-face {
      font-family: 'TheJamsil';
      font-style: normal;
      font-weight: 400;
      src: url('/fonts/TheJamsil3Regular.ttf') format('truetype');
    }
    @font-face {
      font-family: 'TheJamsil';
      font-style: normal;
      font-weight: 500;
      src: url('/fonts/TheJamsil4Medium.ttf') format('truetype');
    }
    @font-face {
      font-family: 'TheJamsil';
      font-style: normal;
      font-weight: 700;
      src: url('/fonts/TheJamsil5Bold.ttf') format('truetype');
    }
    @font-face {
      font-family: 'TheJamsil';
      font-style: normal;
      font-weight: 800;
      src: url('/fonts/TheJamsil6ExtraBold.ttf') format('truetype');
    }

    font-family: 'TheJamsil', Arial, sans-serif;
  }

  button {
    font-family: inherit; /* 상위 요소의 폰트 상속 */
  }
`

function GlobalStyles() {
  return <Global styles={globalStyles} />
}

export default GlobalStyles
