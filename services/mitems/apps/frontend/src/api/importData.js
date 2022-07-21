import axios from 'axios';

export default {
    async importFile(file) {
        let formData = new FormData();
        formData.append('file', file)
        const response = await axios.post(`/import-data`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    },
    async importText(text) {
        const response = await axios.post(`/import-data`, {text: text});
        return response.data;
    }
};