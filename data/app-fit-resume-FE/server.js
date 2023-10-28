const express = require(express);
const path = require(path);
const port = 3000;
const app = express();

app.use(express.static(__dirname + 'dist'));

app.get('*', (request, response) => {
    request.sendFile(path.resolve(__dirname, 'index.html'));
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});