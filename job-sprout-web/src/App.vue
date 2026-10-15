<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { RouterView } from 'vue-router'

const dot = ref(null)
const ring = ref(null)
let mx = 0
let my = 0
let rx = 0
let ry = 0
let animationId = 0

function move(event) {
  mx = event.clientX
  my = event.clientY
  if (dot.value) dot.value.style.transform = `translate(${mx}px, ${my}px) translate(-50%, -50%)`
}
function animate() {
  rx += (mx - rx) * 0.18
  ry += (my - ry) * 0.18
  if (ring.value) ring.value.style.transform = `translate(${rx}px, ${ry}px) translate(-50%, -50%)`
  animationId = requestAnimationFrame(animate)
}
onMounted(() => {
  window.addEventListener('mousemove', move)
  animationId = requestAnimationFrame(animate)
})
onBeforeUnmount(() => {
  window.removeEventListener('mousemove', move)
  cancelAnimationFrame(animationId)
})
</script>

<template>
  <div ref="dot" class="cursor-dot app-cursor-dot"></div>
  <div ref="ring" class="cursor-ring app-cursor-ring"></div>
  <RouterView />
</template>
