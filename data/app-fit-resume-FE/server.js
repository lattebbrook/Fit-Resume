const { default: mongoose, mongo } = require('mongoose');
const Candidate = require('./models/Candidate');
const express = require('express');
const path = require('path');

const PORT = process.env.PORT || 3000; 
const DB_ENV_KEY = process.env.MONGO_DB_ENV;
const app = express();
const connectionString = DB_ENV_KEY;

/** serve static files from the 'public' directory */
app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));

/** send the index.html for any other requests to enable client-side routing */
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.get('/resume-app', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'resume-app.html'));
});

app.get('/test', (req, res) => {
    res.send('Hello World');
});

/** create --> when interact with database should use async and await */
app.post('/api/fe/save/candidate', async(req, res) => {
    try {

        const body = {
            ...req.body, 
            fileName: req.body.fileName,
            name: req.body.name,
            age: req.body.age,
            dateOfBirth: req.body.dateOfBirth,
            tel: req.body.tel,
            address: req.body.address,
            degree: req.body.degree,
            currentPosition: req.body.currentPosition,
            currentWorkplace: req.body.currentWorkplace,
            durationOfWork: req.body.durationOfWork,
            skills: req.body.skills,
            expectedSalary: req.body.expectedSalary,
            currentSalary: req.body.currentSalary,
        };

        console.log(req.body);
        const candidate = await Candidate.create(body);
        res.status(200).json(candidate);
    } catch (error) {
        console.log(error);
        res.status(500).json({message: error.message});
    }
});

/** find all */
 app.get('/api/fe/read/candidate/all', async(req, res) => {
    try {
        const candidate = await Candidate.find({});
        res.status(200).json(candidate);
    } catch (error) {
        res.status(500).json({message: error.message});
    }
}); 
/** find by id */
app.get('/api/fe/read/candidate/:id', async(req, res) => {
    try {
        const {id} = req.params;
        const candidate = await Candidate.findById(id);
        res.status(200).json(candidate);
    } catch (error) {
        res.status(500).json({message: error.message});
    }
});

/** find by name */
app.get('/api/fe/read/candidate', async(req, res) => {
    try {
        const name = req.body.name;

        if(name) {
            const candidate = await Candidate.findOne({ name: encryptData(name) });
            res.status(200).json({ address: candidate.address});
        } else {
            res.status(404).json("Not found");
        }
    } catch (error) {
        res.status(500).json({message: error.message});
    }
});


mongoose.set('strictQuery', false);

mongoose.connect(connectionString).then(() => {
    console.log('connect to mongodb');
    app.listen(PORT, () => {
        console.log(`Server running on http://localhost:${PORT}`);
    });    
}).catch((error) => {
    console.log(error);
});



