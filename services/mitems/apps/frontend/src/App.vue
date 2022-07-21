<template>
  <div id="app">
    <header>
      <div class="container">
        <div class="row">
          <router-link to="/" class="header-title">
            <div class="logo">
              <img src="./assets/aquatic.svg" alt/>
            </div>
            <div class="content">
                <h1 class="title">Mitems</h1>
              <h2 class="subtitle">Text Manager</h2>
            </div>
          </router-link>

          <div v-if="developerMode" class="header-actions">
            <router-link class="button header-action" :to="`/import-data`">Import</router-link>
            <a class="button header-action" href="/export-data">Export</a>
            <a class="button header-action" href="/admin">Admin</a>
          </div>

          <div class="header-actions">
            <label class="checkbox">
              <input
                type="checkbox"
                :checked="developerMode"
                @change="setDeveloperMode($event.target.checked)"
              />
              Developer mode
            </label>
          </div>
        </div>
      </div>
    </header>
    <main>
      <div class="container">
        <router-view v-show="!loading" />
        <div v-show="loading" class="loader-container">
          <div class="loader">
            <div class="lds-ellipsis">
              <div></div>
              <div></div>
              <div></div>
              <div></div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  computed: {
    ...mapState(["loading", "developerMode"])
  },
  methods: {
    ...mapActions(["setDeveloperMode"])
  }
};
</script>

<style lang="scss">
@import "@/styles/main.scss";

header {
  background: #fff;
  padding: $size-1;
}

header .row {
  display: flex;
  align-items: center;
}

.header-title {
  display: flex;
  align-items: center;
  flex: 1;
  margin-right: 1rem;
  .logo img {
    display: block;
    width: 50px;
  }
  .logo {
    margin-right: 1rem;
  }
  .content {
    flex: 1;
  }
}

.loader-container {
  position: relative;
  padding: 5rem 0;
}

.loader {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.lds-ellipsis {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-ellipsis div {
  position: absolute;
  top: 33px;
  width: 13px;
  height: 13px;
  border-radius: 50%;
  background: $grey-dark;
  animation-timing-function: cubic-bezier(0, 1, 1, 0);
}
.lds-ellipsis div:nth-child(1) {
  left: 8px;
  animation: lds-ellipsis1 0.6s infinite;
}
.lds-ellipsis div:nth-child(2) {
  left: 8px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(3) {
  left: 32px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(4) {
  left: 56px;
  animation: lds-ellipsis3 0.6s infinite;
}
@keyframes lds-ellipsis1 {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes lds-ellipsis3 {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
@keyframes lds-ellipsis2 {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(24px, 0);
  }
}
</style>
