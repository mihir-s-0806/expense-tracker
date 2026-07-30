import React, { useState, useEffect } from 'react';
import { X, Plus } from 'lucide-react';
import { api } from '../services/api';

export default function AddTransactionModal({ isOpen, onClose, onSuccess }) {
  const [title, setTitle] = useState('');
  const [type, setType] = useState('EXPENSE');
  const [amount, setAmount] = useState('');
  const [categoryId, setCategoryId] = useState('');
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);
  const [notes, setNotes] = useState('');
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);

  // New category creation state
  const [isAddingCategory, setIsAddingCategory] = useState(false);
  const [newCategoryName, setNewCategoryName] = useState('');

  useEffect(() => {
    if (isOpen) {
      loadCategories(type);
    }
  }, [isOpen, type]);

  const loadCategories = async (selectedType) => {
    try {
      const data = await api.getCategories(selectedType);
      setCategories(data);
      if (data && data.length > 0) {
        setCategoryId(data[0].id);
      } else {
        setCategoryId('');
      }
    } catch (err) {
      console.error('Failed to load categories:', err);
    }
  };

  const handleTypeChange = (e) => {
    const newType = e.target.value;
    setType(newType);
    setIsAddingCategory(false);
    loadCategories(newType);
  };

  const handleCreateCategory = async (e) => {
    e.preventDefault();
    if (!newCategoryName.trim()) return;

    try {
      const colors = ['#EF4444', '#F59E0B', '#10B981', '#3B82F6', '#8B5CF6', '#EC4899', '#06B6D4', '#6366F1'];
      const randomColor = colors[Math.floor(Math.random() * colors.length)];

      const created = await api.createCategory({
        name: newCategoryName.trim(),
        type: type,
        colorHex: randomColor,
        icon: 'folder'
      });

      const updated = await api.getCategories(type);
      setCategories(updated);
      setCategoryId(created.id);
      setNewCategoryName('');
      setIsAddingCategory(false);
    } catch (err) {
      console.error('Error creating category:', err);
      alert('Category already exists or failed to create.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title || !amount || !categoryId || !date) {
      alert('Please fill out all required fields, including selecting a Category.');
      return;
    }

    setLoading(true);
    try {
      await api.createTransaction({
        title,
        type,
        amount: parseFloat(amount),
        categoryId: parseInt(categoryId),
        date,
        notes,
      });

      // Reset form
      setTitle('');
      setAmount('');
      setNotes('');
      setDate(new Date().toISOString().split('T')[0]);
      onSuccess();
      onClose();
    } catch (err) {
      console.error('Failed to create transaction:', err);
      alert('Failed to save transaction. Please check server connection.');
    } finally {
      setLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div class="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center p-4 z-50">
      <div class="glass-card w-full max-w-md p-6 space-y-6">
        <div class="flex justify-between items-center border-b border-slate-700 pb-3">
          <h3 class="text-lg font-semibold text-white">Add New Transaction</h3>
          <button onClick={onClose} class="text-slate-400 hover:text-white">
            <X className="w-5 h-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit} class="space-y-4">
          <div>
            <label class="block text-xs font-semibold text-slate-400 uppercase mb-1">Title</label>
            <input
              type="text"
              required
              placeholder="e.g. Grocery Shopping"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-xs font-semibold text-slate-400 uppercase mb-1">Type</label>
              <select
                value={type}
                onChange={handleTypeChange}
                class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
              >
                <option value="EXPENSE">Expense</option>
                <option value="INCOME">Income</option>
              </select>
            </div>

            <div>
              <label class="block text-xs font-semibold text-slate-400 uppercase mb-1">Amount (₹)</label>
              <input
                type="number"
                step="0.01"
                required
                placeholder="0.00"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
              />
            </div>
          </div>

          {/* Category Dropdown & Custom Category Input */}
          <div>
            <div class="flex justify-between items-center mb-1">
              <label class="text-xs font-semibold text-slate-400 uppercase">Category</label>
              <button
                type="button"
                onClick={() => setIsAddingCategory(!isAddingCategory)}
                class="text-xs text-indigo-400 hover:text-indigo-300 font-medium flex items-center gap-1"
              >
                <Plus className="w-3 h-3" /> {isAddingCategory ? 'Select Existing' : 'New Category'}
              </button>
            </div>

            {isAddingCategory ? (
              <div class="flex gap-2">
                <input
                  type="text"
                  placeholder="Category Name (e.g. Subscriptions)"
                  value={newCategoryName}
                  onChange={(e) => setNewCategoryName(e.target.value)}
                  class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white text-sm focus:outline-none focus:border-indigo-500"
                />
                <button
                  type="button"
                  onClick={handleCreateCategory}
                  class="px-3 py-2 bg-indigo-600 hover:bg-indigo-500 text-white rounded-lg text-xs font-medium whitespace-nowrap"
                >
                  Save
                </button>
              </div>
            ) : (
              <select
                value={categoryId}
                onChange={(e) => setCategoryId(e.target.value)}
                required
                class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
              >
                {categories.length === 0 ? (
                  <option value="">No categories found. Click 'New Category'</option>
                ) : (
                  categories.map((c) => (
                    <option key={c.id} value={c.id}>
                      {c.name}
                    </option>
                  ))
                )}
              </select>
            )}
          </div>

          <div>
            <label class="block text-xs font-semibold text-slate-400 uppercase mb-1">Date</label>
            <input
              type="date"
              required
              value={date}
              onChange={(e) => setDate(e.target.value)}
              class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div>
            <label class="block text-xs font-semibold text-slate-400 uppercase mb-1">Notes (Optional)</label>
            <input
              type="text"
              placeholder="Additional details..."
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              class="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white focus:outline-none focus:border-indigo-500"
            />
          </div>

          <div class="flex justify-end gap-3 pt-4 border-t border-slate-700">
            <button
              type="button"
              onClick={onClose}
              class="px-4 py-2 bg-slate-800 text-slate-300 rounded-lg hover:bg-slate-700 text-sm"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading || categories.length === 0 && !categoryId}
              class="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-500 text-sm font-medium disabled:opacity-50"
            >
              {loading ? 'Saving...' : 'Save Transaction'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
