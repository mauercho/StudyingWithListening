import type { Config } from 'tailwindcss'

const config: Config = {
  mode: 'jit',
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      height: {
        'main-screen': 'calc(100vh - 5rem)',
      },
      minHeight: {
        'main-height': 'calc(100vh - 5rem)',
      },
      backgroundColor: {
        'primary-50': '#EEEDFF',
        'primary-100': '#C7C4FF',
        'primary-200': '#A09BFF',
        'primary-300': '#7A72FF',
        'primary-400': '#4F46F2',
        'primary-500': '#3A32D0',
        'primary-600': '#2921AE',
        'primary-700': '#1A148C',
        'primary-800': '#0F0A6A',
        'primary-900': '#070348',
      },
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
        'dictation-field': "url('/images/dictationField.png')",
      },
      colors: {
        'primary-50': '#EEEDFF',
        'primary-100': '#C7C4FF',
        'primary-200': '#A09BFF',
        'primary-300': '#7A72FF',
        'primary-400': '#4F46F2',
        'primary-500': '#3A32D0',
        'primary-600': '#2921AE',
        'primary-700': '#1A148C',
        'primary-800': '#0F0A6A',
        'primary-900': '#070348',
      },
      gridTemplateColumns: {
        '14': 'repeat(14, minmax(80px, 80px))',
      },
      animation: {
        'ping-slow': 'ping 2s cubic-bezier(0, 0, 0.2, 1) infinite',
        'fade-in': 'fadeIn 1s ease-in-out forwards',
        'fly-in': 'flyIn 1s ease-out forwards',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
      },
    },
  },
  plugins: [],
}
export default config
