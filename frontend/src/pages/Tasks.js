import React, { useEffect, useState } from 'react';
import axios from '../api/axios';
import useAuth from '../auth/useAuth';

export default function Tasks() {
  const { logout } = useAuth();
  const [tasks, setTasks] = useState([]);
  const [form, setForm] = useState({ title: '', description: '', completed: false });
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const res = await axios.get('/tasks');
      setTasks(res.data);
    } catch (err) {
      setError('Error fetching tasks');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingId) {
        await axios.put(`/tasks/${editingId}`, form);
      } else {
        await axios.post('/tasks', form);
      }
      setForm({ title: '', description: '', completed: false });
      setEditingId(null);
      fetchTasks();
    } catch (err) {
      setError('Error saving task');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure?')) {
      try {
        await axios.delete(`/tasks/${id}`);
        fetchTasks();
      } catch {
        setError('Error deleting task');
      }
    }
  };

  const startEdit = (task) => {
    setForm({ title: task.title, description: task.description, completed: task.completed });
    setEditingId(task.id);
  };

  const handleLogout = () => {
    logout();
    window.location.href = '/login';
  };

  return (
    <div style={{ maxWidth: 600, margin: '2rem auto' }}>
      <h2>Tasks</h2>
      <button onClick={handleLogout}>Logout</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Title"
          value={form.title}
          onChange={(e) => setForm({ ...form, title: e.target.value })}
          required
        /><br />
        <input
          placeholder="Description"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
          required
        /><br />
        <label>
          <input
            type="checkbox"
            checked={form.completed}
            onChange={(e) => setForm({ ...form, completed: e.target.checked })}
          />
          Completed
        </label><br />
        <button type="submit">{editingId ? 'Update' : 'Create'} Task</button>
      </form>

      <ul style={{ marginTop: '2rem' }}>
        {tasks.map((task) => (
          <li key={task.id}>
            <strong>{task.title}</strong> — {task.description} — {task.completed ? '✅' : '❌'}
            <br />
            Assigned to: {task.username || 'Not assigned'}<br />
            <button onClick={() => startEdit(task)}>Edit</button>
            <button onClick={() => handleDelete(task.id)}>Delete</button>
            <hr />
          </li>
        ))}
      </ul>
    </div>
  );
}
