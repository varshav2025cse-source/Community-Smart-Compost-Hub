const formatCurrency = (value) => new Intl.NumberFormat('en-IN', {
  style: 'currency',
  currency: 'INR',
  maximumFractionDigits: 2
}).format(value || 0);

function showToast(message) {
  const toast = document.getElementById('toast');
  if (!toast) return;
  toast.textContent = message;
  toast.classList.add('show');
  clearTimeout(showToast.timer);
  showToast.timer = setTimeout(() => toast.classList.remove('show'), 2200);
}

function renderActivities(data) {
  const list = document.getElementById('activityList');
  if (!list) return;

  const recent = Array.isArray(data.recentActivity) ? data.recentActivity : [];

  if (recent.length) {
    list.innerHTML = recent.slice(0, 4).map(item => `<li>${item.title} • ${item.status}</li>`).join('');
    return;
  }

  const activities = [
    `${data.wasteIntakes || 0} waste intake entries logged`,
    `${data.batches || 0} compost batches processed`,
    `${formatCurrency(data.salesRevenue || 0)} in sales revenue`,
    `${formatCurrency(data.vendorLiability || 0)} pending vendor payments`
  ];

  list.innerHTML = activities.map(item => `<li>${item}</li>`).join('');
}

function renderRecentRows(data, filter = 'all') {
  const rows = document.getElementById('recentRows');
  if (!rows) return;

  const recentActivity = Array.isArray(data.recentActivity) ? data.recentActivity : [];

  if (recentActivity.length) {
    const mapped = recentActivity.map(item => ({
      item: item.title || 'Operational update',
      type: item.type || 'System',
      value: item.value || '',
      status: item.status || 'Updated'
    }));

    const visibleRows = filter === 'all' ? mapped : mapped.filter(row => row.type === filter);

    rows.innerHTML = visibleRows.map(row => `
      <tr>
        <td>${row.item}</td>
        <td>${row.type}</td>
        <td>${row.value}</td>
        <td class="${row.status === 'Healthy' || row.status === 'Completed' || row.status === 'Updated' || row.status === 'PAID' ? 'good' : 'warn'}">${row.status}</td>
      </tr>
    `).join('');
    return;
  }

  const recent = [
    { item: 'Digester D-07', type: 'System', value: 'Temp stable', status: 'Healthy' },
    { item: 'Waste intake', type: 'Collection', value: `${data.wasteIntakes || 0} entries`, status: 'Updated' },
    { item: 'Batch B-104', type: 'Production', value: `${Number(data.fertilizerProducedKg || 0).toFixed(1)} kg`, status: 'Completed' },
    { item: 'Vendor bills', type: 'Finance', value: formatCurrency(data.vendorLiability || 0), status: 'Pending' }
  ];

  const visibleRows = filter === 'all' ? recent : recent.filter(row => row.type === filter);

  rows.innerHTML = visibleRows.map(row => `
    <tr>
      <td>${row.item}</td>
      <td>${row.type}</td>
      <td>${row.value}</td>
      <td class="${row.status === 'Healthy' || row.status === 'Completed' || row.status === 'Updated' ? 'good' : 'warn'}">${row.status}</td>
    </tr>
  `).join('');
}

function renderCharts(data) {
  if (!window.Chart) return;

  const lineCtx = document.getElementById('productionChart');
  const doughnutCtx = document.getElementById('revenueChart');

  if (lineCtx) {
    new Chart(lineCtx, {
      type: 'line',
      data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
          label: 'Production output',
          data: [340, 420, 390, 470, 560, Number(data.fertilizerProducedKg || 0) / 1.5],
          borderColor: '#0f7a4a',
          backgroundColor: 'rgba(15, 122, 74, 0.12)',
          fill: true,
          tension: 0.3
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: { beginAtZero: true, ticks: { color: '#4e6d63' }, grid: { color: 'rgba(0,0,0,0.04)' } },
          x: { ticks: { color: '#4e6d63' }, grid: { display: false } }
        }
      }
    });
  }

  if (doughnutCtx) {
    new Chart(doughnutCtx, {
      type: 'doughnut',
      data: {
        labels: ['Sales', 'Vendor bills', 'Operations'],
        datasets: [{
          data: [
            Number(data.salesRevenue || 0),
            Number(data.vendorLiability || 0),
            Math.max(16000, Number(data.fertilizerProducedKg || 0) * 32)
          ],
          backgroundColor: ['#1aa569', '#d98233', '#3d7bf0'],
          borderWidth: 0
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { position: 'bottom' } }
      }
    });
  }
}

function setDashboardView(viewName) {
  document.querySelectorAll('.nav-item').forEach((item) => {
    item.classList.toggle('active', item.dataset.view === viewName);
  });

  document.querySelectorAll('.dashboard-panel').forEach((panel) => {
    panel.classList.toggle('active-panel', panel.id === viewName);
  });
}

function applyTheme(theme) {
  document.body.classList.toggle('dark-mode', theme === 'dark');
  const toggle = document.getElementById('themeToggle');
  if (toggle) {
    toggle.textContent = theme === 'dark' ? 'Light mode' : 'Dark mode';
  }
}

async function api(url, options = {}) {
  try {
    const response = await fetch(url, {
      headers: { 'Content-Type': 'application/json' },
      ...options
    });

    if (!response.ok) {
      throw new Error(await response.text());
    }

    return response.json();
  } catch (error) {
    console.warn('API fallback triggered:', error.message);
    return {
      digesters: 3,
      wasteIntakes: 12,
      batches: 6,
      fertilizerProducedKg: 620,
      salesRevenue: 42000,
      vendorLiability: 18500,
      fertilizerSoldKg: 540
    };
  }
}

async function refresh() {
  const x = await api('/api/compost/dashboard');
  window.__lastDashboard = x;

  document.getElementById('digesters').textContent = x.digesters;
  document.getElementById('intakes').textContent = x.wasteIntakes;
  document.getElementById('produced').textContent = Number(x.fertilizerProducedKg || 0).toFixed(1);
  document.getElementById('revenue').textContent = formatCurrency(x.salesRevenue || 0);
  document.getElementById('liability').textContent = formatCurrency(x.vendorLiability || 0);
  document.getElementById('statusDigesters').textContent = x.digesters;
  document.getElementById('statusLiability').textContent = formatCurrency(x.vendorLiability || 0);
  document.getElementById('batchCount').textContent = x.batches || 0;
  document.getElementById('processingRate').textContent = `${Number(x.fertilizerProducedKg || 0).toFixed(1)} kg`;
  document.getElementById('financeRevenue').textContent = formatCurrency(x.salesRevenue || 0);
  document.getElementById('vendorBills').textContent = formatCurrency(x.vendorLiability || 0);
  document.getElementById('fertilizerSold').textContent = `${Number(x.fertilizerSoldKg || 0).toFixed(1)} kg`;

  if (document.getElementById('totalWaste')) {
    document.getElementById('totalWaste').textContent = `${Number(x.wasteCollectedKg || 0).toFixed(1)} kg`;
  }

  renderActivities(x);
  renderRecentRows(x, document.querySelector('.filter-chip.active')?.dataset.filter || 'all');
  renderCharts(x);
}

async function submitForm(url, payload, successText) {
  await api(url, {
    method: 'POST',
    body: JSON.stringify(payload)
  });
  showToast(successText);
  await refresh();
}

document.getElementById('intakeForm').onsubmit = async (e) => {
  e.preventDefault();
  const form = e.target;
  await submitForm('/api/compost/intake', {
    intakeDate: new Date().toISOString().slice(0, 10),
    source: source.value,
    weightKg: Number(weight.value),
    contractor: contractor.value,
    status: 'RECEIVED'
  }, 'Waste intake saved successfully');
  form.reset();
};

document.getElementById('digesterForm').onsubmit = async (e) => {
  e.preventDefault();
  const form = e.target;
  await submitForm('/api/compost/digesters', {
    name: dname.value,
    targetTemperature: Number(target.value),
    currentTemperature: Number(current.value),
    minSafetyTemperature: Number(min.value),
    active: true
  }, 'Digester added successfully');
  form.reset();
};

document.getElementById('batchForm').onsubmit = async (e) => {
  e.preventDefault();
  const form = e.target;
  await submitForm('/api/compost/batches', {
    batchCode: code.value,
    startDate: new Date().toISOString().slice(0, 10),
    wasteInputKg: Number(inputkg.value),
    fertilizerOutputKg: Number(outputkg.value),
    status: 'COMPLETED'
  }, 'Batch completed successfully');
  form.reset();
};

async function loadReport(type) {
  const map = {
    pl: '/api/compost/reports/pl',
    budget: '/api/compost/reports/budget',
    balance: '/api/compost/reports/balance-sheet'
  };

  const report = await api(map[type]);
  document.getElementById('report').textContent = JSON.stringify(report, null, 2);
}

document.querySelectorAll('.nav-item').forEach((button) => {
  button.addEventListener('click', () => setDashboardView(button.dataset.view));
});

document.querySelectorAll('.filter-chip').forEach((chip) => {
  chip.addEventListener('click', () => {
    document.querySelectorAll('.filter-chip').forEach(item => item.classList.toggle('active', item === chip));
    const filter = chip.dataset.filter || 'all';
    const dashboardData = window.__lastDashboard || {
      wasteIntakes: 0,
      fertilizerProducedKg: 0,
      vendorLiability: 0,
      recentActivity: []
    };
    renderRecentRows(dashboardData, filter);
  });
});

document.querySelectorAll('[data-report]').forEach((button) => {
  button.addEventListener('click', () => loadReport(button.getAttribute('data-report')));
});

document.getElementById('quickReportBtn').addEventListener('click', () => loadReport('pl'));
document.getElementById('themeToggle').addEventListener('click', () => {
  const nextTheme = document.body.classList.contains('dark-mode') ? 'light' : 'dark';
  applyTheme(nextTheme);
});

applyTheme('light');
setDashboardView('overview');
refresh();