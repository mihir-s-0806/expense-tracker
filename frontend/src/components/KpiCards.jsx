import React from 'react';
import { ArrowDownLeft, ArrowUpRight, PiggyBank, TrendingUp } from 'lucide-react';

export default function KpiCards({ summary }) {
  const totalIncome = summary?.totalIncome || 0;
  const totalExpenses = summary?.totalExpenses || 0;
  const netSavings = summary?.netSavings || 0;
  const savingsRate = summary?.savingsRatePercentage || 0;

  return (
    <section class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
      {/* Total Income */}
      <div class="glass-card p-6 flex items-center justify-between">
        <div>
          <p class="text-xs uppercase tracking-wider text-slate-400 font-semibold mb-1">Total Income</p>
          <p class="text-2xl font-bold text-emerald-400">₹{totalIncome.toFixed(2)}</p>
        </div>
        <div class="w-10 h-10 rounded-lg bg-emerald-500/10 text-emerald-400 flex items-center justify-center">
          <ArrowDownLeft className="w-5 h-5" />
        </div>
      </div>

      {/* Total Expenses */}
      <div class="glass-card p-6 flex items-center justify-between">
        <div>
          <p class="text-xs uppercase tracking-wider text-slate-400 font-semibold mb-1">Total Expenses</p>
          <p class="text-2xl font-bold text-rose-400">₹{totalExpenses.toFixed(2)}</p>
        </div>
        <div class="w-10 h-10 rounded-lg bg-rose-500/10 text-rose-400 flex items-center justify-center">
          <ArrowUpRight className="w-5 h-5" />
        </div>
      </div>

      {/* Net Savings */}
      <div class="glass-card p-6 flex items-center justify-between">
        <div>
          <p class="text-xs uppercase tracking-wider text-slate-400 font-semibold mb-1">Net Savings</p>
          <p class="text-2xl font-bold text-indigo-400">₹{netSavings.toFixed(2)}</p>
        </div>
        <div class="w-10 h-10 rounded-lg bg-indigo-500/10 text-indigo-400 flex items-center justify-center">
          <PiggyBank className="w-5 h-5" />
        </div>
      </div>

      {/* Savings Rate */}
      <div class="glass-card p-6 flex items-center justify-between">
        <div>
          <p class="text-xs uppercase tracking-wider text-slate-400 font-semibold mb-1">Savings Rate</p>
          <p class="text-2xl font-bold text-cyan-400">{savingsRate.toFixed(1)}%</p>
        </div>
        <div class="w-10 h-10 rounded-lg bg-cyan-500/10 text-cyan-400 flex items-center justify-center">
          <TrendingUp className="w-5 h-5" />
        </div>
      </div>
    </section>
  );
}
