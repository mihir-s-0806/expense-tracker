import axios from 'axios';

const API_BASE = '/api';

export const api = {
  // Insights
  getSummary: async (monthYear) => {
    const res = await axios.get(`${API_BASE}/insights/summary`, {
      params: { monthYear }
    });
    return res.data;
  },

  // Transactions
  getTransactions: async (params = {}) => {
    const res = await axios.get(`${API_BASE}/transactions`, { params });
    return res.data;
  },

  createTransaction: async (payload) => {
    const res = await axios.post(`${API_BASE}/transactions`, payload);
    return res.data;
  },

  deleteTransaction: async (id) => {
    await axios.delete(`${API_BASE}/transactions/${id}`);
  },

  // Categories
  getCategories: async (type) => {
    const res = await axios.get(`${API_BASE}/categories`, {
      params: { type }
    });
    return res.data;
  },

  createCategory: async (payload) => {
    const res = await axios.post(`${API_BASE}/categories`, payload);
    return res.data;
  },

  // Budgets
  getBudgets: async (monthYear) => {
    const res = await axios.get(`${API_BASE}/budgets`, {
      params: { monthYear }
    });
    return res.data;
  },

  // Export CSV URL
  getExportCsvUrl: (monthYear) => {
    return monthYear 
      ? `${API_BASE}/transactions/export/csv?monthYear=${monthYear}`
      : `${API_BASE}/transactions/export/csv`;
  }
};
