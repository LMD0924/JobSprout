/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        sprout: {
          50: '#effaf5',
          500: '#16a085',
          600: '#12876f',
          700: '#0f6d5a'
        }
      }
    }
  },
  plugins: []
}
