import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import KpiCards from './components/KpiCards';
import CategoryChart from './components/CategoryChart';
import BudgetMonitor from './components/BudgetMonitor';
import TransactionTable from './components/TransactionTable';
import AddTransactionModal from './components/AddTransactionModal';
import { api } from './services/api';

export default function App() {
  const [summary, setSummary] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [summaryData, txData] = await Promise.all([
        api.getSummary(),
        api.getTransactions(),
      ]);
      setSummary(summaryData);
      setTransactions(txData);
    } catch (err) {
      console.error('Error fetching dashboard data:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteTransaction = async (id) => {
    if (window.confirm('Are you sure you want to delete this transaction?')) {
      try {
        await api.deleteTransaction(id);
        loadData();
      } catch (err) {
        console.error('Error deleting transaction:', err);
      }
    }
  };

  return (
    <div class="p-4 md:p-8 max-w-7xl mx-auto space-y-8">
      {/* Header */}
      <Header onOpenModal={() => setIsModalOpen(true)} />

      {/* KPI Stats Cards */}
      <KpiCards summary={summary} />

      {/* Analytics & Budget Section */}
      <section class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <CategoryChart breakdown={summary?.categoryBreakdown} />
        <BudgetMonitor budgets={summary?.budgetStatuses} />
      </section>

      {/* Transactions Table */}
      <TransactionTable
        transactions={transactions}
        onDeleteTransaction={handleDeleteTransaction}
      />

      {/* Modal */}
      <AddTransactionModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSuccess={loadData}
      />
    </div>
  );
}
