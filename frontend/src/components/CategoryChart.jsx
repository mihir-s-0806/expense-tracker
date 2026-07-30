import React from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';
import { PieChart } from 'lucide-react';

ChartJS.register(ArcElement, Tooltip, Legend);

export default function CategoryChart({ breakdown }) {
  if (!breakdown || breakdown.length === 0) {
    return (
      <div class="glass-card p-6 lg:col-span-1 flex flex-col justify-between">
        <h2 class="text-lg font-semibold text-slate-200 mb-4 flex items-center gap-2">
          <PieChart className="w-5 h-5 text-indigo-400" /> Category Breakdown
        </h2>
        <div class="h-64 flex items-center justify-center text-slate-400 text-sm">
          No expense data recorded yet.
        </div>
      </div>
    );
  }

  const data = {
    labels: breakdown.map((b) => b.categoryName),
    datasets: [
      {
        data: breakdown.map((b) => b.amountSpent),
        backgroundColor: breakdown.map((b) => b.colorHex || '#6366F1'),
        borderWidth: 0,
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
        labels: {
          color: '#cbd5e1',
          font: { size: 11 },
        },
      },
    },
  };

  return (
    <div class="glass-card p-6 lg:col-span-1 flex flex-col justify-between">
      <h2 class="text-lg font-semibold text-slate-200 mb-4 flex items-center gap-2">
        <PieChart className="w-5 h-5 text-indigo-400" /> Category Breakdown
      </h2>
      <div class="relative w-full h-64 flex items-center justify-center">
        <Doughnut data={data} options={options} />
      </div>
    </div>
  );
}
