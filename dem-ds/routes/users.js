const express = require('express');
const router = express.Router();
const data = require('../db');

// GET users
router.get('/', async (req, res) => {
    try {
        res.json(data);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});


module.exports = router;
