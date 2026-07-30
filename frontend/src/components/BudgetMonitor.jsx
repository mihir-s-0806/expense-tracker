import React from 'react';
import { Target } from 'lucide-react';

export default function BudgetMonitor({ budgets }) {
  if (!budgets || budgets.length === 0) {
    return (
      <div class="glass-card p-6 lg:col-span-2">
        <h2 class="text-lg font-semibold text-slate-200 mb-4 flex items-center gap-2">
          <Target className="w-5 h-5 text-amber-400" /> Monthly Budget Monitor
        </h2>
        <div class="p-6 text-slate-400 text-sm text-center">
          No monthly budgets configured yet.
        </div>
      </div>
    );
  }

  return (
    <div class="glass-card p-6 lg:col-span-2">
      <h2 class="text-lg font-semibold text-slate-200 mb-4 flex items-center gap-2">
        <Target className="w-5 h-5 text-amber-400" /> Monthly Budget Monitor
      </h2>
      <div class="space-y-4 max-h-72 overflow-y-auto pr-2">
        {budgets.map((b) => {
          let badgeColor = 'bg-emerald-500/20 text-emerald-400 border-emerald-500/30';
          let barColor = 'bg-emerald-500';

          if (b.alertStatus === 'WARNING') {
            badgeColor = 'bg-amber-500/20 text-amber-400 border-amber-500/30';
            barColor = 'bg-amber-500';
          } else if (b.alertStatus === 'EXCEEDED') {
            badgeColor = 'bg-rose-500/20 text-rose-400 border-rose-500/30';
            barColor = 'bg-rose-500';
          }

          const capUsage = Math.min(b.usagePercentage || 0, 100);

          return (
            <div key={b.budgetId} class="p-3 bg-slate-900/50 rounded-lg border border-slate-800">
              <div class="flex justify-between items-center mb-1.5 text-sm">
                <span class="font-medium text-slate-200">{b.categoryName}</span>
                <span class={`text-xs px-2 py-0.5 rounded border ${badgeColor} font-semibold`}>
                  {b.alertStatus === 'EXCEEDED'
                    ? '🚨 EXCEEDED'
                    : b.alertStatus === 'WARNING'
                    ? '⚠️ WARNING (80%+)'
                    : 'OK'}
                </span>
              </div>
              <div class="w-full bg-slate-800 rounded-full h-2 overflow-hidden mb-1">
                <div class={`${barColor} h-2 rounded-full`} style={{ width: `${capUsage}%` }}></div>
              </div>
              <div class="flex justify-between text-xs text-slate-400">
                <span>
                  Spent: ₹{(b.currentSpent || 0).toFixed(2)} / ₹{(b.monthlyLimit || 0).toFixed(2)}
                </span>
                <span>{(b.usagePercentage || 0).toFixed(1)}%</span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
