import axios from 'axios';

export default {
    async all() {
        const response = await axios.get("/api/flows");
        return response.data.flows;
    },
    async get(id) {
        const response = await axios.get(`/api/flows/${id}`);
        return response.data;
    },
    async create(flow) {
        const response = await axios.post(`/api/flows/`, flow);
        return response.data;
    },
    async update(id, flow) {
        const response = await axios.patch(`/api/flows/${id}`, flow);
        return response.data;
    },
    async delete(id) {
        const response = await axios.delete(`/api/flows/${id}`);
        return response.data;
    },
};