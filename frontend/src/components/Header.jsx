import React from 'react';
import { Wallet, Plus, FileSpreadsheet } from 'lucide-react';
import { api } from '../services/api';

export default function Header({ onOpenModal }) {
  const handleDownloadCsv = () => {
    window.location.href = api.getExportCsvUrl();
  };

  return (
    <header class="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 glass-card p-6">
      <div class="flex items-center gap-4">
        <div class="w-12 h-12 rounded-xl bg-indigo-600 flex items-center justify-center text-white text-2xl shadow-lg shadow-indigo-500/30">
          <Wallet className="w-6 h-6" />
        </div>
        <div>
          <h1 class="text-2xl md:text-3xl font-bold bg-gradient-to-r from-white via-indigo-200 to-indigo-400 bg-clip-text text-transparent">
            Smart Expense & Budget Tracker
          </h1>
          <p class="text-sm text-slate-400">React + Spring Boot REST API & Live Analytics Dashboard</p>
        </div>
      </div>

      <div class="flex items-center gap-3 w-full md:w-auto">
        <button
          onClick={onOpenModal}
          class="glow-button bg-indigo-600 hover:bg-indigo-500 text-white font-medium px-4 py-2.5 rounded-lg text-sm flex items-center gap-2"
        >
          <Plus className="w-4 h-4" /> Add Transaction
        </button>
        <button
          onClick={handleDownloadCsv}
          class="bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700 font-medium px-4 py-2.5 rounded-lg text-sm flex items-center gap-2 transition"
        >
          <FileSpreadsheet className="w-4 h-4 text-emerald-400" /> Export CSV
        </button>
      </div>
    </header>
  );
}
