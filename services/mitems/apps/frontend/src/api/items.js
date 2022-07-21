import axios from 'axios';

export default {
    async get(id) {
        const response = await axios.get(`/api/items/${id}`);
        return response.data;
    },
    async create(flowId, item) {
        const response = await axios.post(`/api/items/${flowId}/create`, item);
        return response.data;
    },
    async update(id, item) {
        const response = await axios.patch(`/api/items/${id}`, item);
        return response.data;
    },
    async delete(id) {
        const response = await axios.delete(`/api/items/${id}`);
        return response.data;
    },
};