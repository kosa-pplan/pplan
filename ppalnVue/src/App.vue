<template>
  <div id="app">
    <header-compo></header-compo>
    <middle-compo></middle-compo>
    <footer-compo></footer-compo>
  </div>
</template>

<script>
import HeaderCompo from "@/components/HeaderCompo.vue";
import MiddleCompo from "@/components/MiddleCompo.vue";
import FooterCompo from "@/components/FooterCompo.vue";
export default {
  name: 'App',
  components: {HeaderCompo, MiddleCompo, FooterCompo}
}
</script>

<style>
@import 'assets/base.css';

</style>
