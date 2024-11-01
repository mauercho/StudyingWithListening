// GlobalStyles.js
import React from 'react';
import { Global, css } from '@emotion/react';

function GlobalStyles() {
  return (
    <Global
      styles={css`
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

        /* 전역 폰트 적용 */
        body {
          font-family: 'TheJamsil', Arial, sans-serif;
        }
      `}
    />
  );
}

export default GlobalStyles;
