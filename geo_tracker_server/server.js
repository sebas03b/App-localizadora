// server.js
const express = require('express');
const fs = require('fs');
const app = express();
app.use(express.json());

const DB_FILE = 'locations.json';
if (!fs.existsSync(DB_FILE)) fs.writeFileSync(DB_FILE, '[]');

app.post('/locations', (req, res) => {
  const { deviceId, latitude, longitude, timestamp } = req.body;
  const entry = {
    deviceId: deviceId || 'unknown',
    latitude,
    longitude,
    timestamp: timestamp || new Date().toISOString()
  };
  // append to file (simple persistence)
  const arr = JSON.parse(fs.readFileSync(DB_FILE, 'utf8'));
  arr.push(entry);
  fs.writeFileSync(DB_FILE, JSON.stringify(arr, null, 2));
  console.log('Location received:', entry);
  res.status(201).json({ ok: true });
});

app.get('/locations', (req, res) => {
  const arr = JSON.parse(fs.readFileSync(DB_FILE, 'utf8'));
  res.json(arr);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, '0.0.0.0', () => console.log(`Server listening on http://0.0.0.0:${PORT}`));

