/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: 'var(--brand)',
        accent: 'var(--accent)',
        line: 'var(--line)',
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
