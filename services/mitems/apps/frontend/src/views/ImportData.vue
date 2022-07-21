<template>
  <div>
    <div class="section">
      <div class="container">
        <h1 class="title is-4">Import by file</h1>
        <h2 class="subtitle">
          Here you can upload raw JSON and it will be imported into the database.
        </h2>
        <div class="field">
          <label class="label">File</label>
          <div class="control">
            <div class="file has-name">
              <label class="file-label">
                <input class="file-input" type="file" ref="file" @change="selectFile" />
                <span class="file-cta">
                  <span class="file-icon">
                    <i class="fas fa-upload"></i>
                  </span>
                  <span class="file-label">
                    Choose a file…
                  </span>
                </span>
                <span class="file-name">
                  {{ fileName }}
                </span>
              </label>
            </div>
          </div>
        </div>
        <div class="field">
          <div class="control">
            <button class="button is-primary" @click="importFile()">Import data</button>
          </div>
        </div>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <h1 class="title is-4">Raw JSON input</h1>
        <h2 class="subtitle">
          Here you can input raw JSON and it will be imported into the database.
        </h2>
        <div class="field">
          <label for="jsondata" class="label">JSON data</label>
          <div class="control">
                <textarea v-model="text" class="textarea" id="jsondata" rows="10" name="jsondata"
                          placeholder="Input json data"></textarea>
          </div>
        </div>
        <div class="field">
          <div class="control">
            <button class="button is-primary" @click="importText()">Import data</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api";

import { mapActions } from "vuex";

export default {
  name: "ImportData",
  components: {},
  data() {
    return {
      file: null,
      text: '',
      selectedFiles: [],
      fileName: ''
    };
  },
  methods: {
    ...mapActions(["startLoading", "stopLoading"]),
    async importFile() {
      try {
        const data = await api.importData.importFile(this.selectedFiles[0]);
        if (data.success) {
          this.$toast.info("Data imported");
        } else {
          this.$toast.error("Check your JSON and try again.")
        }
      } catch (e) {
        this.$toast.error(e.message);
      }
    },
    async importText() {
      try {
        const data = await api.importData.importText(this.text);
        if (data.success) {
          this.$toast.info("Data imported");
        } else {
          this.$toast.error("Check your JSON and try again.")
        }
      } catch (e) {
        this.$toast.error(e.message);
      }
    },
    selectFile() {
      this.selectedFiles = this.$refs.file.files;
      this.fileName = this.selectedFiles[0].name;
    }
  },
  async mounted() {
    this.startLoading();
    this.stopLoading();
  }
};
</script>
<style lang="scss" scoped>
.flows-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 1rem;
  row-gap: 1rem;
}

.section {
  margin: 2rem 0;
}
</style>
