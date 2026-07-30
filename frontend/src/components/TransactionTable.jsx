import React from 'react';
import { Receipt, Trash2 } from 'lucide-react';

export default function TransactionTable({ transactions, onDeleteTransaction }) {
  return (
    <section class="glass-card p-6">
      <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 mb-6">
        <h2 class="text-lg font-semibold text-slate-200 flex items-center gap-2">
          <Receipt className="w-5 h-5 text-emerald-400" /> Recent Transactions
        </h2>
        <div class="text-xs text-slate-400">
          Showing latest entries ({transactions?.length || 0})
        </div>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full text-left border-collapse">
          <thead>
            <tr class="border-b border-slate-700 text-slate-400 text-xs uppercase tracking-wider">
              <th class="py-3 px-4">Date</th>
              <th class="py-3 px-4">Title</th>
              <th class="py-3 px-4">Category</th>
              <th class="py-3 px-4">Type</th>
              <th class="py-3 px-4 text-right">Amount</th>
              <th class="py-3 px-4 text-center">Action</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-slate-800 text-sm text-slate-300">
            {(!transactions || transactions.length === 0) ? (
              <tr>
                <td colSpan="6" class="py-8 text-center text-slate-500 text-sm">
                  No transactions recorded yet. Click "Add Transaction" to create your first entry.
                </td>
              </tr>
            ) : (
              transactions.map((t) => {
                const isExpense = t.type === 'EXPENSE';
                const amountClass = isExpense ? 'text-rose-400 font-semibold' : 'text-emerald-400 font-semibold';
                const amountPrefix = isExpense ? '-' : '+';

                return (
                  <tr key={t.id} class="hover:bg-slate-800/40 transition">
                    <td class="py-3 px-4">{t.date}</td>
                    <td class="py-3 px-4 font-medium text-white">{t.title}</td>
                    <td class="py-3 px-4">
                      <span
                        class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium"
                        style={{
                          backgroundColor: `${t.category?.colorHex || '#6366F1'}20`,
                          color: t.category?.colorHex || '#6366F1',
                        }}
                      >
                        {t.category?.name}
                      </span>
                    </td>
                    <td
                      class={`py-3 px-4 text-xs font-semibold ${
                        isExpense ? 'text-rose-400' : 'text-emerald-400'
                      }`}
                    >
                      {t.type}
                    </td>
                    <td class={`py-3 px-4 text-right ${amountClass}`}>
                      {amountPrefix}₹{t.amount?.toFixed(2)}
                    </td>
                    <td class="py-3 px-4 text-center">
                      <button
                        onClick={() => onDeleteTransaction(t.id)}
                        class="text-slate-500 hover:text-rose-400 transition"
                        title="Delete Transaction"
                      >
                        <Trash2 className="w-4 h-4 inline" />
                      </button>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </section>
  );
}
